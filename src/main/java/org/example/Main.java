package org.example;


public class Main {
    public static void main(String[] args) throws Exception {
        Scorpi scorpi = new Scorpi("Byria", "7", 200, "HIGH");
        Scorpi scorpi2 = new Scorpi("Murk", "8", 234, "LOW");
        Scorpi scorpi3 = new Scorpi("Valli", "3", 443, "MEDIUM");
        AnnotationProcessor.createTable(scorpi);
        AnnotationProcessor.insertIntoTable(scorpi);
        AnnotationProcessor.insertIntoTable(scorpi2);
        AnnotationProcessor.insertIntoTable(scorpi3);
    }
}