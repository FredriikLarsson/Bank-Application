package frelab8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/*
 * Detta är controllern till startvyn som hanterar all interaktion med startvyn.
 * 
 * @author Fredrik Larsson, frelab-8
 */

public class StartViewController implements ActionListener{
	private Gui gui; //Hämtar den instans av Gui klassen som finns för att ha tillgång att manipulera denna instans.
	private GuiStartView guiStartView; //Hämtar Startvy instansen för att manipulera denna.
	private String currentIdNumber;
	
	public StartViewController(Gui gui, GuiStartView guiStartView) {
		this.gui = gui;
		this.guiStartView = guiStartView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GuiCustomerView customerView = gui.getCustomerView(); //Hämtar kundvyn för att manipulera denna innan systemets cardlayouten placerar den vyn längst fram i layouten.
		currentIdNumber = guiStartView.getCustomerIdNumberInput().getText();
		gui.setCurrentIdNumber(currentIdNumber);
		try {
			gui.getCustomerView().getCustomerViewController().addRowToTable();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gui.frame, "Kunden kunde tyvärr inte hittas i systemet");
			return;
		}
		customerView.getAccountsPanel().remove(customerView.getTableAccounts());
		customerView.getAccountsPanel().add(customerView.getTableAccounts());
		customerView.getAccountsPanel().revalidate();
		gui.frame.repaint();
		//Placerar kundvyn längst fram i layouten.
		gui.getCardLayout().show(gui.getC(), "customerView");
	}
	

}
