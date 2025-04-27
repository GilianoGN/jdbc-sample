package Dio.jdbc_sample.persistence.entidy;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ModuleEntity {
    
    @EqualsAndHashCode.Include
    private long module_id;
    private String name;

    public ModuleEntity() {}

    public ModuleEntity(String name){
        this.name = name;
    }
}
