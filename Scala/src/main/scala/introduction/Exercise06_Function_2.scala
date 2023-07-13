package introduction

object Exercise06_Function_2 {
  // 匿名函数的学习
  // 没有函数名的函数就是匿名函数
  // 匿名函数的语法格式：(参数列表) => {函数体}
  // 是的，没错，匿名函数没有函数的返回值类型

  def main(args: Array[String]): Unit = {
    // 匿名函数的使用
    // 1、匿名函数赋值给变量后，相当于给匿名函数取了个名字，名字就是变量名，那么变量名现在就是函数的名字
    //    而变量的数据类型的确定有两种方式，一是自动类型推断，二是，根据函数体，确定函数对象的数据类型
    val fun: String => String = (name: String) => {
      "hello " + name
    }

    val res = fun("scala")
    println(res)
  }

  // 2、匿名函数也是一个函数对象，所以一般函数具有的使用方法，匿名函数都有

  // 函数的分层调用
  // 需求：定义一个函数 func，它接收一个Int类型的参数，返回一个函数（记作 f1）。
  // 它返回的函数 f1，接收一个 String 类型的参数，同样返回一个函数（记作 f2）。
  // 函数 f2 接收一个 Char 类型的参数，返回一个 Boolean 的值。
  // 要求调用函数 func(0) (“”) (‘0’)得到返回值为 false，其它情况均返回 true

  def func(i: Int): String => Char => Boolean = {
    def f1(s: String): Char => Boolean = {
      def f2(c: Char): Boolean = {
        if (i == 0 && s == "" && c == '0') false else true
      }

      f2
    }

    f1
  }

  // 测试：函数调用
  println(func(0)("")('0'))
  println(func(0)("")('1'))

  // 匿名函数的简化
  // 1、如果函数逻辑只有一行代码，可以省略大括号
  // 2、如果函数的参数只有一个或者没有，那么参数列表可以省略，同时需要省略"=>"
  // 3、如果参数的类型可以推断出来，那么参数的类型可以省略
  // 4、如果参数按照顺序只使用了一次，参数可以使用下划线代替
  def func1(i: Int): String => Char => Boolean = {
    s => c => if (i == 0 && s == "" && c == '0') false else true
  }

  def funcx(i: Int): String => Char => Boolean = {
    (s: String) => (c: Char) => if (i == 0 && s == "" && c == '0') false else true

  }
}



