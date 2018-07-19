package de.logicline.adv.util;


import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.utils.PDStreamUtils;
import de.logicline.adv.model.dao.CustomerDao;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;


@Component
public class PdfUtil {
    //TODO most parts are  hardcoded for now, as we need to finalize the fields and their types from front-end.

    private MessageSource messageSource;
    private ResourceLoader resourceLoader;
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PATH_PREFIX = "classpath:pdf";
    private static final float MARGIN = 50;
    private  static final Logger LOGGER = LoggerFactory.getLogger(PdfUtil.class);
    
   /* @Value("${pdf.de.firstPart}")*/
    private static String templateDeFirstPart = "Template_logicline_de.pdf";

   /* @Value("${pdf.de.secondPart}")*/
    private static String templateDeSecondPart = "Template_logicline_2_de.pdf";


  /*  @Value("${logo.name}")*/
    private static String logoName = "llogo.png";

    private Image logoImage;

    @Autowired
    public  PdfUtil(MessageSource messageSource,ResourceLoader resourceLoader) throws Exception {
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
        Resource resource = resourceLoader.getResource(PATH_PREFIX + FILE_SEPARATOR +  logoName);
        InputStream fileAsInputStream = resource.getInputStream();

        logoImage = new Image(ImageIO.read(fileAsInputStream));
        // Logo
        float imageWidth = 120;
        logoImage = logoImage.scaleByWidth(imageWidth);
        fileAsInputStream.close();
    }

    public InputStream createPdf(CustomerDao customerDao) throws Exception {
        try {
            
            Resource resource = resourceLoader.getResource(PATH_PREFIX + FILE_SEPARATOR + templateDeFirstPart);
            InputStream fileAsInputStream = resource.getInputStream();
            PDDocument pdfFirstPart = PDDocument.load(fileAsInputStream);
            resource = resourceLoader.getResource(PATH_PREFIX + FILE_SEPARATOR + templateDeSecondPart);
            fileAsInputStream = resource.getInputStream();
            PDDocument pdfSecondPart = PDDocument.load(fileAsInputStream);

            pdfFirstPart.addPage(getFirstPage(customerDao));
            pdfFirstPart.addPage(getSecondPage(customerDao));
            pdfFirstPart.addPage(getThirdPage(customerDao));
            pdfFirstPart.addPage(getFourthPage(customerDao));
            pdfFirstPart.addPage(pdfSecondPart.getPage(0));


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pdfFirstPart.save(out);
            pdfFirstPart.close();
            pdfSecondPart.close();
            fileAsInputStream.close();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            return in;

        } catch (IOException ie) {
           LOGGER.debug("unable to create pdf template ",ie);
        }
        return null;
    }

    private void addHeaderAndFooter(PDDocument document,PDPage page, PDPageContentStream contentStream , PDFont font) throws Exception{
        // hard coded header logo and footer string for now
        logoImage.draw(document, contentStream, page.getMediaBox().getWidth() - (3 * MARGIN), page.getMediaBox().getHeight() - (MARGIN));
        PDStreamUtils.write(contentStream, "www.logicline.de", font, 7.0f, 400f, 50f, Color.RED);
        PDStreamUtils.write(contentStream, ". info@logicline.de", font, 7.0f, 455f, 50f, Color.BLACK);

    }

