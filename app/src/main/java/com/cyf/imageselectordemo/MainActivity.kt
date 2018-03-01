package com.cyf.imageselectordemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.cyf.cyfimageselector.model.PhotoConfigure
import com.cyf.cyfimageselector.utils.DensityUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var list: ArrayList<String> = arrayListOf("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=900872169,3830952677&fm=58&u_exp_0=333838554,3497647267&fm_exp_0=86&bpow=1000&bpoh=1503",
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
        initRcv()
    }

    private fun initRcv() {
        val config = PhotoConfigure()
        // 编辑
        config.type = PhotoConfigure.EditImg
        config.colnum = 4
        config.isDelete = true
        config.isCanDrag = false
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
    }

}
