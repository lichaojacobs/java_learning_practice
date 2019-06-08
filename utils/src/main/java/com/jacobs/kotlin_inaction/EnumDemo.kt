package com.jacobs.kotlin_inaction

/**
 * @author lichao
 * Created on 2019-06-08
 */

enum class Color1 {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO
}

enum class Color2(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    ORANGE(0, 255, 255),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130), VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b
}