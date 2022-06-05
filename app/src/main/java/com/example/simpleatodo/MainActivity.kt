package com.example.simpleatodo

import android.hardware.biometrics.BiometricManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listoftask = mutableListOf<String>()
    lateinit  var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongCLickListner = object : TaskItemAdapter.onLongCLickLIstner{
            override fun onItemLongCLicked(position: Int) {
                //1. Remove item from list
                listoftask.removeAt(position)
                //2. Notify the Adapter that our data changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }


//        // 1. Detect when the user presses the Add button
//            findViewById<Button>(R.id.button).setOnClickListener {
//                // Code in here will execute when user clicks on button
//                Log.i("Ryan""user clicked button")
//    }

        loadItems()


        //look up recyclerview in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listoftask, onLongCLickListner)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the button and input, so the user can add items to the list

        val InputTextField =  findViewById<EditText>(R.id.AddTaskField)

        //Get a reference to button
        //then set onclick listener
        findViewById<Button>(R.id.button).setOnClickListener {
         //1. Grab text the user inputted :id@/addTaskField
        val userInputtedTask = InputTextField.text.toString()
        //2.  Add the string to list of tasks: listofTasks
    listoftask.add(userInputtedTask)

        //notify item inserted
        adapter.notifyItemInserted(listoftask.size -1 )

         //3.  Reset text field
            InputTextField.setText("")

            saveItems()
        }
    }
     // Save Data that the user inputted
    // save data by writing and reading from a file

    // Get the file we need
    fun getDataFile() : File {

        // Every line is going to represent a specific task in our list of task
        return File(filesDir, "data.txt")
    }


    //Load the items by reading every line in the data file
    fun loadItems() {
        try{
        listoftask = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        }
    }

    //Save items by writing them into our data file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listoftask)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

}
