package edu.uic.ids561;

import java.util.Random;

public class ReservoirSampling 
{

	public ReservoirSampling() 
	{
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		Integer[] stream = new Integer[10000];
		Integer[] reservoir = new Integer[10];
		Random random = new Random();
		
		for(int i=0;i<stream.length;i++)
		{
			stream[i] = random.nextInt(10);
		}
		
		for(int j=0;j<reservoir.length;j++)
		{
			reservoir[j] = stream[j];
		}
		
		System.out.println("Initial reservoir values");
		for(int i = 0;i<reservoir.length;i++)
		{
			System.out.print(reservoir[i] + " ");
		}
		System.out.println();
		
		for(int k = reservoir.length;k<stream.length;k++)
		{
			int random_number = random.nextInt(k);
			if(random_number < reservoir.length)
			{
				reservoir[random_number] = stream[k];
			}
		}
		
		System.out.println("Final reservoir values");
		for(int i = 0;i<reservoir.length;i++)
		{
			System.out.print(reservoir[i] + " ");
		}
	}
}
