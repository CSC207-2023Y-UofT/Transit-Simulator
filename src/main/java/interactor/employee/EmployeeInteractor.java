package interactor.employee;

import employee.*;
import model.control.TransitModel;
import model.train.Train;
import model.train.TrainRole;
import model.train.TrainRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class EmployeeInteractor implements IEmployeeInteractor {
    private final EmployeeTracker tracker;
    private final TransitModel model;

    public EmployeeInteractor(EmployeeTracker track, TransitModel model){
        this.tracker = track;
        this.model = model;
    }


    @Override
    public EmployeeInfo registerEmployee(RegisterEmployeeRequest requestModel) {
        switch (requestModel.type){
            case ENGINEER:
                TrainEngineer eng = new TrainEngineer(ThreadLocalRandom.current().nextInt(999999999));
                tracker.addEmployee(eng);
                return new EmployeeInfo(eng.getStaffNumber(), eng.getEmployeeType());
            case OPERATOR:
                TrainOperator ope = new TrainOperator(ThreadLocalRandom.current().nextInt(999999999));
                tracker.addEmployee(ope);
                return new EmployeeInfo(ope.getStaffNumber(), ope.getEmployeeType());
            case ADMINISTRATOR:
                Admin adm = new Admin(ThreadLocalRandom.current().nextInt(999999999));
                tracker.addEmployee(adm);
                return new EmployeeInfo(adm.getStaffNumber(), adm.getEmployeeType());
        }
        return null;
    }

    @Override
    public Optional<EmployeeInfo> getEmployeeInfo(int staffNumber) {
        List<Employee> lst = tracker.getEmployeeList();
        for(Employee emp: lst) {
            if (emp.getStaffNumber() == staffNumber) {
                return Optional.of(new EmployeeInfo(emp.getStaffNumber(), emp.getEmployeeType()));
            }
        }
        return Optional.empty();


    }

    @Override
    public void removeEmployee(int staffNumber) {
        List<Employee> lst = tracker.getEmployeeList();
        for (Employee emp : lst) {
            if (emp.getStaffNumber() == staffNumber) {
                tracker.removeEmployee(emp);

            }
        }
    }

    @Override
    public boolean assignJob(int staffNumber, String trainName, TrainRole job) {
        List<Employee> Emplst = tracker.getEmployeeList();
        for(Employee emp: Emplst) {
            if (emp.getStaffNumber() == staffNumber) {
                Train train = model.getTrain(trainName);
                if (train == null) return false;
                train.setStaff(job, emp);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unassign(int staffNumber) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);
        if (employee == null) return false;
        for (Train train : model.getTrainList()) {
            Map<TrainRole, Employee> employeeMap = new HashMap<>(train.getStaff());
            for (Map.Entry<TrainRole, Employee> entry : employeeMap.entrySet()) {
                if (entry.getValue().equals(employee)) {
                    train.removeStaff(entry.getKey());
                    return true;
                }
            }
        }
        return false;
    }
}
