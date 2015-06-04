package edu.uic.ids561;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.ToolRunner;

import edu.uic.ids561.Driver.MoreIterations;


public class Reducer1 extends Reducer<Text, Text, Text, Text> 
{
	public void reduce(Text key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException
	{
		Node out = new Node();
		out.setId(key.toString());
		
		while(values.iterator().hasNext())
		{
			Text value = values.iterator().next();
			Node in = new Node(key.toString() + "\t" + value.toString());
			
			if(in.getEdges().size() > 0)
			{
				out.setEdges(in.getEdges()); 
			}
			
			if(in.getDist() < out.getDist())
			{
				out.setDist(in.getDist());
				out.setParent(in.getParent());
			}
			
			if(in.getColor().ordinal() > out.getColor().ordinal())
			{
				out.setColor(in.getColor());
			}
		}
		
		context.write(key, new Text(out.getNodeInfo()));
		
		if(out.getColor() == Node.Color.GRAY)
		{	
			context.getCounter(MoreIterations.numberOfIterations).increment(1);
		}
	}


	static int printUsage() 
	{
		System.out.println("wordcount [-m <maps>] [-r <reduces>] <input> <output>");
		ToolRunner.printGenericCommandUsage(System.out);
		return -1;
	}
}