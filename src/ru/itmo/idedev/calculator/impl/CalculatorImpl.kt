package ru.itmo.idedev.calculator.impl

import ru.itmo.idedev.calculator.Calculator
import ru.itmo.idedev.parser.Parser
import ru.itmo.idedev.parser.impl.ArithmeticParser

class CalculatorImpl : Calculator {
    private val parser: Parser<Int> = ArithmeticParser()

    override fun calculate(expression: String): Int {
        val tree = parser.parse(expression)
        return tree.calculate()
    }
}