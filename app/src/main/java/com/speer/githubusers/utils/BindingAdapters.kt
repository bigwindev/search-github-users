package com.speer.githubusers.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import coil.load

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        imageView.load(imageUri)
    }
}

@BindingAdapter("onRefresh")
fun bindOnRefreshListener(swipeRefreshLayout: SwipeRefreshLayout, onRefreshListener: OnRefreshListener?) {
    swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
}

@BindingAdapter("refreshing")
fun bindRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
    swipeRefreshLayout.isRefreshing = isRefreshing
}
