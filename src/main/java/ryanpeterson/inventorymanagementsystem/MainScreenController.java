package ryanpeterson.inventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Class controller for the main screen.
 *
 * @author Ryan Peterson
 */
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;
    /** Part object selected in the Main Screen by the user.
     *
     */
   private static Part partToModify;

    /** The getter for the part object selected in the Main Screen by the user.
     *
     * @return the part object selected by the user in the main screen.
     */
   public static Part getPartToModify() {
       return partToModify;
   }

    /** Product object selected in the Main Screen by the user.
     *
     */
   private static Product productToModify;

    /** The getter for the product object selected in the Main Screen by the user.
     *
     * @return the product object selected by the user in the main screen.
     */
   public static Product getProductToModify() {
       return productToModify;
   }

    /** The part search text field.
     *
     */
    @FXML
    private TextField MainScreenPartSearchTxt;
    /** The inventory column for the part table view.
     *
     */
    @FXML
    private TableColumn<Part, Integer> MainScreenPartsInventoryCol;
    /** The part ID column for the part table view.
     *
     */
    @FXML
    private TableColumn<Part, Integer> MainScreenPartsPartIDCol;
    /** The part name column for the part table view.
     *
     */
    @FXML
    private TableColumn<Part, String> MainScreenPartsPartNameCol;
    /** The price column for the part table view.
     *
     */
    @FXML
    private TableColumn<Part, Double> MainScreenPartsPriceCol;
    /** The parts table view.
     *
     */
    @FXML
    private TableView<Part> MainScreenPartsTableView;
    /** The product search field for the product table view.
     *
     */
    @FXML
    private TextField MainScreenProductSearchTxt;
    /** The inventory column for the product table view.
     *
     */
    @FXML
    private TableColumn<Product, Integer> MainScreenProductsInventoryCol;
    /** The part ID column for the product table view.
     *
     */
    @FXML
    private TableColumn<Product, Integer> MainScreenProductsPartIDCol;
    /** The price column for the product table view.
     *
     */
    @FXML
    private TableColumn<Product, Double> MainScreenProductsPriceCol;
    /** The product name column for the product table view.
     *
     */
    @FXML
    private TableColumn<Product, String> MainScreenProductsProductNameCol;
    /** The product table view.
     *
     */
    @FXML
    private TableView<Product> MainScreenProductsTableView;

    /** Takes the user to the Add Part screen.
     *
     * @param event Add button action.
     * @throws IOException
     */
    @FXML
    void onActionMainScreenAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Takes the user to the Add Product screen.
     *
     * @param event Add button action.
     * @throws IOException
     */
    @FXML
    void onActionMainScreenAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Deletes the selected part from the part table view.
     *
     * @param event Delete button action.
     * @throws IOException
     */
    @FXML
    void onActionMainScreenDeletePart(ActionEvent event) throws IOException {
        Part selectedPart = MainScreenPartsTableView.getSelectionModel().getSelectedItem();

        if (MainScreenPartsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to delete from the part table view.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Part?");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
                MainScreenPartsTableView.setItems(Inventory.getAllParts());
            }
        }
    }

    /** Delete the selected product from the product table view.
     *
     * @param event Delete button action.
     */
    @FXML
    void onActionMainScreenDeleteProduct(ActionEvent event) {
        Product selectedProduct = MainScreenProductsTableView.getSelectionModel().getSelectedItem();

        if (MainScreenProductsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product to delete from the product table view.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Product?");
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();
                if (assocParts.size() >= 1) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Alert");
                    alert2.setHeaderText("Product Cannot Be Deleted");
                    alert2.setContentText("Product cannot be deleted if there are parts associated with it.");
                    alert2.showAndWait();
                }
                else {
                    Inventory.deleteProduct(selectedProduct);
                    MainScreenProductsTableView.setItems(Inventory.getAllProducts());
                }
            }
        }

    }

    /** Exits the program.
     *
     * @param event Exit button action.
     */
    @FXML
    void onActionMainScreenExit(ActionEvent event) {
        System.exit(0);

    }

    /** Stores user selected part from the part table view and takes the user to the Modify Part screen.
     *
     * RUNTIME ERROR: A runtime error occurs if the user does not have a part selected when pressing the Modify button
     * in the parts table view. This is corrected by using try and catch blocks in the method and
     * prompting an alert box if no part is selected.
     *
     * @param event Modify button action.
     * @throws IOException
     */
    @FXML
    void onActionMainScreenModifyPart(ActionEvent event) throws IOException {

        partToModify = MainScreenPartsTableView.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPartScreen.fxml"));
            loader.load();

            ModifyPartController modPartController = loader.getController();
            modPartController.passPart(MainScreenPartsTableView.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to modify from the part table view.");
            alert.showAndWait();

        }

    }

    /** Stores user selected product from the product table view and takes the user to the Modify Product screen.
     *
     * @param event Modify button action.
     * @throws IOException
     */
    @FXML
    void onActionMainScreenModifyProduct(ActionEvent event) throws IOException {

        productToModify = MainScreenProductsTableView.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product to modify from the product table view.");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProductScreen.fxml"));
            loader.load();

            ModifyProductController modProductController = loader.getController();
            modProductController.passProduct(MainScreenProductsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Creates an empty list and adds parts matching either the ID or name of the part from the search field to the list.
     * Refreshes the part table view using the new filtered list.
     * @param event Search button action.
     */
    @FXML
    void onActionMainScreenSearchParts(ActionEvent event) {
        ObservableList<Part> allPartsList = Inventory.getAllParts();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        String searchString = MainScreenPartSearchTxt.getText();

        for (Part part : allPartsList) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                filteredParts.add(part);
            }
        }
        MainScreenPartsTableView.setItems(filteredParts);

        if (filteredParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Window");
            alert.setHeaderText("Not found");
            alert.setContentText("No part found matching that ID or name.");
            alert.showAndWait();
        }

    }
    /** Creates an empty list and adds products matching either the ID or name of the product from the search field to the list.
     * Refreshes the part table view using the new filtered list.
     * @param event Search button action.
     */
    @FXML
    void onActionMainScreenSearchProducts(ActionEvent event) {
        ObservableList<Product> allProductsList = Inventory.getAllProducts();
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        String searchString = MainScreenProductSearchTxt.getText();

        for (Product product : allProductsList) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                filteredProducts.add(product);
            }
        }
        MainScreenProductsTableView.setItems(filteredProducts);

        if (filteredProducts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Window");
            alert.setHeaderText("Not found");
            alert.setContentText("No product matching that ID or name found.");
            alert.showAndWait();
        }

    }

    /** Initializes controller.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainScreenPartsTableView.setItems(Inventory.getAllParts());

        MainScreenPartsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainScreenPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainScreenPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainScreenPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainScreenProductsTableView.setItems(Inventory.getAllProducts());

        MainScreenProductsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainScreenProductsProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainScreenProductsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainScreenProductsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
