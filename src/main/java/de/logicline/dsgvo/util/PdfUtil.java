package de.logicline.dsgvo.util;

import java.io.*;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfUtil {
//TODO everything is hardcoded for testing purpose, once we get the final template this will change
    public static InputStream createPdf() {
        PDDocument document = new PDDocument();

        //Creating a new page and adding it to the document
        PDPage page = new PDPage();
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA_BOLD_OBLIQUE;

        try {
            //ContentStream holds the content
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            //Set the starting offset for contentStream and font
            contentStream.beginText();
            contentStream.setFont(font, 14);
            //Text offset
            contentStream.newLineAtOffset(100, 500);

            //Display the mentioned text at the offset specified
            contentStream.showText("SAMPLE ADV DOCUMENT");
            contentStream.endText();
            

            //Closing the contentStream
            contentStream.close();
            //Location for saving the pdf file
           // document.save("c://JavaInterviewPoint//Hello.pdf");
            //Closing the document
          //  document.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            return in;



        } catch (IOException ie) {
            ie.printStackTrace();
        }
       return null;
    }
}
