cd packaging
mvn clean install -s ../settings.xml -Ppkg-tomcat -Ddownload -Dservers.dir=release
