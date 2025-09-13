## Exercise 5

This exercise implements a simple chat application using Java RMI. Each instance can act as both server and client. The user specifies a port to publish their chat object, and can connect to another instance by providing its IP and port.

**Interface:**

```java
public interface ChatInterface extends Remote {
  void sendMessage(String message) throws RemoteException;
}
```

**Server:**

```java
Scanner scanner = new Scanner(System.in);
System.out.print("Enter port to publish chat object: ");
int port = Integer.parseInt(scanner.nextLine());
ChatServer obj = new ChatServer();
ChatInterface stub = (ChatInterface) UnicastRemoteObject.exportObject(obj, 0);
Registry registry = LocateRegistry.createRegistry(port);
registry.rebind("ChatService", stub);
System.out.println("Chat server published on port " + port);
```

**Client:**

```java
Scanner scanner = new Scanner(System.in);
System.out.print("Enter server IP: ");
String ip = scanner.nextLine();
System.out.print("Enter server port: ");
int port = Integer.parseInt(scanner.nextLine());
Registry registry = LocateRegistry.getRegistry(ip, port);
ChatInterface chat = (ChatInterface) registry.lookup("ChatService");
while (true) {
  System.out.print("You: ");
  String msg = scanner.nextLine();
  if (msg.equalsIgnoreCase("exit")) break;
  chat.sendMessage(msg);
}
```

To test:
- Run the server (`ChatServer.java`) and specify a port.
- Run the client (`ChatClient.java`), enter the server's IP and port, and start chatting interactively.
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

## Exercise 1
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
  out.println("7"); 
  String response = in.readLine();
  System.out.println("Server response: " + response);
}
```

This demonstrates basic socket communication in Java, where the server performs a calculation based on client input and returns the result.

## Exercise 2

This exercise extends the socket server to support trigonometric operations. The server starts by calculating the cosine of any number it receives. The client can send special messages (e.g., `fun:sin`, `fun:cos`, `fun:tan`) to change the operation to sine, cosine, or tangent, respectively. After changing the operation, any number sent by the client will be processed using the selected function.

**Server:**

The server maintains the current function and updates it when receiving a message starting with `fun:`. Otherwise, it parses the input as a number and applies the current function:

```java
String currentFun = "cos";
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
    double num = Double.parseDouble(line.trim());
    double result;
    switch (currentFun) {
      case "sin": result = Math.sin(num); break;
      case "tan": result = Math.tan(num); break;
      case "cos":
      default: result = Math.cos(num); break;
    }
    out.println(result);
  }
}
```

**Client:**

The client allows the user to enter either a number or a function change command. It sends the input to the server and prints the response:

```java
while (true) {
  System.out.print("> ");
  String input = scanner.nextLine();
  out.println(input);
  String response = in.readLine();
  System.out.println("Respuesta: " + response);
}
```

For example, if the client sends `0`, the server responds with `1` (cos(0)). If the client sends `fun:sin` and then `0`, the server responds with `0` (sin(0)).


## Exercise 3

This exercise implements a simple HTTP server in Java that serves static files (HTML, images, etc.) from the `Sockets/WebSocket` directory. The server listens on port 8080 and responds to browser requests for any file in that directory. If the requested file exists, it is returned with the correct MIME type; otherwise, a 404 error page is shown.

**Key code fragment:**

```java
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
```


To test, place your files (e.g., `index.html`, `Horario.png`) in the `Sockets/WebSocket` directory and access them from your browser at `http://localhost:8080/filename`.

## Exercise 4

This exercise implements a UDP-based time server and client. The server responds with the current time to any UDP request. The client requests the time every 5 seconds and displays the last received value. If the server is down, the client keeps showing the last received time and updates when the server is back.

**Server:**

```java
DatagramSocket socket = new DatagramSocket(PORT);
while (true) {
  DatagramPacket packet = new DatagramPacket(buf, buf.length);
  socket.receive(packet);
  String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
  byte[] timeBytes = time.getBytes();
  DatagramPacket response = new DatagramPacket(
    timeBytes, timeBytes.length, packet.getAddress(), packet.getPort());
  socket.send(response);
}
```

**Client:**

```java
DatagramSocket socket = new DatagramSocket();
String lastTime = "No time received yet";
while (true) {
  try {
    DatagramPacket request = new DatagramPacket(new byte[1], 1, InetAddress.getByName(SERVER), PORT);
    socket.send(request);
    socket.setSoTimeout(2000);
    DatagramPacket response = new DatagramPacket(buf, buf.length);
    socket.receive(response);
    lastTime = new String(response.getData(), 0, response.getLength());
  } catch (Exception e) {
    // Timeout or server down: keep lastTime
  }
  System.out.println("Current time: " + lastTime);
  Thread.sleep(5000);
}
```

To test:
- Run the server (`TimeServer.java`).
- Run the client (`TimeClient.java`).
- The client will print the time every 5 seconds. If the server is stopped, the client keeps showing the last time and updates when the server is restarted.
