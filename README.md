## Adam Kerns

# LZW Encoder and Decoder
---

### Program Design

The program is written in Java and made up of one class LZW that can encode and decode a file.
There are 4 methods (other than the main method)
- encodeFile
- decodeFile
- findIndex (helper function)
- outputFile (helper function)

### Program Info

For the encoding and decoding I had to use a memoized "dynamic" array which we have used in dynamic programming algorithms in class. This memoized array has ASCII symbols (0 - 255) and updates as a new symbol is introduced in the program.

Other data structures used are ArrayLists.

Written in VS Code
Compiler: javac 19.0.1

### How to Use

- After Extracting the Folder (as you've done already), go to your terminal and run
```bash
java LZW.java *your-input-text.txt* *length of bits to use*
``` 
