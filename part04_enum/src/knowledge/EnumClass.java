package knowledge;

public class EnumClass {
    /**
     * 枚举类的学习
     *  主要内容
     *      > JDK 5.0之前，如何自定义枚举类
     *      > JDK 5.0之后，如何使用enum关键字创建枚举类
     *      > Enum类的主要方法学习
     *      > 枚举类继承接口
     */

    /**
     *  一、枚举类的使用
     *  1.枚举类的理解：类的对象只有有限个，确定的。我们称此类为枚举类
     *  2.当需要定建议使用枚举类
     *  3.如果枚举类义一组常量时，强烈中只有一个对象，则可以作为单例模式的实现方式。
     *
     */

    /**
     * 二、如何定义枚举类
     *  方式一：jdk5.0之前，自定义枚举类
     *  方式二：jdk5.0，可以使用enum关键字定义枚举类
     *
     */

    /**
     * 三、Enum类中的常用方法：
     *  values()方法：返回枚举类型的对象数组。该方法可以很方便地遍历所有的枚举值。
     *  valueOf(String str)：可以把一个字符串转为对应的枚举类对象。要求字符串必须是枚举类对象的“名字”。如不是，会有运行时异常：IllegalArgumentException。
     *  toString()：返回当前枚举类对象常量的名称
     *
     */

    /**
     * 四、使用enum关键字定义的枚举类实现接口的情况
     * 情况一：实现接口，在enum类中实现抽象方法
     * 情况二：让枚举类的对象分别实现接口中的抽象方法
     */

    public static void main(String args[]) {
        System.out.println(Season.AUTUMN);
        System.out.println(Direction.SOUTH);

        /**
         * 对于利用enum关键字创建的枚举类，具有一些常用的方法
         * 1、values()：该方法会返回一个数组，数字元素的类型是枚举类的类型，
         *     元素为枚举类所包含的对象，数组长度是枚举类包含对象的个数
         *  注意：该方法并不是Enum类中定义的方法，而是编译器在编译时插入的方法，因此只能用枚举类直接调用
         *
         * 2、valueOf(String objName)；该方法根据传入的对象名称返回枚举类中定义的对象常量
         *     个人觉得没什么用，都能写出来对象名称还要这个方法干啥啊
         *     注意：当输入的字符串格式不对的时候会报错：IllegalArgumentException，非法参数异常
         *
         * 3、toString()：Enum类中的toString()方法只会返回对象的名称，该方法可以重写
         */

        // values()方法应用举例
        Direction dir[] = Direction.values();
        for (int i = 0; i < dir.length; i++) {
            System.out.println(dir[i]);
        }

        // valueOf(String objName)方法应用举例
        Direction east = Direction.valueOf("EAST");
        System.out.println(east.getClass().getSuperclass());

        // 自定义类也能够实现接口，并且被重写的方法根据其写法不同会产生不同的效果
        // 重写方式一：在枚举类内只重写一个被重写的方法，那么枚举类的每个对象调用重写方法时，都只调用同一个重写方法
        // 重写方式二：在枚举类的每个对象后面跟写一对大括号{}，在括号内重写被重写的方法，那么每个对象都可以重写不一样的内容
        //            除此之外还需要在枚举类内部再重写一个被重写的方法，虽然这个方法在实际中并不会被调用
        Season winter = Season.WINTER;
        winter.show();

        // enum关键字创建枚举类继承接口：与自定义创建枚举类继承接口同样有两种重写方式
        // 方式一：与自定义创建枚举类相同
        // 方式二：与自定义创建枚举类相同，唯一的不同是，enum关键字创建的枚举类不需要额外在枚举类的内部重写被重写的方法
        Direction dir1 = Direction.NORTH;
        dir1.show();

    }
}

interface Interface {
    public void show();
}

// 自定义枚举类，以季节为例
class Season implements Interface {
    private final String seasonName;
    private final String seasonDes;

    public static final Season SPRING = new Season("春天", "春耕") {
        @Override
        public void show() {
            System.out.println("春神曲");
        }
    };
    public static final Season SUMMER = new Season("夏天", "夏织") {
        @Override
        public void show() {
            System.out.println("生如夏花");
        }
    };
    public static final Season AUTUMN = new Season("秋天", "秋收") {
        @Override
        public void show() {
            System.out.println("秋天不回来");
        }
    };
    public static final Season WINTER = new Season("冬天", "冬读书") {
        @Override
        public void show() {
            System.out.println("大约在冬季");
        }
    };

    private Season(String seasonName, String seasonDes) {
        this.seasonName = seasonName;
        this.seasonDes = seasonDes;
    }

    public String getSeasonName() {
        return this.seasonName;
    }

    public String getSeasonDes() {
        return this.seasonDes;
    }

    public String toString() {
        return "{seasonName: " + this.seasonName + "; seasonDes: " + this.seasonDes + "}";
    }

//    @Override
//    public String toString() {
//        return "Season{" +
//                "seasonName='" + seasonName + '\'' +
//                ", seasonDes='" + seasonDes + '\'' +
//                '}';
//    }


    @Override
    public void show() {

    }
}

/**
 * 使用enum关键字创建枚举类
 * 1、使用关键字enum直接修饰一个类，那么这个类会直接继承Enum类，并成为一个枚举类
 * 2、在枚举类中，上来先直接定义对象常量以及对象的相关属性
 * 3、如果对象常量有相关属性，那么需创建相应的构造器，构造器的参数列表所含参数个数必须与对象属性的个数相同
 * 4、此时枚举类已经算是定义好了
 * 额外的
 * 5、可以给定对象的属性以及在构造器中初始化属性，还可以给出相应的get方法和重写toString方法
 */
enum Direction implements Interface {
    EAST("东方", "东胜神州"),
    SOUTH("南方", "南瞻部州"),
    WEST("西方", "西牛贺州"),
    NORTH("北方", "北俱芦洲");

    private final String directionName;
    private final String directionDes;

    Direction(String directionName, String directionDes) {
        this.directionName = directionName;
        this.directionDes = directionDes;
    }

    public void show() {
        System.out.println("这是一个方向");
    }
}

