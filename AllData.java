import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AllData {
    @JsonProperty("products")
    private List<Product> products;
    @JsonProperty("warehouses")
    private List<Warehouse> warehouses;
    @JsonProperty("shoppingCenters")
    private List<ShoppingCenter> shoppingCenters;
    @JsonProperty("customers")
    private List<Customer> customers;

    public AllData(){}

    public List<Product> getProducts(){return products;}
    public List<Warehouse> getWarehouses(){return warehouses;}
    public List<ShoppingCenter> getShoppingCenters(){return shoppingCenters;}
    public List<Customer> getCustomers() {return customers;}

    public void setProducts(List<Product> products) {this.products = products;}
    public void setShoppingCenters(List<ShoppingCenter> shoppingCenters) {this.shoppingCenters = shoppingCenters;}
    public void setWarehouses(List<Warehouse> warehouses) {this.warehouses = warehouses;}
    public void setCustomers(List<Customer> customers) {this.customers = customers;}
}
