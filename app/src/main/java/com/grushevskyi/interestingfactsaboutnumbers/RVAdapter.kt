package com.grushevskyi.interestingfactsaboutnumbers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.grushevskyi.interestingfactsaboutnumbers.db.Fact


class RVAdapter (private var mData: ArrayList<String>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    var userList = mutableListOf<Fact>()

    var clickListener: ListClickListener<Fact>? = null

    fun setFacts(facts: List<Fact>) {
        this.userList = facts.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mData.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.myNumberView.text = mData[position]
        //holder.myNumberView.text = listOfMyStocks[position]

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var mCtx: Context

        fun quotesAdapter(mCtx: Context) {
            this.mCtx = mCtx
        }

        var myNumberView: TextView = itemView.findViewById(R.id.idNumber)
        //var myFactView: TextView = itemView.findViewById(R.id.idFact)


        override fun onClick(v: View?) {
            val myIntent = Intent(mCtx, SecondaryActivity::class.java)
            myIntent.putExtra("quote", quote)
            startActivity(myIntent)
        }

    }

    interface ListClickListener<T> {
        fun onClick(data: T, position: Int)
    }
}