package com.cyf.imageselectordemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.cyf.cyfimageselector.model.PhotoConfigure
import com.cyf.cyfimageselector.recycler.PostArticleImgAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var postArticleImgAdapter: PostArticleImgAdapter? = null
    private var itemTouchHelper: ItemTouchHelper? = null
    private var list2: ArrayList<String> = arrayListOf("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=900872169,3830952677&fm=58&u_exp_0=333838554,3497647267&fm_exp_0=86&bpow=1000&bpoh=1503",
            "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3587085728,2856235770&fm=58&s=0112C432D6D3F99248CC4BC40300A0A6&bpow=121&bpoh=75",
            "https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=1145580389,1901929664&fm=85&s=6CCA7A234ED323F71DA99486010080A1",
            "https://ss1.baidu.com/70cFfyinKgQFm2e88IuM_a/forum/pic/item/aa64034f78f0f736a4a890f80955b319eac413f0.jpg",
            "https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=912466544,1453991696&fm=85&s=991123D7465109C6668DF11C03001093",
            "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3587085728,2856235770&fm=58&s=0112C432D6D3F99248CC4BC40300A0A6&bpow=121&bpoh=75",
            "https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=1145580389,1901929664&fm=85&s=6CCA7A234ED323F71DA99486010080A1",
            "https://ss1.baidu.com/70cFfyinKgQFm2e88IuM_a/forum/pic/item/aa64034f78f0f736a4a890f80955b319eac413f0.jpg",
            "https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=912466544,1453991696&fm=85&s=991123D7465109C6668DF11C03001093")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initGri()
        initRcv()
    }

    private fun initGri() {
//        button.setOnClickListener {
//            Toast.makeText(this@MainActivity, "选中图片数量 ： " + grapeGridview2.getSelectList().size, Toast.LENGTH_SHORT).show()
//            Log.e("cyf7", "list!!.toString() : " + grapeGridview2.selectList.toString())
//        }
//        grapeGridview1.setOnMyItemClickListener(this, list2, 0)// 查看图片
//        grapeGridview1.isCanDrag = true
//        val c = PhotoConfigure()
//        c.isSingle = false
//        grapeGridview2.setOnMyItemClickListener2(this, ArrayList<String>(), true, c)// 选择图片
//        grapeGridview2.isCanDrag = true
    }

    private fun initRcv() {
//        recyclerView1.setCanDrag(true)
//        recyclerView1.setOnMyItemClickListener(this, list2, 0)// 查看图片
        val c = PhotoConfigure()
        c.isSingle = false
        recyclerView.setColnum(4)
        recyclerView.setTv_delete(textView)
        recyclerView.setCanDrag(true) {
            recyclerView.post({
                val width = recyclerView.measuredWidth
                val h = width / 4 * it
                Log.e("cyf789", " $h    $width   $it")
                rl_view.layoutParams = LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, h)
            })
        }
        recyclerView.setOnMyItemClickListener2(this, ArrayList<String>(), false, c)// 选择图片
    }

}
