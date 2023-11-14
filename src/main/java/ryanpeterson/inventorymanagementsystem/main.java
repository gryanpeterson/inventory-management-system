package ryanpeterson.inventorymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/** The main class
 * FUTURE ENHANCEMENT: To extend functionality, in a future version I would add a transactions screen that would show
 * when a part was added, modified, or deleted from inventory.
 *
 * RUNTIME ERROR: Runtime error can be found in onActionMainScreenModifyPart method of MainScreenController.java file.
 *
 * @author Ryan Peterson
 */
public class main extends Application {

    /** Creates the FXML stage and loads the initial scene.
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** The main method creates sample parts and products and launches the application.
     * @param args
     */

    public static void main(String[] args) {

        int partID = Inventory.getNewPartID();
        Part part1 = new InHouse(partID, "Display", 50.00, 20, 5, 10, 1);
        partID = Inventory.getNewPartID();
        Part part2 = new InHouse(partID, "Camera", 100.00, 5, 2, 5, 2);
        partID = Inventory.getNewPartID();
        Part part3 = new InHouse(partID, "Speaker", 25.00, 10, 5, 10, 3);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        partID = Inventory.getNewPartID();
        Part part4 = new Outsourced(partID, "Battery", 50.00, 8, 4, 10, "Amperex Technology");
        partID = Inventory.getNewPartID();
        Part part5 = new Outsourced(partID, "Motherboard", 80.00, 7, 2, 12, "Pegatron");
        partID = Inventory.getNewPartID();
        Part part6 = new Outsourced(partID, "Chip", 150.00, 5, 2, 8, "TSMC");

        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);

        int productID = Inventory.getNewProductID();
        Product product1 = new Product(productID, "iPhone 14 Plus", 999, 15, 10, 20);
        productID = Inventory.getNewProductID();
        Product product2 = new Product(productID, "iPhone 14", 799, 15, 10, 20);

        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part4);
        product1.addAssociatedPart(part5);
        product1.addAssociatedPart(part6);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);


        launch();
    }
}