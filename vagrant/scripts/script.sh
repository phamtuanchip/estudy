#!/bin/bash

#sudo mkdir  /java
 # sudo mkdir  /java/estudy-working

#wget www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html/jdk-7u45-linux-x64.tar.gz
#  wget 
sudo apt-get -y update

  # install java
	sudo apt-get -y install openjdk-7-jre-headless 

        sudo apt-get -y install openjdk-7-jdk 
	sudo apt-get -y install vim
	sudo apt-get -y install maven
#	sudo apt-get -y install tomcat7

  # install git
  sudo apt-get -y install git
sudo apt-get -y install incron
echo "/estudy/vagrant/deployment IN_MODIFY,IN_CREATE,IN_DELETE /estudy/vagrant/scripts/trigger.sh" >> /tmp/tmpcron
sudo incrontab /tmp/tmpcron
   

#clone estudy
sudo git clone https://github.com/phamtuanchip/estudy
cd /home/vagrant/estudy
sudo ./mvn-pkg.sh
cd /home/vagrant/estudy/packaging/tomcat/tomcat7/target/tomcat/bin
sudo ./gatein.sh run


