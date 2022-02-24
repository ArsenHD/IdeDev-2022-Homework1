package ru.itmo.idedev.parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

import ru.itmo.idedev.parser.expression.Expression
import ru.itmo.idedev.parser.impl.ArithmeticParser

class ArithmeticParserTest {
    private val parser: Parser<Int> = ArithmeticParser()

    @ParameterizedTest
    @MethodSource("parseTestProvider")
    fun parseTest(input: String, expected: String) {
        val expression: Expression<Int> = parser.parse(input)
        assertEquals(expected, expression.toString())
    }

    @ParameterizedTest
    @MethodSource("calculationTestProvider")
    fun calculationTest(input: String, expected: Int) {
        val expression: Expression<Int> = parser.parse(input)
        assertEquals(expected, expression.calculate())
    }

    companion object {

        @JvmStatic
        fun parseTestProvider(): List<Arguments> =
            List(inputs.size) { arguments(inputs[it], expectedText[it]) }

        @JvmStatic
        fun calculationTestProvider(): List<Arguments> =
            List(inputs.size) { arguments(inputs[it], expectedValues[it]) }

        private val inputs: List<String> = listOf(
            "2 + 2",
            "(3 - 10)",
            "1000 * 3 - (15 / 3) * (((2 + 2) + 3) + 4)",
            "((((((0))))))",
            "123",
            "(2) + (3) * ((4)) - 5",
            "5 / 5 + 10"
        )

        private val expectedText: List<String> = listOf(
            "(2 + 2)",
            "(3 - 10)",
            "((1000 * 3) - ((15 / 3) * (((2 + 2) + 3) + 4)))",
            "0",
            "123",
            "((2 + (3 * 4)) - 5)",
            "((5 / 5) + 10)"
        )

        private val expectedValues: List<Int> = listOf(
            4,
            -7,
            2945,
            0,
            123,
            9,
            11
        )
    }
}
