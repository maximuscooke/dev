CC=g++

#Platform options are MAC (Apple Mac) or WIN (windows)
PLATFORM=MAC

VER=c++14

CFLAGS=-Wall -O2 -std=$(VER) -D$(PLATFORM) -DTYPE_SAFE -pthread
#un-comment for debug build
#CFLAGS=-Wall -DDEBUG -g -O0 -std=$(VER) -D$(PLATFORM) -pthread

#source code route directory
DEV_DIR=$(DEV)/src/cpp

HEADER_PATH=$(DEV_DIR)/libcommon
LINKER_PATH = /usr/local/Cellar/glfw/3.3.4/lib

#INC_DIR = -I $(SEARCH_PATH) -L $(LINK_LIB)

OUT=gltest.exe

OBJ=gltest.o

$(OUT): $(OBJ)
	$(CC) $(CFLAGS) -framework OpenGl \
	-L $(HEADER_PATH) -lcommon \
	-L /usr/local/Cellar/glfw/3.3.4/lib -lglfw.3.3 \
	-L /usr/local/Cellar/glew/2.2.0_1/lib -lGLEW \
	$? -o $@

%.o: %.cpp
	$(CC) $(CFLAGS) -c $? -I $(HEADER_PATH)

clean:
	rm -f *.o *.exe
