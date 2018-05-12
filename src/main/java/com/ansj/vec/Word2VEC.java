package com.ansj.vec;

import com.ansj.vec.domain.WordEntry;

import com.ansj.vec.util.FileUtil;
import com.ansj.vec.util.MergeFile;
import com.jcseg.extractor.impl.TextRankKeywordsExtractor;
import com.jcseg.tokenizer.core.*;
import com.zhoulin.demo.service.JcsegService;
import com.zhoulin.demo.utils.TokenizerAnalyzerUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import static com.ansj.vec.constants.Constants.ENCODING;

@Component
public class Word2VEC {

	@Autowired
	private JcsegService jcsegService;

	public static void dataProcess() {
//		File commentFile = new File(Word2VEC.class.getResource("/library/word2vec.txt").getPath());
		//010806 020806 030806

		JcsegTaskConfig config = new JcsegTaskConfig(true);
		config.setClearStopwords(true);
		config.setAppendCJKSyn(false);
		config.setKeepUnregWords(false);
		ADictionary dic = DictionaryFactory.createSingletonDictionary(config);

		File commentFile = new File(Word2VEC.class.getResource("/library/mod/aa.txt").getPath());

		long start = System.currentTimeMillis();
		try {

			ISegment seg = SegmentFactory
					.createJcseg(JcsegTaskConfig.NLP_MODE, new Object[]{config, dic});

			TextRankKeywordsExtractor extractor = new TextRankKeywordsExtractor(seg);
			extractor.setMaxIterateNum(100);
			extractor.setWindowSize(1);
			extractor.setKeywordsNum(50);
			List<String> keywords;

			StringBuilder vectorSB = new StringBuilder();

			List<String> lineList = FileUtils.readLines(commentFile, ENCODING);
			for (String line : lineList) {

				vectorSB.append(extractor.getKeywordsFromString(line.trim()));
//				vectorSB.append(ModTokenizerAnalyzerUtil.getAnalyzerResult(line.trim()) + "\r\n");
				System.out.println("Parsing comment: " + line);
			}

			File file = new File("D:\\Java\\generator\\gxtj\\src\\main\\resources\\library\\33tokenR.txt");
			List<StringBuilder> list = new ArrayList<StringBuilder>();
			list.add(vectorSB);
			FileUtils.writeLines(file, list);
			System.out.println("tokenizer analyze done!!!");
			System.out.println("process last: " + (System.currentTimeMillis() - start)/1000 + "s");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*文本转换*/
	public static void txtConvert() {
		File f = new File("E:\\sougoudata");
		for(File temp : f.listFiles()) {
			if(temp.isFile()) {
				FileUtil.saveTxtFile(FileUtil.Html2Txt(FileUtil.readTxtFile("E:\\sougoudata\\"+temp.getName())),temp.getName());
			}
		}
		txtMerge();
	}
	public static void txtMerge() {
		File f = new File("E:\\sougoudata\\convert");
		int index = 0;
		int length = f.listFiles().length;
		String[] files=new String[length];
		for(File temp : f.listFiles()) {
			if(temp.isFile()) {
				System.out.println(temp.getName());
				files[index] = "E:\\sougoudata\\convert\\"+temp.getName();
				index++;
			}
		}
		String outfile="E:\\sougoudata\\convert\\merged.txt";
		MergeFile.merge(outfile,files);
	}
	public static void main(String[] args) throws IOException {
		//preprocess the original comment to tokenizer and save as tokenizerResult.txt
//		dataProcess();
//		txtMerge();

		//train the model and save model
//		Learn learn = new Learn();
//		learn.learnFile(new File("D:\\Java\\generator\\gxtj\\src\\main\\resources\\library\\33tokenR.txt"));
//		learn.saveModel(new File("D:\\Java\\generator\\gxtj\\src\\main\\resources\\library\\vector21252.mod"));
//
//		//use the trained model to analyze

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("C:\\Users\\84972\\Desktop\\gxtj\\src\\main\\resources\\library\\Google_word2vec_zhwiki1710_300d.bin");

		//
//		System.out.println("法律" + "\t" +
//		Arrays.toString(vec.getWordVector("法律")));
		String str = "财经";
//		for (int i = 0; i < 20; i++) {
//			System.out.println(vec.distance(str));

//		}
//
		List<String> wordList = new ArrayList<String>();
		//娱乐 1 两会 2 体育 3 财经 4 科技 5 汽车 6 军事 7 旅游 8 生活 9 其他 10
		wordList.add("法律");
		wordList.add("两会");
		wordList.add("体育");
		wordList.add("财经");
		wordList.add("科技");
		wordList.add("汽车");
		wordList.add("互联网");
		wordList.add("旅游");
		wordList.add("国家");
		for (String word : wordList) {
			System.out.println(word + "\t" +
					vec.distance(word));
		}

//		System.out.println(vec.analogy("证据", "离婚", "涉及"));
	}

	private HashMap<String, float[]> wordMap = new HashMap<String, float[]>();

	private int words;
	private int size;
	private int topNSize = 40;

	/**
	 * 加载模型
	 * 
	 * @param path
	 *            模型的路径
	 * @throws IOException
	 */
	public void loadGoogleModel(String path) throws IOException {
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		double len = 0;
		float vector = 0;
		try {
			bis = new BufferedInputStream(new FileInputStream(path));
			dis = new DataInputStream(bis);
			// //读取词数
			words = Integer.parseInt(readString(dis));
			// //大小
			size = Integer.parseInt(readString(dis));
			String word;
			float[] vectors = null;
			for (int i = 0; i < words; i++) {
				word = readString(dis);
				vectors = new float[size];
				len = 0;
				for (int j = 0; j < size; j++) {
					vector = readFloat(dis);
					len += vector * vector;
					vectors[j] = (float) vector;
				}
				len = Math.sqrt(len);

				for (int j = 0; j < size; j++) {
					vectors[j] /= len;
				}

				wordMap.put(word, vectors);
				dis.read();
			}
		} finally {
			bis.close();
			dis.close();
		}
	}

	/**
	 * 加载模型
	 *
	 * @param path
	 *            模型的路径
	 * @throws IOException
	 */
	public void loadJavaModel(String path) throws IOException {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
		try {
			words = dis.readInt();
			size = dis.readInt();

			float vector = 0;

			String key = null;
			float[] value = null;
			for (int i = 0; i < words; i++) {
				double len = 0;
				key = dis.readUTF();
				value = new float[size];
				for (int j = 0; j < size; j++) {
					vector = dis.readFloat();
					len += vector * vector;
					value[j] = vector;
				}

				len = Math.sqrt(len);

				for (int j = 0; j < size; j++) {
					value[j] /= len;
				}
				wordMap.put(key, value);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final int MAX_SIZE = 50;

	/**
	 * 近义词
	 *
	 * @return
	 */
	public TreeSet<WordEntry> analogy(String word0, String word1, String word2) {
		float[] wv0 = getWordVector(word0);
		float[] wv1 = getWordVector(word1);
		float[] wv2 = getWordVector(word2);

		if (wv1 == null || wv2 == null || wv0 == null) {
			return null;
		}
		float[] wordVector = new float[size];
		for (int i = 0; i < size; i++) {
			wordVector[i] = wv1[i] - wv0[i] + wv2[i];
		}
		float[] tempVector;
		String name;
		List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
		for (Entry<String, float[]> entry : wordMap.entrySet()) {
			name = entry.getKey();
			if (name.equals(word0) || name.equals(word1) || name.equals(word2)) {
				continue;
			}
			float dist = 0;
			tempVector = entry.getValue();
			for (int i = 0; i < wordVector.length; i++) {
				dist += wordVector[i] * tempVector[i];
			}
			insertTopN(name, dist, wordEntrys);
		}
		return new TreeSet<WordEntry>(wordEntrys);
	}

	private void insertTopN(String name, float score, List<WordEntry> wordsEntrys) {
		// TODO Auto-generated method stub
		if (wordsEntrys.size() < topNSize) {
			wordsEntrys.add(new WordEntry(name, score));
			return;
		}
		float min = Float.MAX_VALUE;
		int minOffe = 0;
		for (int i = 0; i < topNSize; i++) {
			WordEntry wordEntry = wordsEntrys.get(i);
			if (min > wordEntry.score) {
				min = wordEntry.score;
				minOffe = i;
			}
		}

		if (score > min) {
			wordsEntrys.set(minOffe, new WordEntry(name, score));
		}

	}

	public List<String> distance(String queryWord) {

		List<String> kws = new ArrayList<>();

		float[] center = wordMap.get(queryWord);
		if (center == null) {
//			return Collections.emptySet();
			return null;
		}

		int resultSize = wordMap.size() < topNSize ? wordMap.size() : topNSize;
		TreeSet<WordEntry> result = new TreeSet<WordEntry>();

		double min = Float.MIN_VALUE;
		for (Entry<String, float[]> entry : wordMap.entrySet()) {
			float[] vector = entry.getValue();
			float dist = 0;
			for (int i = 0; i < vector.length; i++) {
				dist += center[i] * vector[i];
			}

			if (dist > min) {
				result.add(new WordEntry(entry.getKey(), dist));
				if (resultSize < result.size()) {
					result.pollLast();
				}
				kws.add(entry.getKey());
				min = result.last().score;
			}
		}
		result.pollFirst();

		return kws;
	}

	public Set<WordEntry> distance(List<String> words) {

		float[] center = null;
		for (String word : words) {
			center = sum(center, wordMap.get(word));
		}

		if (center == null) {
			return Collections.emptySet();
		}

		int resultSize = wordMap.size() < topNSize ? wordMap.size() : topNSize;
		TreeSet<WordEntry> result = new TreeSet<WordEntry>();

		double min = Float.MIN_VALUE;
		for (Entry<String, float[]> entry : wordMap.entrySet()) {
			float[] vector = entry.getValue();
			float dist = 0;
			for (int i = 0; i < vector.length; i++) {
				dist += center[i] * vector[i];
			}

			if (dist > min) {
				result.add(new WordEntry(entry.getKey(), dist));
				if (resultSize < result.size()) {
					result.pollLast();
				}
				min = result.last().score;
			}
		}
		result.pollFirst();

		return result;
	}

	private float[] sum(float[] center, float[] fs) {
		// TODO Auto-generated method stub

		if (center == null && fs == null) {
			return null;
		}

		if (fs == null) {
			return center;
		}

		if (center == null) {
			return fs;
		}

		for (int i = 0; i < fs.length; i++) {
			center[i] += fs[i];
		}

		return center;
	}

	/**
	 * 得到词向量
	 *
	 * @param word
	 * @return
	 */
	public float[] getWordVector(String word) {
		return wordMap.get(word);
	}

	public static float readFloat(InputStream is) throws IOException {
		byte[] bytes = new byte[4];
		is.read(bytes);
		return getFloat(bytes);
	}

	/**
	 * 读取一个float
	 *
	 * @param b
	 * @return
	 */
	public static float getFloat(byte[] b) {
		int accum = 0;
		accum = accum | (b[0] & 0xff) << 0;
		accum = accum | (b[1] & 0xff) << 8;
		accum = accum | (b[2] & 0xff) << 16;
		accum = accum | (b[3] & 0xff) << 24;
		return Float.intBitsToFloat(accum);
	}

	/**
	 * 读取一个字符串
	 *
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	private static String readString(DataInputStream dis) throws IOException {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[MAX_SIZE];
		byte b = dis.readByte();
		int i = -1;
		StringBuilder sb = new StringBuilder();
		while (b != 32 && b != 10) {
			i++;
			bytes[i] = b;
			b = dis.readByte();
			if (i == 49) {
				sb.append(new String(bytes));
				i = -1;
				bytes = new byte[MAX_SIZE];
			}
		}
		sb.append(new String(bytes, 0, i + 1));
		return sb.toString();
	}

	public int getTopNSize() {
		return topNSize;
	}

	public void setTopNSize(int topNSize) {
		this.topNSize = topNSize;
	}

	public HashMap<String, float[]> getWordMap() {
		return wordMap;
	}

	public int getWords() {
		return words;
	}

	public int getSize() {
		return size;
	}

}
