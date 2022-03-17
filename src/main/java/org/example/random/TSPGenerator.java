package org.example.random;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class TSPGenerator {
    File newFile = null;
    FileWriter writer = null;
    public abstract void generate(String filename, int count);
    protected void generateFile(String filename){
        newFile = new File(filename);
        try {
            if( !newFile.createNewFile()){
                System.out.println("Cannot create new file with this name");
            }
            writer = new FileWriter(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
