import com.fasterxml.jackson.annotation.JsonProperty;

public class Product extends Entity{
    @JsonProperty("price")
    private double price;
    @JsonProperty("quantity")
    private int quantity;

    public Product(){}

    public Product(int id, String name, double price, int quantity){
        super(id, name);
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice(){return price;}
    public int getQuantity(){return quantity;}

    public void setPrice(double price){this.price = price;}
    public void setQuantity(int quantity){this.quantity = quantity;}

    public boolean changeAmount(int amount){
        if((quantity+amount)>=0){
            quantity+=amount;
            return true;
        }return false;
    }

}
