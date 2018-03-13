package com.cyf.imageselectordemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.cyf.cyfimageselector.model.PhotoConfigure
import com.cyf.cyfimageselector.recycler.CyfRecyclerView
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*


class Main2Activity : AppCompatActivity() {

    private var list1: ArrayList<String> = arrayListOf(
            "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=900872169,3830952677&fm=58&u_exp_0=333838554,3497647267&fm_exp_0=86&bpow=1000&bpoh=1503",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=96212569,1396500668&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1378813371,2481804374&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3185590630,690458775&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=414620367,1705416437&fm=27&gp=0.jpg")

    private var list2: ArrayList<String> = arrayListOf(
            "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3587085728,2856235770&fm=58&s=0112C432D6D3F99248CC4BC40300A0A6&bpow=121&bpoh=75",
            "https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=1145580389,1901929664&fm=85&s=6CCA7A234ED323F71DA99486010080A1",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3079751641,4091754669&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3667916039,4286076556&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3848053080,1301187151&fm=27&gp=0.jpg")

    private var list3: ArrayList<String> = arrayListOf(
            "https://ss1.baidu.com/70cFfyinKgQFm2e88IuM_a/forum/pic/item/aa64034f78f0f736a4a890f80955b319eac413f0.jpg",
            "https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=912466544,1453991696&fm=85&s=991123D7465109C6668DF11C03001093",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1687301639,2264795042&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2657922881,3945635140&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1915522848,2815251119&fm=27&gp=0.jpg")

    private var list4: ArrayList<String> = arrayListOf(
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2544833880,2682850620&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1617850048,3783354623&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2224925339,2344700343&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3636840918,3896845764&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=990852493,2848050334&fm=27&gp=0.jpg")

    private var list5: ArrayList<String> = arrayListOf(
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1473318516,731613934&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2981677819,1942237210&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3998400329,1220074310&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2726034926,4129010873&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3058818465,1029820612&fm=27&gp=0.jpg")

    private var list6: ArrayList<String> = arrayListOf(
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2918403984,2305096680&fm=11&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4115613474,580765086&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3908937358,2901422607&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=668589441,621027411&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1697556290,1343726110&fm=27&gp=0.jpg")

    private var list7: ArrayList<String> = arrayListOf(
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3598165397,811361118&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=900655980,1143045243&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3058342613,664958840&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=825820593,76537721&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1461988901,743179860&fm=27&gp=0.jpg")

    private var list8: ArrayList<String> = arrayListOf(
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1368919744,2575475207&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2095900981,2633594196&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3392623573,2221232040&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1006382275,2152237564&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3889890796,3992880061&fm=27&gp=0.jpg")

    private var list9: ArrayList<String> = arrayListOf(
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2743055457,1804674317&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1325404642,595919074&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2448208384,1431309044&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=121356268,4149522453&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1862596237,3151368312&fm=27&gp=0.jpg")

    private var list10: ArrayList<String> = arrayListOf(
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2037045432,2358613019&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=76137054,4105283605&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1998935427,322473980&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4084642221,1440360043&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2539765007,382507019&fm=27&gp=0.jpg")

    private var list = ArrayList<ArrayList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        list.add(list1)
        list.add(list2)
        list.add(list3)
        list.add(list4)
        list.add(list5)
        list.add(list6)
        list.add(list7)
        list.add(list8)
        list.add(list9)
        list.add(list10)

        listView.adapter = object : BaseAdapter() {
            override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
                var util: Util?
                var view: View? = p1
                if (p1 == null) {
                    util = Util()
                    val inflater = LayoutInflater.from(this@Main2Activity)
                    view = inflater.inflate(R.layout.item_main2, p2, false)
                    util.recyclerView = view!!.findViewById(R.id.recyclerView)
                    view.tag = util
                } else {
                    util = p1.tag as Util?
                }
                val config = PhotoConfigure()
                config.type = PhotoConfigure.WatchImg
                config.list = list[p0]
                config.colnum = 3
                config.isClick = true
                config.isSave = false
                util!!.recyclerView!!.setAbsListView(listView).show(config)
                return view!!
            }

            override fun getItem(p0: Int): Any {
                return list[p0]
            }

            override fun getItemId(p0: Int): Long {
                return p0.toLong()
            }

            override fun getCount(): Int {
                return list.size
            }

        }
    }

    /**
     * 内部类，用于辅助适配
     *
     * @author qiangzi
     */
    internal inner class Util {
        var recyclerView: CyfRecyclerView? = null
    }

}
