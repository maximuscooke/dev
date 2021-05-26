CC=g++

#Platform options are MAC (Apple Mac) or WIN (windows)
PLATFORM=MAC

VER=c++14

CFLAGS=-Wall -O2 -std=$(VER) -D$(PLATFORM) -DTYPE_SAFE -pthread
#un-comment for debug build
#CFLAGS=-Wall -DDEBUG -g -O0 -std=$(VER) -D$(PLATFORM) -pthread

#source code route directory
DEV_DIR=$(DEV)/src/cpp

OUT_DIR=$(DEV_DIR)

SEARCH_DIR=$(DEV_DIR)/libcommon

OUT=libcommon.a

OBJ=CByte.o \
	CDateTime.o \
    CDouble.o \
	CException.o \
	CFile.o \
	CFloat.o \
	CInteger.o \
	CKeyValueFile.o \
	CLinePosition.o \
	CLong.o \
	CMaths.o \
	CObject.o \
	CString.o \
	CShort.o \
	CTextFile.o \
    CThread.o \
	CThreadMutex.o \
	CThreadTimedMutex.o

$(OUT): $(OBJ)
	ar rvs $@ $?

%.o: %.cpp
	$(CC) $(CFLAGS) -c $? -I $(SEARCH_DIR)

#install: $(OUT)
#	cp $(OUT) $(OUT_DIR)

clean:
	rm -f *.o *.a *.ii *.s *.bc
