package com.example.piko_typo_assistant

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.piko_typo_assistant.overlayController.OverlayController

class TypoAccessibilityService : AccessibilityService(){

    private val TAG: String = "AccessibilityService"

    private val overlayController: OverlayController by lazy {
        OverlayController(this)
    }
    companion object {
        var isConnected: Boolean = false
            private set
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        isConnected = true
        try {
            overlayController.show()
        } catch (e: Exception) {
            Log.w(
                TAG,
                "Failed to show overlay",
                e
            )
        }
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        Log.i(TAG,
            "Event = "+p0?.eventType)
    }

    override fun onInterrupt() {
        Log.i(TAG, "Event Interrupt")
    }

    override fun onUnbind(intent: Intent?): Boolean {

        overlayController.hide()
        isConnected = false

        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        overlayController.hide()
        isConnected = false
    }
}