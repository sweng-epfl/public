#!/bin/bash
wget -c https://samate.nist.gov/SRD/testsuites/juliet/Juliet_Test_Suite_v1.3_for_C_Cpp.zip
unzip Juliet_Test_Suite_v1.3_for_C_Cpp.zip

cp asan_update_main_cpp_and_testcases_h.py create_single_asan_Makefile.py C/
cp main_linux.c C/testcasesupport/
pushd C
python3 create_single_asan_Makefile.py
make -f Makefile_asan
popd

pushd C
./all-asan-testcases
popd
