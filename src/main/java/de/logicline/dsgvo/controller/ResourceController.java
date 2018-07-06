package de.logicline.dsgvo.controller;

import de.logicline.dsgvo.model.Customer;
import de.logicline.dsgvo.util.CustomerUtil;
import de.logicline.dsgvo.util.EmailUtil;
import de.logicline.dsgvo.util.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class ResourceController {
//TODO to be removed, this is a sample end point for testing pdf generation, email sending etc
    @Autowired
    PdfUtil pdfUtil;
    @Autowired
    EmailUtil emailUtil;


    @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> getAsPdf() throws Exception {
        Customer customer = CustomerUtil.createDummyCustomer();
        InputStream in = pdfUtil.createPdf(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "filename=" + "adv.pdf");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        headers.setContentLength(in.available());
        ResponseEntity<InputStreamResource> response = new ResponseEntity<>(
                new InputStreamResource(in), headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/mail/{id:.+}",method = RequestMethod.GET)
    public  String sendTestEmail(@PathVariable(value = "id") String id){
        if(emailUtil.sendEmail(id,"test email","This is a test email with attachment.",CustomerUtil.createDummyCustomer())){
            return "sent successfully";
        }
      return "error sending email";

    }
}
