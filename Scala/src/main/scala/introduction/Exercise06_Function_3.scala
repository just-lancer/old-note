package introduction

object Exercise06_Function_3 {
  // 闭包和柯里化
  // 闭包：函数式编程的标配，如果一个函数访问了他的外部(局部)变量的值，那么这个函数和它所处的环境，成为闭包
  // 函数柯里化，把一个参数列表的多个参数，变成多个参数列表，每个参数列表中只包含一个参数

  // 求两数之和
  def add1(a: Int, b: Int): Int = {
    a + b
  }

  // 柯里化，求两数之和
  def add2(a: Int)(b: Int): Int = {
    a + b
  }

}
