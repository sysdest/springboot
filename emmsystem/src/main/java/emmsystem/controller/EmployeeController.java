package emmsystem.controller;

import emmsystem.dao.DepartmentDao;
import emmsystem.dao.EmployeeDao;
import emmsystem.pojo.Department;
import emmsystem.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    @RequestMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("emps",employees);
;        return "emp/list";
    }
    @GetMapping("/emp")
    public String toAddPage(Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emp/add";
    }


    @PostMapping("/emp")
    public String AddEmp(Employee employee){
        //添加的操作
        employeeDao.save(employee);//调用底层业务方法保存员工信息
        return "redirect:/emps";
    }

    //去员工的修改页面
    @GetMapping("/emp/{id}")
    public  String toUpdateEmp(@PathVariable("id")Integer id,Model model){
        //查原来的数据
        Employee employee = employeeDao.getEmployeeById(id);
        model.addAttribute("emp",employee);

        //查出所有部门的信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emp/update";
    }
    //员工信息修改
    @PostMapping("/updateEmp")
    public  String updateEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //删除员工
    @GetMapping("/delemp/{id}")
    public String deleteEmp(@PathVariable("id")int id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
