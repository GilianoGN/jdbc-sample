package Dio.jdbc_sample.persistence.entidy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmployeeEntity {
    
    @EqualsAndHashCode.Include
    private long employee_id;
    private String name;
    private BigDecimal salary;
    private OffsetDateTime birthday;


    public EmployeeEntity() {}

    public EmployeeEntity(String name, BigDecimal salary, OffsetDateTime birthday){
        this.name = name;
        this.salary = salary;
        this.birthday = birthday;
    }

}
