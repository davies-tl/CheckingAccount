//******************************************************************************
//  Check.java                                 Author: Tyler Davies
//  New Check Class (child/subclass to Transaction).
//******************************************************************************

package checking.account;

public class Check extends Transaction
{
    //Declare a variable for the check number of each check transaction.
    private int checkNumber;
 
    //Initialize Check Object(Constructor).
    public Check(int tCount, int tId, double tAmt, int checkNumber)
    {
        super(tCount, tId, tAmt);
        this.checkNumber = checkNumber;
    }
 
    //Returns the check number.
    public int getCheckNumber()
    {
        return checkNumber;
    }
 
    //Can be used to set the check number.
    //Not currently used; check number is set during initialization.
    public void setCheckNumber(int checkNumber)
    {
        this.checkNumber = checkNumber;
    }
    
    //Must have these two methods for the polymorphic relationship to work.
    //Could also have been set to a negative number, but 0 is more appropriate.
    public double getCashAmount()
    {
        return 0.0;
    }
    public double getCheckAmount()
    {
        return 0.0;
    }
}