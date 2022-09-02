
import java.io.*;
import java.util.*;

public class MainOrig {
    static boolean isR(int i){return i == 1 || i == 2 || i == 3 || i == 7 || i == 9;}

    static boolean isIorS(int i) {return i == 0 || i == 4 || i == 5  || i == 10 || i == 11 || i == 12 || i == 13 || i == 14;}

    static String getBit(String s){
        return s.substring(s.length() - 1);
    }

    static String getBin(int n){
        String s = Integer.toBinaryString(n);
        int len = s.length();
        if(len == 1) s = "000"+s;
        if(len == 2) s = "00"+s;
        if(len == 3) s = "0"+s;
        return s;
    }

    static List<String> opcodes = List.of("beq", "add", "nor", "or", "addi", "sll", "sw", "sub", "j", "and", "srl", "ori", "bneq", "subi", "andi","lw");
    static List<String> registers = List.of("$zero", "$t0", "$t1", "$t2", "$t3", "$t4", "$sp");

    public static void assemblyToHex(String fileName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
//        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("output2.txt"));
        writer.write("v2.0 raw\n");
        String instruction;
        String ops;
        String[] operands;
        StringBuilder binIns;
        int opcode;


        while(true){
            binIns = new StringBuilder();
            instruction = reader.readLine();
            System.out.println(instruction);
//            writer.write(instruction);
            if(instruction == null) break;
            ops = instruction.split(" ")[0];
            instruction = instruction.replace(ops, "").replace(" ", "");
            opcode = opcodes.indexOf(ops);
            if(isR(opcode)){
                binIns.append((Integer.toHexString(opcode)));
                operands = instruction.split(",");
                binIns.append(getBit(Integer.toHexString(registers.indexOf(operands[1]))));
                binIns.append(getBit(Integer.toHexString(registers.indexOf(operands[2]))));
                binIns.append(getBit(Integer.toHexString(registers.indexOf(operands[0]))));
            }
            else if(isIorS(opcode)){
                binIns.append((Integer.toHexString(opcode)));
                operands = instruction.split(",");
                binIns.append(getBit(Integer.toHexString(registers.indexOf(operands[1]))));
                binIns.append(getBit(Integer.toHexString(registers.indexOf(operands[0]))));
                binIns.append(getBit(Integer.toHexString(Integer.parseInt(operands[2]))));
            }
            else if(opcode == 6 || opcode == 15){
                binIns.append((Integer.toHexString(opcode)));
                operands = instruction.split(",");
                String src = operands[1].substring(operands[1].indexOf("(") + 1 ,operands[1].indexOf(")"));
                String offset = operands[1].substring(0 ,operands[1].indexOf("("));
                binIns.append(getBit(Integer.toHexString(registers.indexOf(src))));
                binIns.append(getBit(Integer.toHexString(registers.indexOf(operands[0]))));
                binIns.append(getBit(Integer.toHexString(Integer.parseInt(offset))));
            }
            else{
                binIns.append((Integer.toHexString(opcode)));
                String addr = Integer.toHexString(Integer.parseInt(instruction));
                if(addr.length() == 1){
                    addr = "0" + addr;
                }
                binIns.append(addr);
                binIns.append("0");
            }
            writer2.write("0x"+binIns + ", ");
            writer.write(binIns + " ");
        }
        writer2.flush();
        writer2.close();
        writer.flush();
        writer.close();
        reader.close();
    }
}
