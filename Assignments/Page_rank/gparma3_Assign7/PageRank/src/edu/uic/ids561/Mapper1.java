package edu.uic.ids561;

//import statements
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mapper1 extends Mapper<LongWritable, Text, Text, Text>
{
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String line = value.toString();
		
		// Split pageinfo and outlinks 
		String[] input = line.split("\t");
		String pageinfo = input[0];
		String outlinks = input[1];
		
		// Split page and rank 
		String[] pageinfo1 = pageinfo.split(",");
		String page = pageinfo1[0];
		String rank = pageinfo1[1];
		
		// Generate outlink array 
		String[] outlinks1 = outlinks.split(",");
		
		for (int i = 0; i < outlinks1.length; i++) 
		{
			
			String keytemp = outlinks1[i];			
			double valuetemp = (Double.parseDouble(rank)/outlinks1.length);
			
			context.write(new Text(keytemp), new Text(String.valueOf(valuetemp)));
		}
		
		// Add current page , rank to hashmap
		Driver.rank_old.put(page, Double.parseDouble(rank));
		context.write(new Text(page), new Text(outlinks));
		
	}
}