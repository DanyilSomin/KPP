package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadGraphFromFile {
    private File readFile;
    private Scanner reader;
    public ReadGraphFromFile(File readFile) throws FileNotFoundException {
        this.readFile=readFile;
        this.reader=new Scanner(readFile);
    }
    public ReadGraphFromFile(String fileName)throws  FileNotFoundException{
        this.readFile=new File(fileName);
        this.reader=new Scanner(readFile);
    }
    public List<String> ReadFromFile(){
        ArrayList<String> fileData=new ArrayList<String>();
        while (reader.hasNextLine()){
            fileData.add(reader.nextLine());
        }
        return fileData;
    }
    public void ChangeFile(String fileName)throws  FileNotFoundException{
        this.readFile=new File(fileName);
        this.reader=new Scanner((readFile));
    }
    public  String GetFileName(){
        if(readFile!=null){
            return readFile.getName();
        }else {
            return new String();
        }
    }
    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            if(obj.getClass().equals(this.getClass())){
                return this.readFile.getName().equals(((ReadGraphFromFile)obj).readFile.getName());
            }
        }
        return  false;
    }
}
