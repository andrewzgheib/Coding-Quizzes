package com.example.codingquizzes.ui.view

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.codingquizzes.R
import com.example.codingquizzes.databinding.ActivityEditProfileBinding
import com.example.codingquizzes.ui.vm.UserProfileViewModel
import kotlinx.coroutines.launch

class UserProfileActivity : AppCompatActivity() {

    private lateinit var profile_error: TextView
    private lateinit var profile_message: TextView

    private lateinit var binding: ActivityEditProfileBinding
    private val profileVM: UserProfileViewModel by viewModels()

    private var selectedImageUri: Uri? = null

    private val CAMERA_PERMISSION_CODE = 100
    private val GALLERY_PERMISSION_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    private val GALLERY_REQUEST_CODE = 103

    private val cameraPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.CAMERA
    } else {
        Manifest.permission.CAMERA
    }

    private val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profile_error = findViewById(R.id.profile_error)
        profile_message = findViewById(R.id.profile_message)

        setupUI()
        observeData()
        setupSaveButton()
        setupImagePicker()
        observeProfilePicture()
        setupBackNavigation()
    }

    private fun setupUI() {
        setSupportActionBar(binding.profileBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Profile"
    }

    private fun observeData() {
        lifecycleScope.launch {
            profileVM.currentUser.collect { user ->
                user?.let {
                    binding.apply {
                        profileUsername.setText(it.username)
                        profileBio.setText(it.bio)
                    }
                }
            }
        }

        lifecycleScope.launch {
            profileVM.isLoading.collect { isLoading ->
                binding.profileSaveBtn.isEnabled = !isLoading
            }
        }

        profileVM.error_message.observe(this) { error_message ->
            if (error_message != null) {
                profile_error.text = error_message
                profile_error.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    profile_error.visibility = View.GONE
                }, 2000)
            } else {
                profile_error.visibility = View.GONE
            }
        }
    }

    private fun setupSaveButton() {
        binding.profileSaveBtn.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = currentFocus
            view?.let {
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }

            val username = binding.profileUsername.text.toString()
            val bio = binding.profileBio.text.toString()

            try {
                selectedImageUri?.let { uri ->
                    lifecycleScope.launch {
                        val imageUrl = profileVM.uploadProfilePicture(uri)
                        profileVM.updateProfile(
                            username = username,
                            bio = bio.takeIf { it.isNotBlank() },
                            profilePictureUri = imageUrl
                        )
                    }
                } ?: profileVM.updateProfile(
                    username = username,
                    bio = bio.takeIf { it.isNotBlank() },
                )
            } catch (e: Exception){
                Log.e("UserProfileActivity.setupSaveButton()", "Error: ${e.message}")
            } finally {
                profileVM.success_message.observe(this) { success_message ->
                    if (success_message != null) {
                        profile_message.text = success_message
                        profile_message.visibility = View.VISIBLE

                        Handler(Looper.getMainLooper()).postDelayed({
                            profile_message.visibility = View.GONE
                        }, 2000)
                    } else {
                        profile_message.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupImagePicker() {
        binding.profileCamBtn.setOnClickListener {
            showImagePickerDialog()
        }
    }

    private fun observeProfilePicture() {
        lifecycleScope.launch {
            profileVM.currentUser.collect { user ->
                user?.let {
                    it.profilePictureUri?.let { uri ->
                        Glide.with(this@UserProfileActivity)
                            .load(uri)
                            .placeholder(R.drawable.ic_emoji)
                            .error(R.drawable.ic_emoji)
                            .into(binding.profilePicture)
                    }
                }
            }
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        AlertDialog.Builder(this)
            .setTitle("Select Profile Picture")
            .setItems(options) { _, index ->
                when (index) {
                    0 -> checkPermissionAndOpenCamera()
                    1 -> checkPermissionAndOpenGallery()
                }
            }
            .show()
    }

    private fun checkPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, cameraPermission)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(cameraPermission),
                CAMERA_PERMISSION_CODE
            )
        } else {
            openCamera()
        }
    }

    private fun checkPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(this, galleryPermission)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(galleryPermission),
                GALLERY_PERMISSION_CODE
            )
        } else {
            openGallery()
        }
    }

    private fun openCamera() {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "Profile Picture")
            put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
        }

        try {
            selectedImageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri)
            }
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } catch (e: Exception) {
            Log.e("UserProfileActivity", "Error opening camera: ${e.message}")
            Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                }
            }
            GALLERY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    selectedImageUri?.let { uri ->
                        binding.profilePicture.setImageURI(uri)
                    }
                }
                GALLERY_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        selectedImageUri = uri
                        binding.profilePicture.setImageURI(uri)
                    }
                }
            }
        }
    }

    private fun setupBackNavigation() {
        binding.profileBar.setNavigationOnClickListener {
            this.finish()
        }
    }
}