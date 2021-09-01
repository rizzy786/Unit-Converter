import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CurrencyPanel extends JPanel {
  public MainPanel mainPanel;
  /* for requirement 1
  private final static String[] list = { "Euro (EUR)", 
                                         "US Dollars (USD)",
                                         "Australian Dollars (AUD)",
                                         "Canadian Dollars (CAD)",
                                         "Icelandic Króna (ISK)",
                                         "United Arab Emirates Dirham (AED)",
                                         "South African Rand (ZAR)",
                                         "Thai Baht (THB)"
                                         };
  */
  private String [] list;
  private JTextField textField;
  private JComboBox<String> combo;
  private JButton convertButton;
  private JLabel label;
  
  ArrayList<Currency> currencyList = new ArrayList<Currency>();

  public void loadFile(File filename, boolean userSelected) {
	File file = new File(filename.toString());

    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));

      String line = in.readLine();
         
      currencyList.clear();
      
      while ( line != null ) {
        // Process 'line' (split up).
        String [] parts = line.split(",");
        
        Currency currency = new Currency(parts[0].trim(), Double.parseDouble(parts[1].trim()), parts[2].trim());
        currencyList.add(currency);
        // validate, store somewhere etc.
        line = in.readLine();    // read next line (if available)
      }

      in.close();
      
      if (userSelected) {
        combo.removeAllItems();
      }

      list = new String [currencyList.size()];

      for (int i = 0; i < currencyList.size(); i++) {
       	list[i] = currencyList.get(i).getName();
       	if (userSelected) {
       	  combo.addItem(list[i]);
       	}
      }
    } catch (Exception e) {
      // Something went wrong.
      //String msg = e.getMessage();
      // show the message to the user!
    }
  }
  
  public void clearValues() {
    textField.setText("");
    label.setText("---");
  }
  
  CurrencyPanel() {
	ActionListener actionListener = new ConvertListener();
	KeyListener keyListener = new ConvertListener();

    JLabel inputLabel = new JLabel("Enter value:");
	File file = new File("currency.txt");

    loadFile(file, false);

    combo = new JComboBox<String>(list);
    combo.addActionListener(actionListener); //convert values when option changed

    textField = new JTextField(5);
    convertButton = new JButton("Convert");
	convertButton.addActionListener(actionListener); // convert values when pressed	
    label = new JLabel("---");

	textField.addKeyListener(keyListener);

    add(combo);
    add(inputLabel);
    add(textField);
    add(convertButton);
    add(label);
    
    setPreferredSize(new Dimension(800, 80));
	setBackground(Color.LIGHT_GRAY);
  }
  
  private class ConvertListener implements ActionListener, KeyListener {
    @Override
	public void actionPerformed(ActionEvent event) {
      //do the following if convert button is pressed  
	  if (event.getSource() == convertButton) {
	    convert();
	  }
	}    
    
	private void convert() {
      String text = textField.getText().trim();
      
	  String currencySymbol = "";
      String currency = "";
      double value = 0;
      
      if (text.isEmpty() == false) {
	    //validation check for invalid value  
	    try {
	      value = Double.parseDouble(text);
	      
          //double factor = 0;
      
          //double value = Double.parseDouble(text);
          double result = 0;
      
          switch (combo.getSelectedIndex()) {
          	case 0: //Euro (EUR)
          	//factor = 1.06;
          	//currencySymbol="€";
            currencySymbol = currencyList.get(combo.getSelectedIndex()).getSymbol();
          	break;
          	case 1: //US Dollars (USD)
          	//factor = 1.14;
          	//currencySymbol="$";
            currencySymbol = currencyList.get(combo.getSelectedIndex()).getSymbol();
          	break;
          	case 2: //Australian Dollars (AUD)
          	//factor = 1.52;
          	//currencySymbol="$";
          	currencySymbol = currencyList.get(combo.getSelectedIndex()).getSymbol();
          	break;
          	case 3: //Canadian Dollars (CAD) Bermudan Dollar (BMD)
            //factor = 1.77;
            //currencySymbol="$";
          	currencySymbol = currencyList.get(combo.getSelectedIndex()).getSymbol();            
          	break;
          	case 4: //Icelandic Króna (ISK)
          	//factor = 130.62;
          	currency = currencyList.get(combo.getSelectedIndex()).getSymbol();
          	break;
          	case 5://United Arab Emirates Dirham (AED)
          	//factor = 4.6;
          	//currencySymbol="د" + "." + "إ";
            currencySymbol = currencyList.get(combo.getSelectedIndex()).getSymbol();
          	break;
          	case 6: //South African Rand (ZAR) 
          	//factor = 17.96;
          	currency = currencyList.get(combo.getSelectedIndex()).getSymbol();
          	break;
          	case 7: //Thai Baht (THB) 
          	//factor = 44.28;
          	//currencySymbol="฿";
            currencySymbol = currencyList.get(combo.getSelectedIndex()).getSymbol();
          	break;
          }
      
          //reverse conversion
          if (!mainPanel.isCheckboxSelected()) {
            result = (value * currencyList.get(combo.getSelectedIndex()).getFactor());
          } else {
    	    currencySymbol="£";
    	    currency="";
    	    result = (value / currencyList.get(combo.getSelectedIndex()).getFactor());
          }
       
          label.setText(String.format(currencySymbol + "%.2f", result) + currency);
          mainPanel.setConvCount(false);
	    } catch (NumberFormatException nfe) {
          JOptionPane.showMessageDialog(convertButton, "Entered value is not a valid number");          
	    }
      } else {
	    JOptionPane.showMessageDialog(convertButton, "Text input area is empty");
	  }
	}

	@Override
	public void keyTyped(KeyEvent e) {
	  // TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
	  if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        convert();
      }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	  // TODO Auto-generated method stub
    }
  }
}
