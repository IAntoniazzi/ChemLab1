package Labs;

import blackboard.data.course.CourseMembership;
import blackboard.platform.context.Context;

public class inputChecks {
    
    protected int dataX;
    protected int dataY;
    
    protected String data[][];
    protected String type[][];
    protected String error[][];    
    protected String key[][];
    protected double weight[][];
    protected double grade[][];
    
    DataLoader load;
    DataPersister save;

    public inputChecks(String labname)
    {
        dataX = 0;
        dataY = 0;
        load = new DataLoader(labname);
        save = new DataPersister(labname);
        
    }
    
    public inputChecks(int X, int Y, String labname)
    {
        dataX = X;
        dataY = Y;
        load = new DataLoader(labname);
        save = new DataPersister(labname);
        
        data = new String[dataX][dataY];
        type = new String[dataX][dataY];
        error = new String[dataX][dataY];
        key = new String[dataX][dataY];
        weight = new double[dataX][dataY];
        grade = new double[dataX][dataY];

        
        String tempData;
        tempData = load.loadData(labname);
        initData(tempData);
    }

    public inputChecks(int X, int Y, String labname, Context ctx)
    {
        dataX = X;
        dataY = Y;
        load = new DataLoader(labname);
        save = new DataPersister(ctx, labname);
        
        data = new String[dataX][dataY];
        type = new String[dataX][dataY];
        error = new String[dataX][dataY];
        key = new String[dataX][dataY];
        weight = new double[dataX][dataY];
        grade = new double[dataX][dataY];

        
        String tempData;
        tempData = load.loadData(labname);
        initData(tempData);
    }
    
    private void initData(String tempData)
    {
        for (int i = 0; i < dataX; i++)
        {
            for (int j = 0; j < dataY; j++)
            {
                //get first data and remove
                String temp = "";
                while (tempData.length() > 0 && !tempData.substring(0,1).equals(","))
                {
                    temp += "" + tempData.substring(0,1);
                    tempData = tempData.substring(1);
                }
                if (tempData.length() > 0 && tempData.substring(0,1).equals(","))
                {
                    //remove the ,
                    tempData = tempData.substring(1);
                }
                if (temp.length() > 0)
                {
                    data[i][j] = temp;
                }
                else
                {
                    data[i][j] = "";
                }
                
                type[i][j] = "";
                error[i][j] = "";
                key[i][j] = "";
                weight[i][j] = 0.0;
                grade[i][j] = 0.0;
            }
        } 
    }
    
    public void setData(int x, int y, String info)
    {
        data[x][y] = info;
    }
    
    public String getData (int x, int y)
    {
        return data[x][y];
    }
    
    public void setType(int x, int y, String info)
    {
        type[x][y] = info;
    }
    
    public String getType (int x, int y)
    {
        return type[x][y];
    }
    
    public void setError(int x, int y, String info)
    {
        error[x][y] = info;
    }
    
    public String getError(int x, int y)
    {
        return error[x][y];
    }
    
    public void setKey(int x, int y, String info)
    {
        key[x][y] = info;
    }
    
    public String getKey(int x, int y)
    {
        return key[x][y];
    }
    
    public void save()
    {
        //build string of data to save
        String theString = "";
        
        for (int i = 0; i < dataX; i++)
        {
            for (int j = 0; j < dataY; j++)
            {
                theString += data[i][j];
                if (i + j < dataX + dataY - 2)
                {
                    theString += ",";
                }
            }
        }
         //call function
        save.saveData(theString);
    }
    
    public void check()
    {
        for (int i = 0; i < dataX; i ++)
        {
            for (int j = 0; j < dataY; j++)
            {
                if (data[i][j] != null && !data[i][j].equals(""))
                {
                    //clear previous errors
                    error[i][j] = "";

                    //check based on type
                    if (type[i][j].equals("unit"))
                    {
                        unitStandard(i,j);
                    }
                    if (type[i][j].equals("double"))
                    {
                        doubleStandard(i,j);
                    }
                }
                else
                {
                    error[i][j] = "empty";
                }           
            }
        }

        buildKey();
        gradeLab();
    }

