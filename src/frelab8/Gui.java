package frelab8;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder; 

public class Gui implements ActionListener {
	BankLogic bank = new BankLogic();
	MenuActionListener menuActionListener = new MenuActionListener();
	//Skapar ett nytt fönster "frame" med titeln "Banksystem".
	JFrame frame = new JFrame("Banksystem");
	//Alla knappar som finns på sidan.
	JButton handleCustomer = new JButton("Hantera kunder");
	JButton handleAccount = new JButton("Hantera konton");
	JButton searchCustomerButton = new JButton("Sök kund");
	JButton deleteCustomerButton = new JButton("Ta bort kund");
	JButton changeCustomerNameButton = new JButton("Ändra kundnamn");
	JButton searchAccountButton = new JButton("Sök konto");
	JButton deleteAccountButton = new JButton("Ta bort konto");
	JButton showTransactionButton = new JButton("Hämta transaktioner");
	JButton depositButton = new JButton("Sätt in pengar");
	JButton withdrawButton = new JButton("Ta ut pengar");
	JButton createCustomerButton = new JButton("Skapa kund");
	JButton submitButton = new JButton("Ok");
	//Skapar en panel "handlePanel" som kommer innehålla två knappar i "handleCustomer" och "handleAccount".
	JPanel handlePanel = new JPanel();
	//Skapar de paneler som ska innehålla den typ av interaktion textinput och submit knappar som ska finnas på sidan.
	JPanel searchPanel = new JPanel();
	JPanel createCustomerPanel = new JPanel();
	JPanel accountPanel = new JPanel();
	JPanel accountButtonPanel = new JPanel();
	JPanel customerButtonPanel = new JPanel();
	JPanel accountResultPanel = new JPanel();
	JPanel accountLabelPanel = new JPanel();
	JPanel customerPanel = new JPanel();
	JPanel customerInfoPanel = new JPanel();
	JPanel customerResultPanel = new JPanel();
	JPanel customerTextFieldPanel = new JPanel();
	//Skapar en wrapper panel som använder layouten "gridbaglayout" för placeringen av olika objekt på sidan.
	JPanel wrapperOuterPanel = new JPanel();
	//Skapar den label som innehåller kontoinformation på det konto man sökt på.
	JLabel accountLabel = new JLabel();
	//En label som presenterar den information som användaren får vid avslutande av ett konto.
	JLabel closingAccountInfo = new JLabel();
	//En lista med information om transaktioner på den nuvarande kontot man sökt på.
	JList<String> transactionInfo;
	//En lista med information om kunden och kundens olika konton i systemet.
	JList<String> customerInfo;
	//En lista med information om kunden vid borttagning av kunden från systemet.
	JList<String> customerClosingInfo = new JList<String>();
	//En panel "welcomePanel" som innehåller labeln "welcomeLabel" som är rubriken på sidan.
	JPanel welcomePanel = new JPanel();
	JLabel welcomeLabel = new JLabel("Vad vill du göra?");
	//Lagrade kontonummer och personnummer för att veta det konto eller den kund som användaren sökt på.
	String currentAccountNumber;
	String currentIdNumber;
	String currentSearchType = "";
	//De textfält som används för input till funktionaliteten på sidan.
	JTextField accountNumberInput = new JTextField("Fyll i kontonummer", 15);
	JTextField customerIdNumberInput = new JTextField("Fyll i personnummer", 15);
	JTextField createCustomerIdNumber = new JTextField("Fyll i personnummer", 15);
	JTextField customerFirstname = new JTextField("Förnamn", 15);
	JTextField customerLastname = new JTextField("Efternamn", 15);
	TitledBorder border = new TitledBorder("Sök kund");
	
	//Temp text
	String[] s = {"hej", "hejdå"};
	
