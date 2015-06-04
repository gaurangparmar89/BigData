package edu.uic.ids561;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator 
{
	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader("data/input1.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("data/movies.csv"));
		
		String line;
		while((line = br.readLine()) != null)
		{
			String values[] = line.split("\t");
			bw.write(values[0] + "," + values[1] + "," + values[2] + "\n");
		}
		System.out.println("Success");
		br.close();
		bw.close();

	}

}
