package com.ansj.vec.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.*;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 11:39 2018/3/16
 */
public class MergeFile {
    public static final int BUFSIZE = 1024 * 8;

    public static void merge(String outFile, String[] files) {

        FileChannel outChannel = null;

        System.out.println("Merge " + Arrays.toString(files) + " into " + outFile);

        try {

            outChannel = new FileOutputStream(outFile).getChannel();

            for(String f : files){

                Charset charset=Charset.forName("GBK");

                CharsetDecoder chdecoder=charset.newDecoder();

                CharsetEncoder chencoder=charset.newEncoder();

                FileChannel fc = new FileInputStream(f).getChannel();

                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);

                CharBuffer charBuffer=chdecoder.decode(bb);

                ByteBuffer nbuBuffer=chencoder.encode(charBuffer);

                while(fc.read(nbuBuffer) != -1){



                    bb.flip();

                    nbuBuffer.flip();

                    outChannel.write(nbuBuffer);

                    bb.clear();

                    nbuBuffer.clear();

                }

                fc.close();

            }

            System.out.println("Merged!! ");

        } catch (IOException ioe) {

            ioe.printStackTrace();

        } finally {

            try {if (outChannel != null) {outChannel.close();}} catch (IOException ignore) {}

        }

    }
}
