package br.unisanta.usuario_sqlroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.unisanta.usuario_sqlroom.dao.UserDao
import br.unisanta.usuario_sqlroom.model.User


@Database(entities =[User::class], version = 1 )
abstract class AppDatabase :RoomDatabase() {
    abstract fun userDao():UserDao
}