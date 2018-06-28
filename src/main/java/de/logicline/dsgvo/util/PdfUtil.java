package de.logicline.dsgvo.util;


import java.awt.*;
import java.io.*;


import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.utils.PDStreamUtils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.imageio.ImageIO;

public class PdfUtil {
    //TODO everything is hardcoded for testing purpose, once we get the final template this will change
    public InputStream createPdf() throws Exception {
        try {

            // PDDocument document = PDDocument.load(new File(getClass().getResource("/pdf/Template_logicline_de.pdf").getFile()));

            //Creating a new page and adding it to the document
       /* PDPage page = new PDPage();
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA_BOLD_OBLIQUE;


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
           // document.save("c://path//Hello.pdf");
            //Closing the document
          //  document.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            return in;*/
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            //PDPage page = (PDPage) document.getDocumentCatalog().getPages().get(13);
            //            PDFont font = PDType1Font.HELVETICA;

            Image image = new Image(ImageIO.read(new File(getClass().getResource("/pdf/llogo.png").getFile())));
            // Logo
            float imageWidth = 120;
            float margin = 50;
            image = image.scaleByWidth(imageWidth);
            image.draw(document, contentStream, page.getMediaBox().getWidth() - (3 * margin), page.getMediaBox().getHeight() - (margin));


            PDStreamUtils.write(contentStream, "4 Schlussbestimmungen", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);
            PDStreamUtils.write(contentStream, "Wenn und soweit Vorgaben der Aufsichtsbehörden und/oder zusätzliche gesetzliche Vorgaben die Änderung von ", PDType1Font.HELVETICA, 8.0f, 50, 725f, Color.BLACK);
            PDStreamUtils.write(contentStream, "Bestimmungen der DSV und/oder der zugehörigen Anlagen erforderlich machen, sind die Parteien verpflichtet, an ", PDType1Font.HELVETICA, 8.0f, 50, 710f, Color.BLACK);
            PDStreamUtils.write(contentStream, "der Umsetzung der Anforderungen und der Aufnahme in die DSV mitzuwirken. Vorgaben der für die vom Auftrag ", PDType1Font.HELVETICA, 8.0f, 50, 695f, Color.BLACK);
            PDStreamUtils.write(contentStream, "umfasste Datenverarbeitung zuständigen Aufsichtsbehörde oder einer sonstigen zuständigen offiziellen Stelle sind ", PDType1Font.HELVETICA, 8.0f, 50, 680f, Color.BLACK);
            PDStreamUtils.write(contentStream, "dabei als verbindlich zu betrachten", PDType1Font.HELVETICA, 8.0f, 50, 665f, Color.BLACK);



            float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

            boolean drawContent = true;
            float yStart = yStartNewPage;
            float bottomMargin = 70;

            float yPosition = 550;

            BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);


            Row<PDPage> headerRow = table.createRow(15f);
            Cell<PDPage> cell = headerRow.createCell(50, "Auftraggeber");
            cell.setFillColor(Color.lightGray);
            table.addHeaderRow(headerRow);
            cell = headerRow.createCell(50, "Auftragnehmer");
            cell.setFillColor(Color.lightGray);
            table.addHeaderRow(headerRow);
            Row<PDPage> row = table.createRow(12);
            row.createCell(50, "Datum: 12/1/2018");
            row.createCell(50, "Datum: 12/1/2018");
            row = table.createRow(12);
            row.createCell(50, "Name: Herr Thomas");
            row.createCell(50, "Name: Herr Anoop");
            row = table.createRow(12);
            row.createCell(50, "Funktion: CEO");
            row.createCell(50, "Funktion: Dev");
            row = table.createRow(12);
            row.createCell(50, "Unterschrift:");
            row.createCell(50, "Unterschrift:");
            table.draw();
            PDStreamUtils.write(contentStream, "Übersicht Anlagen", PDType1Font.HELVETICA, 12.0f, 50f, 420f, Color.BLACK);
            PDStreamUtils.write(contentStream, "Anlage Inhalt", PDType1Font.HELVETICA, 8.0f, 50f, 405f, Color.BLACK);
            PDStreamUtils.write(contentStream, "Anlage 1 Ansprechpartner und Verantwortliche", PDType1Font.HELVETICA, 8.0f, 50f, 390f, Color.BLACK);
            PDStreamUtils.write(contentStream, "Anlage 2 Gegenstand der Datenverarbeitung", PDType1Font.HELVETICA, 8.0f, 50f, 375f, Color.BLACK);
            PDStreamUtils.write(contentStream, "Anlage 3 Unterauftragnehmer", PDType1Font.HELVETICA, 8.0f, 50f, 350f, Color.BLACK);
            PDStreamUtils.write(contentStream, "Anlage 4 Technische und organisatorische Maßnahmen", PDType1Font.HELVETICA, 8.0f, 50f, 335f, Color.BLACK);

            PDStreamUtils.write(contentStream, "Anlage 1: Autorisierte Personen", PDType1Font.HELVETICA, 12.0f, 50f, 300f, Color.BLACK);
            PDStreamUtils.write(contentStream, "Autorisierte Personen des Auftraggebers", PDType1Font.HELVETICA, 8.0f, 50f, 260f, Color.BLACK);
            //

            table = new BaseTable(240, 240, bottomMargin, tableWidth, margin, document, page, true, drawContent);


            headerRow = table.createRow(15f);
            cell = headerRow.createCell(50, "Name und Vorname");
            cell.setFillColor(Color.WHITE);
            table.addHeaderRow(headerRow);
            cell = headerRow.createCell(50, "Funktion");
            cell.setFillColor(Color.lightGray);
            table.addHeaderRow(headerRow);
            row = table.createRow(12);
            row.createCell(50, "Sample data");
            row.createCell(50, "Sample data");
            table.draw();


//footer
            PDStreamUtils.write(contentStream, "www.logicline.de", PDType1Font.HELVETICA, 8.0f, 500f, 50f, Color.RED);
            document.addPage(page);
            document.addPage(getSecondPage(document));
            contentStream.close();


            /*contentStream.moveTo(500,500);
            contentStream.beginText();contentStream.newLine();
            contentStream.setFont(font, 9);

            contentStream.showText("edited sample");
            //contentStream.drawString("Edited");
            contentStream.endText();
            */
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

    private PDPage getSecondPage(PDDocument document) throws Exception{
        PDPage page = new PDPage(PDRectangle.A4);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        Image image = new Image(ImageIO.read(new File(getClass().getResource("/pdf/llogo.png").getFile())));
        // Logo
        float imageWidth = 120;
        float margin = 50;
        image = image.scaleByWidth(imageWidth);
        image.draw(document, contentStream, page.getMediaBox().getWidth() - (3 * margin), page.getMediaBox().getHeight() - (margin));


        PDStreamUtils.write(contentStream, "Autorisierte Personen des Auftragnehmers", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);



        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;

        float yPosition = 550;

        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        Row<PDPage> headerRow = table.createRow(15f);
        Cell<PDPage> cell = headerRow.createCell(50, "Name und Vorname");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Kontaktdaten");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        Row<PDPage> row = table.createRow(12);
        row.createCell(50, "Datum: 12/1/2018");
        row.createCell(50, "Datum: 12/1/2018");
        row = table.createRow(12);
        row.createCell(50, "Name: Herr Thomas");
        row.createCell(50, "Name: Herr Anoop");
        row = table.createRow(12);
        row.createCell(50, "Funktion: CEO");
        row.createCell(50, "Funktion: Dev");
        row = table.createRow(12);
        row.createCell(50, "Unterschrift:");
        row.createCell(50, "Unterschrift:");
        table.draw();
        PDStreamUtils.write(contentStream, "Übersicht Anlagen", PDType1Font.HELVETICA, 12.0f, 50f, 420f, Color.BLACK);

        PDStreamUtils.write(contentStream, "Ansprechpartner Datenschutz des Auftraggebers", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);
        //

        table = new BaseTable(240, 240, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        headerRow = table.createRow(15f);
        cell = headerRow.createCell(50, "Name und Vorname");
        cell.setFillColor(Color.WHITE);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        row = table.createRow(12);
        row.createCell(50, "Sample data");
        row.createCell(50, "Sample data");
        table.draw();

        PDStreamUtils.write(contentStream, "Ansprechpartner Datenschutz des Auftragnehmers", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);
        //

        table = new BaseTable(240, 240, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        headerRow = table.createRow(15f);
        cell = headerRow.createCell(50, "Name und Vorname");
        cell.setFillColor(Color.WHITE);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        row = table.createRow(12);
        row.createCell(50, "Sample data");
        row.createCell(50, "Sample data");
        table.draw();

        PDStreamUtils.write(contentStream, "Anlage 2: Gegenstand der Datenverarbeitung", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Gegenstand und Dauer der Datenverarbeitung sind wie folgt geplant:", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);
        //

        table = new BaseTable(240, 240, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        headerRow = table.createRow(15f);
        cell = headerRow.createCell(50, "Name und Vorname");
        cell.setFillColor(Color.WHITE);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        row = table.createRow(12);
        row.createCell(50, "Sample data");
        row.createCell(50, "Sample data");
        table.draw();
        PDStreamUtils.write(contentStream, "Ergänzend wird auf die Leistungsbeschreibung des Hauptvertrags verwiesen.", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);
//footer
        PDStreamUtils.write(contentStream, "www.logicline.de", PDType1Font.HELVETICA, 8.0f, 500f, 50f, Color.RED);

        return page;
    }
}
