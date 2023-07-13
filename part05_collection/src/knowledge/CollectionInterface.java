package knowledge;

public class CollectionInterface {

    /**
     * Collection集合学习
     * 主要内容
     *  1、集合框架概述
     *  2、Collection接口方法
     *  3、Iterator迭代器接口
     *  4、Collection子接口：List
     *  5、Collection子接口：Set
     *  6、Map接口
     *  7、CoLlections工具类
     */

    /**
     * 1、集合框架概述
     *  为了方便对多个对象进行操作，我们可以将对象存入Java容器：数组中
     *  除了数组这种Java容器外还有很多容器也能够存储多个对象，如集合
     *  Java集合可以分为Collection和Map两种不同的体系
     *  /----Collection接口：单列集合，用于存储一个一个的对象
     *      /----List接口：由于存储有序的，可重复的数据，并且可以替换原有的数组，它的实现类都是可以扩容的，可变容器
     *          /----ArrayList(实现类)：是List接口的主要实现类，底层使用数组进行存储；线程不安全，执行效率高
     *          /----LinkedList(实现类)：底层使用双向链表进行数据存储；当需要进行不断的插入、删除等操作时适合使用这种类型集合
     *          /----Vector(实现类)：是List接口的古老实现类，线程安全，但效率较低；底层仍然使用数组进行对象的存储
     *      /----Set接口：存储无序的、不可重复的数据
     *          /----HashSet(实现类)：作为Set接口的主要实现类，底层仍然使用数组进行数据的子类，线程不安全的，但可以存储null值
     *              /----linkedHashSet(实现类)：遍历其内部数据时，会按照添加数据的顺序进行遍历，其原因在于底层使用了数组和双向链表的方式进行数据存储
     *              /----TreeSet(实现类)：只能存储相同类型的存储，对于存入的数据可以按照指定属性进行排序；其底层利用红黑树进行数据存储
     *
     *   数组存入数据的特点:
     *      数组一旦定义，其长度就固定了，数组元素的数据类型也同时确定
     *
     */

    /**
     * 2、Collection接口中定义的抽象方法
     *  ① boolean Add(Object obj):向集合中添加对象obj，添加成功返回trur，添加失败返回false
     *  ② boolean AddAll(Collection coll):将集合coll中的所有元素按顺序一个一个添加到当前集合中，添加成功返回true，添加失败返回false
     *      注意：Add()和AddAll()的区别，Add()一次只添加一个元素，如果传入的参数是一个集合，那么将集合当作一个整体添加
     *      而AddAll()会将一个集合中的元素按顺序一个一个添加到当前集合中
     *  ③ void clear()：将集合中的所有元素都清除，但仍然保留集合这个容器
     *  ④ boolean contains(Object obj)：判断当前集合是否包含obj这个对象，如果包含返回true，如果不包含返回false
     *  ⑤ boolean containsAll(Collection coll)：判断当前对象是否包含集合coll中的全部元素，若包含返回true，若不包含返回false
     *      注意：contains()和containsAll()方法在进行对象的比较时，需要调用对象的equals()方法进行元素是否存在的判断
     *      因此所有添加到集合中的对象所在类必须重写equals()方法
     *  ⑥ boolean equals(Object obj)：判断当前集合与形参集合对象所包含的元素的个数、内容、顺序都是否相同，若相同返回true，若不同，返回false
     *  ⑦ boolean isEmpty()：判断当前集合对象是否为空，是返回true，否返回false
     *  ⑧ boolean remove(Object obj)：从当前集合中删除给定的对象obj，若删除成功返回true，删除失败返回false
     *  ⑨ boolean removeAll(Object obquals()方法
     *  ⑩ int size()：返回当前集合中所含元素的个数
     *  11、boolean retainAll(Collection coll)：剔除当前集合中不存在于集合对象obj中的元素，即求两个集合的交集，AB。
     *      注意，同样需要进行对象之间的比较
     *  12、int hashCode ()：返回当前对象的哈希值：从当前集合中移除当前集合与集合对象obj所共有的元素，即差集：A - B = A - AB
     *      *      注意：在删除指定的对象时，首先需要判断集合中是否存在该对象，因此传入的对象需要调用equals()方法不断和集合中的其他对象进行比较
     *      *      所以有一点需要注意，添加到集合中的任何对象，其所在类必须重写
     *  13、Object[] toArray()：将集合对象转换成数组
     *      将数组转换成集合调用数组的asList()方法：Arrays.asList(数组对象)
     *  14、Iterator iterator()：返回一个迭代器
     *
     */

