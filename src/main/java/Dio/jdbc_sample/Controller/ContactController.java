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

import Dio.jdbc_sample.persistence.ContactDAO;
import Dio.jdbc_sample.persistence.entidy.ContactEntity;
import Dio.jdbc_sample.persistence.EmployeeDAO;
import Dio.jdbc_sample.persistence.entidy.EmployeeEntity;

import java.util.List;

@RestController
@RequestMapping("/Contacts")
public class ContactController {

    private final EmployeeDAO employeeDAO;
    private final ContactDAO contactDAO;


    public ContactController(ContactDAO contactDAO, EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
        this.contactDAO = contactDAO;
    }

    @PostMapping("/")
    public ResponseEntity<String> InsertContact(@RequestBody ContactEntity contactEntity) {
        //Inserir contato do funcionario
        EmployeeEntity employeeEntity = employeeDAO.findById(contactEntity.getEmployee().getEmployee_id());        
        contactDAO.insert(contactEntity);
        System.out.println(employeeEntity);
		System.out.println(contactEntity);
        return new ResponseEntity<>("Contact criado com sucesso!", HttpStatus.CREATED); // Retorna 201 Created
    }

    @PutMapping("/{contact_id}")
    public ResponseEntity<String> UpdataContact(@PathVariable Long contact_id, @RequestBody ContactEntity contactEntity) {
        //Alterando contato do funcionario
        EmployeeEntity employeeEntity = employeeDAO.findById(contactEntity.getEmployee().getEmployee_id());        
        contactEntity.setContact_id(contact_id);
        contactDAO.update(contactEntity);
        System.out.println(employeeEntity);
        System.out.println(contactEntity);      
        return new ResponseEntity<>("Contact alterado com sucesso!", HttpStatus.OK); // Retorna 200 OK
    }

    @DeleteMapping("/{contact_id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long contact_id){
        //Deletando contato do funcionario
        contactDAO.delete(contact_id);
        return new ResponseEntity<>("Contact deletado com sucesso!", HttpStatus.OK); // Retorna 200 OK ou 204 No Content
    }

    @GetMapping("/{employee_id}")
    public ResponseEntity<List<ContactEntity>> getAllContactByEmployee(@PathVariable long employee_id, @RequestParam(required = false) String param) {
		//Listar todos os contatos do funcionario
        EmployeeEntity employeeEntity = employeeDAO.findById(employee_id);        
        List<ContactEntity> contacts = contactDAO.findAllByEmployeeId(employee_id);
        if (contacts != null) {
            System.out.println(employeeEntity);
            contacts.forEach(System.out::println);
            return new ResponseEntity<>(contacts,HttpStatus.OK); // Retorna 200 Ok com o Contact
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found não se encontra
        }
    }
}
