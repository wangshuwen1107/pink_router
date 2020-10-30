package cn.cheney.flutter.pink.pink_router_example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cheney.flutter.pink.pink_router.FLUTTER_RESULT_KEY
import cn.cheney.flutter.pink.pink_router.PinkFlutterActivity
import kotlinx.android.synthetic.main.activity_page_a.*
import java.util.*
import kotlin.collections.HashMap

class Native1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_a)
        title = "NativePageA"
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val paramMap = intent.getSerializableExtra("params") as? Map<*, *>
        args_txt.text = String.format(Locale.CHINA, "params : " + paramMap?.toString())

        result_btn.setOnClickListener {

            val intent = Intent()
            val map = HashMap<String, String>()
            map["book"] = "kotlin"
            intent.putExtra(FLUTTER_RESULT_KEY, map)
            setResult(1000, intent)
            finish()
        }
    }


}