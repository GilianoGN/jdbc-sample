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

import Dio.jdbc_sample.persistence.ModuleDAO;
import Dio.jdbc_sample.persistence.entidy.ModuleEntity;

@RestController
@RequestMapping("/Module")
public class ModuleController {

	private final ModuleDAO moduleDAO;

    public ModuleController(ModuleDAO moduleDAO){
        this.moduleDAO = moduleDAO;
    }

    @PostMapping("/")
    public ResponseEntity<String> InsertModule(@RequestBody ModuleEntity moduleEntity){
        //Inserir Modulo
        moduleDAO.insert(moduleEntity);
		System.out.println(moduleEntity);
        return new ResponseEntity<>("Module criado com sucesso!", HttpStatus.CREATED); // Retorna 201 Created
    }

    @PutMapping("/{module_id}")
    public ResponseEntity<String> UpdataModule(@PathVariable Long module_id, @RequestBody ModuleEntity moduleEntity){
        //Alterando Modulo
        moduleEntity.setModule_id(module_id);
        moduleDAO.update(moduleEntity);
        System.out.println(moduleEntity);      
        return new ResponseEntity<>("Module alterado com sucesso!", HttpStatus.OK); // Retorna 200 OK
    }    

    @DeleteMapping("/{module_id}")
    public ResponseEntity<String> deleteModule(@PathVariable Long module_id){
        //Deletando Modulo
        moduleDAO.delete(module_id);
        return new ResponseEntity<>("Module deletado com sucesso!", HttpStatus.OK); // Retorna 200 OK ou 204 No Content
    }

    @GetMapping("/")
    public ResponseEntity<List<ModuleEntity>> getAllmodule(@RequestParam(required = false) String param){
		//Listar todos os funcionarios
        List<ModuleEntity> modules = moduleDAO.findAll();
        modules.forEach(System.out::println);
        return new ResponseEntity<>(modules, HttpStatus.OK); // Retorna 200 OK com a lista
    }
}
