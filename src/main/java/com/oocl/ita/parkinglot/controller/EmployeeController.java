package com.oocl.ita.parkinglot.controller;

import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/{id}/parking-lots")
    public ResponseEntity getEmployeeAllParkingLots(@PathVariable(value = "id") String id){
        List<ParkingLot> employeeAllParkingLots = employeeService.getEmployeeAllParkingLots(id);
        return (employeeAllParkingLots == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(employeeAllParkingLots);
    }
}