    /**
     * 3、Iterator迭代器接口
     *  迭代器概述
     *      Collection接口中的iterator()方法返回的Iterator类型的值是一个迭代器
     *      集合对象每调用一次iterator()方法都会返回一个全新的迭代器
     *      迭代器是设计模式的一种，主要用于遍历Collection集合中的元素
     *      迭代器模式就是为容器而生，并且仅用于遍历集合对象
     *
     *   Iterator接口中的方法
     *      ① boolean hasNext()：判断是否还有下一个遍历元素，若还有未被遍历的元素，返回true；否则，返回false。
     *      ② Object next()：返回集合里的下一个元素
     *      ③ void remove()：删除集合里上一次next方法返回的元素
     *
     *      注意：迭代器的位置不是在某一个元素上，而是在集合中第一个元素之前、两个元素之间或最后一个元素之后
     *
     *   迭代器的工作原理
     *      迭代器遍历代码如下：
     *          Collection coll = new ArraysList;
     *          Iterator iter = coll.iterator();
     *          while(iter.hasNext()){
     *              Object obj = iter.Next();
     *              System.out.println(obj);
     *          }
     *      迭代器开始遍历时，迭代器位于第一个元素之前
     *      调用hasNext()方法，判断迭代器下一个位置是否有元素,
     *          如果有，进入循环，迭代器调用Next()方法，此时迭代器做了两个事
     *              首先，将迭代器指针向下移动，随后返回指针只指向的元素
     *          如果没有，循环结束，集合遍历结束
     *
     *   除了利用迭代器进行集合的遍历，还可以利用增强far循环（JDK 5.0提供的新特性）进行集合的遍历
     *      代码如下：
     *      for(Person p : coll){
     *          System.out.println(p);
     *      }
     *      // 其中Person是要遍历的元素的类型，p是遍历后自定义元素的名称，coll是要遍历的集合变量名称
     *
     *   此外还可以利用普通的for循环进行遍历
     *
     */

    /**
     * 4、List接口
     *
     *  注意：向List接口的实现类中存入数据对象时，该对象所在类的equals()方法必须重写
     *
     *  List接口的实现类所存储数据具有有序性、并且可以重复
     *  List接口除了继承父接口Collection中的抽象方法之外还定义了一系列与数组索引相关的方法
     *
     *  List接口中的抽象方法
     *      ① void Add(int index, Object obj)：将元素插入到当前集合中索引为index的位置上。插入完成之后，obj的索引即为index
     *      ② boolean AddAll(int index, Collection)：将集合的所有元素一个一个插入到指定的索引位置上
     *      ③ Object get(int index)：返回指定索引位置上的元素
     *      ④ int indexOf(Object obj)：返回对象obj在集合中首次出现位置的索引
     *      ⑤ int lastIndexOf(Object obj)：返回对象obj在集合中最后一次出现位置索引
     *      ⑥ Object remove(int index)：删除集合中指定位置处的对象，返回值即是所删除的对象
     *      ⑦ Object set(int index, Object obj)：将集合中指定索引处的元素设置为obj
     *      ⑧ List subList(int fromIndex, int toIndex)：返回[fromIndex,toIndex)索引范围内子List对象
     *
     *  List接口中三个实现类的对比及源码分析
     *      1. ArrayList的源码分析：
     *         1.1 jdk 7情况下
     *            ArrayList list = new ArrayList();//底层创建了长度是10的Object[]数组elementData
     *            list.add(123);//elementData[0] = new Integer(123);
     *            ...
     *            list.add(11);//如果此次的添加导致底层elementData数组容量不够，则扩容。
     *            默认情况下，扩容为原来的容量的1.5倍，同时需要将原有数组中的数据复制到新的数组中。
     *
     *            结论：建议开发中使用带参的构造器：ArrayList list = new ArrayList(int capacity)
     *
     *         1.2 jdk 8中ArrayList的变化：
     *            ArrayList list = new ArrayList();//底层Object[] elementData初始化为{}.并没有创建长度为10的数组
     *
     *            list.add(123);//第一次调用add()时，底层才创建了长度10的数组，并将数据123添加到elementData[0]
     *            ...
     *            后续的添加和扩容操作与jdk 7 无异。
     *         1.3 小结：jdk7中的ArrayList的对象的创建类似于单例的饿汉式，而jdk8中的ArrayList的对象
     *                  的创建类似于单例的懒汉式，延迟了数组的创建，节省内存。
     *
     *       2. LinkedList的源码分析：
     *            LinkedList list = new LinkedList(); 内部声明了Node类型的first和last属性，默认值为null
     *            list.add(123);//将123封装到Node中，创建了Node对象。
     *
     *            其中，Node定义为：体现了LinkedList的双向链表的说法
     *            private static class Node<E> {
     *               E item;
     *               Node<E> next;
     *               Node<E> prev;
     *
     *               Node(Node<E> prev, E element, Node<E> next) {
     *               this.item = element;
     *               this.next = next;
     *               this.prev = prev;
     *               }
     *           }
     *
     *        3. Vector的源码分析：jdk7和jdk8中通过Vector()构造器创建对象时，底层都创建了长度为10的数组。
     *            在扩容方面，默认扩容为原来的数组长度的2倍。
     *
     *     面试题：ArrayList、LinkedList、Vector三者的异同？
     *
     *  同：三个类都是实现了List接口，存储数据的特点相同：存储有序的、可重复的数据
     *     不同：见上
     *
     */

