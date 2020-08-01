package com.example.new_assignment

import android.util.Log
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

//
//private const val Url ="https://fakerestapi.azurewebsites.net/api/Books"
//
// val retrofit=Retrofit.Builder()
//     .addConverterFactory(ScalarsConverterFactory.create())
//     .baseUrl(Url)
//     .build()

object  ServiceBuilder{
    private val client = OkHttpClient.Builder().build()

    private val retrofit= Retrofit.Builder()
        .baseUrl("https://fakerestapi.azurewebsites.net/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service:Class<T>):T{
        return retrofit.create(service)
    }
}
interface PostService {
    @Headers("Content-Type:application/json")
      @POST("Books")
      fun addUser(@Body userData:UserInfo): Call<UserInfo>




}
class PostApiService{
    fun addUser(userData: UserInfo,onResult:(UserInfo?)->Unit){
        val retrofit= ServiceBuilder.buildService(PostService::class.java)
        var status:String?
        retrofit.addUser(userData).enqueue(
            object: Callback<UserInfo>{
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
//                    status="fail"
//                    onResult(null,status)
                    Log.i("response",t.message.toString())
                }

                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    val add =response.body()?:null
                    status="sucess"
                    Log.i("reponse",response.code().toString())
                    onResult(add)
                }

            }
        )
    }
}
data class UserInfo (
    @SerializedName("ID") val userId: Int?,
    @SerializedName("Title") val title: String?,
    @SerializedName("Description") val desc :String?,
    @SerializedName("PublishDate") val publishdate: String?
)