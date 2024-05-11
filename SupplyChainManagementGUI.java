package ClassCodes;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Supplier {
    private String name;
    private List<Product> productsSupplied;

    public Supplier(String name) {
        this.name = name;
        this.productsSupplied = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productsSupplied.add(product);
    }

    public List<Product> getProductsSupplied() {
        return productsSupplied;
    }

    public String getName() {
        return name;
    }
}

class Order {
    private Product product;
    private int quantity;

    public Order(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class SupplyChainManagementGUI extends JFrame implements ActionListener {
    private List<Supplier> suppliers;
    private List<Order> orders;
    private JComboBox<String> supplierComboBox;
    private JTextField productNameField;
    private JTextField quantityField;
    private JTextArea orderTextArea;

    public SupplyChainManagementGUI() {
        this.suppliers = new ArrayList<>();
        this.orders = new ArrayList<>();

        setTitle("Supply Chain Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel supplierLabel = new JLabel("Supplier:");
        supplierComboBox = new JComboBox<>();
        productNameField = new JTextField(15);
        quantityField = new JTextField(5);
        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(this);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(supplierLabel);
        inputPanel.add(supplierComboBox);
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(placeOrderButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
        supplierComboBox.addItem(supplier.getName());
    }

    public void placeOrder(Product product, int quantity) {
        Order order = new Order(product, quantity);
        orders.add(order);
        updateOrderTextArea();
    }

    public void updateOrderTextArea() {
        orderTextArea.setText("Orders:\n");
        for (Order order : orders) {
            orderTextArea.append("Product: " + order.getProduct().getName() +
                    ", Quantity: " + order.getQuantity() + "\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Place Order")) {
            String supplierName = (String) supplierComboBox.getSelectedItem();
            String productName = productNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            for (Supplier supplier : suppliers) {
                if (supplier.getName().equals(supplierName)) {
                    for (Product product : supplier.getProductsSupplied()) {
                        if (product.getName().equalsIgnoreCase(productName)) {
                            placeOrder(product, quantity);
                            return;
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Product not found.");
        }
    }

    public static void main(String[] args) {
        SupplyChainManagementGUI gui = new SupplyChainManagementGUI();

        Supplier supplier1 = new Supplier("Supplier 1");
        supplier1.addProduct(new Product("Product 1", 10.0));
        supplier1.addProduct(new Product("Product 2", 15.0));
        gui.addSupplier(supplier1);

        Supplier supplier2 = new Supplier("Supplier 2");
        supplier2.addProduct(new Product("Product 3", 20.0));
        gui.addSupplier(supplier2);
    }
}
