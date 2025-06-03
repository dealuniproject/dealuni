package com.dealuni.demo.services;

import com.dealuni.demo.dto.CompanyRequest;
import com.dealuni.demo.dto.CompanyResponse;
import com.dealuni.demo.models.Company;
import com.dealuni.demo.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyResponse createNewCompany(CompanyRequest companyRequest) {
        validateCompany(companyRequest);
        Company company = convertCompanyRequestToCompanyEntity(companyRequest);
        Company savedCompany = companyRepository.save(company);
        return convertCompanyEntityToCompanyResponse(savedCompany);
    }

    public List<CompanyResponse> getAllExistingCompanies() {
        List<Company> allExistingCompanies = companyRepository.findAll();
        return convertCompanyEntityToCompanyResponse(allExistingCompanies);
    }

    public CompanyResponse getCompanyById(Long Id) {
        Company company = companyRepository.findById(Id).orElseThrow(() -> new NoSuchElementException("Compania nu a fost găsită."));
        return convertCompanyEntityToCompanyResponse(company);
    }

    public CompanyResponse updateCompanyById(Long id, CompanyRequest companyRequest) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compania nu a fost găsită."));

        if (companyRequest.getName() != null) {
            existingCompany.setName(companyRequest.getName());
        }

        if (companyRequest.getDescription() != null) {
            existingCompany.setDescription(companyRequest.getDescription());
        }

        if (companyRequest.getLogo() != null) {
            existingCompany.setLogo(companyRequest.getLogo());
        }

        Company updatedCompany = companyRepository.save(existingCompany);
        return convertCompanyEntityToCompanyResponse(updatedCompany);
    }

    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    public List<CompanyResponse> convertCompanyEntityToCompanyResponse(List<Company> companies) {
        List<CompanyResponse> companyResponses = new ArrayList<>();
        for (Company company : companies) {
            companyResponses.add(convertCompanyEntityToCompanyResponse(company));
        }
        return companyResponses;
    }

    public CompanyResponse convertCompanyEntityToCompanyResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setDescription(company.getDescription());
        companyResponse.setLogo(company.getLogo());
        return companyResponse;
    }

    public Company convertCompanyRequestToCompanyEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setDescription(companyRequest.getDescription());
        company.setLogo(companyRequest.getLogo());
        return company;
    }

    public void validateCompany(CompanyRequest companyRequest) {

        if (companyRequest.getName() == null || companyRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele companiei nu poate fi null.");
        }

        if (companyRequest.getName().length() > 100) {
            throw new IllegalArgumentException("Numele companiei nu poate depăși 100 de caractere.");
        }

        String regex = "^[A-ZĂÂÎȘȚ0-9][a-zăâîșțA-ZĂÂÎȘȚ0-9\\-\\.\\&\\,\\(\\) ]{1,99}$";

        if (!companyRequest.getName().matches(regex)) {
            throw new IllegalArgumentException("Numele companiei trebuie să înceapă cu literă sau cifră și poate conține doar litere, cifre, cratimă, punct, virgulă, & sau paranteze.");
        }

        if (companyRequest.getDescription() == null || companyRequest.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrierea companiei nu poate fi null.");
        }

        if (companyRequest.getDescription().length() > 600) {
            throw new IllegalArgumentException("Descrierea companiei nu poate depăși 600 de caractere.");
        }

        if (!companyRequest.getDescription().matches("^[\\p{L}0-9.,!?%:;\"'()\\-\\/\\s]{10,600}$")) {
            throw new IllegalArgumentException("Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, spații și semne de punctuație uzuale.");
        }

        if (companyRequest.getLogo() == null || companyRequest.getLogo().trim().isEmpty()) {
            throw new IllegalArgumentException("Logo-ul companiei este obligatoriu.");
        }

        if (companyRequest.getLogo().length() > 200) {
            throw new IllegalArgumentException("URL-ul logo-ului nu poate depăși 200 de caractere.");
        }
    }
}

