package cn.like.code.case2_OO

import scala.beans.BeanProperty

/**
 * @author like 980650920@qq.com
 * @since 2021/6/12 17:38
 */
class Student {

	@BeanProperty var name: String = _
	@BeanProperty var age: Int = _

	def this(name: String) {
		this()
		println("name 被调用")
		this.name = name
	}

	def this(name: String, age: Int) {
		this(name)
		println("age 被调用")
		this.age = age
	}

	println("主 被调用")

	def test(): Unit = {
		println("类中定义的方法")
	}
}

object M {
	def main(args: Array[String]): Unit = {
		val s1 = new Student
	}
}
