javac src/RESTreaming/*.java -cp lib/libraries.jar -d WEB-INF/classes
jar cvf RESTreaming.war WEB-INF
asadmin deploy --force RESTreaming.war
