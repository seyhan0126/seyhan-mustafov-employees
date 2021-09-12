package com.company;

import com.company.data.repo.impl.EmployeeToProjectRepoImpl;
import com.company.service.EmployeeToProjectService;

public class Main {
    public static void main(String[] args) {
        EmployeeToProjectService employeeToProjectService =
                new EmployeeToProjectService(new EmployeeToProjectRepoImpl());
        System.out.println("The Best Team is: "+employeeToProjectService.getTheBestTeam());
    }
}
