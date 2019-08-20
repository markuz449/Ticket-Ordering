
/**
 * The information sent from TicketGUI is put into ArrayLists and then returned to the main program.
 * 
 * @author (Marcus Anderson) 
 * @version (27/05/2016)
 */
import java.util.ArrayList;
public class Orders{
    // Takes in the data from the first tab and sorts out the data and adds the items into an arraylist
    // It outputs the new arraylist with the data added in and is used for the user to display
    public ArrayList createNewOrder (String fName, String lName, String setVenue, String setEvent){
        ArrayList<String> outputInfo = new ArrayList<String>();
        outputInfo.add(fName);
        outputInfo.add(lName);
        outputInfo.add(setEvent);
        outputInfo.add(setVenue);
        return outputInfo;
    }
    public ArrayList generatePrice ( int TicketQuantity, double finalPrice){
        double qty = TicketQuantity;
        double totalPrice = TicketQuantity * finalPrice;
        ArrayList<Double> outputPrice = new ArrayList<Double>();
        outputPrice.add(finalPrice);
        outputPrice.add(qty);
        outputPrice.add(totalPrice);
        return outputPrice;
    }
}   
