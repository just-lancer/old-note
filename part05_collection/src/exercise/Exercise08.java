package exercise;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Exercise08 {
    public static void main(String[] args) {
        TreeMap<Circle, String> circleTreeMap = new TreeMap<Circle, String>();
        circleTreeMap.put(new Circle(0.4), "1");
        circleTreeMap.put(new Circle(3.3), "2");
        circleTreeMap.put(new Circle(7.7), "3");
        circleTreeMap.put(new Circle(6.5), "4");
        circleTreeMap.put(new Circle(1.1), "5");
        circleTreeMap.put(new Circle(4.2), "6");
        
        // 遍历
        Set<Map.Entry<Circle, String>> entries = circleTreeMap.entrySet();
        for (Map.Entry<Circle, String> entry : entries) {
            System.out.println(entry);
        }
    }

}
