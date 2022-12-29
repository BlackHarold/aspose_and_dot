package ru.bluewhale.diagram;

import com.aspose.diagram.*;
import com.aspose.diagram.examples.Utils;

import java.io.*;
import java.util.*;

public class MqlRels {

    public static void main(String[] args) {
        String dataDir = Utils.getDataDir(MqlRels.class);

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
                AsposeDataRelationship relationship = lineProcessor(sb.toString());
                if (relationship.getFromtype() != null && relationship.getTotype() != null) {
                    listRelationships.add(lineProcessor(sb.toString()));
                }
            }

            System.out.println("list relationships: " + listRelationships.size());
            Set<String> types = new HashSet();
            listRelationships.forEach(el -> {
                if (el.getFromtype() != null && el.getTotype() != null) {
                    types.addAll(el.getFromtype());
                    types.addAll(el.getTotype());
                }

            });

            System.out.println("types: " + types);
//            Set types = listRelationships.stream().map(el -> el.getTotype()).collect(Collectors.toCollection(HashSet::new));
//            types.addAll(listRelationships.stream().map(el -> el.getTotype()).collect(Collectors.toCollection(HashSet::new)));

            Diagram diagram = drawDiagram(listRelationships, types);

            //save diagram
            System.out.println("save path" + dataDir);
            saveDiagram(dataDir, diagram);

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

    private static void saveDiagram(String path, Diagram diagram) throws Exception {
        // Установить параметры автоматического макета
        LayoutOptions layoutOptions = new LayoutOptions();

        // Метод
        layoutOptions.setLayoutStyle(LayoutStyle.FLOW_CHART);
        layoutOptions.setDirection(LayoutDirection.BOTTOM_TO_TOP);
        diagram.layout(layoutOptions);

        DiagramSaveOptions options = new DiagramSaveOptions(SaveFileFormat.VSDX);
        System.out.println("ok");
        diagram.save(path + "relationships.vsdx", options);
    }

    private static Diagram drawDiagram(List<AsposeDataRelationship> listRelationships, Set types) throws Exception {
        // Создать новую диаграмму
        int pageNumber = 0;
        String rectangleMaster = "Process", decisionMaster = "Decision", connectorMaster = "Dynamic connector";
        String dataDir = Utils.getDataDir(AsposeGraph.class);
        Diagram diagram = new Diagram(dataDir + "XANFLOWCHARTNEW.vss");

        diagram.addMaster(dataDir + "XANFLOWCHARTNEW.vss", "Process");
        diagram.addMaster(dataDir + "XANFLOWCHARTNEW.vss", "Dynamic connector");
        Page page = diagram.getPages().getPage(pageNumber);

        double width = 1, height = 1, pinX = 4, pinY = 10;

        //add shapes
        Map<String, Shape> shapes = new HashMap<>();

        Iterator it = types.iterator();
        while (it.hasNext()) {
            String typeName = (String) it.next();

            try {
                long shapeId = diagram.addShape(pinX, pinY, width, height, "Process", 0);
                Shape shape = page.getShapes().getShape(shapeId);
                shape.getText().getValue().add(new Txt(typeName));
                shape.setName(typeName);
                shapes.put(typeName, shape);
                pinY = pinY - 2;

            } catch (Exception e) {
                System.err.println("exception adding shape: " + e.getMessage());
                e.printStackTrace();
            }
        }

        Set<String> digraph = new TreeSet<>();
        digraph.add("digraph G {");
        for (int i = 0; i < listRelationships.size(); i++) {
            AsposeDataRelationship relationship = listRelationships.get(i);
            List<String> fromTypes = relationship.getFromtype();
            List<String> toTypes = relationship.getTotype();
            boolean typeProblemChecker;

            try {
                //check problem
                typeProblemChecker = (fromTypes == null || toTypes == null);

                if (!typeProblemChecker) {
                    for (int k = 0; k < fromTypes.size(); k++) {
                        String fromType = relationship.getFromtype().get(k);
                        for (int j = 0; j < toTypes.size(); j++) {
                            long connectorId = diagram.addShape(new Shape(), connectorMaster, 0);
                            Shape connector = diagram.getPages().getPage(pageNumber).getShapes().getShape(connectorId);
                            connector.setName(relationship.getName());
                            connector.getText().getValue().add(new Txt(relationship.getName()));
                            String toType = relationship.getTotype().get(j);
                            diagram.getPages().getPage(pageNumber).connectShapesViaConnector(shapes.get(fromType), ConnectionPointPlace.BOTTOM,
                                    shapes.get(toType), ConnectionPointPlace.TOP, connector);

                            String fromDigraph = "\t" + fromType + " -> " + toType + ";";
                            String toDigraph = "\t" + fromType + " -> " + toType + ";";
                            digraph.add(toDigraph);
                        }
                    }
                } else {
//                    System.out.println("relationship problem: " + relationship);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        digraph.add("}");

        printDigraph(digraph);

        return diagram;
    }

    private static void printDigraph(Set<String> digraph) {
        digraph.stream().forEach(System.out::println);
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
