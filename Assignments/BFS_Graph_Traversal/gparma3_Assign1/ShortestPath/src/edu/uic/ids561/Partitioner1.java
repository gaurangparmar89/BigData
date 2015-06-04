package edu.uic.ids561;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Partitioner1 extends Partitioner<Text, Text>
{
		public int getPartition(Text key, Text values, int numPartitions)
		{
			String id = key.toString();
			
			if(numPartitions==0)
				return 0;
			
			
			if(Integer.parseInt(id) < 30)
			{
				return 0;
			}
			else if((Integer.parseInt(id) >= 30) && (Integer.parseInt(id) <=60))
				return 1 % numPartitions;
			else
				return 2%numPartitions;
		}
}
