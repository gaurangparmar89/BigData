package edu.uic.ids561;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileGenerator
{
    public static void main(String[] args)
    {
        File file = new File("/home/gaurang/Personal/Big_Data/PageRank/input/input.txt");
        int maxnodes = 100;
        int no_of_outlinks = 4;
        int max_value = 100;
        double initial_rank = 0.5;
        StringBuffer st = new StringBuffer();
           
        for(int i=1;i<=maxnodes;i++)
        {
            st.append("Page" + i + "," + initial_rank + "\t");
               
            long randomnumber1;
            Random random1 = new Random();
            randomnumber1 = random1.nextInt(no_of_outlinks);
            if(randomnumber1 == 0)
                randomnumber1 = 1;
           
            for(int j=1;j <= randomnumber1 ;j++)
            {
                long randomnumber;
                Random random = new Random();
                randomnumber = random.nextInt(max_value);
                if(randomnumber == 0)
                    randomnumber = 1;
                   
                if(j==1)
                    st.append("Page" + randomnumber);
                else
                    st.append("," + "Page" + randomnumber);
            }
            st.append("\n");
            }
       
        try
        {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            bf.write(st.toString());
            bf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}