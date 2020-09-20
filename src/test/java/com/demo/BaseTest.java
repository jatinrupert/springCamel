package com.demo;

import com.demo.dto.InvoiceDTO;
import com.demo.dto.InvoiceItemDTO;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public abstract class BaseTest {

    protected InvoiceDTO buildInvoiceDTO() {
        final InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO();
        invoiceItemDTO.setAmount(BigDecimal.TEN);
        invoiceItemDTO.setCurrency("GBP");
        invoiceItemDTO.setDescription("testing GBP");
        invoiceItemDTO.setQuantity(2);

        final InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(1234L);
        invoiceDTO.setInvoicedTo("Test to");
        invoiceDTO.setInvoiceFrom("Test from");
        invoiceDTO.setInvoiceItems(Arrays.asList(invoiceItemDTO));

        return invoiceDTO;
    }

    protected String getExpectedOutput() throws IOException {
        final File file = new File(getClass().getClassLoader().getResource("invoice.xml").getFile());
        return Files.readString(file.toPath(), StandardCharsets.ISO_8859_1);
    }
}
