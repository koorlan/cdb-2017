package com.formation.cdb.mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.formation.cdb.dto.CompanyDto;
import com.formation.cdb.entity.impl.Company;

public class CompanyDtoMapperTest {

    @Test
    public void mapCompanyFromCompanyDto_Empty() throws Exception {
        Optional<Company> companyOptional;
        Optional<CompanyDto> companyDTO = Optional.empty();
        companyOptional = CompanyDtoMapper.mapCompanyFromCompanyDto(companyDTO);
        assertFalse(companyOptional.isPresent());
    }

    @Test
    public void mapCompanyFromCompanyDto_IdNeg() throws Exception {

        long id = -1;
        String name = "FABLER BJÖRN";

        Optional<Company> companyOptional;
        CompanyDto companyDTO = new CompanyDto.CompanyDtoBuilder(id, name).build();
        companyOptional = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.ofNullable(companyDTO));

        assertFalse(companyOptional.isPresent());
    }

    @Test
    public void mapCompanyFromCompanyDto_NameBlank() throws Exception {

        long id = 42;
        String name = "";

        Optional<Company> companyOptional;
        CompanyDto companyDTO = new CompanyDto.CompanyDtoBuilder(id, name).build();
        companyOptional = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.ofNullable(companyDTO));

        assertFalse(companyOptional.isPresent());
    }

    @Test
    public void mapCompanyFromCompanyDto_Full() throws Exception {

        long id = 42;
        String name = "FABLER BJÖRN";

        Optional<Company> companyOptional;
        CompanyDto companyDTO = new CompanyDto.CompanyDtoBuilder(id, name).build();
        companyOptional = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.ofNullable(companyDTO));
        assertTrue(companyOptional.isPresent());
        Company company = companyOptional.get();

        assertEquals(id, company.getId());
        assertEquals(name, company.getName().get());

        assertEquals(companyDTO.getId(), company.getId());
        assertEquals(companyDTO.getName(), company.getName().get());
    }

    public void mapCompanyDtoFromCompany_Empty() throws Exception {
        Optional<Company> companyOptional = Optional.empty();
        Optional<CompanyDto> companyDTO;
        companyDTO = CompanyDtoMapper.mapCompanyDtoFromCompany(companyOptional);
        assertFalse(companyDTO.isPresent());
    }

    @Test
    public void mapCompanyDtoFromCompany_IdNeg() throws Exception {

        long id = -1;
        String name = "FABLER BJÖRN";
        Company c = new Company.CompanyBuilder(id, name).build();
        Optional<Company> companyOptional = Optional.of(c);
        Optional<CompanyDto> companyDTO = CompanyDtoMapper.mapCompanyDtoFromCompany(companyOptional);

        assertFalse(companyDTO.isPresent());
    }

    @Test
    public void mapCompanyDtoFromCompany_NameBlank() throws Exception {

        long id = 42;
        String name = "";
        Company c = new Company.CompanyBuilder(id, name).build();
        Optional<Company> companyOptional = Optional.of(c);
        Optional<CompanyDto> companyDTO = CompanyDtoMapper.mapCompanyDtoFromCompany(companyOptional);

        assertFalse(companyDTO.isPresent());
    }

    @Test
    public void mapCompanyDtoFromCompany_Full() throws Exception {

        long id = 42;
        String name = "FABLER BJÖRN";

        Company c = new Company.CompanyBuilder(id, name).build();

        Optional<Company> companyOptional = Optional.of(c);
        Optional<CompanyDto> companyDTO = CompanyDtoMapper.mapCompanyDtoFromCompany(companyOptional);

        assertTrue(companyDTO.isPresent());

        assertEquals(id, companyDTO.get().getId());
        assertEquals(name, companyDTO.get().getName());

        assertEquals(companyOptional.get().getId(), companyDTO.get().getId());
        assertEquals(companyOptional.get().getName().get(), companyDTO.get().getName());

    }

    @Test(expected=NullPointerException.class)
    public void mapCompaniesDtoFromCompanies_Null() throws Exception {
        List<Company> list = null;

        List<CompanyDto> listDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(list);

    }

    @Test
    public void mapCompaniesDtoFromCompanies_Empty() throws Exception {
        List<Company> list = new ArrayList<>();

        List<CompanyDto> listDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(list);
        assertTrue(listDto.isEmpty());
    }

    @Test
    public void mapCompaniesDtoFromCompanies_OneError() throws Exception {
        List<Company> list = new ArrayList<>();

        long id1 = 42;
        String name1 = "FABLER BJÖRN";

        // Company non valide
        long id2 = -42;
        String name2 = "FABLER BJÖRN2";

        Company c1 = new Company.CompanyBuilder(id1, name1).build();
        Company c2 = new Company.CompanyBuilder(id2, name2).build();

        list.add(c1);
        list.add(c2);

        List<CompanyDto> listDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(list);
        assertTrue(listDto.size() == 1);

        assertEquals(c1.getId(), listDto.get(0).getId());
        assertEquals(c1.getName().get(), listDto.get(0).getName());
    }

    @Test
    public void mapCompaniesDtoFromCompanies_NoError() throws Exception {
        List<Company> list = new ArrayList<>();

        long id1 = 42;
        String name1 = "FABLER BJÖRN";

        long id2 = 33;
        String name2 = "FABLER BJÖRN2";

        Company c1 = new Company.CompanyBuilder(id1, name1).build();
        Company c2 = new Company.CompanyBuilder(id2, name2).build();

        list.add(c1);
        list.add(c2);

        List<CompanyDto> listDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(list);
        assertTrue(listDto.size() == 2);

        assertEquals(c1.getId(), listDto.get(0).getId());
        assertEquals(c1.getName().get(), listDto.get(0).getName());

        assertEquals(c2.getId(), listDto.get(1).getId());
        assertEquals(c2.getName().get(), listDto.get(1).getName());
    }

}
