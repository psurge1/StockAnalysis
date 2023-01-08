#! /usr/bin/bash

# class files
rm -f *.class
cd utils # src/utils
rm -f *.class
cd ..

# __pycache__/
cd Python/__pycache__
rm *.pyc
cd ../..
rmdir Python/__pycache__