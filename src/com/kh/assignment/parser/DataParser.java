package com.kh.assignment.parser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kh.assignment.utils.FileUtils;

public class DataParser {

	public static List<String> urlList;
	
	public void parseData(String url,String dir)
	{
		
		String outputString;
		try {
			outputString = FileUtils.saveFileFromUrlWithJavaIO(dir,
					url);
			Document doc = Jsoup.parse(outputString);
			String countries[] = { "UK", "England","Wales","Scotland" };
			String param[] = { "Tmax", "Tmin", "Tmean", "Sunshine", "Rainfall" };
			
			urlList = new ArrayList<String>();
			for (int countryCnt = 0; countryCnt < countries.length; countryCnt++) {
				for (int paramCnt = 0; paramCnt < param.length; paramCnt++) {
					Elements eles = doc.getElementsByAttributeValue("title",
							countries[countryCnt] + " Date " + param[paramCnt]);
					Element link = eles.get(0);
					if (link.attr("href") != null) {
						urlList.add(link.attr("href"));
					}
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	  private static final String NEWLINE = "\n";
	  private static final String COMMA = ",";

	
	public void saveData()
	{
		    FileOutputStream fos;
			DataOutputStream dos;
			BufferedReader bufferedReader = null;
			BufferedInputStream bis=null;
			try {
				fos = new FileOutputStream(new File(FileUtils.dirName+File.separator+"weather.csv"));
				dos = new DataOutputStream(fos);
				for (String url : DataParser.urlList) {
				  String downloadedPath = FileUtils.downloadFile(url);
				  File file = new File(downloadedPath);
				  FileInputStream fis = new FileInputStream(file);
				//  DataInputStream bis = new DataInputStream(fis);
				   bis = new BufferedInputStream(fis);
				   bufferedReader = new BufferedReader(new InputStreamReader(bis));
				  boolean readData = false;
				  String line = "";
				  line = bufferedReader.readLine();
				  String param = "";
				  
				  if (line.contains("Maximum Temperature")) {
				    param = "Max temp";
				  }
				  else if (line.contains("Minimum Temperature"))
				  {
					  param="Min Temp";
				  }
				  else if (line.contains("Mean Temperature"))
				  {
					  param="Mean Temp";
				  }
				  else if (line.contains("Rainfall"))
				  {
					  param="Rainfall";
				  }
				  else if (line.contains("Sunshine"))
				  {
					  param="Sunshine";
				  }
				
				  while ((line = bufferedReader.readLine()) != null) {
				    if (readData) {
				    	 String columns[];
				    	if(param.contains("Sunshine") || param.contains("Rainfall"))
				        columns = line.split("  ");
				    	else
						   columns = line.split("   ");
	
				      String region = file.getName().split("\\.")[0];
				      
				      Pattern p = Pattern.compile("-?[\\d.\\d]+");
				      Matcher m = p.matcher(line.substring(5));
				      int i = 1;
				      while (m.find()) {
				        StringBuilder record = new StringBuilder(region);
				        record.append(COMMA);
				        record.append(param);
				        record.append(COMMA);
				        record.append(columns[0]);
				        record.append(COMMA);
				        if (i > 12) {
				          break;
				        }
				        String value = m.group().trim();
				        if (value.equals("")) {
				          value = "NA";
				        }
				        record.append(Month.of(i).toString().substring(0, 3)).append(COMMA).append(value);
				        dos.writeBytes(record.toString() + NEWLINE);
				        i++;
				      }
				    } else if (line.trim().equals("") || line.trim() == null) {
				      line = bufferedReader.readLine();
				      if (line.trim().startsWith("Year")) {
				        readData = true;
				      }
				    }
				  }
				}
				if(bufferedReader!=null)
				bufferedReader.close();
				if(bis!=null)
				bis.close();
				    dos.close();
				    fos.close();
    
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		  }
	
}
