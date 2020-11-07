public class Address {

    private String city;
    private String street;
    private String number;


    public Address(String city, String street, String number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public String getCity() {
        return city;
    }


    public String getStreet() {
        return street;
    }


    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Address: " + street + " " + number + ", " + city;
    }
}
