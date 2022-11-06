package org.example.company;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.example.infrastructure.Database.getEntityManagerFactory;

@Service
public class CompanyService {

    private final EntityManagerFactory entityManagerFactory;

    public CompanyService() {
        entityManagerFactory = getEntityManagerFactory();
    }

    public void addCompany(CompanyDto companyDto) {
        addCompany(mapCompany(companyDto));
    }

    public void addCompany(Company company) throws CompanyAlreadyExistsException {
        EntityManager manager = entityManagerFactory.createEntityManager();

        boolean invoiceActorExists = manager.createQuery("" +
                        "SELECT company " +
                        "FROM Company company " +
                        "WHERE company.registrationNumber = :registrationNumber ", Company.class)
                .setParameter("registrationNumber", company.getRegistrationNumber())
                .getResultList().size() > 0;
        if (invoiceActorExists) {
            throw new CompanyAlreadyExistsException(company.getRegistrationNumber());
        }

        // todo check the added actor

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(company);
        transaction.commit();

        manager.close();
    }

    public Company getCompany(String companyRegistrationNumber) {
        // todo not closed
        EntityManager manager = entityManagerFactory.createEntityManager();

        List<Company> companies = manager.createQuery("" +
                        "SELECT company " +
                        "FROM Company company " +
                        "WHERE company.registrationNumber = :registrationNumber", Company.class)
                .setParameter("registrationNumber", companyRegistrationNumber)
                .getResultList();

        return !companies.isEmpty()
                ? companies.get(0)
                : null;
    }

    public Integer getCompanyId(String registrationNumber) {
        Company company = getCompany(registrationNumber);
        if (company == null) {
            throw new RuntimeException(String.format("Company with registration number [%s] could not be found.", registrationNumber));
        }

        return company.getId();
    }

    public Company getCompanyById(int id) {
        // todo not closed
        EntityManager manager = entityManagerFactory.createEntityManager();
        return manager.find(Company.class, id);
    }

    public void deleteCompany(String companyRegistrationNumber) {
        Company company = getCompany(companyRegistrationNumber);
        if (company == null) {
            return;
        }

        deleteCompany(company);
    }

    public void deleteCompany(Company company) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        manager.createQuery("" +
                        "DELETE FROM Company actor " +
                        "WHERE actor.id = :id")
                .setParameter("id", company.getId())
                .executeUpdate();

        transaction.commit();
        manager.close();
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
