package cn.like.code.case_1

/**
 * 闭包 和 函数柯里化
 *
 * @author like 980650920@qq.com
 * @since 2021/6/12 16:45
 */
object f4 {

	def main(args: Array[String]): Unit = {


		val addBy2 = add2(2)
		println(addBy2(4))
	}

	def add(i: Int, j: Int): Int = i + j

	// 固定一个加数
	def add(i: Int): Int = 5 + i;

	// 使用闭包
	def add2(i: Int): Int => Int = {
		i + _
	}

	// 柯里化
	def addCurring(i: Int)(j: Int): Int = i + j
}
