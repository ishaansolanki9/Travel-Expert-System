import java.io.Serializable;

/**
 * TreeNode represents a node in the binary decision tree.
 * Each node stores either a question or a destination name.
 * It also stores references to the left and right child nodes,
 * which represent "no" and "yes" answers, respectively.
 *
 * Implements Serializable so it can be saved/loaded to/from a file.
 */
public class TreeNode implements Serializable {
    String data;       // The question or destination
    TreeNode left;     // Left child = "no" answer
    TreeNode right;    // Right child = "yes" answer

    // Constructor to initialize the node with its data
    public TreeNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    // Returns true if this node is a leaf (i.e., a final destination)
    public boolean isLeaf() {
        return left == null && right == null;
    }
}
