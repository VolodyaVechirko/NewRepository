package com.vvechirko.newrepository.vm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.gson.annotations.SerializedName


typealias Data<T> = (data: T) -> Unit
typealias Error = (error: String) -> Unit
typealias Loading = (b: Boolean) -> Unit

const val DEFAULT_PAGE = 1

sealed class Response<T> {
    class Success<T>(val data: T) : Response<T>()
    class Error<T>(val error: Throwable) : Response<T>()
    class Loading<T>() : Response<T>()
}


class Paginated<T>(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val data: List<T>
)

class Wrapped<T>(
    val data: T
)


class ActionData : ResponseData<Unit>(true)


/**
 * Provide the [ViewModel] for current [Fragment] instance related to itself
 */
inline fun <reified VM : ViewModel> Fragment.provideViewModel(): VM =
    ViewModelProviders.of(this).get(VM::class.java)

/**
 * Provide the [ViewModel] for current [Fragment] instance related to it parentFragment
 */
inline fun <reified VM : ViewModel> Fragment.parentViewModel(): VM =
    ViewModelProviders.of(parentFragment!!).get(VM::class.java)

/**
 * Provide the [ViewModel] for current [Fragment] instance related to it host activity
 */
inline fun <reified VM : ViewModel> Fragment.hostViewModel(): VM =
    ViewModelProviders.of(activity!!).get(VM::class.java)

/**
 * Provide the [ViewModel] for current [FragmentActivity] instance related to itself
 */
inline fun <reified VM : ViewModel> FragmentActivity.provideViewModel(): VM =
    ViewModelProviders.of(this).get(VM::class.java)