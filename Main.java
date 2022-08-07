import java.io.*;
import java.util.ArrayList;

public class Main{
    static String getHex(String s){
        int x = Integer.parseInt(s, 2);
        String hex = Integer.toHexString(x);
        System.out.println(hex);
        return hex;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("mips.csv"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("outHex.txt"));
        ArrayList<String> lines = new ArrayList<>();
        System.out.println(br.readLine());
        while(true){
            String s = br.readLine();
            if(s != null){
                lines.add(s);
            }else break;
        }
        lines.forEach(it -> {
            System.out.println(it);
            String[] ss = it.split(",");
            StringBuilder ans = new StringBuilder();
            for(int i=4; i<16; i+=4){
                String s = ss[i]+ss[i+1]+ss[i+2]+ss[i+3];
                System.out.println(s);
                ans.append(getHex(s));
            }
            System.out.println(ans);
            try {
                bw.write(ans+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        br.close();
        bw.flush();
        bw.close();
    }
}