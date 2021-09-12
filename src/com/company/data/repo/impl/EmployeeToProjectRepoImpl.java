package com.company.data.repo.impl;

import com.company.data.model.EmployeeToProject;
import com.company.data.model.Employee;
import com.company.data.model.Project;
import com.company.data.repo.EmployeeToProjectRepo;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeToProjectRepoImpl implements EmployeeToProjectRepo {
    private static final String FILE_NAME = "src/resources/employee_project_relation.txt";
    private static final String ENCODING = "UTF-8";

    public EmployeeToProjectRepoImpl() {}

    @Override
    public List<EmployeeToProject> getAllRecords() {

        List<EmployeeToProject> employeeToProjectList = new ArrayList<>();
        File file = new File(FILE_NAME);
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(file, ENCODING);
            while (fileReader.hasNextLine()) {
                EmployeeToProject employeeToProject = new EmployeeToProject();
                String recordLine = fileReader.nextLine();
                String[] recordElements = recordLine.split("[ ,]+");
                int employeeId = Integer.parseInt(recordElements[0]);
                int projectId = Integer.parseInt(recordElements[1]);
                LocalDate dateFrom = LocalDate.parse(recordElements[2]);
                LocalDate dateTo;
                if (recordElements[3].equals("null")) {
                    dateTo = LocalDate.now();
                } else {
                    dateTo = LocalDate.parse(recordElements[3]);
                }

                employeeToProject.setEmployee(new Employee(employeeId));
                employeeToProject.setProject(new Project(projectId, "Project " + projectId));
                employeeToProject.setDateFrom(dateFrom);
                employeeToProject.setDateTo(dateTo);

                employeeToProjectList.add(employeeToProject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            fileReader.close();
        }
        return employeeToProjectList;
    }

}
