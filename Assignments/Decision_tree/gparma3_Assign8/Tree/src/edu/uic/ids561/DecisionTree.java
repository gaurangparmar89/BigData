package edu.uic.ids561;

import java.util.ArrayList; 
import java.util.HashMap;

public class DecisionTree
{
	public static int num_nodes=1;
	public static int NUM_ATTRS = 5;
	public static ArrayList<String> attrMap = new ArrayList<String>();
	public static ArrayList<Integer> usedAttributes = new ArrayList<Integer>();
	public static HashMap<Integer, String> level = new HashMap<Integer, String>();
	public static int previous_attribute = -1;
	public static String filename = "";

	public static void main(String[] args) 
	{
		filename = args[0];
		Tree t = new Tree();
		ArrayList<Record> records;

		//read data
		records = FileReader.buildRecords();
		System.out.println("Records fetched!");
		Node root = new Node();
		int i=1;
		for(Record record : records) 
		{
			root.getData().add(record);
		// 	System.out.println("Node of Record- "+(i++)+" added");
		}
		
		t.buildTree(records, root);
		System.out.println("Tree built");

	}

	public static boolean isAttributeUsed(int attribute) 
	{
		if(usedAttributes.contains(attribute)) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	public static int setSize(String set) 
	{
		if(set.equalsIgnoreCase("Outlook")) 
		{
			return 3;
		}
		else if(set.equalsIgnoreCase("Wind")) 
		{
			return 2;
		}
		else if(set.equalsIgnoreCase("Temperature")) 
		{
			return 3;
		}
		else if(set.equalsIgnoreCase("Humidity")) 
		{
			return 2;
		}
		else if(set.equalsIgnoreCase("PlayTennis"))
		{
			return 2;
		}
		return 0;
	}

	public static String getLeafNames(int attributeNum, int valueNum) 
	{
		if(attributeNum == 0) 
		{
			if(valueNum == 0) 
			{
				return "Sunny";
			}
			else if(valueNum == 1)
			{
				return "Overcast";
			}
			else if(valueNum == 2) 
			{
				return "Rain";
			}
		}
		else if(attributeNum == 1) 
		{
			if(valueNum == 0) 
			{
				return "Hot";
			}
			else if(valueNum == 1) 
			{
				return "Mild";
			}
			else if(valueNum == 2) 
			{
				return "Cool";
			}
		}
		else if(attributeNum == 2) 
		{
			if(valueNum == 0) 
			{
				return "High";
			}
			else if(valueNum == 1) 
			{
				return "Normal";
			}
		}
		else if(attributeNum == 3) 
		{
			if(valueNum == 0) 
			{
				return "Weak";
			}
			else if(valueNum == 1)
			{
				return "Strong";
			}
		}

		return null;
	}
}
