package com.vvechirko.newrepository.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.endlessScroll(crossinline onLoad: () -> Unit): EndlessScrollListener {
    val listener = object : EndlessScrollListener(layoutManager!!) {
        override fun loadMore() {
            onLoad.invoke()
        }
    }
    addOnScrollListener(listener)
    return listener
}

abstract class EndlessScrollListener(layoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {

    private val layoutManager = layoutManager as? LinearLayoutManager
        ?: throw IllegalArgumentException("Unsupported LayoutManager")

    private var visibleThreshold = 2
    var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy < 0) return

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        if (!isLoading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            loadMore()
        }
    }

    abstract fun loadMore()
}