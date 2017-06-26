from openjdk:8-jre-alpine
add target/novice.jar /opt/novice.jar
cmd java -jar /opt/novice.jar
