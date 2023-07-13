package arraysexercise;


public class ArraysExercise01 {
	public static void main(String[] args) {
		String stringArray[] = new String[3];
		for (int i = 0; i < stringArray.length; i++) {
//			System.out.println(stringArray[i] + "-----");//输出null-----
			stringArray[i] = new String();
			System.out.println(stringArray[i] + "-----");//输出空/-----
			
		}
	}
}
