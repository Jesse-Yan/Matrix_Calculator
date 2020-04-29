README

Course: cs400
Semester: Spring 2020
Project name: Matrix calculator
Team Members:
Chengpo Yan, LEC001, cyan46@wisc.edu
Jinming Zhang, LEC001, jzhang2279@wisc.edu
Archer Li LEC001, zli885@wisc.edu
Houming Chen, LEC001, hchen634@wisc.edu
Chengxu Bian, LEC001, cbian7@wisc.edu

 

Which team members were on same xteam together?
*no one.

Other notes or comments to the grader:

Program can simply running by using command line to run the executable.jar file. Running in Java code required JavaFx and JDK11 pre-installed.
Running demo
PLEASE USING FOLLOWING COMMAND FOR RUNNING .JAR FILE
REMEMBER TO CHANGE "Path-to-javafx-lib" TO YOUR OWN PATH
java --module-path "Path-to-javafx-lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar

Program Description
program functions as a calculator that support max 9*9 Matrix calculations supports pirorty calculation. Matrixs and calculation can be read from json files for computing result.
!Mark! to open .json file, click Menu then click Open.
Please see SimpleData.json for the sample format. (other json file is design for testing purpose, each is built base on its name. Result.json is sample result output.) 
You can click '<' '>' or '√' to change from different operations that been read from the correct .json files.
Warning, If code in ateam.zip won't run, please check CS400_final_project.zip (for original directory path)

#### Warning, If code in ateam.zip won't run, please check CS400_final_project.zip
#### Be sure to check README.md for more detailed describtion!!!

Known Issue (BUG Report)
*Cholesky will always produce wrong result(FIXED)
*Might cause stackoverflow when giving a Huge Odd number (vary base on PC set up)
*Grammer issue in readme.md and .txt (So Sorry about that)
