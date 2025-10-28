package br.unisanta.usuario_sqlroom.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.unisanta.usuario_sqlroom.controller.UserController
import br.unisanta.usuario_sqlroom.dao.UserDao
import br.unisanta.usuario_sqlroom.databinding.ActivityRegisterBinding
import br.unisanta.usuario_sqlroom.db.AppDatabase
import br.unisanta.usuario_sqlroom.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userDao: UserDao
    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userController = UserController(userDao)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.edtRegisterEmail.text.toString()
        val senha = binding.edtRegisterSenha.text.toString()
        val nome = binding.edtRegisterNome.text.toString()
        val idadeStr = binding.edtRegisterIdade.text.toString()
        val telefoneStr = binding.edtRegisterTelefone.text.toString()
        val curso = binding.edtRegisterCurso.text.toString()

        if (email.isBlank() || senha.isBlank() || nome.isBlank() || idadeStr.isBlank() || telefoneStr.isBlank() || curso.isBlank()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val idade = idadeStr.toIntOrNull()
        val telefone = telefoneStr.toIntOrNull()

        if (idade == null || telefone == null) {
            Toast.makeText(this, "Idade e Telefone devem ser números válidos", Toast.LENGTH_SHORT).show()
            return
        }

        val newUser = User(
            uid = 0, // Room irá gerar
            email = email,
            senha = senha,
            nome = nome,
            idade = idade,
            telefone = telefone,
            curso = curso
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                userController.registerUser(newUser)
            }
            Toast.makeText(this@RegisterActivity, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Retorna para a tela de Login
        }
    }
}
