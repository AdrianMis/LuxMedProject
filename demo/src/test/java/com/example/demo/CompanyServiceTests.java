package com.example.demo;

import com.example.demo.entity.Company;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CompanyServiceTests {

	@InjectMocks
	private CompanyService companyService;

	@Mock
	private CompanyRepository companyRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldReturnAllCompanies() {
		List<Company> companies = new ArrayList<>();
		companies.add(new Company(1L, "Company A", new ArrayList<>()));
		companies.add(new Company(2L, "Company B", new ArrayList<>()));

		when(companyRepository.findAll()).thenReturn(companies);

		List<Company> result = companyService.getAllCompanies();

		assertThat(result).hasSize(2);
		verify(companyRepository, times(1)).findAll();
	}

	@Test
	void shouldReturnCompanyById() {
		Company company = new Company(1L, "Company A", new ArrayList<>());

		when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

		Optional<Company> result = companyService.getCompanyById(1L);

		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Company A");
		verify(companyRepository, times(1)).findById(1L);
	}

	@Test
	void shouldCreateNewCompany() {
		Company company = new Company(null, "Company A", new ArrayList<>());
		Company savedCompany = new Company(1L, "Company A", new ArrayList<>());

		when(companyRepository.save(company)).thenReturn(savedCompany);

		Company result = companyService.createCompany(company);

		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getName()).isEqualTo("Company A");
		verify(companyRepository, times(1)).save(company);
	}

	@Test
	void shouldUpdateExistingCompany() {
		Company existingCompany = new Company(1L, "Old Company", new ArrayList<>());
		Company updatedCompany = new Company(1L, "Updated Company", new ArrayList<>());

		when(companyRepository.findById(1L)).thenReturn(Optional.of(existingCompany));
		when(companyRepository.save(existingCompany)).thenReturn(updatedCompany);

		Company result = companyService.updateCompany(1L, updatedCompany);

		assertThat(result.getName()).isEqualTo("Updated Company");
		verify(companyRepository, times(1)).findById(1L);
		verify(companyRepository, times(1)).save(existingCompany);
	}

	@Test
	void shouldDeleteCompany() {
		doNothing().when(companyRepository).deleteById(1L);

		companyService.deleteCompany(1L);

		verify(companyRepository, times(1)).deleteById(1L);
	}
}