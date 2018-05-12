package com.ansj.vec.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Vector {

    private HashMap<String, float[]> wordMap = new HashMap<String, float[]>();

    public void loadVectorFile(String path) throws IOException {
        BufferedReader br = null;
        double len = 0;
        float vector = 0;
        int size=0;
        try {
            File f = new File(path);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String word;
            String line="";
            String[] outline=new String[210];
            float[] vectors = null;
            while((line=br.readLine())!=null){
                outline=line.split(",");
                size=outline.length-1;
                word = outline[0];
                vectors = new float[size];
                len = 0;
                for (int j = 0; j < size; j++) {
                    vector = Float.parseFloat(outline[j+1]);
                    len += vector * vector;
                    vectors[j] = (float) vector;
                }
                len = Math.sqrt(len);
                for (int j = 0; j < size; j++) {
                    vectors[j] /= len;
                }
                wordMap.put(word, vectors);
            }
        }
        finally {
            System.out.println("total word: "+wordMap.size()+" vector dimensions: "+size);
            br.close();
        }
    }

    public HashMap<String, float[]> getWordMap() {
        return wordMap;
    }
}

