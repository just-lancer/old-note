package introduction

import scala.collection.mutable


object Exercise08_Collection03_Set {
  def main(args: Array[String]): Unit = {
    // Set学习
    // Set集合，特点是无序不重复
    // Set也分为不可变Set集合和可变Set集合，默认条件下，Set都是不可变集合，可变Set集合需要添加包名以区分，即mutable.Set
    //    func1()
    func2()
  }

  def func1(): Unit = {
    // 不可变Set集合
    // 创建Set集合
    val set1: Set[Int] = Set(3, 2, 4, 1, 2, 34, 4, 2, 2)
    println(set1)

    // 向Set集合添加元素，调用+()函数
    // 由于是不可变集合，所以添加元素后会创建新的集合
    val set2: Set[Int] = set1.+(100)
    val set3: Set[Int] = set1 + 200
    println(set2)
    println(set3)

    // 将一个Set集合的元素添加到另一个集合中，调用++()函数
    val set4: Set[Int] = Set(1, 2, 3)
    val set5: Set[Int] = Set(4, 5, 6)
    val set6: Set[Int] = set4.++(set5)
    val set7: Set[Int] = set4 ++ set5
    println(set6)
    println(set7)

    // 删除元素，调用-()函数，用以删除指定元素
    val set8: Set[Int] = set6.-(4)
    val set9: Set[Int] = set6 - 5
    println(set8)
    println(set9)

  }

  def func2(): Unit = {
    // 可变Set集合，需要添加包名用以区分可变集合和不可变集合
    // 创建可变Set集合
    val set1: mutable.Set[Int] = mutable.Set(1, 2, 3)
    println(set1)

    // 向Set集合中添加元素，调用+=()函数，或者调用add()函数，这两者都只能一次添加一个元素
    // 需要说明的是，+()函数对不可变和可变的Set集合都适用，都能添加元素，但都会将添加后的结果创建一个新的Set集合
    // 所以可变Set集合通常不使用+()函数
    set1.+=(4)
    println(set1)

    set1.add(5)
    set1.add(6)
    println(set1)

    // 删除元素，调用-=()函数，或者remove()函数，同样，二者都只能一次删除一个元素
    set1.-=(6)
    set1.remove(5)

    // 合并两个集合，调用++=()函数，
    // ++()函数能够将两个集合的元素合并，但是会创建新的Set集合
    val set2: mutable.Set[Int] = mutable.Set(1, 2, 3)
    val set3: mutable.Set[Int] = mutable.Set(4, 5, 6)
    set2.++=(set3) // 将set3中的元素添加到set2中
    println(set2)
    println(set3)

  }

}
