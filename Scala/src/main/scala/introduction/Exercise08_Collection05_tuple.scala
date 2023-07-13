package introduction

object Exercise08_Collection05_tuple {
  def main(args: Array[String]): Unit = {
    // 元组的学习
    func1()
  }

  def func1(): Unit = {
    // 元组也是可以理解为一个容器，可以存放各种相同或不同类型的数据。说的简单点，就是将多个无关的数据封装为一个整体，称为元组
    // 元组中最大只能有22个元素
    // 创建一个元组
    val tuple1: (String, Int, Boolean, Char) = ("haha", 1, false, 'h')
    println(tuple1)

    // 获取元组的值
    // 方式一：_i，i表示第i个元素，而不是索引
    val element1 = tuple1._1
    println(element1)

    // 方式二：利用索引获取元组元素的值，调用productElement(Int index)函数
    val element2 = tuple1.productElement(1)
    println(element2)

    // 方式三：利用迭代器获取元组元素的值
    // 不同于其他集合的迭代器获取方式，元组的迭代器获取通过调用productIterator函数
    val iter = tuple1.productIterator
    while (iter.hasNext) {
      print(iter.next() + " ")
    }

    // 不可以通过for循环遍历元组元素

    // 也不可以通过增强for循环遍历元组元素

  }

}
