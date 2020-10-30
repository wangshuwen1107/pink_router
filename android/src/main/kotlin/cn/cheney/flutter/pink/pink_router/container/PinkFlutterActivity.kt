package cn.cheney.flutter.pink.pink_router

import android.content.Intent
import android.os.Bundle
import cn.cheney.flutter.pink.pink_router.container.ContainerDelegate
import io.flutter.embedding.android.FlutterActivity


const val FLUTTER_RESULT_KEY = "flutter_result_key"

open class PinkFlutterActivity : FlutterActivity() {

    //lateinit var delegate:ContainerDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this.delegate = ContainerDelegate()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            var result: Map<String, Any>? = null
            val resultSer = data.getSerializableExtra(FLUTTER_RESULT_KEY)
            if (resultSer is Map<*, *>) {
                result = resultSer as Map<String, Any>
            }
            RequestManager.setResult(requestCode, result)
        }
    }
    
    
    

}