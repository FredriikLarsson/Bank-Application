package frelab8;

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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder; 

public class Gui implements ActionListener {
	BankLogic bank = new BankLogic();
	//Alla knappar som finns på sidan.
	JButton handleCustomer = new JButton("Hantera kunder");
	JButton handleAccount = new JButton("Hantera konton");
	JButton searchCustomerButton = new JButton("Sök kund");
	JButton deleteCustomerButton = new JButton("Ta bort kund");
	JButton changeCustomerNameButton = new JButton("Ändra kundnamn");
	JButton searchAccountButton = new JButton("Sök konto");
	JButton deleteAccountButton = new JButton("Ta bort konto");
	JButton showTransactionButton = new JButton("Hämta transaktioner");
	//Skapar de paneler som ska innehålla de knappar som finns på sidan.
	JPanel searchCustomerPanel = new JPanel();
	JPanel deleteCustomerPanel = new JPanel();
	JPanel changeCustomerNamePanel = new JPanel();
	JPanel searchAccountPanel = new JPanel();
	JPanel deleteAccountPanel = new JPanel();
	JPanel showTransactionPanel = new JPanel();
	JPanel accountPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	//En panel "welcomePanel" som innehåller labeln "welcomeLabel" som är rubriken på sidan.
	JPanel welcomePanel = new JPanel();
	JLabel welcomeLabel = new JLabel("Vad vill du göra?");
	//Arrayen som ska hålla de labels som presenterar resultatet utifrån användarens interaktion med systemet.
	//En array som ska hålla de textfält som användaren ska kunna fylla i vid användning av funktionaliteten i systemet.
	private JTextField[] textFields = new JTextField[11];
	String currentAccountNumber;
	String currentIdNumber;
	JTextField accounNumberInput = new JTextField("Fyll i kontonummer:", 15);
	JTextField customerIdNumberInput = new JTextField("Fyll i personnummer", 15);
	
