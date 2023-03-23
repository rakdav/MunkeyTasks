package com.example.myapplication

interface TaskRowListener {
    fun onTaskChanger(objectId:String,isDone:Boolean)
    fun OnTaskDelete(objectId:String)
}