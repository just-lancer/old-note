package introduction

import scala.collection.mutable.ArrayBuffer

object Exercise08_Collection01_Array {

  /*
    Scala集合学习
    Scala集合体系
      > Scala集合有三大类，序列Seq、集Set、映射Map，所有的集合都扩展自Iterable特质
      > 几乎所有的集合类，Scala都同时提供了可变和不可变的版本，分别由不同的包提供
          -- 不可变集合：scala.collection.immutable
          -- 可变集合：scala.collection.mutable
   */

  def main(args: Array[String]): Unit = {
    //    func1()
    //    func2()
    func3()

  }

  def func1() {
    // 以一维数组为例，进行学习
    // 数组：是具有索引的有顺序的序列

    // 不可变数组
    // 创建数组
    // 方式一：
    val arr1: Array[Int] = new Array[Int](10)
    println(arr1) // [I@71c7db30
    // 方式二：
    val arr2: Array[Int] = Array(1, 2, 3, 44, 5, 613, 51, 51, 4234, 542)
    println(arr2) // [I@19bb089b

    // 给数组元素赋值，修改元素的值
    arr1(1) = 100
    arr2(3) = 300
    // 调用方法给某个元素重新赋值
    arr1.update(1, 900)

    // println(arr1.mkString(","))
    // println(arr2.mkString(","))

    // 遍历数组
    // 方式一：普通for循环，根据索引遍历
    for (i <- 0 to arr1.length - 1) {
      print(arr1(i) + " ")
    }

    println("")

    // 方式二：普通for循环，直接遍历元素
    for (i <- arr1) {
      print(i + " ")
    }
    println("")

    // 方式三：增强for循环，数组对象调用foreach方法
    arr1.foreach((i: Int) => print(i + 1 + " "))


    // 添加元素，由于数组是不可变数组，所以添加元素之后，会产生新的数组
    // 在数组末尾添加元素
    // val arr3: Array[Int] = arr1.:+(4000)
    // Scala中，对于符号类型的函数调用，点号调用可以省略点号，又由于参数只有一个，所以可以省略括号
    val arr3: Array[Int] = arr1 :+ 3000
    // 在数组开头添加元素
    // val arr4:Array[Int] = arr1.+:(4000)
    // Scala中，如果省略点号调用函数，如果函数名中有冒号(即方法都是运算符构成)，并且以冒号结尾
    // 那么函数的调用就不是前面是对象，后面是参数，而是前面是参数后面是对象
    // 原因是当符号函数以冒号结尾，函数的调用是从后向前，从右向左进行
    val arr4: Array[Int] = 49 +: arr1

  }

  def func2(): Unit = {
    // 可变数组

    // 创建可变数组
    // 方式一：创建可变数组并指定数组的初始长度
    // 说明：创建可变数组时，在不指定数组长度条件下，默认数组长度为16
    // 数组的length属性描述的是数组当前已有元素的个数，而不是数组占据的“内存空间”的个数
    // 虽然已经指定了数组的长度，但是仍然是个空的数组，只是说当数组的元素个数超过了指定的长度后，开始扩容
    val arr1: ArrayBuffer[Int] = new ArrayBuffer[Int](10)
    println(arr1)

    // 方式二：
    val arr2: ArrayBuffer[Int] = ArrayBuffer[Int](1, 4, 34, 2, 42, 4)
    println(arr2)

    // 获取数组元素的值：与不可变数组的方式一样
    println(arr2(2))

    // 改变数组元素的值
    arr2(3) = 100
    arr2.update(5, 200)
    println(arr2)

    // 向数组中添加元素，分为从末尾添加和从头添加
    // 从末尾添加：调用+=()函数或者append()函数
    arr1.+=(9)
    arr1.append(8)

    // 从头开始添加：调用+=:()函数或者prepend()函数
    arr1.+=:(10) // 或者
    10 +=: arr1
    arr1.prepend(100)
    println(arr1)

    // 向指定的位置添加元素，调用insert()函数
    arr1.insert(2, 999)

    // 向指定位置上添加另一个数组的全部元素，调用insertAll()函数
    // 说明：insert()函数会将一个数组当成一个整体，作为一个元素添加
    arr2.insertAll(4, arr1)
    println(arr2)


    // 遍历数组，方式和不可变数组相同
    // 方式一：普通for循环，根据索引进行遍历
    for (i <- 0 to arr2.length - 1) {
      print(arr2(i) + " ")
    }
    println("")

    // 方式二：普通for循环，直接遍历元素
    for (i <- arr2) {
      print(i + " ")
    }
    println("")

    // 方式三：增强for循环，调用foreach()函数
    arr2.foreach((i: Int) => print(i + " "))
    println(" ")

    // 方式四：迭代器
    // Scala中，迭代器是集合属性，不是通过方法调用获得的
    val iter = arr2.iterator
    while (iter.hasNext) {
      print(iter.next() + " ")
    }
    println()

    // 删除元素，调用remove()函数
    // 删除一个元素，删除的是指定索引位置上的元素，调用remove(Int index)
    arr2.remove(3)
    // 删除一组元素，删除的是指定索引位置开始，后面连续的元素，调用remove(Int index, Int count)
    arr2.remove(3, 2)
    arr2.foreach(i => print(i + " "))

    // 需要说明的是，只有可变数组具有remove方法

  }

