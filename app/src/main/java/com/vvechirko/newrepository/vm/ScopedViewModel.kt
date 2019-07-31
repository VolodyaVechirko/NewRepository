package com.vvechirko.newrepository.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class ScopedViewModel : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler()

    override fun onCleared() {
        super.onCleared()
        job.cancelChildren()
    }
}

fun exceptionHandler(cancelChildren: Boolean = false) = CoroutineExceptionHandler { coroutineContext, throwable ->
    throwable.printStackTrace()

    if (cancelChildren) {
        coroutineContext.cancelChildren()
    }
}