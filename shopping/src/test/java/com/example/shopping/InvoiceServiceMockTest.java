package com.example.shopping;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shopping.entity.Invoice;
import com.example.shopping.entity.InvoiceItem;
import com.example.shopping.model.Customer;
import com.example.shopping.repository.InvoiceRepository;
import com.example.shopping.service.InvoiceService;

@SpringBootTest
public class InvoiceServiceMockTest {
    @Mock
    private InvoiceRepository invoiceRepository;

    private InvoiceService invoiceService;

    @BeforeEach
    public void setUp() {
        invoiceService = new InvoiceService(invoiceRepository);

        //build invoice
        Invoice invoice = Invoice.builder().numberInvoice("123").description("test").customer(new Customer()).customerId(1L).build();

        //build invoice items
        InvoiceItem invoiceItem = InvoiceItem.builder().productId(2L).build();

        //add invoice items to invoice
        invoice.setItems(List.of(invoiceItem));

        Mockito.when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        Mockito.when(invoiceRepository.findAll()).thenReturn(List.of(invoice));
        Mockito.when(invoiceRepository.save(invoice)).thenReturn(invoice);
    }

    //test all invoices
    @Test
    public void testFindAllInvoices() {
        List<Invoice> invoices = invoiceService.findInvoiceAll();
        Assertions.assertEquals(1, invoices.size());
    }

    //test create invoice
    @Test
    public void testCreateInvoice() {
        Invoice invoice = Invoice.builder().numberInvoice("123").description("test").customerId(1L).build();
        Invoice invoiceCreated = invoiceService.createInvoice(invoice);
        Assertions.assertEquals(invoice.getNumberInvoice(), invoiceCreated.getNumberInvoice());
    }
}
