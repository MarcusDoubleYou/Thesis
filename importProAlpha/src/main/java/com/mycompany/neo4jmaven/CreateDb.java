
package com.mycompany.neo4jmaven;

import static com.mycompany.neo4jmaven.Main.DB_PATH;
import static com.mycompany.neo4jmaven.Main.fileLocation;
import static com.mycompany.neo4jmaven.Main.graphDb;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


public class CreateDb {

        String createIndexColumn = "CREATE INDEX ON :Column(to_table_id);";  
            
        String createIndexTable =  "CREATE INDEX ON :Table(name);"; 
        
        String createConstTable = "CREATE CONSTRAINT ON (n:Table) assert n.table_id IS Unique;";
           
        String createConstFK = "CREATE CONSTRAINT ON (f:FK) assert f.fk_id IS Unique;";
            
        String createTable = " LOAD CSV WITH HEADERS FROM " + fileLocation + "table.csv'"
                    + " AS line" 
                    + " CREATE (:Table {name: line.name, table_id: toInt(line.table_Id)})";

        String createRelationship = "LOAD CSV WITH HEADERS FROM " + fileLocation + "fk.csv'  " 
           + " AS line" 
           + " MATCH (t1:Table), (t2:Table) "
           + " WHERE t1.table_id = toInt(line.table_Id) AND t2.name = line.to_table" 
           + " MERGE (t1)-[:FK]->(t2);";
        
        String createLoadColumn = " LOAD CSV WITH HEADERS FROM " + fileLocation + "column.csv'"
           + " AS line" 
           + " Create (c:Column {name: line.name, length: toInt(line.length), type:line.type, to_table_id: toint(line.table_Id)});";
        
        String createConnectColumn = "MATCH (t:Table)  " 
           + " MATCH (c:Column { to_table_id : t.table_id})" 
           + " USING INDEX c:Column(to_table_id)"
           + " MERGE (t)-[r:Column]->(c);";
        

     public void createGraph() //throws org.neo4j.cypher.CypherException
    {
     
      GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
       
       graphDb = graphDbFactory
       .newEmbeddedDatabase(DB_PATH);  
        ExecutionEngine engine = new ExecutionEngine(graphDb);
       
        // you can include multiple result sets in one try block
        // you can include multiple try blocks with one new ExecuteEngin

       try ( Transaction txSchema = graphDb.beginTx() )
        {
            
            ExecutionResult resulCon1 = engine.execute(createConstTable);
               System.out.println("Table Const");
             ExecutionResult resultCon2 = engine.execute(createConstFK);
                System.out.println("FK Const");
             ExecutionResult resultInd1 = engine.execute(createIndexTable);
                System.out.println("index table");
             ExecutionResult resultInd2 = engine.execute(createIndexColumn);
                System.out.println("index column");

             txSchema.success();
        } 
        
       try ( Transaction txCreateDb = graphDb.beginTx() )
        {

             ExecutionResult result = engine.execute(createTable);
                System.out.println("Table created");
             ExecutionResult result2 = engine.execute(createRelationship);
                System.out.println("FK created");
             ExecutionResult result3 = engine.execute(createLoadColumn);
                System.out.println("Columns loaded");
             ExecutionResult result4 = engine.execute(createConnectColumn);
                System.out.println("Columns connected");
             
                txCreateDb.success();
        }
         graphDb.shutdown();
    }
}
