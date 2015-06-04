package edu.uic.ids561;

//import statements
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mapper2 extends Mapper<LongWritable, Text, Text, Text>
{
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String line = value.toString();
		
		String[] input = line.split("\t");
		String point = input[0];
		String attributes = input[1];
		String[] d_attributes = attributes.split(",");
		
		double temp_value = Integer.MAX_VALUE;
		String temp_centroid = "";
		Set centroid = Driver.centroid_list.entrySet();
		Iterator it = centroid.iterator();
		while(it.hasNext())
		{
			Map.Entry<String, String> list = (Map.Entry<String, String>)it.next();
			String centroid_values = list.getValue();
			
			String[] c_attributes = centroid_values.split(",");
			
			double tot_sum = 0;
			double final_value = 0;
			for(int j=0; j<c_attributes.length; j++)
			{
				double sum = Math.pow((Double.parseDouble(c_attributes[j]) 
						    - Double.parseDouble(d_attributes[j])),2);
				
				tot_sum = tot_sum + sum;
			}
			final_value = Math.sqrt(tot_sum);
			if(temp_value > final_value)
			{
				temp_value = final_value;
				temp_centroid = list.getKey();
			}
		}
		
		context.write(new Text(temp_centroid), new Text(attributes));
	}
}