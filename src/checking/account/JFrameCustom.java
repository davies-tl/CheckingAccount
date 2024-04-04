//******************************************************************************
//  JFrameCustom.java                                   Author: Tyler Davies
//  Custom JFrame to allow for saving upon closing the window.
//  Also gives the option to close program based on other choices made.
//  Note that this frame will be created with a default do nothing on close,
//  which will allow the user to hit cancel to get back to the OptionsPanel.
//******************************************************************************

package checking.account;

import javax.swing.*;
//import java.awt.*;
import java.awt.event.*;

public class JFrameCustom extends JFrame
{
    //Constructor
    public JFrameCustom(String title)
    {
        super(title);
        FrameListener listener = new FrameListener();
        addWindowListener(listener);
    }
    
    //New class with override method for windowClosing.
    private class FrameListener extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            int confirm;
            if(!Main.saved)
            {
                String  message = "The data in the application is not saved.\n"+
                                  "Would you like to save it before exiting the application?";
                confirm = JOptionPane.showConfirmDialog (null, message);
                
                if(confirm == JOptionPane.YES_OPTION)
                {
                    Main.selectFile(2);
                    System.exit(0);
                }
                if(confirm == JOptionPane.NO_OPTION)
                {
                        System.exit(0);
                }
            }
            else
            {
                System.exit(0);    //If information is saved, just close the program.
            }
        }
    }  
}
