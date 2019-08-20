
/**
 * The Ticket GUI is a two tabbed pane GUI which collects ticket information. It takes the information and passes it to 
 * another class. The Order Class puts it into an arraylist and returns them. The Ticket GUI displays the information and
 * the user can save the information in a text document or clear the information.
 * 
 * @author (Marcus Anderson) 
 * @version (17/05/2016)
 */
// Imports all things necessary for the program to run, 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import java.util.*;
// The main Class to run the GUI of the program.
class TicketGUI extends JFrame{
    private     JTabbedPane tabbedPane;
    private     JPanel      panel1;
    private     JPanel      panel2;
    private     JLabel      label6 = new JLabel();
    private     JComboBox   venue;
    private     JComboBox   event;
    private     JComboBox   seats;
    private     JButton     getTickets;
    private     JButton     confirmOrder;
    private     JButton     clearOrder;
    private     JTextField  firstName;
    private     JTextField  lastName;
    private     JTextArea   orderList;
    private     SpinnerNumberModel  quantity;
    private     String[]    eventStrings = { "Black Sabbath", "Pink Floyd", "Disturbed", "Fleetwood Mack", "AC/DC", "Bon Jovi", "Def Leppard", "Guns'N'Roses", "Halestorm", "Iron Maiden", "Devilskin", 
                                             "Dropbox", "Evanescence", "Greenday", "Led Zepplin", "Linkin Park", "Metallica", "Nickelback", "Tenacious D" };
    private     double[]    multiply = {1.5, 1.7, 1, 1.1, 0.9, 0.97, 1.45, 1.23, 1.2, 1.25, 0.89, 0.75, 0.95, 1.12, 1.5, 1.32, 1.65, 3.65, 1.21};
    private     String[]    seatStrings = { "Platnium Standing", "Platnium Sitting", "Gold Standing", "Gold Sitting", "Silver Standing", "Silver Sitting" };
    private     int[]       seatPrices = {220, 150, 170, 120, 100, 50};
    public      double      newPrice = 220;
    public      double      multiplier = 1.5;
    public      double      finalPrice;
    public      String      fName;
    public      String      lName;
    public      String      setVenue;
    public      String      setEvent;
    public      int         TicketQuantity;
    private     int         CountI = 0;
    private     int         CountM = 0;
    private     ArrayList<Double> AbsolutePrice = new ArrayList<Double>();
    // Creates an instance of the Orders class
    private     Orders      order = new Orders();
    public TicketGUI(){
        // Creates the base design of the GUI, it being a two tabbed program. It takes no inputs, only outputs the display and meaningful because of the information it displays
        setTitle( "Ticket Ordering Program" );
        setSize( 600, 400 );
        setBackground( Color.lightGray );

        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );

