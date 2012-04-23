package org.ansj.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ansj.util.IOUtil;

public class MakeArray {
	// 终极目标
	public static int base[] = new int[1000000];
	// 验证数组
	public static int check[] = new int[1000000];
	// 词语状态
	public static int status[] = new int[1000000];
	// 当前数组的词
	public static String words[] = new String[1000000];
	// nature
	public static Natures natures[] = new Natures[1000000];
	// weight
	public static String weights[] = new String[1000000];

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		makeBaseArray();
		System.out.println(System.currentTimeMillis() - start);
	}

	/**
	 * path 排序好词典的路径 haspath 字符编码字典的路径 arrayPath 生成字典的路径 charEncoding
	 * 关于字典和生成词典的字符编码设置
	 */
	private static String path = "library/sortLibrary.dic";
	private static String charEncoding = "UTF-8";

	/**
	 * 字典的构建双数组tire树
	 * 
	 * @throws Exception
	 */

	private static int previous;
	private static Map<String, String> tempStringMap = new HashMap<String, String>();

	public static void makeBaseArray() throws Exception {
		//加载数字词典
		BufferedReader reader = IOUtil.getReader(
				InitDictionary.arraysNumberPath, charEncoding);
		makeASCIIArray(reader);
		reader.close() ;
		
		//加载英语词典
		reader = IOUtil.getReader(InitDictionary.arraysEnglishPath,
				charEncoding);
		makeASCIIArray(reader);
		reader = IOUtil.getReader(path, charEncoding);
		reader.close() ;
		
		
		//加载主词典
		reader = IOUtil.getReader(path, charEncoding);
		makeBaseArray(reader);
		writeLibrary();
		reader.close() ;
	}

	/**
	 * 数组的生成
	 */
	public static void makeBaseArray(BufferedReader reader) throws Exception {
		char[] chars = null;
		int length = 0;
		String tempValue;
		String tempValueResult;
		String[] strs = null;
		int tempBase = 0;
		String temp = null;
		while ((tempValue = reader.readLine()) != null) {
			strs = tempValue.split("	");
			temp = strs[0];
			chars = temp.toCharArray();
			length = chars.length;
			if (length == 1) {
				base[chars[0]] = 40863;
				check[chars[0]] = -1;
				status[chars[0]] = Integer.parseInt(strs[1]);
				words[chars[0]] = temp;
				if(natures[chars[0]]==null){
					natures[chars[0]] = new Natures();
				}
				natures[chars[0]].add(strs[2], Integer.parseInt(strs[3])) ;
			} else {
				int previousCheck = getBaseNum(chars);
				if (previous == previousCheck) {
					tempStringMap.put(temp, tempValue);
					continue;
				}
				if (tempStringMap.size() > 0) {
					setBaseValue(tempStringMap, previous);
					it = tempStringMap.values().iterator();
					while (it.hasNext()) {
						tempValueResult = it.next();
						strs = tempValueResult.split("	");
						chars = strs[0].toCharArray();
						tempBase = base[previous] + chars[chars.length - 1];
						base[tempBase] = tempBase;
						check[tempBase] = previous;
						status[tempBase] = Integer.parseInt(strs[1]);
						words[tempBase] = strs[0];
						if(natures[chars[0]]==null){
							natures[chars[0]] = new Natures();
						}
						natures[chars[0]].add(strs[2], Integer.parseInt(strs[3])) ;
					}
				}
				previous = previousCheck;
				tempStringMap = new HashMap<String, String>();
				tempStringMap.put(temp, tempValue);

			}
		}
	}
	private static final Natures EN =  new Natures("en",1) ;
	private static final Natures NB =  new Natures("nb",1) ;
	private static final Natures PO =  new Natures("po",1) ;

	public static void makeASCIIArray(BufferedReader reader) throws IOException {
		String temp = null;
		String[] strs = null;
		int baseValue = 0;
		int sta = 0;
		while ((temp = reader.readLine()) != null) {
			strs = temp.split("\t");
			sta = Integer.parseInt(strs[1]);
			baseValue = strs[0].charAt(0);
			base[baseValue] = baseValue;
			check[baseValue] = -1;
			status[baseValue] = sta;
			words[baseValue] = strs[0];
			switch (sta) {
			case 4:
				natures[baseValue] = EN;
				break;
			case 5:
				natures[baseValue] = NB;
				break;
			case -1:
				natures[baseValue] = PO;
				break;
			}
		}
	}

	/**
	 * 将生成的数组写成词典文件
	 */
	public static void writeLibrary() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < base.length; i++) {
			if (base[i] > 0) {
				sb
						.append(i + "	" + words[i] + "	" + base[i] + "	"
								+ check[i] + "	" + status[i] + "	" + natures[i]
								+ "	" + weights[i]);
				sb.append("\n");
			}
		}
		IOUtil.Writer(InitDictionary.arraysPath, charEncoding, sb.toString());
	}

	/**
	 * 设置base数组中的父值,使得以父开头的档次都能放到数组中
	 * 
	 * @param tempString
	 *            以父开头单词的全部集合
	 */
	private static Iterator<String> it;
	private static char[] chars = null;;

	public static void setBaseValue(Map<String, String> tempStringMap,
			int tempBase) {
		it = tempStringMap.keySet().iterator();
		while (it.hasNext()) {
			chars = it.next().toCharArray();
			if (!isHave(base[tempBase] + chars[chars.length - 1])) {
				base[tempBase]++;
				it = tempStringMap.keySet().iterator();
			}
		}
	}

	/**
	 * 找到该字符串上一个的位置字符串上一个的位置
	 * 
	 * @param chars
	 *            传入的字符串char数组
	 * @return
	 */
	public static int getBaseNum(char[] chars) {
		int tempBase = 0;
		if (chars.length == 2) {
			return chars[0];
		}
		for (int i = 0; i < chars.length - 1; i++) {
			if (i == 0) {
				tempBase += chars[i];
			} else {
				tempBase = base[tempBase] + chars[i];
			}
		}
		return tempBase;
	}

	/**
	 * 判断在base数组中这个位置是否有这个对象昂
	 * 
	 * @param num
	 *            base数组中的位置
	 * @return
	 */
	public static boolean isHave(int num) {
		if (base[num] > 0) {
			return false;
		} else {
			return true;
		}
	}
}
