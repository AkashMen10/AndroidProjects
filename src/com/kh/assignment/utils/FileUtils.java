package com.kh.assignment.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUtils {

	public static String dirName="data";
	// Using Java IO
	public static String saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
			throws MalformedURLException, IOException {
    	FileUtils.makeDir(dirName);

		BufferedInputStream in = null;
		
		FileOutputStream fout = null;
		
		StringBuilder sb = new StringBuilder();

		try {
			
			in = new BufferedInputStream(new URL(fileUrl).openStream());
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
		}
	 finally {
		if (in != null)
			in.close();
		if (fout != null)
			fout.close();
	}
		return sb.toString();
		
	}

		  public static String downloadFile(String urlTODownload) {
		    try {
		      URL url = verify(urlTODownload);
		      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		      InputStream in = null;
		      String filename = url.getFile();
		      filename = filename.substring(filename.lastIndexOf('/') + 1);
		      FileOutputStream out = new FileOutputStream(dirName+File.separator + filename);
		      in = connection.getInputStream();
		      int read = -1;
		      byte[] buffer = new byte[4096];
		      while ((read = in.read(buffer)) != -1) {
		        out.write(buffer, 0, read);
		      }
		      in.close();
		      out.close();
		      return dirName+File.separator + filename;
		    } catch (Exception ex) {
		      System.out.println("Exception occured while doanloading");
		      return "failed";
		    }
		  }


		  private static URL verify(String url) {
		    if (!url.toLowerCase().startsWith("http://")) {
		      return null;
		    }
		    URL verifyUrl = null;

		    try {
		      verifyUrl = new URL(url);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return verifyUrl;
		  }

		 public static void makeDir(String name)
		  {
			  File theDir = new File(name);

			// if the directory does not exist, create it
			if (!theDir.exists()) {
			    System.out.println("creating directory: " + theDir.getName());
			    boolean result = false;

			    try{
			        theDir.mkdir();
			        result = true;
			    } 
			    catch(SecurityException se){
			        //handle it
			    }        
			    if(result) {    
			        System.out.println("DIR created");  
			    }
			}
		  }
}