    /**
     * 5、Set接口
     *
     *  注意：向Set接口的实现类中存入数据时，必须重写数据对象所在类的hashCode()方法和equals()方法
     *      重写的要求是：重写的hashCode()和equals()尽可能保持一致性：
     *      相等的对象必须具有相等的散列码重写两个方法的小技巧：对象中用作 equals() 方法比较的 Field，都应该用来计算 hashCode 值。
     *
     *  Set接口也是Collection接口的子接口之一，但Set接口中并没有额外定义其他抽象方法，其原因是Set接口的实现类不是用数组来存储数据的
     *  Set集合存储数据的特点是，无序，且不可重复
     *
     *  Set：存储无序的、不可重复的数据
     * /----以HashSet为例说明：
     *      HashSet存储数据的特点：
     *          不能保证数据的存储顺序，也就是数据是无序的
     *          HashSet不是线程安全的
     *          HashSet可以存储null值
     *
     *     1. 无序性：不等于随机性。存储的数据在底层数组中并非按照数组索引的顺序添加，而是根据数据的哈希值决定的。
     *
     *     2. 不可重复性：保证添加的元素按照equals()判断时，不能返回true.即：相同的元素只能添加一个。
     *
     *     二、添加元素的过程：以HashSet为例：
     *         我们向HashSet中添加元素a,首先调用元素a所在类的hashCode()方法，计算元素a的哈希值，
     *         此哈希值接着通过某种算法计算出在HashSet底层数组中的存放位置（即为：索引位置），判断数组此位置上是否已经有元素：
     *             如果此位置上没有其他元素，则元素a添加成功。 --->情况1
     *             如果此位置上有其他元素b(或以链表形式存在的多个元素），则比较元素a与元素b的hash值：
     *                 如果hash值不相同，则元素a添加成功。--->情况2
     *                 如果hash值相同，进而需要调用元素a所在类的equals()方法：
     *                        equals()返回true,元素a添加失败
     *                        equals()返回false,则元素a添加成功。--->情况3
     *
     *         对于添加成功的情况2和情况3而言：元素a与已经存在指定索引位置上数据以链表的方式存储。
     *         jdk 7 :元素a放到数组中，指向原来的元素。
     *         jdk 8 :原来的元素在数组中，指向元素a
     *         总结：七上八下
     *
     *         HashSet底层：数组+链表的结构。
     *
     * /----LinkedHashSet的使用
     *      LinkedHashSet在利用数组存储数据时，还维护了数据的双向链表，因此在遍历其数据时，会按照添加的顺序进行打印
     *     LinkedHashSet作为HashSet的子类，在添加数据的同时，每个数据还维护了两个引用，记录此数据前一个
     *     数据和后一个数据。
     *     优点：对于频繁的遍历操作，LinkedHashSet效率高于HashSet
     *
     * /----TreeSet的使用
     *      > TreeSet底层使用红黑树进行数据存储，因此添加到TreeSet的数据对象都必须时同一类型的
     *      > 此外，TreeSet会自动对添加的数据进行排序，所以数据所在类必须要继承Comparable接口，或者在创建TreeSet对象的时候向构造器中添加Comparator比较器对象
     *
     *     1.向TreeSet中添加的数据，要求是相同类的对象。
     *     2.两种排序方式：自然排序（实现Comparable接口） 和 定制排序（Comparator）
     *     3.自然排序中，比较两个对象是否相同的标准为：compareTo()返回0.不再是equals().
     *     4.定制排序中，比较两个对象是否相同的标准为：compare()返回0.不再是equals().
     *
     */
}