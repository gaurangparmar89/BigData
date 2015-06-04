package edu.uic.ids561;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Partitioner2 extends Partitioner<Text, Text>
{
		public int getPartition(Text key, Text values, int numPartitions)
		{
			String centroid_id = key.toString();
			
			if(numPartitions==0)
				return 0;
			
			
			if(Integer.parseInt(centroid_id) == 1)
			{
				return 0;
			}
			else if(Integer.parseInt(centroid_id) == 2) 
				return 1 % numPartitions;
			else
				return 2%numPartitions;
		}
}
