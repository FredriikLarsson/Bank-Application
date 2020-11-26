package frelab8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MenuActionListener implements ActionListener {
	JPanel createCustomerPanel = new JPanel();
	JButton createCustomerButton = new JButton("Skapa ny kund");
	JTextField createCustomerIdNumber = new JTextField("Fyll i personnummer", 15);
	JTextField customerFirstname = new JTextField("Förnamn", 15);
	JTextField customerLastname = new JTextField("Efternamn", 15);
	
	public MenuActionListener () {
		
		createCustomerPanel.add(createCustomerIdNumber);
		createCustomerPanel.add(customerFirstname);
		createCustomerPanel.add(customerLastname);
		createCustomerPanel.add(Box.createVerticalStrut(10));
		createCustomerPanel.add(createCustomerButton);
		createCustomerPanel.setLayout(new BoxLayout(createCustomerPanel, BoxLayout.PAGE_AXIS));
		createCustomerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Skapa ny kund"), new EmptyBorder(50, 50, 50, 50)));
		createCustomerButton.addActionListener(this);
		createCustomerButton.setActionCommand("createCustomerButton");
		
		createCustomerPanel.setVisible(false);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command;
		if(e.getSource() instanceof JMenuItem) {
			command = ((JMenuItem) e.getSource()).getActionCommand();
		} else {
			command = ((JButton) e.getSource()).getActionCommand();
		}
		
		switch(command) {
		case "createCustomer":
			createCustomerPanel.setVisible(true);
			break;
		}
		
	}

}
