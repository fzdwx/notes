package cn.like.code.case_1

/**
 * 匿名函数
 *
 * @author like 980650920@qq.com
 * @since 2021/6/12 15:51
 */
object f2 {

	def main(args: Array[String]): Unit = {
		f(println)

		doubleNumber((a, b) => a + b)
		doubleNumber(_ + _)

		doubleNumber((_ + _), 11, 22)
	}


	def f(func: String => Unit): Unit = {
		func("123")
	}

	def doubleNumber(op: (Int, Int) => Int): Int = {
		op(1, 2)
	}

	def doubleNumber(op: (Int, Int) => Int, a: Int, b: Int): Int = op(a, b)
}

