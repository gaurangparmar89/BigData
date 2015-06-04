package twitterData;

import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class TwitterMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	private String query;

	@Override
	public void configure(JobConf conf) {
		//Analogous to getting session id in jsf. The name "query" is set in the Driver class.
		query = conf.get("query");
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void map(LongWritable key, Text line,
			OutputCollector<Text, Text> oc, Reporter reporter)
					throws IOException {
		try {
			String[] parts = line.toString().split(",");
			long startTimeStamp = Long.parseLong(parts[0]);
			long endTimeStamp = Long.parseLong(parts[1]);
			int perPage = 100;
			boolean more = true;
			int offSet = 0;
			int total = 0;
			while (more) {
				String jsonString = WebUtils.getJSONData("otter.topsy.com",
						"search.js", query, "tweet", "en", offSet, perPage,
						startTimeStamp, endTimeStamp,
						"09C43A9B270A470B8EB8F2946A9369F3");
				JSONObject jsonObj = new JSONObject(jsonString);
				JSONObject response = jsonObj.getJSONObject("response");
				JSONArray tweetList = response.getJSONArray("list");
				if (offSet == 0) {
					total = response.getInt("total");
				}
				System.out.println("Total number of records to process: "
						+ total);
				System.out.println("Processing records " + offSet + " to "
						+ (offSet + perPage));
				System.out.println(tweetList.length());
				for (int i = 0; i < tweetList.length(); i++) {
					JSONObject tweet = tweetList.getJSONObject(i);
					String toWrite = writeTweet(tweet);
					oc.collect(new Text(query), new Text(toWrite));
					// insertTweet(tweet);
				}

				offSet = offSet + perPage;
				if (offSet >= total) {
					more = false;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String writeTweet(JSONObject tweet) throws JSONException {
		// TODO Auto-generated method stub
		/*String toWrite = null;*/
		String content = StringEscapeUtils.escapeCsv(tweet.getString("content"));
		/*String firstpost_date = StringEscapeUtils.escapeCsv(tweet
				.getString("firstpost_date"));
		String highlight = StringEscapeUtils.escapeCsv(tweet
				.getString("highlight"));

		String hits = StringEscapeUtils.escapeCsv(tweet.getString("hits"));

		String mytype = StringEscapeUtils.escapeCsv(tweet.getString("mytype"));
		String score = StringEscapeUtils.escapeCsv(tweet.getString("score"));
		String target_birth_date = StringEscapeUtils.escapeCsv(tweet
				.getString("target_birth_date"));
		String title = StringEscapeUtils.escapeCsv(tweet.getString("title"));
		String topsy_author_img = StringEscapeUtils.escapeCsv(tweet
				.getString("topsy_author_img"));
		String topsy_author_url = StringEscapeUtils.escapeCsv(tweet
				.getString("topsy_author_url"));
		String topsy_trackback_url = StringEscapeUtils.escapeCsv(tweet
				.getString("topsy_trackback_url"));
		String trackback_author_name = StringEscapeUtils.escapeCsv(tweet
				.getString("trackback_author_name"));
		String trackback_author_nick = StringEscapeUtils.escapeCsv(tweet
				.getString("trackback_author_nick"));
		String trackback_author_url = StringEscapeUtils.escapeCsv(tweet
				.getString("trackback_author_url"));
		String trackback_date = StringEscapeUtils.escapeCsv(tweet
				.getString("trackback_date"));
		String trackback_permalink = StringEscapeUtils.escapeCsv(tweet
				.getString("trackback_permalink"));
		String trackback_total = StringEscapeUtils.escapeCsv(tweet
				.getString("trackback_total"));
		String url = StringEscapeUtils.escapeCsv(tweet.getString("url"));*/
		/*toWrite = hits + "," + title + "," + content + "," + score + ","
				+ trackback_author_name + "," + url + ","
				+ trackback_author_nick + "," + trackback_author_url + ","
				+ trackback_date + "," + trackback_permalink + ","
				+ trackback_total + "," + firstpost_date + "," + highlight
				+ "," + mytype + "," + target_birth_date + ","
				+ topsy_author_img + "," + topsy_author_url + ","
				+ topsy_trackback_url;*/

		return content;
	}

}