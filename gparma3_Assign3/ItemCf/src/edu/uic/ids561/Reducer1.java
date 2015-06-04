package edu.uic.ids561;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.ToolRunner;


public class Reducer1 extends Reducer<IntWritable, Text, IntWritable, Text> 
{
	
	public void reduce(IntWritable key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException
	{	
		String list = "";
		while(values.iterator().hasNext())
		{	
			String movie_det = values.iterator().next().toString();
			list = list + " " + movie_det;
		}
		context.write(key, new Text(list));
	
	}

	static int printUsage() 
	{
		System.out.println("wordcount [-m <maps>] [-r <reduces>] <input> <output>");
		ToolRunner.printGenericCommandUsage(System.out);
		return -1;
	}
}