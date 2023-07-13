package introduction

import scala.collection.mutable

object Exercise08_Collection04_Map {
  def main(args: Array[String]): Unit = {
    // Map学习
    // Scala中的Map与Java中的Map类似，也是存储键值对，区别在于，Java中key和value使用":"连接，Scala中使用"->"连接
    // Scala中Map也分为可变和不可变，并且Scala中的Map默认是不可变，如果需要使用可变Map，那么需要添加包名mutable进行区分
    //    func1()
    func2()
  }


  def func1(): Unit = {
    // 创建不可变Map
    val map1: Map[String, Int] = Map("hello" -> 2, "world" -> 3, "scala" -> 4)
    println(map1)

    // 获取Map中的所有key
    // 方式一：调用keys函数，返回的结果是一个可迭代对象，具体来说是一个Set集合
    // 函数keys定义时，没有括号，所以调用的时候也没有括号
    val iter1: Iterable[String] = map1.keys
    println(iter1)
    // 方式二：调用keySet函数，返回的结果是一个Set集合
    // 这也说明了Map的key是不能重复的
    val set1: Set[String] = map1.keySet
    println(set1)

    //方式三：调用keysIterator函数，返回结果是一个迭代器对象
    val iter2: Iterator[String] = map1.keysIterator
    println(iter2)

    // 获取Map中的value值，需要根据key值来确定value值
    // 方式一：Map对象调用get(String key)函数，此时返回的是一个Option对象，并不是一个直接的值
    // Option对象只有两个值，Some和None，Some表示Map对象根据key获取value时，此键值对存在；而None表示键值对不存在
    // 当Option对象值为Some时，调用get函数能够获取value的值
    val value1: Option[Int] = map1.get("hello")
    println(value1)
    // 需要说明的是，在使用get(String key)函数时，当key所对应的value不存在时，会返回None，此时再链式调用函数会出现空指针异常
    val value2: Option[Int] = map1.get("nihao")
    println(value2)

    // 为了解决当key对应的value不存在这种情况，可以调用getOrElse(String key, Any default)函数
    // 即，当key对应的value不存在时，返回默认值，这个默认值可以是任意类型
    val value3: Int = map1.getOrElse("nihao", 13)
    println(value3)

    // 方式二：和Java中类似
    val value4: Int = map1("hello")
    println(value4)

    // 向Map中添加元素
    // 调用+()函数
    val map2 = map1.+("haha" -> 3)
    println(map2)

  }

  def func2(): Unit = {
    // 可变Map
    // 创建可变Map
    val map1: mutable.Map[String, Int] = mutable.Map("hello" -> 2, "world" -> 3, "scala" -> 4)
    println(map1)

    // 向Map中添加元素
    // 方式一：依然调用+()函数，不过该函数会返回新的Map
    map1.+("haha" -> 3)
    println(map1)
    // 方式二：调用+=()函数，在原Map基础上直接添加元素
    // 和以往的集合类型不同，Map类型数据调用+=()函数，进行元素添加时，需要将key-value封装到一个()中，作为一个整体传入，只添加一个元素
    map1.+=(("haha", 4))
    // 方式三：调用put(key: K, value: V)函数，在原Map基础上进行添加
    map1.put("hehe", 4)
    println(map1)

    // 删除元素，同样，根据key进行删除，当指定的key不存在时，不做任何操作
    // 方式一：调用-=()函数
    map1.-=("haha")
    println(map1)

    // 方式二：调用remove()函数
    map1.remove("hehe")
    println(map1)

    map1.remove("shiafhao")
    println(map1)

    // 修改元素，调用update(key : K, value : v)函数，对于Map中存在key，那么是对现有的key-value进行更新，
    // 如果key不存在，那么则时添加新的key-value
    map1.update("shide", 3)
    map1.update("hello", 100)
    println(map1)

    // 合并两个Map集合
    // 对于不可变Map集合，合并后会产生新的Map，合并调用的函数为++()
    // 对于可变Map集合，合并可以在原Map上发生改变，合并调用的函数为++=()

    // 对于合并的说明：当两个Map中的元素的key和value都相同时，只会保留一个；
    // 当两个Map中的元素具有相同的key，具有不同的value时，以作为函数参数的Map的key-value为准

    // 不可变Map合并
    val map10: Map[String, Int] = Map("a" -> 1, "b" -> 3, "c" -> 3)
    val map11: Map[String, Int] = Map("a" -> 1, "b" -> 2, "d" -> 3)
    val map12: Map[String, Int] = map10 ++ map11
    println(map12)

    // 可变Map合并
    val map13: mutable.Map[String, Int] = mutable.Map("a" -> 1, "b" -> 3, "c" -> 3)
    val map14: mutable.Map[String, Int] = mutable.Map("a" -> 1, "b" -> 2, "d" -> 3)
    map13 ++= map14 // 将map14的元素合并到map13中
    println(map13)


  }

}