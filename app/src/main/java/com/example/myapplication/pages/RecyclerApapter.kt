package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter (private var titles: MutableList<String>, private var details: MutableList<String>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val itemTitle: TextView = itemView.findViewById(R.id.tvTitle)
            val itemDetail: TextView = itemView.findViewById(R.id.tv_description )

            /*init {
                itemView.setOnClickListener {v : View ->
                    val position: Int = adapterPosition
                    Toast.makeText(itemView.context, "You clicked on item # ${position + 1} ", Toast.LENGTH_SHORT).show()

                }*/
            fun bind(property: String, index: Int) {
                val itemTitle: TextView = itemView.findViewById(R.id.tvTitle)
                val itemDetail: TextView = itemView.findViewById(R.id.tv_description )
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_cardlayout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
    }
}