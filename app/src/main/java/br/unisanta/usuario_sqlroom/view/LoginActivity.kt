package br.unisanta.usuario_sqlroom.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.unisanta.usuario_sqlroom.controller.UserController
import br.unisanta.usuario_sqlroom.dao.UserDao
import br.unisanta.usuario_sqlroom.databinding.ActivityLoginBinding
import br.unisanta.usuario_sqlroom.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDao: UserDao
    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userController = UserController(userDao)

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.btnRegisterRedirect.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Verifica se há usuário logado e redireciona
        checkUserSession()
    }

    private fun checkUserSession() {
        val sharedPrefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val loggedInUid = sharedPrefs.getInt("logged_in_uid", -1)
        if (loggedInUid != -1) {
            // Usuário já logado, redireciona para a tela de perfil
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser() {
        val email = binding.edtLoginEmail.text.toString()
        val senha = binding.edtLoginSenha.text.toString()

        if (email.isBlank() || senha.isBlank()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                userController.login(email, senha)
            }

            if (user != null) {
                // Login bem-sucedido. Armazenar o UID do usuário (simulação de sessão)
                val sharedPrefs = getSharedPreferences("user_session", MODE_PRIVATE)
                sharedPrefs.edit().putInt("logged_in_uid", user.uid).apply()

                Toast.makeText(this@LoginActivity, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                startActivity(intent)
                finish() // Fechar a tela de login
            } else {
                Toast.makeText(this@LoginActivity, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
