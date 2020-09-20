package com.demo.controller;

import com.demo.BaseTest;
import com.demo.dto.InvoiceDTO;
import com.demo.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceControllerTest extends BaseTest {

    private static final String INVOICE_TRANSFORM_URI = "/invoice/transform";

    private MockMvc mockMvc;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(invoiceController)
                .setControllerAdvice(new ErrorHandlingController()).build();
    }

    @Test
    public void assertTransformHappensOk() throws Exception {
        this.mockMvc.perform(post(INVOICE_TRANSFORM_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(buildInvoiceDTO())).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
    public void assertTransformThrowsException() throws Exception {
        final InvoiceDTO invoiceDTO = buildInvoiceDTO();
        doThrow(new RuntimeException()).when(invoiceService).transformInvoice(any());
        this.mockMvc.perform(post(INVOICE_TRANSFORM_URI).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceDTO)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

}
