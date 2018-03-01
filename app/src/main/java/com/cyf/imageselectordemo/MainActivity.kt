package com.cyf.imageselectordemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.cyf.cyfimageselector.model.PhotoConfigure
import com.cyf.cyfimageselector.recycler.CyfRecyclerView
import com.cyf.cyfimageselector.utils.DensityUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRcv()
    }

    private fun initRcv() {
        val config = PhotoConfigure()
        // 编辑
        config.type = PhotoConfigure.EditImg
        config.colnum = 4
        config.isDelete = true
        config.isCanDrag = false
        config.isAutoDelThm = true
        recyclerView.setTv_delete(textView).setOnUpdateData {
            recyclerView.post({
                val h = DensityUtil.dip2px(this@MainActivity, 100f) +
                        ((recyclerView.width - DensityUtil.dip2px(this@MainActivity, 96f)) / 4 * it)
                if (rl_view.height != h) {
                    rl_view.layoutParams = LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, h)
                }
            })
        }.show(config)
        // 查看
//        config.type = PhotoConfigure.WatchImg
//        config.list = list
//        config.colnum = 3
//        config.isClick = true
//        config.isSave = false
//        recyclerView.setListener {
//            Toast.makeText(this@MainActivity, list[it], Toast.LENGTH_SHORT).show()
//        }.show(config)
        // 压缩图片
        recyclerView.getSelectThumbnailsList(object : CyfRecyclerView.OnCyfThumbnailsListener {
            override fun onStart() {
                // 开启转圈等待效果
            }

            override fun onEnd(list: MutableList<String>?, thumbnailsList: MutableList<String>?) {
                // 这里启动上传图片的线程，上传完成之后调用清除缩略图的方法，并结束转圈等待效果
                // recyclerView.clearThumbnailsList()
            }
        })

    }

}
