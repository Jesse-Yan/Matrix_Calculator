.PHONY = compile run jar runjar zip all clean

JC = /usr/lib/jvm/openjdk-11-jdk-hotspot/bin/javac

JRE =  /usr/lib/jvm/openjdk-11-jdk-hotspot/bin/java

MP = --module-path /usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 

CP = -classpath /usr/project/HelloFX:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.base.jar:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.controls.jar:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.fxml.jar:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.graphics.jar:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.media.jar:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.swing.jar:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.web.jar:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx-swt.jar:/usr/lib/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/src.zip

SRC = application/*.java   
APP = application.Main

ARGS =  projects# place your command line args here

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
