// Adam Kerns 801293998

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LZW {
    short MAX_TABLE_SIZE;
    File File;
    String filename;
    public static void main(String[] args){

        LZW enc = new LZW();

        if (args.length != 0) {
            File file = new File(args[0]);
            enc.File = file;
            enc.MAX_TABLE_SIZE = (short) Math.pow(2, Integer.parseInt(args[1]));
            enc.filename = file.getName();
        } else {
            enc.File = new File("input.txt");
            enc.filename = "input.txt";
            enc.MAX_TABLE_SIZE = (short) Math.pow(2, 12);
        }

        ArrayList<Integer> encoded = encodeFile(enc.File, enc.MAX_TABLE_SIZE);
        System.out.println(encoded);
        outputFile(enc.filename, encoded);

        String lzwName = enc.filename.substring(0, enc.filename.lastIndexOf('.')) + ".lzw";
        decodeFile(lzwName, enc.MAX_TABLE_SIZE);

    }

    public static ArrayList<Integer> encodeFile(File file, short max_size) {
        String[] TABLE = new String[max_size];
        for (int i = 0; i <= 255; i++) {
            TABLE[i] = String.valueOf((char) i);
        }

        int openSpot = 256;
        String STRING = "";
        ArrayList<Integer> code = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(file)) { 
            int ch;
            while ((ch = inputStream.read()) != -1) {
                String SYMBOL = String.valueOf((char) ch);
                
                if (STRING.equals("")) {
                    STRING = SYMBOL;
                    continue;
                }

                String combined = STRING + SYMBOL;
                int tableIndex = findIndex(combined, TABLE, openSpot);
                
                if (tableIndex != -1) {
                    STRING = combined;
                } else {
                    code.add(findIndex(STRING, TABLE, openSpot));
                    
                    if (openSpot < max_size) {
                        TABLE[openSpot] = combined;
                        openSpot++;
                    }
                    STRING = SYMBOL;
                }
            }
            if (!STRING.equals("")) {
                code.add(findIndex(STRING, TABLE, openSpot));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;

    }

    public static int findIndex(String s, String[] table, int openSpot) {
        for (int i = 0; i < openSpot; i++) {
            if (table[i] != null && table[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public static void outputFile(String inputFileName, ArrayList<Integer> codes) {
        String outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".lzw";

        try (OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream(outputFileName), StandardCharsets.UTF_16BE)) {
            
            for (int codeValue : codes) {
                osw.write((char) codeValue);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decodeFile(String lzwFileName, short max_size) {
        String[] TABLE = new String[max_size];
        for (int i = 0; i <= 255; i++) {
            TABLE[i] = String.valueOf((char) i);
        }
        int openSpot = 256;

        String outName = lzwFileName.substring(0, lzwFileName.lastIndexOf('.')) + "_decoded.txt";

        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(lzwFileName), StandardCharsets.UTF_16BE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outName))) {
            
            int code = isr.read();
            if (code == -1) return;

            String string = TABLE[code];
            writer.write(string);
            int prevCode = code;

            while ((code = isr.read()) != -1) {
                String entry;
                if (code < openSpot) {
                    entry = TABLE[code];
                } else {
                    entry = TABLE[prevCode] + TABLE[prevCode].charAt(0);
                }

                writer.write(entry);

                if (openSpot < max_size) {
                    TABLE[openSpot] = TABLE[prevCode] + entry.charAt(0);
                    openSpot++;
                }
                prevCode = code;
            }
            System.out.println("Decoded file saved as: " + outName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}