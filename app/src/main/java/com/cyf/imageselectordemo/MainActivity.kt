package com.cyf.imageselectordemo

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.cyf.cyfimageselector.model.PhotoConfigure
import com.cyf.cyfimageselector.recycler.CyfRecyclerView
import com.cyf.cyfimageselector.utils.DensityUtil
import com.cyf.imageselectordemo.R.id.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var dialog1: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialog1 = ProgressDialog(this)
        initRcv()
    }

    private fun initRcv() {
        val config = PhotoConfigure().setType(PhotoConfigure.EditImg).setColnum(4).setDelete(false).setCanDrag(true).setAutoDelThm(true).setOriginalShow(true)
        recyclerView.setTv_delete(textView).setOnUpdateData {
            recyclerView.post {
                val h = DensityUtil.dip2px(this@MainActivity, 100f) +
                        ((recyclerView.width - DensityUtil.dip2px(this@MainActivity, 96f)) / 4 * it)
                if (rl_view.height != h) {
                    rl_view.layoutParams = LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, h)
                }
            }
        }.setOnCyfThumbnailsListener(object : CyfRecyclerView.OnCyfThumbnailsListener {

            override fun onStart() {
                // 开启转圈等待效果
                dialog1?.show()
            }

            override fun onError(e: Throwable?) {
                dialog1?.cancel()
                Toast.makeText(this@MainActivity, "获取图片异常：" + e.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onEnd(list: MutableList<String>?, thumbnailsList: MutableList<String>?, isOriginalDrawing: Boolean) {
                // 这里启动上传图片的线程，并结束转圈等待效果
                dialog1?.cancel()

                val builder2 = AlertDialog.Builder(
                        this@MainActivity)
                builder2.setTitle("提示")
                builder2.setCancelable(true)
                val dialog2 = builder2.create()
                if (isOriginalDrawing) {
                    dialog2.setMessage("原图：\n" + list.toString())
                } else {
                    dialog2.setMessage("缩略图：\n" + thumbnailsList.toString())
                    if (thumbnailsList!!.isNotEmpty()) {
                        getPixColor(thumbnailsList[0])
                    }
                }
                dialog2.setButton(AlertDialog.BUTTON_POSITIVE, "我知道了") { p0, p1 ->
                    // 上传完成之后按需求调用清除缩略图的方法（config.isAutoDelThm = true可以不调用）
                    // recyclerView.clearThumbnailsList()
                }
                dialog2.show()
            }
        }).show(config)

        button.setOnClickListener {
            // 获取压缩图片
            recyclerView.getSelectThumbnailsList()
        }
    }

    /**
     * 测试获取像素点色值
     */
    fun getPixColor(path: String) {
        val src = BitmapFactory.decodeFile(path)
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixelColor: Int
        // val height = src.height
        val width = src.width
        val y = 0
        for (x in 0 until width) {
            if (x == 0 || x == src.width - 1) {
                pixelColor = src.getPixel(x, y)
                A = Color.alpha(pixelColor)
                R = Color.red(pixelColor)
                G = Color.green(pixelColor)
                B = Color.blue(pixelColor)

                Log.e("A:", A.toString() + "")
                Log.e("R:", R.toString() + "")
                Log.e("G:", G.toString() + "")
                Log.e("B:", B.toString() + "")
            }
        }
    }

}
