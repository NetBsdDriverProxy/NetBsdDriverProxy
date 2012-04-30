#!/bin/bash

pushd src_analyze >/dev/null

dir=../src_netbsd
positive=$(find $dir                     \( -name '*.c' -or -name '*.h' \) )
negative=$(find $dir -name '*.*' -and \! \( -name '*.c' -or -name '*.h' -or -name '*.o' \) )
echo 'OMITTED FILES:'
echo $negative | tr ' ' '\n'
echo

echo 'ANALYZE:'
java -ea Analyze $positive
echo

popd >/dev/null
