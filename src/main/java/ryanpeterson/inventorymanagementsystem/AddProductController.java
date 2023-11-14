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
import java.util.ResourceBundle;

/** Controller class for the add product screen.
 *
 * @author Ryan Peterson
 */
public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;
    /** List containing parts associated with the product.
     *
     */
    ObservableList<Part> assocParts = FXCollections.observableArrayList();
    /** The lower table view on the add product screen.
     *
     */
    @FXML
    private TableView<Part> AddProductBottomTV;
    /** The inventory column for the lower table view.
     *
     */
    @FXML
    private TableColumn<?, ?> AddProductBottomTVInventoryCol;
    /** The part ID column for the lower table view.
     *
     */
    @FXML
    private TableColumn<?, ?> AddProductBottomTVPartIDCol;
    /** The part name column for the lower table view.
     *
     */
    @FXML
    private TableColumn<?, ?> AddProductBottomTVPartNameCol;
    /** The part price column for the lower table view.
     *
     */
    @FXML
    private TableColumn<?, ?> AddProductBottomTVPriceCol;
    /** The product ID text field.
     *
     */
    @FXML
    private TextField AddProductIDTxt;
    /** The product inventory text field.
     *
     */
    @FXML
    private TextField AddProductInventoryTxt;
    /** The product maximum quantity text field.
     *
     */
    @FXML
    private TextField AddProductMaxTxt;
    /** The product minimum quantity text field.
     *
     */
    @FXML
    private TextField AddProductMinTxt;
    /** The product name text field.
     *
     */
    @FXML
    private TextField AddProductNameTxt;
    /** The product price text field.
     *
     */
    @FXML
    private TextField AddProductPriceTxt;
    /** The product search text field.
     *
     */
    @FXML
    private TextField AddProductSearchTxt;
    /** The upper table view on the add product screen.
     *
     */
    @FXML
    private TableView<Part> AddProductTopTV;
    /** The inventory column for the upper table view.
     *
     */
    @FXML
    private TableColumn<Part, Integer> AddProductTopTVInventoryCol;
    /** The part ID column for the upper table view.
     *
     */
    @FXML
    private TableColumn<Part, Integer> AddProductTopTVPartIDCol;
    /** The part name column for the upper table view.
     *
     */
    @FXML
    private TableColumn<Part, String> AddProductTopTVPartNameCol;
    /** The price column for the upper table view.
     *
     */
    @FXML
    private TableColumn<Part, Double> AddProductTopTVPriceCol;

    /** Adds part object selected in the all parts table to the associated parts table.
     *
     * @param event Add button action.
     */
    @FXML
    void onActionAddProductScreenAddProduct(ActionEvent event) {
        Part selectedPart = AddProductTopTV.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to add to the associate parts table.");
            alert.showAndWait();
        }
        else {
            assocParts.add(selectedPart);
            AddProductBottomTV.setItems(assocParts);
        }
    }

    /** Returns the user to the Main Screen menu upon clicking the cancel button.
     *
     * @param event Cancel button action.
     * @throws IOException
     */
    @FXML
    void onActionAddProductScreenCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Removes selected part from the associated parts table view.
     *
     * @param event Remove Associated Part button action.
     */
    @FXML
    void onActionAddProductScreenRemoveProduct(ActionEvent event) {
        Part selectedPart = AddProductBottomTV.getSelectionModel().getSelectedItem();

        assocParts.remove(selectedPart);
        AddProductBottomTV.setItems(assocParts);

    }

    /** Adds new product to inventory with associated parts.
     *
     * @param event Save button action.
     * @throws IOException
     */
    @FXML
    void onActionAddProductScreenSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = 0;
            String name = AddProductNameTxt.getText();
            int stock = Integer.parseInt(AddProductInventoryTxt.getText());
            double price = Double.parseDouble(AddProductPriceTxt.getText());
            int min = Integer.parseInt(AddProductMinTxt.getText());
            int max = Integer.parseInt(AddProductMaxTxt.getText());

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

                newProduct.setId(Inventory.getNewProductID());
                Inventory.addProduct(newProduct);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Add Product");
            alert.setContentText("Product could not be added because a text field is empty or has an invalid value.");
            alert.showAndWait();
        }
    }

    /** Search action that accepts a part ID or part name. If no part is found, an error message appears.
     *
     * @param event Search button action.
     */
    @FXML
    void onActionAddProductScreenSearch(ActionEvent event) {
        ObservableList<Part> allPartsList = Inventory.getAllParts();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        String searchString = AddProductSearchTxt.getText();

        for (Part part : allPartsList) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                filteredParts.add(part);
            }
        }
        AddProductTopTV.setItems(filteredParts);

        if (filteredParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Window");
            alert.setHeaderText("Not found");
            alert.setContentText("No part found matching that ID or name.");
            alert.showAndWait();
        }

    }

    /** Initializes the controller.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AddProductTopTV.setItems(Inventory.getAllParts());

        AddProductTopTVPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductTopTVPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductTopTVInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductTopTVPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        AddProductBottomTVPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductBottomTVPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductBottomTVInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductBottomTVPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}
