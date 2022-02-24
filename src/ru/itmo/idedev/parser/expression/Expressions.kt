package ru.itmo.idedev.parser.expression

sealed class Expression<T> {
    abstract fun calculate(): T
    abstract override fun toString(): String
}


/**
 * Binary expressions, e.g. addition, subtraction, multiplication and division
 */
sealed class BinaryExpression<T : Number>(
    val left: Expression<T>,
    val right: Expression<T>
) : Expression<T>() {

    override fun calculate(): T {
        return applyOperation()
    }

    protected abstract fun applyOperation(): T
}

class AddExpression(left: Expression<Int>, right: Expression<Int>) : BinaryExpression<Int>(left, right) {
    override fun applyOperation(): Int {
        return left.calculate() + right.calculate()
    }

    override fun toString(): String {
        return "($left + $right)"
    }
}

class SubtractExpression(left: Expression<Int>, right: Expression<Int>) : BinaryExpression<Int>(left, right) {
    override fun applyOperation(): Int {
        return left.calculate() - right.calculate()
    }

    override fun toString(): String {
        return "($left - $right)"
    }
}

class ProductExpression(left: Expression<Int>, right: Expression<Int>) : BinaryExpression<Int>(left, right) {
    override fun applyOperation(): Int {
        return left.calculate() * right.calculate()
    }

    override fun toString(): String {
        return "($left * $right)"
    }
}

class DivideExpression(left: Expression<Int>, right: Expression<Int>) : BinaryExpression<Int>(left, right) {
    override fun applyOperation(): Int {
        return left.calculate() / right.calculate()
    }

    override fun toString(): String {
        return "($left / $right)"
    }
}

/**
 * Number expression
 */
class ConstExpression(val value: Int) : Expression<Int>() {
    override fun calculate(): Int {
        return value
    }

    override fun toString(): String {
        return "$value"
    }
}
