package br.unisanta.usuario_sqlroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid:Int,
    @ColumnInfo("email")
    val email:String,
    @ColumnInfo("senha")
    val senha:String,
    @ColumnInfo("curso")
    val curso:String,
    @ColumnInfo("nome")
    val nome:String,
    @ColumnInfo("idade")
    val idade:Int,
    @ColumnInfo("telefone")
    val telefone:Int,
)
