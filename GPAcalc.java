package kthGPA;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GPAcalc{
    public static void main(String[] args) {
        ArrayList<Float> points = new ArrayList<>();
        ArrayList<Float> grades = new ArrayList<>();
        boolean showCalculation = false;
        int numberOfCourse = 0;
        
        if(args.length == 0 || (args.length == 1 && args[0].equals("-help")))
        {
            StartMessege();
            return;
        }
        else if(args[0].equals("-calc")){
            showCalculation = true; 
        }
        
        //read amount of courses
        try {
            if(showCalculation)
            {
                numberOfCourse = Integer.parseInt(args[1]);
            }
            else{
                numberOfCourse = Integer.parseInt(args[0]);
            }
            if(numberOfCourse <= 0)
            {
                throw new IOException();
            }
        } catch (IOException e) {
            StartMessege();
            return;
        } 

        System.out.println("Write each course in the following form:\n: grade[0, 3-5] CompulsoryPoints[1.5-9.5]\n");

        float grade = (float) 0.0;
        float compPoints = (float) 1.5;
        Scanner sc = new Scanner(System.in);
        String line;
        String[] lines;
        byte[] bArr = new byte[1024];

        //Read every grade and every compulsorypoints associated
        //grades and points contain the data and you can reach associated information by using same index
        for (int i = 0; i < numberOfCourse; i++) {
            while (true) {
                System.out.print(": ");
                try {
                    System.in.read(bArr);
                    line = new String(bArr);
                    line = line.replace("\r\n", "");
                    lines = line.split(" ");
                    if(lines.length != 2)
                        throw new Exception();
                        
                    grade = Float.parseFloat(lines[0]);
                    compPoints = Float.parseFloat(lines[1]);

                    if(!(grade == 0 || (grade > 2 && grade < 6)))
                        throw new Exception();
                    else if(!(compPoints >= 1.5 && compPoints <= 9.5))
                        throw new Exception();
                    
                } catch (Exception e) {
                    System.err.println("Follow the format previsouly specified\ngrade[0, 3-5] CompulsoryPoints[1.5-9.5]");
                    continue;
                }
                grades.add(grade);
                points.add(compPoints);
                break;
            }
        }

        //calculate total comulsory points
        float totalCompulsoryPoints = points.get(0);
        if(showCalculation)
        {
            System.out.print("\n[Following KTH Guidelines]\n\nSumming compulsory points...\nAdd " + points.get(0) + " (" + totalCompulsoryPoints + ")\n");
        }

        for (int i = 1; i < points.size(); i++) {
            float d = points.get(i);
            totalCompulsoryPoints += d;
            if(showCalculation)
            {
                System.err.printf("Add %.1f (%.1f)\n", d, totalCompulsoryPoints);
            }
        }
        if(showCalculation){
            System.out.printf("Total: %.1f\n", totalCompulsoryPoints);
        }

        //Sum the total of each grade times point 
        float amaglagmation_score = (float) 0.0;
        if(showCalculation)
        {
            System.out.println("\nSumming [grade times comp.points] for each course...");
        }
        for (int i = 0; i < points.size(); i++) {
            amaglagmation_score += points.get(i) * grades.get(i);
            if (showCalculation) {
                System.out.printf("Add [%.1f ", points.get(i));
                System.out.print("*");
                System.out.printf(" %.1f]", grades.get(i));
                System.out.println(" (" + amaglagmation_score + ")");
            }
        }
        if(showCalculation)
        {
            System.out.printf("Total: %.1f\n", amaglagmation_score);
        }

        //GPA
        float gpa = amaglagmation_score / totalCompulsoryPoints;
        System.out.printf("\nYour GPA is %.2f", gpa);

        sc.close();
    }
    static void StartMessege()
    {
        System.out.println("KTH GPA Calculator\n");
        System.out.println("Usage: \n    GPAcalc #completedCourses");
        System.out.println("    GPAcalc -calc : shows calculations made ");
        System.out.println("    GPAcalc -help : shows this help message, same as \"GPAcalc [enter]\"");
    } 
}