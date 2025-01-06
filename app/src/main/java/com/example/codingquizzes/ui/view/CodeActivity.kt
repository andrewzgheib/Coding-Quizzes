package com.example.codingquizzes.ui.view

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.codingquizzes.databinding.ActivityCodeBinding
import com.example.codingquizzes.ui.vm.CodeViewModel

class CodeActivity : AppCompatActivity() {
    private lateinit var viewModel: CodeViewModel
    private lateinit var binding: ActivityCodeBinding

    private val languages = mapOf(
        "Python" to 71,
        "Java" to 62,
        "C++" to 54,
        "JavaScript" to 63,
        "Kotlin" to 78
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CodeViewModel::class.java)

        setupSpinner()
        setupObservers()
        setupRunButton()
    }

    private fun setupSpinner() {
        ArrayAdapter(
            this,
            R.layout.simple_spinner_item,
            languages.keys.toList()
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.codeLanguages.adapter = adapter
        }
    }

    private fun setupObservers() {
        viewModel.executionResult.observe(this) { result ->
            binding.codeOutput.text = result
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.codeBtn.isEnabled = !isLoading
        }
    }

    private fun setupRunButton() {
        binding.codeBtn.setOnClickListener {
            val code = binding.codeInput.text.toString()
            val input = binding.codeInput.text.toString()
            val selectedLanguage = binding.codeLanguages.selectedItem as String
            val languageId = languages[selectedLanguage] ?: return@setOnClickListener

            viewModel.executeCode(code, languageId, input)
        }
    }
}