package ryanpeterson.inventorymanagementsystem;

/** Class that models an in-house part.
 *
 * @author Ryan Peterson
 */
public class InHouse extends Part {
    /** The machine ID for the in-house part.
     *
     */
    private int machineID;

    /** The constructor for a new instance of an in-house object.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** The getter for the machineID
     *
     * @return machineID of the part
     */
    public int getMachineID() {
        return machineID;
    }

    /** The setter for the machine ID
     *
     * @param machineID the machine ID of the part
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
