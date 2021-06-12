package cn.like.code.case_1

/**
 * 控制抽象
 *
 * @author like 980650920@qq.com
 * @since 2021/6/12 17:19
 */
object f6 {

	def main(args: Array[String]): Unit = {
		func1({
			println("controller")
			1
		})
	}

	// 传入一个 相当于匿名函数
	def func1(a: => Int): Unit = {
		println(s" a  : $a")
		println(s" a  : $a")
	}
}
