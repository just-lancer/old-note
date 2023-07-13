package introduction

object Exercise06_Function_1 {
  // 定义一个基本函数
  def fun(name: String): String = {
    println("调用fun函数")
    return "hello" + name
  }

  def main(args: Array[String]): Unit = {
    // 函数的高级用法
    // 1、作为对象使用
    func1()

    // 2、因为函数能够作为对象使用，所以函数能够作为参数传入到一个函数中
    // func2()

    // 3、因为函数能够作为对象使用，所以函数也能够作为函数的返回值使用
    // 函数作为另一个函数的返回值被返回后，该怎样使用？
    //    ① 可以作为对象，赋值给变量或常量，直接打印(看看类型)
    val ret: (String) => String = func3()
    println(ret)
    //    ② 赋值给变量后，进行函数调用，或者不赋值给变量，直接进行函数调用
    //      当函数作为对象赋值给变量或者常量后，此变量或者常量就变成了一个函数，可以直接在后面添加括号进行函数调用
    println(ret("scala"))
    println(func3()("world"))
  }

  def func1(): Unit = {
    // 1、作为对象使用
    //    函数调用
    fun("world")
    //    将函数作为一个对象赋给一个变量，变量的数据类型进行自动推导
    //    将函数作为一个对象进行操作的格式为：函数名 + 至少一个空格 + 下划线'_'
    // 对空格的说明：不加空格的话，那么函数名和下划线共同构成了函数名，此时就是函数调用，而不是将函数作为一个对象进行调用
    val varFun = fun _
    println(varFun)

    // 对变量或函数对象的数据类型说明：
    //    函数作为对象的数据类型的格式为：输入数据的数据类型 => 输出(返回值)数据类型，即：(参数列表中的类型) => 返回值数据类型
    //    所以varFun的数据类型是：(String) => String
    //    当明确知道函数对象的数据类型后，可以不使用下划线也能够调用函数对象
    val varFunType: (String) => String = fun
    println(varFunType)
  }

  def func2(): Unit = {
    // 2、函数作为参数在传入到一个函数中
    // 需求：定义一个函数，对两个数进行计算，计算方式未定，根据传入的函数参数进行
    def calculate(a: Int, b: Int, f: (Int, Int) => Int): Int = {
      f(a, b)
    }

    // 编写一个求和函数，并作为参数传入calculate函数中，进行计算
    def add(x: Int, y: Int): Int = {
      x + y
    }

    // 实现计算
    val res = calculate(10, 20, add)
    println(res)
  }

  def func3(): String => String = {
    // 函数对象作为另一个函数的返回值使用
    // 定义需要被返回函数的对象
    def returnFunc(string: String): String = {
      "hello, " + string
    }

    returnFunc
  }

}
