package org.example.company;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public void addCompany(@RequestBody CompanyDto dto) {
        companyService.addCompany(dto);
    }

    @DeleteMapping("/{companyRegistrationNumber}")
    public void deleteCompany(@PathVariable String companyRegistrationNumber) {
        companyService.deleteCompany(companyRegistrationNumber);
    }
}
