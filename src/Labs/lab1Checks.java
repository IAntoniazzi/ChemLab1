package Labs;

import blackboard.platform.context.Context;

public class lab1Checks extends inputChecks
{
    public lab1Checks(int x, int y, String labname)
    {
        super(x,y, labname);
    }
    public lab1Checks(int x, int y, String labname, Context ctx)
    {
        super(x,y, labname, ctx);
    }
    
    @Override
    protected void buildKey()
    {
        for (int i = 0; i < dataX; i ++)
        {
            for (int j = 0; j < dataY; j++)
            {
                if (j == 0 && data[i][j] != null)
                {
                    key[i][j] = "*";
                }
                if (j == 1 && data[i][j] != null)
                {
                    key[i][j] = "*";
                }
                if (j == 2 && data[i][j] != null)
                {
                    key[i][j] = "" + getSigFigs(i,0);
                }
            }
        }
    }
}
