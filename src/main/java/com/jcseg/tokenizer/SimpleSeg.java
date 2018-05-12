package com.jcseg.tokenizer;

import com.jcseg.tokenizer.core.ADictionary;
import com.jcseg.tokenizer.core.IChunk;
import com.jcseg.tokenizer.core.IWord;
import com.jcseg.tokenizer.core.JcsegTaskConfig;

import java.io.IOException;
import java.io.Reader;


/**
 * Jcseg simple segmentation implements extend from ASegment
 * 
 * @author chenxin<chenxin619315@gmail.com>
*/
public class SimpleSeg extends ASegment
{
    
    public SimpleSeg(JcsegTaskConfig config, ADictionary dic ) throws IOException
    {
        super(config, dic);
    }
    
    public SimpleSeg( Reader input, JcsegTaskConfig config, ADictionary dic ) throws IOException 
    {
        super(input, config, dic);
    }

    /**
     * @see ASegment#getBestCJKChunk(char[], int) 
     */
    @Override
    public IChunk getBestCJKChunk(char[] chars, int index) throws IOException
    {
        IWord[] words = getNextMatch(chars, index);
        return new Chunk(new IWord[]{words[words.length - 1]});
    }
    
}
