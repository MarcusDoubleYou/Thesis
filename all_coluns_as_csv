import py2neo as db
# get connectino
con = db.Graph('http://localhost:7474')


from py2neo import Graph
import pandas as pd

# executes cypher query within a graph obj

graph = Graph()
tab = []
col_n = []
col_t = []
col_l = []

for result in graph.cypher.execute("MATCH (t:Table )-->(c:Column)"+
                                    "RETURN t.name AS tab_name, c.name AS col_name, c.type AS col_type,  c.length as col_length"):
    tab.append(result.tab_name)
    col_n.append(result.col_name)
    col_t.append(result.col_type)
    col_l.append(result.col_length)
      

# saves to csv.file
import csv
with open('proA_all.csv', 'wb') as csvfile:
    fieldnames = ['table', 'colum', 'type', 'length']
    writer = csv.DictWriter(csvfile, fieldnames=fieldnames, dialect='excel')
    writer.writeheader()
    for t, c, ty, l in zip(tab, col_n, col_t, col_l):
        writer.writerow({'table': t, 'colum': c, 'type': ty, 'length': l})
