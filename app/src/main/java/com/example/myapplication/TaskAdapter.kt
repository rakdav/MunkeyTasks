package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView

class TaskAdapter(context:Context,taskList:MutableList<Task>):BaseAdapter() {
    private val _inflater:LayoutInflater=LayoutInflater.from(context)
    private val _taskList=taskList
    override fun getCount(): Int {
        return _taskList.size
    }

    override fun getItem(position: Int): Any {
        return _taskList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val objectId:String=_taskList.get(position).objectId as String
        val itemText:String=_taskList.get(position).taskDesc as String
        val done:Boolean=_taskList.get(position).done as Boolean
        val view:View
        val listRowHandler:ListRowHandler
        if(convertView==null)
        {
            view=_inflater.inflate(R.layout.task_row,parent,false)
            listRowHandler= ListRowHandler(view)
            view.tag=listRowHandler
        }
        else
        {
            view=convertView
            listRowHandler=view.tag as ListRowHandler
        }
        listRowHandler.desc.text=itemText
        listRowHandler.done.isChecked=done
        return view
    }
    private class ListRowHandler(row:View?)
    {
        val desc:TextView=row!!.findViewById(R.id.txtNewTaskDesc)
        val done:CheckBox=row!!.findViewById(R.id.chkDone)
        val remove:ImageButton=row!!.findViewById(R.id.btnRemove)
    }
}