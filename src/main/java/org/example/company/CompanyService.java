package org.example.company;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.example.company.CompanyQueries_.findByRegistrationNumber;

@Service
public class CompanyService {

    private final Logger log = LogManager.getLogger(CompanyService.class);

    private final SessionFactory sessionFactory;

    public CompanyService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addCompany(CompanyDto companyDto) {
        addCompany(mapCompany(companyDto));
    }

    public void addCompany(Company company) throws CompanyAlreadyExistsException {
        sessionFactory.inTransaction(session -> {
            boolean companyExists = findByRegistrationNumber(session, company.getRegistrationNumber()).size() > 0;

            if (companyExists) {
                throw new CompanyAlreadyExistsException(company.getRegistrationNumber());
            }

            // todo check the added company
            session.persist(company);
        });
    }

    public Company getCompany(String companyRegistrationNumber) {
        return sessionFactory.fromSession(session -> findByRegistrationNumber(session, companyRegistrationNumber))
                .stream()
                .findAny()
                .orElse(null);
    }

    public Integer getCompanyId(String registrationNumber) {
        Company company = getCompany(registrationNumber);
        if (company == null) {
            throw new RuntimeException(String.format("Company with registration number [%s] could not be found.", registrationNumber));
        }

        return company.getId();
    }

    public Company getCompanyById(int id) {
        return sessionFactory.fromSession(session -> session.find(Company.class, id));
    }

    public List<Company> getCompaniesByIds(Set<Integer> ids) {
        return sessionFactory.fromSession(session -> session.byMultipleIds(Company.class).multiLoad(new ArrayList<>(ids)));
    }

    public void deleteCompany(String companyRegistrationNumber) {
        // todo czemu inSession nie działało, a inTransaction działa?
        //   przy używaniu inSession nie widać w ogóle transaction
        sessionFactory.inSession(session -> {
            final var optionalCompany = findByRegistrationNumber(session, companyRegistrationNumber)
                    .stream()
                    .findAny();
            if (optionalCompany.isPresent()) {
                final var company = optionalCompany.get();
                log.info(company);
                session.remove(company);
            } else {
                log.info("Could not find company with registration number [{}]", companyRegistrationNumber);
            }
        });
    }

    private Company mapCompany(CompanyDto dto) {
        Company company = new Company();

        company.setName(dto.getName());
        company.setAddressLine1(dto.getAddressLine1());
        company.setAddressLine2(dto.getAddressLine2());
        company.setAddressLine3(dto.getAddressLine3());
        company.setRegistrationNumber(dto.getRegistrationNumber());
        Timestamp timestamp = Timestamp.from(Instant.now());
        company.setCreated(timestamp);
        company.setLastupdated(timestamp);

        return company;
    }
}
