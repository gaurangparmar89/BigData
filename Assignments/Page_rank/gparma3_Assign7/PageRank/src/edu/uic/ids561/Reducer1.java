package edu.uic.ids561;

import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.ToolRunner;


public class Reducer1 extends Reducer<Text, Text, Text, Text> 
{
	double beta = 0.8;	
	public void reduce(Text key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException
	{	
		double pageRank = 0.0;
		String outlinks = "";
		double no_of_nodes = 100;
		
		while(values.iterator().hasNext())
		{
			
			String tempvalue = values.iterator().next().toString();
			Scanner scanner = new Scanner(tempvalue);
			
			if(scanner.hasNextDouble())
			{
				pageRank= pageRank + (Double.parseDouble(tempvalue) * beta);
			}
			else
			{
				outlinks = tempvalue;
			}
			
		}
		
		pageRank = pageRank + ((1 - beta) / no_of_nodes);
		
		// Add new page , rank to hashmap
		Driver.rank_new.put(key.toString(),Math.round(pageRank*1000)/1000.00);
		context.write(new Text(key.toString()+","+Math.round(pageRank*1000)/1000.00), new Text(outlinks));
	
	}

	static int printUsage() 
	{
		System.out.println("wordcount [-m <maps>] [-r <reduces>] <input> <output>");
		ToolRunner.printGenericCommandUsage(System.out);
		return -1;
	}
}