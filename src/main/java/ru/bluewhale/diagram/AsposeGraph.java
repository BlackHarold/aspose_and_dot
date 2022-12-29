package ru.bluewhale.diagram;

import com.aspose.diagram.*;
import com.aspose.diagram.examples.Utils;
import com.aspose.pdf.*;
import com.aspose.pdf.Color;
import com.aspose.pdf.Page;
import com.aspose.pdf.drawing.Circle;
import com.aspose.pdf.drawing.Graph;

public class AsposeGraph {
    public static void main(String[] args) throws Exception {
        chartDiagram();
    }

    private static void chartDiagram() throws Exception {
        String dataDir = Utils.getDataDir(AsposeGraph.class);

        // Создать новую диаграмму
        int pageNumber = 0;
        String rectangleMaster = "Process", decisionMaster = "Decision", connectorMaster = "Dynamic connector";

        Diagram diagram = new Diagram(dataDir + "XANFLOWCHARTNEW.vss");

        double width = 1, height = 1, pinX = 4, pinY = 10;
        long process1 = diagram.addShape(pinX, pinY, width, height, rectangleMaster, 0);
        Shape processShape1 = diagram.getPages().getPage(pageNumber).getShapes().getShape(process1);

        processShape1.getText().getValue().add(new Txt("PROCESS_1"));
        processShape1.setName("PROCESS_1");
        processShape1.getXForm().getLocPinX().getUfe().setF("Width*0.5");
        processShape1.getXForm().getLocPinY().getUfe().setF("Height*0.5");

        pinY = pinY - 2;

        long decision1 = diagram.addShape(pinX, pinY, width, height, decisionMaster, 0);
        Shape decisionShape1 = diagram.getPages().getPage(pageNumber).getShapes().getShape(decision1);

        decisionShape1.getText().getValue().add(new Txt("DECISION"));
        decisionShape1.setName("DECISION");
        decisionShape1.getXForm().getLocPinX().getUfe().setF("Width*0.5");
        decisionShape1.getXForm().getLocPinY().getUfe().setF("Height*0.5");

        pinY = pinY - 2;
        long process2 = diagram.addShape(pinX, pinY, width, height, rectangleMaster, 0);
        Shape processShape2 = diagram.getPages().getPage(pageNumber).getShapes().getShape(process2);

        processShape2.getText().getValue().add(new Txt("PROCESS_2"));
        processShape2.setName("PROCESS_2");
        processShape2.getXForm().getLocPinX().getUfe().setF("Width*0.5");
        processShape2.getXForm().getLocPinY().getUfe().setF("Height*0.5");

        pinY = pinY - 2;
        long process3 = diagram.addShape(pinX, pinY, width, height, rectangleMaster, 0);
        Shape processShape3 = diagram.getPages().getPage(pageNumber).getShapes().getShape(process3);

        processShape3.getText().getValue().add(new Txt("PROCESS_3"));
        processShape3.setName("PROCESS_3");
        processShape3.getXForm().getLocPinX().getUfe().setF("Width*0.5");
        processShape3.getXForm().getLocPinY().getUfe().setF("Height*0.5");

        pinY = pinY - 2;
        long process4 = diagram.addShape(pinX, pinY, width, height, rectangleMaster, 0);
        Shape processShape4 = diagram.getPages().getPage(pageNumber).getShapes().getShape(process4);

        processShape4.getText().getValue().add(new Txt("PROCESS_4"));
        processShape4.setName("PROCESS_4");
        processShape4.getXForm().getLocPinX().getUfe().setF("Width*0.5");
        processShape4.getXForm().getLocPinY().getUfe().setF("Height*0.5");

        long connecterId = diagram.addShape(new Shape(), connectorMaster, 0);
        diagram.getPages().getPage(pageNumber).connectShapesViaConnector(/*from*/process1, ConnectionPointPlace.BOTTOM,
                /*to*/decision1, ConnectionPointPlace.TOP, connecterId);

        long connecterId1 = diagram.addShape(new Shape(), connectorMaster, 0);
        Shape connector1 = diagram.getPages().getPage(pageNumber).getShapes().getShape(connecterId1);
        connector1.setName("conn1");
        connector1.getText().getValue().add(new Txt("conn1"));
        diagram.getPages().getPage(pageNumber).connectShapesViaConnector(decision1, ConnectionPointPlace.BOTTOM,
                process2, ConnectionPointPlace.TOP, connecterId1);

        long connecterId2 = diagram.addShape(new Shape(), connectorMaster, 0);
        diagram.getPages().getPage(pageNumber).connectShapesViaConnector(process2, ConnectionPointPlace.BOTTOM,
                process3, ConnectionPointPlace.TOP, connecterId2);

        long connecterId3 = diagram.addShape(new Shape(), connectorMaster, 0);
        diagram.getPages().getPage(pageNumber).connectShapesViaConnector(process3, ConnectionPointPlace.BOTTOM,
                process4, ConnectionPointPlace.TOP, connecterId3);

        long connecterId4 = diagram.addShape(new Shape(), connectorMaster, 0);
        diagram.getPages().getPage(pageNumber).connectShapesViaConnector(decision1, ConnectionPointPlace.RIGHT,
                process4, ConnectionPointPlace.TOP, connecterId4);

        // Установить параметры автоматического макета
        LayoutOptions layoutOptions = new LayoutOptions();

        // Метод
        layoutOptions.setLayoutStyle(LayoutStyle.FLOW_CHART);
        layoutOptions.setDirection(LayoutDirection.BOTTOM_TO_TOP);
        diagram.layout(layoutOptions);

        DiagramSaveOptions options = new DiagramSaveOptions(SaveFileFormat.VSDX);
        diagram.save(dataDir + "sample.vsdx", options);
    }
}
