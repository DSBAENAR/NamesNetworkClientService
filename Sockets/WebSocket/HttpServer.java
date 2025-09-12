package Sockets.WebSocket;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

/**
 * Simple HTTP server that responds to browser requests.
 */
public class HttpServer {

    /**
     * Starts the HTTP server on port 8080 and serves static files from the current directory.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		final int PORT = 8080;
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("HTTP Server listening on port " + PORT);
			while (true) {
				try (Socket clientSocket = serverSocket.accept();
					 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					 OutputStream out = clientSocket.getOutputStream()) {
					// Read the HTTP request (first line)
					String requestLine = in.readLine();
                    // Parse the request line
					if (requestLine == null || requestLine.isEmpty()) continue;
					System.out.println("Request: " + requestLine);
					String[] parts = requestLine.split(" ");

                    
					if (parts.length < 2) continue;
					String path = parts[1];

                    // Handle WebSocket upgrade request
					if (path.equals("/")) path = "/index.html";
					path = path.startsWith("/") ? path.substring(1) : path;

                    // Serve static files
					File file = new File("Sockets/WebSocket", path);
					if (file.exists() && file.isFile()) {
						String contentType = getContentType(path);
						out.write(("HTTP/1.1 200 OK\r\n").getBytes());
						out.write(("Content-Type: " + contentType + "\r\n").getBytes());
						out.write("\r\n".getBytes());
						try (FileInputStream fis = new FileInputStream(file)) {
							byte[] buffer = new byte[4096];
							int bytesRead;
							while ((bytesRead = fis.read(buffer)) != -1) {
								out.write(buffer, 0, bytesRead);
							}
						}
					} else {
						String notFound = "<html><body><h1>404 Not Found</h1></body></html>";
						out.write(("HTTP/1.1 404 Not Found\r\n").getBytes());
						out.write("Content-Type: text/html\r\n\r\n".getBytes());
						out.write(notFound.getBytes());
					}
					out.flush();
				}
			}
		} catch (IOException e) {
			System.out.println("Server error: " + e.getMessage());
		}
	}

	private static String getContentType(String path) {
		if (path.endsWith(".html") || path.endsWith(".htm")) return "text/html";
		if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
	if (path.endsWith(".png")) return "image/png";
		if (path.endsWith(".gif")) return "image/gif";
		if (path.endsWith(".css")) return "text/css";
		if (path.endsWith(".js")) return "application/javascript";
		return "application/octet-stream";
	}
}
