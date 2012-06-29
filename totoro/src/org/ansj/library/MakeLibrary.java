package org.ansj.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.ansj.util.IOUtil;
import org.ansj.util.MyStaticValue;

public class MakeLibrary {
	private static String charEncoding = "UTF-8";
	private static String path = MyStaticValue.rb.getString("library");

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		// englishLibrary() ;
		makeLibrary();
		System.out.println(System.currentTimeMillis() - start);
	}

	/**
	 * 构造简单的英语词典 status:4
	 * 
	 * @throws Exception
	 */
//	 public static void englishLibrary() throws Exception{
//	 String[] stringArray = MyStaticValue.ENGLISH.split("") ;
//	 String str = natureLibrary(stringArray,4) ;
//	 IOUtil.Writer(InitDictionary.arraysEnglishPath, "UTF-8", str);
	// }
	/**
	 * 构造简单的数字词典 status:5
	 * 
	 * @throws Exception
	 */
	// public static void numberLibrary() throws Exception{
	// String[] stringArray = MyStaticValue.NUMBER.split("") ;
	// String str = natureLibrary(stringArray,5) ;
	// IOUtil.Writer(InitDictionary.arraysNumberPath, "UTF-8", str);
	// }
	/**
	 * 构造简单的具有通用词性的词典,比如数字.英语...
	 * 
	 * @throws Exception
	 */
	// public static String natureLibrary(String[] StringArray , int nature) {
	// StringBuilder sb = new StringBuilder() ;
	// for (int i = 0; i < StringArray.length; i++) {
	// if(StringArray[i]!=null&&StringArray[i].trim().length()>0){
	// sb.append(StringArray[i] + "	" + nature) ;
	// sb.append('\n') ;
	// }
	// }
	// return sb.toString() ;
	// }
	/**
	 * 重构词典.根据结构体的规则将base数组模型构建出来 base模型的规则首先按顺序排列.其次按hashCode排列
	 * 
	 * @throws IOException
	 */

	public static void makeLibrary() throws Exception {
		BufferedReader reader = IOUtil.getReader(path, charEncoding);
		sortLibrary(reader);

	}

	public static void sortLibrary(BufferedReader reader) throws Exception {
		String line;
		LibraryToTree ltr = new LibraryToTree();
		HashSet<String> keyWords = new HashSet<String>();
		while ((line = reader.readLine()) != null) {
			if (line.contains("#")) {
				continue;
			}
			System.out.println(line);
			keyWords.add(line);
		}
		ltr.addLibrary(keyWords);

		//在内存中生成Branch词典
		List<Branch> head = new ArrayList<Branch>();
		head.add(ltr.head);
		List<Branch> all = treeToLibrary(head, 0, 1);
		//将head移除
		all.remove(0) ;
		MakeArray.makeArray(all) ;
	}


	private static List<Branch> treeToLibrary(List<Branch> all, int begin, int end) {
		int beginNext = end;
		for (int i = begin; i < end; i++) {
			Branch[] branches = all.get(i).branches;
			for (int j = 0; j < branches.length; j++) {
				all.add(branches[j]);
			}
		}
		int endNext = all.size();
		if (begin != end) {
			treeToLibrary(all, beginNext, endNext);
		}
		return all;
	}
}
