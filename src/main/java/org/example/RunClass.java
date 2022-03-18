package org.example;

import org.example.random.EuclideanTSPGen;

public class RunClass {
    public static void main(String[] args) {
//        loaderTest("atsp.gz");
//        generatorTest();
    }

    public static void loaderTest(String filename){
        FileLoader loader = new FileLoader(filename);
        loader.load();
        System.out.println(loader.getTspData().toString());
    }

    public static void generatorTest(){
        EuclideanTSPGen gen = new EuclideanTSPGen(1000.0, 1000.0);
        gen.setType(EuclideanTSPGen.Type.METROPOLIS);
        gen.generate("testMetro", 1000);
    }
}
