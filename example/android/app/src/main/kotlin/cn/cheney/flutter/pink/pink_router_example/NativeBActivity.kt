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

class NativeBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_b)
        title = "NativePageB"
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val paramMap = intent.getSerializableExtra("params") as? Map<*, *>
        args_txt.text = String.format(Locale.CHINA, "params : " + paramMap?.toString())

        result_btn.setOnClickListener {
            TODO()
        }
    }


    companion object {

        fun start(context: Context,params:Serializable) {
            val intent = Intent(context, NativeBActivity::class.java)
            intent.putExtra("params",params)
            context.startActivity(intent)
        }

    }
}