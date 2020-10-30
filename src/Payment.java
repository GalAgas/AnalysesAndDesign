import java.util.Date;

public abstract class Payment {
    private String id;
    private Date paid;
    private float total;
    private String details;

    // Associations
    protected Account account;
    protected Order order;
}
