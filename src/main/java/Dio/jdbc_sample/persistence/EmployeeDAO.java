package Dio.jdbc_sample.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import Dio.jdbc_sample.persistence.entidy.EmployeeEntity;

@Repository
public class EmployeeDAO {

    public void insert(final EmployeeEntity entity) {
        String sql = "INSERT INTO employees (name, salary, birthday) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            if (entity.getSalary() != null) {
                statement.setBigDecimal(2, entity.getSalary());
            } else {
                statement.setNull(2, java.sql.Types.DECIMAL);
            }
            OffsetDateTime birthday = entity.getBirthday();
            if (birthday != null) {
                statement.setString(3, birthday.format(DateTimeFormatter.ISO_LOCAL_DATE)); // Formato YYYY-MM-DD
            } else {
                statement.setNull(3, java.sql.Types.DATE);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setEmployee_id(generatedKeys.getLong(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void update(final EmployeeEntity entity){
        String sql = "UPDATE employees set name = ?, salary = ?, birthday = ? WHERE employee_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            BigDecimal salary = entity.getSalary();
            if (salary != null) {
                statement.setBigDecimal(2, salary);
            } else {
                statement.setNull(2, java.sql.Types.DECIMAL);
            }
            OffsetDateTime birthday = entity.getBirthday();
            if (birthday != null) {
                statement.setString(3, birthday.format(DateTimeFormatter.ISO_LOCAL_DATE)); // Formato YYYY-MM-DD
            } else {
                statement.setNull(3, java.sql.Types.DATE);
            }
            statement.setLong(4, entity.getEmployee_id());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registro com ID " + entity.getEmployee_id() + " alterado na base de dados");
            } else {
                System.out.println("Nenhum registro com ID " + entity.getEmployee_id() + " foi encontrado para alteração");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(final long employee_id){
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, employee_id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registro com ID " + employee_id + " excluído na base de dados");
            } else {
                System.out.println("Nenhum registro com ID " + employee_id + " foi encontrado para exclusão");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<EmployeeEntity> findAll(){
        String sql= "SELECT * FROM employees ORDER BY name asc";
        List<EmployeeEntity> entidies = new ArrayList<>();
        ZoneOffset utcOffset = ZoneOffset.UTC;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){ 
            while (resultSet.next()) {
                EmployeeEntity entity = new EmployeeEntity();
                entity.setEmployee_id(resultSet.getLong("Employee_id"));
                entity.setName(resultSet.getString("name"));
                entity.setSalary(resultSet.getBigDecimal("salary"));
                java.sql.Timestamp birthdaytTimestamp = resultSet.getTimestamp("birthday");
                if (birthdaytTimestamp != null){
                    entity.setBirthday(OffsetDateTime.ofInstant(birthdaytTimestamp.toInstant(), utcOffset));
                }
                entidies.add(entity);
            }
         }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entidies;
    }

    public EmployeeEntity findById(final long employee_id){
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        var entity = new EmployeeEntity();
        var utcOffset = ZoneOffset.UTC;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, employee_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity.setEmployee_id(resultSet.getLong("employee_id"));
                entity.setName(resultSet.getString("name"));
                entity.setSalary(resultSet.getBigDecimal("salary"));
                java.sql.Timestamp birthdaytTimestamp = resultSet.getTimestamp("birthday");
                if (birthdaytTimestamp != null){
                    entity.setBirthday(OffsetDateTime.ofInstant(birthdaytTimestamp.toInstant(), utcOffset));
                }
            } else {
                System.out.println("Nenhum registro com ID " + employee_id + " foi encontrado");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    public void insertWithProcedure(final EmployeeEntity entity) {
        String sql = "call prc_insert_employee (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             var statement = connection.prepareCall(sql)) {
            statement.setString(1, entity.getName());
            if (entity.getSalary() != null) {
                statement.setBigDecimal(2, entity.getSalary());
            } else {
                statement.setNull(2, java.sql.Types.DECIMAL); 
            }
            OffsetDateTime birthday = entity.getBirthday();
            if (birthday != null) {
                statement.setString(3, birthday.format(DateTimeFormatter.ISO_LOCAL_DATE)); // Formato YYYY-MM-DD
            } else {
                statement.setNull(3, java.sql.Types.DATE); 
            }
            statement.registerOutParameter(4,java.sql.Types.BIGINT);
            statement.execute();
            entity.setEmployee_id(statement.getLong(4));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertBatch(final List<EmployeeEntity> entities) {
        String sql = "INSERT INTO employees (name, salary, birthday) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            try {
                for (var entity: entities){
                    statement.setString(1, entity.getName());
                    if (entity.getSalary() != null) {
                        statement.setBigDecimal(2, entity.getSalary());
                    } else {
                        statement.setNull(2, java.sql.Types.DECIMAL); 
                    }
                    OffsetDateTime birthday = entity.getBirthday();
                    if (birthday != null) {
                        statement.setString(3, birthday.format(DateTimeFormatter.ISO_LOCAL_DATE)); // Formato YYYY-MM-DD
                    } else {
                        statement.setNull(3, java.sql.Types.DATE); 
                    }
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
