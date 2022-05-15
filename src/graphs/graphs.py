import matplotlib.pyplot as plt
import csv

x = []
TAS = []
TTAS = []
backoff = []
sem = []
ALOCK = []
MCSLOCK = []
CLHLOCK = []
COMFLOCK = []
COMLOCK = []

with open('./src/graphs/umKCemRepDezBubble.csv', 'r') as csvfile:
    lines = csv.reader(csvfile, delimiter=',')
    for row in lines:
        x.append(row[0])
        # TAS.append(int(row[1]))
        TTAS.append(int(row[2]))
        backoff.append(int(row[3]))
        sem.append(int(row[4]))
        ALOCK.append(int(row[5]))
        MCSLOCK.append(int(row[6]))
        CLHLOCK.append(int(row[7]))
        COMFLOCK.append(int(row[8]))
        COMLOCK.append(int(row[9]))

# plt.plot(x, TAS, color='g', label="TAS")
plt.plot(x, TTAS, color='red', label="TTAS")
plt.plot(x, backoff, color='blue', label="backoff")
plt.plot(x, sem, color='orange', label="semaphore")
plt.plot(x, ALOCK, color='purple', label="ALOCK")
plt.plot(x, MCSLOCK, color='pink', label="MCSLOCK")
plt.plot(x, CLHLOCK, color='c', label="CLHLOCK")
plt.plot(x, COMFLOCK, color='black', label="Composite Fast Path Lock")
plt.plot(x, COMLOCK, color='m', label="Composite Lock")
# plt.axis([None, None, 0, 6000000])

plt.xticks(rotation=25)
plt.xlabel('Threads')
plt.ylabel('Time')
plt.title(' Iterações = 1000000, Threads = 24, Repetições = 10, BubbleSort = 10', fontsize=10)
plt.grid()
plt.legend()
plt.show()
