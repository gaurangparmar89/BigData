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
		Node node = new Node(value.toString());
		
		if(node.getColor() == Node.Color.GRAY)
		{
			for(String neighbor : node.getEdges())
			{
				Node adjacentNode = new Node();
				adjacentNode.setId(neighbor);
				adjacentNode.setDist(node.getDist() + 1);
				adjacentNode.setColor(Node.Color.GRAY);
				adjacentNode.setParent(node.getId());
				
				context.write(new Text(adjacentNode.getId()), new Text(adjacentNode.getNodeInfo()));
			}
			node.setColor(Node.Color.BLACK);
		}
	context.write(new Text(node.getId()), new Text(node.getNodeInfo()));
	}
}
