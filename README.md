Book-data-converter
===================

First phase
==================

This application will convert book data of one text format to another text format. It will take input of 

a filename and a target text format. It will read the content of the file and convert it to another 

format.

=====================
cd to Book-data-converter/bin folder and try command "java com/java/fileconverter/Convert input.txt xml", it will detect input file from "inputfiles" folder. input.txt and input.xml files are exixt in "inputfiles" folder.

And it will generate output in "outputfiles" folder as output.txt and output.xml file.


==============================
Second phase
==============================

ISBN number has been added and checked if all data contain it or not. If not then an error message will be showing and exit from conversion.

When we convert xml to text format, book name will be showing in capital letter in output , but not stored in text file.

cd to Book-data-converter/bin folder and try command "java com/java/fileconverter/Convert input.xml text"

