package com.sunnyweather.android.ui.place
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment // 使用AndroidX Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R

class PlaceAdapter (private val fragment: Fragment, private val placeList: ArrayList<com.google.android.libraries.places.api.model.Place>):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){
    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val place = placeList[position]
        holder.placeName.text =place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount() = placeList.size

}