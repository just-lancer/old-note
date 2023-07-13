package introduction

import scala.io.StdIn

object Exercise02 {
  def main(args: Array[String]): Unit = {
    // 键盘输入
    // keyBoardIn()
    // 从文件从读入
  }

  // 键盘输入
  def keyBoardIn(): Unit = {
    // Scala输入与输出
    // 从键盘写入，需要调用Scala的标准输入输出类库StdIn
    println("请输入你的姓名：")
    val name: String = StdIn.readLine()
    println("请输入你的年龄：")
    val age: Int = StdIn.readInt()

    println(s"姓名：${name}，年龄：${age}")
  }

  // 从文件中输入
  //  def fileIn()
}
