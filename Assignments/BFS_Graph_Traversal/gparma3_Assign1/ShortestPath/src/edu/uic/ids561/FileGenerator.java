package edu.uic.ids561;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileGenerator 
{
	private static int generateNumber(int node, int max)
	{	int randomnumber;
		Random random = new Random();
		randomnumber = random.nextInt(max);
		if(randomnumber == 0 || randomnumber == node)
		{
			return 1;
		}
		else
			return randomnumber;
	}

	public static void main(String[] args) 
	{
		File file = new File("/home/gaurang/Personal/Big_Data/BFS/input/input1.txt");
		int maxedge = 5;
		int maxnode = 100;
		StringBuffer st = new StringBuffer();
			
		for(int i=1;i<=100;i++)
		{
			st.append(i + "\t");
				
			for(int j=1;j <= generateNumber(0, maxedge);j++)
			{
				int edge = generateNumber(i, maxnode);
					
				if(j==1)
					st.append(edge);
				else
					st.append("," + edge);
			}
			st.append("|");
				
			if(i == 1)
				st.append("0" + "|" + "GRAY" + "|" + "source");
			else
				st.append("Integer.MAX_VALUE" + "|" + "WHITE" + "|" + "null");
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
