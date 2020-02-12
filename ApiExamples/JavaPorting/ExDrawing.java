// Copyright (c) 2001-2020 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

package ApiExamples;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.Shape;
import com.aspose.words.NodeType;
import com.aspose.words.Stroke;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.Assert;
import java.awt.Color;
import com.aspose.ms.System.IO.File;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ShapeType;
import com.aspose.words.GroupShape;
import com.aspose.ms.System.msConsole;
import com.aspose.words.DocumentVisitor;
import com.aspose.words.VisitorAction;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.words.LayoutFlow;
import com.aspose.words.Paragraph;
import java.util.ArrayList;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.words.ImageData;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.words.ImageSize;


@Test
public class ExDrawing extends ApiExampleBase
{
            
    @Test
    public void strokePattern() throws Exception
    {
        //ExStart
        //ExFor:Stroke.Color2
        //ExFor:Stroke.ImageBytes
        //ExSummary:Shows how to process shape stroke features.
        // Open a document which contains a rectangle with a thick, two-tone-patterned outline
        Document doc = new Document(getMyDir() + "Shape stroke pattern border.docx");

        // Get the first shape's stroke
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        Stroke s = shape.getStroke();

        // Every stroke will have a Color attribute, but only strokes from older Word versions have a Color2 attribute,
        // since the two-tone pattern line feature which requires the Color2 attribute is no longer supported
        msAssert.areEqual(new Color((128), (0), (0), (255)), s.getColor());
        msAssert.areEqual(new Color((255), (255), (0), (255)), s.getColor2());

        // This attribute contains the image data for the pattern, which we can save to our local file system
        Assert.assertNotNull(s.getImageBytes());
        File.writeAllBytes(getArtifactsDir() + "Drawing.StrokePattern.png", s.getImageBytes());
        //ExEnd
    }

    //ExStart
    //ExFor:DocumentVisitor.VisitShapeEnd(Shape)
    //ExFor:DocumentVisitor.VisitShapeStart(Shape)
    //ExFor:DocumentVisitor.VisitGroupShapeEnd(GroupShape)
    //ExFor:DocumentVisitor.VisitGroupShapeStart(GroupShape)
    //ExFor:Drawing.GroupShape
    //ExFor:Drawing.GroupShape.#ctor(DocumentBase)
    //ExFor:Drawing.GroupShape.#ctor(DocumentBase,Drawing.ShapeMarkupLanguage)
    //ExFor:Drawing.GroupShape.Accept(DocumentVisitor)
    //ExFor:ShapeBase.IsGroup
    //ExFor:ShapeBase.ShapeType
    //ExSummary:Shows how to create a group of shapes, and let it accept a visitor
    @Test //ExSkip
    public void groupOfShapes() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        
        // If you need to create "NonPrimitive" shapes, like SingleCornerSnipped, TopCornersSnipped, DiagonalCornersSnipped,
        // TopCornersOneRoundedOneSnipped, SingleCornerRounded, TopCornersRounded, DiagonalCornersRounded
        // please use DocumentBuilder.InsertShape methods
        Shape balloon = new Shape(doc, ShapeType.BALLOON);
        {
            balloon.setWidth(200.0); 
            balloon.setHeight(200.0);
            balloon.setStroke({ balloon.getStroke().setColor(Color.RED); });
        }

        Shape cube = new Shape(doc, ShapeType.CUBE);
        {
            cube.setWidth(100.0); 
            cube.setHeight(100.0);
            cube.setStroke({ cube.getStroke().setColor(Color.BLUE); });
        }

        GroupShape group = new GroupShape(doc);
        group.appendChild(balloon);
        group.appendChild(cube);

        Assert.assertTrue(group.isGroup());

        builder.insertNode(group);

        ShapeInfoPrinter printer = new ShapeInfoPrinter();
        group.accept(printer);

