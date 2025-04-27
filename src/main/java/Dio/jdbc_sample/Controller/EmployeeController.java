package Dio.jdbc_sample.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import Dio.jdbc_sample.persistence.EmployeeDAO;
import Dio.jdbc_sample.persistence.entidy.EmployeeEntity;

@RestController
@RequestMapping("/Employees")
public class EmployeeController {

	private final EmployeeDAO employeeDAO;
    public EmployeeController(EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
    }

    @PostMapping("/")
    public ResponseEntity<String> InsertEmployee(@RequestBody EmployeeEntity employeeEntity) {
        //Inserir dados dos funcionarios
        employeeDAO.insert(employeeEntity);
		System.out.println(employeeEntity);
        return new ResponseEntity<>("Employee criado com sucesso!", HttpStatus.CREATED); // Retorna 201 Created
    }
    

    @PutMapping("/{employee_id}")
    public ResponseEntity<String> UpdataEmployee(@PathVariable Long employee_id, @RequestBody EmployeeEntity employeeEntity) {
        //Alterando dados do funcionario
        employeeEntity.setEmployee_id(employee_id);
        employeeDAO.update(employeeEntity);
        System.out.println(employeeEntity);      
        return new ResponseEntity<>("Employee alterado com sucesso!", HttpStatus.OK); // Retorna 200 OK
    }

    @DeleteMapping("/{employee_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employee_id){
        //Deletando dados do funcionario
        employeeDAO.delete(employee_id);
        return new ResponseEntity<>("Employee deletado com sucesso!", HttpStatus.OK); // Retorna 200 OK ou 204 No Content
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeEntity>> getAllemployee(@RequestParam(required = false) String param) {
		//Listar todos os funcionarios
        List<EmployeeEntity> employees = employeeDAO.findAll();
        employees.forEach(System.out::println);
        return new ResponseEntity<>(employees, HttpStatus.OK); // Retorna 200 OK com a lista
    }

    @GetMapping("/{employee_id}")
    public ResponseEntity<EmployeeEntity> getEmployee(@PathVariable long employee_id) {
   		//Exibir dados de um funcionario expecifico
        EmployeeEntity employeeEntity = employeeDAO.findById(employee_id);
        if (employeeEntity != null) {
            System.out.println(employeeEntity);
            return new ResponseEntity<>(employeeEntity,HttpStatus.OK); // Retorna 200 Ok com o employee
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found não se encontra
        }
    }    

		//Lista dados de auditoria
		//employeeAuditDAO.findAll().forEach(System.out::println);
		
		//Inserir dados de funcionarios por procedure
		/*var insert = new EmployeeEntidy();
		insert.setName("Anya Forg");
		insert.setSalary(new BigDecimal("500"));
		insert.setBirthday(OffsetDateTime.now().minusYears(7));
		System.out.println(insert);
		employeeDAO.insertWithProcedure(insert);
		System.out.println(insert);*/
}
