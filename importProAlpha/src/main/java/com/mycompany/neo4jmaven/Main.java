package com.mycompany.neo4jmaven;

import org.neo4j.graphdb.GraphDatabaseService;

public class Main {
    
    public static GraphDatabaseService graphDb;
    public static String DB_PATH = "C:\\Users\\Marcus\\Desktop\\TestIm\\db\\data11";
    //public static String fileLocation = "'file:C:/Users/Marcus/Desktop/BADaten/OpenERP/"; // for local file repo
    public static String fileLocation = "'https://raw.githubusercontent.com/MarcusDoubleYou/Thesis/master/ProAlpha/";

    public static void main(String[] args){
        
        CreateDb db1 = new CreateDb();
        db1.createGraph();
        QueryResult q = new QueryResult();
        q.Test();
    }
}
    

