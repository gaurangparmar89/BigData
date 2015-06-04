package edu.uic.ids561;

//import statements
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import edu.uic.ids561.Driver.NoOfEdges;
import edu.uic.ids561.Driver.NoOfNodes;

public class Mapper2 extends Mapper<LongWritable, Text, Text, Text>
{
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		
		Node node2 = new Node(value.toString());
		StringBuffer s1 = new StringBuffer();
		for(String neighbor2 : node2.getEdges())
		{
			s1.append(neighbor2).append(",");
			context.getCounter(NoOfEdges.noOfEdges).increment(1);
		}
		context.getCounter(NoOfNodes.noOfNodes).increment(1);
		context.write(new Text(node2.getId()), new Text(s1.toString()));
	}
}