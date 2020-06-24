package com.titanium.kaanb.volleytry

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CustomAdaptor_future(val myCardList: ArrayList<CardView_future>) : RecyclerView.Adapter<CustomAdaptor_future.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.recycler_view_future,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return myCardList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val cardView: CardView_future = myCardList[p1]
        p0?.textView9.text = cardView.tarih
        p0?.textView10.text = cardView.derece
        p0?.textView11.text = cardView.durum
        p0?.imageView6.setBackgroundResource(cardView.resim)


    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textView9 = itemView.findViewById(R.id.textView9) as TextView
        val textView10 = itemView.findViewById(R.id.textView10) as TextView
        val textView11 = itemView.findViewById(R.id.textView11) as TextView
        val imageView6 = itemView.findViewById(R.id.imageView6) as ImageView


    }
}