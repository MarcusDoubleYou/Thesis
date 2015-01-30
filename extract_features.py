


# In[1]:

import py2neo as db
# get connectino
con = db.Graph('http://localhost:7474')


# In[2]:

# load file with header and item names
headers = []
items = []
with open('proA_header.csv') as f:
    for line in f:
        a = line.split(',')
        b = a[1].split('\n')
        headers.append(a[0])
        items.append(b[0])


# In[3]:

from py2neo import Graph
import pandas as pd

# executes cypher query within a graph obj

graph = Graph()
tab = []
col = []
for table_names in items:
    for result in graph.cypher.execute("MATCH (n {name: {map} })-->(c:Column)  RETURN c.name AS col_name", {"map": table_names}):
        tab.append(table_names)
        col.append(result.col_name)
      


# In[5]:

# saves to file
import csv
with open('items_column.csv', 'wb') as csvfile:
    fieldnames = ['table', 'colum']
    writer = csv.DictWriter(csvfile, fieldnames=fieldnames, dialect='excel')
    writer.writeheader()
    for t, c in zip(tab, col):
        writer.writerow({'table': t, 'colum': c})

