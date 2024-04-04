//******************************************************************************
//  Account.java                               Author: Tyler Davies
//  New Account Class (parent class for CheckingAccount).
//  Class now implements Serializable in order for data to be properly saved.
//******************************************************************************

package checking.account;

import java.io.Serializable;

public class Account implements Serializable
{
    protected String name;  // The person who owns the account
    protected double balance;   // do not define this in CheckingAccount class
    
    public Account(String acctName, double initBalance)
    {
        balance = initBalance;
        name = acctName;
    }
 
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
 
    public double getBalance()
    {
        return balance;
    }
 
    public void setBalance(double balance)
    {
        this.balance = balance;
    }
}