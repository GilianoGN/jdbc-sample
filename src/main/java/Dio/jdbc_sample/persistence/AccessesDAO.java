package Dio.jdbc_sample.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import Dio.jdbc_sample.persistence.entidy.EmployeeEntity;
import Dio.jdbc_sample.persistence.entidy.ModuleEntity;

@Repository
public class AccessesDAO {
    
    public void insert(final long employee_id, final long module_id){
        String sql = "INSERT INTO accesses (employee_id, module_id) values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setLong(1, employee_id);
                statement.setLong(2, module_id);
                statement.executeUpdate();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(final long employee_id, final long module_id) {
        String sql = "DELETE FROM accesses WHERE employee_id = ?, module_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, employee_id);
            statement.setLong(2, module_id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registro com ID " + employee_id + " e " + module_id +" excluído na base de dados");
            } else {
                System.out.println("Nenhum registro com ID " + employee_id + " e " + module_id + " foi encontrado para exclusão");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<ModuleEntity> findAllByEmployeeId(final long employee_id){
        String sql = "SELECT m.module_id, m.name FROM accesses a LEFT JOIN modules m \n" +
                      "ON a.module_id = m.module_id WHERE a.employee_id = ?";
        List<ModuleEntity> entidies = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, employee_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ModuleEntity entity = new ModuleEntity();
                entity.setModule_id(resultSet.getLong("module_id"));
                entity.setName(resultSet.getString("name"));
                entidies.add(entity);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entidies;
    }

    public List<EmployeeEntity> findAllByModuleId(final long module_id){
        String sql = "SELECT e.employee_id, e.name FROM accesses a LEFT JOIN employees e \n" +
                      "ON a.employee_id = e.employee_id WHERE a.module_id = ?";
        List<EmployeeEntity> entidiess = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, module_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                EmployeeEntity entity = new EmployeeEntity();
                entity.setEmployee_id(resultSet.getLong("employee_id"));
                entity.setName(resultSet.getString("name"));
                entidiess.add(entity);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entidiess;
    }
}
