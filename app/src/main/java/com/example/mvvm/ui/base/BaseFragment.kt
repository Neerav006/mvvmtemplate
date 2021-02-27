package com.example.mvvm.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract  class BaseFragment<VM : ViewModel, VB : ViewBinding>:Fragment() {
    protected abstract val mViewModel: VM

    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = getViewBinding()
    }

    /**
     * It returns [VB] which is assigned to [mViewBinding] and used in [onCreate]
     */
    abstract fun getViewBinding(): VB
}