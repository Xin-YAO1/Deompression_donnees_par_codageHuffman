
Uncompression_data_by_huffman_coding

Huffman encoding, named after its designer, is a statistical method of compressing data. Its principle is to replace a character (or symbol) by a series of bits of variable length. The underlying idea is to encode what is frequent on few bits and on the contrary what is rare on longer bit sequences. Huffman encoding allows lossless compression, that is, a string of bits exactly the same as the original is rendered by decompression. However, it requires that the frequencies of appearance of the various symbols to be encoded be known (or estimated). There are thus several variations of the Huffman algorithm (static, semi-adaptive or adaptive) now used in file compression algorithms such as gzip.

This topic concerns the decompression phase of the algorithm in which a compressed text can be decoded if the original alphabet is known as well as the frequency distribution of the characters that constitute it.

How to use?

To work the codes, you must import all and insert the address of the file.bin and file_fre.txt then execute them, you can obtain the decompressed file in the same root as the codes. 
