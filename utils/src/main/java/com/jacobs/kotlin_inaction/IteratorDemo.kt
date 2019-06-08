package com.jacobs.kotlin_inaction

import java.util.*

/**
 * @author lichao
 * Created on 2019-06-08
 */

fun testForLoop() {
    val binaryReps = TreeMap<Char, String>()
    // Iterates over the characters from A to F using a range of characters
    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }

    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }

    for (i in 100 downTo 1 step 2) {
        print(fizzBuzz(i))
    }

    // 在遍历时，同时拿到遍历的index
    val list = arrayListOf("10", "11", "10001")
    for ((index, element) in list.withIndex()) {
        println("$index : $element")
    }
}

//通过 in 来判断元素是否在collection 范围里面
//底层实际转换为a <= c && c <= z
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'

fun isNotDigit(c: Char) = c !in '0'..'9'

fun main() {
    testForLoop()
    println(isLetter('q'))
    println(isNotDigit('x'))
    println(recognize('8'))
}