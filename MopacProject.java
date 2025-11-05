import java.util.HashMap;
import java.util.Scanner;


public class MopacProject {


   static class Task { // class for the task all inside GreenPackCLI
       String id; // id for the properties task
       String text; // the description of the task
       boolean required; // this required boolean made false by default to check if the task is required
       boolean done; // this done boolean is made false by default to check if the task is done
       Task(String id, String text, boolean required) { // constructor for the task class
           this.id = id;
           this.text = text;
           this.required = required; //
           this.done = false; // by default the task is not done
       }
   }


   static class Property {
       String id; // property id itself
       String address; // property address
       String notes = "";
       Task[] tasks;
       Property(String id, String address, Task[] tasks) {
           this.id = id;
           this.address = address;
           this.tasks = tasks;
       }
   }


   static HashMap<Integer, Property> seed() { // creates fake data for testing with the seed method
       Task[] t1 = new Task[]{ // array of tasks for the first property
           new Task("t1", "Mow designated lawn zones", true), // the first task in the array of the first properpty and if it is required
           new Task("t2", "Trim bushes (if needed))", false),
           new Task("t3", "Kill the weeds", true),
           new Task("t4", "Pick up trash and blow off debris", true)
       };
       Task[] t2 = new Task[]{ //
           new Task("t1", "Mow perimeter", true), // the first task in the array of the second property and if it is required
           new Task("t2", "Pick up trash (parking lot)", true),
           new Task("t3", "Kill the weeds", true)
       };


       HashMap<Integer, Property> properties = new HashMap<>();
       properties.put(1, new Property("fw-business1-001", "3713 Cockrell Ave, Fort Worth, TX", t1));
       properties.put(2, new Property("fw-land1-002", "6750 Mandy Ln, Fort Worth, TX", t2));


       return properties;
   }


   static void line() {
       System.out.println("------------------------------------------------------------");
   }


   static void showProperties(HashMap<Integer, Property> props) {
       line();
       System.out.println("Properties:"); // listing all my properites
       for (int i = 1; i <= props.size(); i++) { // loop through the properties map
           System.out.println(i + ") " + props.get(i).address); // print the index of the proptety plus 1 and the address of the property
       }
   }


   static void showProperty(Property p) {
       line();
       System.out.println("Address: " + p.address);
       System.out.println("Notes: " + (p.notes.length() == 0 ? "(none)" : p.notes)); // if the propterty notes length is 0 meaning its empty then print nothing, otherwise print the actualy notes
       System.out.println();
       System.out.println("Tasks:");
       for (int i = 0; i < p.tasks.length; i++) { // for loop to go through the tasks array of the property
           Task t = p.tasks[i]; // have the task t equal the current task in the loop
           String box = t.done ? "[x]" : "[ ]"; // if the task is done print [x] otherwise print [ ]
           String req = t.required ? " (required)" : "";
           System.out.println("  " + (i + 1) + ". " + box + " " + t.text + req);
       }
   }


   static void toggleTask(Property p, int index) {
       if (index < 0 || index >= p.tasks.length) {
           System.out.println("Invalid task number.");
           return;
       }
       Task t = p.tasks[index];
       t.done = !t.done;
       System.out.println("Task \"" + t.text + "\" is now " + (t.done ? "DONE" : "NOT DONE"));
   }


   static void editNotes(Scanner in, Property p) {
       line();
       System.out.println("Current notes: " + (p.notes.length() == 0 ? "(none)" : p.notes));
       System.out.print("New notes (leave empty to clear): ");
       String n = in.nextLine();
       p.notes = n.trim();
       System.out.println("Notes updated.");
   }


   static void summary(Property p) {
       int total = p.tasks.length;
       int done = 0;
       int required = 0;
       int reqDone = 0;
       for (int i = 0; i < p.tasks.length; i++) {
           Task t = p.tasks[i];
           if (t.done) done++;
           if (t.required) {
               required++;
               if (t.done) reqDone++;
           }
       }
       line();
       System.out.println("Summary for: " + p.address);
       System.out.println("Tasks done: " + done + "/" + total);
       System.out.println("Required tasks done: " + reqDone + "/" + required);
       System.out.println("Notes: " + (p.notes.length() == 0 ? "(none)" : p.notes));
   }


   //MAIN METHOD
   public static void main(String[] args) {
       Scanner in = new Scanner(System.in);
       HashMap<Integer, Property> properties = seed();
       int selected = -1;


       while (true) {
           line();
           System.out.println("Mopac (Landscaping Packet - Online)");
           System.out.println("1) List properties");
           System.out.println("2) Select property");
           System.out.println("3) Show current property");
           System.out.println("4) Toggle a task");
           System.out.println("5) Edit notes");
           System.out.println("6) Show summary");
           System.out.println("0) Exit");
           System.out.print("Choose: ");


           String choice = in.nextLine().trim();


           if (choice.equals("1")) {
               showProperties(properties);
               System.out.print("Press Enter to continue...");
               in.nextLine();
           } else if (choice.equals("2")) {
               showProperties(properties);
               System.out.print("Enter number to select: ");
               try {
                   int n = Integer.parseInt(in.nextLine().trim());
                   if (properties.containsKey(n)) {
                       selected = n;
                       System.out.println("Selected: " + properties.get(selected).address);
                   } else {
                       System.out.println("Invalid number.");
                   }
               } catch (NumberFormatException e) {
                   System.out.println("Please enter a number.");
               }
               System.out.print("Press Enter to continue...");
               in.nextLine();
           } else if (choice.equals("3")) {
               if (selected == -1) {
                   System.out.println("No property selected.");
               } else {
                   showProperty(properties.get(selected));
               }
               System.out.print("Press Enter to continue...");
               in.nextLine();
           } else if (choice.equals("4")) {
               if (selected == -1) {
                   System.out.println("Select a property first.");
                   System.out.print("Press Enter to continue...");
                   in.nextLine();
                   continue;
               }
               showProperty(properties.get(selected));
               System.out.print("Task number to toggle: ");
               try {
                   int t = Integer.parseInt(in.nextLine().trim());
                   toggleTask(properties.get(selected), t - 1);
               } catch (NumberFormatException e) {
                   System.out.println("Please enter a number.");
               }
               System.out.print("Press Enter to continue...");
               in.nextLine();
           } else if (choice.equals("5")) {
               if (selected == -1) {
                   System.out.println("Select a property first.");
               } else {
                   editNotes(in, properties.get(selected));
               }
               System.out.print("Press Enter to continue...");
               in.nextLine();
           } else if (choice.equals("6")) {
               if (selected == -1) {
                   System.out.println("Select a property first.");
               } else {
                   summary(properties.get(selected));
               }
               System.out.print("Press Enter to continue...");
               in.nextLine();
           } else if (choice.equals("0")) {
               System.out.println("Great day at work today. See you in the morning!");
               break;
           } else {
               System.out.println("Invalid option, try again.");
               System.out.print("Press Enter to continue...");
               in.nextLine();
           }
       }
       in.close();
   }
}



