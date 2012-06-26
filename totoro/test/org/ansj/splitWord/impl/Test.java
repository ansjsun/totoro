package org.ansj.splitWord.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.ansj.domain.Term;
import org.ansj.splitWord.GetWords;
import org.ansj.util.Graph;

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
//		BufferedReader br = IOUtil.getReader("C://Users//ansj//Desktop//新建文本文档.txt", "GBK") ;
		String[] strs = new String[100] ;
		strs[0] = "他说的确实在理" ;
		strs[1] = "长春市长春节讲话" ;
		strs[2] = "结婚的和尚未结婚的" ;
		strs[3] = "结合成分子时" ;
		strs[4] = "旅游和服务是最好的" ;
		strs[5] = "邓颖超生前最喜欢的一个东西" ;
		strs[6] = "中国航天官员应邀到美国与太空总署官员开会" ;
		strs[7] = "上海大学城书店" ;
		strs[8] = "北京大学生前来应聘" ;
		strs[9] = "中外科学名著" ;
		strs[10] = "为人民服务" ;
		strs[11] = "独立自主和平等互利的原则" ;
		strs[12] = "为人民办公益" ;
		strs[13] = "这事的确定不下来" ;
		strs[14] = "费孝通向人大常委会提交书面报告" ;
		strs[15] = "aaa分事实上发货丨和无哦喝完酒" ;
		strs[16] = "不好意思清清爽爽" ;
		strs[17] = "长春市春节讲话" ;
		strs[18] = "中华人民共和国万岁万岁万万岁" ;
		strs[19] = "检察院鲍绍检察长" ;
		strs[20] = "长春市长春药店" ;
		strs[21] = "乒乓球拍卖完了" ;
		strs[22] = "据路透社报道，印度尼西亚社会事务部一官员星期二(29日)表示，"	
                + "日惹市附近当地时间27日晨5时53分发生的里氏6.2级地震已经造成至少5427人死亡，"	
                + "20000余人受伤，近20万人无家可归。" ;
		strs[23] = "刚试过" ;
		int num  = 0 ;
		for (int mm = 0; mm < 1; mm++) {
			for (int i = 0; i < strs.length; i++) {
				if(strs[i]==null)continue ;
				num+=strs[i].length() ;
				BufferedReader br = new BufferedReader(new StringReader(strs[i])) ;
				while((str=br.readLine())!=null){
//					CopyOfGetWordsImpl gwi = new CopyOfGetWordsImpl();
					GetWordsImpl gwi = new GetWordsImpl();
					gwi.setStr(str);
					String temp= null;
					Graph gp = new Graph(str,0) ;
					while((temp=gwi.allWords())!=null){
						gp.addTerm(new Term(temp,gwi.offe,gwi.getWeight(),gwi.getNatures(),gwi.getMaxNature())) ;
					}
//					gp.print() ;
					gp.getPath().merger(2);
					System.out.println(gp);
//					Term term = gp.optimalRoot() ;
//					while((term=term.getMaxTo())!=null){
//						System.out.println(term);
//					}
				}
			}
		}
		System.out.println(System.currentTimeMillis()-start);
		System.out.println(num);
		
	}
}
