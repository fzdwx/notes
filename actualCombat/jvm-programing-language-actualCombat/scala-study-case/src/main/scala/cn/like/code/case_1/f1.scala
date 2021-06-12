package cn.like.code.case_1

/**
 * @author like 980650920@qq.com
 * @since 2021/6/12 15:26
 */
object f1 {

	def main(args: Array[String]): Unit = {

		val name = "like"
		sayHi(name)


		println(sum(a = 10, b = 20))


	}


	/**
	 * 方法
	 *
	 * @param name 的名字
	 * @return `String`
	 */
	def sayHi(name: String): String = name + " 你好"

	/**
	 * 总和
	 *
	 * @param a 一个
	 * @param b b
	 * @return int
	 */
	def sum(a: Int, b: Int): Int = a + b
}
