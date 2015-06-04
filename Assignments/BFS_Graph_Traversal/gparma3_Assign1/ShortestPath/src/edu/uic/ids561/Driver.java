package edu.uic.ids561;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver extends Configured implements Tool
{
	static enum MoreIterations {numberOfIterations}
	static enum NoOfNodes {noOfNodes}
	static enum NoOfEdges {noOfEdges}
	
	public int run(String[] args) throws Exception 
	{	
		String input, input1, output, output1;
		
		input1 = args[0];
		output1 = args[1];
		
		Job job1 = new Job();
		job1.setJarByClass(Driver.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);
		job1.setMapperClass(Mapper2.class);
		FileInputFormat.setInputPaths(job1, new Path(input1));
		FileOutputFormat.setOutputPath(job1, new Path(output1));
		job1.waitForCompletion(true);
		
		Counters jobCntrs1 = job1.getCounters();
		long no_of_edges = (jobCntrs1.findCounter(NoOfEdges.noOfEdges).getValue()) / 2;
		long no_of_nodes = jobCntrs1.findCounter(NoOfNodes.noOfNodes).getValue();
		System.out.println("No of Nodes: " + no_of_nodes);
		System.out.println("No of Edges: " + no_of_edges);
		
		Job job2;
		int iterationCount = 0;
		long terminationValue = 1;
		
		while(terminationValue > 0)
		{	
			job2 = new Job();		
			job2.setJarByClass(Driver.class);
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(Text.class);
			job2.setMapperClass(Mapper1.class);
			job2.setReducerClass(Reducer1.class);
			job2.setNumReduceTasks(3);
			job2.setPartitionerClass(Partitioner1.class);

			
			
			if(iterationCount == 0)
				input = args[0];
			else
				input = args[1] + iterationCount;
			
			output = args[1] + (iterationCount + 1);			

			FileInputFormat.setInputPaths(job2, new Path(input));
			FileOutputFormat.setOutputPath(job2, new Path(output));
			
			job2.waitForCompletion(true);
			
			Counters jobCntrs = job2.getCounters();
			terminationValue = jobCntrs.findCounter(MoreIterations.numberOfIterations).getValue();
			
			//System.out.println(terminationValue);
			iterationCount++;
		}

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new Driver(), args);
		System.exit(res);
	}
}
