package com.example.new_assignment
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.DownloadManager
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.new_assignment.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.security.Permission
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class Fragment_two : Fragment(){

    val Names = arrayListOf<String>("Jerin", "Tom", "Ben", "Jerry", "Raj", "Shyam", "Banu", "Bheem", "Chutki", "Raju")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    var currentPhotoPath: String = ""
    var imgUri : String = ""
    var emotionString: String = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (  requestCode == 100){
            Log.i("info",data?.data.toString())
            val imgBtn = view?.findViewById<ImageButton>(R.id.imgButton)
            imgBtn!!.setImageURI(data?.data)
            imgUri = data?.data.toString()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_two, container, false)
        val Ctx = context
        var Desc = view.findViewById<EditText>(R.id.editTextTextMultiLine2).text

        val appCtx = activity!!.applicationContext
        val submitBtn = view.findViewById<Button>(R.id.submit_button)

        val db = Room.databaseBuilder(appCtx, StoryDataBase::class.java, "Story")
            .allowMainThreadQueries()
            .build()

        @Throws(IOException::class)
        fun createImageFile(): File {
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? =  activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = absolutePath
            }
        }

        val imgBtn = view?.findViewById<ImageButton>(R.id.imgButton)

        imgBtn?.setOnClickListener {
            var arr = listOf<String>(android.Manifest.permission.CAMERA)

            requestPermissions(arrayOf(android.Manifest.permission.CAMERA),101)

            Log.i("info", "Button clicked")
            val builder = AlertDialog.Builder(activity!!)

            builder.setMessage("Where would you like to import image from?")

                .setCancelable(false)
                .setPositiveButton("Gallery", DialogInterface.OnClickListener{dialog,id ->
    //                 Log.i("info","clicked");
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, 100)

                })
                .setNeutralButton("Camera", DialogInterface.OnClickListener{ dialog, id ->
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        takePictureIntent.resolveActivity(Ctx!!.packageManager)?.also {

                            val photoFile: File? = try {
                                createImageFile()
                            } catch (ex: IOException) {
                                // Error occurred while creating the File
                                null
                            }
                            // Continue only if the File was successfully created
                            photoFile?.also {
                                val photoURI: Uri = FileProvider.getUriForFile(
                                    Ctx,
                                    "com.example.android.fileprovider",
                                    it
                                )

                                Log.i("info", "is"+photoURI.toString())
                                val imgBtn = view?.findViewById<ImageButton>(R.id.imgButton)

                                imgUri = photoURI.toString()

                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                startActivityForResult(takePictureIntent, 1)
                                imgBtn!!.setImageURI(photoURI)
                            }
                            dialog.dismiss()
                        }
                    }
                })
                .setCancelable(true)

            val alert = builder.create()
            alert.setTitle("Insert Image")
            alert.show()
        }
        // spinner
        val spinner: Spinner = view?.findViewById(R.id.emotion_spinner)
        ArrayAdapter.createFromResource(
            activity!!,
            R.array.emotion_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                      Log.i("info",parent.getItemAtPosition(position).toString())

                emotionString = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.i("info","nothiing selected")
            }
        }

        submitBtn?.setOnClickListener {
            val name = Names.shuffled().take(1)[0]
            val description = Desc.toString()
            val imgLink = imgUri
            val emotionText = emotionString
            if(name.isEmpty() || description.isEmpty() || imgLink.isEmpty() || emotionText.isEmpty()){
                Toast.makeText(appCtx, "One of your fields is empty! Fill all of them for uploading", Toast.LENGTH_LONG).show()
            }
            else {
                var insertData = Story(null, description, name, "", imgLink, emotionText)
                db.storyDao().insertStory(insertData)

                fragmentManager?.popBackStack()
            }

        }
//        Random gen

//        Write into database
//        val insertData = Story()

        return view
    }

}
