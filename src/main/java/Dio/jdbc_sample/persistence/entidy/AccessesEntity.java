package Dio.jdbc_sample.persistence.entidy;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccessesEntity {
    
    @EqualsAndHashCode.Include
    private EmployeeEntity employee;
    private ModuleEntity module;

    public AccessesEntity() {}

}
