//kotlin控制流程写法(类似scala) 如果我们把 if 语句当作表达式使用，那么一定要有 else 部分
fun max(a: Int, b: Int) = if (a < b) b else a

fun testWhen(x: Int): Unit {
    when (x) {
    //使用 "," 分隔多个条件
        1, 2 -> println("x = $x")
        else -> println("x is neither 1 nor 2")
    }

    //更强大的地方
    when (x) {
        !is Int -> println("x is not integer")
        in 0..10 -> println("x is between 0 and 10")
        else -> println("x is over 10")
    }

    //还可以不带参数
    when {
        x !is Int -> println("x is not Int")
        (x and 1) == 0 -> println("x is even")
        else -> println("x is odd")
    }

    //同样，还可以把when用作表达式
    val isOdd = when {
        (x and 1) == 1 -> true
        else -> false
    }
}

fun testIterator(): Unit {
    val arrayInt = intArrayOf(1, 2, 3, 4, 5)

    for (item in arrayInt) {

        println("$item")

    }

    //如果需要遍历数组或集合的索引，可以使用数组和集合的 indices 属性：
    for (i in arrayInt.indices) {

        println("arrayInt[$i] is ${arrayInt[i]}")

    }

    //如果需要遍历数组和集合的索引和值
    for ((index, value) in arrayInt.withIndex()) {

        println("arrayInt[$index] is $value")

    }

    for (i in 1..5) println(i) //输出 1 到 5 的数字

    for (i in 5 downTo 1) println(i) //输出 5 到 1 的数字

    for (i in 5 downTo 1 step 3) println(i) //输出 5 2
}

//类
class Person(name: String, password: String) {
    //如果主构造函数中定义的参数使用 val 或者 var 修饰，
    // 则会创建与这个参数同名的成员变量，并使用传入的参数值初始化这个成员变量。
    val name: String = name
    val password: String = password
}

//如果没有外部传入的参数，就使用默认值
class Person2(val id: Long, val name: String = "", val age: Int = 0)

class Person3(name: String) {
    var name = name
        set(value) {
            field = if (value.isEmpty()) "" else value[0].toUpperCase() + value.substring(1)
        }

    val isValidName
        get() = !name.isEmpty()
}

//kotlin 中lambda表达式的写法
//{ [参数: 参数类型]…… -> 函数体 }
val sum = { a: Int, b: Int -> a + b }

object HelloKotlin {
    @JvmStatic
    fun main(args: Array<String>) {
        val hello = "Hello Kotlin!"
        println(hello)
    }
}


