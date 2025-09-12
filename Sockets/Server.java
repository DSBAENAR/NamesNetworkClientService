package Sockets;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


public class Server {
    /**
     * A simple TCP server that listens for integer inputs from clients,
     * computes their squares, and sends the results back to the clients.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int PORT = 12345;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Servidor escuchando en el puerto " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    String line = in.readLine();
                    if (line != null) {
                        try {
                            int num = Integer.parseInt(line.trim());
                            int cuadrado = num * num;
                            out.println(cuadrado);
                            System.out.println("Recibido: " + num + ", respondido: " + cuadrado);
                        } catch (NumberFormatException e) {
                            out.println("Error: entrada no es un número entero");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}