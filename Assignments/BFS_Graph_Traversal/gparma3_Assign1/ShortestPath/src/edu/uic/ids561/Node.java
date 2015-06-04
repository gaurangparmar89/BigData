package edu.uic.ids561;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;

public class Node 
{
	public enum Color { WHITE, GRAY, BLACK };
	private String id;
	private int dist;
	private List<String> edges = new ArrayList<String>();
	private Color color;
	private String parent;
	
	public Node()
	{
		edges = new ArrayList<String>();
		dist = Integer.MAX_VALUE;
		color = Color.WHITE;
		parent = null;
	}
	
	public Node(String input)
	{
		String[] inputNode = input.split("\t");
		String key = "";
		String value = "";

		key = inputNode[0];
		value = inputNode[1];
		
		String[] token = value.split("\\|");
		
		this.id = key;
		
		for(String i: token[0].split(","))
		{
			if(i.length() > 0)
				this.edges.add(i);
		}
		
		if (token[1].equals("Integer.MAX_VALUE")) 
		{
            this.dist = Integer.MAX_VALUE;
        } 
		else 
		{
            this.dist = Integer.parseInt(token[1]);
        }
		
		this.color = Color.valueOf(token[2]);
		
		this.parent = token[3];
	}
	
	public Text getNodeInfo()
	{
		StringBuffer s = new StringBuffer();
		
		try
		{
			for(String i : edges)
			{
				s.append(i).append(",");
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		
		s.append(("|"));
		if(this.dist < Integer.MAX_VALUE)
		{
			s.append(this.dist);
		}
		else
		{
			s.append("Integer.MAX_VALUE");
		}
		s.append("|");
		s.append(this.color.toString());
		s.append("|");
		s.append(this.parent);
			
		return new Text(s.toString());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public List<String> getEdges() {
		return edges;
	}

	public void setEdges(List<String> edges) {
		this.edges = edges;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}	
}
