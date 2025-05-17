import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCenter extends Entity {
    @JsonProperty("products")
    private List<Product> products;
    @JsonProperty("income")
    private double income;

    public ShoppingCenter(){
        this.products = new ArrayList<>();
    }

    public ShoppingCenter(int id, String name){
        super(id,name);
        this.products = new ArrayList<>();
        this.income = 0;
    }

    public List<Product> getProducts(){return products;}
    public double getIncome(){return income;}

    public void setProducts(List<Product> products){this.products = products;}
    public void setIncome(double income){this.income = income;}

    public void addIncome(double amount){income+=amount;}
    public void addProduct(Product product){products.add(product);}
}
