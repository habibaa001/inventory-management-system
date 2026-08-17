package util;

import java.util.Scanner;

public class InputValidator{
    private static final Scanner scanner=new Scanner(System.in);

    public static int getInt(String message){
        while(true){
            try{
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    public static int getPositiveInt(String message){
        while(true){
            int value=getInt(message);
            if(value>0)return value;
            System.out.println("Value must be greater than 0.");
        }
    }

    public static double getDouble(String message){
        while(true){
            try{
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    public static double getPositiveDouble(String message){
        while(true){
            double value=getDouble(message);
            if(value>0)return value;
            System.out.println("Value must be greater than 0.");
        }
    }

    public static String getString(String message){
        while(true){
            System.out.print(message);
            String value=scanner.nextLine().trim();
            if(!value.isEmpty())return value;
            System.out.println("Input cannot be empty.");
        }
    }

    public static Scanner getScanner(){
        return scanner;
    }
}