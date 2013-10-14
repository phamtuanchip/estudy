E-STUDY READ ME

This is an open source project to adap e learing system basic on GateIn portal framework gatein.org

Status 
[![Build Status](https://travis-ci.org/phamtuanchip/estudy.png)](https://travis-ci.org/phamtuanchip/estudy)

public page: http://phamtuanchip.github.io/estudy

wiki page: http://wiki.vfossa.vn/mhst:ideas:mhst2013:gatein01

discussion: https://groups.google.com/d/forum/open-estudy

project manager tool: http://www.hostedredmine.com/projects/estudy

integration build test: https://travis-ci.org/phamtuanchip/estudy

social group: https://www.facebook.com/groups/open.estudy

mailinglist: open-estudy@googlegroups.com

Developing environment:

-Maven 3.0.4

-Java  1.6.x

-Tomcat application server 7

-Eclipse for j2ee

-Git client tool  

Quick run guide: 

clone repository 

build with maven by : mvn clean install -s settings.xml

download gatein 3.5.4 final tomcat 7 bundle  from there https://docs.google.com/file/d/0Bw2eZ8CfkgNBOHZuN0hBNGhSVVE/edit?usp=sharing

copy service/target/estudy-service.jar  to tomcat/lib 

copy webui/target/estudy.war to tomcat/webapps

go to tomcat/bin and run command "gatein.sh run" for unix base (mac, linux) or "gatein.bat run" for windows  

login page http://localhost:8080/portal by root with pass gtn 

import all application in application registry page 

create new page and add estudy portlet to that page

Ref-guide http://docs.exoplatform.com/public/index.jsp?topic=%2FPLF40%2Fsect-Reference_Guide-Tutorials-Deploying_your_first_Portlet.html

Guide for connecto to MySql DB http://docs.exoplatform.com/public/topic/PLF40/PLFAdminGuide.Configuration.DatabaseConfiguration.html



