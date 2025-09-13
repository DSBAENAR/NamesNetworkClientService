package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * RMI chat client that connects to a remote chat server and sends messages interactively.
 */
public class ChatClient {
    /**
     * Main method to start the RMI Chat Client.
     * Prompts the user for server IP and port, connects to the chat service, and allows sending messages.
     * @param args Command line arguments (not used).
     * @throws Exception If an error occurs during client setup or operation.
     */
        public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server IP: ");
        String ip = scanner.nextLine();
        System.out.print("Enter server port: ");
        int port = Integer.parseInt(scanner.nextLine());
        Registry registry = LocateRegistry.getRegistry(ip, port);
        ChatInterface chat = (ChatInterface) registry.lookup("ChatService");
        System.out.println("Connected to chat server. Type messages to send. Type 'exit' to quit.");
        while (true) {
            System.out.print("You: ");
            String msg = scanner.nextLine();
            if (msg.equalsIgnoreCase("exit")) break;
            chat.sendMessage(msg);
        }
        scanner.close();
    }
}
