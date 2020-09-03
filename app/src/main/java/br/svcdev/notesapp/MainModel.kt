package br.svcdev.notesapp

class MainModel {
    private var counter: Int = 0

    fun getCounter(): Int? = ++counter
}