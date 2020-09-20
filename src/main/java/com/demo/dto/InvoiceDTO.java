package com.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class InvoiceDTO {

    @NotNull(message = "Please provide an invoiceId")
    private Long invoiceId;

    @NotEmpty(message = "Please provide an invoicedTo")
    private String invoicedTo;

    @NotEmpty(message = "Please provide an invoiceFrom")
    private String invoiceFrom;

    @NotNull(message = "Please provide invoice items")
    private List<InvoiceItemDTO> invoiceItems;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoicedTo() {
        return invoicedTo;
    }

    public void setInvoicedTo(String invoicedTo) {
        this.invoicedTo = invoicedTo;
    }

    public String getInvoiceFrom() {
        return invoiceFrom;
    }

    public void setInvoiceFrom(String invoiceFrom) {
        this.invoiceFrom = invoiceFrom;
    }

    public List<InvoiceItemDTO> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItemDTO> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
