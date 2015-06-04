package edu.uic.ids561;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Sorting 
{

	public static void main(String[] args) 
	{
		Scanner cin = new Scanner(System.in);
		System.out.println("Enter the file path & name from MapReduce Program: ");
		String file_path = cin.nextLine();
		cin.close();
		File file = new File(file_path);
		// TODO Auto-generated method stub
		try 
		{
			HashMap<String,Double> movie_map = new HashMap<String,Double>();
			HashMap<String,Double> movie_map1 = new HashMap<String,Double>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line = null;
			while ((line = br.readLine()) != null) 
			{
				String[] movie_pair = line.split("\t");
				String key = movie_pair[0];
				Double value = Double.parseDouble(movie_pair[1]);
				movie_map.put(key, value);			
			}	 
			br.close();
			
			movie_map1 = sortMap(movie_map);
			
			int i = 0;
			for (Map.Entry<String, Double> entry : movie_map1.entrySet()) 
			{
				i++;
				System.out.println(i + " " + entry.getKey() + " " + entry.getValue());
				
				if(i==100)
					break;
			}
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static HashMap<String, Double> sortMap(HashMap<String, Double> map)
	{
		
		List<Map.Entry<String, Double>> list = 
				new LinkedList<Map.Entry<String, Double>>(map.entrySet());
	 
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() 
		{
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) 
			{
					return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		
		HashMap<String, Double> map1 = new LinkedHashMap<String, Double>();
		for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();) 
		{
			Map.Entry<String, Double> entry = it.next();
			map1.put(entry.getKey(), entry.getValue());
		}
				
		return map1;
		
	}

}
