package twitterData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class TwitterDriver extends Configured implements Tool {
	String i = null;
	long interval = 0;
	public int run(String[] args) throws Exception {
		// creating a JobConf object and assigning a job name for identification
		// purposes
		String query = null;
		String st = null;
		String et = null;
		String oPath = null;
		long startTimeStamp = 0;
		long endTimeStamp = 0;
		Options op = new Options();
		op.addOption("q", true, "Query String");
		op.addOption("st", true, "Start Date");
		op.addOption("et", true, "End Date");
		op.addOption("o", true, "Output Path");
		op.addOption("i", true, "Interval between Tweets");
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(op, args);
		if (!cmd.hasOption("q")) {
			System.out.println("Query String not specified! Program Stopping.");
			throw new InvalidInputException();
		} else {
			query = cmd.getOptionValue("q");

			query = URLEncoder.encode(query, "UTF-8");
			System.out.println("Read query=" + query);
		}
		if (!cmd.hasOption("st")) {
			System.out.println("Start Date not specified! Using Jan 1st 2006 as Start Date");
			st = "2006-01-01;00:00:00";
		} else {
			st = cmd.getOptionValue("st");
			System.out.println("Read Start Date=" + st);
		}
		if (!cmd.hasOption("et")) {
			System.out.println("End Date not specified! Using Current Date as End date.");
			et = null;
			endTimeStamp = Calendar.getInstance().getTime().getTime() / 1000;
			System.out.println(endTimeStamp);
		} else {
			et = cmd.getOptionValue("et");
			System.out.println("Read end date=" + et);
		}
		if (!cmd.hasOption("i")) {
			System.out.println("Interval not specified! Program stopping!");
			throw new InvalidInputException();
		} else {
			i = cmd.getOptionValue("i");
			interval = Long.parseLong(i);
			System.out.println("Interval=" + interval);
		}
		if (!cmd.hasOption("o")) {
			System.out.println("Output Path not specified.");
		} else {
			oPath = cmd.getOptionValue("o");
			System.out.println("Read output path=" + oPath);
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
		Date stDate = dateFormat.parse(st);

		System.out.println(stDate.getTime());
		startTimeStamp = stDate.getTime() / 1000;
		if (et != null) {
			Date endDate = dateFormat.parse(et);
			endTimeStamp = endDate.getTime() / 1000;
		}

		generateInputFile(startTimeStamp, endTimeStamp, oPath + "/input");
		JobConf conf = new JobConf(getConf(), TwitterDriver.class);
		conf.setJobName("TopsyReader");
		// Setting configuration object with the Data Type of output Key and
		// Value
		conf.set("query", query);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		// Providing the mapper and reducer class names
		conf.setJarByClass(TwitterDriver.class);
		conf.setMapperClass(TwitterMapper.class);
		conf.setReducerClass(TwitterReducer.class);
		// //conf.setNumReduceTasks(3);
		// conf.setPartitionerClass(FrequentItemSetPartitioner.class);
		// the hdfs input and output directory to be fetched from the command
		// line
		FileInputFormat.addInputPath(conf, new Path(oPath + "/input"));
		FileOutputFormat.setOutputPath(conf, new Path(oPath + "/output"));
		JobClient.runJob(conf);
		return 0;
	}

	private void generateInputFile(long startTimeStamp, long endTimeStamp,
			String path) throws IOException {
		// TODO Auto-generated method stub
		
		Configuration configuration = new Configuration();
		FileSystem hdfs = FileSystem.get(configuration);
		Path file = new Path(path + "/timeStamps");
		if (hdfs.exists(file)) {
			hdfs.delete(file, true);
		}
		OutputStream os = hdfs.create(file);
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os,
				"UTF-8"));
		long intermediateTimeStamp = startTimeStamp;
		long intermediateTimeStamp2 = startTimeStamp + interval;
		while (intermediateTimeStamp2 <= endTimeStamp) {
			br.write(intermediateTimeStamp + "," + intermediateTimeStamp2);
			br.write("\n");
			intermediateTimeStamp = intermediateTimeStamp2;
			intermediateTimeStamp2 = intermediateTimeStamp + interval;
		}
		br.write(intermediateTimeStamp + "," + endTimeStamp);
		br.close();
		hdfs.close();
	}
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new TwitterDriver(), args);
		System.exit(res);
	}
}