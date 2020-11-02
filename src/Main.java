import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    // User interface

        // build initial system

        ShoppingSystem shopsys = new ShoppingSystem();

        boolean done = false;

        System.out.println("Welcome to our store!");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        while (!done){
            System.out.println("Please type a command:");
            String input = myObj.nextLine();  // Read user input

            if (input.startsWith("Add WebUser")){
                String isPrem;
                String login_id;
                String password;

                String address;
                String phone;
                String email;

                //if getting login_id throgh input
//                String log = input.substring(12);
//                System.out.println(log);

                //if getting login_id throgh second input from user
                System.out.println("Please enter login ID:");
                login_id = myObj.nextLine();

                System.out.println("Please enter password:");
                password = myObj.nextLine();

                System.out.println("Premium accout? y/n:");
                isPrem = myObj.nextLine();
                boolean isPremium;
                if (isPrem.equals("y")){
                    isPremium = true;
                }
                else if (isPrem.equals("n")){
                    isPremium = false;
                }
                else {
                    System.out.println("not a valid input!");
                    System.out.println("making you not premium!");
                    isPremium = false;
                }

                System.out.println("Please enter address:");
                address = myObj.nextLine();

                System.out.println("Please enter phone:");
                phone = myObj.nextLine();

                System.out.println("Please enter email:");
                email = myObj.nextLine();

                try{
                    shopsys.addUser(login_id, password, isPremium, address, phone, email);

                    System.out.println("Congratulations! Your'e successfully added!");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Web user not added! Please try again.");
                }
            }

            else if (input.startsWith("Remove WebUser")){
                String login_id;

                //if getting login_id throgh second input from user
                System.out.println("Please enter login ID:");
                login_id = myObj.nextLine();

                try {
                    shopsys.removeUser(login_id);
                    System.out.println("Your'e successfully removed!");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Web user not removed! Please try again.");
                }

            }

            else if (input.startsWith("Login WebUser")){
                String login_id;
                String password;

                System.out.println("Please enter your login ID:");
                login_id = myObj.nextLine();

                System.out.println("Please enter your password:");
                password = myObj.nextLine();


                try {
                    shopsys.logIn(login_id, password);
                    System.out.println("Your'e successfully logged in!");

                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Please try again.");
                }
                }

            else if (input.startsWith("Logout WebUser")){
                try {
                    //don't need to pass the currUser, shopSys already have this attribute
                    //need to change the method
                    shopsys.logOut(shopsys.getCurrentLoggedIn().getLogin_id());
                    System.out.println("Your'e successfully logged out!");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Your'e still logged in! Please try again.");
                }
            }

            else if (input.startsWith("Make order")){


            }

            else if (input.startsWith("Display order")){
                shopsys.DisplayOrder();
            }

            else if (input.startsWith("Link Product")){
                String productName;

                System.out.println("Please enter product's name:");
                productName = myObj.nextLine();

                //need status??? succeed/failed
                shopsys.linkProductToPrem(productName);

            }

            else if (input.startsWith("Add Product")){
                String supplierId;
                String productId;
                String productName;

                System.out.println("Dear supplier, please enter your ID:");
                supplierId = myObj.nextLine();

                System.out.println("Please enter product's ID:");
                productId = myObj.nextLine();

                System.out.println("Please enter product's name:");
                productName = myObj.nextLine();

                //need to change method's parameter supplier Id instead of supplier's instance
                //surrounds with try&catch

                shopsys.addProduct(productName, productId, shopsys.getSuppliers().get(supplierId));

                System.out.println("Your product has successfully added!");


                System.out.println("The product not added. Please try again.");
            }

            else if (input.startsWith("Delete Product")){
                String productName;

                System.out.println("Please enter product's name:");
                productName = myObj.nextLine();

                //need status??? succeed/failed
                //surrounds with try&catch
                shopsys.deleteProduct(productName);
            }

            else if (input.startsWith("ShowAllObjects")){
                System.out.println("Current all objects:");
                shopsys.showAllObjects();

            }

            else if (input.startsWith("ShowObjectId")){
                String objectId;

                System.out.println("Please enter object's ID:");
                objectId = myObj.nextLine();

                //need status??? succeed/failed
                //surrounds with try&catch -object doesn't exist
                shopsys.showObject(objectId);
            }

            else if(input.equals("Done")){
                done = true;
                System.out.println("Goodbye!");
            }

            else{
                System.out.println("Not a valid input!");
            }
        }

        /*switch case:
        * case 1:
        *
        * case2:
        *
        * case make_order:
        *   create new order with system.makeNewOrder()
        *   then we build a loop for inserting new line items until finish with system.addlineItetmtoOrder()
        *
        * */

