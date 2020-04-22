.PHONY = make jar runjar test clean

# replace with path to your javac,java,jar,javafx installations
JC = /usr/bin/javac     # replace with path to javac or javac.exe
JAR = /usr/bin/jar      # replace with path to jar or jar.exe
JAVA = /usr/bin/java    # replace with path to java or javaw.exe
MP = --module-path javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml #-Dfile.encoding=UTF-8 
CP = -classpath ".:application" 
APP = application.Main

#CLASSPATH = .:junit-platform-console-standalone-1.5.2.jar:json-simple-1.1.1.jar

make: 
	$(JC) $(MP) $(CP) -d . application/*.java

run:
	$(JAVA) $(MP) $(CP) application.Main

fx: 
	$(JC) $(MP) $(CP) -d . application/*.java

fxrun:
	$(JAVA) $(MP) $(CP) $(APP)

jar: 
	$(JAR) cvmf manifest.txt executable.jar .

runjar:
	java $(MP) -jar executable.jar

zip:
	zip team.zip application/* *

test: 
	javac $(MP) -cp $(CLASSPATH) *.java
	java -jar junit-platform-console-standalone-1.5.2.jar --class-path $(CLASSPATH) -p ""

clean:
	\rm application/*.class
	\rm executable.jar
