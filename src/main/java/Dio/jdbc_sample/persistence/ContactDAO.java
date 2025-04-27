package Dio.jdbc_sample.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import Dio.jdbc_sample.persistence.entidy.ContactEntity;

@Repository
public class ContactDAO {
    
    public void insert(final ContactEntity entity) {
        String sql = "INSERT INTO contacts (description, type, employee_id) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getDescription());
            statement.setString(2, entity.getType());
            statement.setLong(3, entity.getEmployee().getEmployee_id());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setContact_id(generatedKeys.getLong(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); 
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(final ContactEntity entity){
        String sql = "UPDATE contacts set description = ?, type = ? WHERE contact_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, entity.getDescription());
                statement.setString(2, entity.getType());
                statement.setLong(3, entity.getContact_id());
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Registro com ID " + entity.getContact_id() + " alterado na base de dados");
                } else {
                    System.out.println("Nenhum registro com ID " + entity.getContact_id() + " foi encontrado para alteração");
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(final long contact_id){
        String sql = "DELETE FROM contacts WHERE contact_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, contact_id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registro com ID " + contact_id + " excluído na base de dados");
            } else {
                System.out.println("Nenhum registro com ID " + contact_id + " foi encontrado para exclusão");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<ContactEntity> findAllByEmployeeId(final long employee_id){
        String sql= "SELECT contact_id, description, type FROM contacts WHERE employee_id = ?";
        List<ContactEntity> entidies = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
             statement.setLong(1, employee_id);
             ResultSet resultSet = statement.executeQuery(); 
            while (resultSet.next()) {
                ContactEntity entity = new ContactEntity();
                entity.setContact_id(resultSet.getLong("contact_id"));
                entity.setDescription(resultSet.getString("description"));
                entity.setType(resultSet.getString("type"));
                entidies.add(entity);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entidies;
    }
}
