package cn.like.code.kotlin

import kotlin.collections.ArrayList

/**
 *
 * @author like
 * @date 2021/5/13 9:32
 */
class Hello {
    
    
    fun hello(name: Any) = "hello:$name"
    
    
    fun add(a: Int, b: Int) = a + b
    
    fun maxOf(a: Int, b: Int) = if (a > b) a else b
    fun maxOf2(a: Int, b: Int) = a > b
}

fun main() {
    val array = arrayOf(1, 2, 3, 4, 5)
    println(array.contentToString())
    val sliceArray = array.sliceArray(IntRange(2, 4))
    
    println(sliceArray.contentToString())
    
    val test = "null";
    test.run {
        println(Thread.currentThread())
        println("123")
    }
    println(IntArray(10).apply { plus(1) }.contentToString())
    
    val list = object : ArrayList<Int>(10){
        init {
            add(123)
            add(123)
        }
    }
    
    println(list.filter { i -> i > 0 }.toString())
}

fun arrayOfMinusOnes(size: Int): IntArray {
    return IntArray(size).apply { fill(-1) }
}

data class Person(val name: String, val age: Int)

