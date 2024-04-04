//******************************************************************************
//  ServiceCharge.java                                  Author: Tyler Davies
//  New ServiceCharge Class (subclass of Transaction).
//  Used to store service charges now that the Transaction class is abstract.
//  Abstract classes cannot be instantiated.
//******************************************************************************

package checking.account;

public class ServiceCharge extends Transaction
{
    //Initialize ServiceCharge Object(Constructor).
    public ServiceCharge(int tCount, int tId, double tAmt)
    {
        super(tCount, tId, tAmt);
    }

    //Must have these three methods for the polymorphic relationship to work.
    //Could also have been set to a negative number, but 0 is more appropriate.
    
    @Override
    public double getCashAmount()
    {
        return 0.0;
    }
    @Override
    public double getCheckAmount()
    {
        return 0.0;
    } 
    public int getCheckNumber()
    {
        return 0;
    }
}
