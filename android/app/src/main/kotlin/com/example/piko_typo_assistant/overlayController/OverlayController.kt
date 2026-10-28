package com.example.piko_typo_assistant.overlayController

import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.WindowManager
import com.example.piko_typo_assistant.R
import kotlin.math.abs

class OverlayController(private val context: Context) {

    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var overlayView: View? = null
    private var isAttached: Boolean = false

    private var xValue : Int = 0
    private var yValue : Int = 0

    private var param : WindowManager.LayoutParams? = null

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

        param = WindowManager.LayoutParams(
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

        var initialXvalue = 0
        var initialYvalue = 0
        var fingerX = 0
        var fingerY = 0
        var isDragging = false

        val touchSlop = ViewConfiguration.get(context).scaledTouchSlop

        view.setOnTouchListener { view, event ->

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("touch_checking", "DOWN")
                    isDragging = false

                    initialXvalue = param!!.x
                    initialYvalue = param!!.y

                    fingerX = event.rawX.toInt()
                    fingerY = event.rawY.toInt()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    // Drag / Move logic
                    Log.d("touch_checking", "MOVE")

                    val dx = event.rawX - fingerX
                    val dy = event.rawY - fingerY

                    if (abs(dx) > abs(touchSlop) || abs(dy) > abs(touchSlop)) {
                        isDragging = true
                    }

                    val newWindowX = initialXvalue + dx
                    val newWindowY = initialYvalue + dy

                    newWindowX.toInt().coerceIn(0, screenWidth - iconWidth)
                    newWindowY.toInt().coerceIn(0, screenHeight - iconHeight)

                    if(isDragging){
                        param!!.x = newWindowX.toInt()
                        param!!.y = newWindowY.toInt()

                        windowManager.updateViewLayout(view, param)
                    }

                    true
                }
                MotionEvent.ACTION_UP -> {
                    // Touch release logic

                    if(!isDragging){
                        Log.d("touch_checking", "UP")
                        view.performClick() // Crucial for accessibility support
                    }

                    true
                }
                MotionEvent.ACTION_CANCEL -> {
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
            param = null
            xValue = 0
            yValue = 0
        }
    }

}