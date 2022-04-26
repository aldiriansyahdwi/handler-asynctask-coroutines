package com.example.testhandler

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import com.example.testhandler.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Connection
import kotlin.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handler
        // buat runnable yang nantinya akan dijalankan oleh handler dengan ketentuan tertentu
        var c = Runnable {
            binding.tvFirst.text = "I am Running now"
        }

        var mHandler = Handler()
        // handler yang membuat delay selama 5000 ms
        mHandler.postDelayed(c, 5000)

        // memanggil class AsyncTask yang sudah dibuat
        val testAsyncTask = mAsyncTask()
        // melakukan eksekusi secara berurutan dari pre, background dan post
        testAsyncTask.execute()
        // mengambil return dari doInBackground
        Log.d("status-background", "Background result : ${testAsyncTask.get()}")

        GlobalScope.launch {
            delay(3000)
            Log.d("coroutines-status", Thread.currentThread().name)
        }
        Log.d("coroutines-status", Thread.currentThread().name)

    }

    internal inner class mAsyncTask: AsyncTask<Void, Void, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("status-background", "start pre")
            binding.tvSecond.text = "this is pre execute"
        }

        override fun doInBackground(vararg p0: Void?): String {
            Log.d("status-background", "do in background")
            Thread.sleep(5000)
            return "background task success"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d("status-background", "post")
            binding.tvSecond.text = "this is post execute"
        }

    }

}