CC=g++

#Platform options are MAC (Apple Mac) or WIN (windows)
PLATFORM=MAC

VER=c++14

CFLAGS=-Wall -O2 -std=$(VER) -D$(PLATFORM) -DTYPE_SAFE -pthread
#un-comment for debug build
#CFLAGS=-Wall -DDEBUG -g -O0 -std=$(VER) -D$(PLATFORM) -pthread

#source code route directory
DEV_DIR=$(DEV)/src/cpp

SEARCH_DIR=$(DEV_DIR)/libcommon

LINK_LIB = -lcommon

INC_DIR = -I $(SEARCH_DIR) -L $(SEARCH_DIR)

OUT=hello.exe

OBJ=hello.o

$(OUT): $(OBJ)
	$(CC) $(CFLAGS) -L $(SEARCH_DIR) $(LINK_LIB) $? -o $@

%.o: %.cpp
	$(CC) $(CFLAGS) -c $? -I $(SEARCH_DIR) 

clean:
	rm -f *.o *.exe
