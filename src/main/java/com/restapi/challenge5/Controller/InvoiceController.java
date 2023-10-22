package com.restapi.challenge5.Controller;


import com.restapi.challenge5.Service.InvoiceService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping(value = "/generate-invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity generateInvoice(@RequestHeader("username") String username) throws JRException, FileNotFoundException {
        if(username!=null){
        return ResponseEntity.ok()
                .body(invoiceService.generateInvoice(username));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
