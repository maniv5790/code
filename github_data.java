package c1x;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class github_data {
	
	public static void main(String[] args) throws IOException
	{
				String file_date = args[0];
				
				 File file = new File("D:\\WorkSpace\\c1x\\"+file_date);
			      //Creating the directory
			      boolean bool = file.mkdir();
			      if(bool){
			         System.out.println("Directory created successfully");
			      }else{
			         System.out.println("Sorry couldn’t create specified directory since already exists");
			      }
				System.out.println(file_date);
				for(int i = 0 ; i <= 23 ; i++)
				{
				
				String FILE_URL = "https://data.gharchive.org/"+file_date+"-"+i+".json.gz" ;//"https://data.gharchive.org/2020-09-21-16.json.gz" "2020-09-21-16.json.gz"
				String FILE_NAME = file_date+"-"+i+".json.gz";//
				System.out.println(FILE_URL);
				try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
						  FileOutputStream fileOutputStream = new FileOutputStream("D:\\WorkSpace\\c1x\\"+file_date+"\\"+FILE_NAME)) {
							    byte dataBuffer[] = new byte[1024];
							    int bytesRead;
							    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
							        fileOutputStream.write(dataBuffer, 0, bytesRead);
							    }
							} catch (IOException e) {
								System.out.println("exception occured"+e);
							}
			
				}

	}

}
