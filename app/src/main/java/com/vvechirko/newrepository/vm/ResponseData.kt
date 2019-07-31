package com.vvechirko.newrepository.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.vvechirko.newrepository.data.ErrorParser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class ResponseData<T>(val isResettable: Boolean = false) : LiveData<Response<T>>() {

    open val data: T?
        get() = (value as? Response.Success<T>)?.data

    fun observe(
        owner: LifecycleOwner,
        success: Data<T> = { },
        error: Error = { },
        loading: Loading = { }
    ) {
        super.observe(owner, Observer { t ->
            when (t) {
                is Response.Success -> {
                    loading.invoke(false)
                    success.invoke(t.data)
                    resetIfNeed()
                }
                is Response.Error -> {
                    loading.invoke(false)
                    error.invoke(ErrorParser.parse(t.error))
                    resetIfNeed()
                }
                is Response.Loading -> loading.invoke(true)
            }
        })
    }

    suspend fun from(action: suspend () -> Wrapped<T>) = withContext(Dispatchers.Main) {
        value = Response.Loading()

        try {
            val result = withContext(Dispatchers.IO) { action() }
            value = Response.Success(result.data)
        } catch (canceled: CancellationException) {
            // Canceled by user
        } catch (error: Throwable) {
            value = Response.Error(error)
        }
    }

    // external modify [Success] data
    fun updateValue(data: T) {
        value = Response.Success(data)
    }

    private fun resetIfNeed() {
        if (isResettable) {
            value = null
        }
    }
}