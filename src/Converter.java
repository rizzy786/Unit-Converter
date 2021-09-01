import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main driver program for the GUI based conversion program.
 * 
 * @author mdixon
 */
public class Converter {
	 
    public static void main(String[] args) {
    	
        JFrame frame = new JFrame("Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a new master panel
        JPanel masterPanel = new JPanel();
       
        // Use a box layout to stack the panels
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
        
        // Create the new unit conversion panel
        MainPanel panel = new MainPanel();
        frame.setJMenuBar(panel.setupMenu());
        
        // Create the new currency panel
        CurrencyPanel currencyPanel = new CurrencyPanel();
                
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        currencyPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        // Add the sub-panels to the master panel
        masterPanel.add(panel);
        masterPanel.add(currencyPanel);

        frame.getContentPane().add(masterPanel);

        panel.currencyPanel = currencyPanel;

        currencyPanel.mainPanel = panel;

        frame.pack();
        frame.setVisible(true);
    }
}
