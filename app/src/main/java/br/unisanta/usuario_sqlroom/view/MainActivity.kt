package br.unisanta.usuario_sqlroom.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Redireciona imediatamente para a tela de Login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
