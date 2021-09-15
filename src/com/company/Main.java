package com.company;

import com.company.data.model.Team;
import com.company.data.repo.impl.EmployeeToProjectRepoImpl;
import com.company.service.EmployeeToProjectService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeToProjectService employeeToProjectService =
                new EmployeeToProjectService(new EmployeeToProjectRepoImpl());
        List<Team> bestTeams = employeeToProjectService.getTheBestTeam();
        switch (bestTeams.size()) {
            case 0:
                System.out.println("There is no best team");
                break;
            case 1:
                System.out.println("The Best Team is: " + bestTeams.get(0));
                break;
            default:
                System.out.println("The best teams are:");
                for (Team team : bestTeams) {
                    System.out.println(team);
                }

        }
    }
}
