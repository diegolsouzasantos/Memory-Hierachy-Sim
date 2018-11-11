# Memory-Hierachy-Sim
A Cache and Virtual Memory Simulator
Contribution of Gabriel M. Nori

Memory hierarchy in the architecture of Core i7, containing:
1. L1 cache of data, inside the core
2. L1 instruction cache, inside the core
3. L2 cache of data and instructions, within the core
4. L3 cache of data and instructions, shared between cores
5. virtual addressing and virtual memory

Software contains:
1. Fully associative cache module (32 bits)
2. Assembly associative cache module (32 bits)
3. Cache Hierarchy
4. Main Memory
5. Memory hierarchy with cache and main memory
6. Multi-Core Processor
7. File with a sequence of commands

Each of the components is described below. 
The names of functions, 
methods or data types, 
are exactly like in this document:

3.1 FULLY ASSOCIATIVE CACHE

TACache createTACache (int c, int l);
TACache is the data type (struct or class) that represents a fully associative cache with total capacity of c bytes, being 1 bytes per line. c and l are integer powers of 2 and c is a multiple of l

TACache methods:
int getTACacheCapacity (TACache tac);
int getTACacheLineSize (TACache tac);
Returns capacity and cache size of the fully associative cache cache.

bool getTACacheData (TACache tac, int address, int * value);
Search the address address value in the fully associative cache tac.
The value is returned in the value output parameter and the method or function returns true, if the address was found in the cache (hit) or false, otherwise (miss). 
Comparison of the address with the cache directory is done using logical operators.

void setTACacheLine (TACache tac, int address, int * line);
writes the entire "line", which contains the "address", in the fully associative cache "tac". The size of the line is the size defined in the creation of the tac.

bool setTACacheData (TACache tac, int address, int value);
Overrides the "value", from the "address", in the fully associative cache "tac". 
If address is not in the cache, the function returns false. 
If address is in cache, value should be write in the correct position (offset) of the cache line.
Note: Any address, immediately after writing, appear at all cache levels, so, before writing a value in the cache, the entire line containing your address is copied to the cache, that is, write to an address that was never accessed means calling setTACacheData, receiving false as answer, calling setTACacheLine to bring the entire row of data and then call setTACacheData again to update the value.

3.2 ASSOCIATIVE CACHE BY SETS

SACache createSACache (int c, int a, int l);
Where SACache is the data type that represents an associative cache by sets with total capacity of c bytes, associativity to and l bytes per line.
It is obligatory that c, a and l are entire powers of 2 and that c is a multiple of a * l.
Each SACache set is a TACache, and therefore createSACache calls createTACache.

SACache methods:
int getSACacheCapacity (SACache sac);
int getSACacheLineSize (SACache sac);
Returns the capacity and size of the associative cache row by sac sets.

bool getSACacheData (SACache sac, int address, int * value);
Find the value of the address address in the associative cache by sac sets.
The value is returned in the value output parameter and the method or function returns true, if the address was found in the cache (hit) or false, otherwise (miss).
Checking the lookup bits of the address address to extract the set number must be done using logical operators.

void setSACacheLine (SACache tac, int address, int * line);
which writes the entire line line, which contains the address address, in the associative cache by sets
sac, using TACache's setTACacheLine.

bool setSACacheData (SACache sac, int address, int value);
Writes the value value, of address address, in the associative cache by sac sets, using
setTACacheData of TACache, and with the same return pattern of setTACacheData.

SACache duplicateSACache (SACache sac);
Creates a new associative cache by sets with characteristics similar to sac.

3.3 CACHE HIERARCHY
Cache createCache (SACache l1d, SACache l1i, SACache l2, SACache l3);
Cache is the data type representing a cache hierarchy containing an L1 data cache "l1d", an L1 cache of instructions "l1i", an L2 data cache and instructions "l2", and a shared L3 cache of data and instructions "l3".
The cache hierarchy must have the line sizes of l1d and l1i smaller than or equal to the line size of l2, and the line size of l2 must be less than the line size of l3.

Cache methods:
void fetchCacheData (Cache sac, MainMemory mmem, int address, int value);
void fetchCacheInstruction (Cache sac, MainMemory mmem, int address, int value);
Search the main memory "mmem" for the entire cache line corresponding to the "address" for each level of the cache hierarchy "c" and update the entire cache "sac".

int getCacheData (Cache c, MainMemory mmem, int address, int * value);
int getCacheInstruction (Cache c, MainMemory mmem, int address, int * value);
Find the "value" of the "address" in the cache hierarchy "c".
The value is returned in the value output parameter and the method or function returns 1 if the address was found in the L1 data cache; 2, if the address was found in the L2 cache, 3 if the address was found in the L3 cache and 4 if the data was not in the cache (miss) and it was necessary to update the cache through its functions or fetchCacheData or fetchCacheInstruction .

