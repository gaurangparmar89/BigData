package iplAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Analysis
{
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> keywords = new ArrayList();
		BufferedReader br1 = new BufferedReader(new FileReader("/home/gaurang/Personal/Big_Data/Twitter/keywords.txt"));
		String line1 = "";
		while((line1= br1.readLine())!=null)
			keywords.add(line1);
		
		BufferedReader br = new BufferedReader(new FileReader("/home/gaurang/Personal/Big_Data/Twitter/Match_outputs/IPL-fullweek/output/KeyWord_extract_31.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("/home/gaurang/Personal/Big_Data/Twitter/Match_outputs/IPL-fullweek/output/Final_count_31_SRH.txt"));
		
		String line;
		int sum = 0;
		bw.write("Words are:" + "\n");
		while((line = br.readLine()) != null)
		{
			String words_values[] = line.split("\t");
			
			for(int i=0; i< keywords.size(); i++)
			{
				if(words_values[0].equals(keywords.get(i)))
				{
					bw.write(words_values[0] + "\n");
					sum = sum + Integer.parseInt(words_values[1]);
				}
			}
		}
		
		bw.write("\n");
		bw.write("Final count is " + sum);
		br.close();
		bw.close();
		System.out.println("Final Count is: " + sum);
		
	}
}