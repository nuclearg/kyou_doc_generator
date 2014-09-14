#!/bin/sh

mvn clean package

java -jar target/kyou-doc-generator-1.0-SNAPSHOT.jar ../kyou ../kyou.wiki

cd ../kyou.wiki

git add *
git commit -m "wiki"
git push origin master
