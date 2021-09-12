package com.company.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Team {
    private Employee[] team;
    private long togetherWorkDays;
    private List<Project> projects;

    public Team() {
        this.team= new Employee[2];
        this.projects=new ArrayList<>();
    }


    public Employee[] getTeam() {
        return team;
    }

    public void setTeam(Employee[] team) {
        this.team = team;
    }

    public long getTogetherWorkDays() {
        return togetherWorkDays;
    }

    public void setTogetherWorkDays(long togetherWorkDays) {
        this.togetherWorkDays = togetherWorkDays;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Team{" +
                "Employee Id=" + team[0].getId() +
                " Employee Id=" + team[1].getId() +
                ", Together Worked Days=" + togetherWorkDays +
                ", in Projects= " + projects.stream().map(Project::toString).collect(Collectors.joining(", ")) +
                '}';
    }
}
