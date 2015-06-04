package edu.uic.ids561;


public class DiscreteAttribute extends Attribute 
{	
	public static final int Sunny = 0;
	public static final int Overcast = 1;
	public static final int Rain = 2;

	public static final int Hot = 0;
	public static final int Mild = 1;
	public static final int Cool = 2;
	
	public static final int High = 0;
	public static final int Normal = 1;
	
	public static final int Weak = 0;
	public static final int Strong = 1;
	
	public static final int PlayNo = 0;
	public static final int PlayYes = 1;

	public DiscreteAttribute(String name, double value) 
	{
		super(name, value);
	}

}

