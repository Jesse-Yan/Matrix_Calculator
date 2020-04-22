.PHONY = compile run jar runjar zip all clean

JC = /usr/lib/jvm/adoptopenjdk-11-jdk-hotspot/bin/javac

JRE =  /usr/lib/jvm/adoptopenjdk-11-jdk-hotspot/bin/java

MP = --module-path /p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 

CP = -classpath /afs/cs.wisc.edu/u/d/e/deppeler/eclipse-workspace/HelloFX:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.base.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.controls.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.fxml.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.graphics.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.media.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.swing.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.web.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx-swt.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/src.zip

SRC = application/*.java   
APP = application.Main

ARGS = deb mark sapan # place your command line args here

compile:
$(JC) $(CP) $(SRC)

run:
$(JRE) $(MP) $(CP) $(APP) $(ARGS)

jar:
      jar -cvmf manifest.txt executable.jar .

runjar:
        java -jar executable.jar

zip:
        zip -r ateam.zip

clean:
        rm -f application/*.class
        rm -f executable.jar
