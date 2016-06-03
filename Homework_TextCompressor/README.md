##Text Compressor
Huffman code implementation using PriorityQueue to generate optimal prefix code to be used for lossless data compression.

####Useful classes
***TextCompressor*** has method *compress(File uncompressedFile, File compressedFile)*.
It takes two *File* objects as arguments. 
The first one indicates the file to read uncompressed text from.
The second argument specifies where should the compressed data be written to.

***TextExpander*** has method *expand(File compressedFile)*.
It takes a *File* object as an argument.
The file indicates the path to read compressed data from.
The method expands data back to original text which is returned in a form of String.