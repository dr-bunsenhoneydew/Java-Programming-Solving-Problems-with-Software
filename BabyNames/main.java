//Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames() {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int girlNames = 0;
        int boyNames = 0;
        int totalNames = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            totalNames += 1;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                boyNames += 1;
            }
            else {
                totalGirls += numBorn;
                girlNames += 1;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
        System.out.println("total names = " + totalNames);
        System.out.println("girl names = " + girlNames);
        System.out.println("boy names = " + boyNames);
    }
    
    public int getRank(int year, String name, String gender) {
        int rankCount = 0;
        boolean isFound = false;
        //FileResource fr = new FileResource("data/yob" + year + ".csv");
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rankCount += 1;
                if (rec.get(0).equals(name)){
                    isFound = true;
                    break;
                }
            }
        }
        if (isFound == false) { return -1;}
        else {return rankCount;}
    }
    
    public String getName(int year, int rank, String gender) {
        int rankCount = 0;
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rankCount += 1;
                if (rankCount == rank) {
                    return rec.get(0);
                }
            }    
        }
        return "NO NAME";
    }
    
    public String whatIsNameInYear(String name, int year, int newYear, String gender) {
        //get rank of original name from birthyear
        //find name of rank in new year
        int rank = getRank(year, name, gender);
        String newName;
        String result;
        if (rank != -1) {
            newName = getName(newYear, rank, gender);
            return name + " born in " + year + " would be " + newName + " if they were born in " + newYear;
        }
        else {return "NO NAME";}
    }
    
    public int yearOfHighestRank(String name, String gender) {
        int year = Integer.MIN_VALUE;
        int rank = Integer.MAX_VALUE;
        DirectoryResource dr = new DirectoryResource();
        
        for (File f : dr.selectedFiles()) {
            int currentYear = Integer.parseInt(f.getName().substring(3, 7));
            int currentRank = getRank(currentYear, name, gender);
            
            if (currentRank != -1 && currentRank < rank) {
                rank = currentRank;
                year = currentYear;
            }
            else {continue;}
        } 
        
        if (year == Integer.MIN_VALUE) {
            return -1;
        } else {
            return year;
        }
    }
    
    public double getAverageRank(String name, String gender) {
        double runningTotalRank = 0;
        int fileCount = 0;
        int rankCount = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            rankCount = 0;
            FileResource fr = new FileResource(f);
            for (CSVRecord rec : fr.getCSVParser(false)) {
                if (rec.get(1).equals(gender)) {
                    rankCount += 1;
                    if (rec.get(0).equals(name)){break;}
                }
            }
            if (rankCount != 0) {
                runningTotalRank += rankCount;
            }  
            fileCount += 1;
        }
        
        if (runningTotalRank > 0) {return runningTotalRank / fileCount;}
        else {return -1.0;}
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int birthsBefore = 0;
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if (rec.get(0).equals(name)) {
                    break;
                }
                else {
                    birthsBefore += Integer.parseInt(rec.get(2));
                }
            }    
        }
        return birthsBefore;
    }
    
    
    
    
    
    public void testGetTotalBirthsRankedHigher() {
        System.out.println(getTotalBirthsRankedHigher(1990,"Emily","F"));
    }
    
    public void testGetAverageRank() {
        System.out.println(getAverageRank("Susan","F"));
    }    
    
    public void testYearOfHighestRank() {
        String name = "Mich";
        String gender = "M";
        System.out.println(name + " most popular year is " + yearOfHighestRank(name, gender));
    }   
    
    public void testWhatIsNameInYear() {
        System.out.println(whatIsNameInYear("Susan", 1972, 2014, "F"));
        
    }
    
    public void testGetName() {
        System.out.println(getName(1980, 350, "F"));
    }    
    
    
    public void testTotalBirths() {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("data/yob1905.csv");
        totalBirths(fr);
    }
    
    public void testGetRank() {
        //FileResource fr = new FileResource();
        //FileResource fr = new FileResource("data/yob2014.csv");
        System.out.println(getRank(1960, "Emily", "F"));
    }
}
