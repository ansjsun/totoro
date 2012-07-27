package org.ansj.library;

import java.io.BufferedReader;
import java.io.IOException;

import org.ansj.domain.Natures;
import org.ansj.util.IOUtil;
import org.ansj.util.MyStaticValue;
import org.ansj.util.StringUtil;

public class InitDictionary {
	/**
	 * base: 数组用来存放单词的转换..其实就是一个DFA转换过程
	 */
	public static int[] base = null ;
	/**
	 * check: 数组用来验证里面存储的是上一个状态的位置
	 */
	public static int[] check = null ;
	/**
	 * status: 用来判断一个单词的状态 1.为不成词.处于过度阶段 2.成次也可能是词语的一部分. 3.词语结束 example: 中 1 中华
	 * 2 中华人 1 中华人民 3
	 */
	public static byte[] status = null ;
	/**
	 * words : 数组所在位置的词
	 */
	public static String[] words = null ;
	/**
	 * weights : 词性词典,以及词性的相关权重
	 */
	public static Natures[] natures = null ;
	
	/**
	 * arraysPath: base编码的硬盘位置
	 */
	public static String arraysPath = MyStaticValue.rb.getString("arrays");
	
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
		initArraySize(reader) ;
		reader.close() ;
		reader = IOUtil.getReader(arraysPath, charEncoding);
		initArrays(reader) ;
		reader.close() ;
	}
	
	private static void initArraySize(BufferedReader reader) throws IOException {
		// TODO Auto-generated method stub
		String temp = null ;
		String last = null ;
		while((temp=reader.readLine())!=null){
			last = temp ;
		}
		
		String[] strs = last.split("	") ;
		
		int arrayLength = Integer.parseInt(strs[0]) + 1;
		
		base = new int[arrayLength];

		check = new int[arrayLength];

		status = new byte[arrayLength];

		words = new String[arrayLength];
		
		natures = new Natures[arrayLength] ;
		
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
			//去掉和这个字的名字可能
			if(strs[1].equals("和")){
				strs[5] = strs[5].replace(", nr1=4", "") ;
			}
			if(!"null".equals(strs[5])){
				natures[num] = new Natures(strs[5]) ;
			}else{
				natures[num] = Natures.NULL ;
			}
		}
		reader.close() ;
	}
	
	/**
	 * 判断一个词是否在词典中存在
	 * @param str
	 * @return
	 */
	public static boolean isInSystemDic(String str){
		init() ;
		if(StringUtil.isBlank(str)){
			return true ;
		}
		int baseValue = str.charAt(0) ;
		int checkValue = 0 ;
		for (int i = 1; i < str.length(); i++) {
			checkValue = baseValue;
			baseValue = base[baseValue] + str.charAt(i);
			if (check[baseValue] != -1 && check[baseValue] != checkValue) {
				return false ;
			}
		}
		return status[baseValue]>1;
	}
	
	
	/**
	 * 判断一个词返回词性.如果没有则返回null
	 * @param str
	 * @return
	 */
	public static Natures getWordNature(String str){
		init() ;
		if(StringUtil.isBlank(str)){
			return null ;
		}
		int baseValue = str.charAt(0) ;
		int checkValue = 0 ;
		for (int i = 1; i < str.length(); i++) {
			checkValue = baseValue;
			baseValue = base[baseValue] + str.charAt(i);
			if (check[baseValue] != -1 && check[baseValue] != checkValue) {
				return null ;
			}
		}
		if(status[baseValue]>1){
			return natures[baseValue] ;
		}else{
			return null ;
		}
	}
	
	public static void main(String[] args) {
		init() ;
		StringBuilder sb = new StringBuilder() ;
		for (int i = 0; i < words.length; i++) {
			if(natures[i]!=null){
				if(natures[i].maxNature.toString().contentEquals("v")){
					sb.append(words[i]+"	"+"0.1f\n") ;
				}
				if(natures[i].maxNature.toString().contentEquals("d")){
					sb.append(words[i]+"	"+"0.5f\n") ;
				}
			}
		}
		IOUtil.Writer("/Users/ansj/Documents/快盘/分词/v.txt", "UTF-8", sb.toString()) ;
	}
}
