package com.grushevskyi.interestingfactsaboutnumbers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.grushevskyi.interestingfactsaboutnumbers.db.Fact


class RVAdapter : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    var factList = mutableListOf<Fact>()

    fun setFacts(facts: List<Fact>) {
        this.factList = facts.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = factList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myNumberView.text = factList[position].factText

        holder.itemView.setOnClickListener { v ->
            val myIntent = Intent(v.context, SecondaryActivity::class.java)
            myIntent.putExtra("number", factList[position].factText.split(" ").get(0))
            myIntent.putExtra("fact", factList[position].factText)
            v.context.startActivity(myIntent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myNumberView: TextView = itemView.findViewById(R.id.idNumber)
    }
}