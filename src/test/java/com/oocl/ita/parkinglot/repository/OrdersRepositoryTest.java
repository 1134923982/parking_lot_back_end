package com.oocl.ita.parkinglot.repository;

import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.sun.javaws.jnl.ResourcesDesc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrdersRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private OrdersRepository ordersRepository;

    @Before
    public void setUp() throws Exception {
        Employee manager = new Employee("manager","1","male","123",1,"");
        Employee parkingBoy1 = new Employee("boy1","2","male","123",1,"1");
        Employee parkingBoy2 = new Employee("boy2","3","male","123",1,"1");
        Customer customer = new Customer();


        manager.setId("1");
        manager.setRole(1);
        parkingBoy1.setId("2");
        parkingBoy2.setId("3");
        parkingBoy1.setRole(0);
        parkingBoy2.setRole(0);
        Orders order1 = new Orders("1",2,customer,"","");
        order1.setId("1");
        order1.setParkingBoy(parkingBoy2);
        Orders order2 = new Orders("2",2,customer,"","");
        order2.setFetchingBoy(parkingBoy2);
        order2.setId("2");

        entityManager.persist(manager);
        entityManager.persist(parkingBoy1);
        entityManager.persist(parkingBoy2);
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
    }

    @Test
    public void find_all_parking_car_orders_by_manager_id() {
        List<Orders> ordersByManagerId = ordersRepository.findParkingCarOrdersByManagerId("1");
        assertEquals(1, ordersByManagerId.size());
        assertEquals("1", ordersByManagerId.get(0).getId());
    }

    @Test
    public void find_all_fetching_car_orders_by_manager_id() {
        List<Orders> ordersByManagerId = ordersRepository.findFetchingCarOrdersByManagerId("1");
        assertEquals(1, ordersByManagerId.size());
        assertEquals("2", ordersByManagerId.get(0).getId());
    }
}