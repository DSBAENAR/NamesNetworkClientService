package Sockets.funSockets;

import java.net.Socket;
import java.io.*;
import java.util.Scanner;


public class funClient {

    /**
     * A simple TCP client that connects to the funServer, allows the user to send numbers
     * to compute the current trigonometric function (sin, cos, tan), and change the function
     * by sending commands like "fun:sin", "fun:cos", or "fun:tan".
     * @param args the command line arguments
     */
	public static void main(String[] args) {
		final String HOST = "localhost";
		final int PORT = 12346;
		try (Socket socket = new Socket(HOST, PORT);
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 Scanner scanner = new Scanner(System.in)) {
			System.out.println("Conectado a funServer en " + HOST + ":" + PORT);
			System.out.println("Ingrese un número para calcular la función actual, o 'fun:sin', 'fun:cos', 'fun:tan' para cambiar la función.");
			while (true) {
				System.out.print("> ");
				String input = scanner.nextLine();
				out.println(input);
				String response = in.readLine();
				System.out.println("Respuesta: " + response);
			}
		} catch (IOException e) {
			System.out.println("Error en el cliente: " + e.getMessage());
		}
	}
}
