package edu.uic.ids561;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class FileReader 
{
	public static final String PATH_TO_DATA_FILE = DecisionTree.filename;

    public static ArrayList<Record> buildRecords() 
    {
		BufferedReader reader = null;
		DataInputStream dis = null;
		ArrayList<Record> records = new ArrayList<Record>();

        try 
        { 
           File f = new File(PATH_TO_DATA_FILE);
           FileInputStream fis = new FileInputStream(f); 
           reader = new BufferedReader(new InputStreamReader(fis));;
           
           // read header of the file
           String header = reader.readLine();
           StringTokenizer st1 = new StringTokenizer(header, " ");
           DecisionTree.attrMap.add(st1.nextToken());
           DecisionTree.attrMap.add(st1.nextToken());
           DecisionTree.attrMap.add(st1.nextToken());
           DecisionTree.attrMap.add(st1.nextToken());
           DecisionTree.attrMap.add(st1.nextToken());
           
           // read data
           String line;
           Record r = null;
           ArrayList<DiscreteAttribute> attributes;
           while ((line = reader.readLine()) != null) {
              StringTokenizer st = new StringTokenizer(line, " ");
              attributes = new ArrayList<DiscreteAttribute>();
              r = new Record();
              
              if(DecisionTree.NUM_ATTRS != st.countTokens()) {
            	  throw new Exception("Unknown number of attributes!");
              }
              
			  String outlook = st.nextToken();
			  String temperature = st.nextToken();
			  String humidity = st.nextToken();
			  String wind = st.nextToken();
			  String playTennis = st.nextToken();
			  
			  if(outlook.equalsIgnoreCase("overcast")) {
				  attributes.add(new DiscreteAttribute("Outlook", DiscreteAttribute.Overcast));
			  }
			  else if(outlook.equalsIgnoreCase("sunny")) {
				  attributes.add(new DiscreteAttribute("Outlook", DiscreteAttribute.Sunny));
			  }
			  else if(outlook.equalsIgnoreCase("rain")) {
				  attributes.add(new DiscreteAttribute("Outlook", DiscreteAttribute.Rain));
			  }
			  
			  if(temperature.equalsIgnoreCase("hot")) {
				  attributes.add(new DiscreteAttribute("Temperature", DiscreteAttribute.Hot));
			  }
			  else if(temperature.equalsIgnoreCase("mild")) {
				  attributes.add(new DiscreteAttribute("Temperature", DiscreteAttribute.Mild));
			  }
			  else if(temperature.equalsIgnoreCase("cool")) {
				  attributes.add(new DiscreteAttribute("Temperature", DiscreteAttribute.Cool));
			  }
			  
			  if(humidity.equalsIgnoreCase("high")) {
				  attributes.add(new DiscreteAttribute("Humidity", DiscreteAttribute.High));
			  }
			  else if(humidity.equalsIgnoreCase("normal")) {
				  attributes.add(new DiscreteAttribute("Humidity", DiscreteAttribute.Normal));
			  }
			  
			  if(wind.equalsIgnoreCase("weak")) {
				  attributes.add(new DiscreteAttribute("Wind", DiscreteAttribute.Weak));

			  }
			  else if(wind.equalsIgnoreCase("strong")) {
				  attributes.add(new DiscreteAttribute("Wind", DiscreteAttribute.Strong));

			  }
			  
			  if(playTennis.equalsIgnoreCase("no")) {
				  attributes.add(new DiscreteAttribute("PlayTennis", DiscreteAttribute.PlayNo));
			  }
			  else if(playTennis.equalsIgnoreCase("yes")) {
				  attributes.add(new DiscreteAttribute("PlayTennis", DiscreteAttribute.PlayYes));
			  }
			    		    
			  r.setAttributes(attributes);
			  records.add(r);
           }

        } 
        catch (IOException e) 
        { 
           System.out.println(e.getMessage()); 
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage()); 
        }
        finally 
        { 
           if (dis != null) 
           {
              try 
              {
                 dis.close();
              } catch (IOException ioe)
              {
                 System.out.println("IOException error trying to close the file: " + ioe.getMessage()); 
              }
           }
        }
		return records;
	}

}

