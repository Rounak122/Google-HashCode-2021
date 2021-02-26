#!/usr/bin/bash

# echo Hello Shell
# wait

class="Main"

javac $class.java
for x in a b c d e f;
do
	echo $x
	java $class<$x.txt>${x}o.txt &
done
wait