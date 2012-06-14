package org.ansj.splitWord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;

import static org.ansj.library.InitDictionary.natures;
import static org.ansj.library.InitDictionary.status;

import org.ansj.domain.Term;
import org.ansj.library.NatureEnum;
import org.ansj.splitWord.impl.GetWordsImpl;
import org.ansj.util.Graph;
import org.ansj.util.WordAlert;

/**
 * 这个类是用来断句的
 * 
 * @author ansj
 * 
 */
public class ToAnalysis {

	/**
	 * 用来记录偏移量
	 */
	public int offe;

	/**
	 * 分词的类
	 */
	private GetWordsImpl gwi = new GetWordsImpl();

	/**
	 * 文档读取流
	 */
	private BufferedReader br;

	/**
	 * 构造方法..应该是一篇文档作为一次传入.
	 * 
	 * @param str
	 */
	public ToAnalysis(String str) {
		br = new BufferedReader(new StringReader(str));
	}

	/**
	 * 如果文档太过大建议传入输入流
	 * 
	 * @param reader
	 */
	public ToAnalysis(Reader reader) {
		br = new BufferedReader(reader);
	}

	private int start = 0;
	private int end = 0;
	private int length = 0;
	private String temp = null;

	LinkedList<Term> terms = new LinkedList<Term>();

	/**
	 * while 循环调用.直到返回为null则分词结束
	 * 
	 * @return
	 * @throws IOException
	 */
	public Term next() throws IOException {

		if (!terms.isEmpty()) {
			return terms.poll();
		}

		temp = br.readLine();

		if (temp == null) {
			return null;
		}

		length = temp.length();

		String str = null;
		char c = 0;
		for (int i = 0; i < length; i++) {
			switch (status[temp.charAt(i)]) {
			case 0:
//				terms.add(new Term(temp.charAt(i) + "", i + offe,
//						NatureEnum.NULL));
//				System.out.println(terms);
				start = i;
				end = start ;
				break;
			case 3:
				terms.add(new Term(temp.charAt(i) + "", i + offe, natures[temp
						.charAt(i)].maxNature));
				start = i;
				end = start  ;
				System.out.println(terms);
				break;
			case 4:
				start = i;
				while (i < length && status[temp.charAt(i++)] == 4) {
					end++;
				}
				str = WordAlert.alertEnglish(temp, start, end);
				terms.add(new Term(str, start + offe, NatureEnum.en));
				i-- ;
				start = i;
				System.out.println(terms);
				break;
			case 5:
				while (status[temp.charAt(i++)] == 5 && i < length) {
					end++;
				}
				str = WordAlert.alertNumber(temp, start, end);
				terms.add(new Term(str, start + offe, NatureEnum.nb));
				System.out.println(terms);
				i-- ;
				start = i;
				break;
			default:
				c = temp.charAt(i);
				while (i < length && 0 < status[c] && status[c] < 3) {
					end++;
					c = temp.charAt(i++);
				}
				str = temp.substring(start, end);
				gwi.setStr(str);
				Graph gp = new Graph(str.length());
				while ((str = gwi.allWords()) != null) {
					gp.addTerm(new Term(str, gwi.offe, gwi.getWeight(), gwi
							.getNatures(), gwi.getMaxNature(), gwi.getStatus()));
				}
				terms.addAll(gp.getPath().merger(2).getResultLinked());
				i-- ;
				start = i;
				System.out.println(terms);
				break;
			}
		}

		if (!terms.isEmpty()) {
			return terms.poll();
		}

		return null;
	}

	public static void main(String[] args) throws IOException {
		ToAnalysis toAnalysis = new ToAnalysis("java 学习 助手 你好北京");
		
//		System.out.println(status[' ']);
//		
//		System.out.println(status[' ']);
		Term next = null;
		while ((next = toAnalysis.next()) != null) {
			System.out.println(next);
		}
		
//		String str = "abcdefghijklmnopqrstuvwxyz" ;
//		System.out.println(str.substring(5, 8));
	}
}
