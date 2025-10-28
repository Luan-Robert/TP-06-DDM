package br.unisanta.usuario_sqlroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import br.unisanta.usuario_sqlroom.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll():List<User>

    @Query("SELECT * FROM user WHERE email = :email AND senha = :senha")
    suspend fun login(email: String, senha: String): User?

    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun getUserById(uid: Int): User?

    @Update
    suspend fun update(user: User)
    @Insert
    suspend fun insertUser(vararg user:User)
}