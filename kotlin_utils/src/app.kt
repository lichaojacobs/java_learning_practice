/**
 * Created by lichao on 2017/5/26.
 */
fun main(args: Array<String>) {
    cases("Hello")
    cases(1)
    cases(0L)

    val map = hashMapOf<String, Int>()
    map.putIfAbsent("one", 1)
    map.putIfAbsent("two", 2)
    for ((key, value) in map) {
        println("key = $key, value = $value")
    }

    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd))
}

fun isOdd(x: Int) = x % 2 != 0

//Int? 表示可能返回空
fun getStringLength(obj: Any): Int? {
    if (obj is String)
        return obj.length
    return null
}

fun forLoop() {
    val list = listOf(1, 2, 3)

    //1
    for (arg in list)
        print(arg)

    //2
    for (i in list.indices)
        print(list[i])
}

fun rangAndIn() {
    val x = 1
    val y = 10
    if (x in 1..y - 1) {
        print("OK")
    }

    for (a in 1..5) {
        print("$a")
    }

    val array = arrayListOf<String>()
    array.add("aaa")
    array.add("bbb")
    array.add("ccc")

    if (x !in 0..array.size - 1)
        print("out: array has only ${array.size} elements. x=$x")

    //Check if a collection contains an object:
    if ("aaa" in array) // collection.contains(obj) is called
        println("Yes: array contains aaa")

    if ("ddd" in array)
        print("Yes: array constains ddd")
    else
        print("No: array doesn't contains ddd")
}

fun cases(obj: Any) {
    when (obj) {
        1 -> println("one")
        "hello" -> println("Greeting")
        is Long -> println("Long")
        !is String -> println("Not a string")
        else -> println("Unknown")
    }
}

class Pair<K, V>(val first: K, val second: V) {
    operator fun component1(): K {
        return first
    }

    operator fun component2(): V {
        return second
    }
}