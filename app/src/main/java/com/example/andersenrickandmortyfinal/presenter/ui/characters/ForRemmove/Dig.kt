package com.example.andersenrickandmortyfinal.presenter.ui.characters.ForRemmove

class Dig {
}



fun trim ():String{
    val a = "https://rickandmortyapi.com/api/episode/6"
  val new =   a.filter {
        it.isDigit()
    }
    return new
}

fun main() {
   println(trim())
}