package frelab8;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel; 

public class Gui implements ActionListener {
	BankLogic bank = new BankLogic();
	JFrame frame = new JFrame("Banksystem");//Skapar ett nytt fönster "frame" med titeln "Banksystem".
	JButton searchCustomerButton = new JButton("Sök kund");//Knapp för att söka specifik kund.
	JButton deleteCustomerButton = new JButton("Ta bort kund");
	JButton changeCustomerNameButton = new JButton("Ändra kundnamn");
	JButton deleteAccountButton = new JButton("Ta bort konto");
	JButton depositButton = new JButton("Sätt in pengar");
	JButton withdrawButton = new JButton("Ta ut pengar");
	JButton createCustomerButton = new JButton("Skapa kund");
	JButton chooseAccountButton = new JButton("Välj konto");
	JButton chooseCustomerButton = new JButton("Välj kund");
	JButton createCreditAccountButton = new JButton("Skapa kreditkonto");
	JButton createSavingsAccountButton = new JButton("Skapa sparkonto");
	JPanel searchPanel = new JPanel(); //Panel som innehåller interaktionskomponenter för att söka kund.
	JPanel accountsPanel = new JPanel(); //Panel som innehåller en kunds alla konton.
	JPanel customersPanel = new JPanel(); //Panel som innehåller systemets alla kunder.
	JPanel createCustomerPanel = new JPanel();
	JPanel createAccountPanel = new JPanel();
	JPanel accountPanel = new JPanel();
	JPanel accountButtonPanel = new JPanel();
	JPanel customerButtonPanel = new JPanel();
	JPanel accountLabelPanel = new JPanel();
	JPanel changeCustomerNamePanel = new JPanel();
	JPanel wrapperOuterPanel = new JPanel();
	JPanel wrapperPanelMenu = new JPanel();
	JLabel accountLabel = new JLabel();
	JList<Object> transactionInfo = new JList<Object>(); //En lista med information om transaktioner på den nuvarande kontot man sökt på.
	JList<Object> customerClosingInfo = new JList<Object>(); //En lista med information om kunden vid borttagning av kunden från systemet.
	JList<Object> accountClosingInfo = new JList<Object>(); //En lista med information om kontot vid borttagning av kontot från systemet.
	ArrayList<String[]> li = new ArrayList<String[]>();
	JTable tableAccounts = new JTable(); //En lista med alla konton som en specifik kund har i systemet.
	JTable tableCustomers = new JTable(); //En lista med alla kunder som systemet har förtillfället.
	//En panel "welcomePanel" som innehåller labeln "welcomeLabel" som är rubriken på sidan.
	JPanel welcomePanel = new JPanel();
	JLabel welcomeLabel = new JLabel("Vad vill du göra?");
	//Lagrade kontonummer och personnummer för att veta det konto eller den kund som användaren sökt på.
	String currentAccountNumber;
	String currentIdNumber = "";
	//De textfält som används för input till funktionaliteten på sidan.
	//JTextField accountNumberInput = new JTextField("Fyll i kontonummer", 15);
	JTextField customerIdNumberInput = new JTextField("Fyll i personnummer", 15);
	//JTextField idNumberInput = new JTextField("Fyll i personnummer", 15);
	JTextField createCustomerIdNumber = new JTextField("Fyll i personnummer", 15);
	JTextField createCustomerFirstname = new JTextField("Förnamn");
	JTextField createCustomerLastname = new JTextField("Efternamn");
	JTextField changeFirstNameInput = new JTextField("Förnamn", 15);
	JTextField changeLastNameInput = new JTextField("Efternamn", 15);
	
	//Temp text
	String[] cNames = {"Kontonummer", "Saldo", "Kontotyp", "Ränta"};
	Object[][] o = {
			{"1004", "15000", "Sparkonto", "1,0"},	
			{"1005", "1355", "Sparkonto", "1,0"},	
			{"1006", "155", "Sparkonto", "1,0"},	
			{"1007", "1", "Sparkonto", "1,0"},	
			{"1008", "1999", "kreditkonto", "0,5"}	
	};
	Object[] oo = {"1004 15000 kr Sparkonto 1,0 %", "1005 1202 kr Kreditkonto 0,5 %", "1007 800 kr Sparkonto 1,0 %"};
	Object[] cloAccInfo = {"1004", "1202 kr", "Sparkonto", "70 kr"};
	String[] ccNames = {"Personnummer", "Förnamn", "Efternamn"};
	Object[][] cc = {
			{"910129", "Fredrik", "Larsson"},
			{"881212", "Hampus", "Larsson"},
			{"000303", "Dennis", "Karlsson"},
			{"981229", "Anders", "Johansson"},
			{"940610", "Fredrik", "Hansson"},
	};
	
