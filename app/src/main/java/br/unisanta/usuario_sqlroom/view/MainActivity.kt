package br.unisanta.usuario_sqlroom.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.unisanta.usuario_sqlroom.R
import br.unisanta.usuario_sqlroom.dao.UserDao
import br.unisanta.usuario_sqlroom.databinding.ActivityMainBinding
import br.unisanta.usuario_sqlroom.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db-user"
        ).fallbackToDestructiveMigration()
            .build()
        dao = db.userDao()

//        binding.btnSave.setOnClickListener{
//            val firstName = binding.edtFirstName.text.toString()
//            val lastName = binding.edtLastName.text.toString()
//            val user = User(0,firstName,lastName)
//            lifecycleScope.launch {
//                dao.insertUser(user)
//            }
//        }
//        binding.btnFind.setOnClickListener {
//            lifecycleScope.launch {
//                val list = withContext(Dispatchers.IO){
//                    dao.getAll()
//                }
//                list.forEach{
//                    Log.i("USER","${it.uid} - ${it.firstName} - ${it.lastName}")
//                }
//            }
//
//        }
    }
}