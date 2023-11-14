package ryanpeterson.inventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class that models product that may contain associated parts.
 *
 * @author Ryan Peterson
 */
public class Product {
    /** The ID of the product.
     *
     */
    private int id;
    /** The name of the product.
     *
     */
    private String name;
    /** The price of the product.
     *
     */
    private double price;
    /** The current inventory of the product.
     *
     */
    private int stock;
    /** The minimum allowed inventory of the product.
     *
     */
    private int min;
    /** The maximum allowed inventory of the product.
     *
     */
    private int max;
    /** A list of all the parts associated with the product.
     *
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** The constructor for an instance of a product object.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** The getter for the ID of the product.
     *
     * @return the ID of the product.
     */
    public int getId() {
        return id;
    }

    /** The getter for the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /** The getter for the price of the product.
     *
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /** The getter for the current inventory of the product.
     *
     * @return The current inventory of the product.
     */
    public int getStock() {
        return stock;
    }

    /** The getter for the minimum allowed quantity of the product.
     *
     * @return The minimum quantity allowed of the product.
     */
    public int getMin() {
        return min;
    }

    /** The getter for the maximum allowed quantity of the product.
     *
     * @return The maximum quantity allowed of the product.
     */
    public int getMax() {
        return max;
    }

    /** The setter for the ID of the product.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /** The setter for the name of product.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** The setter for the price of the product.
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** The setter for the current inventory of the product.
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** The setter for the minimum allowed quantity of the product.
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** The setter for the maximum allowed quantity of the part.
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Method used to add a part to a products associated parts list.
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Method used to delete an associated part from a product.
     *
     * @param selectedAssociatedPart
     * @return boolean whether part was deleted from the associated parts list or not.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** The getter to return a list of all associatedParts.
     *
     * @return A list of associatedParts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
