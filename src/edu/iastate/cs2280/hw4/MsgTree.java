package edu.iastate.cs2280.hw4;

/**
 * @author Camden Beightler
 */
public class MsgTree 
{
    public char payloadChar; // Character stored in this node
    public MsgTree left;     // Left child node
    public MsgTree right;    // Right child node

    // Static variable for recursive tree-building index tracking
    private static int staticCharIdx = 0;

    /**
     * Constructor that builds the tree from an encoding string using preorder traversal.
     * @param encodingString the encoding string representing the tree
     */
    public MsgTree(String encodingString) 
    {
        // Base case: if the current character is not an internal node (^), create a leaf node
        if (staticCharIdx >= encodingString.length()) 
        {
            return; // check for bounds
        }

        char currentChar = encodingString.charAt(staticCharIdx++); // Read and increment the index

        // Assign the current character to this node
        payloadChar = currentChar;

        if (currentChar == '^') 
        { // Internal node, recursively create children
            left = new MsgTree(encodingString); // Build left subtree
            right = new MsgTree(encodingString); // Build right subtree
        } 
        else 
        {
            left = null; // Leaf nodes have no children
            right = null;
        }
    }

    /**
     * Constructor for a single node with null children.
     * @param payloadChar the character stored in this node
     */
    public MsgTree(char payloadChar) 
    {
        this.payloadChar = payloadChar;
        left = null;
        right = null;
    }

    /**
     * Prints characters and their binary codes using preorder traversal.
     * @param root the root of the tree
     * @param code the current binary code (accumulated as the tree is traversed)
     */
    public static void printCodes(MsgTree root, String code) 
    {
        if (root == null) {
            return; // Base case: stop recursion for null nodes
        }

        // If the current node is a leaf (non-internal), print its payload and code
        if (root.payloadChar != '^') {
            System.out.println(root.payloadChar + " " + code);
        }

        // Recurse for the left and right children with updated codes
        printCodes(root.left, code + "0");
        printCodes(root.right, code + "1");
    }

    /**
     * Decodes the given message using the tree and prints the result to the console.
     * @param codes the root of the MsgTree
     * @param msg the encoded message string
     */
    public void decode(MsgTree codes, String msg) 
    {
        StringBuilder decodedMessage = new StringBuilder(); // To build the decoded message
        MsgTree current = codes; // Start at the root of the tree

        // Loop through the bits in the encoded message
        for (char bit : msg.toCharArray()) 
        {
            if (bit == '0') 
            {
                current = current.left; // Move left on a '0'
            } 
            else if (bit == '1') 
            {
                current = current.right; // Move right on a '1'
            }

            // If we reach a leaf node, append the payloadChar to the message
            if (current.left == null && current.right == null) 
            {
                decodedMessage.append(current.payloadChar); // Add character to the message
                current = codes; // Return to the root for the next character
            }
        }

        // Print the reconstructed message
        System.out.println("MESSAGE:");
        System.out.println(decodedMessage.toString());
    }
}
