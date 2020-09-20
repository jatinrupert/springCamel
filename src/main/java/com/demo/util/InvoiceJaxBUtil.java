package com.demo.util;

import com.demo.schema.Invoice;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class InvoiceJaxBUtil {

    private static JAXBContext context = null;

    private static synchronized JAXBContext createJAXBContext() throws JAXBException {
        if(context == null){
            context = JAXBContext.newInstance(Invoice.class);
        }
        return context;
    }

    public String toXML(Invoice invoice) throws JAXBException {
        final Marshaller jaxbMarshaller = createJAXBContext().createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(invoice, sw);

        return sw.toString();
    }

}