        System.out.println(printer.getText());
    }

    /// <summary>
    /// Visitor that prints shape group contents information to the console.
    /// </summary>
    public static class ShapeInfoPrinter extends DocumentVisitor
    {
        public ShapeInfoPrinter()
        {
            mBuilder = new StringBuilder();
        }

        public String getText()
        {
            return mBuilder.toString();
        }

        public /*override*/ /*VisitorAction*/int visitGroupShapeStart(GroupShape groupShape)
        {
            msStringBuilder.appendLine(mBuilder, "Shape group started:");
            return VisitorAction.CONTINUE;
        }

        public /*override*/ /*VisitorAction*/int visitGroupShapeEnd(GroupShape groupShape)
        {
            msStringBuilder.appendLine(mBuilder, "End of shape group");
            return VisitorAction.CONTINUE;
        }

        public /*override*/ /*VisitorAction*/int visitShapeStart(Shape shape)
        {
            msStringBuilder.appendLine(mBuilder, "\tShape - " + shape.getShapeType() + ":");
            msStringBuilder.appendLine(mBuilder, "\t\tWidth: " + shape.getWidth());
            msStringBuilder.appendLine(mBuilder, "\t\tHeight: " + shape.getHeight());
            msStringBuilder.appendLine(mBuilder, "\t\tStroke color: " + shape.getStroke().getColor());
            msStringBuilder.appendLine(mBuilder, "\t\tFill color: " + shape.getFill().getColor());
            return VisitorAction.CONTINUE;
        }

        public /*override*/ /*VisitorAction*/int visitShapeEnd(Shape shape)
        {
            msStringBuilder.appendLine(mBuilder, "\tEnd of shape");
            return VisitorAction.CONTINUE;
        }

        private /*final*/ StringBuilder mBuilder;
    }
    //ExEnd

    @Test
    public void textBox() throws Exception
    {
        //ExStart
        //ExFor:Drawing.LayoutFlow
        //ExSummary:Shows how to add text to a textbox and change its orientation
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape textbox = new Shape(doc, ShapeType.TEXT_BOX);
        {
            textbox.setWidth(100.0); 
            textbox.setHeight(100.0);
            textbox.setTextBox({ textbox.getTextBox().setLayoutFlow(LayoutFlow.BOTTOM_TO_TOP); });
        }
        
        textbox.appendChild(new Paragraph(doc));
        builder.insertNode(textbox);

        builder.moveTo(textbox.getFirstParagraph());
        builder.write("This text is flipped 90 degrees to the left.");
        
        doc.save(getArtifactsDir() + "Drawing.TextBox.docx");
        //ExEnd
    }

    @Test
    public void getDataFromImage() throws Exception
    {
        //ExStart
        //ExFor:ImageData.ImageBytes
        //ExFor:ImageData.ToByteArray
        //ExFor:ImageData.ToStream
        //ExSummary:Shows how to access raw image data in a shape's ImageData object.
        Document imgSourceDoc = new Document(getMyDir() + "Images.docx");

        // Images are stored as shapes
        // Get into the document's shape collection to verify that it contains 10 images
        ArrayList<Shape> shapes = imgSourceDoc.getChildNodes(NodeType.SHAPE, true).<Shape>Cast().ToList();
        msAssert.areEqual(10, shapes.size());

        // ToByteArray() returns the value of the ImageBytes property
        msAssert.areEqual(shapes.get(0).getImageData().getImageBytes(), shapes.get(0).getImageData().toByteArray());

        // Put the shape's image data into a stream
        // Then, put the image data from that stream into another stream which creates an image file in the local file system
        Stream imgStream = shapes.get(0).getImageData().toStreamInternal();
        try /*JAVA: was using*/
        {
            FileStream outStream = new FileStream(getArtifactsDir() + "Drawing.GetDataFromImage.png", FileMode.CREATE);
            try /*JAVA: was using*/
            {
                imgStream.copyTo(outStream);
            }
            finally { if (outStream != null) outStream.close(); }
        }
        finally { if (imgStream != null) imgStream.close(); }        
        //ExEnd
    }

    @Test
    public void imageData() throws Exception
    {
        //ExStart
        //ExFor:ImageData.BiLevel
        //ExFor:ImageData.Borders
        //ExFor:ImageData.Brightness
        //ExFor:ImageData.ChromaKey
        //ExFor:ImageData.Contrast
        //ExFor:ImageData.CropBottom
        //ExFor:ImageData.CropLeft
        //ExFor:ImageData.CropRight
        //ExFor:ImageData.CropTop
        //ExFor:ImageData.GrayScale
        //ExFor:ImageData.IsLink
        //ExFor:ImageData.IsLinkOnly
        //ExFor:ImageData.Title
        //ExSummary:Shows how to edit images using the ImageData attribute.
        // Open a document that contains images
        Document imgSourceDoc = new Document(getMyDir() + "Images.docx");

        Shape sourceShape = (Shape)imgSourceDoc.getChildNodes(NodeType.SHAPE, true).get(0);
        
        Document dstDoc = new Document();

        // Import a shape from the source document and append it to the first paragraph, effectively cloning it
        Shape importedShape = (Shape)dstDoc.importNode(sourceShape, true);
        dstDoc.getFirstSection().getBody().getFirstParagraph().appendChild(importedShape);

        // Get the ImageData of the imported shape
        ImageData imageData = importedShape.getImageData();
        imageData.setTitle("Imported Image");

        // If an image appears to have no borders, its ImageData object will still have them, but in an unspecified color
        msAssert.areEqual(4, imageData.getBorders().getCount());
        msAssert.areEqual(msColor.Empty, imageData.getBorders().get(0).getColor());

        Assert.assertTrue(imageData.hasImage());

        // This image is not linked to a shape or to an image in the file system
        Assert.assertFalse(imageData.isLink());
        Assert.assertFalse(imageData.isLinkOnly());

        // Brightness and contrast are defined on a 0-1 scale, with 0.5 being the default value
        imageData.setBrightness(0.8d);
        imageData.setContrast(1.0d);

        // Our image will have a lot of white now that we've changed the brightness and contrast like that
        // We can treat white as transparent with the following attribute
        imageData.setChromaKey(Color.WHITE);

        // Import the source shape again, set it to black and white
        importedShape = (Shape)dstDoc.importNode(sourceShape, true);
        dstDoc.getFirstSection().getBody().getFirstParagraph().appendChild(importedShape);

        importedShape.getImageData().setGrayScale(true);

        // Import the source shape again to create a third image, and set it to BiLevel
        // Unlike greyscale, which preserves the brightness of the original colors,
        // BiLevel sets every pixel to either black or white, whichever is closer to the original color
        importedShape = (Shape)dstDoc.importNode(sourceShape, true);
        dstDoc.getFirstSection().getBody().getFirstParagraph().appendChild(importedShape);

        importedShape.getImageData().setBiLevel(true);

        // Cropping is determined on a 0-1 scale
        // Cropping a side by 0.3 will crop 30% of the image out at that side
        importedShape.getImageData().setCropBottom(0.3d);
        importedShape.getImageData().setCropLeft(0.3d);
        importedShape.getImageData().setCropTop(0.3d);
        importedShape.getImageData().setCropRight(0.3d);

        dstDoc.save(getArtifactsDir() + "Drawing.ImageData.docx");
        //ExEnd
    }

    @Test
    public void imageSize() throws Exception
    {
        //ExStart
        //ExFor:ImageSize.HeightPixels
        //ExFor:ImageSize.HorizontalResolution
        //ExFor:ImageSize.VerticalResolution
        //ExFor:ImageSize.WidthPixels
        //ExSummary:Shows how to access and use a shape's ImageSize property.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a shape into the document which contains an image taken from our local file system
        Shape shape = builder.insertImage(getImageDir() + "Logo.jpg");

        // If the shape contains an image, its ImageData property will be valid, and it will contain an ImageSize object
        ImageSize imageSize = shape.getImageData().getImageSize(); 

        // The ImageSize object contains raw information about the image within the shape
        msAssert.areEqual(400, imageSize.getHeightPixels());
        msAssert.areEqual(400, imageSize.getWidthPixels());

		final double DELTA = 0.05;
        Assert.assertEquals(95.98d, imageSize.getHorizontalResolution(), DELTA);
        Assert.assertEquals(95.98d, imageSize.getVerticalResolution(), DELTA);

        // These values are read-only
        // If we want to transform the image, we need to change the size of the shape that contains it
        // We can still use values within ImageSize as a reference
        // In the example below, we will get the shape to display the image in twice its original size
        shape.setWidth(imageSize.getWidthPoints() * 2.0);
        shape.setHeight(imageSize.getHeightPoints() * 2.0);

        doc.save(getArtifactsDir() + "Drawing.ImageSize.docx");
        //ExEnd
    }
}
