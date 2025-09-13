package Datagram;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP Time Client: Requests the time from the server every 5 seconds and displays it.
 * Keeps last received time if no response is received.
 *
 * Usage:
 * 1. Run the server (TimeServer) first.
 * 2. Run this client: java Sockets.TimeClient
 * 3. The client will print the current time received from the server every 5 seconds.
 * 4. If the server is down, the client keeps showing the last received time and updates when the server is back.
 */
public class TimeClient {

    /**
     * @param args Command line arguments.
     * Main method to start the UDP Time Client.
     */
    public static void main(String[] args) throws Exception {
        final String SERVER = "localhost";
        final int PORT = 12347;
    DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[256];
        String lastTime = "No time received yet";
        try {
            while (true) {
                try {
                    // Send request
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
        } finally {
            socket.close();
        }
    }
}
