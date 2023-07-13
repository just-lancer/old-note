package exercise;

import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Exercise07 {
    // Map接口常用的方法
    @Test
    public void test1() {
        HashMap hashMap = new HashMap();
        // Object put(Object key, Object value)
        // 方法返回值的说明 当新添加的key-value的key值与原来的已有的键值对的key值相同时，
        // 新的键值对中的value回替换原来旧的value，并将该值返回
        hashMap.put("张三", 18);
        hashMap.put("李四", 20);
        hashMap.put("王五", 19);
        hashMap.put("赵六", 30);
        Object obj = hashMap.put("赵六", 28);
        System.out.println(hashMap);
        System.out.println(obj);

        // void putAll(Map map)
        // 说明，同样，如果新的键值对和旧的键值对出现重复。那么新的键对应的value值会替换原来的value
        HashMap hashMap1 = new HashMap();
        hashMap1.put("张三", 22);
        hashMap1.put("第五", 33);
        hashMap.putAll(hashMap1);
        System.out.println(hashMap);

        // Object remove(Object key)  按键值删除键值对，同理添加的对象所在类需要重写equals和hashCode方法
        // 返回删除键值对的value值
        Object obj1 = hashMap.remove("李四");
        System.out.println(hashMap);
        System.out.println(obj1);

        // boolean remove(Object key, Object value)
        Object obj2 = hashMap.remove("第五", 33);
        System.out.println(hashMap);
        System.out.println(obj2);
    }

    @Test
    public void test2() {
        HashMap hm = new HashMap();
        hm.put("张三", 18);
        hm.put("李四", 20);
        hm.put("王五", 19);
        hm.put("赵六", 30);
        System.out.println(hm);

        // int size()
        System.out.println(hm.size());

        // boolean isEmpty()
        System.out.println(hm.isEmpty());

        // Object get(Object key) 根据传入的key值，返回value值
        Object obj1 = hm.get("李四");
        System.out.println(obj1);

        // void claer()
        hm.clear();
        System.out.println(hm.size());
    }

    @Test
    public void test3() {
        HashMap hm = new HashMap();
        hm.put("张三", 18);
        hm.put("李四", 20);
        hm.put("王五", 19);
        hm.put("赵六", 30);
        System.out.println(hm);

        // boolean containsKey(Object key)
        System.out.println(hm.containsKey("张三"));

        // boolean containsVaule(Object value)
        System.out.println(hm.containsValue(19));
    }

    // 遍历
    @Test
    public void test4() {
        HashMap hm = new HashMap();
        hm.put("张三", 18);
        hm.put("李四", 20);
        hm.put("王五", 19);
        hm.put("赵六", 30);
        System.out.println(hm);

        // Set keySet()
        Set hmSet = hm.keySet();
        System.out.println(hmSet);

        // Collection containsValue()
        Collection hmValueColl = hm.values();
        System.out.println(hmValueColl);

        // Set entrySet()
        Set hmEntrySet = hm.entrySet();
        System.out.println(hmEntrySet);
    }

}
