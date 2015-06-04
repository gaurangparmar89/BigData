package edu.uic.ids561;

//import statements
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mapper2 extends Mapper<LongWritable, Text, Text, Text>
{
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String line = value.toString();
		String[] user_list = line.split("\t");
		String user_ratings = user_list[1];
		// System.out.println(user_ratings);
		String[] input = user_ratings.split("\\s+");
		// System.out.println(input[0]);
		
		for(int i=0; i< input.length; i++)
		{
			if(input[i].length() == 0) 
				continue;
			for(int j=i+1; j <input.length; j++)
			{
				String[] pair1 = input[i].split(",");
				String [] pair2 = input[j].split(",");
				
				String movie_pair, rating_pair;
				
				if(Integer.parseInt(pair1[0]) <= Integer.parseInt(pair2[0]))
				{
					movie_pair = pair1[0] + "," + pair2[0];
					rating_pair = pair1[1] + "," + pair2[1];
				}
				else
				{
					movie_pair = pair2[0] + "," + pair1[0];
					rating_pair = pair2[1] + "," + pair1[1];
				}
				context.write(new Text(movie_pair), new Text(rating_pair));
			}
		}
	}
}