package currencyConverter;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;


public class MainWindow extends JFrame {
	private RateManager rateManager;
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("localization.translation"); //$NON-NLS-1$
	private JPanel contentPane;
	private JTextField fieldAmount;

	/**
	 * Create the mainWindow frame
	 */
	public MainWindow(RateManager rateManager) {
		this.rateManager = this.rateManager;
		setTitle(BUNDLE.getString("MainWindow.this.title")); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 300);
		setLocationRelativeTo(null);
		setResizable(false);

		// Create menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// "File" menu
		JMenu mnFile = new JMenu(BUNDLE.getString("MainWindow.mnFile.text")); //$NON-NLS-1$
		mnFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnFile);

		// "Quit" menu item
		JMenuItem mntmQuit = new JMenuItem(BUNDLE.getString("MainWindow.mntmQuit.text")); //$NON-NLS-1$
		mntmQuit.setMnemonic(KeyEvent.VK_Q);
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmQuit);

		// "Help" menu
		JMenu mnHelp = new JMenu(BUNDLE.getString("MainWindow.mnHelp.text")); //$NON-NLS-1$
		mnHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(mnHelp);

		// "About" menu item
		JMenuItem mntmAbout = new JMenuItem(BUNDLE.getString("MainWindow.mntmAbout.text"));		 //$NON-NLS-1$
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							AboutWindow.getInstance().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mntmAbout.setMnemonic(KeyEvent.VK_A);
		mnHelp.add(mntmAbout);

		// Window components
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Label "Convert"
		JLabel lblConvert = new JLabel(BUNDLE.getString("MainWindow.lblConvert.text")); //$NON-NLS-1$
		lblConvert.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConvert.setBounds(0, 14, 92, 15);
		contentPane.add(lblConvert);

		// ComboBox of the first currency
		final JComboBox<String> comboBoxCountry1 = new JComboBox<String>();
		comboBoxCountry1.setBounds(147, 7, 240, 28);
		populate(comboBoxCountry1);
		contentPane.add(comboBoxCountry1);

		// Label "To"
		JLabel lblTo = new JLabel(BUNDLE.getString("MainWindow.lblTo.text")); //$NON-NLS-1$
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setBounds(66, 54, 26, 15);
		contentPane.add(lblTo);

		// ComboBox of the second currency
		final JComboBox<String> comboBoxCountry2 = new JComboBox<String>();
		comboBoxCountry2.setBounds(147, 47, 240, 28);
		populate(comboBoxCountry2);
		contentPane.add(comboBoxCountry2);

		// Label "Amount"
		final JLabel lblAmount = new JLabel(BUNDLE.getString("MainWindow.lblAmount.text")); //$NON-NLS-1$
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setBounds(23, 108, 69, 15);
		contentPane.add(lblAmount);

		// Textfield where the user
		fieldAmount = new JTextField();
		fieldAmount.setText("0.00");
		fieldAmount.setBounds(147, 101, 103, 29);
		contentPane.add(fieldAmount);
		fieldAmount.setColumns(10);
		fieldAmount.setDocument(new JTextFieldLimit(8));

		// Label displaying result of conversion
		final JLabel lblResult = new JLabel("");
		lblResult.setHorizontalAlignment(SwingConstants.LEFT);
		lblResult.setBounds(147, 188, 428, 38);
		contentPane.add(lblResult);

		// Button "Convert"
		JButton btnConvert = new JButton(BUNDLE.getString("MainWindow.btnConvert.text")); //$NON-NLS-1$
		btnConvert.setBounds(147, 142, 129, 38);
		btnConvert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nameCurrency1 = comboBoxCountry1.getSelectedItem().toString();
				String nameCurrency2 = comboBoxCountry2.getSelectedItem().toString();
				String result;
				String formattedPrice;
				String formattedAmount;
				Double price;
				Double amount = 0.0;
				DecimalFormat format = new DecimalFormat("#0.00");

				try {
					amount = Double.parseDouble(fieldAmount.getText());
				} catch (NumberFormatException e) {
					// Shows the Error
					JOptionPane.showMessageDialog(MainWindow.this, "Error: You entered an incorrect amount.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				price = convert(nameCurrency1, nameCurrency2, amount);

				// Format numbers to avoid "E7" problem
				formattedPrice = format.format(price);
				formattedAmount = format.format(amount);

				result = formattedAmount + " " + nameCurrency1 + " = " + formattedPrice + " " + nameCurrency2;
				lblResult.setText(result);
			}
		});
		contentPane.add(btnConvert);
	}

	// Fill comboBox with currencies name
	public void populate(JComboBox<String> comboBox) {
		RateManager rateManager = RateManager.getInstance();
		for (String currency : rateManager.getCurrencyList()) {
			comboBox.addItem(currency);
		}
	}

	// Convert currency
	public Double convert(String currency1, String currency2, Double amount) {
		RateManager rateManager = RateManager.getInstance();
		Double exchangeValue = rateManager.getExchangeRate(currency1,currency2);
		Double price;
		price = amount * exchangeValue;
		price = Math.round(price * 100d) / 100d;

		return price;
	}
}
