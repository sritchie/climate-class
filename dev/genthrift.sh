#!/bin/sh

rm -rf ../src/jvm/gen-java

# Hashcode impl is really important!
thrift -o "../src/jvm" -r --gen java:hashcode climate.thrift
