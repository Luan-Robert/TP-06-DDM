package br.unisanta.usuario_sqlroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import br.unisanta.usuario_sqlroom.dao.UserDao
import br.unisanta.usuario_sqlroom.model.User


@Database(entities =[User::class], version = 1 )
abstract class AppDatabase :RoomDatabase() {
    abstract fun userDao():UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db-user"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}