#!/bin/bash

pushd src_analyze >/dev/null
javac Analyze.java
popd >/dev/null

./analyze.sh
