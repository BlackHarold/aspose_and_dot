package ru.bluewhale.diagram;

import com.aspose.diagram.*;
import com.aspose.diagram.examples.Masters.CheckMasterPresencebyID;
import com.aspose.diagram.examples.Utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;


public class CompactTree {
    private static Diagram diagram;
    private static String dataDir = "./src/main/resources/";

    private static String diagramName = "template.vsdx";

    public static void main(String[] args) throws Exception {
//        createDiagram(diagramName);
//        diagram = initDiagram(diagramName);
//        addMaster(diagram);

        treeChart();
        drawFlowChart();
    }

    private static void createDiagram(String diagramName) throws Exception {
        // For complete examples and data files, please go to https://github.com/aspose-diagram/Aspose.Diagram-for-Java
        // The path to the documents directory.

        // initialize a Diagram class
        Diagram diagram = new Diagram();

        // save diagram in the VSDX format
        diagram.save(dataDir + diagramName, SaveFileFormat.VSDX);
    }

    private static Diagram initDiagram(String diagramName) throws Exception {
        // For complete examples and data files, please go to https://github.com/aspose-diagram/Aspose.Diagram-for-Java
        // The path to the documents directory.
        // Open the stream. Read only access is enough for Aspose.Diagram to load a diagram.
        InputStream stream = new FileInputStream(dataDir + "Drawing1.vsdx");

        //Call the diagram constructor to load diagram from a VSDX stream
//        Diagram vsdDiagram = new Diagram(stream);
//        stream.close();

        //Call the diagram constructor to load diagram from a VDX file
        Diagram vdxDiagram = new Diagram(dataDir + diagramName);

        /*
         * Call diagram constructor to load diagram from a VSS file
         * providing load file format
         */
//        Diagram vssDiagram = new Diagram(dataDir + "Basic.vss", LoadFileFormat.VSS);

        /*
         * Call diagram constructor to load diagram from a VSX file
         * providing load options
         */
//        LoadOptions loadOptions = new LoadOptions(LoadFileFormat.VSX);
//        Diagram vsxDiagram = new Diagram(dataDir + "Drawing1.vsx", loadOptions);
        return vdxDiagram;
    }

    private static void addMaster(Diagram diagram) throws Exception {

// Load stencil to a stream
        String templateFileName = dataDir + "NetApp-FAS-series.vss";

// Add master with stencil file path and master id
        String masterName = "FAS80xx rear empty";
        diagram.addMaster(templateFileName, 2);

// Add master with stencil file path and master name
        diagram.addMaster(templateFileName, masterName);

// adds master to diagram from source diagram
        Diagram src = new Diagram(templateFileName);
        diagram.addMaster(src, masterName);

// Adds shape with defined PinX and PinY.
        diagram.addShape(2.0, 2.0, masterName, 0);
        diagram.addShape(6.0, 6.0, masterName, 0);

// Adds shape with defined PinX,PinY,Width and Height.
        diagram.addShape(7.0, 3.0, 1.5, 1.5, masterName, 0);
    }

