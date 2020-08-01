package com.example.new_assignment

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.new_assignment.databinding.FragmentoneBinding
import kotlinx.android.synthetic.main.fragmentone.*
import kotlinx.android.synthetic.main.fragmentone.view.*

class Fragmentone : Fragment(){
private lateinit var viewmodel:FragmentOneViewModel
    private lateinit var binding: FragmentoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


      val Ctx =activity!!.applicationContext
      binding = DataBindingUtil.inflate(inflater, R.layout.fragmentone, container, false)

      ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 2)


      binding.setLifecycleOwner(this)
        viewmodel=ViewModelProviders.of(this).get(FragmentOneViewModel::class.java)
        val swipetorefresh=binding.root.findViewById<SwipeRefreshLayout>(R.id.refresh)
        swipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(Ctx,R.color.colorPrimary))
        swipetorefresh.setColorSchemeColors(Color.WHITE)
        swipetorefresh.setOnRefreshListener {
            val recyclerView_r = binding.root.findViewById<RecyclerView>(R.id.main_recycler_view)
            val data =viewmodel.getAllStories()
            val adapter_r = RecyclerAdapter(Ctx,data)
            recyclerView_r.adapter = adapter_r
            recyclerView_r.layoutManager = LinearLayoutManager(Ctx)
            swipetorefresh.isRefreshing=false
        }
        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.main_recycler_view)
        val data =viewmodel.getAllStories() as List<Story>
        val adapter = RecyclerAdapter(Ctx,data)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(Ctx)
         return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val Ctx =activity!!.applicationContext

        viewmodel=ViewModelProviders.of(this).get(FragmentOneViewModel::class.java)

//        viewmodel.getAllStories().observe(this, Observer {story ->
//                story?.let {
//                    Log.i("checking",it.size.toString())
////                    RecyclerAdapter(Ctx).SetData(it)
////                    RecyclerAdapter(Ctx).notifyDataSetChanged()
//
//                }
//        })

    }


}