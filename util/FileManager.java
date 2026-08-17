package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static <T> void saveData(String filePath,List<T> data){
        try(ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream(filePath))){
            output.writeObject(data);
        }catch(IOException e){
            System.out.println("Error saving data: "+e.getMessage());
        }
    }
    public static <T> List<T> loadData(String filePath){
        File file=new File(filePath);
        if(!file.exists())return new ArrayList<>();

        try(ObjectInputStream input=new ObjectInputStream(new FileInputStream(file))){
            return (List<T>)input.readObject();
        }catch(IOException|ClassNotFoundException e){
            System.out.println("Error loading data: "+e.getMessage());
            return new ArrayList<>();
        }
    }
}