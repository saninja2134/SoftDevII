import java.io.*;
import java.util.*;


// TODO TASK 13 ->>>>>>> 14 TOMMOROW NO GAMES UNTIL YOU FINISH COURSEWORK!!!!!


public class Theatre {

    // Declare arrays for each row to keep track of seat availability
    static int[] row1 = new int[12];
    static int[] row2 = new int[16];
    static int[] row3 = new int[20];
    
    // Define an array of Ticket objects with a length of 48
    static Ticket[] tickets = new Ticket[48];

    public static void main(String[] args) {
        // Welcome message
        System.out.println("Welcome to the New Theatre");
        Scanner input = new Scanner(System.in);

        // Menu
        int choice;
        do {
            System.out.println("\nPlease select an option:");
            System.out.println("1) Buy a ticket");
            System.out.println("2) Print seating area");
            System.out.println("3) Cancel ticket");
            System.out.println("4) List available seats");
            System.out.println("5) Save to file");
            System.out.println("6) Load from file");
            System.out.println("7) Print ticket information and total price");
            System.out.println("8) Sort tickets by price");
            System.out.println("0) Quit");

            System.out.print("Enter option: ");
            choice = input.nextInt();

            // Switch case to handle user choice
            switch (choice) {
                case 1:
                    buy_ticket();
                    break;
                case 2:
                    print_seating_area();
                    break;
                case 3:
                    cancel_ticket();
                    break;
                case 4:
                    show_available();
                    break;
                case 5:
                    save();
                    break;
                case 6:
                    load();
                    break;
                case 7:
                    show_tickets_info();
                    break;
                case 8:
                    sort_tickets();
                    break;
                case 0:
                    System.out.println("Quitting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    // Method to buy a ticket
    public static void buy_ticket() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter row number: ");
        int row;
        try {
            row = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid row number. Please try again.");
            return;
        }

        System.out.print("Enter seat number: ");
        int seat;
        try {
            seat = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat number. Please try again.");
            return;
        }

        // Check if the row and seat numbers are correct
        if (row < 1 || row > 3 || seat < 1 || seat > 20) {
            System.out.println("Invalid row or seat number. Please try again.");
            return;
        }

        // So that we can acess the correct row and price
        int[] rowArray;
        double price = 0.00;
        switch (row) {
            case 1:
                rowArray = row1;
                // Front row cost less as you have to look up which is uncomfortable
                // Also tends to be the least sold tickets to any cinema
                price = 10.00;
                break;
            case 2:
                rowArray = row2;
                // Middle row seats are the best position as you get the ideal veiwing angle
                // Stage is not too far or too close for it to be a problem
                price = 20.00;
                break;
            default:
                rowArray = row3;
                //Furthest row tends to be bought because its cheaper and not painful to watch
                price = 15.00;
                break;
        }

        // Check if the seat is available
        if (rowArray[seat - 1] == 1) {
            System.out.println("Seat is not available");
            return;
        } else {
            rowArray[seat - 1] = 1;

            // Prompt user for person and ticket information
            System.out.print("Enter name: ");
            String name = input.nextLine();
            System.out.print("Enter surname: ");
            String surname = input.nextLine();
            System.out.print("Enter email: ");
            String email = input.nextLine();
            Person person = new Person(name, surname, email);

            // Create a new ticket object with the entered information
            Ticket t = new Ticket(row, seat, price, person);

            // Add the ticket object to the next available index in 'tickets' array
            // initializing an iteratable variable
            int i = 0; 
            // Checks to see if the iterated object i has hit the maximum objects allowed in the array
            // Or if the value doesnt exist in the specific node, if both are true then 
            while (i < tickets.length && tickets[i] != null) {
                i++;
            }
            // If i has iterated to the maximum length of the array then the while loop above would stop
            // and the if statement below would be valid, this meaning that the tickets have all been sold
            if (i >= tickets.length) {
                System.out.println("Sorry, all tickets have been sold out.");
                return;
            }
            tickets[i] = t;

            // Output a confirmation message
            System.out.println("Ticket purchased successfully");
            return;
        }
    }


    public static void print_seating_area() {
        // Define an array to hold the output for each row
        String[] rows = new String[3];

        // Iterate over each row
        for (int i = 0; i < 3; i++) {
            // Define a variable to hold the output for the current row
            StringBuilder rowOutput = new StringBuilder();

            // Determine which row to process based on the index
            int[] row;
            if (i == 0) {
                row = row1;
            } else if (i == 1) {
                row = row2;
            } else {
                row = row3;
            }

            // Iterate over each seat in the row
            for (int j = 0; j < row.length; j++) {
                // Determine whether to print "o", "x", or " "
                if (j == row.length / 2) {
                    rowOutput.append(" ");
                }
                if (row[j] == 0) {
                    rowOutput.append("o");
                } else {
                    rowOutput.append("x");
                }
            }

            // Store the output for the current row in the rows array
            rows[i] = rowOutput.toString();
        }

        // Print out the seating area
        System.out.println("    ***********\n    *  STAGE  *\n    ***********");
        System.out.println("    " + rows[0] + "\n" + "  " + rows[1] + "\n" + rows[2]);
    }

    // Method to cancel a ticket
    public static void cancel_ticket() {
        Scanner input = new Scanner(System.in);
        
        // Prompt user for row and seat number
        System.out.print("Enter row number: ");
        int row = input.nextInt();

        System.out.print("Enter seat number: ");
        int seat = input.nextInt();
        
        // Check if the row and seat numbers are correct
        if (row < 1 || row > 3 || seat < 1) {
            System.out.println("Invalid row or seat number. Please try again.");
            return;
        }

        // Check if the seat is occupied
        int[] chosen_row;
        switch (row) {
            case 1:
                chosen_row = row1;
                break;
            case 2:
                chosen_row = row2;
                break;
            default:
                chosen_row = row3;
                break;
        }
        if (chosen_row[seat - 1] == 1) { 
            // If the seat is occupied, set it as free
            chosen_row[seat - 1] = 0; 
            
            // checking the tickets array to see if there is a Ticket object in it whose seat and row number matches with the values of seat and row variables.
            // They are searched through a for loop iterating over each index in the tickets array.
            // Once a match is found, that corresponding seat index is set to null to remove the ticket from the array, then the loop breaks using the break keyword.
            for (int i = 0; i < tickets.length; i++) {
                Ticket t = tickets[i];
                if (t != null && t.getSeat() == seat && t.getRow() == row) {
                    tickets[i] = null;
                    break;
                }
            }
            
            // Output a confirmation message
            System.out.println("Ticket cancelled.");
        } else { 
            // If the seat is already available, tell the user
            System.out.println("This seat is already available.");
        }
    }


    public static void show_available() {
        // Define a string to hold the seating availability for each row starting it out with what's needed
        StringBuilder output_statement = new StringBuilder("Seats available in row 1:");
        // For loop iterates through the array then we check to see if the value in the array is 0 or not
        // If it is then the locational data of the seat is added to the string
        for (int i = 0; i < row1.length; i++){
            if (row1[i] == 0){
                output_statement.append(" ").append(i + 1);
            }
        }
        output_statement.append("\nSeats available in row 2:");
        for (int i = 0; i < row2.length; i++){
            if (row2[i] == 0){
                output_statement.append(" ").append(i + 1);
            }
        }
        output_statement.append("\nSeats available in row 3:");
        for (int i = 0; i < row3.length; i++){
            if (row3[i] == 0){
                output_statement.append(" ").append(i + 1);
            }
        }
        // Outputting the string that is required for the task
        System.out.println(output_statement);
    }

    // This method is used to save the seating area information and tickets array to a file named "theatre.txt" in a readable format
    public static void save() {
        try {
            // Create a new file object using File class constructor
            File file = new File("theatre.txt");
            // Initialize FileWriter with created file
            FileWriter writer = new FileWriter(file); 

            // Write information about the seating arrangement to the file
            writer.write("Seating area information\n");
            writer.write("Row 1: " + Arrays.toString(row1) + "\n"); 
            writer.write("Row 2: " + Arrays.toString(row2) + "\n");
            writer.write("Row 3: " + Arrays.toString(row3) + "\n");

            // Write information about the tickets array to the file
            writer.write("Tickets:\n");
            
            // Loop over all elements in tickets array and write data for each ticket
            for(Ticket t : tickets) {
                if (t != null){
                    String data = t.getRow() + "," + t.getSeat() + "," + t.getPrice() + "," +
                                t.getPerson().getName() + "," + t.getPerson().getSurname() + "," + t.getPerson().getEmail(); 
                    writer.write(data + "\n"); 
                }else {
                    break;
                }
            }

            writer.close(); // Close the writer to save changes made to the file

            System.out.println("Information saved to file."); // Print confirmation message
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file."); // Handle exception if found
        }
    }

    // This method is used to load the seating area information and tickets array from a file named "theatre.txt"
    public static void load() {
        try {
            // Create a new file object using File class constructor
            File file = new File("theatre.txt");
            // Initialize Scanner to read from the file
            Scanner input = new Scanner(file);

            // Clear the arrays
            tickets = new Ticket[48];
            row1 = new int[12];
            row2 = new int[16];
            row3 = new int[20];

            // Discard the first line ("Seating area information")
            input.nextLine();

            // Read each row of seating data and update the corresponding array
            String row1Data = input.nextLine().substring(6); // remove "Row 1: "
            row1Data = row1Data.replace("[", ""); // removes the [] from the string it hinders the parseInt
            String[] row1Strings = row1Data.substring(1, row1Data.length() - 1).split(", ");
            for (int i = 0; i < row1Strings.length; i++) {
                row1[i] = Integer.parseInt(row1Strings[i]);
            }

            String row2Data = input.nextLine().substring(6); // remove "Row 2: "
            row2Data = row2Data.replace("[", ""); // removes the [] from the string it hinders the parseInt
            String[] row2Strings = row2Data.substring(1, row2Data.length() - 1).split(", ");
            for (int i = 0; i < row2Strings.length; i++) {
                row2[i] = Integer.parseInt(row2Strings[i]);
            }

            String row3Data = input.nextLine().substring(6); // remove "Row 3: "
            row3Data = row3Data.replace("[", ""); // removes the [] from the string it hinders the parseInt
            String[] row3Strings = row3Data.substring(1, row3Data.length() - 1).split(", ");
            for (int i = 0; i < row3Strings.length; i++) {
                row3[i] = Integer.parseInt(row3Strings[i]);
            }

            // Discard the next line ("Tickets:")
            input.nextLine();
            int i = 0;

            // Loop over each line in the file and create a new Ticket object for it
            while (input.hasNextLine()) {
                String[] lineData = input.nextLine().split(",");
                int row = Integer.parseInt(lineData[0]);
                int seat = Integer.parseInt(lineData[1]);
                double price = Double.parseDouble(lineData[2]);
                String name = lineData[3];
                String surname = lineData[4];
                String email = lineData[5];

                Person person = new Person(name, surname, email);
                Ticket ticket = new Ticket(row, seat, price, person);
                tickets[i] = ticket;
                i++;
            }

            input.close(); // Close the scanner

            System.out.println("Seating information and tickets loaded from file."); // Print confirmation message
        } catch (IOException e) {
            System.out.println("An error occurred while loading the file."); // Handle exception if found
        } catch (NumberFormatException e) {
            System.out.println("Invalid format in the file."); // Handle exception if found
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of bounds in the file."); // Handle exception if found
        }
    }

    public static void show_tickets_info() {
        double total_price = 0;
        //  Iterates over all non-null elements in the tickets array
        for (Ticket t : tickets) {
            if (t != null) {
                // Uses the ticket function print to output all the data of the ticket
                t.print();
                System.out.println();
                // Adds its price to the total_price variable
                total_price += t.getPrice();
            }
        }
        // prints the total price with a formatted string that rounds it to two decimal places
        System.out.println("Total price: £" + String.format("%.2f", total_price));
    }

    public static void sort_tickets() {
    // Make a copy of the tickets array to avoid modifying original
    Ticket[] sortedTickets = Arrays.copyOf(tickets, tickets.length);
    
    // Implementing bubble sort
    for (int i = 0; i < sortedTickets.length; i++) {
        boolean isSwapped = false;
        for (int j = 0; j < sortedTickets.length - i - 1; j++) {
            if (sortedTickets[j] == null || sortedTickets[j + 1] == null) {
                Ticket temp = sortedTickets[j];
                sortedTickets[j] = sortedTickets[j + 1];
                sortedTickets[j + 1] = temp;
                isSwapped = true;
            } else if (sortedTickets[j].getPrice() > sortedTickets[j + 1].getPrice()) {
                Ticket temp = sortedTickets[j];
                sortedTickets[j] = sortedTickets[j + 1];
                sortedTickets[j + 1] = temp;
                isSwapped = true;
            }
        }
        // If no elements were swapped during a traversal, then the array is already sorted
        if (!isSwapped) {
            break;
        }
    }

    // Loop over all elements in sortedTickets array and print ticket information
    for (Ticket t : sortedTickets) {
        if (t != null) {
            t.print();
            System.out.println(); 
        } else { 
            continue;
        }
    }
}


    
}