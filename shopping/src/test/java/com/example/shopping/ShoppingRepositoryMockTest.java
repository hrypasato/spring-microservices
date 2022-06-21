package com.example.shopping;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.shopping.entity.Invoice;
import com.example.shopping.entity.InvoiceItem;
import com.example.shopping.repository.InvoiceItemsRepository;
import com.example.shopping.repository.InvoiceRepository;

@DataJpaTest
public class ShoppingRepositoryMockTest {
    
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemsRepository invoiceItemsRepository;


    //test find Invoice by id
    @Test
    public void testFindInvoiceById() {
        Invoice invoice = invoiceRepository.findById(1L).get();
        Assertions.assertThat(invoice.getId()).isEqualTo(1L);
    }

    //test create invoice and select all invoices
    @Test
    public void testCreateInvoiceAndSelectAllInvoices() {
        
        Invoice invoice = Invoice.builder().numberInvoice("123").description("test").customerId(1L).build();
        invoiceRepository.save(invoice);

        List<Invoice> invoices = invoiceRepository.findAll();
        Assertions.assertThat(invoices.size()).isEqualTo(2);
    }

    //test find invoice items by invoice id
    @Test
    public void testFindInvoiceItemsByInvoiceId() {
        List<Invoice> invoices = invoiceRepository.findAll();
        Assertions.assertThat(invoices.get(0).getItems().size()).isEqualTo(3);
    }

    //test find invoiceItem by id
    @Test
    public void testFindInvoiceItemById() {
        InvoiceItem invoiceItem = invoiceItemsRepository.findById(1L).get();
        Assertions.assertThat(invoiceItem.getId()).isEqualTo(1L);
    }

    //test create invoice item and select all invoice items
    @Test
    public void testCreateInvoiceItemAndSelectAllInvoiceItems() {
        InvoiceItem invoiceItem = InvoiceItem.builder().productId(2L).build();
        invoiceItemsRepository.save(invoiceItem);

        List<InvoiceItem> invoiceItems = invoiceItemsRepository.findAll();
        Assertions.assertThat(invoiceItems.size()).isEqualTo(4);
    }
}
