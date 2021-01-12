package com.example.jetvideo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jetvideo.databinding.ItemLayoutTestadapterBinding

class MyTestAdapter(private val context: Context, data: List<String>) : CommonAdapter<MyTestAdapter.MyVH, String>(data) {

    override fun generateViewHolder(viewType: Int) = MyVH(ItemLayoutTestadapterBinding.inflate(LayoutInflater.from(context)))

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.binding.tv.text = data[position]
        holder.binding.tv.setOnClickListener(this::click)
    }

    private fun click(v: View) {
        Toast.makeText(v.context, " --- ", Toast.LENGTH_SHORT).show()
    }

    class MyVH(val binding: ItemLayoutTestadapterBinding) : RecyclerView.ViewHolder(binding.root)

}

