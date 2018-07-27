package de.logicline.adv.controller.admin;

import de.logicline.adv.model.dao.CustomerDao;
import de.logicline.adv.util.CustomerUtil;
import de.logicline.adv.util.EmailUtil;
import de.logicline.adv.util.PdfManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Controller
@RequestMapping("/adv-service/v1/test")
public class TestController {
    //TODO to be removed ?, this is a sample end point for testing pdf generation, email sending etc

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private PdfManager pdfManager;

    private  static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);


    @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAsPdf() throws Exception {
        CustomerDao customerDao = CustomerUtil.createDummyCustomer("anoop.krishnapillai@logicline.de",pdfManager);
        InputStream in =  new ByteArrayInputStream(pdfManager.generatePdf(customerDao));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.CONTENT_TYPE);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "filename=" + "adv.pdf");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        headers.setContentLength(in.available());
        ResponseEntity<InputStreamResource> response = new ResponseEntity<>(
                new InputStreamResource(in), headers, HttpStatus.OK);
        in.close();
        LOGGER.info("Rendered pdf sucessfully ");
        return response;
    }

    @RequestMapping(value = "/mail/{id:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> sendTestEmail(@PathVariable(value = "id") String id) throws  Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.CONTENT_TYPE);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        String message;

        if (emailUtil.sendEmail("test email",  CustomerUtil.createDummyCustomer(id,pdfManager))) {
            message = "Email sent successfully to " + id;
            LOGGER.info(message);

        }
        else {
            message ="Error sending email to "+id;
            LOGGER.error(message);
        }
        return new ResponseEntity<>(
                message, headers, HttpStatus.OK);
    }




}
