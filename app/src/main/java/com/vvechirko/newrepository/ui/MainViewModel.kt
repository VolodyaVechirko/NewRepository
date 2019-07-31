package com.vvechirko.newrepository.ui

import com.vvechirko.newrepository.data.Api
import com.vvechirko.newrepository.data.UserEntity
import com.vvechirko.newrepository.data.provideService
import com.vvechirko.newrepository.vm.DEFAULT_PAGE
import com.vvechirko.newrepository.vm.PaginatedData
import com.vvechirko.newrepository.vm.ResponseData
import com.vvechirko.newrepository.vm.ScopedViewModel
import kotlinx.coroutines.launch

class MainViewModel : ScopedViewModel() {

    val api = provideService<Api>()
    val usersData = PaginatedData<UserEntity>()
    val userData = ResponseData<UserEntity>()

    init {
        if (usersData.data.isEmpty()) {
            refresh()
        }

        launch {
            userData.from { api.getUser(3) }
        }
    }

    fun refresh() = launch {
        usersData.from { api.getUsers(DEFAULT_PAGE) }
    }

    fun fetch() = launch {
        if (usersData.hasNextPage) {
            usersData.from { api.getUsers(usersData.nextPage) }
        }
    }
}