package ryanpeterson.inventorymanagementsystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Class controller for the modify product screen.
 *
 * @author Ryan Peterson
 */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    /** The product object selected from the main screen.
     *
     */
    private  Product selectedProduct;
    /** The list of parts associated with the product.
     *
     */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    /** The inventory column for the lower table view.
     *
     */
    @FXML
    private TableColumn<Part, Integer> ModifyProductBottomTVInventoryCol;
    /** The part ID column for the lower table view.
     *
     */
    @FXML
    private TableColumn<Part, Integer> ModifyProductBottomTVPartIDCol;
    /** The part name column for the lower table view.
     *
     */
    @FXML
    private TableColumn<Part, String> ModifyProductBottomTVPartNameCol;
    /** The price column for the lower table view.
     *
     */
    @FXML
    private TableColumn<Part, Double> ModifyProductBottomTVPriceCol;
    /** The lower table view.
     *
     */
    @FXML
    private TableView<Part> ModifyProductBottomTableView;
    /** The product ID text field.
     *
     */
    @FXML
    private TextField ModifyProductIDTxt;
    /** The inventory text field.
     *
     */
    @FXML
    private TextField ModifyProductInventoryTxt;
    /** The maximum quantity text field.
     *
     */
    @FXML
    private TextField ModifyProductMaxTxt;
    /** The minimum quantity text field.
     *
     */
    @FXML
    private TextField ModifyProductMinTxt;
    /** The product name text field.
     *
     */
    @FXML
    private TextField ModifyProductNameTxt;
    /** The price text field.
     *
     */
    @FXML
    private TextField ModifyProductPriceTxt;
    /** The search text field.
     *
     */
    @FXML
    private TextField ModifyProductSearchTxt;
    /** The inventory column for the upper table view.
     *
     */
    @FXML
    private TableColumn<Part, Integer> ModifyProductTopTVInventoryCol;
    /** The part ID column for the upper table view.
     *
     */
    @FXML
    private TableColumn<Part, Integer> ModifyProductTopTVPartIDCol;
    /** The part name column table view for the upper table view.
     *
     */
    @FXML
    private TableColumn<Part, String> ModifyProductTopTVPartNameCol;
    /** The price column table view for the upper table view.
     *
     */
    @FXML
    private TableColumn<Part, Double> ModifyProductTopTVPriceCol;
    /** The upper table view.
     *
     */
    @FXML
    private TableView<Part> ModifyProductTopTableView;

    /** Adds part object selected from the upper table view to the lower table view.
     * Associating the part with the product.
     * @param event Add button action.
     */
    @FXML
    void onActionModifyProductScreenAddProduct(ActionEvent event) {
        Part selectedPart = ModifyProductTopTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to add to the associate parts table.");
            alert.showAndWait();
        }
        else {
            assocParts.add(selectedPart);
            ModifyProductBottomTableView.setItems(assocParts);
        }

    }

    /** Returns the user to the main screen.
     *
     * @param event Cancel button action.
     * @throws IOException
     */
    @FXML
    void onActionModifyProductScreenCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Removes part object from the lower table view. Removing the associated part.
     *
     * @param event Remove button action.
     */
    @FXML
    void onActionModifyProductScreenRemoveProduct(ActionEvent event) {
        Part selectedPart = ModifyProductBottomTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Remove Associated Part?");
        alert.setContentText("Are you sure you want to remove the associated part from this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            assocParts.remove(selectedPart);
            ModifyProductBottomTableView.setItems(assocParts);
        }

    }

    /** Replaces product with a new product by creating a new product and deleting the old product.
     *
     * @param event Save button action.
     * @throws IOException
     */
    @FXML
    void onActionModifyProductScreenSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = selectedProduct.getId();
            String name = ModifyProductNameTxt.getText();
            int stock = Integer.parseInt(ModifyProductInventoryTxt.getText());
            double price = Double.parseDouble(ModifyProductPriceTxt.getText());
            int min = Integer.parseInt(ModifyProductMinTxt.getText());
            int max = Integer.parseInt(ModifyProductMaxTxt.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Minimum value cannot be greater than Maximum value");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory value must be between Minimum and Maximum values.");
                alert.showAndWait();
            } else {
                Product newProduct = new Product(id, name, price, stock, min, max);

                for (Part part : assocParts) {
                    newProduct.addAssociatedPart(part);
                }

                Inventory.addProduct(newProduct);
                Inventory.deleteProduct(selectedProduct);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Modify Product");
            alert.setContentText("Product could not be modified because a text field is empty or has an invalid value.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionModifyProductScreenSearch(ActionEvent event) {

        ObservableList<Part> allPartsList = Inventory.getAllParts();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        String searchString = ModifyProductSearchTxt.getText();

        for (Part part : allPartsList) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                filteredParts.add(part);
            }
        }
        ModifyProductTopTableView.setItems(filteredParts);

        if (filteredParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Window");
            alert.setHeaderText("Not found");
            alert.setContentText("No part found matching that ID or name.");
            alert.showAndWait();
        }

    }
    /** Passes product object from Main Screen to Modify Product screen.
     *
     * @param product product object selected from the main screen.
     */
    public void passProduct(Product product) {
        ModifyProductIDTxt.setText(String.valueOf(product.getId()));
        ModifyProductNameTxt.setText(product.getName());
        ModifyProductInventoryTxt.setText(String.valueOf(product.getStock()));
        ModifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        ModifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        ModifyProductMinTxt.setText(String.valueOf(product.getMin()));
    }

    /** Initializes controller.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = MainScreenController.getProductToModify(); // Object selected from Main Screen Product Table
        assocParts = selectedProduct.getAllAssociatedParts(); // All associated parts with Product object.

        ModifyProductTopTableView.setItems(Inventory.getAllParts());

        ModifyProductTopTVPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductTopTVPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductTopTVInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductTopTVPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        ModifyProductBottomTableView.setItems(assocParts);

        ModifyProductBottomTVPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductBottomTVPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductBottomTVInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductBottomTVPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
