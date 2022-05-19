//Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
    public CSVRecord hottestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord largestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }
    
    public CSVRecord coldestHourInFile(CSVParser parser) {
		//start with largestSoFar as nothing
		CSVRecord smallestSoFar = null;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			// use method to compare two records
			smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
		}
		//The largestSoFar is the answer
		return smallestSoFar;
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
		//start with largestSoFar as nothing
		double totalSoFar = 0;
		int ct = 0;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			// use method to compare two records
			totalSoFar += Double.parseDouble(currentRow.get("TemperatureF"));
			ct += 1;
		}
		//The largestSoFar is the answer
		return totalSoFar/ct;
    }
    
    public double averageTemperatureWithHumidityInFile(CSVParser parser) {
		//start with largestSoFar as nothing
		double totalSoFar = 0;
		int ct = 0;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			// use method to compare two records
			if (Integer.parseInt(currentRow.get("Humidity")) >= 80) {  
			    totalSoFar += Double.parseDouble(currentRow.get("TemperatureF"));
			    ct += 1;
			 }
		}
		//The largestSoFar is the answer
		if (ct == 0) {
		    return -1;
		}
		else {return totalSoFar/ct;}
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
		//start with largestSoFar as nothing
		CSVRecord smallestSoFar = null;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			// use method to compare two records
			if (smallestSoFar == null) {
                            smallestSoFar = currentRow;
                        }
                        //Otherwise
                        else {
                            if (currentRow.get("Humidity").equals("N/A")) {continue;}
                            double currentHum = Integer.parseInt(currentRow.get("Humidity"));
                            
                            double smallestHum = Integer.parseInt(smallestSoFar.get("Humidity"));
                                //Check if currentRow’s temperature > largestSoFar’s
                                if (currentHum < smallestHum) {
                                    //If so update largestSoFar to currentRow
                                    smallestSoFar = currentRow;
                                }
                        }
        
		}
		//The largestSoFar is the answer
		return smallestSoFar;
    }
    
    public void allTemperatures(CSVParser parser) {
        for (CSVRecord currentRow : parser) {
            System.out.println(currentRow.get("DateUTC") + ": " + currentRow.get("TemperatureF"));
        }
    }    
    
    public void allHumidities(CSVParser parser) {
        for (CSVRecord currentRow : parser) {
            System.out.println(currentRow.get("DateUTC") + ": " + currentRow.get("Humidity"));
        }
    }

    public CSVRecord hottestInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }
    
    
    public String fileWithLowestHumidity() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord smallestSoFar = null;
        String smallestFile = null;
        
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            
            
            if (smallestSoFar == null) {
                smallestSoFar = currentRow;
                smallestFile = f.getName();
            }
        //Otherwise
            else {
                double currentHumidity = Integer.parseInt(currentRow.get("Humidity"));
                double smallestHumidity = Integer.parseInt(smallestSoFar.get("Humidity"));
            //Check if currentRow’s temperature > largestSoFar’s
                if (currentHumidity < smallestHumidity) {
                //If so update largestSoFar to currentRow
                    smallestSoFar = currentRow;
                    smallestFile = f.getName();
                }
            }
        }
        return smallestFile;
    }
    
    public String fileWithColdestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord smallestSoFar = null;
        String smallestFile = null;
        
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            
            
            if (smallestSoFar == null) {
                smallestSoFar = currentRow;
                smallestFile = f.getName();
            }
        //Otherwise
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature > largestSoFar’s
                if (currentTemp < smallestTemp) {
                //If so update largestSoFar to currentRow
                    smallestSoFar = currentRow;
                    smallestFile = f.getName();
                }
            }
        }
        return smallestFile;
    }
    
    public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
        //If largestSoFar is nothing
        if (largestSoFar == null) {
            largestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature > largestSoFar’s
            if (currentTemp > largestTemp) {
                //If so update largestSoFar to currentRow
                largestSoFar = currentRow;
            }
        }
        return largestSoFar;
    }
    
    public CSVRecord getSmallestOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        //If largestSoFar is nothing
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature > largestSoFar’s
            if ((currentTemp < smallestTemp) && currentTemp > -99) {
                //If so update largestSoFar to currentRow
                smallestSoFar = currentRow;
            }
        }
        return smallestSoFar;
    }

    public void testHottestInDay () {
        FileResource fr = new FileResource("data/2014/weather-2014-01-01.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("TimeEST"));
    }
    
    public void testHottestInManyDays () {
        CSVRecord largest = hottestInManyDays();
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("DateUTC"));
    }
   
    public void testColdestInDay() {
        FileResource fr = new FileResource("data/2014/weather-2014-05-01.csv");
        CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature was " + smallest.get("TemperatureF") +
                   " at " + smallest.get("TimeEDT"));
    }
    
    public void testFileWithColdestTemperature() {
        String smallestFile = fileWithColdestTemperature();
        System.out.println("Coldest day was in file " + smallestFile);
        FileResource fr = new FileResource("data/2014/" + smallestFile);
        CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature on that day was " + smallest.get("TemperatureF"));
        System.out.println("All the Temperatures on the coldest day were:");
        allTemperatures(fr.getCSVParser());
    }
    
    public void testLowestHumidityInDay() {
        FileResource fr = new FileResource("data/2014/weather-2014-04-01.csv");
        CSVRecord smallest = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Lowest Humidity was " + smallest.get("Humidity") +
                   " at " + smallest.get("DateUTC"));
    }
    
    public void testAverageTemperatureInDay() {
        FileResource fr = new FileResource("data/2014/weather-2014-06-01.csv");
        double average = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average temperature in file is " + average);
    }
    
    public void testAverageTemperatureWithHumidityInDay() {
        FileResource fr = new FileResource("data/2014/weather-2014-03-30.csv");
        double average = averageTemperatureWithHumidityInFile(fr.getCSVParser());
        if (average == -1) {System.out.println("No temperatures with that humidity");}
        else {System.out.println("Average temperature in file is " + average);}
    }
    
    public void testFileWithLowestHumidity() {
        String smallestFile = fileWithLowestHumidity();
        System.out.println("Lowest Humidity day was in file " + smallestFile);
        FileResource fr = new FileResource("data/2014/" + smallestFile);
        CSVRecord smallest = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Lowest Humidity on that day was " + smallest.get("Humidity"));
        System.out.println("All the Humidities on the coldest day were:");
        allHumidities(fr.getCSVParser());
    }
}
