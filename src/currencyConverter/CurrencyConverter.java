package currencyConverter;

import java.awt.EventQueue;
import javax.swing.UIManager;
public class CurrencyConverter {
	public static void main(String[] args) {
		// Set the look and feel of the UI to match the system's native look
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// Create an instance of the RateManager class
		RateManager rateManager = RateManager.getInstance();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Create the main window for the currency converter application
					// and pass the RateManager instance to it
					MainWindow mainWindow = new MainWindow(rateManager);
					mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

