package ru.bluewhale.bean;

import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class Group {
    @CsvBindByName(column = "Group")
    private String name;

    @CsvBindByName(column = "Название строк")
    private String department;

    @CsvBindByName
    List<Code> codes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Code> getCodes() {
        return codes;
    }

    public void setCodes(List<Code> codes) {
        this.codes = codes;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", codes=" + codes +
                '}';
    }
}
