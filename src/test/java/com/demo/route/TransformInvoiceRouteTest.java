package com.demo.route;

import com.demo.BaseTest;
import com.demo.dto.InvoiceDTO;
import org.apache.camel.*;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(CamelSpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransformInvoiceRouteTest extends BaseTest {

    @Autowired
    private CamelContext camelContext;

    @Produce("direct:transformInvoice")
    private ProducerTemplate producerTemplate;

    @Test
    public void assertTransformHappensOk() throws IOException {
        final Exchange sendExchange = ExchangeBuilder.anExchange(camelContext).withBody(buildInvoiceDTO()).build();
        final Exchange outExchange = producerTemplate.send(sendExchange);

        assertFalse(outExchange.isFailed());

        final Diff xmlDiff = DiffBuilder.compare(getExpectedOutput())
                .withTest(outExchange.getMessage().getBody(String.class))
                .ignoreComments()
                .ignoreWhitespace()
                .build();

        assertFalse(
                "The resulting XML is not matching",
                xmlDiff.hasDifferences()
        );
    }

    @Test
    public void assertTransformThrowsValidationException() {
        final InvoiceDTO invoiceDTO = buildInvoiceDTO();
        invoiceDTO.getInvoiceItems().stream().findFirst().ifPresent(x -> x.setQuantity(null));

        final Exchange sendExchange = ExchangeBuilder.anExchange(camelContext).withBody(invoiceDTO).build();
        final Exchange outExchange = producerTemplate.send(sendExchange);
        assertTrue(outExchange.isFailed());
    }

}