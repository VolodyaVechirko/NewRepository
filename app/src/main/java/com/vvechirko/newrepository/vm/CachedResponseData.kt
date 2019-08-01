package com.vvechirko.newrepository.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.vvechirko.newrepository.data.ErrorParser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CachedResponseData<T>(val db: LiveData<T>) : LiveData<Response<T>>() {

    val data: T?
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
//                    resetIfNeed()
                }
                is Response.Error -> {
                    loading.invoke(false)
                    error.invoke(ErrorParser.parse(t.error))
//                    resetIfNeed()
                }
                is Response.Loading -> loading.invoke(true)
            }
        })

        db.observe(owner, Observer {
            value = Response.Success(it)
        })
    }

    suspend fun from(action: suspend () -> T) = withContext(Dispatchers.Main) {
        value = Response.Loading()

        try {
            val result = withContext(Dispatchers.IO) { action() }
//            value = Response.Success(result.data)
        } catch (canceled: CancellationException) {
            // Canceled by user
        } catch (error: Throwable) {
            value = Response.Error(error)
        }
    }
}