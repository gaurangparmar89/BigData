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
		File file = new File("/home/gaurang/Personal/Big_Data/KMeans/input/File_1.txt");
		int maxlines = 1000;
		int no_of_vars = 10;
		int max_value = 10;
		StringBuffer st = new StringBuffer();
			
		for(int i=1;i<=maxlines;i++)
		{
			st.append(i + "\t");
				
			for(int j=1;j <= no_of_vars ;j++)
			{
				long randomnumber;
				Random random = new Random();
				randomnumber = random.nextInt(max_value);
				if(randomnumber == 0)
					randomnumber = 1;
					
				if(j==1)
					st.append(randomnumber);
				else
					st.append("," + randomnumber);
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
