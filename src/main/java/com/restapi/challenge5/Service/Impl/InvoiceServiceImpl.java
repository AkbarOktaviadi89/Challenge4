package com.restapi.challenge5.Service.Impl;

import com.restapi.challenge5.Model.Response.OrderDetailResponse;
import com.restapi.challenge5.Service.InvoiceService;
import com.restapi.challenge5.Service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    //Sorry kak cmn bisa gini, banyak kendala lain dan kurang paham untuk materi ini

    @Override
    public byte[] generateInvoice(String username) throws FileNotFoundException, JRException {
        log.info("Generating invoice for {}", username);
        List<OrderDetailResponse> orderDetails = Arrays.asList(
                OrderDetailResponse.builder().productName("Kwetiaw").price("Rp. 10.000").quantity(2L).build(),
                OrderDetailResponse.builder().productName("Mie Ayam").price("Rp. 12.000").quantity(2L).build(),
                OrderDetailResponse.builder().productName("Es Teh Manis").price("Rp. 5.000").quantity(4L).build()
        );

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("username", username);
        parameterMap.put("finalPrice", "Rp. 27.000");
        parameterMap.put("orderDetail", orderDetails);
        JasperPrint invoice = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:Invoice.jrxml").getAbsolutePath()),
                parameterMap,
                new JRBeanCollectionDataSource(orderDetails)
        );

        return JasperExportManager.exportReportToPdf(invoice);
    }
}
