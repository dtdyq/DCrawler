/*
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author dtdyq
 * @create 2018-04-16 20:49
 **/
public class Transfer {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\CODE\\src");
        String newPath = new String("E:\\src");
        if(!Files.exists(Paths.get(newPath)))
            Files.createDirectory(Paths.get(newPath));
        for(File f:file.listFiles()){
            String dir = newPath+"/"+f.getName();
            if(!Files.exists(Paths.get(dir))){
                Files.createDirectory(Paths.get(dir));
            }
            for(File tmp:f.listFiles()){
                String s = EncodingDetect.getJavaEncode(tmp.getPath());
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(tmp),s)
                );
                String newName = dir+"/"+tmp.getName();
                File fil = new File(newName);
                fil.createNewFile();
                BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(newName),"utf-8")
                );

                String ss = null;
                while((ss = br.readLine())!=null){
                    out.write(ss);
                    out.newLine();
                    out.flush();
                }
            }
        }
    }
}
