package com.example.new_assignment

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_rows.view.*

class RecyclerAdapter internal constructor(context: Context,alldata:List<Story>): RecyclerView.Adapter<ViewHolder>(){



    private var data: List<Story> = alldata
    val inflater = LayoutInflater.from(context)

    init {
        notifyDataSetChanged()

    }
     internal fun SetData(story: List<Story>){
         data = data.plus(story)
         notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = inflater.inflate(R.layout.recycler_rows, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.emotiontext.text= "Feeling ${data[position].Emo}"
        holder.description.text=data[position].Desc
        holder.name_text.text =data[position].Name
        val uri_string =data[position].Uri
        val urifinal =Uri.parse(uri_string)
       holder.uri.setImageURI(urifinal)
    }

    override fun getItemCount(): Int {

        return data.size
    }
}

class ViewHolder(v:View) :RecyclerView.ViewHolder(v){

   // accessing layout elemnts inside viewholder
    val emotiontext=v.findViewById<TextView>(R.id.emotion_text_view)
      val name_text =v.findViewById<TextView>(R.id.name_text)
      val description =v.findViewById<TextView>(R.id.textView10)
      val uri =v.findViewById<ImageView>(R.id.imageView2)

}