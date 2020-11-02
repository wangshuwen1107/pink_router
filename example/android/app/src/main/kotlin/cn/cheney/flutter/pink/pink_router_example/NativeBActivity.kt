package cn.cheney.flutter.pink.pink_router_example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.cheney.flutter.pink.pink_router.PinkRouter
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
            PinkRouter.navigation("pink://test/flutterB", null) {
                AlertDialog.Builder(this@NativeBActivity)
                        .setTitle("回传Result")
                        .setMessage("$it")
                        .show()
            }
        }
    }

    companion object {

        fun start(context: Context, params: Serializable) {
            val intent = Intent(context, NativeBActivity::class.java)
            intent.putExtra("params", params)
            context.startActivity(intent)
        }

    }
}