package introduction

import scala.collection.mutable.ListBuffer

object Exercise08_Collection02_List {
  def main(args: Array[String]): Unit = {
    // 列表：有顺序的序列，区别于数组，没有索引
    // 列表也分为不可变列表和可变列表，同样，不同于数组，在不可变列表中，无法对列表直接操作
    //
    //    func1()
    func2()
  }

  def func1(): Unit = {
    // 以一维列表为例
    // 不可变列表
    // 创建不可变列表，不同于数组，创建不可变列表只能调用伴生对象进行创建。如果要添加数据类型，那么需要带上泛型
    // 以后只要是添加数据类型，能带泛型都带上泛型
    val list1: List[Int] = List[Int](4, 5, 6)
    println(list1)

    // 空列表：Nil
    // 这是一个不可变列表
    val list2: List[Int] = Nil
    println(list2)

    // 获取列表中的元素的值，List对数据进行了优化，虽然没有索引，但是可以利用类似索引的方式获取元素的值，但不能对元素的值进行修改
    val value1: Int = list1(1)
    //    list1(1) = 19 // 直接报错
    println(value1)

    // 向列表中添加元素，分为末尾添加和开头添加
    // 值得注意的是，List是不可变列表，所以添加元素都不是在原来的基础上添加，而是会创建新的List
    // 并且元素的添加都是一个一个的添加，如果一次添加多个会变成二维列表
    // 末尾添加元素，调用:+()函数
    val list3: List[Int] = list1.:+(20)
    val list4: List[Int] = list1 :+ 30
    println(list3)
    println(list4)

    // 开头添加元素，调用+:()函数
    val list5: List[Int] = list3.+:(49)
    val list6: List[Int] = 33 +: list3
    println(list5)
    println(list6)

    // 开头添加元素，调用::()函数
    val list7: List[Int] = Nil.::(10)
    val list8: List[Int] = 23 :: Nil
    println(list7)
    println(list8)

    println("=====================")
    // 向一个列表中添加另一个列表
    // 将列表作为一个整体添加，调用普通的追加函数，末尾追加，调用：:+()函数；开头添加，调用：+:()函数，或者，::()函数
    val list10: List[Int] = List(3, 4, 5, 6)
    val list11: List[Int] = List(100, 200)
    // 尾添加
    val list12: List[Any] = list10.:+(list11)
    println(list12)
    //头添加
    val list13: List[Any] = list10.+:(list11)
    val list14: List[Any] = list10.::(list11)
    println(list13 + "13")
    println(list14 + "14")

    println("================")
    // 将列表中的元素一个个添加到另一个列表中
    // 头添加，调用:::()函数
    // 没有尾添加，如果需要尾添加，改变两个列表在:::()函数两侧的顺序就可以了
    val list15: List[Int] = list10.:::(list11)
    val list16: List[Int] = list11 ::: list10
    println(list15)
    println(list16)

    // 还有一个函数，++()函数，是尾添加，底层是调用了:::()函数，只是对顺序进行了调整
    val list17: List[Int] = list10.++(list11)
    println(list17)
    println("===================")

    // 遍历不可变列表
    // 方式一：普通for循环，利用索引遍历
    for (i <- 0 to list17.length - 1) {
      print(list17(i) + " ")
    }
    println()

    // 方式二：普通for循环，直接遍历值
    for (i <- list17) {
      print(i + " ")
    }
    println()

    // 方式三：迭代器
    val iter: Iterator[Int] = list17.iterator
    while (iter.hasNext) {
      print(iter.next() + " ")
    }
    println()

    // 方式四：增强for循环，直接调用foreach()函数
    list17.foreach(i => print(i + " "))

  }

  def func2(): Unit = {
    // 可变列表
    // 创建可变列表
    // 方式一：创建可变列表
    val list1: ListBuffer[Int] = new ListBuffer[Int]() // 无法指定列表长度
    // 方式二：创建可变列表并赋初始化值
    val list2: ListBuffer[Int] = ListBuffer(100, 200)
    println(list1)
    println(list2)

    println("=========")
    // 添加元素
    // 说明：在不可变列表中调用的元素添加的函数，例如+:()，:+()，都不适用于可变列表，因为调用他们都会创建新的列表
    // 而::()，:::()，++()等函数都不是可变列表中的函数，都不能使用

    // 区别于不可变数组添加元素只能一个一个添加，在可变列表中，添加元素能够一次性添加多个，而不会产生多维列表
    // 在列表末尾添加元素，调用append()函数，或者调用+=()函数
    list2.append(10)
    list2.append(20, 30)
    list2.+=(76)
    list2.+=(88, 99)
    println(list2)

    // 在列表开头添加元素，调用prepend()函数，或者调用+=:()函数
    list2.prepend(100)
    list2.prepend(200, 300)
    list2.+=:(242)
    list2.+=:(78) // 不同于其他添加元素的函数，头添加函数+=:()只能添加一个元素
    println(list2)

    // 在指定位置插入元素，调用insert(n: Int, elems: A*)函数
    list2.insert(2, 45, 65) // insert()函数的第一个参数是索引位置，第二个参数是可变参数，是需要插入的值
    println(list2)

    // 在指定位置插入另一个列表中的所有元素，调用insertAll(n: Int, seq: Traversable[A])函数
    val list4: ListBuffer[Int] = ListBuffer(42, 2141) // 同样，insertAll()函数的第一个参数是索引位置，第二个参数是seq集合
    list2.insertAll(1, list4)
    println(list2)

    println("==================")
    // 合并两个列表中的所有元素，形成一个新的列表，调用++()函数
    // 将一个列表的所有元素都添加到另一个列表中，不产生新的列表，调用++=()函数
    val list5: ListBuffer[Int] = ListBuffer(1, 2, 3)
    val list6: ListBuffer[Int] = ListBuffer(4, 5, 6)
    // 调用++()函数
    val list7: ListBuffer[Int] = list5.++(list6) // 将list6的所有元素添加到list5的末尾，并创建一个新的列表
    println(list7)
    list5.++=(list6) // 将list6的所有元素添加到list5的末尾，list5发生改变
    println(list5)

    println("==============")
    // 修改元素的值
    // 方式一：直接修改
    val list8: ListBuffer[Int] = ListBuffer(1, 2, 3)
    list8(1) = 10
    // 方式二：调用update()函数
    list8.update(2, 100)
    println(list8)

    println("==========")
    // 删除元素
    val list9: ListBuffer[Int] = ListBuffer(1, 2, 3, 4, 3, 4, 5, 6, 7, 7, 8)
    // 方式一：调用-=()函数，该函数是根据元素值进行删除，遇到具有多个相同元素的值，只会删除第一个元素
    list9.-=(1)
    // 方式二：调用remove()函数，该函数根据元素的索引进行删除
    list9.remove(8)
    println(list9)
    // 方式三：调用remove(n: Int, count: Int)函数，删除一波元素
    list9.remove(3, 2) // 同样，第一个函数参数的值为索引，第二个参数表示需要删除多少个元素
    println(list9)


  }

}
