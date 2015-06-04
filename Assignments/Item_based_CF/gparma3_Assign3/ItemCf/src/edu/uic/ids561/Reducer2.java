package edu.uic.ids561;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.ToolRunner;


public class Reducer2 extends Reducer<Text, Text, Text, Text> 
{
	
	public void reduce(Text key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException
	{	
		double tot_sum = 0;
		double tot_x = 0;
		double tot_y = 0;
		int i = 0;
		while(values.iterator().hasNext())
		{	
			i++;
			String ratings = values.iterator().next().toString();
			String[] ratings_pair = ratings.split(",");
			double x = Double.parseDouble(ratings_pair[0]);
			double y = Double.parseDouble(ratings_pair[1]);
			double product = x*y;
			tot_sum = tot_sum + product;
			tot_x = tot_x + Math.pow(x, 2);
			tot_y = tot_y + Math.pow(y, 2);
		}
		
		if(i>1)
		{
			double similarity_score = tot_sum / (Math.sqrt(tot_x) * Math.sqrt(tot_y));
		
			context.write(key, new Text(Double.toString(similarity_score)));
		}
		else
			System.out.println(i);
	
	}

	static int printUsage() 
	{
		System.out.println("wordcount [-m <maps>] [-r <reduces>] <input> <output>");
		ToolRunner.printGenericCommandUsage(System.out);
		return -1;
	}
}