package org.example.company;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.stream.StreamSupport;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void addCompany(CompanyDto companyDto) {
        addCompany(mapCompany(companyDto));
    }

    public void addCompany(Company company) throws CompanyAlreadyExistsException {
        boolean companyExists = companyRepository.findByRegistrationNumber(company.getRegistrationNumber())
                .iterator().hasNext();
        if (companyExists) {
            throw new CompanyAlreadyExistsException(company.getRegistrationNumber());
        }

        // todo check the added company
        companyRepository.save(company);
    }

    public Company getCompany(String companyRegistrationNumber) {
        return StreamSupport.stream(companyRepository.findByRegistrationNumber(companyRegistrationNumber).spliterator(), false)
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
        return companyRepository.findById(id)
                .orElse(null);
    }

    public Iterable<Company> getCompaniesByIds(Iterable<Integer> ids) {
        return companyRepository.findAllById(ids);
    }

    public void deleteCompany(String companyRegistrationNumber) {
        Company company = getCompany(companyRegistrationNumber);
        if (company == null) {
            return;
        }

        companyRepository.delete(company);
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
