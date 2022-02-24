package ru.itmo.idedev.lexer

interface Lexer<T> {
    val currentToken: Token
    val currentValue: T
    val currentPos: Int

    fun nextToken()
}
