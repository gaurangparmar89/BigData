package twitterData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebUtils {

	public static String getJSONData(String server, String path, String query,
			String type, String lang, int offset, int perPage, long minTime,
			long maxTime, String apiKey) {
		URL url = null;
		try {
			url = new URL("http://" + server + "/" + path + "?q=" + query
					+ "&type=" + type + "&allow_lang=" + lang + "&offset="
					+ offset + "&perpage=" + perPage + "&mintime=" + minTime
					+ "&maxtime=" + maxTime + "&apikey=" + apiKey);
			System.out.println(url);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String inputLine = in.readLine();
			StringBuffer sb = new StringBuffer();
			while (inputLine != null) {
				sb.append(inputLine);
				inputLine = in.readLine();
			}
			in.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}