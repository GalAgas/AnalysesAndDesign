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
            String input = null;

            try{
                input = myObj.nextLine();// Read user input
            }
            catch(Exception e){
                System.out.println("Goodbye!");
                done = true;
                break;
            }

            if (input.startsWith("Add WebUser") && input.length() > 11){
                String login_id;
                String password;
                String isPrem;
                String city;
                String street;
                String number;
                String phone;
                String email;

                //getting login_id through input
                login_id = input.substring(12);

                System.out.println("Please enter a password:");
                password = myObj.nextLine();

                System.out.println("Premium account? y/n:");
                isPrem = myObj.nextLine();
                boolean isPremium;
                if (isPrem.equals("y")){
                    isPremium = true;
                }
                else if (isPrem.equals("n")){
                    isPremium = false;
                }
                else {
                    System.out.println("Not a valid input!");
                    System.out.println("Making you not premium");
                    isPremium = false;
                }

                System.out.println("Please enter city name:");
                city = myObj.nextLine();

                System.out.println("Please enter street name:");
                street = myObj.nextLine();

                System.out.println("Please enter street's number:");
                number = myObj.nextLine();

                System.out.println("Please enter a phone:");
                phone = myObj.nextLine();

                System.out.println("Please enter an email:");
                email = myObj.nextLine();

                try{
                    shopsys.addUser(login_id, password, isPremium, city, street, number, phone, email);

                    System.out.println("Congratulations! You have successfully added!");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Web user not added! Please try again.");
                }
            }

            else if (input.startsWith("Remove WebUser") && input.length() > 14){
                String login_id;

                //getting login_id through input
                login_id = input.substring(15);

                try {
                    shopsys.removeUser(login_id);
                    System.out.println("You're successfully removed!");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Web user not removed! Please try again.");
                }

            }

            else if (input.startsWith("Login WebUser") && input.length() > 13){
                String login_id;
                String password;


                //getting login_id through input
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

            else if (input.startsWith("Logout WebUser") && input.length() > 14){

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
                System.out.println("Type the premium user's account name: ");
                String premiumAccount = myObj.nextLine() + "'s Account";
                PremiumAccount PA = null;
                try {
                    Order o = shopsys.makeNewOrder(premiumAccount);
                    PA = (PremiumAccount)shopsys.retrieveFromMap(premiumAccount);
                    String choose = "";
                    boolean orderDeleted = false;
                    while(!choose.equals("Done")){
                        System.out.println("Choose product's number as shown below: ");
                        System.out.println("If you don't want to order anymore, type 'Done' ");

                        shopsys.displayPremiumProducts(premiumAccount);
                        choose = myObj.nextLine();

                        if(choose.equals("Done")) break;
                        if(!shopsys.orderValidation(premiumAccount, choose)){
                            System.out.println("You've entered a wrong number");
                            continue;
                        }
                        Product chosen = shopsys.chooseProduct(premiumAccount, choose);
                        System.out.println("Choose the amount that you want to purchase: ");

                        String amount = myObj.nextLine();
                        if(!ShoppingSystem.isNumeric(amount)) {
                            System.out.println("You didn't enter an amount.");
                            System.out.println("This Line Item has not been added to your order. ");
                            continue;

                        }
                        else if(Integer.parseInt(amount) > chosen.getAmountToSale()){
                            System.out.println("The amount you specified is not available.");
                            System.out.println("This Line Item has not been added to your order. ");
                            continue;
                        }
                        else{
                            shopsys.addlineItetmtoOrder(o, chosen, Integer.parseInt(amount));
                        }
                    }
                    if(orderDeleted) continue;
                    System.out.println("The price is: " + o.getTotal());
                    System.out.println("Do you want to pay now?\n\tPress 'y' if yes, else press any other key.");
                    String toPay = myObj.nextLine();
                    if (!toPay.equals("y")){
                        System.out.println("You didn't pay for your order!");
                        continue;
                    }
                    else{
                        String paymentType = "";
                        while (!paymentType.equals("3")) {
                            System.out.println("Choose your payment:\n\tFor ImmediatePayment press '1'\n\tFor DelayedPayment press '2'\n\t" +
                                    "Press '3' when you're done");
                            paymentType = myObj.nextLine();
                            if (paymentType.equals("3")) {
                                if(o.getTotal() > o.getPaid()){
                                    System.out.println("Reminder: You didn't finish paying for this order.");
                                }
                                break;
                            }
                            if (paymentType.equals("2") || paymentType.equals("1")) {
                                System.out.println("How much do you want to pay?");
                                toPay = myObj.nextLine();
                                try {
                                    if(o.getTotal() < Float.parseFloat(toPay)){
                                        System.out.println("Because we're nice, we won't let you pay above the order's price. ");
                                        continue;
                                    }
                                    shopsys.paymentMethod(PA, o, shopsys.getCurrentLoggedIn().getCustomer().getAccount(), paymentType, toPay);
                                    System.out.println("Your payment has been created successfully.");
                                }
                                catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                            }
                            else{
                                System.out.println("Not a valid input!");
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

            else if (input.equals("Display order")){
                shopsys.displayOrder();
            }

            else if (input.startsWith("Link Product") && input.length() > 12){
                String productName;
                String price;
                String amountToSale;

                productName = input.substring(13);
                System.out.println("Please enter product's price:");
                price = myObj.nextLine();
                if(!ShoppingSystem.isNumeric(price)){
                    System.out.println("The price isn't a number");
                    continue;
                }
                System.out.println("Please enter the amount you want to sell:");
                amountToSale = myObj.nextLine();
                if(!ShoppingSystem.isNumeric(amountToSale)){
                    System.out.println("The amount isn't a number");
                    continue;
                }

                try {
                    shopsys.linkProductToPrem(productName, Integer.parseInt(price), Integer.parseInt(amountToSale));
                    System.out.println("Product was successfully linked.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if (input.equals("Add Product")){
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
                    System.out.println(e.getMessage());
                    System.out.println("Product not added. Please try again.");
                }
            }

            else if (input.startsWith("Delete Product") && input.length() > 14){
                String productName;
                productName = input.substring(15);
                try {
                    shopsys.deleteProduct(productName);
                    System.out.println("Product was successfully deleted.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if (input.equals("ShowAllObjects")){
                System.out.println("Current all objects:");
                shopsys.showAllObjects();
            }

            else if (input.startsWith("ShowObjectId") && input.length() > 12){
                String objectId;
                objectId = input.substring(13);
                try {
                    shopsys.showObject(objectId);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if(input.equals("Done")){
                done = true;
                System.out.println("Goodbye!");
            }

            else{
                System.out.println("Not a valid input!");
            }
        }
    }
}
