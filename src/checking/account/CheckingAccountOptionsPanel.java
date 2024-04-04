//******************************************************************************
//  CheckingAccountOptionsPanel.java                    Author: Tyler Davies
//  Sets-up GUI with Radio Button Options/Group; has added options for files.
//******************************************************************************

package checking.account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckingAccountOptionsPanel  extends JPanel 
{
    //Declare variables for the JLabel and JRadioButtons.
    private JLabel prompt;
    private JRadioButton one, two, three, four, five, six;
        
    public CheckingAccountOptionsPanel()
    {
        //Set-up JLabel and Radio Buttons.
        prompt = new JLabel("Choose action:");
        prompt.setFont (new Font ("Helvetica", Font.BOLD, 30));
        one = new JRadioButton("Enter Transaction");
        one.setFont(new Font("Helvetica", Font.PLAIN, 12));
        one.setBackground (Color.green);
        two = new JRadioButton("List All Transactions");
        two.setFont(new Font("Helvetica", Font.PLAIN, 12));
        two.setBackground (Color.green);
        three = new JRadioButton("List All Checks");
        three.setFont(new Font("Helvetica", Font.PLAIN, 12));
        three.setBackground (Color.green);
        four = new JRadioButton("List All Deposits");
        four.setFont(new Font("Helvetica", Font.PLAIN, 12));
        four.setBackground (Color.green);
        five = new JRadioButton("Open File");
        five.setFont(new Font("Helvetica", Font.PLAIN, 12));
        five.setBackground (Color.green);
        six = new JRadioButton("Save File");
        six.setFont(new Font("Helvetica", Font.PLAIN, 12));
        six.setBackground (Color.green);
        
        //Add Radio Buttons to Group.
        ButtonGroup group = new ButtonGroup();
        group.add(one);
        group.add(two);
        group.add(three);
        group.add(four);
        group.add(five);
        group.add(six);
        
        //Create listener for Radio Buttons.
        CheckingAccountOptionListener listener = new CheckingAccountOptionListener();
        one.addActionListener(listener);
        two.addActionListener(listener);
        three.addActionListener(listener);
        four.addActionListener(listener);
        five.addActionListener(listener);
        six.addActionListener(listener);
        
        //Add JLabel and Radio Buttons to panel.
        add(prompt);
        add(one);
        add(two);
        add(three);
        add(four);
        add(five);
        add(six);

        //Set color and size of the panel.
        setBackground (Color.green);
        setPreferredSize (new Dimension(300, 150));
    }
        
    private class CheckingAccountOptionListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            //Set-up Action Listener and provide options for each choice.
            Object source = event.getSource();
            if (source == one)
            {
                Main.addTrans();
            }
            else if (source == two)
            {
                Main.displayAllTrans();
            }
            else if (source == three)
            {
                Main.displayChecks();
            }
            else if (source == four)
            {
                Main.displayDeposits();
            }
            else if (source == five)
            {
                Main.openFile();
            }
            else
            {
                Main.saveFile();
            }
        }
    }
}