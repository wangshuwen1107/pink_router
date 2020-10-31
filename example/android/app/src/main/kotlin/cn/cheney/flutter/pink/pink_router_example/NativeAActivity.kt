package cn.cheney.flutter.pink.pink_router_example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cheney.flutter.pink.pink_router.ResultCallback
import kotlinx.android.synthetic.main.activity_page_a.*
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

class NativeAActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_a)
        title = "NativePageA"
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        
        val paramMap = intent.getSerializableExtra("params") as? Map<*, *>
        args_txt.text = String.format(Locale.CHINA, "params : " + paramMap?.toString())

        result_btn.setOnClickListener {
            val map = HashMap<String, String>()
            map["book"] = "kotlin"
            callbackResult(map)
            finish()
        }
    }


    companion object {

        var callback: ResultCallback? = null

        fun start(context:Context,params:Serializable,callback: ResultCallback) {
            this.callback = callback
            val intent= Intent(context,NativeAActivity::class.java)
            intent.putExtra("params",params)
            context.startActivity(intent)
        }

        fun callbackResult(result:Any) {
            this.callback?.invoke(result)
            this.callback = null
        }
    }
}