package Dio.jdbc_sample.persistence.entidy;

import java.util.stream.Stream;

public enum OperationEnum {
    INSERT,
    UPDATE,
    DELETE;

    public static OperationEnum getByDbOperation(final String dbOperation){
        return Stream.of(OperationEnum.values())
            .filter(o -> o.name().equalsIgnoreCase(dbOperation))
            .findFirst()
            .orElseThrow(()-> new IllegalArgumentException("Operação de banco de dados desconhecida: "+ dbOperation));
    }
}
