package Dio.jdbc_sample.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import Dio.jdbc_sample.persistence.entidy.ModuleEntity;

@Repository
public class ModuleDAO {
    
    public void insert(final ModuleEntity entity){
        String sql = "INSERT INTO modules (name) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setModule_id(generatedKeys.getLong(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); 
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(final ModuleEntity entity){
        String sql = "UPDATE modules set name = ? WHERE module_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getModule_id());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registro com ID " + entity.getModule_id() + " alterado na base de dados");
            } else {
                System.out.println("Nenhum registro com ID " + entity.getModule_id() + " foi encontrado para alteração");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(final long module_id){
        String sql = "DELETE FROM modules WHERE module_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {    
            statement.setLong(1, module_id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registro com ID " + module_id + " excluído na base de dados");
            } else {
                System.out.println("Nenhum registro com ID " + module_id + " foi encontrado para exclusão");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<ModuleEntity> findAll(){
        List<ModuleEntity> entidies = new ArrayList<>();
        String sql = "SELECT * FROM modules";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){ 
            while (resultSet.next()) {
                ModuleEntity entity = new ModuleEntity();
                entity.setModule_id(resultSet.getLong("module_id"));
                entity.setName(resultSet.getString("name"));
                entidies.add(entity);
            }
         }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entidies;
    }

    public ModuleEntity findById(final long module_id){
        String sql = "SELECT * FROM modules WHERE module_id = ?";
        var entity = new ModuleEntity();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, module_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity.setModule_id(resultSet.getLong("module_id"));
                entity.setName(resultSet.getString("name"));
            } else {
                System.out.println("Nenhum registro com ID " + module_id + " foi encontrado");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }
}
