package com.example.piko_typo_assistant

import android.content.Intent
import android.provider.Settings
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import androidx.core.net.toUri

class MainActivity : FlutterActivity(){

    private val CHANNEL = "typo_assistant"
    private lateinit var methodChannel: MethodChannel

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)

        methodChannel.setMethodCallHandler { call, result ->

                    if(call.method == "requestOverlayPermission"){
                        if(Settings.canDrawOverlays(this)){
                            result.success(true)
                        } else {
                           val intent = Intent(
                               Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                               "package:$packageName".toUri()
                           )
                           startActivity(intent)

                            result.success(false)
                        }
                    } else {
                        result.notImplemented()
                    }


                }
    }

    override fun onResume() {
        super.onResume()

        if(::methodChannel.isInitialized){
            if(Settings.canDrawOverlays(this)){
                methodChannel.invokeMethod("overlayPermissionResult", true)
            } else {
                methodChannel.invokeMethod("overlayPermissionResult", false)
            }
        }
    }
}
