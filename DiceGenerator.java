//If its stupid and its works, its not stupid.
public class DiceGenerator {

    public static void main(String[] args) {
        int Ndice = 10000;
        System.out.println(Ndice);
        //Bad code starts -->
        for (int i = 0; i < Ndice; i++) {
            int a = 0;
            int b = 0;
            int c = 0;
            int d = 0;
            int e = 0;
            int f = 0;
            for (int j = 0; j < 1; j++) {


                while (a == 0)
                    a = (int) (Math.random() * 7);

                while (b == a || b == 0) {
                    b = (int) (Math.random() * 7);
                }
                while (c == a || c == b || c == 0) {
                    c = (int) (Math.random() * 7);
                }
                while (d == a || d == b || d == c || d == 0) {
                    d = (int) (Math.random() * 7);
                }
                while (e == a || e == b || e == c || e == d || e == 0) {
                    e = (int) (Math.random() * 7);
                }
                while (f == a || f == b || f == c || f == d || f == e || f == 0) {
                    f = (int) (Math.random() * 7);
                }

                //<-- Bad code ends here.
            }
            System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + f);
        }
    }
}