package edu.uic.ids561;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class DocGenerator
{
	public static void main(String args[]) throws IOException
	{
		BufferedReader br1 = new BufferedReader(new FileReader("/home/gaurang/Personal/Big_Data/LDA/vocab.nips.txt"));
		int lineno = 1;
		String line1;
		HashMap<Integer,String> hm = new HashMap<Integer, String>();
		while((line1 = br1.readLine()) != null)
		{
			hm.put(lineno++, line1);
		}
		br1.close();
		
		BufferedReader br = new BufferedReader(new FileReader("/home/gaurang/Personal/Big_Data/LDA/docword.nips.txt"));
		
		String line;
		int currlineno = 1;
		int skiplines = 3;
		
		while((line=br.readLine())!=null)
		{
			if(currlineno++ <= skiplines)
				continue;
			String values[] = line.split("\\s+");
			
			FileWriter fw = new FileWriter("/home/gaurang/Personal/Big_Data/LDA/Doc_files/" + values[0] + ".txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0;i<Integer.parseInt(values[2]);i++)
			{
				bw.append(values[1] + " ");
				// bw.append(hm.get(Integer.parseInt(values[1])) + " ");
			}
			bw.close();
		}
		br.close();
		System.out.println("Success");
			
	}
}

