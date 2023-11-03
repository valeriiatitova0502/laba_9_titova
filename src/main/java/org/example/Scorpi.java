package org.example;

@Table(title = "Scorpion")
public class Scorpi {

    public enum woolLenght {
        LOW, MEDIUM, HIGH;
        public String getWoolLenght() {
            return this.toString();
        }
    }

    @Column
    private String name;

    @Column
    private String age;

    @Column
    private int maxRunDistance;

    @Column
    private String woolLenght;

    public Scorpi(String name, String age, int maxRunDistance, String woolLenght) {
        this.name = name;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.woolLenght = woolLenght;
    }


}