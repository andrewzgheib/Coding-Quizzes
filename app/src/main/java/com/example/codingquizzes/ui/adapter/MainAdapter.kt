package com.example.codingquizzes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R

class MainAdapter(
    private var buttonLabels: List<String>,
    private val itemClickListener: ButtonClickListener
) : RecyclerView.Adapter<MainAdapter.ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_actvity_rv_row_layout, parent, false)
        return ButtonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return buttonLabels.size
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val label = buttonLabels[position]
        holder.button.text = label

        holder.button.setOnClickListener {
            itemClickListener.onButtonClick(position)
        }
    }

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.main_btn)
    }

    interface ButtonClickListener {
        fun onButtonClick(position: Int)
    }
}