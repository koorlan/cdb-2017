package com.formation.cdb.dto;


import java.util.List;

public class ComputersApiDto {
    private List<ComputerDto> computers;
    private int total;

    public void setComputers(List<ComputerDto> computers) {
        this.computers = computers;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ComputerDto> getComputers() {
        return computers;
    }

    public int getTotal() {
        return total;
    }
}
