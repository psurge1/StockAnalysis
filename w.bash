#!/usr/bin/bash

cd src # src
javac Main.java
    echo "Compiled"
java Main "2000x2040x0.1" "8" "1982 2001 2008 2013 2015 2016 2021 2022" "0.08 0.4 5.76 14.77 32.24 26.42 151.28 171.1"
# java Main "0x10x0.1" "5" "0, 1, 2, 3, 4" "0, 1, 4, 9, 16"
    echo "Run"
rm -f *.class
cd utils # src/utils
rm -f *.class
    echo "Cleaned"
cd .. # src
    echo "Displayed"
python main.py "../storage/points.txt"
    echo "Closed"

# kotlinc -script Script.kts
# kotlinc main.kt -d main.jar  # library
# kotlinc main.kt -include-runtime -d Main.jar  # runnable
# java -jar Main.jar