import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * Single object will be used to read two different files(implemented using multi-threading.
 * We can store state of read pointer when it reads one line.
 * When thread resumes its execution we can restore this state to read pointer
 * and in can start reading from where it left earlier.
 */
public class ParallelFileReader {
	
	ParallelFileReader(){
	}
	
	public synchronized String readNextLine(BufferedReader b)
	{
		String s="";
		try {
			s = b.readLine();
			if(s!=null)
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	public static void main(String []args) throws FileNotFoundException
	{
		//Enter Filenames
		Scanner s= new Scanner(System.in);
		System.out.print("Enter filename 1:");
		String file1= s.nextLine();
		System.out.print("Enter filename 2:");
		String file2= s.nextLine();
		s.close();
		
		// Single object of BufferedReader will read two files.
		BufferedReader br= null;
		Read r1 = new Read(file1,br);
		Read r2 = new Read(file2,br);
		r1.start();
		r2.start();
	}
}

class Read extends Thread
{
	String filename;
	BufferedReader reader,br;
	ParallelFileReader p;
	Read(String f, BufferedReader b) throws FileNotFoundException
	{
		filename=f;
		reader=b;
		br=new BufferedReader(new FileReader(filename));
		p=new ParallelFileReader();
	}
	@Override
	public void run()
	{
		reader=br;
		String s=p.readNextLine(reader);
		while(true)
		{
			s=p.readNextLine(reader);
			br=reader;
			if(s==null)
				break;
		}
	}
}

/*

SAMPLE OUTPUT:
 
Enter filename 1:C:\Temp\file-1.txt
Enter filename 2:C:\Temp\file-2.txt
You are in file-1 line-1
You are in file-2 line-1
You are in file-1 line-2
You are in file-2 line-2
You are in file-2 line-3
You are in file-2 line-4
You are in file-2 line-5
You are in file-2 line-6
You are in file-2 line-7
You are in file-2 line-8
You are in file-2 line-9
You are in file-2 line-10
You are in file-2 line-11
You are in file-1 line-3
You are in file-2 line-12
You are in file-1 line-4
You are in file-2 line-13
You are in file-1 line-5
You are in file-2 line-14
You are in file-1 line-6
You are in file-1 line-7
You are in file-2 line-15
You are in file-1 line-8
You are in file-1 line-9
You are in file-1 line-10
You are in file-1 line-11
You are in file-1 line-12
You are in file-1 line-13
You are in file-1 line-14
You are in file-1 line-15

*/