    private PDPage getFirstPage(CustomerDao customerDao) throws Exception {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        Locale locale = LocaleContextHolder.getLocale();
        PDType1Font font = PDType1Font.HELVETICA;

        addHeaderAndFooter(document,page,contentStream,font);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s1", null, locale), font, 8.0f, 50, 725f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.provisions", null, locale), font, 12.0f, 50, 741.88f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s2", null, locale), font, 8.0f, 50, 710f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s3", null, locale), font, 8.0f, 50, 695f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s4", null, locale), font, 8.0f, 50, 680f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.s5", null, locale), font, 8.0f, 50, 665f, Color.BLACK);


        float yStartNewPage = page.getMediaBox().getHeight() - (2 * MARGIN);

        float tableWidth = page.getMediaBox().getWidth() - (2 * MARGIN);

        boolean drawContent = true;
        float bottomMargin = 70;

        float yPosition = 550;

        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, MARGIN, document, page, true, drawContent);


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
        row.createCell(50, messageSource.getMessage("firstPage.date", null, locale) + "12/1/2018");
        row.createCell(50, messageSource.getMessage("firstPage.date", null, locale) + " 12/1/2018");
        row = table.createRow(12);
        row.createCell(50, messageSource.getMessage("salutation", null, locale) +" "+ customerDao.getFirstName() + " " + customerDao.getLastName());
        row.createCell(50, messageSource.getMessage("salutation", null, locale) + " Anoop");
        row = table.createRow(12);
        row.createCell(50, messageSource.getMessage("designation", null, locale) + " CEO");
        row.createCell(50, messageSource.getMessage("designation", null, locale) + " Dev");
        row = table.createRow(12);
        row.createCell(50, messageSource.getMessage("signature", null, locale));
        row.createCell(50, messageSource.getMessage("signature", null, locale));
        table.draw();
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.plans", null, locale), font, 12.0f, 50f, 420f, Color.BLACK);
        PDStreamUtils.write(contentStream, messageSource.getMessage("firstPage.planContent", null, locale), font, 8.0f, 50f, 405f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Anlage 1 Ansprechpartner und Verantwortliche", font, 8.0f, 50f, 390f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Anlage 2 Gegenstand der Datenverarbeitung", font, 8.0f, 50f, 375f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Anlage 3 Unterauftragnehmer", font, 8.0f, 50f, 350f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Anlage 4 Technische und organisatorische Maßnahmen", font, 8.0f, 50f, 335f, Color.BLACK);

        PDStreamUtils.write(contentStream, "Anlage 1: Autorisierte Personen", font, 12.0f, 50f, 300f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Autorisierte Personen des Auftraggebers", font, 8.0f, 50f, 260f, Color.BLACK);
        table = new BaseTable(240, 240, bottomMargin, tableWidth, MARGIN, document, page, true, drawContent);
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
        contentStream.close();

        return page;
    }



    private PDPage getSecondPage(CustomerDao customerDao) throws Exception {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDType1Font font = PDType1Font.HELVETICA;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * MARGIN);
        float tableWidth = page.getMediaBox().getWidth() - (2 * MARGIN);
        boolean drawContent = true;
        float bottomMargin = 70;
        addHeaderAndFooter(document,page,contentStream,font);
        PDStreamUtils.write(contentStream, "Autorisierte Personen des Auftragnehmers", font, 12.0f, 50, 741.88f, Color.BLACK);
        BaseTable table = new BaseTable(720f, yStartNewPage, bottomMargin, tableWidth, MARGIN, document, page, true, drawContent);
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
        PDStreamUtils.write(contentStream, "Ansprechpartner Datenschutz des Auftraggebers", font, 12.0f, 50f, 600f, Color.BLACK);
        table = new BaseTable(580, 240, bottomMargin, tableWidth, MARGIN, document, page, true, drawContent);
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
        PDStreamUtils.write(contentStream, "Ansprechpartner Datenschutz des Auftragnehmers", font, 12.0f, 50, 500f, Color.BLACK);
        table = new BaseTable(485, 240, bottomMargin, tableWidth, MARGIN, document, page, true, drawContent);
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
        PDStreamUtils.write(contentStream, "Anlage 2: Gegenstand der Datenverarbeitung", font, 12.0f, 50, 270f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Gegenstand und Dauer der Datenverarbeitung sind wie folgt geplant:", font, 12.0f, 50, 255, Color.BLACK);
        table = new BaseTable(240, 240, bottomMargin, tableWidth, MARGIN, document, page, true, drawContent);
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
        PDStreamUtils.write(contentStream, "Ergänzend wird auf die Leistungsbeschreibung des Hauptvertrags verwiesen.", font, 12.0f, 50, 190f, Color.BLACK);
        contentStream.close();

        return page;
    }

    private PDPage getThirdPage(CustomerDao customerDao) throws Exception {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDType1Font font = PDType1Font.HELVETICA;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        addHeaderAndFooter(document,page,contentStream,font);
        PDStreamUtils.write(contentStream, "Art und Zweck der Datenverarbeitung wie folgt geplant:", font, 8.0f, 50, 741.88f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data ", font, 8.0f, 50, 720.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data", font, 8.0f, 50, 705.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data", font, 8.0f, 50, 690.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Ergänzend wird auf die Leistungsbeschreibung des Hauptvertrags verwiesen.", font, 8.0f, 50, 675.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Die Datenverarbeitung umfasst die folgenden Arten personenbezogener Daten:", font, 8.0f, 50, 615.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data", font, 8.0f, 50, 585.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data", font, 8.0f, 50, 570.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data", font, 8.0f, 50, 555.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "Die Verarbeitung umfasst die folgenden Kategorien betroffener Personen:", font, 8.0f, 50, 540.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data", font, 8.0f, 50, 525.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data", font, 8.0f, 50, 510.0f, Color.BLACK);
        PDStreamUtils.write(contentStream, "sample data sample data", font, 8.0f, 50, 495.0f, Color.BLACK);
        contentStream.close();
        return page;

    }

    private PDPage getFourthPage(CustomerDao customerDao) throws Exception {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDType1Font font = PDType1Font.HELVETICA;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        addHeaderAndFooter(document,page,contentStream,font);
        float bottomMargin = 70;
        boolean drawContent = true;
        float tableWidth = page.getMediaBox().getWidth() - (2 * MARGIN);
        PDStreamUtils.write(contentStream, "Anlage 3: Unterauftragnehmer", font, 12.0f, 50, 741.88f, Color.BLACK);
        BaseTable table = new BaseTable(715, 240, bottomMargin, tableWidth, MARGIN, document, page, true, drawContent);
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
        contentStream.close();
        return page;
    }
}
