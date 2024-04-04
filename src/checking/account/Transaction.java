//******************************************************************************
//  Transaction.java                           Author: Tyler Davies
//  New Transaction Class.
//  Class now implements Serializable in order for data to be properly saved.
//******************************************************************************

package checking.account;

import java.io.Serializable;

abstract public class Transaction implements Serializable
{
    //Declare variables for transaction number, ID, and amount.
    private int transNumber;
    private int transId;
    private double transAmt;
    
    //Initialize Transaction Object(Constructor).
    //Since Transaction is now abstract, it cannot be instantiated.
    //Must use child/subclasses (Check, Deposit, or ServiceCharge).
    public Transaction(int number, int id, double amount)
    {
        transNumber = number;
        transId = id;
        transAmt = amount;
    }
    
    //Returns the transaction number; the index in the array.
    public int getTransNumber()
    {
        return transNumber;
    }
    
    //Returns the transaction ID; indicates if the transaction is a Check, Deposit, or Service Charge.
    public int getTransId()
    {
        return transId;
    }
    
    //Returns the amount of the transaction.
    public double getTransAmount()
    {
        return transAmt;
    }
    
    //Abstract class to allow access to method in Check class.
    public abstract int getCheckNumber();
    
    //Abstract class to allow access to methods in Deposit class.
    public abstract double getCashAmount();
    public abstract double getCheckAmount();
}
