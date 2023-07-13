package introduction

object Exercise08_Collection06_Method {
  def main(args: Array[String]): Unit = {
    // 集合常用函数
    //    func1() // 集合的常用属性及常用操作
    //    func2() // 单集合 —— 衍生集合的相关函数操作
    //    func3() // 双集合 —— 衍生集合的相关操作
    //    func4() // 集合常见的简单计算函数
    func5() // 集合常见的高级计算函数
  }

  def func1(): Unit = {
    // 集合的常用属性及常用操作
    val array: Array[Int] = Array(1, 2, 3, 4)
    val list: List[Int] = List(5, 6, 7, 8)
    val set: Set[Int] = Set(9, 10, 11, 12)
    val map: Map[String, Int] = Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4)
    val tuple: (String, Int, Boolean, Char) = ("hello", 10, false, 'c')

    // 1、集合长度或者大小，对于有序集合，用长度描述它们的容量，对于无序集合，用大小描述它们的容量
    // 对于元组，没有容量的说法，元组最大只能有22个元素
    println(array.length)
    println(list.length)
    println(set.size)
    println(map.size)

    println("===========")

    // 2、集合的遍历
    // 对于有序集合，可以使用索引对其遍历，而无序集合无法使用索引遍历，所以，有序集合比无序集合多一种遍历方法
    // 有序集合遍历，有四种方法：① for循环，根据索引遍历；② for 循环，直接遍历元素；③ foreach循环；④ 迭代器
    // 无序集合遍历，有三种方法：① for 循环，直接遍历元素；② foreach循环；③ 迭代器

    // 2.3 遍历Set集合
    for (i <- set) {
      print(i + " ")
    }

    println()

    set.foreach(i => print(i + " "))

    println()

    val iterSet = set.iterator
    while (iterSet.hasNext) {
      print(iterSet.next() + " ")
    }

    println()

    // 2.4 遍历Map集合
    // Map集合不是单列集合，一般需要根据key来获取值
    // 遍历Map集合的所有key-value
    for (i <- map) {
      print(i + " ")
    }

    println()

    map.foreach(i => print(i + " "))

    println()

    val iterMap = map.iterator
    while (iterMap.hasNext) {
      print(iterMap.next() + " ")
    }

    println("-------------------")
    // 获取key的集合
    // 调用keys函数，返回的结果是一个由所有key构成的可迭代对象，即一个Set集合
    val keys = map.keys
    println(keys)

    // 调用keySet函数，返回结果是由所有key构成的一个Set集合
    val setMap = map.keySet
    println(setMap)

    // 调用keysIterator函数，返回结果是由所有key构成的一个迭代器
    val iterMap1 = map.keysIterator
    while (iterMap1.hasNext) {
      val key = iterMap1.next()
      print(key + ": " + map.get(key) + " ") // 打印键值对
      // 根据key获得value，调用Map集合的get(String key)函数
      // 当指定的key不存在时，会返回None
      // 注意：get(String key)返回的结果是一个Some对象

      // 和Java类似，为了直接获得value，可以直接获取value
      print(key + ": " + map(key) + " ")
    }
    println()
    println("====================================")

    // 遍历元组
    // 无法对元组进行遍历

    // 3、转换成字符串
    // 除了数组Array以外，其他的所有的集合都重写了toString()函数，所以数组需要调用它自己的方法用于生成字符串
    // 调用mkString函数或者调用mkString(String split)
    println(array)
    println(array.mkString("-"))
    println(list)
    println(set)
    println(map)
    println(tuple)

