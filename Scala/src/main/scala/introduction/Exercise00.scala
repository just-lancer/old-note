package introduction

object Exercise00 {
  def main(args: Array[String]): Unit = {
    val a: Int = 10
    var b: String = "123"
    b = "456"

    println(a)
    println(b)

    var stu: Student = new Student("zhangsan", 18)
    println(stu.toString())

    var per1: Person = new Person()
    val per2: Person = new Person()

    per1.name = "lisi"

    println(per1)
    println(per2)
    println(per1.name)

  }

  class Person {
    var name: String = "zhangsan"
    var age: Int = 18

  }

}
