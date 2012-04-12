package org.ansj.library;

import java.io.BufferedReader;

import org.ansj.util.IOUtil;
import org.ansj.util.MyStaticValue;

public class InitDictionary {
	/**
	 * arrayLength : 记录了数组的大小
	 */
	private static final int ARRAYLENGTH = 562940;
	/**
	 * base: 数组用来存放单词的转换..其实就是一个DFA转换过程
	 */
	public static int[] base = new int[ARRAYLENGTH];
	/**
	 * check: 数组用来验证里面存储的是上一个状态的位置
	 */
	public static int[] check = new int[ARRAYLENGTH];
	/**
	 * status: 用来判断一个单词的状态 1.为不成词.处于过度阶段 2.成次也可能是词语的一部分. 3.词语结束 example: 中 1 中华
	 * 2 中华人 1 中华人民 3
	 */
	public static byte[] status = new byte[ARRAYLENGTH];
	/**
	 * words : 数组所在位置的词
	 */
	public static String[] words = new String[ARRAYLENGTH];
	/**
	 * weights : 权重
	 */
	public static int[] weights = new int[ARRAYLENGTH];
	/**
	 * nature : 词性
	 */
	public static String[] natures = new String[ARRAYLENGTH];
	/**
	 * arraysPath: base编码的硬盘位置
	 */
	public static String arraysPath = "library/arrays.dic";
	/**
	 * arraysPath: base编码的硬盘位置
	 */
	public static String arraysNumberPath = MyStaticValue.rb.getString("number");
	/**
	 * arraysPath: base编码的硬盘位置
	 */
	public static String arraysEnglishPath = MyStaticValue.rb.getString("english");
	
	/**
	 * arraysStopPath: 标点符号截断词词典
	 */
	public static String arraysStopPath = MyStaticValue.rb.getString("stop");
	/**
	 * 两本词典的字符编码
	 */
	private static String charEncoding = "UTF-8";

	/**
	 * 判断词典是否加载过
	 */
	private static boolean isInit = false;
	

	public static void init() {
		if (!isInit) {
			long start = System.currentTimeMillis();
			try {
				initArrays();
				natures[0] = "N" ;
				isInit = true ;
				System.out.println("词典加载完成用时:"
						+ (System.currentTimeMillis() - start) + "毫秒");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print("词典加载出错");
			}
		}
	}

	/**
	 * 对于base,check,natrue,status的加载
	 * 0.代表这个字不在词典中 1.继续 2.是个词但是还可以继续 3.停止已经是个词了
	 * 
	 * @throws Exception
	 */
	public static void initArrays() throws Exception {
		BufferedReader reader = IOUtil.getReader(arraysPath, charEncoding);
		initArrays(reader) ;
	}
	
	public static void initArrays(BufferedReader reader) throws Exception{
		String temp = null;
		String[] strs = null;
		int num = 0;
		while ((temp = reader.readLine()) != null) {
			strs = temp.split("	");
			num = Integer.parseInt(strs[0]);
			words[num] = strs[1] ;
			base[num] = Integer.parseInt(strs[2]);
			check[num] = Integer.parseInt(strs[3]);
			status[num] = Byte.parseByte(strs[4]);
			
			if(!"null".equals(strs[5])){
				natures[num] = strs[5] ;
				weights[num] = Integer.parseInt(strs[6]) ;
			}
		}
	}


	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		init();
		System.out.println(System.currentTimeMillis() - start);
	}
}
