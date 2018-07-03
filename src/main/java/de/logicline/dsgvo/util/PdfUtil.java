package de.logicline.dsgvo.util;


import java.awt.*;
import java.io.*;
import java.util.Locale;


import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.utils.PDStreamUtils;

import de.logicline.dsgvo.model.Customer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;

@Component
public class PdfUtil {


    @Autowired
    private MessageSource messageSource;


    public InputStream createPdf(Customer customer) throws Exception {
        try {

            PDDocument document = PDDocument.load(new File(getClass().getResource("/pdf/Template_logicline_de.pdf").getFile()));
            PDDocument document2 = PDDocument.load(new File(getClass().getResource("/pdf/Template_logicline_2_de.pdf").getFile()));

            document.addPage(getFirstPage(customer));
            document.addPage(getSecondPage(customer));
            document.addPage(getThirdPage(customer));
            document.addPage(getFourthPage(customer));
            document.addPage(document2.getPage(0));


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

    private PDPage getFirstPage(Customer customer) throws Exception {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        Locale locale = Locale.ENGLISH;

        Image image = new Image(ImageIO.read(new File(getClass().getResource("/pdf/llogo.png").getFile())));
        // Logo
        float imageWidth = 120;
        float margin = 50;
        image = image.scaleByWidth(imageWidth);
        image.draw(document, contentStream, page.getMediaBox().getWidth() - (3 * margin), page.getMediaBox().getHeight() - (margin));

        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s1", null, locale),  PDType1Font.HELVETICA, 8.0f, 50, 725f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.provisions", null, locale), PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s2", null, locale), PDType1Font.HELVETICA, 8.0f, 50, 710f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s3", null, locale), PDType1Font.HELVETICA, 8.0f, 50, 695f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s4", null, locale),  PDType1Font.HELVETICA, 8.0f, 50, 680f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s5", null, locale), PDType1Font.HELVETICA, 8.0f, 50, 665f, Color.BLACK);


        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;

        float yPosition = 550;

        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        Row<PDPage> headerRow = table.createRow(15f);
        headerRow.setHeaderRow(true);
        Cell<PDPage> cell = headerRow.createCell(50, messageSource.getMessage("firstPage.customer", null, locale));
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, messageSource.getMessage("firstPage.contractor", null, locale));
        headerRow.setHeaderRow(true);
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        Row<PDPage> row = table.createRow(12);
        row.createCell(50, messageSource.getMessage("firstPage.date", null, locale)+"12/1/2018");
        row.createCell(50, messageSource.getMessage("firstPage.date", null, locale)+" 12/1/2018");
        row = table.createRow(12);
        row.createCell(50, messageSource.getMessage("salutation", null, locale) + customer.getFirstName() + " " + customer.getLastName());
        row.createCell(50, messageSource.getMessage("salutation", null, locale) +" Anoop");
        row = table.createRow(12);
        row.createCell(50, messageSource.getMessage("designation", null, locale)+" CEO");
        row.createCell(50, messageSource.getMessage("designation", null, locale)+" Dev");
        row = table.createRow(12);
        row.createCell(50, messageSource.getMessage("signature", null, locale));
        row.createCell(50,  messageSource.getMessage("signature", null, locale));
        table.draw();
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.plans", null, locale), PDType1Font.HELVETICA, 12.0f, 50f, 420f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.planContent", null, locale), PDType1Font.HELVETICA, 8.0f, 50f, 405f, Color.BLACK);
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
        cell.setHeaderCell(true);
        headerRow.setHeaderRow(true);
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        row = table.createRow(12);
        row.createCell(50, "Sample data");
        row.createCell(50, "Sample data");
        table.draw();


