package inaction

/**
 * Created by lichao on 2017/5/26.
 */

data class Person(val name: String, val age: Int? = null)

fun main() {
    val persons = listOf(Person("Alice"), Person("Bob", age = 29))
    print(persons)
}