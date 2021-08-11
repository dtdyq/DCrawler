/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.docresolve;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-27 19:07
 **/
public class DocumentWritable implements WritableComparable<DocumentWritable> {
    private byte[] data;
    private int len;
    private String contentType;

    public DocumentWritable(){}
    public DocumentWritable(byte[] data, String contentType){
        this.data = data;
        this.len = data.length;
        this.contentType = contentType;
    }
    public DocumentWritable data(byte[] data){
        this.data = data;
        this.len = data.length;
        return this;
    }

    public DocumentWritable contentType(String contentType){
        this.contentType = contentType;
        return this;
    }
    @Override
    public int compareTo(DocumentWritable o) {
        return this.contentType.compareTo(o.contentType);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.write(data,0,len);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        dataInput.readFully(data,0,len);
    }

    @Override
    public String toString() {
        return getUTF8();
    }

    public String getUTF8(){
        return String.valueOf(Charset.forName("utf-8").decode(ByteBuffer.wrap(data)));
    }
    public byte[] getBytes(){
        return data;
    }

    public String getContentType() {
        return contentType;
    }
}
