//******************************************************************************
//  Deposit.java                                        Author: Tyler Davies
//  New Deposit Class (subclass of Transaction).
//  Used to store cash and check amounts per deposit transaction.
//******************************************************************************

package checking.account;

public class Deposit extends Transaction
{
    //Declare variables for Cash and Check Amounts
    private double cashAmount, checkAmount;
 
    //Initialize Deposit Object(Constructor).
    public Deposit(int transCount, int transId, double transAmt, double cashDeposit, double checkDeposit)
    {
        super(transCount, transId, transAmt);
        this.cashAmount = cashDeposit;
        this.checkAmount = checkDeposit;
    }
    
    //Returns amount of cash per deposit.
    @Override
    public double getCashAmount()
    {
        return cashAmount;
    }
    
    //Returns amount for checks per deposit.
    @Override
    public double getCheckAmount()
    {
        return checkAmount;
    }
    
    //Must have this method for the polymorphic relationship to work.
    //Could also have been set to a negative number, but 0 is more appropriate.
    @Override
    public int getCheckNumber()
    {
        return 0;
    }
}
