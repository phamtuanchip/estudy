mvn clean install -s settings.xml -P!pkg-tomcat -Pdocs 
cd packaging
mvn clean install -s ../settings.xml -Ppkg-tomcat -Ddownload
