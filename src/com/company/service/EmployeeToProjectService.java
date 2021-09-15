package com.company.service;


import com.company.data.model.EmployeeToProject;
import com.company.data.model.Team;
import com.company.data.model.Employee;
import com.company.data.model.Project;
import com.company.data.repo.EmployeeToProjectRepo;

import java.time.LocalDate;
import java.util.*;


import static java.time.temporal.ChronoUnit.DAYS;

public class EmployeeToProjectService {

    private final List<EmployeeToProject> employeeToProjectList;

    public EmployeeToProjectService(EmployeeToProjectRepo employeeToProjectRepo) {
        this.employeeToProjectList = employeeToProjectRepo.getAllRecords();
    }

    public List<Team> getTheBestTeam() {
        Map<String, Team> teams = getAllTeams();
        List<Team> bestTeams = new ArrayList<>();
        Long maxDaysWorkedByTeam = teams.values().stream()
                .map(Team::getTogetherWorkDays).max(Long::compareTo).get();

        for (Map.Entry<String, Team> team : teams.entrySet()) {
            if (maxDaysWorkedByTeam == team.getValue().getTogetherWorkDays()){
                bestTeams.add(team.getValue());
            }
        }
        return bestTeams;
    }

    private Map<String, Team> getAllTeams() {
        int recordCount = employeeToProjectList.size();
        Map<String, Team> teamMap = new HashMap<>();
        EmployeeToProject firstEmployeeToProject = null;
        EmployeeToProject secondEmployeeToProject = null;
        for (int i = 0; i < recordCount; i++) {
            for (int j = i + 1; j < recordCount; j++) {
                firstEmployeeToProject = employeeToProjectList.get(i);
                secondEmployeeToProject = employeeToProjectList.get(j);
                if (isSameProject(firstEmployeeToProject, secondEmployeeToProject)) {
                    if (hasNotWorkTogether(firstEmployeeToProject, secondEmployeeToProject)) {
                        continue;
                    } else {
                        Team team = createTeam(firstEmployeeToProject, secondEmployeeToProject);
                        int firstTeammateId = firstEmployeeToProject.getEmployee().getId();
                        int secondTeammateId = secondEmployeeToProject.getEmployee().getId();
                        String teamIdVersionOne = firstTeammateId + "-" + secondTeammateId;
                        String teamIdVersionTwo = secondTeammateId + "-" + firstTeammateId;
                        if (!teamMap.containsKey(teamIdVersionOne)
                                && !teamMap.containsKey(teamIdVersionTwo)) {
                            teamMap.put(teamIdVersionOne, team);
                        } else {
                            updateTeam(teamMap, team, teamIdVersionOne, teamIdVersionTwo);
                        }
                    }
                }
            }
        }
        return teamMap;

    }

    private void updateTeam(Map<String, Team> teamMap, Team team, String key1, String key2) {
        Team existedTeam = teamMap.get(key1) != null ? teamMap.get(key1) : teamMap.get(key2);
        existedTeam.setTogetherWorkDays(existedTeam.getTogetherWorkDays() + team.getTogetherWorkDays());
        team.getProjects().stream().forEach(
                p -> {
                    if (!existedTeam.getProjects().contains(p)) {
                        existedTeam.getProjects().add(p);
                    }
                }
        );
    }


    private boolean isSameProject(EmployeeToProject record1, EmployeeToProject record2) {
        return record1.getProject().equals(record2.getProject());
    }

    private boolean hasNotWorkTogether(EmployeeToProject record1, EmployeeToProject record2) {
        return record1.getDateFrom().isAfter(record2.getDateTo())
                || record2.getDateFrom().isAfter(record1.getDateTo());
    }

    private Team createTeam(EmployeeToProject record1, EmployeeToProject record2) {
        Team team = new Team();
        Employee[] employees = new Employee[2];
        Project project = record1.getProject();
        employees[0] = record1.getEmployee();
        employees[1] = record2.getEmployee();
        long workedDays = computeWorkDays(new EmployeeToProject[]{record1, record2});
        team.setTeam(employees);
        team.setTogetherWorkDays(workedDays);
        team.getProjects().add(project);
        return team;
    }

    private long computeWorkDays(EmployeeToProject[] records) {
        EmployeeToProject employeeOne = records[0];
        EmployeeToProject employeeTwo = records[1];
        LocalDate partnershipStartDate;
        LocalDate partnershipEndDate;
        if (employeeOne.getDateFrom().isAfter(employeeTwo.getDateFrom())) {
            partnershipStartDate = employeeOne.getDateFrom();
        } else {
            partnershipStartDate = employeeTwo.getDateFrom();
        }
        if (employeeOne.getDateTo().isBefore(employeeTwo.getDateTo())) {
            partnershipEndDate = employeeOne.getDateTo();
        } else {
            partnershipEndDate = employeeTwo.getDateTo();
        }
        return DAYS.between(partnershipStartDate, partnershipEndDate) + 1;
    }

}
