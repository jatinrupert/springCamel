package com.demo;

import com.demo.dto.InvoiceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class InvoiceTransformTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void assertTransformHappensOk() throws Exception {
        this.mockMvc.perform(post(INVOICE_TRANSFORM_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(buildInvoiceDTO())).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalToIgnoringWhiteSpace(getExpectedOutput())));
    }

    @Test
    public void assertTransformFailsValidationOnRequest() throws Exception {
        final InvoiceDTO invoiceDTO = buildInvoiceDTO();
        invoiceDTO.setInvoicedTo(null);
        this.mockMvc.perform(post(INVOICE_TRANSFORM_URI).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceDTO)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"status\":400,\"errors\":[\"Please provide an invoicedTo\"]}"));
    }

    @Test
    public void assertTransformThrowsValidationException() throws Exception {
        final InvoiceDTO invoiceDTO = buildInvoiceDTO();
        invoiceDTO.getInvoiceItems().stream().findFirst().ifPresent(x -> x.setQuantity(null));
        this.mockMvc.perform(post(INVOICE_TRANSFORM_URI).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceDTO)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("Invalid content was found starting with element 'price'. One of '{quantity}' is expected.")));

    }

}
