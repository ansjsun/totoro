package org.ansj.splitWord.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.ansj.domain.Term;
import org.ansj.util.Graph;
import org.ansj.util.IOUtil;

public class Test {
//	public static void main(String[] args) {
//		String str = "他说的确实在理";
//		GetWordsImpl gwi = new GetWordsImpl();
//		String temp = null;
//		int maxColumn = 0;
//		Word word;
//		for (int i = 0; i < 1; i++) {
//			List<Word> all = new ArrayList<Word>();
//			gwi.setStr(str);
//			while ((temp = gwi.allWords()) != null) {
//				maxColumn = gwi.offe + temp.length()-1;
//				System.out.println(temp + "	" + gwi.offe + "	" + maxColumn);
//				word = new Word(gwi.offe, maxColumn, temp);
//				all.add(word);
//			}
//
//			String[][] strs = new String[str.length()][maxColumn+1];
//
//			for (int j = 0; j < all.size(); j++) {
//				word = all.get(j);
//				System.out.println(word.end+"	"+word.begin);
//				strs[word.begin][word.end] = word.value;
//			}
//
//			for (int j = 0; j < strs.length; j++) {
//				for (int j2 = 0; j2 < strs[j].length; j2++) {
//					System.out.print(strs[j][j2]);
//					System.out.print("	");
//				}
//				System.out.println();
//			}
//		}
//	}
	
	public static void main(String[] args) throws IOException {
		String str = null ;
		long start = System.currentTimeMillis() ;
		BufferedReader br = IOUtil.getReader("C://Users//ansj//Desktop//新建文本文档.txt", "GBK") ;
		while((str=br.readLine())!=null){
			GetWordsImpl gwi = new GetWordsImpl();
			gwi.setStr(str);
			String temp= null;
			Graph gp = new Graph(str.length()) ;
			while((temp=gwi.allWords())!=null){
				gp.addTerm(new Term(temp,gwi.offe,gwi.getWeight(),gwi.getNature())) ;
			}
			gp.getPath();
			System.out.println(gp);
		}
		System.out.println(System.currentTimeMillis()-start);
		
	}
}
