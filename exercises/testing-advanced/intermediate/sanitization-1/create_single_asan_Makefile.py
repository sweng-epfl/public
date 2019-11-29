#! /usr/bin/env/python 3.0
#
# Running this script will create the file "Makefile_asan", which can be
# run using "make -f Makefile_asan" to compile all the test cases into a single executable file on Linux.  This script
# also edits source code and header files needed for a successful compilation
# with this Makefile.
#

import sys,os

# add parent directory to search path so we can use py_common
sys.path.append("..")

import py_common

import asan_update_main_cpp_and_testcases_h
import create_per_cwe_files

def create_makefile(dirs):
	contents = "CC=clang\n"
	contents += "CLANG=clang\n"
	contents += "CPP=clang++\n"
	contents += "DEBUG=-g\n"
	contents += "CFLAGS=-c $(DEBUG) -fsanitize=address\n"
	contents += "LFLAGS=-lpthread -fsanitize=address\n"

	contents += "\nINCLUDES=-I testcasesupport\n"

	contents += "\nSUPPORT_PATH=testcasesupport/\n"

	contents += "\nMAIN=$(SUPPORT_PATH)main_linux.c\n"
	contents += "MAIN_OBJECT=main_linux.o\n"

	contents += "\nC_SUPPORT_FILES=$(SUPPORT_PATH)io.c $(SUPPORT_PATH)std_thread.c\n"
	contents += "C_SUPPORT_OBJECTS=io.o std_thread.o\n"

	contents += "\n# only grab the .c files without \"w32\" or \"wchar_t\" in the name\n"
	contents += "C_SOURCES="

	for dir in sorted(dirs):
		linux_dir = (os.path.relpath(dir)).replace('\\', '/')

		contents += "$(filter-out $(wildcard " + linux_dir + "/CWE*w32*.c) $(wildcard " + linux_dir + "/CWE*wchar_t*.c),$(wildcard " + linux_dir + "/CWE*.c)) \\\n"

	# remove the last '\' from the previous line
	contents = contents[:-2] + "\n"
	contents += "C_OBJECTS=$(C_SOURCES:.c=.o)\n"

	contents += "\n# only grab the .cpp files without \"w32\" or \"wchar_t\" in the name\n"
	contents += "CPP_SOURCES="

	for dir in sorted(dirs):
		linux_dir = (os.path.relpath(dir)).replace('\\', '/')

		contents += "$(filter-out $(wildcard " + linux_dir + "/CWE*w32*.cpp) $(wildcard " + linux_dir + "/CWE*wchar_t*.cpp),$(wildcard " + linux_dir + "/CWE*.cpp)) \\\n"

	# remove the last '\' from the previous line
	contents = contents[:-2] + "\n"
	contents += "CPP_OBJECTS=$(CPP_SOURCES:.cpp=.o)\n"

	contents += "\nOBJECTS=$(MAIN_OBJECT) $(C_OBJECTS) $(C_SUPPORT_OBJECTS)\n"
	contents += "TARGET=all-asan-testcases\n"

	contents += "\nall: $(TARGET)\n"

	contents += "\n$(TARGET) : $(OBJECTS)\n"
	contents += "	$(CC) $(LFLAGS) $(OBJECTS) -o $(TARGET)\n"

	contents += "\n$(C_OBJECTS) : $(C_SOURCES)\n"
	contents += "	$(CC) $(CFLAGS) $(INCLUDES) $(@:.o=.c) -o $@\n"

	contents += "\n$(CPP_OBJECTS) : $(CPP_SOURCES)\n"
	contents += "	$(CC) $(CFLAGS) $(INCLUDES) $(@:.o=.cpp) -o $@\n"

	contents += "\n$(C_SUPPORT_OBJECTS) : $(C_SUPPORT_FILES)\n"
	contents += "	$(CC) $(CFLAGS) $(INCLUDES) $(SUPPORT_PATH)$(@:.o=.c) -o $@\n"

	contents += "\n$(MAIN_OBJECT) : $(MAIN)\n"
	contents += "	$(CC) $(CFLAGS) $(INCLUDES) $(MAIN) -o $@\n"

	contents += "\nclean:\n"
	contents += "	rm -rf *.o $(TARGET)"

	for dir in sorted(dirs):
		linux_dir = (os.path.relpath(dir)).replace('\\', '/')

		contents += " " + linux_dir + "/*.o"

	contents += "\n"

	return contents

def get_directory_names_to_compile(directory):
	files = py_common.find_files_in_dir(directory, testregex)

	dirs = set()
	for file in files:
		base_dir = os.path.dirname(file)
		dirs.add(base_dir)

	return dirs

if __name__ == "__main__":

	# check if ./testcases directory exists, if not, we are running
	# from wrong working directory
	if not os.path.exists("testcases"):
		py_common.print_with_timestamp("Wrong working directory; could not find testcases directory")
		exit()

	global testregex
	# Only include the tests cases that are relevant, that don't involve sockets
	#testregex = "CWE(122|XXX|YYY|ZZZ)+((?!socket).)*(\.c)$"
	testregex = "CWE(562)+((?!socket).)*(\.c)$"

	# update main_linux.cpp/testcases.h
	# Only take the test cases we care about, and only the .c tests
	testcase_files = asan_update_main_cpp_and_testcases_h.build_list_of_primary_c_cpp_testcase_files("testcases", [testregex])
	fcl = asan_update_main_cpp_and_testcases_h.generate_calls_to_fxs(testcase_files)
	linux_fcl = asan_update_main_cpp_and_testcases_h.generate_calls_to_linux_fxs(testcase_files)
	asan_update_main_cpp_and_testcases_h.update_main_cpp("testcasesupport", "main_linux.c", linux_fcl)

	# update testcases.h to include all functions (including w32-only)
	asan_update_main_cpp_and_testcases_h.update_testcases_h("testcasesupport", "testcases.h", fcl)

	dirs = get_directory_names_to_compile("testcases")

	makefile_contents = create_makefile(dirs)

	py_common.write_file("Makefile_asan", makefile_contents)
