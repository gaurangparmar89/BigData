package edu.uic.ids561;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver extends Configured implements Tool
{
	
	public int run(String[] args) throws Exception 
	{	
		String input1 = args[0] + "/input1.txt";
		String output1 = args[1] + "/output1.txt";
		String output2 = args[1] + "/output2.txt";
		
		Job job1 = new Job();
		job1.setJarByClass(Driver.class);
		job1.setOutputKeyClass(IntWritable.class);
		job1.setOutputValueClass(Text.class);
		job1.setMapperClass(Mapper1.class);
		job1.setReducerClass(Reducer1.class);
		FileInputFormat.setInputPaths(job1, new Path(input1));
		FileOutputFormat.setOutputPath(job1, new Path(output1));
		job1.waitForCompletion(true);
		
		Job job2 = new Job();
		job2.setJarByClass(Driver.class);
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		job2.setMapperClass(Mapper2.class);
		job2.setReducerClass(Reducer2.class);
		FileInputFormat.setInputPaths(job2, new Path(output1));
		FileOutputFormat.setOutputPath(job2, new Path(output2));
		job2.waitForCompletion(true);
		
		return 0;
	}
	
	public static void main(String[] args) throws Exception 
	{
		int res = ToolRunner.run(new Configuration(), new Driver(), args);
		System.exit(res);
	}

}
