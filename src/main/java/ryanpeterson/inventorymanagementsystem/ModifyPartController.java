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

/** Class controller for modify part screen.
 *
 * @author Ryan Peterson
 */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;
    /** The part object selected in the Main Screen controller.
     *
     */
    private static Part selectedPart;
    /** The part ID text field.
     *
     */
    @FXML
    private TextField ModifyPartIDTxt;
    /** The In-House radio button.
     *
     */
    @FXML
    private RadioButton ModifyPartInHouseRBtn;
    /** The inventory text field.
     *
     */
    @FXML
    private TextField ModifyPartInventoryTxt;
    /** The machineID or companyName text field.
     *
     */
    @FXML
    private TextField ModifyPartMachineIDCompanyNameTxt;
    /** The maximum quantity text field.
     *
     */
    @FXML
    private TextField ModifyPartMaxTxt;
    /** The minimum quantity text field.
     *
     */
    @FXML
    private TextField ModifyPartMinTxt;
    /** The part name text field.
     *
     */
    @FXML
    private TextField ModifyPartNameTxt;
    /** The Outsourced radio button.
     *
     */
    @FXML
    private RadioButton ModifyPartOutsourcedRBtn;
    /** The price text field.
     *
     */
    @FXML
    private TextField ModifyPartPriceCostTxt;
    /** The toggle group for the in-house and outsourced radio buttons.
     *
     */
    @FXML
    private ToggleGroup ModifyPartTG;
    /** The machineID or companyName label.
     *
     */
    @FXML
    private Label ModifyPartMachineIDCompanyNameLabel;

    @FXML
    void onActionModifyPartScreenInHouseRBtn(ActionEvent event) {
        ModifyPartMachineIDCompanyNameLabel.setText("Machine ID");

    }

    @FXML
    void onActionModifyPartScreenOutsourcedRBtn(ActionEvent event) {
        ModifyPartMachineIDCompanyNameLabel.setText("Company Name");

    }

    /** Returns the user to the main screen.
     *
     * @param event Cancel button action.
     * @throws IOException
     */
    @FXML
    void onActionModifyPartScreenCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Replaces part in inventory and returns user to the Main Screen.
     *
     * @param event Save button action.
     * @throws IOException
     */
    @FXML
    void onActionModifyPartScreenSavePart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(ModifyPartIDTxt.getText());
            String name = ModifyPartNameTxt.getText();
            int stock = Integer.parseInt(ModifyPartInventoryTxt.getText());
            double price = Double.parseDouble(ModifyPartPriceCostTxt.getText());
            int min = Integer.parseInt(ModifyPartMinTxt.getText());
            int max = Integer.parseInt(ModifyPartMaxTxt.getText());
            boolean modifyPartSuccessful = false;

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
                if (ModifyPartInHouseRBtn.isSelected()) {
                    int machineID = Integer.parseInt(ModifyPartMachineIDCompanyNameTxt.getText());
                    Inventory.updatePart(id - 1, new InHouse(id, name, price, stock, min, max, machineID));
                    modifyPartSuccessful = true;
                }
                if (ModifyPartOutsourcedRBtn.isSelected()) {
                    String companyName = ModifyPartMachineIDCompanyNameTxt.getText();
                    Inventory.updatePart(id - 1, new Outsourced(id, name, price, stock, min, max, companyName));
                    modifyPartSuccessful = true;
                }
                if (modifyPartSuccessful == true) {
                    Inventory.deletePart(selectedPart);
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
            alert.setHeaderText("Cannot Modify Part");
            alert.setContentText("Part could not be modified because a text field is empty or has an invalid value.");
            alert.showAndWait();
        }
    }

    /** Passes part object from Main Screen to Modify Part screen.
     *
     * @param part part object selected from the main screen.
     */
    public void passPart(Part part) {
        ModifyPartIDTxt.setText(String.valueOf(part.getId()));
        ModifyPartNameTxt.setText(part.getName());
        ModifyPartInventoryTxt.setText(String.valueOf(part.getStock()));
        ModifyPartPriceCostTxt.setText(String.valueOf(part.getPrice()));
        ModifyPartMaxTxt.setText(String.valueOf(part.getMax()));
        ModifyPartMinTxt.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse) {
            ModifyPartInHouseRBtn.setSelected(true);
            ModifyPartMachineIDCompanyNameLabel.setText("Machine ID");
            ModifyPartMachineIDCompanyNameTxt.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else {
            ModifyPartOutsourcedRBtn.setSelected(true);
            ModifyPartMachineIDCompanyNameLabel.setText("Company Name");
            ModifyPartMachineIDCompanyNameTxt.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }
    }

    /** Initializes controller.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModifyPartInHouseRBtn.setSelected(true);
        selectedPart = MainScreenController.getPartToModify();
    }

}