import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<Warehouse> warehouses;
    private List<ShoppingCenter> shoppingCenters;
    private List<Product> products;
    private List<Customer> customers;
    private final JsonSaver json;
    private int nextId = 1;

    public DataManager(){
        warehouses = new ArrayList<>();
        shoppingCenters = new ArrayList<>();
        products = new ArrayList<>();
        customers = new ArrayList<>();
        json = new JsonSaver("inventory.json");
        readData();
    }

    private void readData(){
        AllData data = json.read();
        if(data == null){ return; }
        products = data.getProducts()!=null ? data.getProducts():new ArrayList<>();
        warehouses = data.getWarehouses()!=null ? data.getWarehouses():new ArrayList<>();
        shoppingCenters = data.getShoppingCenters()!=null ? data.getShoppingCenters():new ArrayList<>();
        customers = data.getCustomers() != null ? data.getCustomers() : new ArrayList<>();

        for (Product p : products) nextId = Math.max(nextId, p.getId() + 1);
        for (Warehouse w : warehouses) nextId = Math.max(nextId, w.getId() + 1);
        for (ShoppingCenter s : shoppingCenters) nextId = Math.max(nextId, s.getId() + 1);
        for (Customer c : customers) nextId = Math.max(nextId, c.getId() + 1);
    }

    public void saveData(){
        AllData data = new AllData();
        data.setProducts(products);
        data.setWarehouses(warehouses);
        data.setShoppingCenters(shoppingCenters);
        data.setCustomers(customers);
        json.save(data);
    }

    private Product findProduct(int id){
        for (Product p: products){
            if(p.getId()==id){return p;}
        }
        return null;
    }
    private Warehouse findWarehouse(int id){
        for (Warehouse w: warehouses){
            if(w.getId()==id){return w;}
        }
        return null;
    }
    private ShoppingCenter findShoppingCenter(int id){
        for (ShoppingCenter s: shoppingCenters){
            if(s.getId()==id){return s;}
        }
        return null;
    }

    private Customer findCustomer(int id) {
        for (Customer cust : customers) {
            if (cust.getId() == id) return cust;
        }
        return null;
    }

    public void addProduct(String name, double price, int quantity){
        int id = nextId++;
        products.add(new Product(id,name,price,quantity));
        System.out.println("Продукт добавлен");
        saveData();
    }

    public void addCustomer(String name) {
        int id = nextId++;
        Customer customer = new Customer(id, name);
        customers.add(customer);
        System.out.println("Customer added: " + name);
        saveData();
    }

    public void moveProduct(int productId, int fromId, int toId, int quantity) {
        Product product = findProduct(productId);
        Warehouse from = findWarehouse(fromId);
        Warehouse to = findWarehouse(toId);

        if(product == null || from == null || to == null){
            System.out.println("Не хватает данных");
            return;
        }

        WarehouseCell oldCell = null;
        for (WarehouseCell cell: from.getCells()){
            if(cell.getProduct().equals(product)){oldCell=cell;}
        }
        for(WarehouseCell cell: to.getCells()){
            if(cell.isEmpty() && cell.getCapacity()>=quantity){
                cell.setProduct(product);
                oldCell.removeProduct();
                System.out.println("Продукт успешно перемещен!");
                saveData();
                return;
            }
        }
    }

    public void sellProduct(int productId, int shoppingCenterId,int customerId, int quantity){
        Product product = findProduct(productId);
        ShoppingCenter shoppingCenter = findShoppingCenter(shoppingCenterId);
        Customer customer = findCustomer(customerId);

        if(product == null || shoppingCenter==null || customer == null){
            System.out.println("Не хватает данных");
        }

        if (product.changeAmount(-quantity)){
            shoppingCenter.addProduct(new Product(productId, product.getName(), product.getPrice(), product.getQuantity()-quantity));
            shoppingCenter.addIncome(product.getPrice()*quantity);
            System.out.println("Продукт успешно продан покупателю "+customer.getName());
            saveData();
        }else{
            System.out.print("Неправильное количество товара");
        }
    }

    public void returnProduct(int productId, int shoppingcenterId,int customerId, int quantity){
        Product product = findProduct(productId);
        ShoppingCenter shoppingCenter = findShoppingCenter(shoppingcenterId);
        Customer customer = findCustomer(customerId);

        if(product == null || shoppingCenter == null || quantity<=0 || customer == null){
            System.out.println("Неправильные данные");
        }

        for(Product pr: shoppingCenter.getProducts()){
            if(pr.getId() == (productId) && pr.changeAmount(-quantity) ){
                pr.changeAmount(quantity);
                shoppingCenter.addIncome(-quantity*pr.getPrice());
                System.out.println("Продукт успешно возвращен от покупателя "+customer.getName());
                saveData();
                return;
            }
        }
        System.out.println("Продукт не был найден в магазине");
    }

    public void purchaseProduct(int productId, int warehouseId, int quantity){
        Product product = findProduct(productId);
        Warehouse warehouse = findWarehouse(warehouseId);

        if (product==null || warehouse==null || quantity<=0){
            System.out.println("Неправильные данные");
            return;
        }

        for(WarehouseCell cell: warehouse.getCells()){
            if(cell.isEmpty()){
                Product newProd = new Product(productId, product.getName(), product.getPrice(), quantity);
                if (cell.addProduct(newProd)){
                    products.add(newProd);
                    System.out.println("Продукт успешно куплен!");
                    saveData();
                }
            }
        }
        System.out.println("Нет свободных мест на складе");
    }

    public void hireEmployee(String name, int warehouseId){
        Warehouse warehouse = findWarehouse(warehouseId);

        if(warehouse==null){
            System.out.println("Неправильно введены данные");
        }

        int id = nextId++;
        Employee employee = new Employee(id,name);
        warehouse.addEmployee(employee);
        if (warehouse.getMainEmployee()==null){
            warehouse.setMainEmployee(employee);
        }
        System.out.println("Работник успешно нанят!");
        saveData();
    }

    public void fireEmployee(int employeeId){
        for(Warehouse warehouse: warehouses){
            for (Employee emp: warehouse.getEmployees()){
                if(emp.getId()==employeeId){
                    warehouse.fireEmployee(emp);
                    if (warehouse.getMainEmployee().getId() == employeeId){
                        warehouse.setMainEmployee(null);
                    }
                    System.out.println("Работник уволен :(");
                    saveData();
                    return;
                }
            }
        }
        System.out.println("Работник не нашелся");
    }

    public void openWarehouse(String name){
        int id = nextId++;
        Warehouse warehouse = new Warehouse(id, name);
        for(int i = 0; i<10; i++){
            warehouse.addCell(new WarehouseCell(id, 100));
        }
        warehouses.add(warehouse);
        System.out.println("Склад успешно добавлен!");
        saveData();
    }

    public void closeWarehouse(int warehouseId){
        Warehouse warehouse = findWarehouse(warehouseId);

        if(warehouse==null){
            System.out.println("Неправильно введены данные");
            return;
        }

        if(warehouse.getCells().stream().allMatch(WarehouseCell::isEmpty)){
            warehouses.remove(warehouse);
            System.out.println("Склад закрыт");
            saveData();
        }else{
            System.out.println("Склад нельзя закрыть вместе с товарами");
        }
    }

    public void openShoppingCenter(String name){
        int id = nextId++;
        ShoppingCenter shoppingCenter = new ShoppingCenter(id,name);
        shoppingCenters.add(shoppingCenter);
        System.out.println("Магазин успешно открыт!");
        saveData();
    }

    public void closeShoppingCenter(int shoppingCenterId){
        ShoppingCenter shoppingCenter = findShoppingCenter(shoppingCenterId);

        if (shoppingCenter == null){
            System.out.println("Неправильно введены данные");
            return;
        }

        if (shoppingCenter.getProducts().isEmpty()){
            shoppingCenters.remove(shoppingCenter);
            System.out.println("Магазин успешно закрыт!");
            saveData();
        }else{
            System.out.println("В магазине остались непроданные позиции");
        }
    }

    public String getWarehouseInfo(int warehouseId){
        Warehouse warehouse = findWarehouse(warehouseId);

        if(warehouse == null){
            return("Склад не найден.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Склад: ").append(warehouse.getName()).append("\n");
        sb.append("Ответственный: ").append(warehouse.getMainEmployee() != null ? warehouse.getMainEmployee().getName() : "пусто").append("\n");
        sb.append("Работники: ").append(warehouse.getEmployees().size()).append("\n");
        sb.append("Ячейки:\n");
        for (WarehouseCell cell : warehouse.getCells()) {
            sb.append("  Ячейка ").append(cell.getId()).append(": ");
            sb.append(cell.getProduct() != null ? cell.getProduct().getName() + " (" + cell.getProduct().getQuantity() + ")" : "пусто");
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getShoppingCenterIfo(int shoppingCenterId) {
        ShoppingCenter sp = findShoppingCenter(shoppingCenterId);
        if (sp == null) {return "Магазин не найден.";}

        StringBuilder sb = new StringBuilder();
        sb.append("Магазин: ").append(sp.getName()).append("\n");
        sb.append("Прибыль: ").append(sp.getIncome()).append(" долларов").append("\n");
        sb.append("Продукты:\n");
        for (Product prod : sp.getProducts()) {
            sb.append("  ").append(prod.getName()).append(" (").append(prod.getQuantity()).append(")\n");
        }
        return sb.toString();
    }

    public String getAvailableProducts() {
        StringBuilder sb = new StringBuilder("Продукты в наличии:\n");
        for (Product prod : products) {
            sb.append(prod.getName()).append(" (ID: ").append(prod.getId()).append(", Количество: ").append(prod.getQuantity()).append(", Цена: ").append(prod.getPrice()).append(" долларов").append(")\n");
        }
        return sb.toString();
    }

    public String getProfitability(int shoppingCenterId) {
        ShoppingCenter sp = findShoppingCenter(shoppingCenterId);
        if (sp == null) {return "Магазин не найден.";}
        return "Магазин " + sp.getName() + " Доходность: " + sp.getIncome()+ " долларов";
    }
}
