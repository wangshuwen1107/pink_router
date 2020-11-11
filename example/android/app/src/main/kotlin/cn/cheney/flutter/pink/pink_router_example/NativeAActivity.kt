package cn.cheney.flutter.pink.pink_router_example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import cn.cheney.flutter.pink.router.ResultCallback
import kotlinx.android.synthetic.main.activity_page_a.*
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

class NativeAActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_a)
        title = "NativePageA"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val paramMap = intent.getSerializableExtra("params") as? Map<*, *>
        args_txt.text = String.format(Locale.CHINA, "params : " + paramMap?.toString())

        result_btn.setOnClickListener {
            val map = HashMap<String, String>()
            map["book"] = "kotlin"
            callbackResult(map)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        var callback: ResultCallback? = null

        fun start(context: Context, params: Serializable?, callback: ResultCallback) {
            this.callback = callback
            val intent = Intent(context, NativeAActivity::class.java)
            params?.let {
                intent.putExtra("params", it)
            }
            context.startActivity(intent)
        }

        fun callbackResult(result: Map<String, Any>) {
            this.callback?.invoke(result)
            this.callback = null
        }
    }
}