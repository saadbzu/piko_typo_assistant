package com.example.piko_typo_assistant.overlayController

import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.example.piko_typo_assistant.R

class OverlayController(private val context: Context) {

    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var overlayView: View? = null
    private var isAttached: Boolean = false

    private var xValue : Int = 0;
    private var yValue : Int = 0;

    fun show(){
        if(isAttached) return

        val view = LayoutInflater.from(context).inflate(R.layout.overlay_icon_layout, null)

        // Calculate position (x and y)
        val screenWidth = context.resources.displayMetrics.widthPixels
        val screenHeight = context.resources.displayMetrics.heightPixels


        // X value
        val iconWidth = context.resources.getDimensionPixelSize(R.dimen.app_icon_width)
        val iconMargin = context.resources.getDimensionPixelSize(R.dimen.app_icon_margin)
        xValue = screenWidth - iconWidth - iconMargin

        // Y value
        val iconHeight = context.resources.getDimensionPixelSize(R.dimen.app_icon_height)
        yValue = (screenHeight - iconHeight) / 2

        val param = WindowManager.LayoutParams(
            context.resources.getDimensionPixelSize(R.dimen.app_icon_width),
            context.resources.getDimensionPixelSize(R.dimen.app_icon_height),
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {  gravity = Gravity.TOP or Gravity.START
            x = xValue
            y = yValue
        }

        windowManager.addView(view, param)

        overlayView = view
        isAttached = true

        view.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Touch down logic
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    // Drag / Move logic
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // Touch release logic
                    view.performClick() // Crucial for accessibility support
                    true
                }
                else -> false
            }
        }
    }

    fun hide(){
        if(!isAttached) return

        try {
            overlayView?.let { view->
                windowManager.removeView(view)
            }
        } catch (e: IllegalArgumentException){
            Log.w("overlay_exception", ""+e.message)
        } finally {
            overlayView = null
            isAttached = false
            xValue = 0
            yValue = 0
        }
    }

}