  // 可变数组与不可变数组之间的转换，以及多维数组
  def func3(): Unit = {
    // 可变数组与不可变数组之间的转换
    val arr1: Array[Int] = new Array[Int](3)
    arr1(0) = 10
    arr1(1) = 20
    arr1(2) = 30

    val arr2: ArrayBuffer[Int] = new ArrayBuffer[Int](10)
    // 添加元素
    arr2.+=(100)
    arr2.+=(200)
    arr2.+=(300)
    arr2.+=(400)
    println(arr2.mkString(", "))

    // 可变数组转换成不可变数组，调用可变数组的toArray()函数，如果没有参数的话，不要添加括号
    val arr3: Array[Int] = arr2.toArray
    // 不可变数组转换成可变数组，调用toBuffer函数
    val arr4 = arr1.toBuffer

    println(arr1.mkString(", "))
    println(arr2.mkString(", "))
    println(arr3.mkString(", "))
    println(arr4.mkString(", "))

    // 多维数组
    // 创建二维数组。Scala编程语言已经定义了一种内置方法ofDim()来创建多维数组 。
    // 使用这种方法，可以制作一个高达5维的阵列。但是，使用此方法创建数组时，应在创建时精确行数和列数。
    // 首先需要使用所需的行数和列数创建数组，然后用元素值填充数组。
    // 方式一：调用Array的ofDim()函数
    val arrD1 = Array.ofDim[Int](3, 2) // 创建了一个3行2列的二维数组
    // 方式二：数组的数组即是二维数组
    val arrD2: Array[Array[Int]] = new Array[Array[Int]](4) // 创建了一个数组，有4个类型是Array[Int]的元素，这些元素都是null，还需要赋值
    // 这种多维数组创建方式能够创建不规整的多维数组

    println(arrD1.mkString(", ")) // [D@67b64c45, [D@4411d970, [D@6442b0a6
    println(arrD2.mkString(", ")) // null, null, null, null

    // 为多维数组赋值
    arrD1(0) = Array(1, 2)
    arrD1(1) = Array(3, 4)
    arrD1(2) = Array(5, 6)
    println(arrD1.mkString(", "))

    // 遍历多维数组
    // 普通for循环，双重for循环，根据索引
    for (i <- 0 to arrD1.length - 1; j <- 0 to arrD1(i).length - 1) {
      print(arrD1(i)(j) + " ")
    }

    for (i <- arrD1) {
      for (j <- i) {
        print(j + ", ")
      }
    }
    println()

    // 迭代器
    val iter = arrD1.iterator
    while (iter.hasNext) {
      val iter1 = iter.next().iterator
      while (iter1.hasNext) {
        print(iter1.next() + ", ")
      }
      println()
    }

    println()

    // 增强for循环，调用foreach()函数
    arrD1.foreach(i => i.foreach(j => print(j + ", ")))
  }


}
