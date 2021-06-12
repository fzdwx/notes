package cn.like.code.kotlin.case_1

/**
 * desc:
 * details:
 * @author:  like
 * @since:   2021/6/12 14:08
 * @email:   980650920@qq.com
 */
fun main(args: Array<String>) {

    val numbers = listOf(5, 2, 10, 4)
    val s1 = ""



    println(numbers.reduce { acc, i -> acc - i })
}