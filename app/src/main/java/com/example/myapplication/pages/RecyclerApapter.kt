package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter class for the RecyclerView in the application.
 *
 * @param titles List of titles to be displayed in the RecyclerView.
 * @param details List of details corresponding to each title.
 * @param images List of image resources corresponding to each title.
 */
class RecyclerAdapter (private var titles: MutableList<String>, private var details: MutableList<String>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    /**
     * Inner class representing a ViewHolder for the RecyclerView.
     *
     * @param itemView The view for each item in the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        // Views within the item layout
        val itemTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)


            fun bind(property: String, index: Int) {
                val itemTitle: TextView = itemView.findViewById(R.id.tvTitle)
                val itemDetail: TextView = itemView.findViewById(R.id.tv_description )
            }
        }

    /**
     * Overrides onCreateViewHolder to create ViewHolders for the RecyclerView.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout for the RecyclerView
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_cardlayout, parent, false)
        return ViewHolder(v)
    }

    /**
     * Overrides getItemCount to return the total number of items in the data set.
     *
     * @return The total number of items in the RecyclerView.
     */
    override fun getItemCount(): Int {
        return titles.size
    }

    /**
     * Overrides onBindViewHolder to bind data to the ViewHolder at the specified position.
     *
     * @param holder The ViewHolder to bind the data to.
     * @param position The position of the item within the data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the views in the ViewHolder
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
    }
}
