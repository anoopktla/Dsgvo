package de.logicline.adv.util;

import be.quodlibet.boxable.utils.PDStreamUtils;
import de.logicline.adv.model.dao.CustomerDao;
import org.apache.commons.io.IOUtils;
import org.apache.fontbox.util.autodetect.FontFileFinder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

@Component
public class PdfManager {
    private MessageSource messageSource;
    private ResourceLoader resourceLoader;
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PATH_PREFIX = "classpath:pdf";
    private static final String FILE_NAME = "Template_ADV_DE.pdf";
    private static final PDType1Font font = PDType1Font.HELVETICA;

    @Autowired
    public PdfManager(MessageSource messageSource, ResourceLoader resourceLoader) {
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
    }

    public byte[] generatePdf(CustomerDao customerDao) throws Exception {
        Resource resource = resourceLoader.getResource(PATH_PREFIX + FILE_SEPARATOR + FILE_NAME);
        InputStream fileAsInputStream = resource.getInputStream();
        PDDocument advAsPdfDocument = PDDocument.load(fileAsInputStream);
        fileAsInputStream.close();
        PDPage firstPage = advAsPdfDocument.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(advAsPdfDocument, firstPage,true,true);
        PDStreamUtils.write(contentStream, customerDao.getSalutation(), font,
                9.0f, 240, 470, Color.BLACK);

        PDStreamUtils.write(contentStream, customerDao.getFirstName(), font,
                9.0f, 260, 470, Color.BLACK);
        PDStreamUtils.write(contentStream, customerDao.getLastName(), font,
                9.0f, 290, 470, Color.BLACK);
        FontFileFinder fontFinder = new FontFileFinder();
        List<URI> fontURIs = fontFinder.find();
        for(URI uri:fontURIs){
            System.out.println(uri.toString());
        }

        contentStream.close();
        PDPage thirdPage = advAsPdfDocument.getPage(2);
        contentStream = new PDPageContentStream(advAsPdfDocument, thirdPage,true,true);
        PDStreamUtils.write(contentStream, "123456789", font,
                9.0f, 160, 695, Color.BLACK);
        PDStreamUtils.write(contentStream, "1/1/1999", font,
                9.0f, 240, 695, Color.BLACK);
        contentStream.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        advAsPdfDocument.save(out);
        advAsPdfDocument.close();
        return out.toByteArray();
    }

}
