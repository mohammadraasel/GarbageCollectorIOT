import rethinkdb as r
import threading

conn1 = r.connect('192.168.0.4', 28015)
conn2 = r.connect('192.168.0.4', 28015)

conn1.use('pi')
conn2.use('pi')

def data1():
	cursor1 = r.table("bin").changes().run(conn1)
	for doc in cursor1:
		print(doc)

def data2():
	cursor2 = r.table("status").changes().run(conn2)
	for doc in cursor2:
		print(doc)

data1_t = threading.Thread(target=data1)
data2_t = threading.Thread(target=data2)

data1_t.start()
data2_t.start()
