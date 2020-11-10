package cn.cheney.flutter.pink.pink_router_example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.cheney.flutter.pink.pink_router.PinkRouter
import kotlinx.android.synthetic.main.activity_page_a.*
import java.io.Serializable
import java.util.*

class NativeBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_b)
        title = "NativePageB"
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val paramMap = intent.getSerializableExtra("params") as? Map<*, *>
        args_txt.text = String.format(Locale.CHINA, "params :${paramMap?.toString() ?: "form launcher"} ")

        result_btn.setOnClickListener {
            val params = mutableMapOf<String, Any>()
            params["NativeB"] = "i am form nativeB"
            PinkRouter.push("pink://test/flutterB", params) {
//                AlertDialog.Builder(this@NativeBActivity)
//                        .setTitle("回传Result")
//                        .setMessage("$it")
//                        .show()
            }
        }
    }

    companion object {

        fun start(context: Context, params: Serializable?) {
            val intent = Intent(context, NativeBActivity::class.java)
            params?.let {
                intent.putExtra("params", it)
            }

            context.startActivity(intent)
        }

    }
}