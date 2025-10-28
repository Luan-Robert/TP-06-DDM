package br.unisanta.usuario_sqlroom.controller

import br.unisanta.usuario_sqlroom.dao.UserDao
import br.unisanta.usuario_sqlroom.model.User

class UserController(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, senha: String): User? {
        return userDao.login(email, senha)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun getUserById(uid: Int): User? {
        return userDao.getUserById(uid)
    }
}