        // Create the tab pages
        addTickets();
        viewOrders();
        // Create a tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab( "Home - Add tickets", panel1 );
        tabbedPane.addTab( "View orders and checkout", panel2 );
        topPanel.add( tabbedPane, BorderLayout.CENTER );
    }
    
    // The addTickets method is a null layout on the first tab. It contains displays the information to generate a Ticket for a show
    public void addTickets(){
        // Creates the base layout for the 1st tab. It sets it to a null layout and all the components will be added to this panel
        // I prefer a null layout because I then have control over where all the components go on the main panel
        // Creates a new Action listner which will be used by the components in this method
        EventListener listenera = new EventListener();
        panel1 = new JPanel();
        panel1.setLayout( null );
        panel1.setBackground(Color.white);

        JLabel label1 = new JLabel( "Venue" );
        label1.setBounds( 10, 15, 150, 20 );
        panel1.add( label1 );
        
        // Generates a combo box which takes the input of the array venueStrings and displays it to the user as an output. The user will then select which venue they want
        String[] venueStrings = { "Vector Arena", "AMI Stadium", "Eden Park", "Forsyth Barr", "Sabbath Stadium" };
        venue = new JComboBox<String>(venueStrings);
        venue.setSelectedIndex(0);
        venue.setBounds( 10, 45, 150, 20 );
        panel1.add( venue );
        
        JLabel label2 = new JLabel( "Event" );
        label2.setBounds( 200, 15, 150, 20 );
        panel1.add( label2 );
        
        // Generates a combo box which takes the input of the array eventStrings and displays it to the user as an output. The user will then select which event they want
        // There is a listner so whenever the user changes the value of the combo box the program reacts to that
        event = new JComboBox<String>(eventStrings);
        event.addActionListener(listenera);
        event.setSelectedIndex(0);
        event.setBounds( 200, 45, 150, 20 );
        panel1.add( event );
        
        JLabel label4 = new JLabel( "Seats and Prices" );
        label4.setBounds( 10, 100, 150, 20 );
        panel1.add( label4 );
        
        // Generates a combo box which takes the input of the array seatStrings and displays it to the user as an output. The user will then select which seat they want
        // There is a listner so whenever the user changes the value of the combo box the program reacts to that
        seats = new JComboBox<String>(seatStrings);
        seats.addActionListener(listenera);
        seats.setSelectedIndex(0);
        seats.setBounds( 10, 130, 250, 20 );
        panel1.add( seats );
        
        JLabel label5 = new JLabel( "Price per Ticket" );
        label5.setBounds( 287, 100, 150, 20 );
        panel1.add( label5 );
        
        // Inputs finalPrice after it being changed by the listner. It outputs it's value and is always updating. It tells the user what each ticket costs individually
        finalPrice = newPrice * multiplier;
        DecimalFormat round = new DecimalFormat("##.00");
        label6.setText("$" + round.format(finalPrice));
        label6.setBounds( 287, 130, 150, 20 );
        panel1.add( label6 );
        
        JLabel label3 = new JLabel( "Quantity" );
        label3.setBounds( 420, 100, 150, 20 );
        panel1.add( label3 );
        
        // The user can select what the quantity is for there order. It has a limit that can not be broken. It inputs a certain value which is defined by the user
        quantity = new SpinnerNumberModel(0, 0, 10, 1);
        JSpinner spinner1 = new JSpinner(quantity);
        spinner1.setBounds( 420, 130, 60, 20 );
        panel1.add (spinner1);
        
        JLabel label7 = new JLabel( "First Name" );
        label7.setBounds( 10, 185, 150, 20 );
        panel1.add( label7 );
        
        // This is a text field which the user will input in their information, it can handle anything as long as it is between 1 and 50 characters
        firstName = new JTextField();
        firstName.setBounds( 10, 215, 190, 20 );
        panel1.add( firstName );
        
        JLabel label8 = new JLabel( "Last Name" );
        label8.setBounds( 287, 185, 150, 20 );
        panel1.add( label8 );
        
        // This is a text field which the user will input in their information, it can handle anything as long as it is between 1 and 70 characters
        lastName = new JTextField();
        lastName.setBounds( 287, 215, 220, 20 );
        panel1.add( lastName );
        
        // At this point the program will take the information provided with the text fiels, combo boxes and spinner number model and will collect that information and send it away
        // It takes all of the information (venue, event, seat, quantity first and last names) as an input 
        // With the listner, it will output that information
        getTickets = new JButton("Get Tickets!");
        getTickets.addActionListener(listenera);
        getTickets.setBounds( 200, 270, 150, 20);
        panel1.add(getTickets);
    }
    
    // This method displays the ticket information that has been entered
    public void viewOrders(){
        // Creates the base layout for the 2nd tab. It sets it to a null layout and all the components will be added to this panel
        // I prefer a null layout because I then have control over where all the components go on the main panel
        // Creates a new Action listner which will be used by the components in this method
        EventListener listenerb = new EventListener();
        panel2 = new JPanel();
        panel2.setLayout( null );
        panel2.setBackground(Color.white);
        
        JLabel label9 = new JLabel( "Order List:" );
        label9.setBounds( 10, 10, 150, 20 );
        panel2.add( label9 );
        
        // Shows the ticket information entered. The scroll panes act to what is entered into the text area so the user can always see the information
        orderList = new JTextArea("This will show what ticket orders you have entered ", 50, 10);
        JScrollPane scrollPane = new JScrollPane(orderList);
        panel2.add(scrollPane);
        scrollPane.setBounds (10, 30, 555, 230 );
        orderList.setEditable(false);
        
        // Here the button tells the program to take all of the information and save it to a text document
        // It outputs the information in the text area orderList
        confirmOrder = new JButton("Confirm Order");
        confirmOrder.addActionListener(listenerb);
        confirmOrder.setBounds( 100, 285, 150, 20);
        panel2.add(confirmOrder);
        
        // If the button is clicked, it clears all of the information in the text area
        clearOrder = new JButton("Clear all entries");
        clearOrder.addActionListener(listenerb);
        clearOrder.setBounds( 300, 285, 150, 20);
        panel2.add(clearOrder);
    }
    
    // Main method to get the program running
    public static void main( String [] args ){
        // Create an instance of the Ticket GUI
        TicketGUI mainFrame  = new TicketGUI();
        mainFrame.setVisible( true );
    }
    
    // This method takes in the strings from the first and last name and checks to see if they have a suitable amount of charachters.
    // This method outputs boolean values to check if the amount of charachters are between the limits 
    public  Boolean checkStringLength( String inputString, Integer minLength, Integer maxLength ){
        int stringLength = inputString.length();
        if (stringLength >= minLength && stringLength <= maxLength){
            return true;
        } else{
            return false;
        }
    }
 
    // This method import order sends the information of to the Orders class and then sends it back to this method. This method takes in the set data in the first tab
    public void importOrder (){
        // Sets Arraylists to become different objects when sent to the Orders class. this eventually outputs as arraylists and will be shown in the text area
        ArrayList outputInfo = order.createNewOrder(fName, lName, setVenue, setEvent);
        ArrayList outputPrice = order.generatePrice(TicketQuantity, finalPrice);
        
        // Once the arraylists are set, It makes the double values set to the correct decimal point
        DecimalFormat round = new DecimalFormat("##.00");
        DecimalFormat intRound = new DecimalFormat("#0");
        // This part adds in a new order and displays it in the text area. It outputs the correct text to the user for them to check if their order is correct
        orderList.append("\n" + "\n" + outputInfo.get(CountI) + " " + outputInfo.get(CountI+1) + ",\n" + 
                         "You are going to see " + outputInfo.get(CountI+2) + " at the " + outputInfo.get(CountI+3) + "\n" +
                         "Price per ticket:    $" + round.format(outputPrice.get(CountM)) + "\n" +
                         "Quantity:                 " + intRound.format(outputPrice.get(CountM+1)) + "\n" + 
                         "Total Price:           $" + round.format(outputPrice.get(CountM+2)) + "\n");
        
        // This section takes the total price before and adds it to it's own seperate arraylist which won't be shown until the user confirms their order. 
        // It is important so the user knows the total amount of all the tickets combined to know what they will be paying
        Object o = outputPrice.get(CountM+2);
        Number n = (Number) o;
        double addingAmount = n.doubleValue();
        AbsolutePrice.add(addingAmount);
    }
    
    // private Ticket GUI Listner class 
    private class EventListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
           if(e.getSource() == event){
               // Takes in the object event and converts it too a String then checks what index it is. With it's index found it sets a variable to be equal to the same index of the Array multiply
               // With that found it displays the new price with the selected event as an output so the user knows the new price for each ticket
               String chosenEvent = (String) event.getSelectedItem();
               for (int i=0; i < eventStrings.length; i++) {
                   if (eventStrings[i].equals(chosenEvent)) {
                       multiplier = multiply[i];
                       DecimalFormat round = new DecimalFormat("##.00");
                       finalPrice = newPrice * multiplier;
                       label6.setText("$" + round.format(finalPrice));
                       label6.setBounds( 287, 130, 150, 20 );
                       panel1.add( label6 );
                   }    
                }
            }
           if(e.getSource() == seats){
               // Takes in the object event and converts it too a String then checks what index it is. With the index found it sets a variable to be equal to the same index of the Array seatPrices
               // With that found it displays the new price with the selected seat as an output so the user knows the new price for each ticket
               String chosenSeat = (String) seats.getSelectedItem();
               for (int i=0; i <seatStrings.length; i++) {
                   if (seatStrings[i].equals(chosenSeat)) {
                       newPrice = seatPrices[i];
                       DecimalFormat round = new DecimalFormat("##.00");
                       finalPrice = newPrice * multiplier;
                       label6.setText("$" + round.format(finalPrice));
                       label6.setBounds( 287, 130, 150, 20 );
                       panel1.add( label6 );
                   }    
                }
            }
           if(e.getSource() == getTickets){
                // When the getTickets button is pressed by the user this listner reacts to what data has been entered. It takes the data in the first tab as inputs
                fName = firstName.getText();
                lName = lastName.getText();   
                // Sends the First and Last names to go check if the charachters are between the limits
                Boolean testfName = checkStringLength(fName,1,50);
                Boolean testlName = checkStringLength(lName,1,70);
                // This if statement takes in the boolean variables and checks if they are true or false
                // If false, the program brings up a error message saying that the data the user has entered is invalid
                if (testfName == false || testlName == false){
                   JOptionPane.showMessageDialog(new JFrame(),
                   "Error - first needs to be between 1 and 50 characters in length and the last need to be between 1 and 70 charachters",
                   "Name entry error",
                   JOptionPane.ERROR_MESSAGE);
                }
                // If the boolean variables are true the program continues. It converts the object from quantity into a Integer
                // It takes the data from tab 1 as an input and sends it to the importOrder method
                else{
                    setVenue = venue.getSelectedItem().toString();
                    setEvent = event.getSelectedItem().toString();
                    Object o = quantity.getValue();
                    Number n = (Number) o;
                    TicketQuantity = n.intValue();
                    importOrder();
                }
           }
           // If the clearOrder button is hit, this if statement clears the text area to it's original form and clears the AbsolutePrice arraylist back to zero
           // This is important to the user to show that there is no more orders that they can buy
           if(e.getSource() == clearOrder){
               orderList.setText("This will show what ticket orders you have entered ");
               AbsolutePrice.clear();
           }
           // At this part the information in the text area will be saved in a text document so the user can print of the ticket information if they want
           if(e.getSource() == confirmOrder){
               // Finds the full price of all the tickets by adding every item in the AbsolutePrice together for the full price
               double payPrice = 0.00;
               for (Iterator<Double> i = AbsolutePrice.iterator(); i.hasNext(); ) {
                   double adding = i.next();
                   payPrice = payPrice + adding;
               }
               // Displays an option message if the user wants to continue with purchaseing their tickets. 
               // It displays the total price combined and is useful for the user if they clicked the wrong button
               DecimalFormat round = new DecimalFormat("##.00");
               int reply = JOptionPane.showConfirmDialog(new JFrame(),
                    "The total cost will be $" + round.format(payPrice) + ", If yes then a file with the ticket information will be saved in your Documents folder",
                    "Purchase Confirmation",
                    JOptionPane.YES_NO_OPTION);
               // This if statement takes the result from the option pane as an input and checks to see if the user clicked yes
               if (reply == JOptionPane.YES_OPTION){ 
                   try {
                       // Begins to save the information in the text area into the text file
                       String saveFile = orderList.getText();
                       // Save directory will have to be changed for each computer
                       File newTextFile = new File("C:/Users/marcusa/Documents/Tickets.txt");
                       saveFile = saveFile + "\n" + "\n" + "The total cost will be $" + round.format(payPrice);
                       String outString = saveFile.replaceAll("\n", System.lineSeparator());
                       
                       FileWriter fw = new FileWriter(newTextFile);
                       fw.write(outString);
                       fw.close();
    
                   }  catch (IOException iox) {
                       // Do stuff with exception
                       iox.printStackTrace();
                   }
                   // Creates another option message if the user wants to continue with ordering or not
                   int reply2 = JOptionPane.showConfirmDialog(new JFrame(),
                        "The Tickets have been saved, however if wish to make another order and confirm it, the new information will overide the old ticket information" + "\n"
                        + "Do you want to make another purchase?",
                        "Continue",
                        JOptionPane.YES_NO_OPTION);
                   // This if statement takes the result from the option pane as an input and checks to see if the user clicked no
                   if (reply2 == JOptionPane.NO_OPTION){
                       // If the user clicked no the program closes
                       System.exit(0);
                   }
               }
           }
        } 
    }
}
