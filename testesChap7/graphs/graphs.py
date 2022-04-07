import matplotlib.pyplot as plt
import csv
  
x = []
TAS = []
TTAS = []
backoff = []
sem = []
  
with open('./testesChap7/graphs/results.csv','r') as csvfile:
    lines = csv.reader(csvfile, delimiter=',')
    for row in lines:
        x.append(row[0])
        TAS.append(int(row[1]))
        TTAS.append(int(row[2]))
        backoff.append(int(row[3]))
        sem.append(int(row[4]))
        
plt.plot(x, TAS, color = 'g', linestyle = 'dashed',
         marker = 'o',label = "TAS")
plt.plot(x, TTAS, color = 'red', linestyle = 'dashed',
         marker = 'o',label = "TTAS")
plt.plot(x, backoff, color = 'blue', linestyle = 'dashed',
         marker = 'o',label = "backoff")
plt.plot(x, sem, color = 'orange', linestyle = 'dashed',
         marker = 'o',label = "semaphore")
  
plt.xticks(rotation = 25)
plt.xlabel('Threads')
plt.ylabel('Time')
plt.title('SpinLock', fontsize = 20)
plt.grid()
plt.legend()
plt.show()