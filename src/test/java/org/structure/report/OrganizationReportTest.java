package org.structure.report;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.structure.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrganizationReportTest {
    @Mock
    private OrganizationReport organizationReport;
    List<Employee> employees = new ArrayList<>();

    @BeforeEach
    void init() {
        try {
            Reader reader = new BufferedReader(new FileReader("src/main/resources/org-employee-structure.csv"));
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withIgnoreQuotations(true)
                    .withType(Employee.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
        } catch (Exception exception) {
            System.out.println("Exception " + exception);
        }
    }

    @Test
    void testEarningsOfManagers() {
        organizationReport.earningsOfManager(employees);
        Mockito.verify(organizationReport, Mockito.times(1)).earningsOfManager(employees);
    }
}