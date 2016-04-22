#!/bin/sh

export MEMORANDA_HOME=./data

LCP="./build/memoranda.jar:./lib/xom-1.0.jar:./lib/xercesImpl.jar:./lib/xmlParserAPIs.jar:./lib/nekohtml.jar:./lib/nekohtmlXni.jar:./lib/slf4j-simple-1.7.20.jar:./lib/slf4j-api-1.7.20.jar:./lib/ical4j-2.0-beta1.jar:./lib/commons-codec-1.10.jar"

java -cp ${LCP} net.sf.memoranda.Start $1
