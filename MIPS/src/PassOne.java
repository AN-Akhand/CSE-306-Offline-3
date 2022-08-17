import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PassOne {

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    public static void intermediateASM(String inputFile) throws IOException {
        String outputFile = "int3.txt";
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        ArrayList<String> lines = new ArrayList<>();
        int lineNo = 0;
        Map<String, Integer> map = new HashMap<>();
        while(true){
            String line = br.readLine();
            if(line == null) break;
            line = line.trim();
            if(line.equals(""))continue;
            if(line.contains(":")){
                map.put(line.replace(":", ""), lineNo);
            }
            else {
                lineNo ++;
                lines.add(line.trim());
            }
        }

        BufferedWriter int1 = new BufferedWriter(new FileWriter("int2.txt"));
        for(int i=0; i<lines.size(); i++){
            int1.write("Line "+i+": "+lines.get(i)+"\n");
        }
        int1.flush();
        int1.close();


        for(int ln=0; ln<lines.size(); ln++){
            String line = lines.get(ln);
            try {
                String[] ss = line.split(" ");
                String command = ss[0];
                if(command.equalsIgnoreCase("j")){
                    ss[1] = ss[1].trim();
                    Integer i = map.get(ss[1]);
                    if(i != null) line = line.replace(ss[1],i.toString());
                    else line = "error";
                }else if(command.equalsIgnoreCase("beq") || command.equalsIgnoreCase("bneq")){
                    String[] sss = line.split(",");
                    String level = sss[sss.length - 1].trim();
                    if(!isInteger(level)) {
                        Integer i = map.get(level);
//                        System.out.println(line);
//                        System.out.println("I: "+i);
//                        System.out.println("LineNo: "+ln);
                        if (i != null) {
                            line = line.replace(level, Integer.toString((i - ln - 1)));
                        } else line = "error";
                    }
                }
                bw.write(line+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        bw.flush();
        bw.close();
        br.close();


        Main.assemblyToHex("int3.txt");
    }
}
