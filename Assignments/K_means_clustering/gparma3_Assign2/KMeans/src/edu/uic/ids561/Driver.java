package edu.uic.ids561;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver extends Configured implements Tool
{	
	public static HashMap<String,String> centroid_list = new HashMap<String,String>();
	public static HashMap<String,String> new_centroid_list = new HashMap<String,String>();
	
	public int run(String[] args) throws Exception 
	{	
		String input = args[0] + "/Centroid.txt";
		String output = args[1] + "/Centroid_1.txt";
		
		Job job1 = new Job();
		job1.setJarByClass(Driver.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);
		job1.setMapperClass(Mapper1.class);
		FileInputFormat.setInputPaths(job1, new Path(input));
		FileOutputFormat.setOutputPath(job1, new Path(output));
		job1.waitForCompletion(true);
		
		int iteration = 0;
		boolean is_done = false;
		String input1 = args[0] + "/File_1.txt";
		String output1 = "";
		while(is_done == false)
		{
			if(iteration == 0)
			{
				output1 = args[1] + "/intermediate.txt";
			}
			else
			{
				output1 = args[1] + "/intermediate.txt" + iteration;
			}
			
			Job job2 = new Job();
			job2.setJarByClass(Driver.class);
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(Text.class);
			job2.setMapperClass(Mapper2.class);
			job2.setReducerClass(Reducer2.class);
			job2.setNumReduceTasks(3);
			job2.setPartitionerClass(Partitioner2.class);
			FileInputFormat.setInputPaths(job2, new Path(input1));
			FileOutputFormat.setOutputPath(job2, new Path(output1));
			job2.waitForCompletion(true);
			
			Collection<String> c_values = centroid_list.values(); 
			Collection<String> c_new_values = new_centroid_list.values();
			List<String> c_list = new ArrayList<String>(c_values);
			List<String> c_new_list = new ArrayList<String>(c_new_values);
			
			Collections.sort(c_list);
			Collections.sort(c_new_list);
			
			
			boolean[] result = new boolean[c_list.size()];
			
			for(int i=0; i< c_list.size(); i++)
			{
				// System.out.println("c_list " + c_list.get(i));
				// System.out.println("c_new_list " + c_new_list.get(i));
				String c_attributes = c_list.get(i);
				String c_new_attributes = c_new_list.get(i);
				
				String[] c_points = c_attributes.split(",");
				String[] c_new_points = c_new_attributes.split(",");
				
				double tot_sum = 0;
				double final_value = 0;
				for(int j=0; j < c_points.length; j++)
				{
					double sum = Math.pow((Double.parseDouble(c_points[j]) 
						         - Double.parseDouble(c_new_points[j])),2);
					
					tot_sum = tot_sum + sum;
				}
				
				final_value = Math.sqrt(tot_sum);
				// System.out.println(final_value);
				if(final_value < 0.01)
					result[i] = true;
				else 
					result[i] = false;
			}
			
			boolean contains_false = false;
			for(int k = 0; k < result.length; k++)
			{	
				// System.out.println(result[k]);
				if (result[k] == false)
				{
					centroid_list.putAll(new_centroid_list);;
					contains_false = true;
					break;
				}
			}
			
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
