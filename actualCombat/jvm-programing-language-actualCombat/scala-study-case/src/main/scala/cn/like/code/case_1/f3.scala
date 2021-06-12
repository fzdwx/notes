package cn.like.code.case_1

/**
 * 高阶函数 针对集合
 *
 * @author like 980650920@qq.com
 * @since 2021/6/12 16:24
 */
object f3 {

	def main(args: Array[String]): Unit = {
		val arr = Array.apply(12, 44, 55, 11)

		// 对每个元素+1
		val addOneArrays = arrayOperation(arr, (_ + 1))
		println(addOneArrays.mkString(" "))

		// 乘2
		println(arrayOperation(arr, (_ * 2)).mkString(" "))



	}

	// 对传入的array数组进行操作，把操作过程抽象出来，处理完毕后返回一个新的数组
	def arrayOperation(array: Array[Int], op: Int => Int): Array[Int] = {
		for (e <- array) yield op(e)
	}

	def f1(i: Int): String => (Char => Boolean) = {
		f2 =>
			f3 =>
				if (i == 0 && f2 == "" && f3 == '0')
					false else true
	}

	def f2(i:Int)(s:String)(c:Char):Boolean={
		if (i == 0 && s == "" && c == '0')
			false else true
	}
}
