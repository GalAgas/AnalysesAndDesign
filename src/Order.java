import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Order {

    private String number;
    private Date ordered;
    private Date shipped;
    private String shipTo; //Address???
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

    public Order(Account account) {
        this.autoID += 1;
        this.number = autoID.toString();
        this.account = account;
        this.payments = new ArrayList<>();
        this.lineItems = new ArrayList<>();
        this.orderStatus = OrderStatus.New;
        this.ordered = new Date();
        this.shipTo = this.account.getBilling_address();
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
        paid += payment.getTotal();
        if (paid == this.total){ // if the total amount was paid then close the order
            this.orderStatus = OrderStatus.Closed;
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

    public void removeLineItem(LineItem l) {
        this.lineItems.remove(l);
    }

    @Override
    public String toString() {
        String toReturn = "Order No." + number +":\n"
                + "STATUS: " + this.orderStatus +"\n"
                + "Account No." + account.getId()+"\n"
                + "Address: " + this.shipTo + "\n"
                + "Ordered in " + this.ordered+"\n"
                + "Shipping date: " + this.shipped + "\n"
                + "Products:\n";
        for (LineItem li: lineItems){
            toReturn += li.toString()+"\n";
        }
        toReturn += "Total cost: " + this.total + "\n"
                    + "Payments:\n";
        for (Payment p: payments){
            toReturn += p.toString()+"\n";
        }
        toReturn += "Total Paid: " + paid + ", remaining payment: " + (this.total - paid)+"\n";
        return toReturn;

    }

}
