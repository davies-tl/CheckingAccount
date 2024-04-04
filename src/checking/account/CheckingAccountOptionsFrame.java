//******************************************************************************
//  CheckingAccountOptionsFrame.java                    Author: Tyler Davies
//  Switches GUI from OptionsPane to OptionsFrame now with JMenu.
//******************************************************************************

package checking.account;

//import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CheckingAccountOptionsFrame extends JFrameCustom
{
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
    private JMenu fileMenu, accountsMenu, transMenu;
    private JMenuItem openFile, saveFile, addNew, listAll, listChecks, listDeposits, listService, findAccount, listAccounts, enterTrans;
    private JMenuBar bar;
    
    public CheckingAccountOptionsFrame(String title )
    {
        super(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       
        fileMenu = new JMenu("File");
        
        MenuListener ml = new MenuListener();     

        openFile = new JMenuItem("Open File");
        openFile.addActionListener(ml);
        fileMenu.add(openFile);

        saveFile = new JMenuItem("Save File");
        saveFile.addActionListener(ml);
        fileMenu.add(saveFile);

        accountsMenu = new JMenu("Accounts");
        
        addNew = new JMenuItem("Add New Account");
        addNew.addActionListener(ml);
        accountsMenu.add(addNew);

        listAll = new JMenuItem("List All Transactions");
        listAll.addActionListener(ml);
        accountsMenu.add(listAll);

        listChecks = new JMenuItem("List All Checks");
        listChecks.addActionListener(ml);
        accountsMenu.add(listChecks);
        
        listDeposits = new JMenuItem("List All Deposits");
        listDeposits.addActionListener(ml);
        accountsMenu.add(listDeposits);
        
        listService = new JMenuItem("List All Service Charges");
        listService.addActionListener(ml);
        accountsMenu.add(listService);
        
        findAccount = new JMenuItem("Find An Account");
        findAccount.addActionListener(ml);
        accountsMenu.add(findAccount);
        
        listAccounts = new JMenuItem("List All Accounts");
        listAccounts.addActionListener(ml);
        accountsMenu.add(listAccounts);
        
        transMenu = new JMenu("Transactions");
        
        enterTrans = new JMenuItem("Enter Transaction");
        enterTrans.addActionListener(ml);
        transMenu.add(enterTrans);

        bar = new JMenuBar();
        bar.add(fileMenu);
        bar.add(accountsMenu);
        bar.add(transMenu);
        setJMenuBar(bar);

    }
    private class MenuListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event) 
        {
            String source = event.getActionCommand();

            if (source.equals("Open File"))
                Main.openFile();
            else
                if (source.equals("Save File"))
                    Main.saveFile();
                else
                    if (source.equals("Add New Account"))
                        Main.newAccount();
                    else
                        if (source.equals("List All Transactions"))
                            Main.displayAllTrans();
                        else
                            if (source.equals("List All Checks"))
                                Main.displayChecks();
                            else
                                if (source.equals("List All Deposits"))
                                    Main.displayDeposits();
                                else
                                    if (source.equals("List All Service Charges"))
                                        Main.displayServiceCharges();
                                    else
                                        if (source.equals("Find An Account"))
                                            Main.findAccount();
                                        else
                                            if (source.equals("List All Accounts"))
                                                Main.listAccounts();
                                            else
                                                if (source.equals("Enter Transaction"))
                                                    Main.addTrans();
        }
    }
}
