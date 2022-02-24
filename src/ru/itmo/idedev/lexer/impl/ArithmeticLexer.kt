package ru.itmo.idedev.lexer.impl

import ru.itmo.idedev.lexer.Lexer
import ru.itmo.idedev.lexer.Token
import ru.itmo.idedev.parser.exception.UnexpectedInputException

class ArithmeticLexer(private val input: String) : Lexer<Int> {
    override var currentPos: Int = -1
    override lateinit var currentToken: Token
    override var currentValue: Int = -1

    override fun nextToken() {
        currentPos++

        skipWhitespaces()
        if (currentPos >= input.length) {
            currentToken = Token.END
            return
        }

        val char = input[currentPos]

        val token: Token? = charToToken[char]
        if (token != null) {
            currentToken = token
            return
        }

        if (char.isDigit()) {
            val startPos = currentPos
            while (currentPos < input.length && input[currentPos].isDigit()) {
                currentPos++
            }
            currentValue = input.substring(startPos until currentPos).toInt()
            currentToken = Token.NUMBER
            currentPos--
            return
        }

        throw UnexpectedInputException(currentPos, char)
    }

    private fun skipWhitespaces() {
        while (currentPos < input.length && input[currentPos].isWhitespace()) {
            currentPos++
        }
    }

    companion object {
        private const val PLUS: Char = '+'
        private const val MINUS: Char = '-'
        private const val MULT: Char = '*'
        private const val DIV: Char = '/'

        private const val LP: Char = '('
        private const val RP: Char = ')'

        val charToToken: Map<Char, Token> = mapOf(
            PLUS to Token.PLUS,
            MINUS to Token.MINUS,
            MULT to Token.MUL,
            DIV to Token.DIV,
            LP to Token.LP,
            RP to Token.RP,
        )
    }
}
