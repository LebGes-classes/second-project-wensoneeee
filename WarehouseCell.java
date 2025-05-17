import com.fasterxml.jackson.annotation.JsonProperty;

public class WarehouseCell extends Entity{
    @JsonProperty("product")
    private Product product;
    @JsonProperty("capacity")
    private final int capacity;

    public WarehouseCell(){
        this.capacity = 100;
    }

    public WarehouseCell(int id, int capacity){
        super(id, "Ячейка "+id);
        this.capacity = capacity;
        this.product = null;
    }

    public Product getProduct() {return product;}
    public int getCapacity() {return capacity;}

    public void setProduct(Product product){this.product = product;}

    public boolean isEmpty(){
        return product == null || product.getQuantity()==0;
    }

    public boolean addProduct(Product product) {
        if (isEmpty() && product.getQuantity()<=capacity){
            this.product = product;
            return true;
        }return false;
    }
    public void removeProduct(){
        this.product = null;
    }
}