	public Gui() {
		//Skapar ett menyfält som ska ligga högst upp i fönstret.
		JMenuBar menuBar = new JMenuBar();
		//Skapar två menyer där ena är huvudmenyn "menu" och den andra är en submeny "openAccount" som innehåller egna menyobjekt.
		JMenu menu = new JMenu("Meny");
		JMenu openAccount = new JMenu("Öppna nytt konto");
		//Skapar fyra olika menyobjekt som ska placeras inuti de två skapade menyerna.
		JMenuItem getAllCustomer = new JMenuItem("Hämta alla kunder");
		JMenuItem createCustomer = new JMenuItem("Skapa ny kund");
		createCustomer.addActionListener(menuActionListener);
		createCustomer.setActionCommand("createCustomer");
		JMenuItem savingsAccount = new JMenuItem("Sparkonto");
		JMenuItem creditAccount = new JMenuItem("Kreditkonto");
		GridBagConstraints c = new GridBagConstraints();
		
		//Lägger till rubriken(welcomeLabel) och de två knapparna (handleCustomer och handleAccount) i deras respektive skapade panel.
		welcomePanel.add(welcomeLabel);
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		handlePanel.add(handleCustomer);
		handleCustomer.setActionCommand("handleCustomer");
		handleCustomer.addActionListener(this);
		handlePanel.add(handleAccount);
		handleAccount.setActionCommand("handleAccount");
		handleAccount.addActionListener(this);
		
		//Skapar den sökruta man ska komma till efter man tryckt på "handleAccountButton" eller "handleCustomerButton".
		searchPanel.add(customerIdNumberInput);
		searchPanel.add(accountNumberInput);
		searchPanel.add(Box.createVerticalStrut(10));
		searchPanel.add(searchAccountButton);
		searchPanel.add(searchCustomerButton);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
		searchPanel.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(50, 50, 50, 50)));
		searchAccountButton.addActionListener(this);
		searchAccountButton.setActionCommand("searchAccountButton");
		searchCustomerButton.addActionListener(this);
		searchCustomerButton.setActionCommand("searchCustomerButton");
		
		createCustomerPanel.add(createCustomerIdNumber);
		createCustomerPanel.add(customerTextFieldPanel);
		createCustomerPanel.add(Box.createVerticalStrut(10));
		createCustomerPanel.add(createCustomerButton);
		createCustomerPanel.setLayout(new BoxLayout(createCustomerPanel, BoxLayout.PAGE_AXIS));
		createCustomerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Skapa ny kund"), new EmptyBorder(50, 50, 50, 50)));
		createCustomerButton.addActionListener(this);
		createCustomerButton.setActionCommand("createCustomerButton");
		
		accountLabelPanel.add(accountLabel);
		accountPanel.add(accountLabelPanel);
		accountButtonPanel.add(deleteAccountButton);
		accountButtonPanel.add(showTransactionButton);
		deleteAccountButton.setActionCommand("deleteAccountButton");
		deleteAccountButton.addActionListener(this);
		showTransactionButton.setActionCommand("showTransactionButton");
		showTransactionButton.addActionListener(this);
		accountResultPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		accountResultPanel.add(closingAccountInfo);
		accountPanel.add(accountButtonPanel);
		accountPanel.add(accountResultPanel);
		accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.PAGE_AXIS));
		
		customerPanel.setBorder(BorderFactory.createTitledBorder("Konton"));
		customerTextFieldPanel.add(customerFirstname);
		customerTextFieldPanel.add(customerLastname);
		customerTextFieldPanel.add(submitButton);
		submitButton.setActionCommand("submitButton");
		submitButton.addActionListener(this);
		customerTextFieldPanel.setLayout(new BoxLayout(customerTextFieldPanel, BoxLayout.PAGE_AXIS));
		customerButtonPanel.add(deleteCustomerButton);
		customerButtonPanel.add(changeCustomerNameButton);
		customerButtonPanel.add(customerTextFieldPanel);
		deleteCustomerButton.addActionListener(this);
		deleteCustomerButton.setActionCommand("deleteCustomerButton");
		changeCustomerNameButton.addActionListener(this);
		changeCustomerNameButton.setActionCommand("changeCustomerNameButton");
		customerPanel.add(customerInfoPanel);
		customerResultPanel.add(customerClosingInfo);
		
		//Sätter layouten på den yttersta panelen "wrapperOuterPanel till en "GridBagLayout".
		wrapperOuterPanel.setLayout(new GridBagLayout());
		
		//Här ändras "GridBagConstraints" innan varje enskild panel adderas till den yttre wrapperOuterPanel.
		//welcomePanel och handlePanel ska befinna sig på samma sida.
		c.gridx = 0;
		c.gridy = 0;
		wrapperOuterPanel.add(welcomePanel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperOuterPanel.add(handlePanel, c);
		//searchCustomerPanel ska befinna sig ensam på en egen sida.
		c.gridx = 0;
		c.gridy = 0;
		//searchAccountPanel ska befinna sig ensam på en egen sida.
		wrapperOuterPanel.add(searchPanel, c);
		//accountPanel ska befinna sig ensam på en egen sida.
		wrapperOuterPanel.add(accountPanel, c);
		wrapperOuterPanel.add(customerPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperOuterPanel.add(customerButtonPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		wrapperOuterPanel.add(customerResultPanel, c);
		
		//Panelerna här ska inte vara synliga på startsidan i systemet.
		searchPanel.setVisible(false);
		accountPanel.setVisible(false);
		customerPanel.setVisible(false);
		customerButtonPanel.setVisible(false);
		customerResultPanel.setVisible(false);
		customerTextFieldPanel.setVisible(false);
		
		//Adderar den menu jag ska ha i mitt menyfält.
		menuBar.add(menu);
		//Adderar menyobjekten createCustomer, getAllCustomer och openAccount till menyn som olika alternativ.
		menu.add(createCustomer);
		menu.add(getAllCustomer);
		menu.add(openAccount);
		//De olika alternativen sparkonto och kreditkonto som ska finnas under submenyn "skapa nytt konto"
		openAccount.add(savingsAccount);
		openAccount.add(creditAccount);
		//Fönstret "frame" ska ha storleken 800*600.
		frame.setSize(800, 600);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		//Fönstret ska ha menufältet "menuBar".
		frame.setJMenuBar(menuBar);
		//Fönstret ska ha en panel som allt innehåll ska ligga inom, panelen ska ha layouten GridBagLayout.
		frame.add(wrapperOuterPanel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Gui();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Tar reda på vilken av knapparna användaren har tryckt in.
		String command = ((JButton) e.getSource()).getActionCommand();
		
		switch(command) {
		case "handleCustomer":
			border.setTitle("Sök kund");
			searchPanel.setVisible(true);
			searchCustomerButton.setVisible(true);
			searchAccountButton.setVisible(false);
			accountNumberInput.setVisible(false);
			handlePanel.setVisible(false);
			welcomePanel.setVisible(false);
			break;
		case "handleAccount":
			border.setTitle("Sök konto");
			searchPanel.setVisible(true);
			searchAccountButton.setVisible(true);
			searchCustomerButton.setVisible(false);
			accountNumberInput.setVisible(true);
			handlePanel.setVisible(false);
			welcomePanel.setVisible(false);
			break;
		case "searchCustomerButton":
			//Ändra det nuvarande personnumret till den text i textfältet "customerIdNumberInput".
			currentIdNumber = customerIdNumberInput.getText();
			//Temporär påhittad data, kommentaren under är till för den verkliga implementationen senare.
			customerInfo = new JList<String>(s);
			//customerInfo = new JList<Object>(bank.getCustomer(currentIdNumber).toArray());
			customerInfoPanel.add(customerInfo);
			customerResultPanel.setVisible(false);
			searchPanel.setVisible(false);
			customerPanel.setVisible(true);
			customerButtonPanel.setVisible(true);
			break;
		case "deleteCustomerButton":
			customerResultPanel.remove(customerClosingInfo);
			customerClosingInfo = new JList<String>(s);
			customerResultPanel.add(customerClosingInfo);
			wrapperOuterPanel.revalidate();
			wrapperOuterPanel.repaint();
			customerClosingInfo.setVisible(true);
			customerResultPanel.setVisible(true);
			break;
		case "changeCustomerNameButton":
			customerTextFieldPanel.setVisible(true);
			break;
		case "submitButton":
			//String firstName_ = customerFirstname.getText();
			//String lastName_ = customerLastname.getText();
			//String idNumber_ = currentIdNumber;
			Boolean checkIfChanged = true;
			//Boolean checkIfChanged = bank.changeCustomerName(firstName_, lastName_, idNumber_);
			if(checkIfChanged) {
				JOptionPane.showMessageDialog(frame, "Namnet på kunden har ändrats!");
			} else {
				JOptionPane.showMessageDialog(frame, "Något gick fel, namnet kunde inte ändras!");
			}
			customerTextFieldPanel.setVisible(false);
			break;
		case "searchAccountButton":
			//Ändra det nuvarande kontonumret och personnumret till den text i textfälten "accountNumberInput" och "customerIdNumberInput".
			currentAccountNumber = accountNumberInput.getText();
			currentIdNumber = customerIdNumberInput.getText();
			try {
				//Temporär påhittad data, kommentaren under är till för den verkliga implementationen senare.
				accountLabel.setText("1004 1500kr sparkonto 1,0%");
				//accountLabel.setText(bank.getAccount(currentIdNumber, Integer.parseInt(currentAccountNumber)));
			} catch (NumberFormatException e1) {
				System.out.println("Kontonumrets format ogiltigt");
			}
			accountResultPanel.setVisible(false);
			searchPanel.setVisible(false);
			accountPanel.setVisible(true);
			break;
		case "deleteAccountButton":
			//Temporär påhittad data, kommentaren under är till för den verkliga implementationen senare.
			closingAccountInfo.setText("1004 1500kr sparkonto 70kr");
			//closingAccountInfo.setText(bank.closeAccount(currentIdNumber, Integer.parseInt(currentAccountNumber)));
			accountResultPanel.setVisible(true);
			closingAccountInfo.setVisible(true);
			transactionInfo.setVisible(false);
			break;
		case "showTransactionButton":
			//Temporär påhittad data "transactionInfo", kommentaren under är till för den verkliga implementationen senare.
			transactionInfo = new JList<String>(s);
			//transactionInfo = new JList<Object>(bank.getTransactions(currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray);
			accountResultPanel.add(transactionInfo);
			accountResultPanel.setVisible(true);
			transactionInfo.setVisible(true);
			closingAccountInfo.setVisible(false);
			break;
		}
		
	}

}
