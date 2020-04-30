# CS400_final_project
This is the final project of ateam2 of CS 400 Spring 2020

##### *Course: cs400

##### *Semester: Spring 2020

##### *Project name: Matrix calculator

## contributer/Team member
#### Chengpo Yan LEC001 xteam186 - cyan46@wisc.edu
#### Jinming Zhang, LEC001, x-team132 - jzhang2279@wisc.edu
#### Archer Li LEC001 x-team145 - zli885@wisc.edu
#### Houming Chen, LEC001, xteam149 - hchen634@wisc.edu
#### Chengxu Bian, LEC001, xteam102 - cbian7@wisc.edu


## Installation
Program can simply running by using command line to run the executable.jar file.
Running in Java code required JavaFx and JDK11 pre-installed.

## Running demo
using command line to run executable.jar for demo(see file: running_command.txt)

## Program Description
program functions as a calculator that support max 9*9 Matrix calculations supports pirorty calculation. Matrixs and calculation can be read from json files for computing result.

!Mark! To open .json file, click Menu then click Open.
       To save result of opened .json file, please click Menu-Save and enter the file name

Please see SimpleData.json for the sample format. (other json file is design for testing purpose, each is built base on its name. Result.json is sample result output.) 

You can click '<' '>' or '√' to change from different operations that been read from the correct .json files.

Warning, If code in ateam.zip won't run, please check CS400_final_project.zip (for original directory path)

#### !Mark! To open .json file, click Menu then click Open.
#### !Mark! To save result of opened .json file, please click Menu-Save and enter the file name

#### Warning, If code in ateam.zip won't run, please check CS400_final_project.zip

## Code Organization
UW-madison default setup.
program are base on stack, 2D array, Arraylist, and other combination of data structure. 
Check design.pdf to learn more.

## Functions
functions can be entered by press the button

The matrix must first set the row and column then press the button to do the calculate

The user must choose if the matrix calculates with one or two matrixes

The people can analyze an input sequence and return the max, min, and average value.



## Known Issue (BUG Report)

1. Cannot always find all the real eigenvalues of a Matrix.

Theoretically, a QR algorithm with Wilkinson-Shift will always converge for hessenburg matrices. See https://www.ams.org/journals/mcom/2002-71-240/S0025-5718-01-01387-4/S0025-5718-01-01387-4.pdf. However, although a QR algorithm with Wilkinson-Shift is implemented in this program, it seems sometimes, though very rare, the QR algorithm still does not converge. To deal with this, we tried to do some QR iteration with normal shift if the QR algorithm with Wilkinson-Shift doesn't converge in 1000 iterations, but still it cannot make sure that the algorithm always converges. If the QR algorithm does not converge in 4000 iterations, the QR algorithm will be stopped. This led to the fact that our algorithm cannot always find all the real eigenvalues of a Matrix. This bug might be fixed in the future by more studies on the QR algorithm and its implementation in the program.

2. Floating-point error might cause inaccuracies

In this program, since many calculations are operations with double, due to the floating-point error, the result might not be accurate. Sometimes, the floating-point error might be enlarged significantly by the Butterfly Effect in the complicated calculation processes. To deal with this, in order to decrease the impact of floating-point error, we now require the input matrix to be no larger than 9*9, and the input numbers must be in the range [-100000, 100000] with no more than 3 decimal digits. This issue might be fixed in the future by changing some double type to java.math.BigDecimal to decrease the floating-point error.

Author:Archer Li
