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

with open('./testesChap7/graphs/results.csv', 'r') as csvfile:
    lines = csv.reader(csvfile, delimiter=',')
    for row in lines:
        x.append(row[0])
        TAS.append(int(row[1]))
        TTAS.append(int(row[2]))
        backoff.append(int(row[3]))
        sem.append(int(row[4]))
        ALOCK.append(int(row[5]))
        MCSLOCK.append(int(row[6]))
        CLHLOCK.append(int(row[7]))

plt.plot(x, TAS, color='g', label="TAS")
plt.plot(x, TTAS, color='red', label="TTAS")
plt.plot(x, backoff, color='blue', label="backoff")
plt.plot(x, sem, color='orange', label="semaphore")
plt.plot(x, ALOCK, color='purple', label="ALOCK")
plt.plot(x, MCSLOCK, color='pink', label="MCSLOCK")
plt.plot(x, CLHLOCK, color='yellow', label="CLHLOCK")
plt.axis([None, None, 0, 2000])

plt.xticks(rotation=25)
plt.xlabel('Threads')
plt.ylabel('Time')
plt.title('L = 100000, N = 12, REPEAT = 10, D = 10', fontsize=10)
plt.grid()
plt.legend()
plt.show()
