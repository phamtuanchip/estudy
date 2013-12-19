#!/bin/bash


sudo ./gatein.sh stop
find /home/vagrant/estudy/vagrant/deployment/ -type f -iname *.war -exec cp {} /home/vagrant/estudy/packaging/tomcat/tomcat7/target/tomcat/webapps
find /home/vagrant/estudy/vagrant/deployment/ -type f -iname *.jar -exec cp {} /home/vagrant/estudy/packaging/tomcat/tomcat7/target/tomcat/lib
sudo ./gatein.sh start


