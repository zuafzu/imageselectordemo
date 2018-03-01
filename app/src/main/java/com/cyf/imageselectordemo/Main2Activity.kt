package com.cyf.imageselectordemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.ArrayList

class Main2Activity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main2)
    }
}
