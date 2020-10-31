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

/*

        Supplier s = new Supplier("1", "k");
        Product p = new Product("p1", "ppp", s);
        System.out.println(p);
        System.out.println(s);
        LineItem l = new LineItem(p);
        */
/*
        WebUser wu = new WebUser("1","11",false,"the moving life 2", "05222","postbgu");
        Order o = new Order(wu.getCustomer().getAccount());
//        System.out.println(o);

        Supplier s = new Supplier("1", "k");
        Product p = new Product("bamba","bamba",s);
        Product p1 = new Product("37", "choclate chips cookies", s);
        LineItem li = new LineItem(p, 2, 5);
        LineItem li2 = new LineItem(p1, 1, 10);
        o.addLineItem(li);
        o.addLineItem(li2);
        Payment pa = new DelayedPayment(wu.getCustomer().getAccount(),o,8);
        Payment pa2 = new ImmediatePayment(wu.getCustomer().getAccount(),o,2);
//        System.out.println(o);
        Payment pa3 = new ImmediatePayment(wu.getCustomer().getAccount(),o,5);
//        System.out.println(o);
        Order o1 = new Order(wu.getCustomer().getAccount());
        LineItem li3 = new LineItem(p,1,5);
        o1.addLineItem(li3);
        System.out.println(wu.getCustomer().getAccount().getOrders().get(0));
        */
    }

}
