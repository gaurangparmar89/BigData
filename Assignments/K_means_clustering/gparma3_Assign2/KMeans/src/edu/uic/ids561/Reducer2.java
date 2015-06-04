package edu.uic.ids561;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.ToolRunner;


public class Reducer2 extends Reducer<Text, Text, Text, Text> 
{
	
	public void reduce(Text key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException
	{
		int no_of_elem = 0;
		ArrayList<Double> list = new ArrayList<Double>();
		
		while(values.iterator().hasNext())
		{	
			String attributes = values.iterator().next().toString();
			String[] attributes_list = attributes.split(",");
			if(no_of_elem == 0)
			{
				for(int j=0; j<attributes_list.length; j++)
				{
					list.add(j, Double.parseDouble(attributes_list[j]));
				}
			}
			else
			{
				for(int j=0; j<attributes_list.length; j++)
				{
					Double value = list.get(j);
					Double sum = value + Double.parseDouble(attributes_list[j]);
					list.set(j, sum);
				}
			}
			
			no_of_elem++;
			
		}

		StringBuffer st = new StringBuffer();
		for(int k = 0; k < list.size(); k++)
		{
			if(k == 0)
				st.append(String.format("%.2f", (list.get(k) / no_of_elem)));
			else 
				st.append("," + String.format("%.2f", (list.get(k) / no_of_elem)));
		}
		
		Driver.new_centroid_list.put(key.toString(), st.toString());
		context.write(key, new Text(st.toString()));
	
	}

	static int printUsage() 
	{
		System.out.println("wordcount [-m <maps>] [-r <reduces>] <input> <output>");
		ToolRunner.printGenericCommandUsage(System.out);
		return -1;
	}
}