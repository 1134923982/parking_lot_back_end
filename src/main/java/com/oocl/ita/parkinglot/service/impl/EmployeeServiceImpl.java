package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.dto.GetEmployeeParkingLotDTO;
import com.oocl.ita.parkinglot.enums.CodeMsgEnum;
import com.oocl.ita.parkinglot.enums.OrdersStatusEnum;
import com.oocl.ita.parkinglot.enums.ParkingLotStatusEnum;
import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.Orders;
import com.oocl.ita.parkinglot.model.ParkingLot;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.repository.OrdersRepository;
import com.oocl.ita.parkinglot.repository.ParkingLotRepository;
import com.oocl.ita.parkinglot.service.EmployeeService;
import com.oocl.ita.parkinglot.vo.PageVO;
import com.oocl.ita.parkinglot.vo.ParkingLotVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public List<ParkingLot> getEmployeeAllParkingLots(String parkingBoyId) {
        Employee employee = employeeRepository.findById(parkingBoyId).orElse(null);
        if (employee == null) {
            return null;
        } else {
            return employee.getParkingLots().stream()
                    .filter(element -> element.getStatus() == ParkingLotStatusEnum.EXIST.ordinal())
                    .collect(Collectors.toList());
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
        else {
            if (!finish) {
                List<Orders> orders = ordersRepository.findEmployeeUnfinishOrders(id);

                List<Orders> unfinishOrders = orders.stream()
                        .filter(element -> element.getStatus() == OrdersStatusEnum.PARK_ORDER_RECEIVED.ordinal()
                                || element.getStatus() == OrdersStatusEnum.PARK_ORDER_PICKED_UP_THE_CAR.ordinal()
                                || element.getStatus() == OrdersStatusEnum.FETCH_ORDER_RECEIVED.ordinal()
                                || element.getStatus() == OrdersStatusEnum.FETCH_ORDER_PICKED_UP_THE_CAR.ordinal())
                        .collect(Collectors.toList());

                return unfinishOrders;
            } else {
                List<Orders> parkingFinishorders = ordersRepository.findEmployeeParkingFinishOrders(id);

                for (Orders order : parkingFinishorders) {
                    order.setStatus(OrdersStatusEnum.PARK_ORDER_CAR_IS_PARKED_AND_FETCH_ORDER_NOT_RECEIVED.ordinal());
                }

                List<Orders> findFetchingFinishOrders = ordersRepository.findEmployeeFetchingFinishOrders(id);
                ArrayList<Orders> cloneOrders = new ArrayList<>();

                for (Orders order : findFetchingFinishOrders) {
                    if (parkingFinishorders.contains(order)) {
                        Orders cloneOrder = new Orders();
                        cloneOrder.setStatus(OrdersStatusEnum.FETCH_ORDER_COMPLETED.ordinal());
                        BeanUtils.copyProperties(order, cloneOrder, "status");
                        cloneOrders.add(cloneOrder);
                    }
                }

                List<Orders> resultFetchingFinishOrders = findFetchingFinishOrders.stream()
                        .filter(element -> !parkingFinishorders.contains(element))
                        .collect(Collectors.toList());

                ArrayList<Orders> finishOrders = new ArrayList<>();
                finishOrders.addAll(parkingFinishorders);
                finishOrders.addAll(resultFetchingFinishOrders);
                finishOrders.addAll(cloneOrders);
                return finishOrders;
            }

        }
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

    public PageVO<ParkingLot> findByConditions(String id, GetEmployeeParkingLotDTO getEmployeeParkingLotDTO) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        int page = getEmployeeParkingLotDTO.getPage();
        int pageSize = getEmployeeParkingLotDTO.getPageSize();
        PageVO<ParkingLot> parkingLotPage = new PageVO<ParkingLot>();
        if (employee == null) {
            throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
        }
        if (page == -1 && pageSize == -1) {
            parkingLotPage.setPageContent(employee.getParkingLots());
            parkingLotPage.setTotal(employee.getParkingLots().size());
        } else {
            String name = getEmployeeParkingLotDTO.getName();
            String position = getEmployeeParkingLotDTO.getPosition();
            if (StringUtils.isEmpty(name) && StringUtils.isEmpty(position)) {

                List<ParkingLot> parkingLots = employee.getParkingLots().stream()
                        .filter(element -> element.getStatus() == ParkingLotStatusEnum.EXIST.ordinal())
                        .collect(Collectors.toList());
                int last = page * pageSize < parkingLots.size() ? page * pageSize : parkingLots.size();
                if ((page - 1) * pageSize > parkingLots.size()) {
                    throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
                }
                parkingLotPage.setPageContent(parkingLots.subList((page - 1) * pageSize, last));
                parkingLotPage.setTotal(parkingLots.size());



            } else {
                List<ParkingLot> parkingLots = parkingLotRepository.findAll(new Specification<ParkingLot>() {
                    @Override
                    public Predicate toPredicate(Root<ParkingLot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicateList = new ArrayList<>();
                        if (!StringUtils.isEmpty(name)) {
                            Path namePath = root.get("name");
                            predicateList.add(criteriaBuilder.like(namePath, "%"  + name + "%" ));
                        }
                        if (!StringUtils.isEmpty(position)) {
                            Path positionPath = root.get("position");
                            predicateList.add(criteriaBuilder.like(positionPath, "%" + position + "%"));
                        }
                        Path statusPath = root.get("status");
                        Predicate p = criteriaBuilder.equal(statusPath, 0);
                        predicateList.add(p);

                        Predicate[] predicates = new Predicate[predicateList.size()];
                        predicateList.toArray(predicates);
                        criteriaQuery.where(predicates);
                        return criteriaBuilder.and(predicates);
                    }
                });
                List<ParkingLot> needFilterParkingLots = employee.getParkingLots();

                List<ParkingLot> resultParkingLots = new ArrayList<>();
                for (ParkingLot p : needFilterParkingLots) {
                    for (ParkingLot p2 : parkingLots) {
                        if (p.equals(p2)) {
                            resultParkingLots.add(p);
                            break;
                        }
                    }
                }
                int last = page * pageSize < parkingLots.size() ? page * pageSize : parkingLots.size();
                if ((page - 1) * pageSize > parkingLots.size()) {
                    throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
                }
                parkingLotPage.setPageContent(parkingLots.subList((page - 1) * pageSize, last));
                parkingLotPage.setTotal(parkingLots.size());

            }
        }
        return parkingLotPage;
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

    public int updateEmployeeParkingLotCapacityById(String id, ParkingLot parkingLot) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
        } else {
            ParkingLot findParkingLot = employee.getParkingLots().stream().filter(element -> element.getId().equals(parkingLot.getId())).findAny().orElse(null);
            if (findParkingLot == null) {
                throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
            } else {
                int result = parkingLotRepository.updateCapacityById(parkingLot.getId(), parkingLot.getCapacity());
                if (result == 1) {
                    return result;
                } else {
                    throw new ParkingLotException(CodeMsgEnum.PARAMETER_ERROR);
                }
            }
        }
    }

}
