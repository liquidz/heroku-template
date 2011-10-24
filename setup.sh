#!/bin/sh

if [ $# = 0 ]; then
	echo "Usage: ./setup.sh YOUR_NEW_NAMESPACE"
	exit
fi

NS=$1
NSPATH=`echo $1 | sed -e "s/\./\//g"`

mkdir -p .backup

# project.clj
mv project.clj .backup
sed -e "s/heroku-template/$NS/g" .backup/project.clj > project.clj

# src
cp -pir src .backup
git rm -r src
mkdir -p "src/$NSPATH/addons"

for FILE in `find .backup/src -type f`; do
	NAME=`echo $FILE | cut -d"/" -f4-`
	sed -e "s/heroku-template/$NS/g" $FILE > "src/$NSPATH/$NAME"
done

# html
mkdir -p "src/html"

for FILE in `find .backup/src/html -type f`; do
    NAME=`echo $FILE | cut -d"/" -f4-`
    sed -e "s/heroku-template/$NS/g" $FILE > "src/html/$NAME"
done

git add src

# test
cp -pir test .backup
git rm -r test
mkdir -p "test/$NSPATH/test"

for FILE in `find .backup/test -type f`; do
	NAME=`echo $FILE | cut -d"/" -f5-`
	sed -e "s/heroku-template/$NS/g" $FILE > "test/$NSPATH/test/$NAME"
done

git add test
git commit -am "heroku template setup"
