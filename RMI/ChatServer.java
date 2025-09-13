package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * RMI chat server that publishes itself on a user-specified port.
 */
public class ChatServer implements ChatInterface {
    /**
     * Handles incoming chat messages by printing them to the console.
     * @param message The message received from a client.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void sendMessage(String message) throws RemoteException {
        System.out.println("Remote: " + message);
    }

    /**
     * Main method to start the RMI Chat Server.
     * Prompts the user for a port number, publishes the chat service, and waits for messages.
     * @param args Command line arguments (not used).
     * @throws Exception If an error occurs during server setup or operation.
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter port to publish chat object: ");
        int port = Integer.parseInt(scanner.nextLine());
        ChatServer obj = new ChatServer();
        ChatInterface stub = (ChatInterface) UnicastRemoteObject.exportObject(obj, 0);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("ChatService", stub);
        System.out.println("Chat server published on port " + port);
        System.out.println("Waiting for messages...");
        // Keep running
        while (true) Thread.sleep(1000);
    }
}