	public Gui() {
		//Skapar ett nytt fönster "frame" med titeln "Banksystem".
		JFrame frame = new JFrame("Banksystem");
		//Skapar ett menyfält som ska ligga högst upp i fönstret.
		JMenuBar menuBar = new JMenuBar();
		//Skapar två menyer där ena är huvudmenyn "menu" och den andra är en submeny "openAccount" som innehåller egna menyobjekt.
		JMenu menu = new JMenu("Meny");
		JMenu openAccount = new JMenu("Öppna nytt konto");
		//Skapar fyra olika menyobjekt som ska placeras inuti de två skapade menyerna.
		JMenuItem getAllCustomer = new JMenuItem("Hämta alla kunder");
		JMenuItem createCustomer = new JMenuItem("Skapa ny kund");
		JMenuItem savingsAccount = new JMenuItem("Sparkonto");
		JMenuItem creditAccount = new JMenuItem("Kreditkonto");
		//Skapar en panel "handlePanel" som kommer innehålla två knappar i "handleCustomer" och "handleAccount".
		JPanel handlePanel = new JPanel();
		//Skapar en wrapper panel som använder layouten "gridbaglayout" för placeringen av olika objekt på sidan.
		JPanel wrapperOuterPanel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		
		//Lägger till rubriken(welcomeLabel) och de två knapparna (handleCustomer och handeAccount) i deras respektive skapade panel.
		welcomePanel.add(welcomeLabel);
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		handlePanel.add(handleCustomer);
		handleCustomer.setActionCommand("handleCustomer");
		handleCustomer.addActionListener(this);
		handlePanel.add(handleAccount);
		handleAccount.setActionCommand("handleAccount");
		handleAccount.addActionListener(this);
		
		//Skapar och stylar de olika rutorna som ska inkludera textfält för input tillsammans med en submit knapp för varje ruta,
		//rutorna searchAccountPanel, deleteAccountPanel och showTransactionPanel ska befinna sig på samma sida i systemet.

		String s[] = {"hej", "hejdå"};
		JList<String> list = new JList<String>(s);
		searchCustomerPanel.add(list);
		
		searchAccountPanel.add(customerIdNumberInput);
		searchAccountPanel.add(accounNumberInput);
		searchAccountPanel.add(Box.createVerticalStrut(10));
		searchAccountPanel.add(searchAccountButton);
		searchAccountPanel.setLayout(new BoxLayout(searchAccountPanel, BoxLayout.PAGE_AXIS));
		searchAccountPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Sök konto"), new EmptyBorder(50, 50, 50, 50)));
		searchAccountButton.addActionListener(this);
		searchAccountButton.setActionCommand("searchAccountButton");
		
		accountPanel.add(new JLabel("konto: 1500222"));
		buttonPanel.add(deleteAccountButton);
		buttonPanel.add(showTransactionButton);
		accountPanel.add(buttonPanel);
		accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.PAGE_AXIS));
		
		//deleteAccountPanel.add(deleteAccountButton);
		//deleteAccountPanel.setLayout(new BoxLayout(deleteAccountPanel, BoxLayout.PAGE_AXIS));
		//deleteAccountPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Ta bort konto"), new EmptyBorder(50, 50, 50, 50)));
		//deleteAccountButton.addActionListener(this);
		//deleteAccountButton.setActionCommand("deleteAccountButton");
		showTransactionPanel.add(textFields[4] = new JTextField("Fyll i personnummer", 15));
		showTransactionPanel.add(textFields[5] = new JTextField("Fyll i kontonummer", 15));
		showTransactionPanel.add(Box.createVerticalStrut(10));
		showTransactionPanel.add(showTransactionButton);
		showTransactionPanel.setLayout(new BoxLayout(showTransactionPanel, BoxLayout.PAGE_AXIS));
		showTransactionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Hämta transaktioner"), new EmptyBorder(30, 30, 30, 30)));
		showTransactionButton.addActionListener(this);
		showTransactionButton.setActionCommand("showTransactionButton");
		//Skapar och stylar tre till rutor i form av tre olika paneler som innehåller input i form av textfält och varsin submit knapp,
		//rutorna searchCustomerPanel, deleteCustomerPanel och changeCustomerNamePanel ska befinna sig på samma sida i systemet.
		searchCustomerPanel.add(textFields[6] = new JTextField("Fyll i personnummer", 15));
		searchCustomerPanel.add(Box.createVerticalStrut(10));
		searchCustomerPanel.add(searchCustomerButton);
		searchCustomerPanel.setLayout(new BoxLayout(searchCustomerPanel, BoxLayout.PAGE_AXIS));
		searchCustomerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Sök kund"), new EmptyBorder(50, 50, 50, 50)));
		searchCustomerButton.addActionListener(this);
		searchCustomerButton.setActionCommand("searchCustomerButton");
		deleteCustomerPanel.add(textFields[7] = new JTextField("Fyll i personnummer", 15));
		deleteCustomerPanel.add(Box.createVerticalStrut(10));
		deleteCustomerPanel.add(deleteCustomerButton);
		deleteCustomerPanel.setLayout(new BoxLayout(deleteCustomerPanel, BoxLayout.PAGE_AXIS));
		deleteCustomerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Ta bort kund"), new EmptyBorder(50, 50, 50, 50)));
		deleteCustomerButton.addActionListener(this);
		deleteCustomerButton.setActionCommand("deleteCustomerButton");
		changeCustomerNamePanel.add(textFields[8] = new JTextField("Förnamn", 15));
		changeCustomerNamePanel.add(textFields[9] = new JTextField("Efternamn", 15));
		changeCustomerNamePanel.add(textFields[10] = new JTextField("Personnummer", 15));
		changeCustomerNamePanel.add(Box.createVerticalStrut(10));
		changeCustomerNamePanel.add(changeCustomerNameButton);
		changeCustomerNamePanel.setLayout(new BoxLayout(changeCustomerNamePanel, BoxLayout.PAGE_AXIS));
		changeCustomerNamePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Ändra kundnamn"), new EmptyBorder(30, 30, 30, 30)));
		changeCustomerNameButton.addActionListener(this);
		changeCustomerNameButton.setActionCommand("changeCustomerNameButton");
		//Sätter layouten på den yttersta panelen "wrapperOuterPanel till en "GridBagLayout" för att kunna bygga upp ett flexibelt mindre rutnät av de olika rutorna
		//som skapats.
		wrapperOuterPanel.setLayout(new GridBagLayout());
		
		//Här ändras "GridBagConstraints" innan varje enskild panel adderas till den yttre wrapperOuterPanel, detta för att få till ett önskat rutnät.
		//welcomePanel och handlePanel ska befinna sig på samma sida.
		c.gridx = 0;
		c.gridy = 0;
		wrapperOuterPanel.add(welcomePanel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperOuterPanel.add(handlePanel, c);
		//searchCustomerPanel, deleteCustomerPanel och changeCustomerNamePanel ska befinna sig på samma sida.
		c.gridx = 0;
		c.gridy = 0;
		wrapperOuterPanel.add(searchCustomerPanel, c);
		c.gridx = 1;
		c.gridy = 0;
		wrapperOuterPanel.add(deleteCustomerPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperOuterPanel.add(changeCustomerNamePanel, c);
		//searchAccountPanel, deleteAccountPanel och showTransactionPanel ska befinna sig på samma sida.
		c.gridx = 0;
		c.gridy = 0;
		wrapperOuterPanel.add(searchAccountPanel, c);
		c.gridx = 1;
		c.gridy = 0;
		wrapperOuterPanel.add(deleteAccountPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		wrapperOuterPanel.add(showTransactionPanel, c);
		
		c.gridx = 0;
		c.gridy = 0;
		wrapperOuterPanel.add(accountPanel);
		
		searchCustomerPanel.setVisible(false);
		deleteCustomerPanel.setVisible(false);
		changeCustomerNamePanel.setVisible(false);
		searchAccountPanel.setVisible(false);
		deleteAccountPanel.setVisible(false);
		showTransactionPanel.setVisible(false);
		accountPanel.setVisible(false);
		
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
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Gui();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = ((JButton) e.getSource()).getActionCommand();
		
		switch(command) {
		case "handleCustomer":
			searchCustomerPanel.setVisible(true);
			deleteCustomerPanel.setVisible(true);
			changeCustomerNamePanel.setVisible(true);
			handleCustomer.setVisible(false);
			handleAccount.setVisible(false);
			welcomePanel.setVisible(false);
			textFields[6].setVisible(false);
			
			break;
		case "handleAccount":
			searchAccountPanel.setVisible(true);
			deleteAccountPanel.setVisible(false);
			showTransactionPanel.setVisible(false);
			handleCustomer.setVisible(false);
			handleAccount.setVisible(false);
			welcomePanel.setVisible(false);
			break;
		case "searchCustomerButton":
			//a = new JList<Object>(bank.getCustomer(textFields[6].getText()).toArray());
			//textFields[0].setVisible(false);
			//textFields[1].setVisible(false);;
			break;
		case "deleteCustomerButton":
			break;
		case "changeCustomerNameButton":
			break;
		case "searchAccountButton":
			searchAccountPanel.setVisible(false);
			accountPanel.setVisible(true);
			break;
		case "deleteAccountButton":
			break;
		case "showTransactionButton":
			break;
		}
		
	}

}
