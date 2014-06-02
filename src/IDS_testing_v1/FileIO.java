package IDS_testing_v1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

	public static void appendAtEnd(String fileDirectory, String data) {
		try{
			File file = new File(fileDirectory);

			//if file doesnt exists, then create it
			if(!file.exists()){
				file.createNewFile();
			}

			//true = append file
			FileWriter fileWritter = new FileWriter(file,true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data);
			bufferWritter.close();

			System.out.println("Appended data to file");

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void appendTestCase(String fileDirectory,String fileName,String testName, String pcapPath, String pattern) {
		
		try{
			String readLine, stopLine;

			stopLine = "return self.payloads";

			File fh1 = new File(fileDirectory + "modified_" + fileName);
			fh1.createNewFile();
			FileWriter fw = new FileWriter(fh1);
			BufferedWriter bw = new BufferedWriter(fw);

			File fh2 = new File(fileDirectory + fileName);
			FileReader fr = new FileReader(fh2);
			BufferedReader br = new BufferedReader(fr);

			while( (readLine=br.readLine()) != null) {

				if( readLine.contains(stopLine) ) {

					System.out.println("Found");
					
					bw.write("\n\tself.payloads.append([\n");
					bw.write("\t\t\"" + testName + "\",\n");
					bw.write("\t\t\"pcap\",\n");
					bw.write("\t\t\"" + pcapPath + "\",\n");
					bw.write("\t\t\"" + pattern + "\"\n");
					bw.write("\t\t])\n");

				}

				System.out.println(readLine);

				bw.write(readLine + "\n");
			}

			br.close();
			bw.close();

		}catch(IOException e){
			e.printStackTrace();
		}
	}

}