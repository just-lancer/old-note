package introduction

object Exercise06_Function_4 {
  // 递归函数
  // 控制抽象
  // 概念：函数参数列表中，函数参数的类型不完整，只有返回值类型没有输入值类型
  //        这样的函数在调用时就不能传入函数对象，可以传入代码块
  //        在函数内部，每调用一次该函数的参数名，就相当于执行了一次该代码块
  //  1、函数的值传递：参数列表中，参数的调用是值
  //  2、函数的名传递：参数列表中，参数的调用是一段代码
  //      函数名调用次数取决于函数体中，参数名的调用次数
  //      在Scala中，每一个代码块，每一个语句都有一个返回值，默认是代码块的最后一行代码的值为代码块的值
  //      因此，对于函数的名调用会有特殊的声明格式

  //  惰性加载：当函数返回值被声明为 lazy 时，函数的执行将被推迟，直到我们首次对此取值，
  //            该函数才会执行。这种函数我们称之为惰性函数。
  //          同理，当常量被声明为lazy时，只有当首次对该常量进行调用时，该常量才会加载
  //  说明：lazy不能修饰变量，即var类型的值


  def main(args: Array[String]): Unit = {
    // 声明一个函数，包含值调用，和名调用
    def funcTest(name: String, block: => Int): Unit = {
      println("hello " + name)
      val num: Int = block
      println(num)
    }

    funcTest("name", {
      10
    })

    def fun(): Int = {
      100
    }

    funcTest("world", fun)
  }
}
