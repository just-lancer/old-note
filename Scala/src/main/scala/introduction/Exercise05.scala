package introduction

import scala.util.control.Breaks

object Exercise05 {
  def main(args: Array[String]) = {
    //循环结构：for循环，while循环
    // 基本for循环
    // forLooop01(7, 8)

    // 循环守卫
    // forLoop02()

    // 循环步长控制
    forLoop03()

    // 嵌套循环
    // forLoop04()

    // while循环
    // whileLoop01()

    // do...while循环
    // doWhileLoop01()

    // 循环中断
    // Scala是函数式编程，没有continue和break等循环控制关键字
    // 对于continue，可以利用循环守卫实现
    // 对于break，可以手动写break实现循环中断，也可以使用Breaks类中的方法实现
    // 即，首先给需要跳出循环的代码打个标记：将循环代码作为参数传递给Breaks.breakable()方法
    // 当满足结束循环的条件时，调用Breaks.break()即可
    //    loopBreak()
  }

  // 基本for循环
  def forLooop01(m: Int, n: Int): Unit = {
    // Scala中的for循环结构不同于Java，和python相似
    // 都是对一个集合(python是迭代器)进行遍历，将其中的值都依次进行赋给循环变量
    // to：左闭右闭
    // until：左闭右开
    //    for (i <- 1 to m) {
    for (i <- 1 until m) {
      for (j <- 1 until n) {
        print("*")
      }
      print("\n")
    }
  }

  // 循环守卫
  def forLoop02(): Unit = {
    for (i <- 1 to 10 if i != 5) {
      print(i + "\t")
    }

  }

  // 循环步长控制
  def forLoop03(): Unit = {
    for (i <- 1 to 20 by 3) {
      print(i + "\t")
    }

    println("")

    // 逆向遍历
    for (i <- 1 to 20 by 3 reverse) {
      print(i + "\t")
    }

  }

  // 嵌套循环
  // 区别于Java中的嵌套循环，Scala的嵌套循环有优化
  def forLoop04(): Unit = {
    // 打印九九乘法表
    //    for (i <- 1 to 9) {
    //      for (j <- 1 to i) {
    //        print(s"${i} * ${j} = ${i * j} \t")
    //      }
    //      println("")
    //    }

    // 嵌套循环
    for (i <- 1 to 9; j <- 1 to i) {
      print(s"${j} * ${i} = ${i * j} \t")
      if (i == j) {
        println("")
      }
    }
  }

  // while循环，do...while循环和Java一样
  def whileLoop01(): Unit = {
    var isflag: Boolean = true
    var i: Int = 1
    while (isflag) {
      if (i != 10) {
        print(i + "\t")
      } else {
        isflag = false
      }
      i += 1
    }
  }

  // do...while循环，和Java一样
  def doWhileLoop01(): Unit = {
    var i: Int = 1
    do {
      print(i + "\t")
      i += 1
    } while (i != 10)
  }

  // 循环中断
  def loopBreak(): Unit = {
    Breaks.breakable(
      for (i <- 1 to 10) {
        if (i != 5) {
          print(i + "\t")
        } else {
          Breaks.break()
        }
      }
    )
  }

}
