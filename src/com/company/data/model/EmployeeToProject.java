package com.company.data.model;

import java.time.LocalDate;

public class EmployeeToProject {
    private Employee employee;
    private Project project;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public EmployeeToProject() {
    }

    public EmployeeToProject(Employee employee, Project project, LocalDate dateFrom, LocalDate dateTo) {
        this.employee = employee;
        this.project = project;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