    public void clear()
    {
        for (int i = 0; i < dataX; i ++)
        {
            for (int j = 0; j < dataY; j++)
            {
                if (data[i][j] != null && !data[i][j].equals(""))
                {
                    //clear data
                    data[i][j] = "";
                }

                if (error[i][j] != null && !error[i][j].equals(""))
                {
                    //clear previous errors
                    error[i][j] = "";
                }
                    
                if (key[i][j] != null && !key[i][j].equals(""))
                {
                    //remove any built key
                    key[i][j] = "";
                }
            }
        }
    }
    
    public void submit(Context ctx)
    {
        //call save and then save grade
        save();

        //build string of grade to save
        String theString = "";
        
        for (int i = 0; i < dataX; i++)
        {
            for (int j = 0; j < dataY; j++)
            {
                theString += grade[i][j];
                if (i + j < dataX + dataY - 2)
                {
                    theString += ",";
                }
            }
        }

        //call function
        save.saveGrade(theString);

        //set submitted
        save.submitted(ctx);
    }
    
    protected void unitStandard (int x, int y)
    {
        String temp;
        temp = data[x][y].toUpperCase();
        temp = temp.trim();
        
        if (temp.equals("G"))
        {
            temp = "g";
        }
        else if (temp.equals("MG"))
        {
            temp = "mg";
        }
        else if (temp.equals("KG"))
        {
            temp = "kg";
        }
        else if (temp.equals("L"))
        {
            temp = "L";
        }
        else if (temp.equals("ML"))
        {
            temp = "mL";
        }
        else if (temp.equals("CM"))
        {
            temp = "cm";
        }
        else if (temp.equals("M"))
        {
            temp = "m";
        }
        else if (temp.equals("KM"))
        {
            temp = "km";
        }
        else if (temp.equals("LB"))
        {
            temp = "lb";
        }
        else if (temp.equals("OZ"))
        {
            temp = "oz";
        }
        else if (temp.equals("CM"))
        {
            temp = "cm";
        }
        else if (temp.equals("S"))
        {
            temp = "s";
        }
        else if (temp.equals("IN"))
        {
            temp = "in";
        }
        else if (temp.equals("FT"))
        {
            temp = "ft";
        }
        else
        {
            temp = "";
        }
        
        if (temp.equals(""))
        {
            error[x][y] = "Invalid unit: \"" + data[x][y] + "\"";
            data[x][y] = "";
        }
        else
        {
            data[x][y] = temp;
        }
    }
    
    protected void doubleStandard (int x, int y)
    {
        String temp = data[x][y];
        boolean hasDot = false;
        
        //check for numbers and . only
        //check for multiple .
        
        for(int i = 0; i < temp.length(); i++)
        {
            if (temp.charAt(i) == '.')
            {
                if (hasDot)
                {
                error[x][y] = "Invalid character: '.'";
                data[x][y] = "";
                return;
                }
                else
                {
                    hasDot = true;
                }
            }
            
            if (temp.charAt(i) != '.' && (int)temp.charAt(i) < 48 || (int)temp.charAt(i) > 57)
            {
                error[x][y] = "Invalid character: '" + temp.charAt(i) + "'";
                data[x][y] = "";
                return;
            }
        }
    }

    protected int getSigFigs (int x, int y)
    {
        int sigFigs = 0;
        String num = data[x][y];
        int length;
        
        if (num != null && num.length() != 0)
        {
            length = num.length();

            //remove leading zeros
            int i = 0;
            while (num.charAt(i) == '0')
            {
               i++;
            }
            //check to see if there is a .
            //inc sig figs
            boolean flag = false;
            while ( i < length)
            {
                if (num.charAt(i) == '.')
                {
                    flag = true;
                    i++;
                }
                else
                {
                    i++;
                    sigFigs++;
                }
            }
            //remove trailing zero's if not a decimal
            if (!flag)
            {
                i = length;
                while (i < length && num.charAt(i) == '0')
                {
                    sigFigs --;
                }
            }

            // for testing only! error[x][y] += "" + sigFigs;
            return sigFigs;
        }
        else
        {
            return (-1);
        }
    }
    
    protected void gradeLab()
    {
        //compare data to key
        for (int i = 0; i < dataX; i ++)
        {
            for (int j = 0; j < dataY; j++)
            {
                if (data[i][j] != null && key[i][j] != null)
                {   
                    if (data[i][j].equals(key[i][j]) || key[i][j].equals("*"))
                    {
                        grade[i][j] = weight[i][j];
                    }                
                }
            }        
        }
    }

    protected void buildKey()
    {
    }
}
    