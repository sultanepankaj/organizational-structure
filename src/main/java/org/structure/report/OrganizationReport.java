package org.structure.report;

import org.apache.commons.lang3.StringUtils;
import org.structure.model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class OrganizationReport {
    public void earningsOfManager(List<Employee> employees) {

        List<String> managerIds = employees.stream().map(Employee::getManagerId).filter(Objects::nonNull).distinct()
                .collect(Collectors.toList());
        managerIds.forEach(managerId -> {
            calculateManagerEarnings(managerId, employees);
        });
        findEmployeeWithLongManagerList(employees);
    }

    private void findEmployeeWithLongManagerList(List<Employee> employees) {
        Map<String, List<String>> managerToEmployees = new HashMap<>();
        Map<String, Employee> employeeMap = new HashMap<>();
        for (Employee employee : employees) {
            employeeMap.put(employee.getId(), employee);
            managerToEmployees.computeIfAbsent(employee.getManagerId(), e -> new ArrayList<>()).add(employee.getId());
        }
        String ceoEmployeeId = getCeoEmployeeId(employees);
        Map<String, Integer> employeeDepthCounts = new HashMap<>();
        Map<String, Integer> employeeDepths = findEmployeeDepth(ceoEmployeeId, 0, managerToEmployees, employeeMap, employeeDepthCounts);
        for (Map.Entry<String, Integer> employeeDepth : employeeDepths.entrySet()) {
            System.out.println(" Employee Id " + employeeDepth.getKey() + " Employee manager depth or lines " + employeeDepth.getValue());
        }
    }

    private Map<String, Integer> findEmployeeDepth(String employeeId, int depthCount, Map<String, List<String>> managerToEmployees, Map<String, Employee> employeeMap, Map<String, Integer> employeeDepthCounts) {
        employeeDepthCounts.put(employeeId, depthCount);
        List<String> subordinates = managerToEmployees.get(employeeId);
        if (subordinates != null) {
            for (String empId : subordinates) {
                findEmployeeDepth(empId, depthCount + 1, managerToEmployees, employeeMap, employeeDepthCounts);
            }
        }
        return employeeDepthCounts;
    }

    private String getCeoEmployeeId(List<Employee> employees) {
        String ceoEmployeeId = "";
        for (Employee employee : employees) {
            if (StringUtils.isEmpty(employee.getManagerId())) {
                ceoEmployeeId = employee.getId();
            }
        }
        return ceoEmployeeId;
    }

    private void calculateManagerEarnings(String managerId, List<Employee> employees) {
        OptionalDouble subordinateAverageSalary = employees.stream().filter(employee -> StringUtils.isNotEmpty(employee.getManagerId()) && managerId.equals(employee.getManagerId())).
                mapToInt((e) -> Integer.parseInt(e.getSalary())).average();
        if (subordinateAverageSalary.isPresent()) {
            int averageSalary = (int) subordinateAverageSalary.getAsDouble();
            int twentyPercentageOfAverageSalary = new Double(averageSalary * 0.20).intValue();
            int fiftyPercentageOfAverageSalary = new Double(averageSalary * 0.50).intValue();
            String employeeName = employees.stream().filter(employee -> managerId.equals(employee.getId())).map(e -> String.format("%s %s", e.getFirstName(), e.getLastName())).collect(Collectors.joining());
            String managerSalary = employees.stream().filter(employee -> managerId.equals(employee.getId())).map(Employee::getSalary).collect(Collectors.joining());
            int managerCurrentSalary = Integer.parseInt(managerSalary);
            int allowedManagerSalaryByTwentyPercentage = Integer.sum(averageSalary, twentyPercentageOfAverageSalary);
            int allowedManagerSalaryByFiftyPercentage = Integer.sum(averageSalary, fiftyPercentageOfAverageSalary);
            if (allowedManagerSalaryByTwentyPercentage == managerCurrentSalary || allowedManagerSalaryByFiftyPercentage == managerCurrentSalary) {
                System.out.println("Manager Id " + managerId + " Manager Name : " + employeeName + " have same salary " + managerCurrentSalary);
            } else {
                checkAndReportManagerEarnings(managerId, employeeName, managerCurrentSalary, allowedManagerSalaryByTwentyPercentage, allowedManagerSalaryByFiftyPercentage);
            }

        }
    }

    private void checkAndReportManagerEarnings(String managerId, String employeeName, int managerCurrentSalary, int allowedManagerSalaryByTwentyPercentage,
                                               int allowedManagerSalaryByFiftyPercentage) {
        int earnings = 0;
        String message = "";
        if (managerCurrentSalary < allowedManagerSalaryByTwentyPercentage) {
            earnings = allowedManagerSalaryByTwentyPercentage - managerCurrentSalary;
            message = " earn less than they should by ";
        } else if (managerCurrentSalary > allowedManagerSalaryByFiftyPercentage) {
            earnings = managerCurrentSalary - allowedManagerSalaryByFiftyPercentage;
            message = " earn more than they should by ";
        } else {
            earnings = managerCurrentSalary;
            message = " earns as expected ";
        }
        System.out.println("Manager Id " + managerId + " Manager Name : " + employeeName + message + earnings);

    }
}