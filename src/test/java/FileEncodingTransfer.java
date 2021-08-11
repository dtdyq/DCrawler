/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
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
 * @create 2018-04-16 22:18
 **/
public class FileEncodingTransfer {
    public static void transfer(String src,String dest) throws IOException {

        if(!Files.exists(Paths.get(dest))){
            Files.createDirectory(Paths.get(dest));
        }
        File file = new File(src);
        if(file.isFile()){
            String code = EncodingDetect.getJavaEncode(file.getPath());
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file),code)
            );
            String newName = dest+"/"+file.getName();
            File fil = new File(newName);
            fil.createNewFile();
            new File("");
            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(newName),"utf-8")
            );

            String ss = null;
            while((ss = br.readLine())!=null){
                out.write(ss);
                out.newLine();
                out.flush();
            }
        }else{
            for(File f:file.listFiles()){
                transfer(f.getPath(),dest+"/"+new File(src).getName());
            }
        }
    }
    public static void main(String[] args) throws IOException {
        transfer("E:\\CODE\\javac\\javacode\\src\\main\\java","E:");
    }
}