	public Gui() {
		JMenuBar menuBar = new JMenuBar(); //Skapar ett menyfält som ska ligga högst upp i fönstret.
		JMenu menu = new JMenu("Meny"); //Skapar två menyer där ena är huvudmenyn "menu" och den andra är en submeny "openAccount" som innehåller egna menyobjekt.
		JMenu openAccount = new JMenu("Öppna nytt konto");
		JMenuItem getAllCustomer = new JMenuItem("Hämta alla kunder");
		getAllCustomer.addActionListener(this);
		getAllCustomer.setActionCommand("getAllCustomer");
		JMenuItem createCustomer = new JMenuItem("Skapa ny kund");
		createCustomer.addActionListener(this);
		createCustomer.setActionCommand("createCustomer");
		JMenuItem savingsAccount = new JMenuItem("Sparkonto");
		savingsAccount.addActionListener(this);
		savingsAccount.setActionCommand("savingsAccount");
		JMenuItem creditAccount = new JMenuItem("Kreditkonto");
		creditAccount.addActionListener(this);
		creditAccount.setActionCommand("creditAccount");
		GridBagConstraints c = new GridBagConstraints();
		
		//Lägger till rubriken(welcomeLabel) och sökrutan som ska finnas på startsidan.
		welcomePanel.add(welcomeLabel);
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		searchPanel.add(customerIdNumberInput);
		searchPanel.add(Box.createVerticalStrut(10));
		searchPanel.add(searchCustomerButton);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
		searchPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Sök kund"), new EmptyBorder(50, 50, 50, 50)));
		searchCustomerButton.addActionListener(this);
		searchCustomerButton.setActionCommand("searchCustomerButton");
		
		//Lägger till de komponenter som ska finnas med när man vill hämta alla kunder.
		customersPanel.add(tableCustomers);
		customersPanel.setLayout(new BoxLayout(customersPanel, BoxLayout.PAGE_AXIS));
		customersPanel.setBorder(BorderFactory.createTitledBorder("Kunder"));
		chooseCustomerButton.addActionListener(this);
		chooseCustomerButton.setActionCommand("chooseCustomerButton");
		
		//Lägger till de komponenter som ska finnas på sidan hos en specifik kund med dennes nuvarande konton.
		accountsPanel.add(tableAccounts);
		accountsPanel.setLayout(new BoxLayout(accountsPanel, BoxLayout.PAGE_AXIS));
		accountsPanel.setBorder(BorderFactory.createTitledBorder("Konton"));
		chooseAccountButton.addActionListener(this);
		chooseAccountButton.setActionCommand("chooseAccountButton");
		customerButtonPanel.setLayout(new BoxLayout(customerButtonPanel, BoxLayout.PAGE_AXIS));
		customerButtonPanel.add(changeCustomerNameButton);
		customerButtonPanel.add(deleteCustomerButton);
		customerButtonPanel.add(createCreditAccountButton);
		customerButtonPanel.add(createSavingsAccountButton);
		deleteCustomerButton.addActionListener(this);
		deleteCustomerButton.setActionCommand("deleteCustomerButton");
		changeCustomerNameButton.addActionListener(this);
		changeCustomerNameButton.setActionCommand("changeCustomerNameButton");
		createCreditAccountButton.addActionListener(this);
		createCreditAccountButton.setActionCommand("createCreditAccountButton");
		createSavingsAccountButton.addActionListener(this);
		createSavingsAccountButton.setActionCommand("createSavingsAccountButton");
		
		//Lägger till de komponenter som ska finnas på sidan då man ska skapa en ny kund.
		createCustomerPanel.add(createCustomerIdNumber);
		createCustomerPanel.add(createCustomerFirstname);
		createCustomerPanel.add(createCustomerLastname);
		createCustomerPanel.add(Box.createVerticalStrut(10));
		createCustomerPanel.add(createCustomerButton);
		createCustomerPanel.setLayout(new BoxLayout(createCustomerPanel, BoxLayout.PAGE_AXIS));
		createCustomerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Skapa ny kund"), new EmptyBorder(50, 50, 50, 50)));
		createCustomerButton.addActionListener(this);
		createCustomerButton.setActionCommand("createCustomerButton");
		
		//De textfält som ska finnas i den popupruta som kommer upp när man ska byta namn på kunden.
		changeCustomerNamePanel.add(changeFirstNameInput);
		changeCustomerNamePanel.add(changeLastNameInput);
		
		//De komponenter som ska finnas när man kommer till ett specifikt konto.
		accountPanel.add(transactionInfo);
		accountButtonPanel.add(depositButton);
		accountButtonPanel.add(withdrawButton);
		accountButtonPanel.add(deleteAccountButton);
		accountButtonPanel.setLayout(new FlowLayout());
		deleteAccountButton.setActionCommand("deleteAccountButton");
		deleteAccountButton.addActionListener(this);
		depositButton.setActionCommand("depositButton");
		depositButton.addActionListener(this);
		withdrawButton.setActionCommand("withdrawButton");
		withdrawButton.addActionListener(this);
		accountPanel.setBorder(BorderFactory.createTitledBorder("Transaktioner"));
		accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.PAGE_AXIS));
		
		//Sätter layouten på den yttersta panelen "wrapperOuterPanel till en "GridBagLayout".
		wrapperOuterPanel.setLayout(new GridBagLayout());
		//Sätter layouten på den yttersta panelen som ska hålla i komponenterna som ska visas vid val av något av menyalternativen som systemet innehåller.
		wrapperPanelMenu.setLayout(new GridBagLayout());
		
		//Här ändras "GridBagConstraints" innan varje enskild panel adderas till den yttre wrapperOuterPanel eller wrapperPanelMenu.
		//welcomePanel och searchPanel ska befinna sig på samma sida.
		c.gridx = 0;
		c.gridy = 0;
		wrapperOuterPanel.add(welcomePanel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperOuterPanel.add(searchPanel, c);
		
		//customerButtonPanel, accountsPanel, chooseAccountButton ska alla  befinna sig på sidan för en specifik kund.
		c.gridx = 1;
		c.gridy = 0;
		wrapperOuterPanel.add(customerButtonPanel, c);
		c.gridx = 0;
		c.gridy = 0;
		wrapperOuterPanel.add(accountsPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperOuterPanel.add(chooseAccountButton, c);
		
		//createCustomerPanel ska befinna sig på en ensam sida där användaren ska skapa en ny kund.
		c.gridx = 0;
		c.gridy = 0;
		wrapperPanelMenu.add(createCustomerPanel, c);
		c.gridx = 0;
		c.gridy = 0;
		wrapperPanelMenu.add(customersPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperPanelMenu.add(chooseCustomerButton, c);
		
		//accountLabel, accountPanel, deleteAccountButton ska befinna sig på samma sida när användaren kommer till ett specifikt konto.
		c.gridx = 0;
		c.gridy = 0;
		wrapperOuterPanel.add(accountLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperOuterPanel.add(accountPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		wrapperOuterPanel.add(accountButtonPanel, c);
		
		//Panelerna här ska inte vara synliga på startsidan i systemet.
		accountButtonPanel.setVisible(false);
		accountPanel.setVisible(false);
		accountLabel.setVisible(false);
		wrapperPanelMenu.setVisible(false);
		createCustomerPanel.setVisible(false);
		createAccountPanel.setVisible(false);
		customersPanel.setVisible(false);
		accountsPanel.setVisible(false);
		chooseAccountButton.setVisible(false);
		chooseCustomerButton.setVisible(false);
		customerButtonPanel.setVisible(false);
		
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
		frame.add(wrapperPanelMenu);
		frame.add(wrapperOuterPanel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Gui();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int newAccountNumber;
		String command;
		if(e.getSource() instanceof JMenuItem) {
			command = ((JMenuItem) e.getSource()).getActionCommand();
		} else {
			command = ((JButton) e.getSource()).getActionCommand();
		}
		//Tar reda på vilken av knapparna eller menyalternativen som användaren har tryckt in.
		switch(command) {
		case "searchCustomerButton":
			//Ändra det nuvarande personnumret till den text i textfältet "customerIdNumberInput".
			currentIdNumber = customerIdNumberInput.getText();
			addRowToTable();
			accountsPanel.remove(tableAccounts);
			accountsPanel.add(tableAccounts);
			accountsPanel.revalidate();
			frame.repaint();
			searchPanel.setVisible(false);
			welcomePanel.setVisible(false);
			accountsPanel.setVisible(true);
			chooseAccountButton.setVisible(true);
			customerButtonPanel.setVisible(true);
			break;
		case "deleteCustomerButton":
			Object[] list = bank.deleteCustomer(currentIdNumber).toArray();
			customerClosingInfo = new JList<Object>(list);
			JOptionPane.showMessageDialog(frame, customerClosingInfo);
			searchPanel.setVisible(true);
			welcomePanel.setVisible(true);
			accountsPanel.setVisible(false);
			chooseAccountButton.setVisible(false);
			customerButtonPanel.setVisible(false);
			break;
		case "changeCustomerNameButton":
			JOptionPane.showMessageDialog(frame, changeCustomerNamePanel);
			String firstName_ = changeFirstNameInput.getText();
			String lastName_ = changeLastNameInput.getText();
			Boolean checkChange = bank.changeCustomerName(firstName_, lastName_, currentIdNumber);
			if(checkChange) {
				JOptionPane.showMessageDialog(frame, "Kundnamnet har ändrats");
				changeCustomerRow(firstName_, lastName_);
				accountsPanel.remove(tableAccounts);
				accountsPanel.add(tableAccounts);
				accountsPanel.revalidate();
				frame.repaint();
			} else {
				JOptionPane.showMessageDialog(frame, "Kundnamnet har inte ändrats");
			}
			break;
		case "getAllCustomer":
			addRowToCustomerTable();
			customersPanel.remove(tableCustomers);
			customersPanel.add(tableCustomers);
			customersPanel.revalidate();
			frame.repaint();
			wrapperOuterPanel.setVisible(false);
			wrapperPanelMenu.setVisible(true);
			customersPanel.setVisible(true);
			chooseCustomerButton.setVisible(true);
			break;
		case "chooseCustomerButton":
			currentIdNumber = tableCustomers.getValueAt(tableCustomers.getSelectedRow(), 0).toString();
			System.out.println(currentIdNumber);
			addRowToTable();
			accountsPanel.remove(tableAccounts);
			accountsPanel.add(tableAccounts);
			accountsPanel.revalidate();
			frame.repaint();
			wrapperPanelMenu.setVisible(false);
			chooseCustomerButton.setVisible(false);
			customersPanel.setVisible(false);
			searchPanel.setVisible(false);
			welcomePanel.setVisible(false);
			accountPanel.setVisible(false);
			accountLabel.setVisible(false);
			wrapperOuterPanel.setVisible(true);
			accountsPanel.setVisible(true);
			chooseAccountButton.setVisible(true);
			customerButtonPanel.setVisible(true);
			break;
		case "chooseAccountButton":
			currentAccountNumber = tableAccounts.getValueAt(tableAccounts.getSelectedRow(), 0).toString();
			Object[] transactionList = bank.getTransactions(currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray();
			transactionInfo = new JList<Object>(transactionList);
			String[] accountInfo = bank.getAccount(currentIdNumber, Integer.parseInt(currentAccountNumber));
			String accountBalance = accountInfo[1];
			String accountType = accountInfo[2];
			String accountInterest = accountInfo[3];
			try {
				accountLabel.setText(currentAccountNumber + " " + accountBalance + "kr " + accountType + " " + accountInterest + "%");
			} catch (NumberFormatException e1) {
				System.out.println("Kontonumrets format ogiltigt");
			}
			accountPanel.remove(transactionInfo);
			accountPanel.add(transactionInfo);
			accountPanel.revalidate();
			frame.repaint();
			accountsPanel.setVisible(false);
			chooseAccountButton.setVisible(false);
			customerButtonPanel.setVisible(false);
			accountButtonPanel.setVisible(true);
			accountPanel.setVisible(true);
			accountLabel.setVisible(true);
			break;
		case "deleteAccountButton":
			Object[] closingList = bank.closeAccount(currentIdNumber, Integer.parseInt(currentAccountNumber));
			accountClosingInfo = new JList<Object>(closingList);
			JOptionPane.showMessageDialog(frame, accountClosingInfo);
			addRowToTable();
			accountsPanel.remove(tableAccounts);
			accountsPanel.add(tableAccounts);
			accountsPanel.revalidate();
			frame.repaint();
			accountPanel.setVisible(false);
			accountButtonPanel.setVisible(false);
			accountLabel.setVisible(false);
			accountsPanel.setVisible(true);
			chooseAccountButton.setVisible(true);
			customerButtonPanel.setVisible(true);
			break;
		case "createCustomer":
			wrapperOuterPanel.setVisible(false);
			chooseCustomerButton.setVisible(false);
			wrapperPanelMenu.setVisible(true);
			createCustomerPanel.setVisible(true);
			break;
		case "createCustomerButton":
			String idNumber = createCustomerIdNumber.getText();
			String firstName = createCustomerFirstname.getText();
			String lastName = createCustomerLastname.getText();
			Boolean check = bank.createCustomer(firstName, lastName, idNumber);
			wrapperPanelMenu.setVisible(false);
			createCustomerPanel.setVisible(false);
			wrapperOuterPanel.setVisible(true);
			if(check) {
				JOptionPane.showMessageDialog(frame, "Kunden finns nu tillagd i systemet");
			} else {
				JOptionPane.showMessageDialog(frame, "Kunden kunde inte läggas till i systemet");
			}
			wrapperPanelMenu.setVisible(false);
			createCustomerPanel.setVisible(false);
			customersPanel.setVisible(false);
			chooseCustomerButton.setVisible(false);
			accountPanel.setVisible(false);
			accountButtonPanel.setVisible(false);
			accountLabel.setVisible(false);
			chooseAccountButton.setVisible(false);
			accountsPanel.setVisible(false);
			customerButtonPanel.setVisible(false);
			searchPanel.setVisible(true);
			welcomePanel.setVisible(true);
			wrapperOuterPanel.setVisible(true);
			break;
		case "createCreditAccountButton":
			newAccountNumber = bank.createCreditAccount(currentIdNumber);
			addRowToTable();
			accountsPanel.remove(tableAccounts);
			accountsPanel.add(tableAccounts);
			accountsPanel.revalidate();
			frame.repaint();
			JOptionPane.showMessageDialog(frame, "Det nya kontot har kontonummer: " + newAccountNumber);
			break;
		case "createSavingsAccountButton":
			newAccountNumber = bank.createSavingsAccount(currentIdNumber);
			addRowToTable();
			accountsPanel.remove(tableAccounts);
			accountsPanel.add(tableAccounts);
			accountsPanel.revalidate();
			frame.repaint();
			JOptionPane.showMessageDialog(frame, "Det nya kontot har kontonummer: " + newAccountNumber);
			break;
		}
	}
	
	private void addRowToTable() {
		ArrayList<String[]> li = bank.getCustomer(currentIdNumber);
		DefaultTableModel model = (DefaultTableModel) tableAccounts.getModel();
		model.setRowCount(0);
		model.setColumnCount(4);
		Object rowData[] = new Object[5];
		for(int i = 0; i < li.size(); i++) {
			for(int y = 0; y < li.get(i).length; y++) {
				rowData[y] = li.get(i)[y];
			}
			model.addRow(rowData);
		}
	}
	
	private void addRowToCustomerTable() {
		ArrayList<String[]> li = bank.getAllCustomers();
		DefaultTableModel model = (DefaultTableModel) tableCustomers.getModel();
		model.setRowCount(0);
		model.setColumnCount(3);
		Object rowData[] = new Object[5];
		for(int i = 0; i < li.size(); i++) {
			for(int y = 0; y < li.get(i).length; y++) {
				rowData[y] = li.get(i)[y];
			}
			model.addRow(rowData);
		}
	}
	
	private void changeCustomerRow(String firstName, String lastName) {
		tableAccounts.setValueAt(firstName, 0, 1);
		tableAccounts.setValueAt(lastName, 0, 2);
	}

}
