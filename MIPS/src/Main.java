
import java.io.*;
import java.util.*;

public class Main {
    static boolean isR(int i){return i == 1 || i == 2 || i == 3 || i == 7 || i == 9;}

    static boolean isIorS(int i) {return i == 0 || i == 4 || i == 5  || i == 10 || i == 11 || i == 12 || i == 13 || i == 14;}

    static String getBin(int n){
        String s = Integer.toBinaryString(n);
        int len = s.length();
        if(len == 1) s = "000"+s;
        if(len == 2) s = "00"+s;
        if(len == 3) s = "0"+s;
        return s;
    }

    public static void main(String[] args) throws IOException {
        List<String> opcodes = List.of("beq", "add", "nor", "or", "addi", "sll", "sw", "sub", "j", "and", "srl", "ori", "bneq", "subi", "andi","lw");
        List<String> registers = List.of("$zero", "$t0", "$t1", "$t2", "$t3", "$t4");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("text.txt")));
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
        String instruction;
        String ops;
        String[] operands;
        StringBuilder binIns;
        int opcode;


        while(true){
            binIns = new StringBuilder();
            instruction = reader.readLine();
            if(instruction == null) break;
            ops = instruction.split(" ")[0];
            instruction = instruction.replace(ops, "").replace(" ", "");
            opcode = opcodes.indexOf(ops);
            if(isR(opcode)){
                binIns.append(Integer.toHexString(opcode));
                operands = instruction.split(",");
                binIns.append(Integer.toHexString(registers.indexOf(operands[1])));
                binIns.append(Integer.toHexString(registers.indexOf(operands[2])));
                binIns.append(Integer.toHexString(registers.indexOf(operands[0])));
                System.out.println(binIns);
            }
            else if(isIorS(opcode)){
                binIns.append(Integer.toHexString(opcode));
                operands = instruction.split(",");
                binIns.append(Integer.toHexString(registers.indexOf(operands[1])));
                binIns.append(Integer.toHexString(registers.indexOf(operands[0])));
                binIns.append(Integer.toHexString(Integer.parseInt(operands[2])));
                System.out.println(binIns);
            }
            else if(opcode == 6 || opcode == 15){
                binIns.append(Integer.toHexString(opcode));
                operands = instruction.split(",");
                String src = operands[1].substring(operands[1].indexOf("(") + 1 ,operands[1].indexOf(")"));
                String offset = operands[1].substring(0 ,operands[1].indexOf("("));
                binIns.append(Integer.toHexString(registers.indexOf(src)));
                binIns.append(Integer.toHexString(registers.indexOf(operands[0])));
                binIns.append(Integer.toHexString(Integer.parseInt(offset)));
                System.out.println(binIns);
            }
            else{
                binIns.append(Integer.toHexString(opcode));
                String addr = Integer.toHexString(Integer.parseInt(instruction));
                if(addr.length() == 1){
                    addr = "0" + addr;
                }
                binIns.append(addr);
                binIns.append("0");
                System.out.println(binIns);
            }
        }
    }
}
