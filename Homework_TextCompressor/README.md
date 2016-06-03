##Text Compressor
Huffman code implementation using PriorityQueue to generate optimal prefix code to be used for lossless data compression.

####Useful classes
[***TextCompressor***](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_TextCompressor/src/main/java/com/daniilyurov/training/compressor/TextCompressor.java) has method *compress(File uncompressedFile, File compressedFile)*.
It takes two *File* objects as arguments. 
The first one indicates the file to read uncompressed text from.
The second argument specifies where should the compressed data be written to.

[***TextExpander***](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_TextCompressor/src/main/java/com/daniilyurov/training/compressor/TextExpander.java) has method *expand(File compressedFile)*.
It takes a *File* object as an argument.
The file indicates the path to read compressed data from.
The method expands data back to original text which is returned in a form of String.