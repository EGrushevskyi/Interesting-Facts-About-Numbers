package com.grushevskyi.interestingfactsaboutnumbers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAdapter (private var mData: ArrayList<String>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mData.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.myFactView.text = mData[position]
        //holder.myNumberView.text = listOfMyStocks[position]

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var myFactView: TextView = itemView.findViewById(R.id.idFact)
        var myNumberView: TextView = itemView.findViewById(R.id.idNumber)

    }
}