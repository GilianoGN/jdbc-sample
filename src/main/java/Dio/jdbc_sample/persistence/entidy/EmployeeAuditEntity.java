package Dio.jdbc_sample.persistence.entidy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record EmployeeAuditEntity (
    Long employee_id,
    String name,
    String oldName,
    BigDecimal salary,
    BigDecimal oldSalary,
    OffsetDateTime birthday,
    OffsetDateTime oldBirthday,
    OperationEnum operation
) {
    // Você pode adicionar métodos utilitários relevantes aqui, se necessário.
    // Por exemplo, um método para gerar uma descrição da auditoria.
    public String getAuditDescription() {
        StringBuilder sb = new StringBuilder("Employee ID: ").append(employee_id).append(", Operation: ").append(operation);
        if (name != null && oldName != null && !name.equals(oldName)) {
            sb.append(", Name changed from '").append(oldName).append("' to '").append(name).append("'");
        } else if (name != null) {
            sb.append(", Name: '").append(name).append("'");
        }
        if (salary != null && oldSalary != null && !salary.equals(oldSalary)) {
            sb.append(", Salary changed from ").append(oldSalary).append(" to ").append(salary);
        } else if (salary != null) {
            sb.append(", Salary: ").append(salary);
        }
        if (birthday != null && oldBirthday != null && !birthday.equals(oldBirthday)) {
            sb.append(", Birthday changed from ").append(oldBirthday).append(" to ").append(birthday);
        } else if (birthday != null) {
            sb.append(", Birthday: ").append(birthday.toLocalDate()).append("'");
        }
        return sb.toString();
    }
}
