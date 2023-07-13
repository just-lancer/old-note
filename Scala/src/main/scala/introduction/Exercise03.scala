package introduction

import scala.io.StdIn

object Exercise03 {
  def main(args: Array[String]): Unit = {
    // 条件判断
    // if[ -else if -else]
    println("请输入一个整数：")
    val num: Int = StdIn.readInt()

    if (num < 10) {
      println("该数字小于10")
    } else if (num > 10) {
      println("该数字大于10")
    } else {
      println("该数字是10")
    }
  }


}
