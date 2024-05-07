package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.ColumnParameters;
import eus.ehu.giigsi.abd.parser.CreateTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestDatabase {

    Database database;

    @BeforeEach
    public void init() {
        database = new Database("admin", "admin");
        database.tables.clear();

        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));

        CreateTable ct1 = new CreateTable("prueba1", listaCP);
        CreateTable ct2 = new CreateTable("prueba2", listaCP);
        ct1.execute(database);
        ct2.execute(database);
    }

    @Test
    public void test1() {
        Table table = database.tableByName("prueba2");
        assertEquals(table.toString(), database.tables.get(1).toString());

        Column c1 = new Column(Column.DataType.STRING, "str");
        Column c2 = new Column(Column.DataType.DOUBLE, "dbl");
        Column c3 = new Column(Column.DataType.INT, "int");

        List<Column> columns = new ArrayList<>();
        columns.add(c1);
        columns.add(c2);
        columns.add(c3);

        Table t3 = new Table("table3", columns);
        database.addTable(t3);

        assertEquals("table3", database.tables.get(2).name);

        database.dropTable("prueba1");

        Table table1 = new Table("prueba2", columns);
        assertEquals(table1.toString(), database.tableByName("prueba2").toString());
    }

    @Test
    public void LoadNoExistente() {
        assertNull(Database.load((String)"jgoprawjipjgpajwpgjp", (String)"admin", (String)"admin"));
    }

    @Test
    public void CreateInsertTablaNoExiste() {
        Database database = new Database("admin", "adminPassword");
        assertEquals("ERROR: Table does not exist", database.executeMiniSQLQuery("INSERT INTO MyTable2 VALUES ('Eva',18,'Calle Los Herran 16 2 Vitoria')"));
    }

    @Test
    public void CreateDropSelectTablaNoExiste() {
        Database database = new Database("admin", "adminPassword");
        assertEquals("MSG: Table created", database.executeMiniSQLQuery("CREATE TABLE MyTable (Name TEXT,Age INT,Address TEXT)"));
        assertEquals("MSG: Table dropped", database.executeMiniSQLQuery("DROP TABLE MyTable"));
        assertEquals("ERROR: Table does not exist", database.executeMiniSQLQuery("SELECT Column FROM MyTable"));
    }

    @Test
    public void CreateTableSinColumnas() {
        Database database = new Database("admin", "adminPassword");
        assertEquals("ERROR: Cannot create table without columns", database.executeMiniSQLQuery("CREATE TABLE MyTable ()"));
    }
    @Test
    public void CreateTableInsertAndSelectWhereVacio() {
        Database database = new Database("admin", "adminPassword");
        assertEquals("MSG: Table created", database.executeMiniSQLQuery("CREATE TABLE MyTable (Name TEXT,Age INT,Address TEXT)"));
        assertEquals("ERROR: Table does not exist", database.executeMiniSQLQuery("INSERT INTO MyTable2 VALUES ('Eva',18,'Calle Los Herran 16 2 Vitoria')"));
        assertEquals("MSG: Tuple added", database.executeMiniSQLQuery("INSERT INTO MyTable VALUES ('Ramon',26,'Larratxo kalea 23')"));
        assertEquals("MSG: Tuple added", database.executeMiniSQLQuery("INSERT INTO MyTable VALUES ('Miren',26,'Larratxo kalea 23')"));
        assertEquals("['Name','Age']", database.executeMiniSQLQuery("SELECT Name,Age FROM MyTable WHERE Age=18"));
    }

}
