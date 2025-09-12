package Sockets;

import java.net.Socket;
import java.io.*;

public class Client {
    /**
     * A simple TCP client that connects to the server, sends an integer,
     * and prints the squared result received from the server.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("7");
            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
