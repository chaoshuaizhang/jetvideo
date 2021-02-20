package com.example.jetvideo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.view.get
import com.example.jetvideo.R
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


@SuppressLint("RestrictedApi")
class BottomWithFloatingNavView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : BottomNavigationView(context, attributeSet, defStyle) {

    init {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.BottomWithFloatingNavView)
        val fabCradleMargin = attrs.getDimension(R.styleable.BottomWithFloatingNavView_fabCradleMargin, 0f)
        val fabCradleRoundedCornerRadius = attrs.getDimension(R.styleable.BottomWithFloatingNavView_fabCradleRoundedCornerRadius, 0f)
        val fabCradleVerticalOffset = attrs.getDimension(R.styleable.BottomWithFloatingNavView_fabCradleVerticalOffset, 0f)
        val fabCradleSize = attrs.getDimension(R.styleable.BottomWithFloatingNavView_fabCradleSize, 0f)
        attrs.recycle()
        val curvedEdgeTreatment = BottomAppBarTopEdgeTreatment(fabCradleMargin, fabCradleRoundedCornerRadius, fabCradleVerticalOffset).apply {
            fabDiameter = fabCradleSize
        }
        val shapeAppearanceModel = ShapeAppearanceModel.Builder()
            .setTopEdge(curvedEdgeTreatment)
            .build()
        val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            setTint(ContextCompat.getColor(context, R.color.color_white))
            paintStyle = Paint.Style.FILL_AND_STROKE
        }
        background = materialShapeDrawable
        val menuViewField = BottomNavigationView::class.java.getDeclaredField("menuView")
        menuViewField.isAccessible = true
        val menuView = menuViewField.get(this) as BottomNavigationMenuView
        // TODO: 2020/12/23 这块儿本来打算重写各个view的layout方法，然后改变放置位置，
        // 但是突然想到设置n+1个tab，中间那个不设置文字、icon，并且取消点击事件
        menuView[2].isClickable = false
    }

}