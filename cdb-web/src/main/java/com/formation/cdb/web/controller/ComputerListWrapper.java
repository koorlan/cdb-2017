package com.formation.cdb.web.controller;

import java.util.ArrayList;

import com.formation.cdb.entity.impl.Computer;

public class ComputerListWrapper {
    private ArrayList<Long> computers;

    public ArrayList<Long> getComputers() {
        return computers;
    }

    public void setComputers(ArrayList<Long> computers) {
        this.computers = computers;
    }

}