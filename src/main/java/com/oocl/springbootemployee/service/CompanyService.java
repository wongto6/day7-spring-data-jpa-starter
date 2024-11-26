package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.CompanyInMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyInMemoryRepository companyInMemoryRepository;

    public CompanyService(CompanyInMemoryRepository companyInMemoryRepository) {
        this.companyInMemoryRepository = companyInMemoryRepository;
    }

    public List<Company> findAll(){
        return companyInMemoryRepository.findAll();
    }

    public List<Company> findAll(int pageIndex, int pageSize) {
        List<Company> companiesInPage = companyInMemoryRepository.getCompaniesByPagination(pageIndex, pageSize);
        return companiesInPage.stream().toList();
    }

    public Company findById(Integer id) {
        return companyInMemoryRepository.findById(id);
    }


    public List<Employee> getEmployeesByCompanyId(Integer id) {
        Company company = companyInMemoryRepository.findById(id);
        return company.getEmployees();
    }

    public Company create(Company company) {
        return companyInMemoryRepository.addCompany(company);
    }

    public Company update(Integer id, Company company) {
        final var companyNeedToUpdate = companyInMemoryRepository
                .findById(id);

        var nameToUpdate = company.getName() == null ? companyNeedToUpdate.getName() : company.getName();
        var employeesToUpdate = company.getEmployees() == null ? companyNeedToUpdate.getEmployees() : company.getEmployees();

        final var companyToUpdate = new Company(id,nameToUpdate,employeesToUpdate);
        return companyInMemoryRepository.updateCompany(id, companyToUpdate);
    }
}