    // 4、判断集合中是否包含某元素，调用contains()函数
    // 元组，没有contains()函数
    println(array.contains(3))
    println(list.contains(4))
    println(set.contains(5))
    println(map.contains("6")) // 对于Map集合而言，稍微特殊一点，Map集合判断的是，是否包含key为该值的键值对，只判断key构成的集合
  }

  def func2(): Unit = {
    // 会产生新的集合的相关函数调用
    // 即衍生集合相关函数
    // 不对元组进行讨论
    val array: Array[Int] = Array(1, 2, 3, 4)
    val list: List[Int] = List(5, 6, 7, 8)
    val set: Set[Int] = Set(9, 10, 11, 12)
    val map: Map[String, Int] = Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4)

    // 对于集合有序和无序的说明：
    // 对有序集合而言，在内存中间中，开辟一块连续空间，其中的元素按顺序依次添加到内存空间中，因此对开发者而言，它们是有序的，具有的索引是其特征
    // 对于无序集合而言，在内存中，也是开辟一块连续空间，其中的元素按照hashcode值对空间大小求余，所得数值为元素存储的位置，因此，无序的集合实际上是
    //    一种伪随机，当元素一旦被写入到内存中，那么它们的位置就是确定的

    // 以下函数为，单集合衍生新的集合的函数操作

    // 1、获取集合的第一个元素，调用head函数，相当于一个属性，返回结果是集合的第一个元素
    val arrayHead: Int = array.head
    val listHead: Int = list.head
    val setHead: Int = set.head
    val mapHead: (String, Int) = map.head
    println(arrayHead)
    println(listHead)
    println(setHead)
    println(mapHead)
    println()

    // 2、获取集合的尾部元素（除第一个元素的其他所有元素），调用tail函数，返回结果是一个新的集合
    val arrayTail: Array[Int] = array.tail
    val listTail: List[Int] = list.tail
    val setTail: Set[Int] = set.tail
    val mapTail: Map[String, Int] = map.tail
    println(arrayTail.mkString("-"))
    println(listTail)
    println(setTail)
    println(mapTail)
    println()

    // 3、获取集合的最后一个元素，调用last函数，返回结果是集合最后的一个元素
    val arrayLast: Int = array.last
    val listLast: Int = list.last
    val setLast: Int = set.last
    val mapLast: (String, Int) = map.last
    println(arrayLast)
    println(listLast)
    println(setLast)
    println(mapLast)

    // 4、获取集合的头部元素（除最后一个元素的其他所有元素），调用init函数，返回结果是一个新的集合
    val arrayInit: Array[Int] = array.init
    val listInit: List[Int] = list.init
    val setInit: Set[Int] = set.init
    val mapInit: Map[String, Int] = map.init
    println(arrayInit)
    println(listInit)
    println(setInit)
    println(mapInit)

    // 5、获取前n个元素，调用take(Int amount)，take函数默认从集合左边向右边获取元素，其返回结果是一个新的集合
    // 当集合调用take(Int n)函数获取集合前n个元素时，如果n大于集合本身含有的元素的个数，那么会将集合的所有元素都获取，形成新的集合
    val arrayTake: Array[Int] = array.take(3)
    val listTake: List[Int] = list.take(3)
    val setTake: Set[Int] = set.take(3)
    val mapTake: Map[String, Int] = map.take(3)
    println(arrayTake)
    println(listTake)
    println(setTake)
    println(mapTake)

    // 6、获取后n个元素，调用takeRight(Int n)，从右到左获取指定个数的元素，返回结果是一个新的集合
    // 当集合调用takeRight(Int n)函数获取集合前n个元素时，如果n大于集合本身含有的元素的个数，那么会将集合的所有元素都获取，形成新的集合
    // 注意：尽管是从右往左获取集合的元素，但得到的新的集合的元素顺序和原来的集合中元素的顺序是相同的
    val arrayTakeRight: Array[Int] = array.takeRight(3)
    val listTakeRight: List[Int] = list.takeRight(3)
    val setTakeRight: Set[Int] = set.takeRight(3)
    val mapTakeRight: Map[String, Int] = map.takeRight(3)
    println(arrayTakeRight)
    println(listTakeRight)
    println(setTakeRight)
    println(mapTakeRight)

    // 7、删除前n个元素，调用drop(Int n)函数，从集合左边到右边，删除指定个数元素，将剩下的元素构成的集合作为结果返回
    val arrayDrop: Array[Int] = array.drop(1)
    val listDrop: List[Int] = list.drop(1)
    val setDrop: Set[Int] = set.drop(1)
    val mapDrop: Map[String, Int] = map.drop(1)
    println(arrayDrop)
    println(listDrop)
    println(setDrop)
    println(mapDrop)

    // 8、删除后n个元素，调用dropRight(Int n)函数，从集合右边到左边，删除指定个数元素，将剩下的元素构成的集合作为结果返回
    val arrayDropRight: Array[Int] = array.dropRight(1)
    val listDropRight: List[Int] = list.dropRight(1)
    val setDropRight: Set[Int] = set.dropRight(1)
    val mapDropRight: Map[String, Int] = map.dropRight(1)
    println(arrayDropRight)
    println(listDropRight)
    println(setDropRight)
    println(mapDropRight)

    // 9、反转集合中的元素，调用reverse函数，将元素反序后形成的新的集合作为结果返回
    // 区别于其他的函数，对于集合反转而言，必须是“有序”的集合才能够调用，所以，对于set和map两个在“开发者”看来的无序的集合，没有reverse函数
    val arrayReverse: Array[Int] = array.reverse
    val listReverse: List[Int] = list.reverse
    println(arrayReverse.mkString("-"))
    println(listReverse)

  }

  def func3(): Unit = {
    val array1: Array[Int] = Array(1, 2, 3, 4)
    val array2: Array[Int] = Array(4, 5, 6, 7)

    val list1: List[Int] = List(1, 2, 3, 4)
    val list2: List[Int] = List(4, 5, 6, 7)

    val set1: Set[Int] = Set(1, 2, 3, 4)
    val set2: Set[Int] = Set(4, 5, 6, 7)

    val map1: Map[String, Int] = Map("a" -> 1, "b" -> 2, "c" -> 3)
    val map2: Map[String, Int] = Map("a" -> 1, "b" -> 20, "d" -> 30)
    // 以下函数为，多集合衍生新的集合的函数操作
    // 1、并集，调用union()函数，将两个集合合并为一个新的集合
    // 对于有序集合而言，因为合并后的集合也是有序集合，所以调用union()函数的集合的元素会在作为参数的集合的元素之前，即按顺序合并
    // 对于无序集合而言，因为合并后的集合也是无序的，并且也不能出现重复元素，所以集合合并后会进行去重操作
    // 说明：Map集合没有并集操作
    val arrayUnion: Array[Int] = array1.union(array2)
    val arrayUnion1: Array[Int] = array1 union array2
    println(arrayUnion)
    println(arrayUnion1)

    val listUnion: List[Int] = list1.union(list2)
    val listUnion1: List[Int] = list1 union list2
    println(listUnion)
    println(listUnion1)

    val setUnion: Set[Int] = set1.union(set2)
    val setUnion1: Set[Int] = set1 union set2
    println(setUnion)
    println(setUnion1)

    // 2、交集，调用intersect()函数，将同时出现在两个集合中的元素提取出来，形成一个新的集合，并作为结果返回
    // 说明：Map集合也没有交集操作
    val arrayIntersert: Array[Int] = array1.intersect(array2)
    val arrayIntersert1: Array[Int] = array1 intersect array2
    println(arrayIntersert.mkString("-"))
    println(arrayIntersert1.mkString("-"))

    val listIntersect: List[Int] = list1.intersect(list2)
    val listIntersect1: List[Int] = list1 intersect list2
    println(listIntersect)
    println(listIntersect1)

    val setIntersect: Set[Int] = set1.intersect(set2)
    val setIntersect1: Set[Int] = set1 intersect set2
    println(setIntersect)
    println(setIntersect1)

    // 3、差集，调用diff()函数，将一个集合中存在，另一个集合中不存在的元素形成一个新的集合，并作为结果返回
    // 数学表达为：有集合A和集合B，则 A.intersect(B) => A - B = A - AB
    // 说明：Map集合没有差集操作
    val arrayDiff: Array[Int] = array1.diff(array2)
    val arrayDiff1: Array[Int] = array1 diff array2
    println(arrayDiff)
    println(arrayDiff1)

    val listDiff: List[Int] = list1.diff(list2)
    val listDiff1: List[Int] = list1 diff list2
    println(listDiff)
    println(listDiff1)

    val setDiff: Set[Int] = set1.diff(set2)
    val setDiff1: Set[Int] = set1 diff set2
    println(setDiff)
    println(setDiff1)

    println("=================================")

    // 4、拉链，调用zip()函数，将两个集合中的元素，一一对应，并进行组合，形成一个元组，作为一个新的集合的元素
    // 当进行合并的两个集合的长度，或者大小不相等时，以长度短或大小小的集合为准，较长的集合的多余的元素会被截取掉

    // 同类集合进行拉链操作
    // 较为特殊的是Map集合的拉链操作，其返回的结果依然是一个Map，其元素由原来的两个Map的元素(一个tuple类型)分别形成key和valu，进而形成新Map的一个元素
    val arrayTuples: Array[(Int, Int)] = array1.zip(array2)
    val listTuples: List[(Int, Int)] = list1.zip(list2)
    val setTuples: Set[(Int, Int)] = set1.zip(set2)
    val mapTupleToTuple: Map[(String, Int), (String, Int)] = map1.zip(map2)
    println(arrayTuples.mkString("-"))
    println(listTuples)
    println(setTuples)
    println(mapTupleToTuple)

    // 不同类型集合进行拉链
    // array和list
    val arr_list: Array[(Int, Int)] = array1.zip(list1)
    println(arr_list)

    // array和set
    val arr_set: Array[(Int, Int)] = array1.zip(set2)
    println(arr_set)

    // array和Map
    val arr_map: Array[(Int, (String, Int))] = array1.zip(map1)
    println(arr_map)

    println("--------------------------")
    // list和set
    val list_set: List[(Int, Int)] = list1.zip(set2)
    println(list_set)

    // list和Map
    val list_map: List[(Int, (String, Int))] = list1.zip(map1)
    println(list_map)

    println("----------------------------")
    // set和Map
    val set_map: Set[(Int, (String, Int))] = set1.zip(map1)
    println(set_map)

    // 关于zip()函数的说明：
    // ① 当两个集合的元素的数量不匹配时，以较‘小’的集合的元素个数为准，较‘长’的集合中，多余的元素会被截去
    // ② 两个集合进行拉链操作后形成的新的集合的类型取决于调用zip()函数的集合的类型；而新集合的元素类型都是元组

    // 5、滑窗，调用sliding(size: Int, step: Int = 1)函数，参数size表示窗口的大小，step表示窗口每次滑动的步长，默认步长是1
    // sliding()函数的返回结果是一个迭代器对象，迭代器中的元素也是由窗口确定的集合，而集合的类型与调用sliding()函数的集合的类型相同
    val arraySliding: Iterator[Array[Int]] = array1.sliding(2, 1)
    while (arraySliding.hasNext) {
      println(arraySliding.next().mkString("-"))
    }

    val listSliding: Iterator[List[Int]] = list1.sliding(2, 1)
    while (listSliding.hasNext) {
      println(listSliding.next())
    }

    val setSliding: Iterator[Set[Int]] = set1.sliding(2, 1)
    while (setSliding.hasNext) {
      println(setSliding.next())
    }

    val mapSliding: Iterator[Map[String, Int]] = map1.sliding(2, 1)
    while (mapSliding.hasNext) {
      println(mapSliding.next())
    }

  }

  def func4(): Unit = {
    // 集合常见的简单计算函数
    val array: Array[Int] = Array(1, 2, 3, 4)
    val list: List[Int] = List(5, 6, 7, 8)
    val set: Set[Int] = Set(9, 10, 11, 12)
    val map: Map[String, Int] = Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4)

    // 1、最大值与最小值
    // 对于单列集合，求最大值调用max函数，求最小值调用min函数
    val arrMax: Int = array.max
    val arrMin: Int = array.min
    println("max:" + arrMax + "," + " min:" + arrMin)

    val listMax: Int = list.max
    val listMin: Int = list.min
    println("max:" + listMax + "," + " min:" + listMin)

    val setMax: Int = set.max
    val setMin: Int = set.min
    println("max:" + setMax + "," + " min:" + setMin)

    // 对于双列集合，直接调用max函数，默认以第一列的数据构成的集合进行求最大值操作，对于Map集合，遵循这样的规则
    val mapMax: (String, Int) = map.max
    val mapMin: (String, Int) = map.min
    println("max:" + mapMax + "," + " min:" + mapMin)

    // 对于双列集合Map（多列集合也适用），如果需要根据指定列进行求最大值和最小值操作，那么需要调用maxBy(f: A => B)和minBy(f: A => B)函数
    // 根据maxBy()函数和minBy()函数传入的一个函数参数，将需要参与比较大小的列提取出来，进行比较
    val mapMax1 = map.maxBy((elem: (String, Int)) => {
      elem._2
    })

    val mapMin1 = map.minBy((elem: (String, Int)) => {
      elem._2
    })
    println("max:" + mapMax1 + "," + " min:" + mapMin1)

    // 2、求和，调用sum函数，对于多列集合求和，如果第一列是数值列，那么可以直接调用sum函数，如果第一列不是数值列，那么需要调用传参的sum()函数
    // 具体怎么做，不知道
    val arrSum = array.sum
    val listSum = list.sum
    val setSum = set.sum
    println(arrSum)
    println(listSum)
    println(setSum)

    // 3、求积，调用product函数，对于多列集合求和，如果第一列是数值列，那么可以直接调用product函数，如果第一列不是数值列，那么需要调用传参的product()函数
    val arrProduct = array.product
    val listProduct = list.product
    val setProduct = set.product
    println(arrProduct)
    println(listProduct)
    println(setProduct)

    // 5、排序，Scala中有三个排序函数

    // 说明：都涉及到排序，那么只能对有序集合进行排序

    // 1、sorted，对一个集合进行自然排序，通过传递隐式的Ordering
    // sorted函数，默认对数据进行升序排序
    val arrSorted = array.sorted
    val listSort = list.sorted
    println(arrSorted.mkString("-"))
    println(listSort)

    // 如果需要降序排序，可以链式调用reverse函数
    val arrSortedR = array.sorted
    val listSortR = list.sorted
    println(arrSortedR.mkString("-"))
    println(listSortR)

    // 除此之外，还可以调用传参sorted()函数，向其中传入一个隐式参数Ordering，即将数据比较规则传入
    // 如果需要如果需要降序排序，传入的参数应该是Ordering[T].reverse
    val arrSortedO = array.sorted(Ordering[Int].reverse)
    val listSortO = list.sorted(Ordering[Int].reverse)
    println(arrSortedO.mkString("-"))
    println(listSortO)

    // 2、sortBy，对一个属性或多个属性进行排序，通过它的类型
    // sorted函数能对单列集合进行排序，但在对多列集合进行排序时，sorted默认按照第一列进行排序
    // 为了实现多列集合按指定列进行排序，可以调用sortBy函数，与maxBy()和MinBy()函数类似，需要传入一个函数，将需要排序的列提取出来
    // 同样，sortBy()函数默认按升序进行排序，如果需要进行逆序排序可以传入隐式参数Ordering，降序排序的参数：Ordering[T].reverse

    // 3、sortWith(lt: (A, A) => Boolean)，基于函数的排序，通过传入一个comparator函数，实现自定义排序的逻辑
    // 传入的函数比较器，当返回值为true时，两个数值不需要调换顺序，当返回值为false时，两个数值就调换顺序

  }

  def func5(): Unit = {
    val array: Array[Int] = Array(1, 2, 3, 4)
    val list: List[Int] = List(5, 6, 7, 8)
    val set: Set[Int] = Set(9, 10, 11, 12)
    val map: Map[String, Int] = Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4)

    // 集合常见的高级计算函数
    // 1、过滤，调用filter(p: A => Boolean)函数，filter()函数需要传入另一个函数作为参数，该函数的实现是对集合的每一个元素的进行条件判断，
    //  当集合中的元素满足判断条件，该函数返回true，即表示该元素会被加入到新的集合中，新的集合也将会作为filter()函数的返回结果被返回
    //  而当集合中的元素不满足判断条件，那么该函数将会返回false，表示该元素被过滤掉

    // 2、转化或映射，调用map(f: A => B)函数，map()函数也需要传入另一个函数作为参数，该函数的实现是对集合的每一个元素进行操作或计算，
    // 以得到新的值，新的值会作为新的集合的元素，新的集合则会作为map()函数的返回结果

    // 3、扁平化，调用flatten函数。扁平化是指将多维集合转换成一维集合，例如，将二维数组转换成一维数组，将二维数组中每一个一维数组的元素全部取出，
    // 并按顺序拼接成一个新的一维数组。

    // 4、扁平化 + 映射，即先对多维集合进行扁平化，随后对扁平化得到的新集合进行映射；或者先对集合进行映射，再对形成的多维集合进行扁平化。
    //  因此是对flatten函数和map函数的组合应用。
    //  扁平化 + 映射有一个函数flatMap(f: A => GenTraversableOnce[B])，是对集合先映射后扁平化操作，操作顺序与函数名称无关

    // 5、分组(groupBy)，调用groupBy(f: A => K)函数，groupBy()函数需要另一个函数作为参数。groupBy()函数是对集合中的元素按照某种规则进行排序，
    //  排序的规则由传入的函数参数所确定，即集合中的所有元素经过该参数函数计算或操作过后，会得到一个函数值，具有相同函数值的元素会被分到一个组中，
    //  该组是一个集合，该集合将会作为一个Map集合中某个key-value键值对的value，该集合的类型与调用groupBy()函数的集合的类型相同，
    //  而元素经过函数参数计算得到的函数值则会成为对应的key
    //  因此，一个集合经过分组后得到的新的集合的数据类型是：Map[K, C]，其中，K表示原集合元素经过分组规则函数计算后得到的数据的数据类型，C是原集合的数据类型

    // 6、简化或归约，调用reduce(op: (A1, A1) => A1)函数，reduce()函数需要另一个函数作为参数。归约是指将集合的所有元素按指定的逻辑进行聚合，从而得到一个结果。
    //  reduce()函数进行元素聚合的逻辑是，在集合中，从左往右，依次取出两个元素进行聚合得到一个结果，该结果在与第三个元素进行聚合得到一个新的结果，新的结果再与
    //  第四个元素进行聚合，依次类推，最终将所有元素进行聚合得到最终的一个结果。而当集合的元素只有一个时，直接将该元素作为聚合结果返回。
    //  由此可知，传入的作为reduce()函数的参数的函数，有两个参数，一个返回值，三个值的数据类型必须相同，(A1, A1) => A1

    //  reduce()函数底层调用了reduceLeft(op: (B, A) => B)函数，不同于reduce()函数，reduceLeft()函数能够对不同类的数据进行聚合，这也是与reduce()函数的区别

    // reduceLeft()函数是从左向右进行聚合，从右向左进行聚合的函数是reduceRight(op: (A, B) => B)，其执行逻辑是从右向左进行元素聚合，其他与reduceLeft()无差别

    // 7、折叠，调用fold(z: A1)(op: (A1, A1) => A1)函数，fold()函数需要传入两个参数（柯里化传入），第一个参数为一个与集合元素同类型的值，
    //  该值表示聚合的初始状态，第二个参数仍然为一个函数，表示集合元素聚合的规则，fold()函数也是从左往右进行聚合，首先取集合中的第一个元素与聚合初始状态值进行聚合，
    //  得到的结果作为新的聚合状态，再与第二个元素进行聚合，从而得到新的聚合状态，随后依次与剩下的元素进行聚合，最终得到该集合的聚合结果，并将其返回

    // 与reduce()函数不同的是，fold()函数有一个初始的聚合状态，该状态通过函数参数由开发者定义
    // 与reduce()函数相同的是，fold系列函数也有foldLeft()和foldRight()函数，fold系列函数的区别与reduce系列函数的区别相同
    //  fold(z: A1)(op: (A1, A1) => A1) 函数，只能对相同数据类型进行聚合，并且从左到右进行聚合
    //  foldLeft(z: B)(op: (B, A) => B) 函数，可以对不同的数据类型进行聚合，并且从左到右进行聚合
    //  foldRight(z: B)(op: (A, B) => B) 函数，可以对不同的数据类型进行聚合，并且从右到左进行聚合

  }

}
