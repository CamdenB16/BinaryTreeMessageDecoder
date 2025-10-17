package edu.iastate.cs2280.hw4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main 
{
    public static void main(String[] args) 
    {
        // Verify correct usage
        if (args.length != 1) 
        {
            System.err.println("Usage: java Main <filename>");
            return;
        }

        String filename = args[0];
        String encodingString = "";
        String compressedMessage = "";

        // Read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) 
        {
            StringBuilder encodingBuilder = new StringBuilder();

            // Read the encoding part (may span one or two lines)
            String line;
            while ((line = reader.readLine()) != null) 
            {
                if (line.matches("[01]+")) // Stop when we reach the binary string
                { 
                    compressedMessage = line;
                    break;
                }
                encodingBuilder.append(line);
            }

            encodingString = encodingBuilder.toString();
        } 
        catch (IOException e) 
        {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Construct the MsgTree
        MsgTree tree = new MsgTree(encodingString);

        // Print character codes
        System.out.println("character code");
        System.out.println("-------------------------");
        MsgTree.printCodes(tree, "");

        // Decode the message
        tree.decode(tree, compressedMessage);
    }
}

