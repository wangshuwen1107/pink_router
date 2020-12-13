package cn.cheney.flutter.pink.pink_router_example

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.cheney.flutter.pink.router.PinkRouter
import kotlinx.android.synthetic.main.activity_page_main.*

class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_main)
        flutter_main.setOnClickListener {
            PinkRouter.push("pink://test/main")
        }
        flutter_a.setOnClickListener {

            PinkRouter.push("pink://test/flutterA",
                    params = mapOf("NativeMainKey" to "NativeMainValue")) {
                AlertDialog.Builder(this@MainActivity)
                        .setTitle("回传Result")
                        .setMessage("$it")
                        .show()
            }
        }
    }

}
