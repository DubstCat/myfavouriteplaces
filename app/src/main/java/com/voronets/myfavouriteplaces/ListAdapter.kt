package com.voronets.myfavouriteplaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voronets.myfavouriteplaces.data.Point

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.text_view_item)
    }
    private var pointList = emptyList<Point>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from((parent.context)).inflate(R.layout.item_layout, parent, false))
    }


    override fun getItemCount(): Int {
        return pointList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = pointList[position]
        holder.textView.text = currentItem.name
        }


    fun setData(points: List<Point>){
        this.pointList = points
        notifyDataSetChanged()
    }



}