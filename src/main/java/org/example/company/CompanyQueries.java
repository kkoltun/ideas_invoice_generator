package org.example.company;

import org.hibernate.annotations.processing.Find;

import java.util.List;

interface CompanyQueries {
    @Find
    List<Company> findByRegistrationNumber(String registrationNumber);
}
