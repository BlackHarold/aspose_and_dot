package ru.bluewhale.bean;

public class Code {

    private String name;

    public Code(String header) {
        this.name = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
