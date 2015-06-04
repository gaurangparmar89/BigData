package mavenlda;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf ;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.clustering.LDA;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.DistributedLDAModel;
import org.apache.spark.mllib.clustering.LDA;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.rdd.RDD;

import scala.Function1;
import scala.Tuple2;

public class NewClass 
{

	public static void main(String[]args) throws IOException
	{
		SparkConf conf = new SparkConf().setMaster("local").setAppName("LDAEx");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		File output_file = new File("/home/gaurang/Personal/Big_Data/LDA/output/out1.txt");
		FileWriter fw = new FileWriter(output_file);
		BufferedWriter bw = new BufferedWriter(fw);	
		
		String path = "/home/gaurang/Personal/Big_Data/LDA/Doc_files1/*.txt";
		
		JavaRDD<String> data = sc.textFile(path);
		JavaRDD<Vector> parsedData = data.map(new Function<String, Vector>() 
			{
		    public Vector call(String s) 
		    {
		    	String[] sarray = s.trim().split(" ");
		    	double[] values = new double[sarray.length];
		    	for (int i = 0; i < sarray.length; i++)
		    		values[i] = Double.parseDouble(sarray[i]);
		    	return Vectors.dense(values);
		    }
		    });
		
		 JavaPairRDD<Long, Vector> corpus = JavaPairRDD.fromJavaRDD(parsedData.zipWithIndex().map(
				new Function<Tuple2<Vector, Long>, Tuple2<Long, Vector>>() 
				{
		    		public Tuple2<Long, Vector> call(Tuple2<Vector, Long> doc_id) 
		    		{
		    			return doc_id.swap();
		    		}
		    	}
		    	));
		 corpus.cache();
		    
		 DistributedLDAModel ldaModel = new LDA().setK(10).run(corpus);
	
		 /* Doc distribution pending -- 
		 Matrix topics = ldaModel.topicsMatrix();
		 RDD<Tuple2<Object, Vector>> doc_dist = ldaModel.topicDistributions();
		 Tuple2<Object, Vector> d = doc_dist.first();
		 Object f = d._1;
		 Vector g = d._2;
		 System.out.println(doc_dist.count());
		 System.out.println("Doc: " + f + " " + g);
		
		  -- */
	    
		 Tuple2<int[], double[]>[] topic_dist = ldaModel.describeTopics(10);
	      
		 for(int i =0;i<topic_dist.length;i++)
		 {
			 // System.out.println(topic_dist.length);
		    double[] prob = topic_dist[i]._2;
		    int[] id = topic_dist[i]._1;
		    
		    // System.out.print("Topic " + i + ": ");
		    bw.write("Topic " + i + ": ");
		    
		    for(int j=0;j<prob.length;j++)
		    {
		    	// System.out.print(id[j] + "-" + (Math.round(prob[j]*10000)/10000.0) +", ");
		    	bw.write(id[j] + "-" + (Math.round(prob[j]*10000)/10000.0) +", ");
		    }   	
		    // System.out.println();
		    bw.write("\n");
			
	      }  
		  // System.out.println();
		  bw.write("\n");
		  bw.close();
	    
	} 

}
