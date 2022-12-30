package ru.bluewhale.diagram;

import com.aspose.diagram.*;
import com.aspose.diagram.examples.Utils;

import java.io.*;
import java.util.*;

public class GraphMqlRelationship {

    public static void main(String[] args) {
        String dataDir = Utils.getDataDir(GraphMqlRelationship.class);

        try (BufferedReader br = new BufferedReader(new FileReader(dataDir + "1.txt"))) {

            StringBuffer sb = null;
            String line = br.readLine();

            List<AsposeDataRelationship> listRelationships = new ArrayList<>();
            while (line != null) {
                if (line.contains("relationship")) {
                    sb = new StringBuffer();
                    sb.append(line.trim()).append("|");
                }

                while ((line = br.readLine()) != null && !line.contains("relationship")) {
                    sb.append(line.trim()).append("|");
                }

                System.out.println("sb: " + sb.toString());
                listRelationships.add(lineProcessor(sb.toString()));
            }

            drawDiagram(listRelationships/*, types*/);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("diagram exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void drawDiagram(List<AsposeDataRelationship> listRelationships/*, Set types*/) {

        Set<String> digraph = new TreeSet<>();
        for (int i = 0; i < listRelationships.size(); i++) {
            AsposeDataRelationship relationship = listRelationships.get(i);
            digraph.add(relationship.toDotData());
        }

        printDigraph(digraph);
    }

    private static void printDigraph(Set<String> digraph) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Utils.getDataDir(GraphMqlRelationship.class) + "sample.dot"))) {
            pw.println("digraph G {");
            pw.println("\tgraph [charset=\"UTF-8\", dpi=2400, fontname=arial, size=\"10,10\"");
            pw.println("\tlabel=\"Relationships SUID, 2022, Development by Evgeniy Gul (c)\", labelloc=\"b\"];");
            pw.println("\tedge [color=darkslateblue];");
            pw.println("\tnode [shape=oval, style=filled, color=aquamarine1];");
            pw.println("\tbeatify=true;");
            digraph.stream().forEach(pw::println);
            pw.println("}");
            System.out.println("printed to file");
        } catch (FileNotFoundException e) {
            System.out.println("file not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO exception" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Shape getShape(int pageNumber, Diagram diagram, String rectangleMaster, String name) {
        double width = 1, height = 1, pinX = 4, pinY = 10;
        long process1 = 0;
        try {
            process1 = diagram.addShape(pinX, pinY, width, height, rectangleMaster, 0);
        } catch (Exception e) {
            System.err.println("exception adding shape: " + e.getMessage());
            e.printStackTrace();
        }

        return diagram.getPages().getPage(pageNumber).getShapes().getShape(process1);
    }

    private static AsposeDataRelationship lineProcessor(String line) {

        String[] splitted = line.split("\\|");

        AsposeDataRelationship relationship = new AsposeDataRelationship();

        for (int i = 0; i < splitted.length; i++) {
            String[] tmpSplitted;
            if (splitted[i].contains("relationship type")) {
                tmpSplitted = splitted[i].split("   ");
                relationship.setName(tmpSplitted[1]);
            } else {
                tmpSplitted = splitted[i].split("=");

                System.out.println(Arrays.asList(tmpSplitted));
                if (tmpSplitted[0].contains("fromtype")) {
                    relationship.addFromType(tmpSplitted[1].trim());
                }
                if (tmpSplitted[0].contains("totype")) {
                    relationship.addToType(tmpSplitted[1].trim());
                }
                if (tmpSplitted[0].contains("fromcardinality")) {
                    relationship.setFromcardinality(tmpSplitted[1].trim());
                }
                if (tmpSplitted[0].contains("tocardinality")) {
                    relationship.setTocardinality(tmpSplitted[1].trim());
                }
            }
        }

        System.out.println("proceeded relationship: " + relationship);
        return relationship;
    }
}
