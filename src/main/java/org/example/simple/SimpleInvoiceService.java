package org.example.simple;

import org.example.invoice.Invoice;
import org.example.invoice.InvoiceActor;
import org.example.invoice.InvoiceService;
import org.example.nbp.NbpService;
import org.example.nbp.NbpRateDto;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.DayOfWeek.SATURDAY;

@Service
public class SimpleInvoiceService {

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final InvoiceService invoiceService;

    public SimpleInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void addInvoiceActor(InvoiceActorDto invoiceActorDto) {
        invoiceService.addActor(mapInvoiceActor(invoiceActorDto));
    }

    public void addInvoice(HourlyInvoiceDto hourlyInvoiceDto) {
        invoiceService.addInvoice(mapHourlyInvoice(hourlyInvoiceDto));
    }

    public void addInvoice(BonusInvoiceDto bonusInvoiceDto) {
        invoiceService.addInvoice(mapBonusInvoice(bonusInvoiceDto));
    }

    public void deleteInvoice(String invoiceNumber) {
        Invoice invoice = invoiceService.getInvoice(invoiceNumber);
        if (invoice == null) {
            return;
        }

        invoiceService.deleteInvoice(invoice);
    }

    public void deleteInvoiceActor(String invoiceActorRegistrationNumber) {
        InvoiceActor invoiceActor = invoiceService.getInvoiceActor(invoiceActorRegistrationNumber);
        if (invoiceActor == null) {
            return;
        }

        invoiceService.deleteInvoiceActor(invoiceActor);
    }

    private InvoiceActor mapInvoiceActor(InvoiceActorDto dto) {
        InvoiceActor invoiceActor = new InvoiceActor();

        invoiceActor.setName(dto.getName());
        invoiceActor.setAddressLine1(dto.getAddressLine1());
        invoiceActor.setAddressLine2(dto.getAddressLine2());
        invoiceActor.setAddressLine3(dto.getAddressLine3());
        invoiceActor.setRegistrationNumber(dto.getRegistrationNumber());
        Timestamp timestamp = Timestamp.from(Instant.now());
        invoiceActor.setCreated(timestamp);
        invoiceActor.setLastupdated(timestamp);

        return invoiceActor;
    }

    private Invoice mapHourlyInvoice(HourlyInvoiceDto hourlyInvoiceDto) {
        LocalDate invoiceDate = hourlyInvoiceDto.getToDate();
        LocalDate dueDate = invoiceDate.plusMonths(2).withDayOfMonth(1);

        String description = String.format("Invoice for completing the software programming tasks in period %s - %s",
                hourlyInvoiceDto.getFromDate().format(DATE_FORMAT),
                hourlyInvoiceDto.getToDate().format(DATE_FORMAT));
        String descriptionPl = String.format("Realizacja zadań związanych z oprogramowaniem w dniach %s - %s",
                hourlyInvoiceDto.getFromDate().format(DATE_FORMAT),
                hourlyInvoiceDto.getToDate().format(DATE_FORMAT));

        LocalDate lastBusinessDay = getNearestBusinessDay(hourlyInvoiceDto.getToDate());
        NbpRateDto usdPlnRate = new NbpService().getUsdRate(lastBusinessDay);

        if (!usdPlnRate.getDate().isEqual(lastBusinessDay)) {
            throw new RuntimeException(String.format("Found NBP USD-PLN rate from day [%s] when searching for day [%s].",
                    usdPlnRate.getDate(),
                    lastBusinessDay));
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(invoiceDate);
        invoice.setDueDate(dueDate);
        invoice.setInvoiceNumber(hourlyInvoiceDto.getInvoiceNumber());
        invoice.setDebtorId(getInvoiceActorId(hourlyInvoiceDto.getDebtorRegistrationNumber()));
        invoice.setVendorId(getInvoiceActorId(hourlyInvoiceDto.getVendorRegistrationNumber()));
        invoice.setDescription(description);
        invoice.setDescriptionPl(descriptionPl);
        invoice.setUnitAmount(hourlyInvoiceDto.getUnitAmount());
        invoice.setUnitName("h");
        invoice.setUnitNetPrice(hourlyInvoiceDto.getUnitNetPrice());
        invoice.setInvoiceAmount(hourlyInvoiceDto.getInvoiceAmount());
        invoice.setUsdPlnRate(usdPlnRate.getRate());
        invoice.setVatRate(null);
        invoice.setNbpTableNumber(usdPlnRate.getTableNo());
        invoice.setNbpTableDate(usdPlnRate.getDate());
        Timestamp timestamp = Timestamp.from(Instant.now());
        invoice.setCreated(timestamp);
        invoice.setLastupdated(timestamp);

        return invoice;
    }

    private Invoice mapBonusInvoice(BonusInvoiceDto bonusInvoiceDto) {
        LocalDate dueDate = bonusInvoiceDto.getInvoiceDate().plusMonths(2).withDayOfMonth(1);
        LocalDate lastBusinessDay = getNearestBusinessDay(bonusInvoiceDto.getInvoiceDate());

        NbpRateDto usdPlnRate = new NbpService().getUsdRate(lastBusinessDay);

        if (!usdPlnRate.getDate().isEqual(lastBusinessDay)) {
            throw new RuntimeException(String.format("Found NBP USD-PLN rate from day [%s] when searching for day [%s].",
                    usdPlnRate.getDate(),
                    lastBusinessDay));
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(bonusInvoiceDto.getInvoiceDate());
        invoice.setDueDate(dueDate);
        invoice.setInvoiceNumber(bonusInvoiceDto.getInvoiceNumber());
        invoice.setDebtorId(getInvoiceActorId(bonusInvoiceDto.getDebtorRegistrationNumber()));
        invoice.setVendorId(getInvoiceActorId(bonusInvoiceDto.getVendorRegistrationNumber()));
        invoice.setDescription(bonusInvoiceDto.getDescription());
        invoice.setDescriptionPl(bonusInvoiceDto.getDescriptionPl());
        invoice.setUnitAmount(null);
        invoice.setUnitName(null);
        invoice.setUnitNetPrice(null);
        invoice.setInvoiceAmount(bonusInvoiceDto.getInvoiceAmount());
        invoice.setUsdPlnRate(usdPlnRate.getRate());
        invoice.setVatRate(null);
        invoice.setNbpTableNumber(usdPlnRate.getTableNo());
        invoice.setNbpTableDate(usdPlnRate.getDate());
        Timestamp timestamp = Timestamp.from(Instant.now());
        invoice.setCreated(timestamp);
        invoice.setLastupdated(timestamp);

        return invoice;
    }

    private Integer getInvoiceActorId(String registrationNumber) {
        InvoiceActor invoiceActor = invoiceService.getInvoiceActor(registrationNumber);
        if (invoiceActor == null) {
            throw new RuntimeException(String.format("Invoice actor with registration number [%s] could not be found.", registrationNumber));
        }

        return invoiceActor.getId();
    }

    private static LocalDate getNearestBusinessDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek != DayOfWeek.SUNDAY && dayOfWeek != SATURDAY) {
            return date;
        }

        int daysBack = dayOfWeek == SATURDAY ? 1 : 2;

        return date.minusDays(daysBack);
    }
}
