package org.structure.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Employee {
    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private String firstName;
    @CsvBindByPosition(position = 2)
    private String lastName;
    @CsvBindByPosition(position = 3)
    private String salary;
    @CsvBindByPosition(position = 4)
    private String managerId;
}
