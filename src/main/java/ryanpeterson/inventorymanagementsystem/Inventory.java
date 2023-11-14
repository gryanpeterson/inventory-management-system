package ryanpeterson.inventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class that models the inventory of parts and products.
 *
 * @author Ryan Peterson
 */
public class Inventory {
    /** The ID for a part. It is the variable used for unique part IDs.
     *
     */
    private static int partID = 0;
    /** The ID for a product. It is the variable used for unique product IDs.
     *
     */
    private static int productID = 0;
    /** The list of all parts in inventory.
     *
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /** The list of all products in inventory.
     *
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Method used to add a part to the allParts inventory list.
     *
     * @param part
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /** Method used to add a product to the allProducts inventory list.
     *
     * @param product
     */
    public static void addProduct(Product product) {
        allProducts.add((product));
    }

    /** Method used to search allParts list by ID.
     *
     * @param partID
     * @return The part object. Returns null if part is not found.
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /** Method used to search allProducts list by ID.
     *
     * @param productID
     * @return The product object. Returns null if product is not found.
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /** Method that searches allParts list by name.
     *
     * @param name
     * @return A list of parts found matching the name.
     */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> filteredByName = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().equals(name)) {
                filteredByName.add(part);
            }
        }
        return filteredByName;
    }

    /** Method that searches allProducts list by name.
     *
     * @param name
     * @return A list of products found matching the name.
     */
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> filteredByName = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().equals(name)) {
                filteredByName.add(product);
            }
        }
        return filteredByName;
    }

    /** Method that updates the part in the list of allParts.
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);

    }

    /** Method that updates the product in the list of allProducts.
     *
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /** Method that deletes a part from the allParts list.
     *
     * @param selectedPart
     * @return boolean indicating whether the parts was removed or not.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Method that deletes a product from the allProducts list.
     *
     * @param selectedProduct
     * @return boolean indicating whether the product was removed or not.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** The getter for the allParts list.
     *
     * @return A list of all parts in inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** The getter for the allProducts list.
     *
     * @return A list of all products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** The getter for retrieving a new part ID.
     *
     * @return A new part ID.
     */
    public static int getNewPartID() {
        return ++partID;
    }

    /** The getter for retrieving a new product ID.
     *
     * @return A new product ID.
     */
    public static int getNewProductID() {
        return ++productID;
    }
}
