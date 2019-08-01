package com.vvechirko.newrepository.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM user")
    fun getAllLive(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user where id = :id")
    fun getLive(id: Int): LiveData<UserEntity>

    @Query("SELECT * FROM user where first_name LIKE :firstName AND last_name LIKE :lastName")
    fun findByName(firstName: String, lastName: String): UserEntity

    @Query("SELECT COUNT(*) from user")
    fun countUsers(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Insert
    fun insertList(users: List<UserEntity>)

    @Insert
    fun insertAll(vararg users: UserEntity)

    @Delete
    fun delete(user: UserEntity)

//    @Delete
//    suspend fun deleteSuspend(user: UserEntity)
}


@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
//        private lateinit var instance: AppDatabase
//
//        @Synchronized
//        fun getInstance(context: Context): AppDatabase {
//            if (!this::instance.isInitialized) {
//                instance = Room
//                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, "app.db")
//                    .fallbackToDestructiveMigration()
//                    .build()
//            }
//            return instance
//        }

        @Synchronized
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}