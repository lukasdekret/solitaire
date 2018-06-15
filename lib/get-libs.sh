#!/bin/sh

wget http://www.stud.fit.vutbr.cz/~xdekre00/img.zip | tr -d '\r'
unzip img.zip -d lib | tr -d '\r'
rm img.zip | tr -d '\r'
