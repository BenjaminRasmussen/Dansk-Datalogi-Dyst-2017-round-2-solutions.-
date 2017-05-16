import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by benjamin on 19/02/2017.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> test = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()){
            test.add(sc.nextInt());
            System.out.println(test);
            if(test.size() == test.get(0) * 6 + 1){
                break;
            }
        }


    }
}
