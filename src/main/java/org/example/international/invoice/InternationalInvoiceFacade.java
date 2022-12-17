package org.example.international.invoice;

import org.example.company.CompanyService;
import org.example.nbp.NbpRateDto;
import org.example.nbp.NbpService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.DayOfWeek.SATURDAY;

@Service
public class InternationalInvoiceFacade {

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final InternationalInvoiceService internationalInvoiceService;
    private final CompanyService companyService;
    private final NbpService nbpService;

    public InternationalInvoiceFacade(InternationalInvoiceService internationalInvoiceService, CompanyService companyService, NbpService nbpService) {
        this.internationalInvoiceService = internationalInvoiceService;
        this.companyService = companyService;
        this.nbpService = nbpService;
    }

    public InternationalInvoice getInvoice(String invoiceNumber) {
        return internationalInvoiceService.getInvoice(invoiceNumber);
    }

    public void addInvoice(HourlyInvoiceDto hourlyInvoiceDto) {
        internationalInvoiceService.addInvoice(mapHourlyInvoice(hourlyInvoiceDto));
    }

    public void addInvoice(BonusInvoiceDto bonusInvoiceDto) {
        internationalInvoiceService.addInvoice(mapBonusInvoice(bonusInvoiceDto));
    }

    public void deleteInvoice(String invoiceNumber) {
        InternationalInvoice internationalInvoice = internationalInvoiceService.getInvoice(invoiceNumber);
        if (internationalInvoice == null) {
            return;
        }

        internationalInvoiceService.deleteInvoice(internationalInvoice);
    }

    private InternationalInvoice mapHourlyInvoice(HourlyInvoiceDto hourlyInvoiceDto) {
        LocalDate invoiceDate = hourlyInvoiceDto.getToDate();
        LocalDate dueDate = invoiceDate.plusMonths(2).withDayOfMonth(1);

        String description = String.format("Invoice for completing the software programming tasks in period %s - %s",
                hourlyInvoiceDto.getFromDate().format(DATE_FORMAT),
                hourlyInvoiceDto.getToDate().format(DATE_FORMAT));
        String descriptionPl = String.format("Realizacja zadań związanych z oprogramowaniem w dniach %s - %s",
                hourlyInvoiceDto.getFromDate().format(DATE_FORMAT),
                hourlyInvoiceDto.getToDate().format(DATE_FORMAT));

        LocalDate lastBusinessDay = getNearestBusinessDay(hourlyInvoiceDto.getToDate());
        NbpRateDto usdPlnRate = nbpService.getUsdRate(lastBusinessDay).block();

        if (!usdPlnRate.getDate().isEqual(lastBusinessDay)) {
            throw new RuntimeException(String.format("Found NBP USD-PLN rate from day [%s] when searching for day [%s].",
                    usdPlnRate.getDate(),
                    lastBusinessDay));
        }

        InternationalInvoice internationalInvoice = new InternationalInvoice();
        internationalInvoice.setInvoiceDate(invoiceDate);
        internationalInvoice.setDueDate(dueDate);
        internationalInvoice.setInvoiceNumber(hourlyInvoiceDto.getInvoiceNumber());
        internationalInvoice.setDebtorId(companyService.getCompanyId(hourlyInvoiceDto.getDebtorRegistrationNumber()));
        internationalInvoice.setVendorId(companyService.getCompanyId(hourlyInvoiceDto.getVendorRegistrationNumber()));
        internationalInvoice.setDescription(description);
        internationalInvoice.setDescriptionPl(descriptionPl);
        internationalInvoice.setUnitAmount(hourlyInvoiceDto.getUnitAmount());
        internationalInvoice.setUnitName("h");
        internationalInvoice.setUnitNetPrice(hourlyInvoiceDto.getUnitNetPrice());
        internationalInvoice.setInvoiceAmount(hourlyInvoiceDto.getInvoiceAmount());
        internationalInvoice.setUsdPlnRate(usdPlnRate.getRate());
        internationalInvoice.setVatRate(null);
        internationalInvoice.setNbpTableNumber(usdPlnRate.getTableNo());
        internationalInvoice.setNbpTableDate(usdPlnRate.getDate());
        Timestamp timestamp = Timestamp.from(Instant.now());
        internationalInvoice.setCreated(timestamp);
        internationalInvoice.setLastupdated(timestamp);

        return internationalInvoice;
    }

    private InternationalInvoice mapBonusInvoice(BonusInvoiceDto bonusInvoiceDto) {
        LocalDate dueDate = bonusInvoiceDto.getInvoiceDate().plusMonths(2).withDayOfMonth(1);
        LocalDate lastBusinessDay = getNearestBusinessDay(bonusInvoiceDto.getInvoiceDate());

        NbpRateDto usdPlnRate = nbpService.getUsdRate(lastBusinessDay).block();

        if (!usdPlnRate.getDate().isEqual(lastBusinessDay)) {
            throw new RuntimeException(String.format("Found NBP USD-PLN rate from day [%s] when searching for day [%s].",
                    usdPlnRate.getDate(),
                    lastBusinessDay));
        }

        InternationalInvoice internationalInvoice = new InternationalInvoice();
        internationalInvoice.setInvoiceDate(bonusInvoiceDto.getInvoiceDate());
        internationalInvoice.setDueDate(dueDate);
        internationalInvoice.setInvoiceNumber(bonusInvoiceDto.getInvoiceNumber());
        internationalInvoice.setDebtorId(companyService.getCompanyId(bonusInvoiceDto.getDebtorRegistrationNumber()));
        internationalInvoice.setVendorId(companyService.getCompanyId(bonusInvoiceDto.getVendorRegistrationNumber()));
        internationalInvoice.setDescription(bonusInvoiceDto.getDescription());
        internationalInvoice.setDescriptionPl(bonusInvoiceDto.getDescriptionPl());
        internationalInvoice.setUnitAmount(null);
        internationalInvoice.setUnitName(null);
        internationalInvoice.setUnitNetPrice(null);
        internationalInvoice.setInvoiceAmount(bonusInvoiceDto.getInvoiceAmount());
        internationalInvoice.setUsdPlnRate(usdPlnRate.getRate());
        internationalInvoice.setVatRate(null);
        internationalInvoice.setNbpTableNumber(usdPlnRate.getTableNo());
        internationalInvoice.setNbpTableDate(usdPlnRate.getDate());
        Timestamp timestamp = Timestamp.from(Instant.now());
        internationalInvoice.setCreated(timestamp);
        internationalInvoice.setLastupdated(timestamp);

        return internationalInvoice;
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
