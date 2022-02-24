package ru.itmo.idedev.parser

/**
 * [E] - expression type returned by parser
 */
interface Parser<E> {
    fun parse(input: String): E
}
