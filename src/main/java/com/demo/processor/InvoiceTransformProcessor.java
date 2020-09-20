package com.demo.processor;

import com.demo.dto.InvoiceDTO;
import com.demo.dto.InvoiceItemDTO;
import com.demo.schema.Invoice;
import com.demo.util.InvoiceJaxBUtil;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;


@Component
public class InvoiceTransformProcessor implements Processor {

    private InvoiceJaxBUtil invoiceJaxBUtil = new InvoiceJaxBUtil();

    @Override
    public void process(Exchange exchange) throws Exception {
        final InvoiceDTO invoiceDTO = exchange.getIn().getBody(InvoiceDTO.class);

        final Invoice invoice = new Invoice();
        invoice.setInvoiceid(String.valueOf(invoiceDTO.getInvoiceId()));
        invoice.setInvoiceto(invoiceDTO.getInvoicedTo());
        invoice.setInvoicefrom(invoiceDTO.getInvoiceFrom());
        invoiceDTO.getInvoiceItems().forEach(x -> invoice.getItem().add(buildItem(x)));
        exchange.getIn().setBody(invoiceJaxBUtil.toXML(invoice));
    }

    private Invoice.Item buildItem(InvoiceItemDTO invoiceItemDTO) {
        final Invoice.Item item = new Invoice.Item();
        item.setNote(invoiceItemDTO.getDescription());
        item.setPrice(invoiceItemDTO.getAmount());
        item.setQuantity(invoiceItemDTO.getQuantity() != null ? BigInteger.valueOf(invoiceItemDTO.getQuantity()) : null);
        item.setCurrency(invoiceItemDTO.getCurrency());
        return item;
    }
}
