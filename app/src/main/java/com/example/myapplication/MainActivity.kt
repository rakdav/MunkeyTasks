package com.example.myapplication

import android.os.Bundle
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var _db:DatabaseReference;

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
}