import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;

public class UrlApp {

    /**
     * This method creates a simple GUI application that allows users to input a URL,
     * download the content, and save it to a local file named "resultado.html".
     * @param args the command line arguments
     */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Mini Browser");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 150);

			JPanel panel = new JPanel(new BorderLayout());
			JTextField urlField = new JTextField();
			JButton downloadButton = new JButton("Descargar");
			JLabel statusLabel = new JLabel("Ingrese una URL y presione Descargar");

			panel.add(urlField, BorderLayout.CENTER);
			panel.add(downloadButton, BorderLayout.EAST);
			frame.add(panel, BorderLayout.NORTH);
			frame.add(statusLabel, BorderLayout.SOUTH);

			downloadButton.addActionListener((ActionEvent _) -> {
				String urlText = urlField.getText().trim();
				if (urlText.isEmpty()) {
					statusLabel.setText("Por favor ingrese una URL.");
					return;
				}
				try {
					URL url = new URL(urlText);
					try (InputStream in = url.openStream();
						 FileOutputStream out = new FileOutputStream("resultado.html")) {
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
				}
			});

			frame.setVisible(true);
		});
	}
}
