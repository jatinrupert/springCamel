package com.demo.service;

import com.demo.dto.InvoiceDTO;
import com.demo.exception.TransformValidationException;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final static String INVOICE_TRANSFORM_URI ="direct:transformInvoice";

    @Autowired
    private CamelContext camelContext;

    @Autowired
    @EndpointInject(INVOICE_TRANSFORM_URI)
    ProducerTemplate producerTemplate;

    public String transformInvoice(InvoiceDTO invoice) {
        final Exchange sendExchange = ExchangeBuilder.anExchange(camelContext).withBody(invoice).build();
        final Exchange outExchange = producerTemplate.send(sendExchange);
        if(outExchange.isFailed()) {
            throw new TransformValidationException(outExchange.getException().getMessage());
        }
        return outExchange.getMessage().getBody(String.class);
    }
}
