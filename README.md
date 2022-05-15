## Implementações do capítulo 7 
Herlihy, Maurice; Shavit, Nir. The Art of Multiprocessor Programming. 2008.

### Foram avaliados os métodos:

- Test-And-Set (TAS)
- Test-and-test-and-set (TTAS)
- Exponential Backoff
- Queue Locks:
  - Array-Based (Alock)
  - CLH Queue (CLHLock)
  - MCS Queue (MCSLock)
- Composite Lock:
  - Fast-Path Composite Lock
- Semaphore

### Para compilar
`/src$ javac -d ../bin -cp  . ./Main.java`

### Execução: 
`java -cp 'bin/' Main numThreads numIterations numRepetitions sizeBubbleSort`

Basta executar o arquivo Main.java
Para gerar os gráficos, ./graphs/graphs.py
É necessário ter executado o java antes para gerar o arquivo results.csv
A cada execução, ele é zerado.

#### Parametros:
    int numThreads = {número de threads} 
    int numIterations = {quantidade de iterações}
    int numRepetitions = {número de repetições para execução}
    int sizeBubbleSort = {tamanho do array a ser ordenado pelo bubbleSort. Para não executar, inserir 0}
