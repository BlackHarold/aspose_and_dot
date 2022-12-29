package ru.bluewhale.diagram;

import java.util.ArrayList;
import java.util.List;

public class AsposeDataRelationship {
    private String name;
    private List<String> fromtype;
    private List<String> totype;
    private String tocardinality;
    private String fromcardinality;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFromtype() {
        return fromtype;
    }

    public void setFromtype(List<String> fromtype) {
        this.fromtype = fromtype;
    }

    public void addFromType(String s) {
        if (fromtype != null) {
            fromtype.add(s);
        } else {
            fromtype = new ArrayList<>();
            fromtype.add(s);
        }
    }

    public List<String> getTotype() {
        return totype;
    }

    public void setTotype(List<String> totype) {
        this.totype = totype;
    }

    public void addToType(String s) {
        if (totype != null) {
            totype.add(s);
        } else {
            totype = new ArrayList<>();
            totype.add(s);
        }
    }

    public String getTocardinality() {
        return tocardinality;
    }

    public void setTocardinality(String tocardinality) {
        this.tocardinality = tocardinality;
    }

    public String getFromcardinality() {
        return fromcardinality;
    }

    public void setFromcardinality(String fromcardinality) {
        this.fromcardinality = fromcardinality;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "name='" + name + '\'' +
                ", fromtype=" + fromtype +
                ", totype=" + totype +
                ", tocardinality='" + tocardinality + '\'' +
                ", fromcardinality='" + fromcardinality + '\'' +
                '}';
    }
}
