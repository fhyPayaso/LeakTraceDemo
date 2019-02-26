package com.bytedance.fanhongyu.leaktrace.main

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bytedance.fanhongyu.leaktrace.R

/**
 *
 * @author fhyPayaso
 * @since 2019/2/26 3:14 PM
 */
class MainAdapter(val items: List<MenuItem>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_main_list, p0, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(items[p1])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: MenuItem) {
            view.findViewById<TextView>(R.id.txt_module_name).text = item.description
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, item.activity)
                itemView.context.startActivity(intent)
            }
        }
    }
}