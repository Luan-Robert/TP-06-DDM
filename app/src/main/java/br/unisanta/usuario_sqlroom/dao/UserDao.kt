package br.unisanta.usuario_sqlroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.unisanta.usuario_sqlroom.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll():List<User>
    @Insert
    suspend fun insertUser(vararg user:User)
}