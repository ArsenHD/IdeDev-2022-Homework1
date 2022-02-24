package ru.itmo.idedev.parser.exception

import ru.itmo.idedev.lexer.Token

abstract class ParserException(override val message: String?) : Exception(message)

class UnexpectedInputException(
    val position: Int,
    val character: Char
) : ParserException("Unexpected character '$character' at position $position in the input expression")

class UnexpectedTokenException(
    private val token: Token
) : ParserException("Unexpected token ${token.name} received during parsing")

class UnmatchedOpenParenthesis(
    val position: Int
) : ParserException("Open parenthesis at position $position is unmatched")
