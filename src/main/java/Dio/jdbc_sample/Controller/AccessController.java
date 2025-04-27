package Dio.jdbc_sample.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import Dio.jdbc_sample.persistence.AccessesDAO;
import Dio.jdbc_sample.persistence.EmployeeDAO;
import Dio.jdbc_sample.persistence.ModuleDAO;
import Dio.jdbc_sample.persistence.entidy.AccessesEntity;
import Dio.jdbc_sample.persistence.entidy.EmployeeEntity;
import Dio.jdbc_sample.persistence.entidy.ModuleEntity;

@RestController
@RequestMapping("/Accesses")
public class AccessController {
    
	private final AccessesDAO accessesDAO;
    private final EmployeeDAO employeeDAO;
    private final ModuleDAO moduleDAO;

    public AccessController(AccessesDAO accessesDAO, EmployeeDAO employeeDAO, ModuleDAO moduleDAO){
        this.accessesDAO = accessesDAO;
        this.employeeDAO = employeeDAO;
        this.moduleDAO = moduleDAO;
    }

    @PostMapping("/")
    public ResponseEntity<String> InsertAccess(@RequestBody AccessesEntity accessesEntity){
        //Inserir Acessos
        long employeeId = accessesEntity.getEmployee().getEmployee_id();
        long moduleId = accessesEntity.getModule().getModule_id();
        EmployeeEntity employeeEntity = employeeDAO.findById(employeeId); 
        ModuleEntity moduleEntity = moduleDAO.findById(moduleId); 
        accessesDAO.insert(employeeId, moduleId);
        if (employeeEntity != null) {
            System.out.println("Funcionário: " + employeeEntity.getName());
        } else {
            System.out.println("Funcionário com ID " + employeeId + " não encontrado.");
        }
        if (moduleEntity != null) {
            System.out.println("Módulo: " + moduleEntity.getName());
        } else {
            System.out.println("Módulo com ID " + moduleId + " não encontrado.");
        }
        return new ResponseEntity<>("Acesso criado com sucesso!", HttpStatus.CREATED); // Retorna 201 Created
    }

    @DeleteMapping("/employees/{employeeId}/modules/{moduleId}")
    public ResponseEntity<String> deleteAccess(@PathVariable Long employeeId, @PathVariable Long moduleId){
        // Deletando Acessos
        accessesDAO.delete(employeeId, moduleId);
        return new ResponseEntity<>("Acesso deletado com sucesso!", HttpStatus.OK); // Retorna 200 OK ou 204 No Content
    }

    @GetMapping("/Employee/{employee_id}")
    public ResponseEntity<List<ModuleEntity>> getAllAccessesByEmployee(@PathVariable long employee_id, @RequestParam(required = false) String param) {
		//Listar todos os Modulo do funcionario
        EmployeeEntity employeeEntity = employeeDAO.findById(employee_id);        
        List<ModuleEntity> accesses = accessesDAO.findAllByEmployeeId(employee_id);
        if (accesses != null) {
            System.out.println(employeeEntity);
            accesses.forEach(System.out::println);
            return new ResponseEntity<>(accesses,HttpStatus.OK); // Retorna 200 Ok com o Contact
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found não se encontra
        }
    }

    @GetMapping("/Module/{module_id}")
    public ResponseEntity<List<EmployeeEntity>> getAllAccessesByModule(@PathVariable long module_id, @RequestParam(required = false) String param) {
		//Listar todos os Modulo do funcionario
        ModuleEntity moduleEntity = moduleDAO.findById(module_id);        
        List<EmployeeEntity> accesses = accessesDAO.findAllByModuleId(module_id);
        if (accesses != null) {
            System.out.println(moduleEntity);
            accesses.forEach(System.out::println);
            return new ResponseEntity<>(accesses,HttpStatus.OK); // Retorna 200 Ok com o Contact
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found não se encontra
        }
    }
}
