package com.example.shopping.service;

import java.util.List;

import com.example.shopping.entity.Invoice;

public interface IInvoiceService {
    public List<Invoice> findInvoiceAll();

    public Invoice createInvoice(Invoice invoice);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice deleteInvoice(Long id);

    public Invoice getInvoice(Long id);
}
