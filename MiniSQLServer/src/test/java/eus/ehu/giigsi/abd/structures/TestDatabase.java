package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.ColumnParameters;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {
    @Test
    public  void createTestDatabase(){
        Database database = new Database("admin", "admin", "admin");

        List<ColumnParameters> columnParameters = new ArrayList<>();
        columnParameters.add(new ColumnParameters("a", Column.DataType.STRING));
        columnParameters.add(new ColumnParameters("b", Column.DataType.INT));
        columnParameters.add(new ColumnParameters("c", Column.DataType.STRING));
        boolean created = database.createTable("Table", columnParameters);


        assertTrue(created, "Error al crear la tabla en la base de datos.");


        List<Table> tables = database.getTables();
        assertFalse(tables.isEmpty(), "No se encontraron tablas en la base de datos después de la creación.");

        Table createdTable = tables.get(0);


        List<ColumnParameters> expectedParams = new ArrayList<>(columnParameters);

        List<Column> tableColumns = createdTable.columns;


        assertEquals(expectedParams.size(), tableColumns.size(), "El número de columnas en la tabla creada no coincide.");


        for (Column column : tableColumns) {
            boolean columnFound = false;
            for (ColumnParameters param : expectedParams) {
                if (column.name.equals(param.getName()) && column.type == param.getType()) { // Accedemos directamente a los atributos de la columna
                    columnFound = true;
                    break;
                }
            }
            assertTrue(columnFound, "Columna inesperada encontrada en la tabla creada.");
        }
    }
}