    private static void treeChart() throws Exception {

        // Загружайте образцы из любой существующей диаграммы, трафарета или шаблона
        String dataDir = Utils.getDataDir(CheckMasterPresencebyID.class);
        String visioStencil = "Basic Shapes.vss";
        String rectangleMaster = "Rectangle";
        String connectorMaster = "Dynamic connector";
        int pageNumber = 0;
        double width = 1;
        double height = 1;
        double pinX = 4.25;
        double pinY = 9.5;

        // Определите значения для построения иерархии
        List<String> listPos = Arrays.asList(new String[]{"0", "0:0", "0:1", "0:2", "0:3", "0:4", "0:5", "0:6", "0:0:0", "0:0:1", "0:3:0", "0:3:1", "0:3:2", "0:6:0", "0:6:1"});

        // Определите Hashtable для сопоставления имени строки с длинным идентификатором формы.
        Hashtable shapeIdMap = new Hashtable();

        // Создать новую диаграмму
        Diagram diagram = new Diagram(dataDir + visioStencil);
        diagram.getPages().get(pageNumber).getPageSheet().getPageProps().getPageWidth().setValue(11);
        for (String orgnode : listPos) {
            // Добавьте новую форму прямоугольника
            long rectangleId = diagram.addShape(pinX++, pinY++, width, height, rectangleMaster, pageNumber);
            // Установите свойства новой фигуры
            Shape shape = diagram.getPages().get(pageNumber).getShapes().getShape(rectangleId);
            shape.getText().getValue().add(new Txt(orgnode));
            shape.setName(orgnode);
            shapeIdMap.put(orgnode, rectangleId);
        }

        // Создание соединений между узлами
        for (String orgName : listPos) {
            int lastColon = orgName.lastIndexOf(':');
            if (lastColon > 0) {
                String parendName = orgName.substring(0, lastColon);
                long shapeId = (long) shapeIdMap.get(orgName);
                long parentId = (long) shapeIdMap.get(parendName);
                Shape connector1 = new Shape();
                long connecter1Id = diagram.addShape(connector1, connectorMaster, pageNumber);
                diagram.getPages().get(pageNumber).connectShapesViaConnector(parentId, ConnectionPointPlace.RIGHT,
                        shapeId, ConnectionPointPlace.LEFT, connecter1Id);
            }
        }

        //автоматическая компоновка диаграммы CompactTree
        LayoutOptions compactTreeOptions = new LayoutOptions();
        compactTreeOptions.setLayoutStyle(LayoutStyle.COMPACT_TREE);
        compactTreeOptions.setDirection(LayoutDirection.DOWN_THEN_RIGHT);
        compactTreeOptions.setEnlargePage(false);

        diagram.getPages().get(pageNumber).layout(compactTreeOptions);

        // Сохранить диаграмму
        diagram.save(dataDir + "DrawCompactTreeChart_java.vsdx", SaveFileFormat.VSDX);
    }

    private static void drawFlowChart() throws Exception {
        String dataDir = Utils.getDataDir(CheckMasterPresencebyID.class);
        // Загружайте образцы из любой существующей диаграммы, трафарета или шаблона
        String visioStencil = "Basic Shapes.vss";
        String rectangleMaster = "Rectangle";
        String connectorMaster = "Dynamic connector";
        int pageNumber = 0;
        double width = 1;
        double height = 1;
        double pinX = 4.25;
        double pinY = 9.5;

        // Определите значения для построения иерархии
        List<String> listPos = Arrays.asList(new String[]{"0", "0:0", "0:1", "0:2", "0:3", "0:4", "0:5", "0:6", "0:0:0", "0:0:1", "0:3:0", "0:3:1", "0:3:2", "0:6:0", "0:6:1"});

        // Определите Hashtable для сопоставления имени строки с длинным идентификатором формы.
        Hashtable shapeIdMap = new Hashtable();

        // Создать новую диаграмму
        Diagram diagram = new Diagram(dataDir + visioStencil);
        diagram.getPages().get(pageNumber).getPageSheet().getPageProps().getPageWidth().setValue(11);
        for (String orgnode : listPos) {
            // Добавьте новую форму прямоугольника
            long rectangleId = diagram.addShape(pinX++, pinY++, width, height, rectangleMaster, pageNumber);
            // Установите свойства новой фигуры
            Shape shape = diagram.getPages().get(pageNumber).getShapes().getShape(rectangleId);
            shape.getText().getValue().add(new Txt(orgnode));
            shape.setName(orgnode);
            shapeIdMap.put(orgnode, rectangleId);
        }

        // Создание соединений между узлами
        for (String orgName : listPos) {
            int lastColon = orgName.lastIndexOf(':');
            if (lastColon > 0) {
                String parendName = orgName.substring(0, lastColon);
                long shapeId = (long) shapeIdMap.get(orgName);
                long parentId = (long) shapeIdMap.get(parendName);
                Shape connector1 = new Shape();
                long connecter1Id = diagram.addShape(connector1, connectorMaster, pageNumber);
                diagram.getPages().get(pageNumber).connectShapesViaConnector(parentId, ConnectionPointPlace.RIGHT,
                        shapeId, ConnectionPointPlace.LEFT, connecter1Id);
            }
        }

        //блок-схема автоматического макета
        LayoutOptions flowChartOptions = new LayoutOptions();
        flowChartOptions.setLayoutStyle(LayoutStyle.FLOW_CHART);
        flowChartOptions.setDirection(LayoutDirection.TOP_TO_BOTTOM);
        flowChartOptions.setEnlargePage(true);

        diagram.getPages().get(pageNumber).layout(flowChartOptions);

        // Сохранить диаграмму
        diagram.save(dataDir + "DrawFlowChart_java.vsdx", SaveFileFormat.VSDX);
    }
}
