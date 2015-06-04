package twitterData;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class TwitterReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	@Override
	public void configure(JobConf arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reduce(Text key, Iterator<Text> values,
			OutputCollector<Text, Text> oc, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		while (values.hasNext()) {
			Text text = (Text) values.next();
			oc.collect(null, text);

		}

	}
}
