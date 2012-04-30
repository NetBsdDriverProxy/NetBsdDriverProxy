#!/bin/bash

pushd src_netbsd >/dev/null

dir=.
positive=$(find $dir                     \( -name '*.c'                 \) )
header__=$(find $dir                     \(                 -name '*.h' \) )
negative=$(find $dir -name '*.*' -and \! \( -name '*.c' -or -name '*.h' \) )
echo 'OMITTED FILES:'
echo $negative | tr ' ' '\n'
echo

incdir=$(for f in $header__
do
	echo " -I "
	dirname $f
done)
echo $incdir

echo 'BUILD:'
for f in $positive
do
	gcc $incdir $f
	:
done
echo

popd >/dev/null 
