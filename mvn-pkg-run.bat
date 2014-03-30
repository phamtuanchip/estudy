mvn clean install -s settings.xml
cd packaging 
mvn clean install -s ../settings.xml
cd tomcat\tomcat7\target\tomcat\bin
call gatein.bat run
