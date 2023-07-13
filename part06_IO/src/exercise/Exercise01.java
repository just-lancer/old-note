package exercise;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class Exercise01 {
    public static void main(String[] args) {
        File file = new File("E:\\test\\hello.txt");//利用绝对路径创建文件，这样创建hello.txt即可以是一个文件，也可以是一个文件夹
        File file1 = new File("world.txt");// 利用相对路径创建对象
        File file2 = new File("D:\\test\\", "hello\\world");// 字符路径创建文件
        File file3 = new File(file, "java.txt");// 根据父路径创建对象

        System.out.println(file);
        System.out.println(file1);
        System.out.println(file2);
        System.out.println(file3);// 其toString()方法直接打印其路径

    }

    @Test
    public void test1() {
        File file1 = new File("D:\\Test\\Java\\hello.txt");
        File file2 = new File("hello1.txt");

        // get系列方法
        // 获取文件的绝对路径
        String s1 = file1.getAbsolutePath();
        String s2 = file2.getAbsolutePath();
        System.out.println(s1);
        System.out.println(s2);

        // 获取文件的绝对路径，返回File对象
        File f1 = file1.getAbsoluteFile();
        File f2 = file2.getAbsoluteFile();
        System.out.println(f1);
        System.out.println(f2);

        // 获取文件的相对路径
        String s3 = file1.getPath();
        String s4 = file2.getPath();
        System.out.println(s3);
        System.out.println(s4);

        // 获取文件或文件夹的名字
        String s5 = file1.getName();
        String s6 = file2.getName();
        System.out.println(s5);
        System.out.println(s6);

        //获取文件的长度（以字节为单位）
        double d1 = file1.length();
        double d2 = file1.length();
        System.out.println(d1);
        System.out.println(d2);

        // 获取文件或文件夹的上级目录
        String s7 = file1.getParent();
        String s8 = file2.getParent();
        System.out.println(s7);
        System.out.println(s8);

        // 获取文件或文件夹最后一次修改的时间
        double d3 = file1.lastModified();
        double d4 = file2.lastModified();
        System.out.println(d3);
        System.out.println(d4);

    }

    @Test
    public void test2() {
        File file1 = new File("D:\\Test\\Java\\hello.txt");
        File file2 = new File("hello1.txt");

        // 因为不存在，所有全部都是false

        // 判断类方法
        // 判断是否是一个文件夹
        boolean b1 = file1.isDirectory();
        boolean b2 = file2.isDirectory();
        System.out.println(b1);
        System.out.println(b2);

        // 判断是否是一个文件
        boolean b3 = file1.isFile();
        boolean b4 = file2.isFile();
        System.out.println(b3);
        System.out.println(b4);

        // 判断是否可写
        boolean b5 = file1.canRead();
        boolean b6 = file2.canRead();
        System.out.println(b5);
        System.out.println(b6);

        // 判断是否可写
        boolean b7 = file1.canWrite();
        boolean b8 = file2.canWrite();
        System.out.println(b7);
        System.out.println(b8);

        // 判断是否隐藏
        boolean b9 = file1.isHidden();
        boolean b10 = file2.isHidden();
        System.out.println(b9);
        System.out.println(b10);

        //是否存在
        boolean b11 = file1.exists();
        boolean b12 = file1.exists();
        System.out.println(b11);
        System.out.println(b12);
    }

    @Test
    public void test3() throws IOException {
        File file1 = new File("E:\\Test\\Java\\hello.txt");
//        file1 = new File("E:\\Test");
        File file2 = new File("hello1.txt");

        // 创建、删除文件、文件夹的相关的方法
        // 创建文件或文件夹

        // public boolean mkdir(): 按当前File对象所包含的路径信息创建文件夹，成功返回true，失败返回false。
        //      注意： mkdir()一次只能创建一级文件夹；
        //              mkdir()创建文件夹时，若上层文件夹不存在则创建失败，返回false

        // boolean mkdir = file1.mkdir();
        // System.out.println(mkdir);


        // public boolean mkdirs():按当前File对象所包含的路径信息创建文件夹，成功返回true，失败返回false
        //      注意：mkdirs()方法会按当前File的路径信息一次性创建文件夹，若上层文件夹不存在或者路径缺失（部分文件夹存在或失败）一并创建
//        boolean b1 = file1.mkdirs();
//        System.out.println(b1);

        // public boolean creatNewFile():按当前File对象所包含的路径信息进行对象创建，若路径在硬盘中并不存在，报错：找不到文件路径；
        // 若当前路径下已经含有相应的文件，则不创建文件，返回false
//        boolean b1 = file1.createNewFile();
//        System.out.println(b1);


    }

    @Test
    public void test4() {
        File file = new File("E:\\Test\\java");

        // 创建文件夹
        boolean b1 = file.mkdirs();

        // 删除文件夹
        boolean b2 = file.delete();

        System.out.println(b1);
        System.out.println(b2);

    }

}
