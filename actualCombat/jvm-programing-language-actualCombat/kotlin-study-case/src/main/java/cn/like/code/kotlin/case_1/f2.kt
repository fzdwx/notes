package cn.like.code.kotlin.case_1

/**
 * desc:
 * details:
 * @author:  like
 * @since:   2021/6/12 12:21
 * @email:   980650920@qq.com
 */
fun main() {
    val map = hashMapOf("a" to 1, "b" to 2)


    for ((k, v) in map) {
        println("  key : $k, val : $v")
    }

    val a = "123".apply {
        map { it + 1 }
    }
    println(" a: $a")
    println(1.shr(2))

    val list = arrayListOf(1, 2, 3, 3, 4)

    foo { print(123) }
}

fun foo(bar: Int = 1, f1: () -> Unit) {
    f1()
}