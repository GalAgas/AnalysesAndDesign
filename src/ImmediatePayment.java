public class ImmediatePayment extends Payment{
    private boolean phoneConfirmation;

    public ImmediatePayment(String id,Account account, Order order, float total) {
        super(id, account, order, total);
        this.phoneConfirmation = true;
    }

    @Override
    public String toString() {
        return super.toString()+
                ", phoneConfirmation=" + phoneConfirmation;
    }
}
