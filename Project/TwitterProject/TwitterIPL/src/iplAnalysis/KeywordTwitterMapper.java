package iplAnalysis;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class KeywordTwitterMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
	private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String words[] = value.toString().split(" ");
		
		for(int i=0; i<words.length; i++)
		{
			words[i] = words[i].replaceAll("[^ a-zA-Z]+","");
			words[i] = words[i].toLowerCase();
			words[i].trim();
			if(words[i].length() > 0)
			{
				word.set(words[i]);
				context.write(word, one);
			}
		}
	}
}