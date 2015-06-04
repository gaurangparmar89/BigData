package edu.uic.ids561;

import java.util.ArrayList;
import java.util.Map.Entry;


public class Tree {
	
	public Node buildTree(ArrayList<Record> records, Node root)
	{
		int bestAttribute = -1;
		double bestGain = 0;
		root.setEntropy(Entropy.calculateEntropy(root.getData()));
		
		if(root.getEntropy() == 0) {
			return root;
		}
		
		for(int i = 0; i < DecisionTree.NUM_ATTRS - 1; i++) 
		{
			if(!DecisionTree.isAttributeUsed(i)) 
			{
				// System.out.println("inside for loop " + DecisionTree.attrMap.get(i));
				double entropy = 0;
				ArrayList<Double> entropies = new ArrayList<Double>();
				ArrayList<Integer> setSizes = new ArrayList<Integer>();
				
				int number_of_values = DecisionTree.setSize(DecisionTree.attrMap.get(i));
				for(int j = 0; j < number_of_values; j++) 
				{
					ArrayList<Record> subset = subset(root, i, j);
					setSizes.add(subset.size());
					
					if(subset.size() != 0) 
					{
						entropy = Entropy.calculateEntropy(subset);
						entropies.add(entropy);
					}
				}
				
				double gain = Entropy.calculateGain(root.getEntropy(), entropies, setSizes, root.getData().size());
				
				if(gain > bestGain) {
					bestAttribute = i;
					bestGain = gain;
				}
			}
		}
		if(bestAttribute != -1) 
		{
			int setSize = DecisionTree.setSize(DecisionTree.attrMap.get(bestAttribute));
			root.setTestAttribute(new DiscreteAttribute(DecisionTree.attrMap.get(bestAttribute), 0));
			root.children = new Node[setSize];
			root.setUsed(true);
			DecisionTree.usedAttributes.add(bestAttribute);
			
			if(DecisionTree.previous_attribute == -1)
				DecisionTree.level.put(0,DecisionTree.attrMap.get(bestAttribute));
			else
			{
				 for (Entry<Integer, String> entry : DecisionTree.level.entrySet()) 
				 {
					 if (entry.getValue().equals(DecisionTree.attrMap.get(DecisionTree.previous_attribute)))
					 {
						 if(DecisionTree.level.containsKey(entry.getKey() + 1))
						 {
							 DecisionTree.level.replace(entry.getKey() + 1, DecisionTree.attrMap.get(bestAttribute));
						 }
						 else
							 DecisionTree.level.put(entry.getKey() + 1,DecisionTree.attrMap.get(bestAttribute));
			     
					 }
				 }
			}
			
			DecisionTree.previous_attribute = bestAttribute;
			
			for (int j = 0; j< setSize; j++) 
			{
				
				root.children[j] = new Node();
				root.children[j].setParent(root);
				root.children[j].setData(subset(root, bestAttribute, j));
				root.children[j].getTestAttribute().setName(DecisionTree.getLeafNames(bestAttribute, j));
				root.children[j].getTestAttribute().setValue(j);
			}

			for (int j = 0; j < setSize; j++) 
			{	
				int level = -1; 
				for (Entry<Integer, String> entry1 : DecisionTree.level.entrySet()) 
				 {
					 if (entry1.getValue().equals(DecisionTree.attrMap.get(bestAttribute)))
					 {
						level = entry1.getKey();
			     
					 }
				 }
				String major_class = commonClass(root.children[j].getData());
				System.out.println(level + "-" + DecisionTree.attrMap.get(bestAttribute)
						   + "-" + DecisionTree.getLeafNames(bestAttribute, j)
						   + "-" + major_class);
				
				buildTree(root.children[j].getData(), root.children[j]);
			} 

			root.setData(null);
		}
		else {
			return root;
		}
		
		return root;
	}
	
	public ArrayList<Record> subset(Node root, int attr, int value) 
	{
		ArrayList<Record> subset = new ArrayList<Record>();
		
		for(int i = 0; i < root.getData().size(); i++) {
			Record record = root.getData().get(i);
			
			// System.out.println(record.getAttributes().get(attr).getValue());
			if(record.getAttributes().get(attr).getValue() == value) {
				subset.add(record);
			}
		}
		return subset;
	}
	
	public String commonClass(ArrayList<Record> records)
	{	int count_no = 0;
		int count_yes = 0;
		for(int i=0; i<records.size(); i++)
		{
			Record record = records.get(i);
			ArrayList<DiscreteAttribute> attributes = record.getAttributes();
			if(attributes.get(4).getValue() == 0)
				count_no++;
			else
				count_yes++;
			//	System.out.print(attributes.get(j).getValue() + " ");
		}
		
		if(count_no > count_yes)
			return "no";
		else
			return "yes";
	}
	
}
