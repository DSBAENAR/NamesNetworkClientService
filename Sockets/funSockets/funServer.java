package Sockets.funSockets;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


public class funServer {
    /**
     * A simple TCP server that listens for numbers and function change commands from clients,
     * computes the result based on the current function (sin, cos, tan), and sends the results back to the clients.
     * Clients can change the function by sending commands like "fun:sin", "fun:cos", or "fun:tan".
     * @param args the command line arguments
     */
	public static void main(String[] args) {
		final int PORT = 12346;
		String currentFun = "cos";
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Servidor fun escuchando en el puerto " + PORT);
			while (true) {
				try (Socket clientSocket = serverSocket.accept();
					 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
					String line;
					while ((line = in.readLine()) != null) {
						if (line.startsWith("fun:")) {
							String fun = line.substring(4).trim();
							if (fun.equals("sin") || fun.equals("cos") || fun.equals("tan")) {
								currentFun = fun;
								out.println("Function changed to " + currentFun);
							} else {
								out.println("Unknown function: " + fun);
							}
						} else {
							try {
								double num = Double.parseDouble(line.trim());
								double result;
								switch (currentFun) {
									case "sin":
										result = Math.sin(num);
										break;
									case "tan":
										result = Math.tan(num);
										break;
									case "cos":
									default:
										result = Math.cos(num);
										break;
								}
								out.println(result);
								System.out.println("Recibido: " + num + " | Funcion: " + currentFun + " | Respuesta: " + result);
							} catch (NumberFormatException e) {
								out.println("Error: entrada no es un número válido");
							}
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error en el servidor: " + e.getMessage());
		}
	}
}
