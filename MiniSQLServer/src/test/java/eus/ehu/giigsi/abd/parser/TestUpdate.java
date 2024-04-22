package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.*;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestUpdate {

    @Test
    public void Update() {

        Database db  = new Database("Alumnos", "admin", "admin");
        List<Column> columnsTable1 = new ArrayList<>();
        columnsTable1.add(new Column(Column.DataType.STRING, "Nombres"));
        columnsTable1.add(new Column(Column.DataType.STRING, "LDAP"));
        Table estudiantes = new Table("Estudiantes", columnsTable1);

        List<Column> columnsTable2 = new ArrayList<>();
        columnsTable2.add(new Column(Column.DataType.STRING, "LDAP"));
        columnsTable2.add(new Column(Column.DataType.STRING, "Asignatura"));
        columnsTable2.add(new Column(Column.DataType.STRING, "Nota"));
        columnsTable2.add(new Column(Column.DataType.STRING, "NotaTexto"));
        Table notas = new Table("Registro de notas", columnsTable2);

        // Agregar las tablas a la lista de tablas de la base de datos
        db.addTable(estudiantes);
        db.addTable(notas);

        // Insertar alumnos a la bd
        List<String> estu1 = new ArrayList<>();
        estu1.add("Martina Gioiello");
        estu1.add("1007012");
        estudiantes.insert(estu1);

        List<String> estu2 = new ArrayList<>();
        estu2.add("Asier Montero");
        estu2.add("1407292");
        estudiantes.insert(estu2);

        List<String> estu3 = new ArrayList<>();
        estu3.add("Francisco Fernandez");
        estu3.add("1293715");
        estudiantes.insert(estu3);

        // Insertar calificaciones
        List<String> nota1 = new ArrayList<>();
        nota1.add("1007012");
        nota1.add("Calculo");
        nota1.add("9.2");
        notas.insert(nota1);

        List<String> nota2 = new ArrayList<>();
        nota2.add("1007012");
        nota2.add("Diseño de Bases de Datos");
        nota2.add("4.3");
        notas.insert(nota2);

        List<String> nota3 = new ArrayList<>();
        nota3.add("1007012");
        nota3.add("Investigación Operativa");
        nota3.add("7.2");
        notas.insert(nota3);

        List<String> nota4 = new ArrayList<>();
        nota4.add("1407292");
        nota4.add("Cálculo");
        nota4.add("6.0");
        notas.insert(nota4);

        List<String> nota5 = new ArrayList<>();
        nota5.add("1407292");
        nota5.add("Diseño de Bases de Datos");
        nota5.add("8.5");
        notas.insert(nota5);

        List<String> nota6 = new ArrayList<>();
        nota6.add("1407292");
        nota6.add("Investigación Operativa");
        nota6.add("8.0");
        notas.insert(nota6);

        List<String> nota7 = new ArrayList<>();
        nota7.add("1293715");
        nota7.add("Cálculo");
        nota7.add("5.2");
        notas.insert(nota7);

        List<String> nota8 = new ArrayList<>();
        nota8.add("1293715");
        nota8.add("Diseño de Bases de Datos");
        nota8.add("6.4");
        notas.insert(nota8);

        List<String> nota9 = new ArrayList<>();
        nota9.add("1293715");
        nota9.add("Investigación Operativa");
        nota9.add("4.3");
        notas.insert(nota9);


        // Crear condiciones para la actualización
        Condition condition = new Condition("Nota", ">", "8.9");
        Condition condition1 = new Condition("Nota", "<", "5.0");

        // Crear una lista de cambios
        List<SetValue> sobresaliente = new ArrayList<>();
        sobresaliente.add(new SetValue("NotaTexto", "Sobresaliente"));

        List<SetValue> suspenso = new ArrayList<>();
        suspenso.add(new SetValue("NotaTexto", "Suspenso"));

        boolean result = db.update("notas", sobresaliente, condition);
        boolean result1 = db.update("notas", suspenso, condition1);

        assertTrue(result);
        assertTrue(result1);

    }
}
