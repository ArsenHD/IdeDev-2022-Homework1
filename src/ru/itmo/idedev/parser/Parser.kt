package ru.itmo.idedev.parser

import ru.itmo.idedev.parser.expression.Expression

interface Parser<T> {
    fun parse(input: String): Expression<T>
}
