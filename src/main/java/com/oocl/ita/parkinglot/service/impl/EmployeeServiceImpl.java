package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;
import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
import com.oocl.ita.parkinglot.enums.ParkingLotStatusEnum;
import com.oocl.ita.parkinglot.enums.RoleEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.repository.ParkingLotRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import com.oocl.ita.parkinglot.utils.converters.EmployeeToEmployeeVOConverter;
import com.oocl.ita.parkinglot.vo.EmployeesVO;
import com.oocl.ita.parkinglot.vo.PageVO;
import com.oocl.ita.parkinglot.vo.ParkingLotVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.CREATE_ERROR;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public PageVO<ParkingLot> getEmployeeAllParkingLots(String Id) {
        Employee employee = employeeRepository.findById(Id).orElse(null);
        if (employee == null) {
            throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
        } else {
            List<ParkingLot> parkingLots = employee.getParkingLots().stream()
                    .filter(element -> element.getStatus() == ParkingLotStatusEnum.EXIST.ordinal())
                    .collect(Collectors.toList());
            PageVO<ParkingLot> parkingLotPageVO = new PageVO<>();
            parkingLotPageVO.setPageContent(parkingLots);
            return parkingLotPageVO;
        }

    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public List<Orders> getEmployeeOrdersByFinish(String id, boolean finish) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null)
            return null;
        else if (employee.getRole() == 0) {
            if (!finish) {
                return getUnFinishOrdersByParkingBoyId(id);
            } else {
                return getFinishOrdersByParkingBoyId(id);
            }
        } else {
            return getOrdersByManagerId(employee);
        }
    }

    private List<Orders> getOrdersByManagerId(Employee employee) {
        List<Orders> fetchingCarOrdersByManagerId = ordersRepository.findFetchingCarOrdersByManagerId(employee.getId());
        fetchingCarOrdersByManagerId = fetchingCarOrdersByManagerId.stream().map(orders -> new Orders(orders.getId(), orders.getOrderNumber(), orders.getStatus(), orders.getCarNumber(), orders.getParkingLot())).collect(Collectors.toList());
        List<Orders> parkingCarOrdersByManagerId = ordersRepository.findParkingCarOrdersByManagerId(employee.getId());
        parkingCarOrdersByManagerId = parkingCarOrdersByManagerId.stream().map(orders -> new Orders(orders.getId(), orders.getOrderNumber(), orders.getStatus(), orders.getCarNumber(), orders.getParkingLot())).collect(Collectors.toList());
        parkingCarOrdersByManagerId.addAll(fetchingCarOrdersByManagerId);
        return parkingCarOrdersByManagerId;
    }

    private List<Orders> getFinishOrdersByParkingBoyId(String id) {
        List<Orders> parkingFinishOrders = ordersRepository.findEmployeeParkingFinishOrders(id);

        for (Orders order : parkingFinishOrders) {
            order.setStatus(OrdersStatusEnum.PARK_ORDER_CAR_IS_PARKED_AND_FETCH_ORDER_NOT_RECEIVED.ordinal());
        }

        List<Orders> findFetchingFinishOrders = ordersRepository.findEmployeeFetchingFinishOrders(id);
        ArrayList<Orders> cloneOrders = new ArrayList<>();

        for (Orders order : findFetchingFinishOrders) {
            if (parkingFinishOrders.contains(order)) {
                Orders cloneOrder = new Orders();
                cloneOrder.setStatus(OrdersStatusEnum.FETCH_ORDER_COMPLETED.ordinal());
                BeanUtils.copyProperties(order, cloneOrder, "status");
                cloneOrders.add(cloneOrder);
            }
        }

        List<Orders> resultFetchingFinishOrders = findFetchingFinishOrders.stream()
                .filter(element -> !parkingFinishOrders.contains(element))
                .collect(Collectors.toList());

        ArrayList<Orders> finishOrders = new ArrayList<>();
        finishOrders.addAll(parkingFinishOrders);
        finishOrders.addAll(resultFetchingFinishOrders);
        finishOrders.addAll(cloneOrders);
        return finishOrders;
    }

    private List<Orders> getUnFinishOrdersByParkingBoyId(String id) {
        List<Orders> orders = ordersRepository.findEmployeeFinishOrders(id);

        List<Orders> unFinishOrders = orders.stream()
                .filter(element -> element.getStatus() == OrdersStatusEnum.PARK_ORDER_RECEIVED.ordinal()
                        || element.getStatus() == OrdersStatusEnum.PARK_ORDER_PICKED_UP_THE_CAR.ordinal()
                        || element.getStatus() == OrdersStatusEnum.FETCH_ORDER_RECEIVED.ordinal()
                        || element.getStatus() == OrdersStatusEnum.FETCH_ORDER_PICKED_UP_THE_CAR.ordinal())
                .collect(Collectors.toList());

        return unFinishOrders;
    }

    @Override
    public List<ParkingLotVO> getParkingLotVOsByEmployeeId(String id, int page, int pageSize) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            List<ParkingLot> parkingLots = employee.getParkingLots();
            List<ParkingLotVO> parkingLotVOs = parkingLots.stream()
                    .map(parkingLot -> new ParkingLotVO(parkingLot.getId(), parkingLot.getName(), parkingLot.getPosition(), parkingLot.getCapacity(), parkingLot.getNowAvailable()))
                    .collect(Collectors.toList());
            for (int i = 0; i < parkingLots.size(); i++) {
                List<Employee> employees = employeeRepository.findEmployeesByParkingLotsContains(parkingLots.get(i));
                employees = employees.stream().filter(e -> e.getRole() == 0).collect(Collectors.toList());
                List<Employee> resultEmployees = new ArrayList<>();
                for (int j = 0; j < employees.size(); j++) {
                    Employee resultEmployee = new Employee();
                    BeanUtils.copyProperties(employees.get(j), resultEmployee, "password", "parkingLots");
                    resultEmployees.add(resultEmployee);
                }
                parkingLotVOs.get(i).setParkingBoys(resultEmployees);
            }
            if ((page - 1) * pageSize > parkingLotVOs.size()) {
                return new ArrayList<>();
            }
            if (page * pageSize < parkingLotVOs.size()) {
                parkingLotVOs = parkingLotVOs.subList((page - 1) * pageSize, page * pageSize);
            } else {
                parkingLotVOs = parkingLotVOs.subList((page - 1) * pageSize, parkingLotVOs.size());
            }
            return parkingLotVOs;
        }
        return null;
    }


    @Override
    public ParkingLot addEmployeeNewParkingLot(String id, ParkingLot parkingLot) {

        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
        } else {
            parkingLot.setStatus(ParkingLotStatusEnum.EXIST.ordinal());
            parkingLot.setNowAvailable(parkingLot.getCapacity());
            ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);
            if (savedParkingLot == null) {
                throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
            } else {
                employee.getParkingLots().add(savedParkingLot);
                Employee savedEmployee = employeeRepository.save(employee);
                if (savedEmployee == null) {
                    throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
                } else {
                    return savedParkingLot;
                }

            }
        }

    }

    @Override
    public ParkingLot updateParkingLotByEmployeeId(String id, ParkingLot parkingLot) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
        } else {
            ParkingLot findParkingLot = employee.getParkingLots().stream().filter(element -> element.getId().equals(parkingLot.getId())).findAny().orElse(null);
            if (findParkingLot == null) {
                throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
            } else {
                BeanUtils.copyProperties(parkingLot, findParkingLot, "id", "nowAvailable", "name", "position");
                return parkingLotRepository.save(findParkingLot);
            }
        }
    }

    @Override
    public List<EmployeesVO> getParkingBoyByManagedId(String id) {
        List<Employee> employees = employeeRepository.findByManagedId(id);
        if (employees == null) {
            return null;
        }

        return EmployeeToEmployeeVOConverter.convert(employees);
    }

    @Override
    public List<Employee> findAllEmployees(int role) {
        return role >= RoleEnum.parkingBoy.ordinal() ? employeeRepository.findByRole(role) : employeeRepository.findAll();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employee.getRole() < RoleEnum.admin.ordinal()) {
            return employeeRepository.save(employee);
        } else {
            throw new ParkingLotException(CREATE_ERROR);
        }
    }
}
