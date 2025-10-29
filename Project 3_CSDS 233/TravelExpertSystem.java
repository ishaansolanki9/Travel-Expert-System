import java.util.Scanner;

/**
 * TravelExpertSystem is the main class that provides a menu-driven interface
 * for interacting with the expert system.
 */
public class TravelExpertSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DecisionTree tree = new DecisionTree(); // Our main decision tree
        boolean running = true;

        // Main menu loop
        while (running) {
            System.out.println("\n--- Travel Expert System ---");
            System.out.println("1. Build Expert System (20+ destinations)");
            System.out.println("2. Find a Destination");
            System.out.println("3. Save Tree to Text File");
            System.out.println("4. Load Tree from Text File");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            // Menu choices using a switch
            switch (choice) {
                case "1":
                    tree.buildInitialTree();
                    System.out.println("Expert system initialized with 20+ destinations.");
                    break;

                case "2":
                    tree.startRecommendation();
                    break;

                case "3":
                    System.out.print("Enter filename to save tree as text (save as a .txt): ");
                    tree.saveTreeAsText(scanner.nextLine());
                    break;

                case "4":
                    System.out.print("Enter filename to load tree from text (enter as a .txt): ");
                    tree.loadTreeFromText(scanner.nextLine());
                    break;

                case "5":
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        // No need to close scanner since System.in should remain open
    }
}
