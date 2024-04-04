//******************************************************************************
//  CheckingAccount.java                                Author: Tyler Davies
//  Creates a checking account java class.
//******************************************************************************

package checking.account;

import java.util.ArrayList;

public class CheckingAccount extends Account
{
    //Declare global variables.
    //Declare and ArrayList to hold a list of Transaction objects.
    //Declare a integer count of Transaction objects, which will be used as transaction ID.
    private double totalServiceCharge;
    private ArrayList<Transaction> transList = new ArrayList<Transaction>();
    private int transCount = 0;
    
    //Set initial account balance, and initialize service charge.
    public CheckingAccount(String accountName, double initialBalance)
    {
        super(accountName, initialBalance);
    }
    
    //Commented out the following code, because the method is already inherited from Account class.
    //Returns account balance.
    //public double getBalance()
    //{
    //    return balance;
    //}
    
    //Calculate new balance with Check or Deposit using the transaction code.
    public void setBalance(double transAmt, int tCode)
    {
        if (tCode == 1)
        {
            balance = balance - transAmt;
        }
        else //(tCode == 2)
        {
            balance = balance + transAmt;
        }
    }
    
    //Return total service charge.
    public double getSerivceCharge()
    {
        return totalServiceCharge;
    }
    
    //Add new service charge to existing service charge total.
    public void setServiceCharge(double currentServiceCharge)
    {
        totalServiceCharge = totalServiceCharge + currentServiceCharge;
    }
    
    //Add Transaction object to the transList.
    public void addTrans(Transaction newTrans)
    {
        transList.add(newTrans);
        transCount++;
    }
            
    //Return current value of transCount.
    public int getTransCount() 
    {
        return transCount;
    }
    
    //Return Transaction object located at position/index i in the transList.
    public Transaction getTrans(int i) 
    {
        return transList.get(i);
    }
    
    //Sets the transCount
    public void setTransCount()
    {
        transCount = transList.size();
    }
}