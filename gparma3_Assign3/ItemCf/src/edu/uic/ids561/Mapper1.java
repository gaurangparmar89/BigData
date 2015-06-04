package edu.uic.ids561;

//import statements
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mapper1 extends Mapper<LongWritable, Text, IntWritable, Text>
{
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String line = value.toString();
		
		String[] input = line.split("\t");
		int user_id = Integer.parseInt(input[0]);  
		String movie_det = input[1] + "," + input[2];
		
		context.write(new IntWritable(user_id), new Text(movie_det));
	}
}