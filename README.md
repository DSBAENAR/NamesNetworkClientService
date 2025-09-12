# 3. Working with URLs

## Exercise 1

The following code block shows how to create a URL object and print the data returned by its 8 methods: \
getProtocol, \
getAuthority, \
getHost, \
getPort, \
getPath, \
getQuery, \
getFile, \
getRef.

```java
try {
 URI uri = new URI("https://doodles.google/doodle/nba-playoffs-2025-am");
 URL url = uri.toURL();
 System.out.println("getProtocol: " + url.getProtocol());
 System.out.println("getAuthority: " + url.getAuthority());
 System.out.println("getHost: " + url.getHost());
 System.out.println("getPort: " + url.getPort());
 System.out.println("getPath: " + url.getPath());
 System.out.println("getQuery: " + url.getQuery());
 System.out.println("getFile: " + url.getFile());
 System.out.println("getRef: " + url.getRef());
} catch (Exception e) {
 System.out.println("URL mal formada: " + e.getMessage());
}
```

SALIDA

```sh
getProtocol: https
getAuthority: doodles.google
getHost: doodles.google
getPort: -1
getPath: /doodle/nba-playoffs-2025-am
getQuery: null
getFile: /doodle/nba-playoffs-2025-am
getRef: null
```

## Exercise 2

This code creates a simple application that allows users to input a URL,download the content, and save it to a local file named "resultado.html".

```java

try {
 URL url = new URL(urlText);

 try (InputStream in = url.openStream();
  FileOutputStream out = new FileOutputStream("resultado.html")) 
    {

  byte[] buffer = new byte[4096];
  int bytesRead;
  while ((bytesRead = in.read(buffer)) != -1) {
    out.write(buffer, 0, bytesRead);

      }
      statusLabel.setText("Descarga exitosa. Guardado en resultado.html");
     }
    } catch (MalformedURLException ex) {
     statusLabel.setText("URL inválida: " + ex.getMessage());
    } catch (IOException ex) {
     statusLabel.setText("Error al descargar: " + ex.getMessage());
    };
```

------


# 4. Sockets

In this section, a simple client-server application was implemented using Java sockets. The server listens for incoming connections on a specific port, receives a number from the client, calculates its square, and sends the result back to the client.

**Server:**

The server uses a `ServerSocket` to listen for connections. When a client connects, it reads a number sent by the client, calculates the square, and responds with the result:

```java
try (ServerSocket serverSocket = new ServerSocket(12345)) {
  while (true) {
    try (Socket clientSocket = serverSocket.accept();
       BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
      String line = in.readLine();
      if (line != null) {
        int num = Integer.parseInt(line.trim());
        int square = num * num;
        out.println(square);
      }
    }
  }
}
```

**Client:**

The client connects to the server, sends a number, and prints the response (the square):

```java
try (Socket socket = new Socket("localhost", 12345);
   BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
  out.println("7"); // Send a number
  String response = in.readLine();
  System.out.println("Server response: " + response);
}
```

This demonstrates basic socket communication in Java, where the server performs a calculation based on client input and returns the result.
