//package ru.bluewhale.diagram;
//
//import com.aspose.diagram.Diagram;
//import com.aspose.diagram.Page;
//
//public class Peggy {
//    // Directory path for input and output documents.
//    String dirPath = "D:\\Download\\";
//
//    // Load the template Microsoft Visio file in Aspose Diagram object.
//    Diagram dg = new Diagram(dirPath + "templateManipulateVisioFileUsingAsposeDiagram.vsdx");
//
//    // Access the Page-1 of the template Visio file.
//    Page pg = dg.getPages().getPage("Page-1");
//
//// Add Master Shapes into Aspose Diagram object from SampleMasterShapes Stencil.
//dg.addMaster(dirPath + "SampleMasterShapes.vssx", "Rectangle");
//dg.addMaster(dirPath + "SampleMasterShapes.vssx", "Ellipse");
//dg.addMaster(dirPath + "SampleMasterShapes.vssx", "Hexagon");
//dg.addMaster(dirPath + "SampleMasterShapes.vssx", "Dynamic connector");
//
//    /*--------------------------------------------------------
//             START: Add Shapes
//    --------------------------------------------------------*/
//// Add Rectangle shape from master Rectangle shape and assign it name.
//    long shId = dg.addShape(2.70, 7.54, 1.5, 1, "Rectangle", 0);
//pg.getShapes().getShape(shId).setName("shpRect");
//
//// Add Ellipse shape from master Ellipse shape and assign it name.
//    shId = dg.addShape(2.70, 5.1, 2.25, 1.5, "Ellipse", 0);
//pg.getShapes().getShape(shId).setName("shpEllipse");
//
//// Add Hexagon shape from master Hexagon shape and assign it name.
//    shId = dg.addShape(6.3, 5.1, 1.5, 1.5, "Hexagon", 0);
//pg.getShapes().getShape(shId).setName("shpHexagon");
//
//// Add first "Dynamic connector" shape from master "Dynamic connector" shape and assign it name.
//    shId = dg.addShape(4.3, 3.1, 1.5, 1.5, "Dynamic connector", 0);
//pg.getShapes().getShape(shId).setName("shpDynConnect1");
//
//// Add second "Dynamic connector" shape from master "Dynamic connector" shape and assign it name.
//    shId = dg.addShape(4.3, 3.1, 1.5, 1.5, "Dynamic connector", 0);
//pg.getShapes().getShape(shId).setName("shpDynConnect2");
///*--------------------------------------------------------
//		 END: Add Shapes
//--------------------------------------------------------*/
//
//    // Access all added shapes by their assigned names.
//    Shape shpRect = pg.getShapes().getShape("shpRect");
//    Shape shpEllipse = pg.getShapes().getShape("shpEllipse");
//    Shape shpHexagon = pg.getShapes().getShape("shpHexagon");
//    Shape shpDynConnect1 = pg.getShapes().getShape("shpDynConnect1");
//    Shape shpDynConnect2 = pg.getShapes().getShape("shpDynConnect2");
//
///*--------------------------------------------------------
//		 START: Fill and Format Rectangle Shape
//--------------------------------------------------------*/
//// Fill the Rectangle shape with Red color.
//shpRect.getFill().getFillForegnd().setValue("#ff0000");
//
//// Add text inside the shape.
//shpRect.getText().getValue().add(new Cp(0));
//shpRect.getText().getValue().add(new Txt("Structure"));
//
//// Format text of the shape.
//shpRect.getChars().add(new com.aspose.diagram.Char());
//shpRect.getChars().get(0).setIX(0);
//
//// Set text shape color as White.
//shpRect.getChars().get(0).getColor().setValue("#FFFFFF");
//
//// Set text style as Bold.
//shpRect.getChars().get(0).getStyle().setValue(StyleValue.BOLD);
//
//// Set text size as 0.2.
//shpRect.getChars().get(0).getSize().setValue(0.2);
///*--------------------------------------------------------
//		 END: Fill and Format Rectangle Shape
//--------------------------------------------------------*/
//
///*--------------------------------------------------------
//		 START: Fill and Format Ellipse Shape
//--------------------------------------------------------*/
//// Fill the Ellipse shape with Green color.
//shpEllipse.getFill().getFillForegnd().setValue("#00ff00");
//
//// Add text inside the shape.
//shpEllipse.getText().getValue().add(new Cp(0));
//shpEllipse.getText().getValue().add(new Txt("Role"));
//
//// Format text of the shape.
//shpEllipse.getChars().add(new com.aspose.diagram.Char());
//shpEllipse.getChars().get(0).setIX(0);
//
//// Set text shape color as Black.
//shpEllipse.getChars().get(0).getColor().setValue("#000000");
//
//// Set text style as Bold.
//shpEllipse.getChars().get(0).getStyle().setValue(StyleValue.BOLD);
//
//// Set text size as 0.2.
//shpEllipse.getChars().get(0).getSize().setValue(0.2);
///*--------------------------------------------------------
//		 END: Fill and Format Ellipse Shape
//--------------------------------------------------------*/
//
///*--------------------------------------------------------
//		 START: Fill and Format Hexagon Shape
//--------------------------------------------------------*/
//// Fill the Ellipse shape with Yellow color.
//shpHexagon.getFill().getFillForegnd().setValue("#ffff00");
//
//// Add text inside the shape.
//shpHexagon.getText().getValue().add(new Cp(0));
//shpHexagon.getText().getValue().add(new Txt("Incentive"));
//
//// Format text of the shape.
//shpHexagon.getChars().add(new com.aspose.diagram.Char());
//shpHexagon.getChars().get(0).setIX(0);
//
//// Set text shape color as Black.
//shpHexagon.getChars().get(0).getColor().setValue("#000000");
//
//// Set text style as Bold.
//shpHexagon.getChars().get(0).getStyle().setValue(StyleValue.BOLD);
//
//// Set text size as 0.2.
//shpHexagon.getChars().get(0).getSize().setValue(0.2);
///*--------------------------------------------------------
//		 END: Fill and Format Hexagon Shape
//--------------------------------------------------------*/
//
//// Connect Rectangle shape with Ellipse shape using first "Dynamic connector".
//pg.connectShapesViaConnector(shpRect, ConnectionPointPlace.BOTTOM, shpEllipse,
//    ConnectionPointPlace.TOP, shpDynConnect1);
//
//// Connect Hexagon shape with Ellipse shape using second "Dynamic connector".
//pg.connectShapesViaConnector(shpHexagon, ConnectionPointPlace.TOP, shpEllipse,
//    ConnectionPointPlace.RIGHT, shpDynConnect2);
//
//// Save the Aspose Diagram object into Microsoft Visio VSDX format.
//dg.save(dirPath + "outputManipulateVisioFileUsingAsposeDiagram.vsdx", SaveFileFormat.VSDX);
//}
