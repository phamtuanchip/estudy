#!/bin/bash

LOG_PATH=/vagrant/logs
DELIVERY_PATH=/vagrant/delivery
DELIVERY_FAILED_PATH=$DELIVERY_PATH/failed
DELIVERY_SUCCEEDED_PATH=$DELIVERY_PATH/succeeded
DELIVERY_TMP_PATH=$DELIVERY_PATH/tmp
DELIVERY_LOG_FILE=$LOG_PATH/delivery.txt
TAR_EXT=.tar.gz
ZIP_EXT=.zip
WAR_EXT=.war


MANIFEST_NAME=manifest.txt
# the field separator in each line in the manifest.txt
FIELD_SEPARATOR=,
# ignore lines beginning with '#'
LINE_COMMENT=#

REPOSITORY_PATH=/vagrant/repository
DEPLOYMENT_LOG_FILE=$LOG_PATH/deployment.txt

################################################################################################
# print current time with special format
function TIMESTAMP() {

  local NOW=$(date +"%m-%d-%Y %H:%M:%S")
  echo $NOW $*

}

################################################################################################
# initialize git repository
function InitializeGitRepository() {
  if [ ! -d $REPOSITORY_PATH/.git ]
  then
mkdir -p $REPOSITORY_PATH
    cd $REPOSITORY_PATH
    git init
    touch Readme
    git add Readme
    git commit -m "Create an empty repository"
  fi
}

 

################################################################################################
# $1 file path which should be deployed
# $2 context path
# $3 the deployment log
function DeployWarFile() {

  curl --upload-file $1 "http://root:gtngtn@localhost:8080/manager/deploy?path=/$2&update=true" 1>>$3 2>/dev/null

}


 
################################################################################################
function InstallCurl() {

# install a tool to transfer data from or to a server
sudo apt-get -y install curl


}

################################################################################################
# run a job every 't' minutes
# $1 job name
# $2 the number of minutes the job will run after
function AddScriptToCrontab() {

  # try to remove the old schedule of the job
  sudo crontab -l | grep -v $1 > /tmp/cron.$$
  # add the new schedule
  sudo echo "*/$2 * * * * bash $1" >> /tmp/cron.$$
 
  sudo crontab /tmp/cron.$$
  sudo rm -f /tmp/cron.$$
}
