package iplAnalysis;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class KeywordTwitterDriver extends Configured implements Tool
{	
	public int run(String[] args) throws Exception
	{
		String input = args[0];
		String output = args[1];
		
		Job job1 = new Job();
		job1.setJarByClass(KeywordTwitterDriver.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(IntWritable.class);
		job1.setMapperClass(KeywordTwitterMapper.class);
		job1.setReducerClass(KeywordTwitterReducer.class);
		FileInputFormat.setInputPaths(job1, new Path(input));
		FileOutputFormat.setOutputPath(job1, new Path(output));
		job1.waitForCompletion(true);
		
		return 0;
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		int res = ToolRunner.run(new Configuration(), new KeywordTwitterDriver(), args);
		System.exit(res); 
	}
}