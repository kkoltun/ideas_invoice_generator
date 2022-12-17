package org.example.company;

import org.springframework.data.repository.CrudRepository;

interface CompanyRepository extends CrudRepository<Company, Integer> {
    Iterable<Company> findByRegistrationNumber(String registrationNumber);
}
