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

!Mark! To open .json file, click Menu then click Open.
       To save result of opened .json file, please click Menu-Save and enter the file name

Please see SimpleData.json for the sample format. (other json file is design for testing purpose, each is built base on its name. Result.json is sample result output.) 

You can click '<' '>' or '√' to change from different operations that been read from the correct .json files.

Warning, If code in ateam.zip won't run, please check CS400_final_project.zip (for original directory path)

# Please check Calculator Instructions.docx

# Be sure to check README.md for more detailed describtion for the program.

Known Issue (BUG Report)
1. Cannot always find all the real eigenvalues of a Matrix.
Theoretically, a QR algorithm with Wilkinon-Shift will always converge for hessenburg matrices. See https://www.ams.org/journals/mcom/2002-71-240/S0025-5718-01-01387-4/S0025-5718-01-01387-4.pdf. However, although a QR algorithm with Wilkinon-Shift is implemented in this program, it seems sometimes, though very rare, the QR algorithm still doesn't converge. To deal with this, we tried to do some QR iterration with normal shift if the QR algorithm with Wilkinon-Shift doesn't converge in 1000 iterations, but still it cannot make sure that the algorithm always converge. If the QR algorithm doesn't converge in 4000 iterations, the QR algorithm will be stopped. This lead to the fact that our algorithm cannot always find all the real eigenvalues of a Matrix. This bug might be fixed in the future by more studies on the QR algorithm and its implementation in the program.

2. Floating-point error might cause inaccuracies
In this program, since many calculations are operations with double, due to the floating-point error, the result might not be accurate. Sometimes, the floating-point error might be enlarged significantly by the Butterfly Effect in the complicated calculation processes. To deal with this, in order to decrease the impact of floating-point error, we now require the input matrix to be no larger than 9*9, and the input numbers must be in the range [-100000, 100000] with no more than 3 decimal digits. This issue might be fixed in the future by changing some double type to java.math.BigDecimal to decrease the floating-point error.

／ イ  　　　(((ヽ        +++++++++++++++
(　 ﾉ　　　　  ￣Ｙ ＼   （Future updates）
|　(＼　(\   /)  ｜  ) / +++++++++++++++++
ヽ　ヽ` ( ﾟ∀ﾟ ) _ノ /
　＼ |　⌒Ｙ⌒　/  /
　  ｜ヽ　 ｜　 ﾉ ／
　   ＼トー仝ーイ
　　  ｜ ミ土彡/
      ) \  °   /  
     (   \  /  \
   / /  /      \ \ \
   ( (  ).       )  ).)
   (    ).       ( |  |
    |  /           \  |
  nn.  ).           (  nnm
  
  @arthor - Archer
  
  （Future updates）
  
  1. More Accurate calculation
  2. Support ui key board focus on power section
  3. Support fraction input
  4. Alert when user try to save when there is no input file.
