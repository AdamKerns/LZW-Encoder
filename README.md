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

Choose one of the following methods to get the project locally:

#### Option 1. Clone Repository
- Clone
```bash
git clone https://github.com/AdamKerns/LZW-Encoder.git
```

#### Option 2. Download as ZIP

1. Click the Code button
2. Select Download ZIP
3. Extract the files to your desired location


- After Extracting the Folder, create a text file name ```input.txt``` go to your terminal and run
```bash
java LZW.java input.txt 12
``` 
