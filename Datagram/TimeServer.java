package Datagram;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UDP Time Server: Responds with the current server time to any request.
 *
 * Usage:
 * 1. Run this server: java Sockets.TimeServer
 * 2. The server will listen on port 12347 for UDP requests.
 * 3. Any UDP packet received will be answered with the current time (HH:mm:ss).
 * 4. You can stop and restart the server; clients will keep last received time and update when server is back.
 */
public class TimeServer {
    /**
     * @param args Command line arguments.
     * Main method to start the UDP Time Server.
     */
    public static void main(String[] args) throws Exception {
        final int PORT = 12347;
    DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("UDP TimeServer listening on port " + PORT);
        byte[] buf = new byte[256];
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                byte[] timeBytes = time.getBytes();
                DatagramPacket response = new DatagramPacket(
                    timeBytes, timeBytes.length, packet.getAddress(), packet.getPort());
                socket.send(response);
                System.out.println("Responded time: " + time + " to " + packet.getAddress() + ":" + packet.getPort());
            }
        } finally {
            socket.close();
        }
    }
}
