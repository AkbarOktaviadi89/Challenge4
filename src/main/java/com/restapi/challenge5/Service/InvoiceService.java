package com.restapi.challenge5.Service;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface InvoiceService {

    byte[] generateInvoice(String username) throws FileNotFoundException, JRException;

}