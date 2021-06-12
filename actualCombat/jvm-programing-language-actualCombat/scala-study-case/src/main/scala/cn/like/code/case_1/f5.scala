package cn.like.code.case_1

import scala.annotation.tailrec

/**
 *
 * 递归
 *
 * @author like 980650920@qq.com
 * @since 2021/6/12 17:04
 */
object f5 {

	def main(args: Array[String]): Unit = {
		println(f(5))
	}

	// 尾递归  5,1 = > 4,5 = > 3,20 = > 2,60 => 1,120 = > 120
	@tailrec
	def f(n: Int, last: Int = 1): Int = {
		if (n <= 1) {
			return n * last
		}
		return f(n - 1, n * last)
	}
}
