package ryanpeterson.inventorymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** The controller class for the Add Part Screen.
 *
 * @author Ryan Peterson
 */
public class AddPartController implements Initializable {
    Stage stage;
    Parent scene;
    /** The part ID text field.
     *
     */
    @FXML
    private TextField AddPartIDTxt;
    /** The in-house radio button.
     *
     */
    @FXML
    private RadioButton AddPartInHouseRBtn;
    /** The inventory text field.
     *
     */
    @FXML
    private TextField AddPartInventoryTxt;
    /** The machineID or companyName text field depending on the radio button selection.
     *
     */
    @FXML
    private TextField AddPartMachineIDCompanyNameTxt;
    /** The maximum quantity text field.
     *
     */
    @FXML
    private TextField AddPartMaxTxt;
    /** The minimum quantity text field.
     *
     */
    @FXML
    private TextField AddPartMinTxt;
    /** The part name text field.
     *
     */
    @FXML
    private TextField AddPartNameTxt;
    /** The outsourced radio button
     *
     */
    @FXML
    private RadioButton AddPartOutsourcedRBtn;
    /** The part price text field.
     *
     */
    @FXML
    private TextField AddPartPriceCostTxt;
    /** The part machineID or companyName label.
     *
     */
    @FXML
    private Label AddPartMachineIDCompanyNameLabel;

    /** Method that sets the machineID or companyName label to "Machine ID".
     *
     * @param event In-house radio button action.
     */
    @FXML
    void onActionAddPartScreenInHouseRBtn(ActionEvent event) {
        AddPartMachineIDCompanyNameLabel.setText("Machine ID");

    }

    /** Method that sets the machineID or companyName label to "Company Name".
     *
     * @param event Outsourced radio button action.
     */
    @FXML
    void onActionAddPartScreenOutsourcedRBtn(ActionEvent event) {
        AddPartMachineIDCompanyNameLabel.setText("Company Name");

    }

    /** Method that returns the user to the Main Screen upon clicking cancel.
     *
     * @param event Cancel button action
     * @throws IOException
     */
    @FXML
    void onActionAddPartScreenCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Adds new part to inventory then returns to Main Screen if successful.
     *
     * @param event Save button action.
     * @throws IOException
     */
    @FXML
    void onActionAddPartScreenSavePart(ActionEvent event) throws IOException {
        try {
            int id = 0;
            String name = AddPartNameTxt.getText();
            int stock = Integer.parseInt(AddPartInventoryTxt.getText());
            double price = Double.parseDouble(AddPartPriceCostTxt.getText());
            int min = Integer.parseInt(AddPartMinTxt.getText());
            int max = Integer.parseInt(AddPartMaxTxt.getText());
            int machineID;
            String companyName;
            boolean addPartSuccessful = false;

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name Field Empty");
                alert.setContentText("The name field cannot be left blank.");
                alert.showAndWait();
            } else if (min > max) {
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
                if (AddPartInHouseRBtn.isSelected()) {
                    machineID = Integer.parseInt(AddPartMachineIDCompanyNameTxt.getText());
                    InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                    newInHousePart.setId(Inventory.getNewPartID());
                    Inventory.addPart(newInHousePart);
                    addPartSuccessful = true;
                }

                if (AddPartOutsourcedRBtn.isSelected()) {
                    companyName = AddPartMachineIDCompanyNameTxt.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    newOutsourcedPart.setId(Inventory.getNewPartID());
                    Inventory.addPart(newOutsourcedPart);
                    addPartSuccessful = true;
                }

                if (addPartSuccessful == true) {
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Add Part");
            alert.setContentText("Part could not be added because a text field is empty or has an invalid value.");
            alert.showAndWait();
        }
    }

    /** Initializes controller
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPartInHouseRBtn.setSelected(true);
    }
}
