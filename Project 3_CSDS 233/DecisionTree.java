import java.io.*;
import java.util.*;

/**
 * Manages the binary decision tree for recommending destinations.
 */
public class DecisionTree {
    private TreeNode root;
    private final Scanner scanner = new Scanner(System.in);

    public DecisionTree() {
        root = null;
    }

    /**
     * Builds a clean initial tree with 20+ preset destinations.
     */
    public void buildInitialTree() {
        root = new TreeNode("Do you like warm weather?");

        // Cold destinations
        TreeNode cold = new TreeNode("Do you like snowy vacations?");
        TreeNode urban = new TreeNode("Do you prefer urban destinations?");
        urban.left = new TreeNode("Reykjavik");

        TreeNode culture = new TreeNode("Do you enjoy cultural landmarks?");
        TreeNode ruins = new TreeNode("Do you like historic ruins?");
        ruins.left = new TreeNode("Amsterdam");
        ruins.right = new TreeNode("Rome");

        TreeNode museums = new TreeNode("Do you enjoy museums?");
        museums.left = new TreeNode("New York City");
        museums.right = new TreeNode("Paris");

        TreeNode tech = new TreeNode("Do you enjoy technology and anime?");
        tech.left = new TreeNode("Seoul");
        tech.right = new TreeNode("Tokyo");

        museums.left.left = tech; // keeps structure valid

        culture.left = museums;
        culture.right = ruins;
        urban.right = culture;

        TreeNode ski = new TreeNode("Do you prefer Europe?");
        ski.left = new TreeNode("Colorado");
        ski.right = new TreeNode("Swiss Alps");

        cold.left = urban;
        cold.right = ski;

        // Warm destinations
        TreeNode warm = new TreeNode("Do you enjoy beaches?");
        TreeNode desert = new TreeNode("Do you enjoy desert landscapes?");
        desert.left = new TreeNode("Bangkok");
        desert.right = new TreeNode("Dubai");

        TreeNode nature = new TreeNode("Would you like coastal mountains?");
        nature.left = new TreeNode("Cape Town");
        nature.right = new TreeNode("Vancouver");

        TreeNode tropical = new TreeNode("Do you prefer the Pacific Ocean?");
        tropical.left = new TreeNode("Miami");
        tropical.right = new TreeNode("Hawaii");

        warm.left = desert;
        warm.right = tropical;

        root.left = cold;
        root.right = warm;
    }

    /**
     * Walks through the tree asking yes/no questions to find a destination.
     */
    public void startRecommendation() {
        if (root == null) {
            System.out.print("Enter file name to load decision tree: ");
            loadTreeFromText(scanner.nextLine());
        }

        TreeNode current = root;

        while (current != null && current.left != null && current.right != null) {
            System.out.println(current.data + " (y/n)");
            String response = scanner.nextLine().trim().toLowerCase();
            while (!response.equals("y") && !response.equals("n")) {
                System.out.print("Please enter 'y' or 'n': ");
                response = scanner.nextLine().trim().toLowerCase();
            }
            current = response.equals("y") ? current.right : current.left;
        }

        if (current == null) {
            System.out.println("Error: Tree is incomplete or malformed.");
            return;
        }

        System.out.println("I recommend the following Destination: " + current.data);
        System.out.println("Is this where you’d like to go? (y/n)");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("n")) {
            learnNewDestination(current);
        } else {
            System.out.println("Thank you for using the Travel Expert System.");
        }
    }

    /**
     * Learns a new destination and updates the tree interactively.
     */
    private void learnNewDestination(TreeNode node) {
        System.out.println("Please tell me where you are thinking of going:");
        String newPlace = scanner.nextLine();

        System.out.println("Please give me a yes/no question that distinguishes between \"" +
                node.data + "\" and \"" + newPlace + "\":");
        String newQuestion = scanner.nextLine();

        System.out.println("For \"" + newPlace + "\", what would the answer to that question be? (y/n)");
        String answer = scanner.nextLine().trim().toLowerCase();

        TreeNode oldDestination = new TreeNode(node.data);
        TreeNode newDestination = new TreeNode(newPlace);

        node.data = newQuestion;
        if (answer.equals("y")) {
            node.right = newDestination;
            node.left = oldDestination;
        } else {
            node.right = oldDestination;
            node.left = newDestination;
        }

        System.out.println("Destination added. Thank you!");
    }

    /**
     * Saves the tree in pre-order to a text file using A:/Q: format.
     */
    public void saveTreeAsText(String filename) {
        // Patch any missing children before saving to avoid malformed save
        patchMissingChildren(root);
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            savePreOrder(root, writer);
            System.out.println("Tree saved in pre-order format to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing tree to text file: " + e.getMessage());
        }
    }

    private void patchMissingChildren(TreeNode node) {
        if (node == null) return;
    
        if (node.left == null && node.right != null) {
            node.left = new TreeNode("UNKNOWN");
        } else if (node.right == null && node.left != null) {
            node.right = new TreeNode("UNKNOWN");
        }
    
        patchMissingChildren(node.left);
        patchMissingChildren(node.right);
    }
    


    /**
     * Recursively saves the tree using pre-order traversal.
     */
    private void savePreOrder(TreeNode node, PrintWriter writer) {
        if (node == null) return;

        if (node.left == null && node.right == null) {
            writer.println("A:" + node.data);
        } else {
            writer.println("Q:" + node.data);
            savePreOrder(node.left, writer);
            savePreOrder(node.right, writer);
        }
    }

    /**
     * Loads a tree from a pre-order formatted text file.
     */
    public void loadTreeFromText(String filename) {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            if (lines.isEmpty()) {
                System.out.println("Error: File is empty or malformed.");
                return;
            }

            int[] index = {0};
            root = buildTreeFromPreOrder(lines, index);
            System.out.println("Tree loaded from text file: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading tree from text file: " + e.getMessage());
        }
    }

    /**
     * Recursively rebuilds the tree from lines in pre-order format.
     */
    private TreeNode buildTreeFromPreOrder(List<String> lines, int[] index) {
        if (index[0] >= lines.size()) return null;

        String line = lines.get(index[0]++);
        if (line.startsWith("A:")) {
            return new TreeNode(line.substring(2).trim());
        } else if (line.startsWith("Q:")) {
            TreeNode node = new TreeNode(line.substring(2).trim());
            node.left = buildTreeFromPreOrder(lines, index);
            node.right = buildTreeFromPreOrder(lines, index);

            if (node.left == null || node.right == null) {
                System.out.println("Warning: Question node \"" + node.data + "\" is missing a child.");
            }
            return node;
        }
        return null;
    }
}
