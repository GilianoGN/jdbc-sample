package Dio.jdbc_sample.persistence.entidy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString (exclude = "employee")
public class ContactEntity {
    
    @EqualsAndHashCode.Include
    private long contact_id;
    private String description;
    private String type;
    private EmployeeEntity employee;

    public ContactEntity() {}

    public ContactEntity(String description, String type, EmployeeEntity employee){
        this.description = description;
        this.type = type;
        this.employee = employee;
    }

}
