package com.titanium.kaanb.volleytry

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CustomAdaptor(val myCardList: ArrayList<CardView>) : RecyclerView.Adapter<CustomAdaptor.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
    val v = LayoutInflater.from(p0?.context).inflate(R.layout.recycler_view,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
    return myCardList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val cardView: CardView = myCardList[p1]
        p0?.textView7.text = cardView.saat
        p0?.textView8.text = cardView.derece

        p0?.image7.setBackgroundResource(cardView.resim)
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textView7 = itemView.findViewById(R.id.textView7) as TextView
        val textView8 = itemView.findViewById(R.id.textView8) as TextView
        val image7 = itemView.findViewById(R.id.imageView2) as ImageView

    }
}