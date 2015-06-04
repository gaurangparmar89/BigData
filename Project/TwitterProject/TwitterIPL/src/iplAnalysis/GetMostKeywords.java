package iplAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GetMostKeywords 
{

	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		HashMap<String, Integer> map1 = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new FileReader("/home/gaurang/Personal/Big_Data/Twitter/output/Twitter_extract_31.txt/part-r-00000"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("/home/gaurang/Personal/Big_Data/Twitter/output/Descorder_31.txt"));
				
		String line;
		while((line = br.readLine()) != null)
		{
			String values[] = line.split("\t");
			map1.put(values[0], Integer.valueOf(values[1]));		
		}
		br.close();
		Set<Entry<String, Integer>> set = map1.entrySet();
	    List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
	    Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
	    {
	    	public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
	        {
	    		return (o2.getValue()).compareTo( o1.getValue() );
	        }
	    } );
	    
	    for(Map.Entry<String, Integer> entry:list)
	    {
	    	bw.write(entry.getKey() + "\t" + entry.getValue()+ "\n");
	    	// System.out.println(entry.getKey()+" ==== "+entry.getValue());
	    }
		System.out.println("Success");
		bw.close();
	}

}