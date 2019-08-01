package com.vvechirko.newrepository.ui

import android.util.Log
import com.vvechirko.newrepository.App
import com.vvechirko.newrepository.data.Api
import com.vvechirko.newrepository.data.UserEntity
import com.vvechirko.newrepository.data.provideService
import com.vvechirko.newrepository.vm.*
import kotlinx.coroutines.launch

class MainViewModel : ScopedViewModel() {

    val api = provideService<Api>()
    val userDao = App.db.userDao()

    val usersData = PaginatedData<UserEntity>()
    val userData = ResponseData<UserEntity>()

    val userDbLiveData = userDao.getLive(3)

    val userCachedData = CachedResponseData<UserEntity>(userDao.getLive(4))

    init {
        if (usersData.data.isEmpty()) {
            refresh()
        }

        launch {
//            userData.from {
//                val user = api.getUser(3)
//                Log.d("RoomTest", "api.getUser ${user.data}")
//                userDao.insert(user.data)
//                Log.d("RoomTest", "userDao.insert ${user.data}")
//                user
//            }


            userCachedData.from {
                val user = api.getUser(4).data
                Log.d("RoomTest", "api.getUser $user")
                userDao.insert(user)
                Log.d("RoomTest", "userDao.insert $user")
                user
            }
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