//footer
        PDStreamUtils.write(contentStream, "www.logicline.de", PDType1Font.HELVETICA, 7.0f, 400f, 50f, Color.RED);
        PDStreamUtils.write(contentStream, ".info@logicline.de", PDType1Font.HELVETICA, 7.0f, 455f, 50f, Color.BLACK);
        contentStream.close();
        return page;
    }

    private PDPage getSecondPage(Customer customer) throws Exception {
        PDDocument document = new PDDocument();
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

        BaseTable table = new BaseTable(720f, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        Row<PDPage> headerRow = table.createRow(15f);
        Cell<PDPage> cell = headerRow.createCell(50, "Name und Vorname");
        headerRow.setHeaderRow(true);
        cell.setHeaderCell(true);
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
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
        PDStreamUtils.write(contentStream, "Ansprechpartner Datenschutz des Auftraggebers", PDType1Font.HELVETICA, 12.0f, 50f, 600f, Color.BLACK);

        //    PDStreamUtils.write(contentStream, "Ansprechpartner Datenschutz des Auftraggebers", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);
        //

        table = new BaseTable(580, 240, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        headerRow = table.createRow(15f);
        cell = headerRow.createCell(50, "Name und Vorname");
        headerRow.setHeaderRow(true);
        cell.setHeaderCell(true);
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        row = table.createRow(12);
        row.createCell(50, "Sample data");
        row.createCell(50, "Sample data");
        table.draw();

        PDStreamUtils.write(contentStream, "Ansprechpartner Datenschutz des Auftragnehmers", PDType1Font.HELVETICA, 12.0f, 50, 500f, Color.BLACK);
        //

        table = new BaseTable(485, 240, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        headerRow = table.createRow(15f);
        cell = headerRow.createCell(50, "Name und Vorname");
        headerRow.setHeaderRow(true);
        cell.setHeaderCell(true);
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        row = table.createRow(12);
        row.createCell(50, "Sample data");
        row.createCell(50, "Sample data");
        table.draw();

        PDStreamUtils.write(contentStream, "Anlage 2: Gegenstand der Datenverarbeitung", PDType1Font.HELVETICA, 12.0f, 50, 270f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Gegenstand und Dauer der Datenverarbeitung sind wie folgt geplant:", PDType1Font.HELVETICA, 12.0f, 50, 255, Color.BLACK);
        //

        table = new BaseTable(240, 240, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        headerRow = table.createRow(15f);
        cell = headerRow.createCell(50, "Name und Vorname");
        cell.setFillColor(Color.lightGray);
        headerRow.setHeaderRow(true);
        cell.setHeaderCell(true);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(50, "Funktion");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        row = table.createRow(12);
        row.createCell(50, "Sample data");
        row.createCell(50, "Sample data");
        table.draw();
        PDStreamUtils.write(contentStream, "Ergänzend wird auf die Leistungsbeschreibung des Hauptvertrags verwiesen.", PDType1Font.HELVETICA, 12.0f, 50, 190f, Color.BLACK);
//footer
        PDStreamUtils.write(contentStream, "www.logicline.de", PDType1Font.HELVETICA, 8.0f, 500f, 50f, Color.RED);
        contentStream.close();


        return page;
    }

    private PDPage getThirdPage(Customer customer) throws Exception {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        Image image = new Image(ImageIO.read(new File(getClass().getResource("/pdf/llogo.png").getFile())));
        // Logo
        float imageWidth = 120;
        float margin = 50;
        image = image.scaleByWidth(imageWidth);
        image.draw(document, contentStream, page.getMediaBox().getWidth() - (3 * margin), page.getMediaBox().getHeight() - (margin));


        PDStreamUtils.write(contentStream, "Art und Zweck der Datenverarbeitung wie folgt geplant:", PDType1Font.HELVETICA, 8.0f, 50, 741.88f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 720.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 705.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 690.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Ergänzend wird auf die Leistungsbeschreibung des Hauptvertrags verwiesen.", PDType1Font.HELVETICA, 8.0f, 50, 675.0f, Color.BLACK);

        PDStreamUtils.write(contentStream, "Die Datenverarbeitung umfasst die folgenden Arten personenbezogener Daten:", PDType1Font.HELVETICA, 8.0f, 50, 615.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 585.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 570.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 555.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Die Verarbeitung umfasst die folgenden Kategorien betroffener Personen:", PDType1Font.HELVETICA, 8.0f, 50, 540.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 525.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 510.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "___________________________________________________________________________", PDType1Font.HELVETICA, 8.0f, 50, 495.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "www.logicline.de", PDType1Font.HELVETICA, 8.0f, 500f, 50f, Color.RED);
        contentStream.close();

        return page;

    }

    private PDPage getFourthPage(Customer customer) throws Exception {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        Image image = new Image(ImageIO.read(new File(getClass().getResource("/pdf/llogo.png").getFile())));
        // Logo
        float imageWidth = 120;
        float margin = 50;
        float bottomMargin = 70;
        boolean drawContent = true;
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
        image = image.scaleByWidth(imageWidth);
        image.draw(document, contentStream, page.getMediaBox().getWidth() - (3 * margin), page.getMediaBox().getHeight() - (margin));

        PDStreamUtils.write(contentStream, "Anlage 3: Unterauftragnehmer", PDType1Font.HELVETICA, 12.0f, 50, 741.88f, Color.BLACK);

        BaseTable table = new BaseTable(715, 240, bottomMargin, tableWidth, margin, document, page, true, drawContent);


        Row<PDPage> headerRow = table.createRow(15f);
        Cell<PDPage> cell = headerRow.createCell(40, "Vollständige Firmierung des Unterauftragnehmers");
        cell.setFillColor(Color.lightGray);
        headerRow.setHeaderRow(true);
        cell.setHeaderCell(true);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(20, "Anschrift");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(20, "Land");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        cell = headerRow.createCell(20, "Beschreibung der Leistung");
        cell.setFillColor(Color.lightGray);
        table.addHeaderRow(headerRow);
        Row<PDPage> row = table.createRow(12);
        row.createCell(40, "Sample data");
        row.createCell(20, "Sample data");
        row.createCell(20, "Sample data");
        row.createCell(20, "Sample data");
        table.draw();
        PDStreamUtils.write(contentStream, "www.logicline.de", PDType1Font.HELVETICA, 8.0f, 500f, 50f, Color.RED);
        contentStream.close();
        return page;
    }
}
