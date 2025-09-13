package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface for chat messages.
 */
public interface ChatInterface extends Remote {
    /**
     * Sends a chat message to the server.
     * @param message The message to send.
     * @throws RemoteException If a remote communication error occurs.
     */
    void sendMessage(String message) throws RemoteException;
}
