import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Order {

    private String number;
    private Date ordered;
    private Date shipped;
    private Address shipTo; //Address???
    private OrderStatus orderStatus;
    private float total;
    private static Integer autoID=0;
    private float paid;

    // Associations
    private Account account;
    private ArrayList<Payment> payments;
    private ArrayList<LineItem> lineItems;

    public String getId() {
        return number;
    }

    public Order(String id, Account account) {
        this.autoID += 1;
        this.number = id;
        this.account = account;
        this.payments = new ArrayList<>();
        this.lineItems = new ArrayList<>();
        this.orderStatus = OrderStatus.New;
        this.ordered = new Date();
        this.shipTo = this.account.getCustomer().getAddress();
        this.total =0;
        this.paid=0;
        this.account.addOrder(this);
    }

    /**
     * adds a new payment to payments list
     * @param payment
     */
    public void addPayment(Payment payment){
        payments.add(payment);
        this.orderStatus = OrderStatus.Hold; // change the status of the order
        this.getAccount().setBalance(this.account.getBalance() - payment.getTotal());
        paid += payment.getTotal();
        if (paid == this.total){ // if the total amount was paid then close the order and ship it to the customer.
            this.orderStatus = OrderStatus.Closed;
            this.shipped = new Date();
        }
    }

    /**
     * applies the shipping date
     */
    public void setShipped(){
        this.shipped = new Date();
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTotal() {
        return total;
    }

    public Account getAccount() {
        return account;
    }

    /**
     * adds new line item to line items list only if doesn't belong to other order
     * and if the order wasn't closed
     * @param lineItem
     */
    public void addLineItem (LineItem lineItem){
        if (this.orderStatus != OrderStatus.Closed && !lineItem.isOrdered() && !lineItems.contains(lineItem)){
            lineItems.add(lineItem);
            total += lineItem.getPrice();
            lineItem.setOrdered(true);
        }
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public void removeLineItem(LineItem l) {
        this.lineItems.remove(l);
        this.total-=l.getPrice();
    }

    public String showAllDetails() {
        return "Order:\n\t" + "Order number: " + number + ", Status: " + this.orderStatus
                + ", Address: " + this.shipTo
                + ", Ordered in: " + this.ordered + ", Shipping date: " + this.shipped +
                "\n\tTotal cost: " + this.total + ", Total Paid: " + paid + ", remaining payment: " + (this.total - paid)+"\n" +
                this.showAssociated();
    }

    @Override
    public String toString(){
        return "Order:\n\tOrder Number: " + this.number;
    }

    public void removeAssociated() {
        this.account = null;
        for(Payment p : this.payments){
            p.removeAssociated();
        }
        this.payments = null;
        for(LineItem li: this.lineItems){
            li.removeAssociated();
        }
        this.lineItems = null;
    }

    public String showAssociated() {
        String toReturn = "";
        if (this.account != null) toReturn+=(this.account) +"\n";
        if (this.lineItems != null){
            toReturn += "LineItems:\n";
            for (LineItem li: this.lineItems){
                toReturn += "\t" + li + "\n";

            }
        }
        if (this.payments != null){
            toReturn+="Payments:\n";
            for (Payment p: this.payments){
                toReturn += "\t" + p + ", amount: " + p.getTotal() + "\n";

            }
        }
        return toReturn;
    }
}
