import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JCheckBox;

/**
 * The main graphical panel used to display conversion components.
 * 
 * This is the starting point for the assignment.
 *
 * You may want to start by improving the variable names and commenting what the existing code does.
 * 
 * @author Rizwan 
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	public CurrencyPanel currencyPanel;
	
	private final static String[] list = { "inches/cm", 
			                               "Miles/Nautical Miles",
			                               "Acres/Hectares",
			                               "Miles per hour/Kilometres per hour", 
			                               "Yards/Metres", 
			                               "Celsius/Fahrenheit", 
			                               "Degrees/Radians"
			                             };

	private JTextField textField;
	private JLabel label, conversionCount;
	private JComboBox<String> combo;
	private JButton convertButton, clearButton;
	private int convCount = 0;
	private JMenuItem exit, about, load;
	private JCheckBox checkbox;

	public int getConvCount() {
	  return convCount;
	}
	
	public void setConvCount(boolean clearBtnClicked) {
	  if (clearBtnClicked) {
	    convCount = 0;
	  } else {
	    convCount++;
	  }
	  conversionCount.setText("Conversion count : " + getConvCount());
	}

	public boolean isCheckboxSelected() {
	  return checkbox.isSelected();
	}
	
	JMenuBar setupMenu() {

		JMenuBar menuBar = new JMenuBar(); 
		
		// add file button with mnemonic
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
	    // add help button with mnemonic
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
	
		menuBar.add(file);
		menuBar.add(help);

		load = new JMenuItem("Load", new ImageIcon("load.jpg"));
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.ALT_MASK));
		//add exit button with shortcut
		exit = new JMenuItem("Exit", new ImageIcon("exit.png"));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,ActionEvent.ALT_MASK));
		file.add(load);
		file.add(exit);
		exit.setToolTipText("Enter ALT-1 to exit the application");

		ActionListener actionListener = new ConvertListener();
		load.addActionListener(actionListener);
		exit.addActionListener(actionListener);
        
		//add about button with shortcut
		about = new JMenuItem("About", new ImageIcon("help.png"));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,ActionEvent.ALT_MASK ));
		help.add(about);
		about.addActionListener(actionListener);
		about.setToolTipText("Enter ALT-2 to view dialogue box");

		return menuBar;
	}

	MainPanel() {

		ActionListener actionListener = new ConvertListener();
		KeyListener keyListener = new ConvertListener();
		
		combo = new JComboBox<String>(list);
		combo.addActionListener(actionListener); //convert values when option changed
		combo.setToolTipText("Click drop-down to see more options");

		JLabel inputLabel = new JLabel("Enter value:");

		convertButton = new JButton("Convert");
		convertButton.addActionListener(actionListener); // convert values when pressed	
		convertButton.setToolTipText("Click this button to convert the value");
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(actionListener);// removes values when pressed
		clearButton.setToolTipText("Click this button to clear all values");
				
		label = new JLabel("---");
		conversionCount =  new JLabel("Conversion count : " + getConvCount());
		
		textField = new JTextField(5);
		textField.setToolTipText("Enter value here");
		
		checkbox = new JCheckBox("Reverse conversion");
		checkbox.setToolTipText("Tick the box to reverse the conversion");

		textField.addKeyListener(keyListener);
		
		add(combo);
		add(inputLabel);
		add(textField);
		add(convertButton);
		add(label);
		add(clearButton);
		add(conversionCount);
		add(checkbox);
		
		setPreferredSize(new Dimension(800, 80));
		setBackground(Color.LIGHT_GRAY);
	}

    private class ConvertListener implements ActionListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
          //do the following if clear button is pressed  
		  if (event.getSource() == clearButton) {
		    textField.setText("");
		    setConvCount(true);
		    label.setText("---");
		    currencyPanel.clearValues();
		    //convert when button is pressed
		  } else if (event.getSource() == convertButton) {
            convert();
            //exit the application with button
		  } else if (event.getSource() == exit) {
			  System.exit(0);
			//dialogue box for about button  
		  } else if (event.getSource() == about) {
		      JOptionPane.showMessageDialog(about, "Purpose of application: Conversion between different units"
		      		                               + "\nAuthor Name: Rizwan Mansoor"
		      		                               + "\n© 2019 Rizwan Mansoor All Rights Reserved");
		  } else if (event.getSource() == load) {
		    JFileChooser chooser = new JFileChooser();
		    chooser.showOpenDialog(chooser);
			File file = chooser.getSelectedFile();
			currencyPanel.loadFile(file, true);
		  }
		}
		//enter key pressed to convert
		@Override
		public void keyPressed(KeyEvent event) {
		  if (event.getKeyCode() == KeyEvent.VK_ENTER) {
		    convert();
		  }
		}
		
		private void convert() {
		  String text = textField.getText().trim();
		  double value = 0;
		  
	      if (text.isEmpty() == false) {
	    	//validation check for invalid value  
	        try {
	    	  value = Double.parseDouble(text);
	        
	        // the default factor applied during the conversion
	        double factor = 0;

	        // the default offset applied during the conversion.
	        double offset = 0;

	        // Setup the correct factor/offset values depending on required conversion
	        switch (combo.getSelectedIndex()) {
	          case 0: // inches/cm
		      factor = 2.54;
		      break;
	          case 1: //Miles/Nautical Miles
			  factor = 0.868976;
			  break;
	          case 2: //Acres/Hectares
	          factor = 0.404686;
	          break;
	          case 3: //Miles per hour/Kilometres per hour
	          factor = 1.60934;
	          break;
	          case 4: //Yards/Metres
	          factor = 0.9144;
	          break;
	          case 5: //Celsius/Fahrenheit
	          factor = 1.8;
	          offset = 32;
	          break;
	          case 6: //Degrees/Radians
	          factor = 0.0174533; 
	          break;
	        }
	        //assign data type
	        double result;
	        
	        //reverse conversion
	        if (!isCheckboxSelected()) {
	          result = (value * factor) + offset;
	        } else {
		      result = (value - offset) / factor;
	        }
	        
	        //set result to 2 d.p
	        label.setText(String.format("%.2f",result));
	        setConvCount(false);
	        //validation check for empty box
	        } catch (NumberFormatException nfe) {
		      JOptionPane.showMessageDialog(convertButton, "Entered value is not a valid number");
		    }
	      	} else {
	          JOptionPane.showMessageDialog(convertButton, "Text input area is empty");
	      }
		}
						
		//@Override Needed for key pressed
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}
    }
}