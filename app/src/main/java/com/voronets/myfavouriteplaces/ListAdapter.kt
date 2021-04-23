package com.voronets.myfavouriteplaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voronets.myfavouriteplaces.data.Point


class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
    private var pointList = emptyList<Point>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from((parent.context)).inflate(R.layout.point_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return pointList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currentItem = pointList[position]
        holder.itemView.findViewById<TextView>(R.id.tv_name).text = currentItem.name
    }

    fun setData(points: List<Point>){
        this.pointList = points
        notifyDataSetChanged()
    }

}