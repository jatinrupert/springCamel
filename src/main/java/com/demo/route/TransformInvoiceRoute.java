package com.demo.route;

import com.demo.processor.InvoiceTransformProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransformInvoiceRoute extends RouteBuilder {

    @Autowired
    private InvoiceTransformProcessor invoiceTransformProcessor;

    @Override
    public void configure() {
        from("direct:transformInvoice")
                .log("Camel body: ${body}")
                .process(invoiceTransformProcessor)
                .to("validator:xsd/invoice.xsd")
                .end();
    }
}

