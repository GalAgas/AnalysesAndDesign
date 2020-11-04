import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

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
                String login_id;
                String password;
                String isPrem;

                String address;
                String phone;
                String email;

                //if getting login_id through input
                login_id = input.substring(12);

                //if getting login_id through second input from user
//                System.out.println("Please enter login ID:");
//                login_id = myObj.nextLine();

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
                    System.out.println("making you not premium");
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

                    System.out.println("Congratulations! You're successfully added!");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Web user not added! Please try again.");
                }
            }

            else if (input.startsWith("Remove WebUser")){
                String login_id;

                //if getting login_id through input
                login_id = input.substring(15);

                //if getting login_id through second input from user
//                System.out.println("Please enter login ID:");
//                login_id = myObj.nextLine();

                try {
                    shopsys.removeUser(login_id);
                    System.out.println("You're successfully removed!");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Web user not removed! Please try again.");
                }

            }

            else if (input.startsWith("Login WebUser")){
                String login_id;
                String password;

//                System.out.println("Please enter your login ID:");
//                login_id = myObj.nextLine();

                //if getting login_id through input
                login_id = input.substring(14);

                System.out.println("Please enter your password:");
                password = myObj.nextLine();


                try {
                    shopsys.logIn(login_id, password);
                    System.out.println("You're successfully logged in!");

                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Please try again.");
                }
            }

            else if (input.startsWith("Logout WebUser")){

                String login_id = input.substring(15);

                try {
                    shopsys.logOut(login_id);

                    System.out.println("You're successfully logged out!");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("You're still logged in! Please try again.");
                }
            }

            else if (input.equals("Make order")){
                System.out.println("Type the premium's account name: ");
                String premiumAccount = myObj.nextLine() + "'s Account";
                try {
                    Order o = shopsys.makeNewOrder(premiumAccount);

                    String choose = "";
                    while(!choose.equals("DONE")){
                        System.out.println("Choose product's number: ");
                        System.out.println("If you don't want to order anymore, type DONE");
                        shopsys.displayPremiumProducts(premiumAccount);
                        choose = myObj.nextLine();
                        if(choose.equals("DONE")) break;
                        Product chosen = shopsys.chooseProduct(premiumAccount, choose);
                        System.out.println("Choose the amount: ");
                        String amount = myObj.nextLine();
                        if(isNumeric(amount)) {
                            shopsys.addlineItetmtoOrder(o, chosen, Integer.parseInt(amount));
                        }
                        else{
                            System.out.println("You didn't enter an amount");
                            continue;
                        }
                    }
                    System.out.println("Do you want to pay now? y/n");
                    String toPay = myObj.nextLine();
                    if (!toPay.equals("y")){
                        continue;
                    }
                    else{
                        String paymentType = "";
                        while (!paymentType.equals("3")) {
                            System.out.println("Choose your payment:\n\tFor ImmediatePayment press 1\n\tFor DelayedPayment press 2\n\t" +
                                    "Press 3 to cancel");
                            paymentType = myObj.nextLine();
                            if (paymentType.equals("3")) continue;
                            System.out.println("How much do you want to pay?");
                            toPay = myObj.nextLine();
                            shopsys.paymentMethod(o, shopsys.getCurrentLoggedIn().getCustomer().getAccount(), paymentType, toPay);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

            else if (input.startsWith("Display order")){
                shopsys.displayOrder();
            }

            else if (input.startsWith("Link Product")){
                String productName;
                String price;

//                System.out.println("Please enter product's name:");
//                productName = myObj.nextLine();

                productName = input.substring(13);

                System.out.println("Please enter product's price:");
                price = myObj.nextLine();
                if(!isNumeric(price)){
                    System.out.println("The price isn't a number");
                    continue;
                }



                //need status??? succeed/failed
                try {
                    shopsys.linkProductToPrem(productName, Integer.parseInt(price));
                    System.out.println("Product was successfully linked.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


            }

            else if (input.startsWith("Add Product")){
                String supplierId;
                String productId;
                String productName;

                System.out.println("Dear supplier, please enter your ID:");
                supplierId = myObj.nextLine();

                if(!shopsys.idValidation(supplierId)){
                    System.out.println("Not valid id.");
                    continue;
                }

                System.out.println("Please enter product's ID:");
                productId = myObj.nextLine();

                System.out.println("Please enter product's name:");
                productName = myObj.nextLine();

                try {
                    shopsys.addProduct(productName, productId, supplierId);
                    System.out.println("Your product has successfully added!");

                } catch (Exception e) {
                    e.getMessage();
                    System.out.println("Product not added. Please try again.");
                }
            }

            else if (input.startsWith("Delete Product")){
                String productName;
                productName = input.substring(15);

                try {
                    shopsys.deleteProduct(productName);
                    System.out.println("Product was successfully deleted.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

            else if (input.startsWith("ShowAllObjects")){
                System.out.println("Current all objects:");
                shopsys.showAllObjects();

            }

            else if (input.startsWith("ShowObjectId")){
                String objectId;

//                System.out.println("Please enter object's ID:");
//                objectId = myObj.nextLine();

                objectId = input.substring(13);

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
//            ss.addProduct("Bisli", "Bisli", "123");
//            ss.linkProductToPrem("Bisli");
////            ss.showAllObjects();
//
//            ss.logIn("Dani", "Dani123");
//            ss.makeNewOrder("Agasiii's Account");
//            Order o = ss.getCurrentLoggedIn().getCustomer().getAccount().getOrders().get(0);
//            ss.showAllObjects();
//
//            System.out.println();
//            ss.removeUser("Dani");
//            ss.deleteProduct("Bisli");
//            ss.showAllObjects();
//
//            //makeNewOrder
//            // Dani
//            //              price   amount
//            //1. Bamba -    10          2
//            //2. Bisli -     5          3
//            //1 5 10
//            // new Order -> add Product
//
//
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        ss.showObject("Dani");
//        System.out.println(ss.makeNewOrder("Dani", "Dani123"));
//        System.out.println(ss.makeNewOrder("Dani", "Dani123"));
//        System.out.println(ss.makeNewOrder("Agasiii", "123"));




    }

}
