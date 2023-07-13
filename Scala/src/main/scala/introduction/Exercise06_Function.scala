package introduction

object Exercise06_Function {
  // 函数
  // 函数定义语法：def 函数名(参数列表):返回值类型={函数体}
  // 函数与方法的区别：
  // 函数：一段代码的封装，可以存在任何位置
  // 方法：类中定义的函数，称为方法
  // 函数没有重载和重写的概念，方法有
  // 函数的参数列表可以具有默认值
  // 参数列表还可以包含不定长参数
  // 当需要为函数添加可变长度参数时，在参数数据类型后添加'*'表示该参数为可变长度参数
  // 一个函数只能有一个可变长参数，可变长参数只能放在参数列表的最后
  // 可变长参数的实质是一个带泛型的数组，但不能直接传入一个满足条件的数组，需要将参数打包成数组
  def unsureLongFunc(name: String, str: String*) = {

  }

  // 测试函数
  // 标准函数定义
  def test0(name: String): String = {
    print("hello world")
    return "你好！" + name
  }

  // 1、return可以省略
  def test1(name: String): String = {
    print("hello world")
    "你好！" + name
  }

  // 2、当函数体只有一行代码时，可以省略'{}'
  def test2(name: String): String = "你好！" + name

  // 3、当函数的返回值类型能够自动推断时，可以省略返回值类型
  def test3(name: String) = {
    print("hello world")
    "你好！" + name
  }

  // 4、当不关注函数的名称，只关注函数的执行逻辑，那么可以省略函数的声明关键字和函数名，但需要将'=' 改成'=>'，即形成一个匿名函数
  //    同时还需要删除函数的返回值类型
  (name: String) => {
    print("hello world")
    "你好！" + name
  }

  // ------------------------------------------------------------------------------------------------------------------
  // 5、当函数没有参数列表，在定义函数时，可以省略'()'，此时，在调用函数的时候，也不能使用'()'
  // 同理，如果使用'()'，在调用函数时，可以省略'()'，也可以不省略'()'
  def testParameter: String = {
    print("hello world")
    "hello world"
  }

  // 函数调用
  def main(args: Array[String]): Unit = {
    val res: String = testParameter
    print(res)
  }


  // 6、如果不希望函数有返回值，或者说希望函数的返回值是Unit，那么可以将'='省略，同时将函数的返回值类型也省略
  def test(name: String) {
    print("hello world")
    "你好！" + name
  }

  // 说明：
  // 以上的所有省略，相互之间没有冲突，在满足条件的情况下都可以使用
  // 对函数体使用了return关键字，那么函数必须声明函数的返回值类型
  // 对于声明函数的返回值类型，并同时使用return语句返回，需要说明的是，函数声明的返回值类型的优先级高
  // 即，当函数声明返回值类型为Unit，那么return语句的返回值无效

}
