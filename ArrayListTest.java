import java.util.ArrayList;

public class ArrayListTest{
	public static void main(String[] args){
		ArrayList<Integer> ali = new ArrayList<Integer>();
		for (int i=0; i<1000; i++){
			ali.add(null);
		}
		ali.add(3, new Integer(21));
		System.out.println(ali);
	}
}
