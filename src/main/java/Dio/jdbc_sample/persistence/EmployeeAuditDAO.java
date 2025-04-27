package Dio.jdbc_sample.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ArrayList;

import Dio.jdbc_sample.persistence.entidy.EmployeeAuditEntity;
import Dio.jdbc_sample.persistence.entidy.OperationEnum;


public class EmployeeAuditDAO {
    private static final ZoneOffset UTC_OFFSET = ZoneOffset.UTC;
    public List<EmployeeAuditEntity> findAll() {
        List<EmployeeAuditEntity> entities = new ArrayList<>();
        try (
            Connection connection = ConnectionUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM view_employee_audit")){ 
            while (resultSet.next()) {
                entities.add(new EmployeeAuditEntity(
                    resultSet.getLong("employee_id"),
                    resultSet.getString("name"),
                    resultSet.getString("old_name"),
                    resultSet.getBigDecimal("salary"),
                    resultSet.getBigDecimal("old_salary"),
                    resultSet.getTimestamp("birthday") != null ?
                        OffsetDateTime.ofInstant(resultSet.getTimestamp("birthday").toInstant(), UTC_OFFSET): null,
                    resultSet.getTimestamp("old_birthday") != null ?
                        OffsetDateTime.ofInstant(resultSet.getTimestamp("old_birthday").toInstant(), UTC_OFFSET): null,
                    OperationEnum.getByDbOperation(resultSet.getString("operation"))
                    ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); 
        }
        return entities;
    }
}
