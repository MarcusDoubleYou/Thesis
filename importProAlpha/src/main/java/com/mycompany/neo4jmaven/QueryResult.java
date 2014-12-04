package com.mycompany.neo4jmaven;

import static com.mycompany.neo4jmaven.Main.DB_PATH;
import static com.mycompany.neo4jmaven.Main.graphDb;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class QueryResult {
   
        String resultString = null;
        String columnsString = null;
        String columns = null;
    
        String QUERY = "MATCH (n) RETURN n LIMIT 10;";  
          

     public void Test() //throws org.neo4j.cypher.CypherException
    {
     
      GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
       
       graphDb = graphDbFactory
       .newEmbeddedDatabase(DB_PATH);  
        ExecutionEngine engine = new ExecutionEngine(graphDb);
       

       try ( Transaction tx = graphDb.beginTx() )
        {

         resultString = engine.execute( QUERY ).dumpToString();
         //columnsString = columns.toString();
         System.out.println(resultString);

             tx.success();
        } 
        
         graphDb.shutdown();

    }
}

