#!/bin/bash

sudo mkdir  /java
  sudo mkdir  /java/estudy-working

#wget www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html/jdk-7u45-linux-x64.tar.gz
#  wget 
sudo apt-get -y update

  # install java
	sudo apt-get -y install jdk*
	sudo apt-get -y install vim
	sudo apt-get -y install maven
#	sudo apt-get -y install tomcat7

  # install git
  sudo apt-get -y install git

   

#clone estudy
cd /java/estudy-working
sudo git clone https://github.com/phamtuanchip/estudy
cd /java/estudy-working/estudy
sudo ./mvn-pkg.sh
cd /java/estudy-working/estudy/packaging/tomcat/tomcat7/target/tomcat/bin
sudo ./gatein.sh start


