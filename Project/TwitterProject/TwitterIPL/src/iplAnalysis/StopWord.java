package iplAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StopWord 
{
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> stopwords = new ArrayList();
		BufferedReader br1 = new BufferedReader(new FileReader("/home/gaurang/Personal/Big_Data/Twitter/output/stopwords.txt"));
		String line1 = "";
		while((line1= br1.readLine())!=null)
			stopwords.add(line1);
		
		BufferedReader br = new BufferedReader(new FileReader("/home/gaurang/Personal/Big_Data/Twitter/output/Descorder_31.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("/home/gaurang/Personal/Big_Data/Twitter/output/KeyWord_extract_31.txt"));
		
		String line;
		while((line = br.readLine()) != null)
		{
			String words_values[] = line.split("\t");
			
			if(stopwords.contains(words_values[0]))
			{
				continue;
			}		
			else
			{
				bw.write(line + "\n");
			}
		}
		br.close();
		bw.close();
		System.out.println("Stopwords removed.");
		
	}
}