package com.example.myapplication

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var _db:DatabaseReference;
    private  lateinit var _adapter: TaskAdapter
    private var _taskList:MutableList<Task>?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
          showFooter()
        }
        _db=FirebaseDatabase.getInstance().reference
        binding.btnAdd.setOnClickListener{
            addTask()
        }
        _taskList= mutableListOf()
        _adapter=TaskAdapter(this,_taskList!!)
        binding.listviewTask!!.adapter=_adapter

        var _taskListener:ValueEventListener=object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                loadTaskList(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("MainActivity","loadItem",error.toException())
            }

        }
        _db.orderByKey().addValueEventListener(_taskListener)
    }
    fun showFooter()
    {
        binding.footer.visibility= View.VISIBLE;
        binding.fab.visibility=View.GONE;
    }
    fun addTask()
    {
        val task=Task.create()
        task.taskDesc=binding.txtNewTaskDesc.text.toString()
        task.done=false
        val newTask=_db.child(Statics.FIREBASE_TASK).push()
        task.objectId=newTask.key
        newTask.setValue(task)
        binding.footer.visibility=View.GONE
        binding.fab.visibility=View.VISIBLE
        binding.txtNewTaskDesc.setText("")
        Toast.makeText(this,"Task added",Toast.LENGTH_LONG).show()
    }

    private fun loadTaskList(dataSnapShot:DataSnapshot)
    {
        val  tasks=dataSnapShot.children.iterator()
        if(tasks.hasNext())
        {
            _taskList!!.clear()
            val listIndex=tasks.next()
            var itemsIterator=listIndex.children.iterator()
            while(itemsIterator.hasNext())
            {
                val currentItem=itemsIterator.next()
                val task=Task.create()
                val map=currentItem.getValue() as HashMap<String,Any>
                task.objectId=currentItem.key
                task.done=map.get("done") as Boolean
                task.taskDesc=map.get("taskDesc") as String
                _taskList!!.add(task)
            }
        }
        _adapter.notifyDataSetChanged()
    }

}