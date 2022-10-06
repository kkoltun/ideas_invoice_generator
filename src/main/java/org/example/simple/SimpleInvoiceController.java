package org.example.simple;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simple")
public class SimpleInvoiceController {

    private final SimpleInvoiceService simpleInvoiceService;

    public SimpleInvoiceController(SimpleInvoiceService simpleInvoiceService) {
        this.simpleInvoiceService = simpleInvoiceService;
    }

    @PostMapping("/actor")
    public void addInvoiceActor(@RequestBody InvoiceActorDto dto) {
        simpleInvoiceService.addInvoiceActor(dto);
    }

    @PostMapping("/hourly")
    public void addHourlyInvoice(@RequestBody HourlyInvoiceDto dto) {
        simpleInvoiceService.addInvoice(dto);
    }

    @PostMapping("/bonus")
    public void addBonusInvoice(@RequestBody BonusInvoiceDto dto) {
        simpleInvoiceService.addInvoice(dto);
    }

    @DeleteMapping("/actor/{invoiceActorRegistrationNumber}")
    public void deleteActor(@PathVariable String invoiceActorRegistrationNumber) {
        simpleInvoiceService.deleteInvoiceActor(invoiceActorRegistrationNumber);
    }

    @DeleteMapping("/invoice/{invoiceNumber}")
    public void deleteInvoice(@PathVariable String invoiceNumber) {
        simpleInvoiceService.deleteInvoice(invoiceNumber);
    }
}
