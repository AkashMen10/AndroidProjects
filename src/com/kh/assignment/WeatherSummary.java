package com.kh.assignment;

import java.io.IOException;
import java.net.MalformedURLException;

import com.kh.assignment.parser.DataParser;
import com.kh.assignment.utils.FileUtils;




public class WeatherSummary {

	static final String url = "http://www.metoffice.gov.uk/climate/uk/summaries/datasets#Yearorder";
	static final String dirName = "d://WeatherData";

	public static void main(String[] args) {
		
			System.out.println("Data Processing Started");

			// Parse data and get urls 
			DataParser dataParser = new DataParser();
			dataParser.parseData(url,dirName);

			// Download data from urls and save data into csv
			dataParser.saveData();

			System.out.println("Data Downloaded");

	}


		
	

	

}
