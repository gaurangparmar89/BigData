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
		
		String[] input = line.split("\t");
		String point = input[0];
		String attributes = input[1];
		
		Driver.centroid_list.put(point, attributes);
		context.write(new Text(point), new Text(attributes));
	}
}