#!/bin/sh

pushd src_analyze >/dev/null

positive=$(find ../src_netbsd                     \( -name '*.c' -or -name '*.h' \) )
negative=$(find ../src_netbsd -name '*.*' -and \! \( -name '*.c' -or -name '*.h' \) )
echo 'OMITTED FILES:'
echo $negative | tr ' ' '\n'
echo

echo 'ANALYZE:'
java -ea Analyze $positive
echo

popd >/dev/null
