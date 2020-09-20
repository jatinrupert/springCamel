package com.demo.controller;

import com.demo.dto.InvoiceDTO;
import com.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping(value = "/info", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getInfo() {
        return new ResponseEntity<>("Demo is running...", HttpStatus.OK);
    }

    @RequestMapping(value = "/transform", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> transformInvoice(@Valid @RequestBody InvoiceDTO invoice) {
        final String message = invoiceService.transformInvoice(invoice);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}