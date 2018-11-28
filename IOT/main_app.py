import os
import rethinkdb as r
import threading
import subprocess
import multiprocessing as mp


conn1 = r.connect('192.168.0.108', 28015)
# conn2 = r.connect('localhost', 28015)

 conn1.use('pi')
# conn2.use('pi')

def run_bin_sensor(id):
    subprocess.call(['python3', 'app.py', id)

# def data1():
# 	cursor1 = r.table("bin").changes().run(conn1)
# 	for doc in cursor1:
# 		print("Run Program Using Trigger {} and Echo {}".format(doc['new_val']['trig_pin'], doc['new_val']['echo_pin']))

# def data2():
# 	cursor2 = r.table("status").changes().run(conn2)
# 	for doc in cursor2:
# 		print(doc)
def main():
    # data1_t = threading.Thread(target=data1)
    # data2_t = threading.Thread(target=data2)
    processes = []
    # # Get all the bins from database which are active
    cursor = r.table("bin").filter(r.row["status"] == "active").run(conn1)
    for document in cursor:
        processes.append(mp.Process(target=run_bin_sensor, args=(document['id']),))
        print("Starting Sensor Program Using Trigger {} and Echo {}".format(document['trig_pin'], document['echo_pin']))

    # data1_t.start()
    # # data2_t.start()
    # data1_t.join()


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        pass
    finally:
        print("GMS Stopped")
