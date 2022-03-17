package org.example;

import org.example.random.EuclideanTSPGen;

public class RunClass {
    public static void main(String[] args) {
//        loaderTest("atsp.gz");
        EuclideanTSPGen gen = new EuclideanTSPGen(1000.0, 1000.0);
        gen.generate("test", 1000);
    }

    public static void loaderTest(String filename){
        FileLoader loader = new FileLoader(filename);
        loader.load();
        System.out.println(loader.getTspData().toString());
    }
}
