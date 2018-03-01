package com.cyf.imageselectordemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        button.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this,Main2Activity::class.java))
        }
    }
}
