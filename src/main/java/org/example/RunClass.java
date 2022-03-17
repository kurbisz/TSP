package org.example;

public class RunClass {
    public static void main(String[] args) {
        FileLoader loader = new FileLoader("atsp.gz");
        loader.load();
        System.out.println(loader.getTspData().toString());
    }
}
