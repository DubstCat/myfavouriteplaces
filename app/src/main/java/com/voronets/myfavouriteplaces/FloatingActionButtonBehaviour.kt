package com.voronets.myfavouriteplaces

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.min


public class FloatingActionButtonBehavior: CoordinatorLayout.Behavior<FloatingActionButton> {
    constructor(context: Context?, attrs: AttributeSet?) {}

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ): Boolean {
        val translationY =
            min(0.0f, (attr.translationY - attr.height).toFloat()).toFloat()
        child.translationY = translationY
        return true
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ): Boolean {
        return dependency is FrameLayout;
    }

    }
