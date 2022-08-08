import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PassOne {
    public static void main(String[] args) throws IOException {
        String inputFile = "inp.txt";
        String outputFile = "out2.txt";
        if(args.length >= 1) inputFile = args[0];
        if(args.length >= 2) outputFile = args[1];
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
        lines.forEach(line -> {
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
                    Integer i = map.get(level);
                    if(i != null) line = line.replace(level, i.toString());
                    else line = "error";
                }
                bw.write(line+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        bw.flush();
        bw.close();
        br.close();
    }
}
