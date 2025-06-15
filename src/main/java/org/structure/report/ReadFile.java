package org.structure.report;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.structure.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFile {

    public void readAndMapFile() {
        OrganizationReport organizationReport = new OrganizationReport();
        try {
            List<Employee> employees = mapFileToModelObject().stream().skip(1).collect(Collectors.toList());
            organizationReport.earningsOfManager(employees);
        } catch (IOException ioException) {
            System.out.println("Exception while reading a file " + ioException);
        }
    }

    private List<Employee> mapFileToModelObject() throws IOException {
        Reader reader = new BufferedReader(new FileReader("src/main/resources/org-employee-structure.csv"));
        CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                .withIgnoreQuotations(true)
                .withType(Employee.class)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }
}
