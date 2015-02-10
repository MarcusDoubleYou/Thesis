import py2neo as db
# get connectino
con = db.Graph('http://localhost:7474')

# load file with header and item names
headers = []
items = []
with open('proA_header.csv') as f:
    for line in f:
        a = line.split(',')
        b = a[1].split('\n')
        headers.append(a[0])
        items.append(b[0])


from py2neo import Graph
import pandas as pd

# executes cypher query within a graph obj

graph = Graph()
tab = []   
col_n = [] 
col_t = []
col_l = []
for table_names in items:
    for result in graph.cypher.execute("MATCH (n {name: {map} })-->(c:Column)"+ 
                                       "RETURN c.name AS col_name, c.type AS col_type,  c.length as col_length", 
                                       {"map": table_names}):
        tab.append(table_names)
        col_n.append(result.col_name)
        col_t.append(result.col_type)
        col_l.append(result.col_length)
      
