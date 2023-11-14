package ryanpeterson.inventorymanagementsystem;

/** Class that models an outsourced part.
 *
 */
public class Outsourced extends Part {
    /** The company name for the outsourced part.
     *
     * @author Ryan Peterson
     */
    private String companyName;

    /** The Constructor for a new instance of an outsourced object.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** The getter for the company name.
     *
     * @return the company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /** The setter for the company name.
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