/*

        Supplier s = new Supplier("1", "k");
        Product p = new Product("p1", "ppp", s);
        System.out.println(p);
        System.out.println(s);
        LineItem l = new LineItem(p);
        */


//         ShoppingSystem shs = new ShoppingSystem();

//         WebUser wu = new WebUser("1","11",true,"the moving life 2", "05222","postbgu");
//         shs.setCurrentLoggedIn(wu);
// //        Order o = new Order(wu.getCustomer().getAccount());
// //        System.out.println(o);

//         Supplier s = new Supplier("1", "k");
// //        Product p = new Product("bamba","bamba",s);
//         shs.addProduct("bamba","bamba",s);
//         shs.addProduct("chocolate chips cookies", "chocolate chips cookies", s);
//         Product p = shs.getIdsToProducts().get("bamba");
//         Product p1 = shs.getIdsToProducts().get("chocolate chips cookies");
//        LineItem li = new LineItem(p, 2, 5,o, wu.getShoppingCart());
//        LineItem li2 = new LineItem(p1, 1, 10, o, wu.getShoppingCart());
//        Payment pa = new DelayedPayment(wu.getCustomer().getAccount(),o,8);
//        Payment pa2 = new ImmediatePayment(wu.getCustomer().getAccount(),o,2);
//        System.out.println(o);
//        Payment pa3 = new ImmediatePayment(wu.getCustomer().getAccount(),o,5);
//        System.out.println(o);
//        Order o1 = new Order(wu.getCustomer().getAccount());
//        LineItem li3 = new LineItem(p,1,5,o, wu.getShoppingCart());
//        System.out.println(o);

//         Order o = shs.makeNewOrder();
//         Order o1 = shs.makeNewOrder();

//         shs.addlineItetmtoOrder(o,p,10);

//         shs.linkProductToPrem(p.getName());
//         shs.linkProductToPrem(p1.getName());

//         System.out.println(o);

//         shs.deleteProduct(p.getName());

//         System.out.println(o);

//
//        WebUser wu = new WebUser("1","11",false,"the moving life 2", "05222","postbgu");
//        Order o = new Order(wu.getCustomer().getAccount());
////        System.out.println(o);
//
//        Supplier s = new Supplier("1", "k");
//        Product p = new Product("bamba","bamba",s);
//        Product p1 = new Product("37", "choclate chips cookies", s);
//        LineItem li = new LineItem(p, 2, 5,o, wu.getShoppingCart());
//        LineItem li2 = new LineItem(p1, 1, 10, o, wu.getShoppingCart());
//        Payment pa = new DelayedPayment(wu.getCustomer().getAccount(),o,8);
//        Payment pa2 = new ImmediatePayment(wu.getCustomer().getAccount(),o,2);
////        System.out.println(o);
//        Payment pa3 = new ImmediatePayment(wu.getCustomer().getAccount(),o,5);
////        System.out.println(o);
//        Order o1 = new Order(wu.getCustomer().getAccount());
//        LineItem li3 = new LineItem(p,1,5,o, wu.getShoppingCart());
//        System.out.println(o);

//        ShoppingSystem ss = new ShoppingSystem();
//
////        System.out.println(ss.addUser("Agasiii", "1", false, "as", "12", "as"));
////        System.out.println(ss.removeUser("Agasiii"));
////        System.out.println(ss.removeUser("Agasiii"));
//        try{
//            ss.addUser("Agasiii", "123", true, "be", "123", "eas");
//            ss.logIn("Agasiii", "123");
////            ss.showAllObjects();
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        ss.showObject("Dani");



    }

}
