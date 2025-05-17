import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataManager manager = new DataManager();
        Scanner scan = new Scanner(System.in);

        boolean exit = false;
        while(!exit) {
            System.out.println("=====Система управления=====");
            System.out.print("Выберите опцию: ");
            System.out.println();
            System.out.println("1. Добавить продукт");
            System.out.println("2. Переместить продукт");
            System.out.println("3. Продать продукт");
            System.out.println("4. Вернуть продукт");
            System.out.println("5. Купить продукт");
            System.out.println("6. Продукты в наличии");
            System.out.println();
            System.out.println("7. Нанять работника");
            System.out.println("8. Уволить работника");
            System.out.println();
            System.out.println("9. Открыть склад");
            System.out.println("10. Закрыть склад");
            System.out.println("11. Информация о складах");
            System.out.println();
            System.out.println("12. Открыть магазин");
            System.out.println("13. Закрыть магазин");
            System.out.println("14. Информация о магазинах");
            System.out.println("15. Посмотреть доходность");
            System.out.println("16. Добавить покупателя");
            System.out.println();
            System.out.println("17. Выход");

            int choice;
            try {
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неправильный ввод, введите доступную опцию.");
                continue;
            }

            switch (choice) {

                case 1:
                    String name = getString("Введите название продукта:", scan);
                    double price = getDouble("Введите цену:",scan);
                    int quantity = getInt("Введите количество:",scan);

                    manager.addProduct(name, price, quantity);
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 2:
                    int productId = getInt("Введите id продукта:",scan);
                    int fromId = getInt("Введите id склада, откуда переместить:",scan);
                    int toId = getInt("Введите id склада, куда переместить:",scan);
                    int moveQuantity = getInt("Введите кол-во продукта",scan);

                    manager.moveProduct(productId, fromId, toId, moveQuantity);
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 3:
                    productId = getInt("Введите id продукта:",scan);
                    int shoppingCenterId = getInt("Введите id магазина:",scan);
                    quantity = getInt("Введите кол-во продукта",scan);
                    int customerId = getInt("Введите id покупателя",scan);

                    manager.sellProduct(productId, shoppingCenterId, customerId, quantity);
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 4:
                    productId = getInt("Введите id продукта:",scan);
                    shoppingCenterId = getInt("Введите id магазина:",scan);
                    quantity = getInt("Введите кол-во продукта",scan);
                    customerId = getInt("Введите id покупателя",scan);

                    manager.returnProduct(productId, shoppingCenterId, customerId, quantity);
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 5:
                    productId = getInt("Введите id продукта:",scan);
                    int warehouseId = getInt("Введите id склада:",scan);
                    quantity = getInt("Введите кол-во продукта",scan);

                    manager.purchaseProduct(productId, warehouseId, quantity);
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 6:
                    System.out.println(manager.getAvailableProducts());
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 7:
                    name = getString("Введите имя работника:",scan);
                    warehouseId = getInt("Введите id склада",scan);

                    manager.hireEmployee(name, warehouseId);
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 8:
                    int employeeId = getInt("Введите id работника",scan);

                    manager.fireEmployee(employeeId);
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 9:
                    name = getString("Укажите название склада:",scan);

                    manager.openWarehouse(name);
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 10:
                    warehouseId = getInt("Введите id склада", scan);
                    manager.closeWarehouse(warehouseId);

                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 11:
                    System.out.println(manager.getWarehouseInfo(getInt("Укажите id склада",scan)));
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 12:
                    name = getString("Укажите название магазина:",scan);
                    manager.openShoppingCenter(name);

                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 13:
                    shoppingCenterId = getInt("Укажите id магазина",scan);
                    manager.closeShoppingCenter(shoppingCenterId);

                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 14:
                    System.out.println(manager.getShoppingCenterIfo(getInt("Укажите id магазина",scan)));

                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 15:
                    System.out.println(manager.getProfitability(getInt("Укажите id магазина",scan)));

                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scan.nextLine();
                    break;

                case 16:
                    name = getString("Введите имя:",scan);
                    manager.addCustomer(name);
                    break;

                case 17:
                    exit = true;
            }
        }
    }
    //методы считывания данных
private static int getInt(String message, Scanner scan){
        while(true){
            try{
                System.out.println(message);
                return Integer.parseInt(scan.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Неправильный ввод");
            }
        }
}

private static double getDouble(String message, Scanner scan){
        while(true){
            try{
                System.out.println(message);
                return Double.parseDouble(scan.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Неправильный ввод");
            }
        }
    }

private static String getString(String message, Scanner scan){
    System.out.println(message);
        return scan.nextLine();
}

}
