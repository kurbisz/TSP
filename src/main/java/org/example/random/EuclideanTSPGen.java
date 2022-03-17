package org.example.random;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class EuclideanTSPGen extends TSPGenerator{

    private final double max_x, max_y;

    public EuclideanTSPGen(double max_x, double max_y) {
        this.max_x = max_x;
        this.max_y = max_y;
    }

    public enum Type{
        RANDOM,
        METROPOLIS,
        SPREADED
    }

    private class WritePoint{
        double x;
        double y;

        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    Type type = Type.RANDOM;
    File newFile;

    public void setType(Type type){
        this.type = type;
    }

    @Override
    public void generate(String filename, int count) {
        generateFile(filename+".tsp");
        if(writer == null) return;
         switch (type){
             case RANDOM:
                 generateRnd(filename, count);
                 break;
             case SPREADED:
                 generateSpreaded(filename, count);
                 break;
             case METROPOLIS:
                 generateMetro(filename, count);
                 break;
         }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateHeader(String filename, int count){
        try {
            writer.write("NAME : "+filename+"\n");
            writer.write("COMMENT : "+count+" city problem.\n");
            writer.write("TYPE : TSP\n");
            writer.write("DIMENSION : "+count+"\n");
            writer.write("EDGE_WEIGHT_TYPE : EUC_2D\n");
            writer.write("NODE_COORD_SECTION\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void generateRnd(String filename, int count){
        generateHeader(filename, count);
        Random generator = new Random(System.currentTimeMillis());
        ArrayList<WritePoint> generated = new ArrayList<>();
        int i = count;
        while(i > 0){
            WritePoint p = new WritePoint();
            p.x = generator.nextDouble()*max_x;
            p.y = generator.nextDouble()*max_y;
            boolean unique = true;
            for (WritePoint point: generated) {
                if(point.x == p.y && point.y == p.y) {
                    unique = false;
                    break;
                }
            }
            if(unique){
                generated.add(p);
                i--;
            }
        }

        try {
            i = 1;
            for(WritePoint point: generated){
                writer.write(i+" "+point.toString()+"\n");
                i++;
            }
            writer.write("EOF\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void generateMetro(String filename, int count){

    }
    private void generateSpreaded(String filename, int count){

    }

}
