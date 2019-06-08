package com.jacobs.kotlin_inaction

import com.jacobs.kotlin_inaction.Color2.*

/**
 * @author lichao
 * Created on 2019-06-08
 */

// using "when" to deal with enum classes
// 这里不像java，不需要写break, 一旦匹配成功就会退出匹配
// 这里的情况必须穷举，否则需要手动加上else分支，防止匹配不上
fun getMnemonic(color: Color2) = when (color) {
    RED -> "Richard"
    YELLOW -> "York"
    BLUE -> "battle"
    else -> "not found"
}

fun getWarmth(color: Color2) = when (color) {
    RED, YELLOW -> "warm"
    GREEN -> "neutral"
    BLUE, INDIGO, VIOLET -> "cold"
    else -> "not found"
}

// using "when" with arbitrary objects
// 适用于其他任何类型
fun mix(c1: Color2, c2: Color2) = when (setOf(c1, c2)) {
    setOf(RED, YELLOW) -> ORANGE
    setOf(YELLOW, BLUE) -> GREEN
    setOf(BLUE, VIOLET) -> INDIGO
    else -> throw Exception("Dirty color")
}

//Using “when” without an argument
fun mixOptimized(c1: Color2, c2: Color2) =
        when {
            (c1 == RED && c2 == YELLOW) ||
                    (c1 == YELLOW && c2 == RED) ->
                ORANGE
            (c1 == YELLOW && c2 == BLUE) ||
                    (c1 == BLUE && c2 == YELLOW) ->
                GREEN
            (c1 == BLUE && c2 == VIOLET) ||
                    (c1 == VIOLET && c2 == BLUE) ->
                INDIGO
            else -> throw Exception("Dirty color")
        }


// 用when来替代if分支写法
interface Expr

// Simple value object class with one property, value, implementing the kotlin.inaction.Expr interface
class Num(val value: Int) : Expr

// The argument of a kotlin.inaction.Sum operation can be any kotlin.inaction.Expr: either kotlin.inaction.Num or another kotlin.inaction.Sum
class Sum(val left: Expr, val right: Expr) : Expr

//原始的写法
fun eval(e: Expr): Int {
    if (e is Num) {
        return e.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left)
    }
    throw IllegalArgumentException("Unknown expression")
}

fun evalRefactor(e: Expr): Int = when (e) {
    is Num -> e.value
    is Sum -> eval(e.right) + eval(e.left)
    else -> throw IllegalArgumentException("Unknown expression")
}

// using "when" to implement fizzbuzz game
fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz"
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}

// using in
fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know..."
}

fun main() {
    println(BLUE.rgb())
    println(getMnemonic(YELLOW))
    println(mix(BLUE, YELLOW))

    println(eval(Sum(Num(1), Num(2))))
}