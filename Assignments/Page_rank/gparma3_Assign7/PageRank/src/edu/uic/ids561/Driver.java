package edu.uic.ids561;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver extends Configured implements Tool
{
	public static HashMap<String,Double> rank_old = new HashMap<String,Double>();
	public static HashMap<String,Double> rank_new = new HashMap<String,Double>();
	
	public int run(String[] args) throws Exception 
	{	
		int iteration = 0;
		boolean is_done = false;
		String input, output = "";
		
		while(is_done == false)
		{ 
			if(iteration == 0)
			{
				input = args[0] + "/input.txt";
			}
			else
			{
				input = args[1] + "/output" + (iteration - 1) + ".txt";
			}
			
			output = args[1] + "/output" + iteration + ".txt";
			
			Job job1 = new Job();
			job1.setJarByClass(Driver.class);
			job1.setOutputKeyClass(Text.class);
			job1.setOutputValueClass(Text.class);
			job1.setMapperClass(Mapper1.class);
			job1.setReducerClass(Reducer1.class);
			FileInputFormat.setInputPaths(job1, new Path(input));
			FileOutputFormat.setOutputPath(job1, new Path(output));
			job1.waitForCompletion(true);
			
			boolean[] result = new boolean[rank_old.size()];
			Set<Entry<String, Double>> set = rank_old.entrySet();
			Iterator it = set.iterator();
			
			int i = 0;
			// Compares values in both current,new hashmap and returns true or false
			while(it.hasNext())
			{
				@SuppressWarnings("unchecked")
				Map.Entry<String, Double> map = (Map.Entry<String, Double>)it.next();
				Double new_rank_value = rank_new.get(map.getKey());
				double diff = new_rank_value.doubleValue() - map.getValue().doubleValue();
				if(Math.abs(diff) < 0.5)
					result[i] = true;
				else 
					result[i] = false;
				
				i++;				
			}
			
			// Check if result contains atleast 1 false
			boolean contains_false = false;
			for(int k = 0; k < result.length; k++)
			{	
				// System.out.println(result[k]);
				if (result[k] == false)
				{
					contains_false = true;
					break;
				}
			}
			
			// If atleast 1 yes present, continue to next iteration by setting is_done = false
			if(contains_false == true)
				is_done = false;
			else
				is_done = true;
			
			iteration++;
			
		}
		
		return 0;
	}
	
	public static void main(String[] args) throws Exception 
	{
		int res = ToolRunner.run(new Configuration(), new Driver(), args);
		System.exit(res);
	}

}
