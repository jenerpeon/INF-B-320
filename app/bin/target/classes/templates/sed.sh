#!/bin/bash
FILES="/home/lars/develop/Uni/swt/swt15w22/app/src/main/resources/templates/*.html"

for f in $FILES
do
    $(sed 's/src="img\//src="\/resources\/img\//' <$f >${f}_conv)
    mv ${f}_conv $f
    $(sed 's/src="script\//src="\/resources\/script\//' <$f >${f}_conv)
    mv ${f}_conv $f
    $(sed 's/src="Bilder\//src="\/resources\/Bilder\//' <$f >${f}_conv)
    mv ${f}_conv $f
    $(sed 's/<link href="/<link href="\/resources\/css\//' <$f >${f}_conv)
    mv ${f}_conv $f
    $(sed 's/url("/url("\/resources\/css\//' <$f >${f}_conv)
    mv ${f}_conv $f
done 
