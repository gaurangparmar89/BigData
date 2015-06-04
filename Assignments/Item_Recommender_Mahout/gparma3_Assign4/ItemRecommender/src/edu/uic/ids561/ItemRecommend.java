package edu.uic.ids561;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemRecommend {

	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new FileReader("/home/gaurang/Personal/Big_Data/CF/input/u.item"));
		String line;
		HashMap<Integer,String> hm = new HashMap<Integer, String>();
		while((line = br.readLine()) != null)
		{
			String values[] = line.split("\\|");
			hm.put(Integer.parseInt(values[0]), values[1]);
		}
		br.close();

		try
		{
		// TODO Auto-generated method stub
		DataModel model = new FileDataModel(new File("data/movies.csv"));
		ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
		
		for(LongPrimitiveIterator item = model.getItemIDs();item.hasNext();)
		{
			long itemID = item.nextLong();
			List<RecommendedItem> recommendations;
			recommendations = recommender.mostSimilarItems(itemID, 10);
		
			for (RecommendedItem recommendation : recommendations)
			{
				System.out.println(hm.get((int)itemID) + "," + hm.get((int)recommendation.getItemID())+ "," + recommendation.getValue());
			}
				 
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
