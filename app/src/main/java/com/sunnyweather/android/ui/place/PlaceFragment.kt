package com.sunnyweather.android.ui.place
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sunnyweather.android.R


@Suppress("DEPRECATION")
class PlaceFragment:Fragment() {
    val viewModel by lazy {
        // 修正：ViewModelProvider首字母大写
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }
    private lateinit var adapter: PlaceAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var bgImageView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 添加视图初始化
        recyclerView = view?.findViewById(R.id.recyclerView)!!
        bgImageView = view?.findViewById(R.id.bgImageView)!!

        val layoutManager = LinearLayoutManager(activity)
        // 统一变量名大小写
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this,viewModel.placeList)
        // 统一变量名大小写
        recyclerView.adapter = adapter

        val searchPlaceEdit = view?.findViewById<EditText>(R.id.searchPlaceEdit)
        searchPlaceEdit?.addTextChangedListener(object : TextWatcher {
            // 正确实现TextWatcher接口
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(editable: Editable?) {
                val content = editable.toString()
                if(content.isNotEmpty()){
                    viewModel.searchPlaces(content)
                }else{
                    // 修正拼写错误
                    recyclerView.visibility = View.GONE
                    bgImageView.visibility = View.VISIBLE
                    viewModel.placeList.clear()
                    adapter.notifyDataSetChanged()
                }
            }
        })

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            // 修正lambda参数接收问题
            val places = result.getOrNull()
            if(places != null){
                // 修正拼写错误
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.run {
                    placeList.clear()
                    // 将自定义Place对象转换为Google Place对象
                    val googlePlaces = places.map { customPlace ->
                        // 创建Google Place对象并设置属性
                        com.google.android.libraries.places.api.model.Place.builder()
                            .setName(customPlace.name) // 假设你的自定义Place有name属性
                            .setAddress(customPlace.address) // 假设你的自定义Place有address属性
                            // 设置其他需要的属性...
                            .build()
                    }
                    placeList.addAll(googlePlaces)
                }
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "未能查询任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}


