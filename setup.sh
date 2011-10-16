#!/bin/sh

if [ $# = 0 ]; then
	echo "Usage: ./setup.sh YOUR_NEW_NAMESPACE"
	exit
fi

NS=$1
NSPATH=`echo $1 | sed -e "s/\./\//g"`

mkdir -p .backup

## project.clj
mv project.clj .backup
sed -e "s/heroku-template/$NS/g" .backup/project.clj > project.clj

# src
mv src .backup
mkdir -p "src/$NSPATH"

for FILE in `find .backup/src -type f`; do
	NAME=`echo $FILE | cut -d"/" -f4`
	sed -e "s/heroku-template/$NS/g" $FILE > "src/$NSPATH/$NAME"
done

# test
mv test .backup
mkdir -p "test/$NSPATH/test"

for FILE in `find .backup/test -type f`; do
	NAME=`echo $FILE | cut -d"/" -f5`
	sed -e "s/heroku-template/$NS/g" $FILE > "test/$NSPATH/test/$NAME"
done



