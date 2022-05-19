import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        int ct = 0;
        for (Point p : s.getPoints()) {
            ct += 1;
        }    
        return ct;
    }

    public double getAverageLength(Shape s) {
        double perim = getPerimeter(s);
        int points = getNumPoints(s);
        return perim/points;
    }

    public double getLargestSide(Shape s) {
        int maxSide = 0;
        Point prevPt = s.getLastPoint();
        for (Point currPt : s.getPoints()) {
            double currDist = prevPt.distance(currPt);
            if (maxSide == 0 || currDist > maxSide) {
                maxSide = currDist;
            }    
        }    
        return maxSide;
    }

    public double getLargestX(Shape s) {
        double largestX = 0;
        for (Point currPt : s.getPoints()) {
            double currX = currPt.getX();
        }
        if (largestX == 0 || currX > largestX) {
            largestX = currX;
        }    
        return largestX;
    }

    public double getLargestPerimeterMultipleFiles() {
        double largestPerim = 0;
        DirectoryResource dr = new DirectoryResource();
        
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerim = getPerimeter(s);
            if (largestPerim == 0 || currPerim > largestPerim) {
                largestPerim = currPerim;
            }    
        }    
        
        return largestPerim;
    }

    public String getFileWithLargestPerimeter() {
        double largestPerim = 0;
        DirectoryResource dr = new DirectoryResource();
        File temp = null;
        
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerim = getPerimeter(s);
            if (largestPerim == 0 || currPerim > largestPerim) {
                temp = f;
                largestPerim = currPerim;
            }    
        }    
        
        return temp.getName();
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        int points = getNumPoints(s);
        double avgLen = getAverageLength(s); 
        double largestSide = getLargestSide(s);
        double largestX = getLargestX(s);
        System.out.println("number of points = " + points);
        System.out.println("perimeter = " + length);
        System.out.println("average length " + avgLen);
        System.out.println("largest side " + largestSide);
        System.out.println("largest x value " + largestX);
    }
    
    public void testPerimeterMultipleFiles() {
        System.out.println("largest perimeter: " + getLargestPerimeterMultipleFiles());
    }

    public void testFileWithLargestPerimeter() {
        System.out.println("file name: " + getFileWithLargestPerimeter());
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        System.out.println("Assignment 1: Calculating information about shapes");
        pr.testPerimeter();
        
        System.out.println("\n\nAssignment 2: Processing multiple Shape files");
        pr.testPerimeterMultipleFiles();
        pr.testFileWithLargestPerimeter();
    }
}
