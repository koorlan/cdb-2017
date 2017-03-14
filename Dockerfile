FROM tomcat:7-jre8

ADD target/cdb.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]
