package com.example.mvvm.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindItem(item: T?,pos:Int)
}