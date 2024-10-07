package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company companyDetails) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(companyDetails.getName());
                    company.setDepartments(companyDetails.getDepartments());
                    return companyRepository.save(company);
                })
                .orElseThrow(() -> new RuntimeException("Company not found with id " + id));
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}