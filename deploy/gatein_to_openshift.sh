#gatein deploy to openshift
#https://community.jboss.org/wiki/DeployingGateInToOpenShift

sudo gem install rhc
gem update rhc
rhc setup
rhc app create -a portal -t jbossas-7
rhc show-app portal
curl http://downloads.jboss.org/gatein/Releases/Portal/3.4.0.Final/GateIn-3.4.0.Final-jbossas7.zip
unzip 3.4.0.Final/GateIn-3.4.0.Final-jbossas7.zip
cd portal 
cp -r ../GateIn-3.5.0.Final-jbossas7/modules .openshift/config/
mkdir -p .openshift/config/modules/org/jboss/as/web     
cp -r ../GateIn-3.5.0.Final-jbossas7/modules/org/jboss/as/web .openshift/config/modules/org/jboss/as
cp -r ../GateIn-3.5.0.Final-jbossas7/standalone/configuration/gatein .openshift/config/
cp -r ../GateIn-3.5.0.Final-jbossas7/gatein/gatein.ear* deployments
less .git/config grep url
curl -o .openshift/config/standalone.xml https://raw.github.com/mstruk/gatein-openshift/stickshift/config/standalone.xml
git diff HEAD .openshift/config/standalone.xml
touch .openshift/markers/skip_maven_build
git add .openshift/config/gatein/
git add .openshift/config/modules/*
git add .openshift/markers/skip_maven_build
git add deployments/*
git commit -a -m "Initial commit to deploy gatein to openshift"
git push

#after I push : 

Counting objects: 3055, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (2612/2612), done.
Writing objects: 100% (3048/3048), 178.70 MiB | 319 KiB/s, done.
Total 3048 (delta 551), reused 0 (delta 0)
remote: Stopping jbossas cartridge
remote: Sending SIGTERM to jboss:26552 ...
remote: Starting application portal
remote: Deploying JBoss
remote: Starting jbossas cartridge
remote: Timed out waiting for http listening port
remote: An error occurred executing 'gear postreceive' (exit code: 1)
remote: Error message: Failed to execute: 'control start' for /var/lib/openshift/5237e3684382ec4c09000041/jbossas
remote: 
remote: For more details about the problem, try running the command again with the '--trace' option.
To ssh://5237e3684382ec4c09000041@portal-jbosssix.rhcloud.com/~/git/portal.git/
   f133106..2070fff  master -> master
may102:portal tuanp$ 




