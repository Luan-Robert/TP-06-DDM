package br.unisanta.usuario_sqlroom.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.unisanta.usuario_sqlroom.controller.UserController
import br.unisanta.usuario_sqlroom.dao.UserDao
import br.unisanta.usuario_sqlroom.databinding.ActivityProfileBinding
import br.unisanta.usuario_sqlroom.db.AppDatabase
import br.unisanta.usuario_sqlroom.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userDao: UserDao
    private lateinit var userController: UserController
    private var currentUserId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userController = UserController(userDao)

        // Recupera o ID do usuário logado
        val sharedPrefs = getSharedPreferences("user_session", MODE_PRIVATE)
        currentUserId = sharedPrefs.getInt("logged_in_uid", -1)

        if (currentUserId == -1) {
            // Se não houver ID, redireciona para o login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        loadUserProfile()

        binding.btnUpdate.setOnClickListener {
            updateUserProfile()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun loadUserProfile() {
        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                userController.getUserById(currentUserId)
            }
            user?.let {
                binding.edtProfileEmail.setText(it.email)
                binding.edtProfileNome.setText(it.nome)
                binding.edtProfileIdade.setText(it.idade.toString())
                binding.edtProfileTelefone.setText(it.telefone.toString())
                binding.edtProfileCurso.setText(it.curso)
            } ?: run {
                Toast.makeText(this@ProfileActivity, "Erro ao carregar perfil.", Toast.LENGTH_SHORT).show()
                logout()
            }
        }
    }

    private fun updateUserProfile() {
        val nome = binding.edtProfileNome.text.toString()
        val idadeStr = binding.edtProfileIdade.text.toString()
        val telefoneStr = binding.edtProfileTelefone.text.toString()
        val curso = binding.edtProfileCurso.text.toString()

        if (nome.isBlank() || idadeStr.isBlank() || telefoneStr.isBlank() || curso.isBlank()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val idade = idadeStr.toIntOrNull()
        val telefone = telefoneStr.toIntOrNull()

        if (idade == null || telefone == null) {
            Toast.makeText(this, "Idade e Telefone devem ser números válidos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val oldUser = withContext(Dispatchers.IO) {
                userController.getUserById(currentUserId)
            }

            oldUser?.let {
                val updatedUser = it.copy(
                    nome = nome,
                    idade = idade,
                    telefone = telefone,
                    curso = curso
                    // A senha e o email permanecem os mesmos
                )
                withContext(Dispatchers.IO) {
                    userController.updateUser(updatedUser)
                }
                Toast.makeText(this@ProfileActivity, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(this@ProfileActivity, "Erro: Usuário não encontrado.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logout() {
        val sharedPrefs = getSharedPreferences("user_session", MODE_PRIVATE)
        sharedPrefs.edit().remove("logged_in_uid").apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
