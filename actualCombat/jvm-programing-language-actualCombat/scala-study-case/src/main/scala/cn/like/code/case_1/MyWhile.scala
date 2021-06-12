package cn.like.code.case_1

import scala.annotation.tailrec

/**
 * while
 *
 * @author like 980650920@qq.com
 * @since 2021/6/12 17:26
 */
object MyWhile {

	def main(args: Array[String]): Unit = {
		var n = 10
		while (n >= 1) {
			println(n)
			n -= 1
		}
		n = 10
		myWhile(n >= 1, {
			println(n)
			n -= 1
		})
	}

	@tailrec
	def myWhile(condition: => Boolean, op: => Unit): Unit = {
		if (condition) {
			op
			myWhile(condition, op)
		}
	}
}
