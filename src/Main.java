public class Main {

    public static void main(String[] args) {
	// write your code here
    // User interface

        // build initial system



        /*switch case:
        * case 1:
        *
        * case2:
        *
        *
        *
        * */



        Supplier s = new Supplier("1", "k");
        Product p = new Product("p1", "ppp", s);
        System.out.println(p);
        System.out.println(s);
        LineItem l = new LineItem(p);
    }
}
