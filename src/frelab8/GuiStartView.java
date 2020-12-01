package frelab8;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GuiStartView {
	private Gui gui;
	private JPanel mainPanel = new JPanel();
	private JPanel welcomePanel = new JPanel();
	private JLabel welcomeLabel = new JLabel("Vad vill du göra?");
	private JPanel searchPanel = new JPanel(); //Panel som innehåller interaktionskomponenter för att söka kund.
	private JButton searchCustomerButton = new JButton("Sök kund");//Knapp för att söka specifik kund.
	private JTextField customerIdNumberInput = new JTextField("Fyll i personnummer", 15);
	
	public JTextField getCustomerIdNumberInput() {
		return customerIdNumberInput;
	}

	public GuiStartView(Gui gui) {
		this.gui = gui;
		StartViewController startViewController = new StartViewController(gui, this);
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(new GridBagLayout());
		welcomePanel.add(welcomeLabel);
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		searchPanel.add(customerIdNumberInput);
		searchPanel.add(Box.createVerticalStrut(10));
		searchPanel.add(searchCustomerButton);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
		searchPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Sök kund"), new EmptyBorder(50, 50, 50, 50)));
		searchCustomerButton.addActionListener(startViewController);
		searchCustomerButton.setActionCommand("searchCustomer");
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(welcomePanel, c);
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(searchPanel, c);
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
}
