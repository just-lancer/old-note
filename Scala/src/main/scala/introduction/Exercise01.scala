package introduction

object Exercise01 {
  def main(args: Array[String]): Unit = {
    // 字符串的输出模式
    val name: String = "zhangsan"
    val age: Int = 18
    val money: Double = 333.4

    // 拼接字符串输出，和Java中的一样，
    val strRes1: String = "姓名：" + name + "，年龄：" + age + "，钱包有：" + money
    println(strRes1)

    // 占位符的使用，和C语言中相似
    printf("姓名：%s，年龄：%d，钱包有：%1.3f", name, age, money)

    println()
    // 字符串模板，Scala中特有的
    // 说明
    // Scala 模板字符串的前缀不同，效果也是不同的
    // s，表示${}仅用于获取变量的值
    // f，表示${}可以对数值型变量进行数据格式化输出
    //raw，表示${}无视转义字符，将""中的所有内容都输出
    val strRes3: String = s"姓名：${name}，年龄：${age}，钱包有：${money}"
    val strRes4: String = f"姓名：${name}，年龄：${age}，钱包有${money}%5.4f"
    val strRes5: String = raw"姓名：${name}，年龄：${age}，钱包有${money}%5.4f"

    println(strRes3)
    println(strRes4)
    println(strRes5)

    // 多行字符串输出
    // 利用三引号将需要输出的内容包括起来，就可以实现多行输出
    // 需要说明的是，三引号包括的所有内容都将输出，因此在代码编辑时，为了
    // 美观而添加的转义字符，\n，\t等都会输出，所有会出现，输出结果没有对齐
    // 利用字符串的stripMargin可以进行格式化，
    // stripMargin默认以'|'为分隔符
    val str: String =
    """aganzuo
      |xilan
      |buwanjia
      |baen
      """.stripMargin
    println(str)
  }

}
