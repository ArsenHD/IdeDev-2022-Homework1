package ru.itmo.idedev.parser.impl

import ru.itmo.idedev.lexer.Lexer
import ru.itmo.idedev.lexer.Token
import ru.itmo.idedev.lexer.impl.ArithmeticLexer
import ru.itmo.idedev.parser.Parser
import ru.itmo.idedev.parser.exception.UnexpectedTokenException
import ru.itmo.idedev.parser.exception.UnmatchedOpenParenthesis
import ru.itmo.idedev.parser.expression.*

class ArithmeticParser : Parser<Int> {
    private lateinit var lexer: Lexer<Int>

    private val currentToken: Token
        get() = lexer.currentToken

    private val currentValue: Int
        get() = lexer.currentValue

    override fun parse(input: String): Expression<Int> {
        lexer = ArithmeticLexer(input)
        lexer.nextToken()
        return parseAddSub()
    }

    private fun parseAddSub(): Expression<Int> {
        var expression = parseMulDiv()
        while (true) {
            when (currentToken) {
                Token.PLUS -> {
                    lexer.nextToken()
                    expression = AddExpression(expression, parseMulDiv())
                }
                Token.MINUS -> {
                    lexer.nextToken()
                    expression = SubtractExpression(expression, parseMulDiv())
                }
                else -> return expression
            }
        }
    }

    private fun parseMulDiv(): Expression<Int> {
        var expression = parsePrimary()
        while (true) {
            when (currentToken) {
                Token.MUL -> {
                    lexer.nextToken()
                    expression = ProductExpression(expression, parsePrimary())
                }
                Token.DIV -> {
                    lexer.nextToken()
                    expression = DivideExpression(expression, parsePrimary())
                }
                else -> return expression
            }
        }
    }

    private fun parsePrimary(): Expression<Int> {
        return when (currentToken) {
            Token.LP -> {
                val pos = lexer.currentPos
                lexer.nextToken()
                val expression = parseAddSub()
                if (currentToken != Token.RP) {
                    throw UnmatchedOpenParenthesis(pos)
                }
                lexer.nextToken()
                expression
            }
            Token.NUMBER -> {
                val expression = ConstExpression(currentValue)
                lexer.nextToken()
                expression
            }
            else -> throw UnexpectedTokenException(currentToken)
        }
    }
}