void setCacheData (Cache c, int address, int value);
void setCacheInstruction (Cache c, int address, int value);
Write "value", from the "address", in the cache hierarchy "c".
At the end of a read or write, the accessed address is copied to the L1 cache

Cache duplicateCache (Cache c);
Creates a new cache hierarchy in which L1 and L2 are duplicated by duplicateSACache from c and L3 is the same as c (shared).
3.4 MEMÓRIA PRINCIPAL

MainMemory createMainMemory (int ramsize, int vmsize);
MainMemory is the data type that represents the main memory, composed of RAM, with ramsize bytes, and virtual memory, with vmsize bytes.
(MainMemory implemented as a single vector of size ramsize + vmsize)

int getMainMemoryData (MainMemory mem, int address, int * value);
Find the value of the address address in the main memory mem.
The value is returned in the value output parameter. The method or function returns 4, if the address is valid and was read correctly; -1 if the address is outside the range of valid virtual addresses.

void setMainMemoryData (MainMemory mem, int address, int value);
Stores the value of the address address in the main memory mem.
The method or function returns 4, if the address is valid and the data was spelled correctly in memory; -1 if the address is outside the range of valid virtual addresses.

3.5 MEMORY HIERARCHY
Memory createMemory (Cache c, MainMemory mem);
Memory is the data type that represents the memory hierarchy, composed of a cache hierarchy c and a mem memory.

int getData (Memory mem, int address, int * value);
int getInstruction (Memory mem, int address, int * value);
Find the value of the address address in the mem memory hierarchy.
The value is returned in the value output parameter.
The method or function returns 1, if the address was read in L1; 2, if the address was read in L2; 3, if the address was read in L3; 4, if the address was read in main memory; -1 if the address is outside the range of valid virtual addresses.

void setData (Memory mem, int address, int value);
void setInstruction (Memory mem, int address, int value);
Writes the value of the "address" in the mem memory hierarchy.

Memory duplicateMemory (Memory mem);
Creates a new memory hierarchy in which the cache hierarchy is duplicated by duplicateCache (duplicate L1 and L2 and shared L3) and the main memory is the same as mem.

3.6 MULTINUCCESS PROCESSOR

Processor createProcessor (Memory mem, int ncores);
Processor is the data type that represents the processor, composed of
"ncores", all using the mem memory hierarchy or its duplications.

3.7 COMMAND FILE READING

The program created must be initialized by passing a command file as a parameter.

The program will read and execute each command sequentially, printing on the screen a message for each command executed, with the command information executed and the result obtained. At the end, it will issue a general report of the execution of all the commands and will end.

This section explains the file format and the commands that should be interpreted.

The file is a text file with one command per line.

All commands are in the form "par1 command par2", and the number of parameters can vary
according to the command. The commands that are to be implemented are listed below.

Commands for creating the memory hierarchy:
1. cl1d c a l. Creates a variable l1d that is an associative cache by sets with
capacity, associativity, and bytes per line.
2. cl1i c a l. Creates a variable l1i that is an associative cache by sets with
capacity, associativity, and bytes per line.
3. cl2 c a l. Creates a variable l2 that is an associative cache by sets with
c, associativity at 1 bytes per line.
4. cl3 c a l. Creates a variable l3 that is an associative cache by sets with
c, associativity at 1 bytes per line.
5. cmp ramsize vmsize. Creates a variable mp that is a main memory with ramsize
bytes of RAM and vmsize bytes of virtual memory.
6. cm.m. Creates a mem variable that is a memory hierarchy created with l1d, l1i, l2, l3
and mp files previously created.
7. cp n. Creates a processor with n cores, with each core having a hierarchy of
memory based memory.

All creation commands must be executed in the order above before any other
command can be executed.

Data access commands and instructions:
1. ri n addr. Reads the addr address instruction in the memory hierarchy of core n.
2. wi n addr value. Write the value statement at address addr through the memory hierarchy of core n.
3. rd n addr. Reads the address data addr in the memory hierarchy of core n.
4. wd n addr value. Write the given value in address addr by the memory hierarchy of core n.
5. asserti n addr level value. Reads the addr address instruction in the core memory hierarchy n and checks to see if the value was read from the level level (ranging from 1 to 5 according to getInstruction return) and the read value is equal to value.
6. assertd n addr level value. Reads the address data addr in the core memory hierarchy n and verifies that the value was read from the level level (ranging from 1 to 5 as per return from getData) and the read value is equal to value.
