//******************************************************************************
//  Main.java                                           Author: Tyler Davies
//  Using GUIs, set up CheckingAccount and Transaction objects
//  to help track a checking account's transactions and service charges.
//  Now user is able to input, output, and save data using a file dialog.
//  Adds vectors and JMenu.
//******************************************************************************

package checking.account;

import java.text.DecimalFormat;
//import java.util.Arrays;
//import java.util.Locale;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main
{
    //Declare JFrames.
    //Declare CheckingAccount object to keep track of account information.
    //Declare Transaction objec to keep a list of transactions.
    //Declare other global variables.
    //Set DecimalFormat to accounting format (both positive and negative).
    public static JFrame deposits;
    public static CheckingAccountOptionsFrame frame;
    public static JTextArea textArea;
    public static String filename = "C:\\acct.dat";
    public static boolean saved = false;
    public static Vector<CheckingAccount> accountList;
    static CheckingAccount account;
    static Transaction trans;
    public static String message, accountName;
    static boolean check = false, selectAccount = false;
    static double startingBalance, cashAmt, checkAmt;
    static int checkNumber;
    static DecimalFormat fmt = new DecimalFormat("$#0.00; ($#0.00)"); //zero need to be placed before period for correct money format.
      
    //Get initial account balance, and set-up JFrame with account options.
    public static void main(String[] args) 
    {
        accountList = new Vector<CheckingAccount>();
        
        frame = new CheckingAccountOptionsFrame("Checking Account Menu");      
        
        textArea = new JTextArea(25, 75);
        textArea.setFont(new Font("Monospaced",Font.PLAIN, 12));

        frame.getContentPane().add(textArea);
        frame.pack();
        frame.setVisible(true);  
    }
    
    //Add transaction to account, using transaction code 0 to exit, 1 for checks, and 2 for deposits.
    //If 0, print a final account update/message.
    public static void addTrans()
    {
        double transAmount;
        int transCode;
        
        saved = false;    //Sets saved to false for newly opened files (since new changes are to be made)
        
        if(!selectAccount)
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        else
        {
            transCode = getTransCode();
            if (transCode == 0)
            {
                message = "Transaction: End" + "\n" +
                          "Current Balance: " + fmt.format(account.getBalance()) + "\n" +
                          "Total Service Charge: " + fmt.format(account.getSerivceCharge()) + "\n" +
                          "Final Balance: " + fmt.format(account.getBalance() - account.getSerivceCharge());
                JOptionPane.showMessageDialog (null, message);
            }
            else if (transCode == -1)
            {
                //Do nothing; user exited the code input and are being sent back to main menu.
            }
            else
            {
                if (transCode == 1)
                {
                    checkNumber = getCheck();
                    if(checkNumber >= 0)
                    {
                        transAmount = getCheckAmount();
                        trans = new Check(account.getTransCount(), transCode, transAmount, checkNumber);
                    }
                    else
                    {
                        transAmount = -1;
                    }
                }
                else //if (transCode == 2)
                {
                    transAmount = getDepositAmount();
                    trans = new Deposit(account.getTransCount(), transCode, transAmount, cashAmt, checkAmt);
                }
            
                if(transAmount != -1)
                {
                    account.addTrans(trans);
                    account.setBalance(transAmount, transCode);
            
                    if (transCode == 1)
                    {
                        account.setServiceCharge(0.15);
                        trans = new ServiceCharge(account.getTransCount(), 3, 0.15);
                        account.addTrans(trans);
                        processCheck(account, transAmount);
                    }
                    else //if (transCode == 2)
                    {
                        account.setServiceCharge(0.10);
                        trans = new ServiceCharge(account.getTransCount(), 3, 0.10);
                        account.addTrans(trans);
                        processDeposit(account, transAmount);
                    }
                }
            }
        }
    }
    
    //Prompt user for their transaction code (0, 1, or 2).
    public static int getTransCode()
    {
        boolean validInput = false, validChoice = false;
        int code = -1;
        
        while(!validInput)
        {
            try
            {
                while(!validChoice)
                {
                    String codeStr = JOptionPane.showInputDialog ("Enter your transaction code:");
                    if(codeStr.length() >= 0)
                    {
                        code = Integer.parseInt(codeStr);
                    }
                    if(code < 0 || code > 2)
                    {
                        JOptionPane.showMessageDialog(null, "ERROR: You must enter a valid transaction code to continue:" + "\n" +
                                                            "Checks(1), Deposits(2), OR Exit Program(0)");
                    }
                    else
                    {
                        validChoice = true;
                    }
                }
                validInput = true;
            }
            catch(NullPointerException nullEx)
            {
                validInput = true;   //Set to true to exit loop   
            }
            catch(NumberFormatException n)
            {
                JOptionPane.showMessageDialog(null, "ERROR: You must enter a valid transaction code to continue:" + "\n" +
                                                    "Checks(1), Deposits(2), OR Exit Program(0)");
            }
            catch (Exception e)
            {
                e.printStackTrace(System.err);
                System.err.println("An unknown error/exception has occurred.");
            }
        }
        return code;
    }
    
    //Prompt user for the check number.
    public static int getCheck()
    {
        boolean validInput = false, validPositive = false;
        int amount = -1;
        
        while(!validInput)
        {
            try
            {
                while(!validPositive)
                {
                    String checkNumber = JOptionPane.showInputDialog ("Enter your check number:");
                    if (checkNumber.length() >= 0)
                    {
                        amount = Integer.parseInt(checkNumber);
                    }
                    if (amount < 0)
                    {
                        JOptionPane.showMessageDialog(null, "ERROR: The check number must be a positive value." + "\n" +
                                                            "Please try again!");
                    }
                    else
                    {
                        validPositive = true;
                    }
                }
                validInput = true;
                return amount;
            }
            //User existed or pressed cancel; user will be taken back to JFrameCustom frame.
            catch(NullPointerException nullEx)
            {
                validInput = true;   //Set to true to exit loop
            }
            catch(NumberFormatException n)
            {
                JOptionPane.showMessageDialog(null, "ERROR: The check number must be entered in digits." + "\n" +
                                                    "Please try again!");
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.err.println("An unknown error/exception has occurred.");
            }
        }
        //Should only return -1 if the while loop is never entered.
        return -1;
    }

    //Prompt user for the check amount.
    public static double getCheckAmount()
    {
        boolean validInput = false, validPositive = false;
        double amount = -1.0;
        
        while(!validInput)
        {
            try
            {
                while(!validPositive)
                {
                    String amountStr = JOptionPane.showInputDialog ("Enter your check amount:");
                    if (amountStr.length() >= 0)
                    {
                        amount = Double.parseDouble(amountStr);
                    }
                    if (amount < 0)
                    {
                        JOptionPane.showMessageDialog(null, "ERROR: The check amount must be a positive value." + "\n" +
                                                            "Please try again!");
                    }
                    else
                    {
                        validPositive = true;
                    }
                }
                validInput = true;
                return amount;
            }
            //User existed or pressed cancel; user will be taken back to JFrameCustom frame.
            catch(NullPointerException nullEx)
            {
                amount = -1;
                validInput = true;   //Set to true to exit loop
            }
            catch(NumberFormatException n)
            {
                JOptionPane.showMessageDialog(null, "ERROR: The check amount must be entered in digits." + "\n" +
                                                    "Please try again!");
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.err.println("An unknown error/exception has occurred.");
            }
        }
        return amount;
    }
    
    //Prompt user for deposit amount via cash and checks.
    //Will need use of JTextField in order to prompt for two input options.
    //Place in an array of objects, and place in a confirm dialog box.
    //Make sure to change confirm options to OK and CANCEL.
    //If string length is less than or equal to zero, automatically set values to 0.
    public static double getDepositAmount()
    {
        String cashAmount, checkAmount;
        boolean validInput = false, validPositive = false;
        int test;    //Check for users choice (yes or cancel).
          
        JTextField cashEntered = new JTextField();
        JTextField checkEntered = new JTextField();
        Object[] depositInput = {"Cash", cashEntered, "Checks", checkEntered};
        
        while(!validInput)
        {
            try
            {
                while(!validPositive)
                {
                    test = JOptionPane.showConfirmDialog(deposits, depositInput, null, JOptionPane.OK_CANCEL_OPTION);
                    cashAmount = cashEntered.getText();
                    checkAmount = checkEntered.getText();
                    
                    if (test != JOptionPane.CANCEL_OPTION)
                    {                         
                        if (cashAmount.length() <= 0 && checkAmount.length() <= 0)
                        {
                            if(test == JOptionPane.YES_OPTION)
                            {
                                JOptionPane.showMessageDialog(null, "ERROR: No deposit amounts entered." + "\n" +
                                                                    "Please try again!");
                            }
                            else
                            {
                                return -1;
                            }
                        }
                        else
                        {
                            if (cashAmount.length() <= 0)
                                cashAmt = 0.0;
                            else
                                cashAmt = Double.parseDouble(cashAmount);
                            
                            if (checkAmount.length() <= 0)
                                checkAmt = 0.0;
                            else
                                checkAmt = Double.parseDouble(checkAmount);
                                                
                            if(cashAmt < 0 || checkAmt < 0)
                            {
                                JOptionPane.showMessageDialog(null, "ERROR: The deposit amounts must be positive values." + "\n" +
                                                                    "Please try again!");
                            }
                            else
                            {
                                validPositive = true;
                            }
                        }
                    }
                    else
                    {
                        return -1.0;
                    }
                }
                validInput = true;
            }
            catch(NumberFormatException n)
            {
                JOptionPane.showMessageDialog(null, "ERROR: The deposit amounts must be entered in digits." + "\n" +
                                                    "Please try again!");
            }
            catch (Exception e)
            {
                e.printStackTrace(System.err);
                System.err.println("An unknown error/exception has occurred.");
            }
        }       
        return cashAmt + checkAmt;
    }
    
    //Process check, and display an account update.
    public static void processCheck(CheckingAccount account, double amount)
    {
        message = account.getName() + "'s Account" + "\n" +
                  "Transaction: Check #" + checkNumber + " in Amount of " + fmt.format(amount) + "\n" +
                  "Current Balance: " + fmt.format(account.getBalance()) + "\n" +
                  "Service Charge: Check --- charge $0.15" + "\n";
               
        if(account.getBalance() < 500 && check == false)
        {
            account.setServiceCharge(5);
            trans = new ServiceCharge(account.getTransCount(), 3, 5);
            account.addTrans(trans);
            check = true; //Making sure to only change $5 fee on first offense.
            message = message + "Service Charge: Below $500 -- charge $5.00" + "\n";
        }
        if(account.getBalance() < 50)
        {
            message = message + "Warning: Balance below $50" + "\n";
        }
        if(account.getBalance() < 0)
        {
            account.setServiceCharge(10);
            trans = new ServiceCharge(account.getTransCount(), 3, 10);
            account.addTrans(trans);
            message = message + "Service Charge: Below $0 -- charge $10.00" + "\n";
        }
        
        message = message + "Total Service Charge: " + fmt.format(account.getSerivceCharge()) + "\n";
        JOptionPane.showMessageDialog (null, message);
    }
    
    //Process deposit, and display an account update.
    public static void processDeposit(CheckingAccount account, double amount)
    {
        message = account.getName() + "'s Account" + "\n" +
                  "Transaction: Deposit in Amount of " + fmt.format(amount) + "\n" +
                  "Current Balance: " + fmt.format(account.getBalance()) + "\n" +
                  "Service Charge: Check --- charge $0.10" + "\n" +
                  "Total Service Charge: " + fmt.format(account.getSerivceCharge()) + "\n";
        JOptionPane.showMessageDialog (null, message);
    }
    
    //Display an organized list of all transactions, including service charges.
    public static void displayAllTrans()
    {
        String  type, tab;
        int id;
        double amount;
                
        if(!selectAccount)
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        else
        {
            message = "List All Transactions" + "\n" +
                      "Name: " + account.getName() + "\n" +
                      "Balance: " + fmt.format(account.getBalance()) + "\n" +
                      "Total Service Charge: " + fmt.format(account.getSerivceCharge()) + "\n \n" +
                      "ID       Type               Amount \n";
                    
            for(int i = 0; i < account.getTransCount(); i++)
            {
                trans = account.getTrans(i);
                id = trans.getTransNumber();
                amount = trans.getTransAmount();
                if(trans.getTransId() == 1)
                {
                    type = "Check       ";
                }
                else if(trans.getTransId() == 2)
                {
                    type = "Deposit     ";
                }
                else
                {
                    type = "Svc. Chg.   ";
                }
            
                if(id > 99)
                {
                    tab = "     ";
                }
                else if(id > 9)
                {
                    tab = "       ";
                }
                else
                {
                    tab = "        ";
                }
                message += id + tab + type + "       " + fmt.format(amount) + "\n";
            }
            textArea.setText(message);
        }
    }
    
    //Display a list of all checks.
    public static void displayChecks()
    {
        String tab, tab2;
        
        if(!selectAccount)
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        else
        {
            message = "List All Checks" + "\n" +
                      "Name: " + account.getName() + "\n" +
                      "Balance: " + fmt.format(account.getBalance()) + "\n" +
                      "Total Service Charge: " + fmt.format(account.getSerivceCharge()) + "\n \n" +
                      "ID" + "        " + "Check         Amount" + "\n";
                    
            for(int i = 0; i < account.getTransCount(); i++)
            {
                trans = account.getTrans(i);
            
                if(trans.getTransNumber() > 99)
                {
                    tab = "       ";
                }
                else if(trans.getTransNumber() > 9)
                {
                    tab = "        ";
                }
                else
                {
                    tab = "         ";
                }
            
                if(trans.getCheckNumber() > 99999999)
                {
                    tab2 = "     ";
                }
                else if(trans.getCheckNumber() > 9999999)
                {
                    tab2 = "      ";
                }
                else if(trans.getCheckNumber() > 999999)
                {
                    tab2 = "       ";
                }
                else if(trans.getCheckNumber() > 99999)
                {
                    tab2 = "        ";
                }
                else if(trans.getCheckNumber() > 9999)
                {
                    tab2 = "         ";
                }
                else if(trans.getCheckNumber() > 999)
                {
                    tab2 = "          ";
                }
                else if(trans.getCheckNumber() > 99)
                {
                    tab2 = "           ";
                }
                else if(trans.getCheckNumber() > 9)
                {
                    tab2 = "            ";
                }
                else
                {
                    tab2 = "             ";
                }
            
                if(trans.getTransId() == 1)
                {
                    message = message + trans.getTransNumber() + tab +
                              trans.getCheckNumber() + tab2 +
                              fmt.format(trans.getTransAmount()) + "\n";
                }
            }
            textArea.setText(message);
        }
    }
    
    //Display a list of all deposits.
    public static void displayDeposits()
    {
        String tab, tab2, tab3;
        
        if(!selectAccount)
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        else
        {
            message = "List All Deposits" + "\n" +
                      "Name: " + account.getName() + "\n" +
                      "Balance: " + fmt.format(account.getBalance()) + "\n" +
                      "Total Service Charge: " + fmt.format(account.getSerivceCharge()) + "\n \n" +
                      "ID" + "        " + "Cash" + "              " + "Check" +
                      "             " + "Amount" + "          \n";
                    
            for(int i = 0; i < account.getTransCount(); i++)
            {
                trans = account.getTrans(i);
            
                                if(trans.getTransNumber() > 99)
                {
                    tab = "       ";
                }
                else if(trans.getTransNumber() > 9)
                {
                    tab = "        ";
                }
                else
                {
                    tab = "         ";
                }
            
                if(trans.getCashAmount() > 99999999)
                {
                    tab2 = "     ";
                }
                else if(trans.getCashAmount() > 9999999)
                {
                    tab2 = "      ";
                }
                else if(trans.getCashAmount() > 999999)
                {
                    tab2 = "       ";
                }
                else if(trans.getCashAmount() > 99999)
                {
                    tab2 = "        ";
                }
                else if(trans.getCashAmount() > 9999)
                {
                    tab2 = "         ";
                }
                else if(trans.getCashAmount() > 999)
                {
                    tab2 = "          ";
                }
                else if(trans.getCashAmount() > 99)
                {
                    tab2 = "           ";
                }
                else if(trans.getCashAmount() > 9)
                {
                    tab2 = "            ";
                }
                else
                {
                    tab2 = "             ";
                }     
                
                if(trans.getCheckAmount() > 99999999)
                {
                    tab3 = "     ";
                }
                else if(trans.getCheckAmount() > 9999999)
                {
                    tab3 = "      ";
                }
                else if(trans.getCheckAmount() > 999999)
                {
                    tab3 = "       ";
                }
                else if(trans.getCheckAmount() > 99999)
                {
                    tab3 = "        ";
                }
                else if(trans.getCheckAmount() > 9999)
                {
                    tab3 = "         ";
                }
                else if(trans.getCheckAmount() > 999)
                {
                    tab3 = "          ";
                }
                else if(trans.getCheckAmount() > 99)
                {
                    tab3 = "           ";
                }
                else if(trans.getCheckAmount() > 9)
                {
                    tab3 = "            ";
                }
                else
                {
                    tab3 = "             ";
                }  
            
                if(trans.getTransId() == 2)
                {
                    message = message + trans.getTransNumber() + tab + fmt.format(trans.getCashAmount()) +
                              tab2 + fmt.format(trans.getCheckAmount()) +
                              tab3 + fmt.format(trans.getTransAmount()) + "\n";
                }
            }
            textArea.setText(message);
        }
    }
    
    //Display a list of all service charges.
    public static void displayServiceCharges()
    {
        String tab;
               
        if(!selectAccount)
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        else
        {
            message = "List All Service Charges" + "\n" +
                      "Name: " + account.getName() + "\n" +
                      "Balance: " + fmt.format(account.getBalance()) + "\n" +
                      "Total Service Charge: " + fmt.format(account.getSerivceCharge()) + "\n \n" +
                      "ID" + "        " + "Amount" + "\n";
                    
            for(int i = 0; i < account.getTransCount(); i++)
            {
                trans = account.getTrans(i);
            
                if(trans.getTransNumber() > 99)
                {
                    tab = "       ";
                }
                else if(trans.getTransNumber() > 9)
                {
                    tab = "        ";
                }
                else
                {
                    tab = "         ";
                }
            
                if(trans.getTransId() == 3)
                {
                    message = message + trans.getTransNumber() + tab +
                              fmt.format(trans.getTransAmount()) + "\n";
                }
            }
            textArea.setText(message);
        }
    }
       
    //Opens a selected file, and places into an input object stream.
    //Object is converted to a vector and saved as the accountList.
    //First element in the vector is saved as the account.
    public static void openFile()
    {
        int confirm;  
        boolean valid = false;
        
        if(!saved)
        {
            String  message = "The data in the application is not saved.\n"+
                              "Would you like to save it before opening a new file?";
            confirm = JOptionPane.showConfirmDialog (null, message);
            if(confirm == JOptionPane.YES_OPTION)
            {
                selectFile(2);
                selectFile(1);
            }
            if(confirm == JOptionPane.NO_OPTION)
            {
                selectFile(1);
            }
        }
        else
        {
            selectFile(1);
        }
        	
        while(!valid)
        {
            try
            {
                FileInputStream inFS = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(inFS);
                accountList = (Vector<CheckingAccount>)in.readObject();        
                in.close();
                account = accountList.elementAt(0);
                selectAccount = true;
                saved = true;
//                account.setTransCount();
                valid = true;
            }	
            //ClassNotFoundException needed to use readObject() above.
            catch(ClassNotFoundException noClass)
            {
                noClass.printStackTrace(System.err);
                System.err.println("Required CheckingAccount Class not found.");
            }
            //Returns program to JFrameCustom frame, if no file is selected.
            catch(IOException ioEx)
            {
                return;
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.err.println("An unknown error/exception has occurred.");
            }
        }
    }
    
    //Saves information to a file in the form of a serialized vector.
    public static void saveFile()
    {
        selectFile(2);
      	try
	{
            FileOutputStream outFS = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(outFS);
            out.writeObject(accountList);
            out.close();
            saved = true;
        }	
	//Returns program to JFrameCustom frame, if no file is selected.
        catch(IOException ioError)
        {
            JOptionPane.showMessageDialog(null, "File not saved!");
            return;
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            System.err.println("An unknown error/exception has occurred.");
        }
    }
    
    //Opens either a save or open file dialog, depending on the option passed.
    public static void selectFile(int option) 
    {  
        int returnVal, confirm;       
                
        if(option == 2)
        {
            String message = "Would you like to use the current default file: \n" + filename;
            confirm = JOptionPane.showConfirmDialog (null, message);
       
            if(confirm == JOptionPane.YES_OPTION)
            {
                 return;
            }
        }
            
        JFileChooser io = new JFileChooser();
        if(option == 1)
        {
            returnVal = io.showOpenDialog (null);
        }
        else
        {
            returnVal = io.showSaveDialog (null);
        }
        
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = io.getSelectedFile();
            filename = file.getPath();
        }
    }
    
    //Adds a new account to the accountList vector.
    //Prompts user for name and beginning balance.
    public static void newAccount()
    {
        boolean validName = false, validBalance = false, negativeBalance = true;
        String accountName = "temp";    //Initializing as temp in case loop below is never entered.
                                        //Loop should iterate at least once.
                
        while(!validName)
        {
            try
            {
                accountName = JOptionPane.showInputDialog("Enter the account name:");

                if(accountName.length() > 0)
                {
                    //This loop checks the characters entered to make sure that the name is a valid name.
                    for(int i = 0; i < accountName.length(); ++i)
                    {
                        if((accountName.charAt(i) < 'A' || accountName.charAt(i) > 'Z'))
                        {
                            if((accountName.charAt(i) < 'a' || accountName.charAt(i) > 'z'))
                            {
                                if(accountName.charAt(i) != ' ' && accountName.charAt(i) != '-')
                                {
                                    JOptionPane.showMessageDialog(null, "ERROR: The name entered is not valid. Please try again!" + "\n\n" +
                                                                        "Suggestions:" + "\n" +
                                                                        "- Use only letters (A-Z or a-z)" + "\n" +
                                                                        "- Spaces and hyphens (-) are okay.");
                                    validName = false;
                                    break;
                                }
                            }
                        }
                        validName = true;
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "ERROR: You must enter a name to continue." + "\n" +
                                                        "Please try again!");
                }               
            }
            //This exception is caught because the user hit cancel on the name; or closed the box.
            //Cancelling at this point will close the software.
            catch(NullPointerException nullEx)
            {
                System.exit(0);
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.err.println("An unknown error/exception has occurred.");
            }
        }
        
        while(!validBalance)
        {
            try
            {
                while(negativeBalance)
                {
                    String balanceStr = JOptionPane.showInputDialog("Enter your initial balance:");
                    startingBalance = Double.parseDouble(balanceStr);
                    if(startingBalance >= 0)
                    {
                        negativeBalance = false;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "ERROR: Initial balance cannot be a negative value." + "\n" +
                                                            "Please try again!");
                    }
                }
                validBalance = true;
            }
            catch(NumberFormatException n)
            {
                JOptionPane.showMessageDialog(null, "ERROR: Initial balance entered is not valid. Please try again!" + "\n\n" +
                                                    "Suggestions:" + "\n" +
                                                    "- Enter the balance in digits only (no words or currency symbols)." + "\n" +
                                                    "- Do not leave the box blank (if balance is zero, enter 0).");
            }
            //This exception is caught because the user hit cancel on the initial balance; or closed the box.
            //Cancelling at this point will close the software.
            catch(NullPointerException nullEx)
            {
                System.exit(0);
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.err.println("An unknown error/exception has occurred.");
            }
        }
                
        account = new CheckingAccount(accountName, startingBalance);
        accountList.addElement(account);
        selectAccount = true;
        textArea.setText("New account added for " + accountName);
        frame.setVisible(true);
    }
    
    //Searches accountList vector for an account name given by the user.
    public static void findAccount()
    {
        String name = JOptionPane.showInputDialog ("Enter the account name: ");
        boolean found = false;
        
        for(int i = 0; i < accountList.size(); i++)
	{
            CheckingAccount test = accountList.elementAt(i);
	
            if(name.equals(test.getName()))
		{
                    textArea.setText("Found account for " + name);
                    found = true;
                    account = accountList.elementAt(i);
		}
        }
        if(!found)
            textArea.setText("Account not found for " + name);
    }
    
    //Lists all accounts in the accountList.
    public static void listAccounts()
    {
        CheckingAccount hold;   
        String message;
     
        message = "List of All Accounts: \n \n";
        for (int i = 0; i < accountList.size();i++)
        {
            hold = accountList.elementAt(i);
            message += "Name: " + hold.getName() + "\n" +
                       "Balance: " + fmt.format(hold.getBalance()) + "\n" +
                       "Total Service Charge: " + fmt.format(hold.getSerivceCharge()) + "\n \n";
        }
        textArea.setText(message);
    }
}