package cn.cheney.flutter.pink.pink_router_example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_page_a.*
import java.util.*

class NativeAActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_a)

        title = "NativePageA"
        val paramMap = intent.getSerializableExtra("params") as? Map<*, *>
        args_txt.text = String.format(Locale.CHINA, "params : " + paramMap?.toString())
    }
}