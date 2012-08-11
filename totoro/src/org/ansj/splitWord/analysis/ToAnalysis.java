package org.ansj.splitWord.analysis;

import static org.ansj.library.InitDictionary.status;
import static org.ansj.library.InitDictionary.termNatures;
import static org.ansj.library.InitDictionary.IN_SYSTEM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.domain.TermNature;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.impl.GetWordsImpl;
import org.ansj.util.Graph;
import org.ansj.util.StringUtil;
import org.ansj.util.WordAlert;

/**
 * 基本分词+人名识别
 * 
 * @author ansj
 * 
 */
public class ToAnalysis implements Analysis {

	/**
	 * 用来记录偏移量
	 */
	public int offe;

	/**
	 * 记录上一次文本长度
	 */
	private int tempLength;

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

	private LinkedList<Term> terms = new LinkedList<Term>();

	/**
	 * while 循环调用.直到返回为null则分词结束
	 * 
	 * @return
	 * @throws IOException
	 */
	private Term term = null;

	public Term next() throws IOException {

		if (!terms.isEmpty()) {
			term = terms.poll();
			term.updateOffe(offe);
			return term;
		}

		String temp = br.readLine();

		while (StringUtil.isBlank(temp)) {
			if (temp == null) {
				return null;
			} else {
				offe = offe + temp.length() + 1;
				temp = br.readLine();
			}

		}

		offe += tempLength;

		System.out.println(terms.size());	
		analysis(temp);
		
		if (!terms.isEmpty()) {
			term = terms.poll();
			term.updateOffe(offe);
			return term;
		}

		return null;
	}

	private void analysis(String temp) {
		// TODO Auto-generated method stub

		int start = 0;
		int end = 0;
		int length = 0;

		length = temp.length();

		tempLength = length + 1;

		String str = null;
		char c = 0;
		for (int i = 0; i < length; i++) {
			switch (status[temp.charAt(i)]) {
			case 0:
				terms.add(new Term(temp.charAt(i) + "", i, TermNature.NULL));
				break;
			case 4:
				start = i;
				end = 1;
				while (++i < length && status[temp.charAt(i)] == 4) {
					end++;
				}
				str = WordAlert.alertEnglish(temp, start, end);
				terms.add(new Term(str, start, TermNature.EN));
				i--;
				break;
			case 5:
				start = i;
				end = 1;
				while (++i < length && status[temp.charAt(i)] == 5) {
					end++;
				}
				str = WordAlert.alertNumber(temp, start, end);
				terms.add(new Term(str, start, TermNature.NB));
				i--;
				break;
//			case 3:
//				//非常卑鄙的利用case穿透.拦截标点符号
//				if(termNatures[temp.charAt(i)].length==1){
//					terms.add(new Term(temp.charAt(i) + "", i, termNatures[temp.charAt(i)][0]));
//					start = i;
//					end = start;
//					break;
//				}
			default:
				start = i;
				end = i;
				c = temp.charAt(start);
				while (IN_SYSTEM[c]) {
					end++;
					if (++i >= length)
						break;
					c = temp.charAt(i);
				}
				str = temp.substring(start, end);
				gwi.setStr(str);
				Graph gp = new Graph(str);
				while ((str = gwi.allWords()) != null) {
					gp.addTerm(new Term(str, gwi.offe, gwi.getTermNatures()));
				}
				List<Term> result = gp.getPath().merger().getResult();
				
//				gp.print() ;
				
				for (Term term : result) {
					term.updateOffe(start);
				}
				terms.addAll(result);
				if (i < length) {
					i -= 1;
				}
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException {
//		String str = "费孝通向人大常委会提交书面报告" ;
//		String str = "结婚的和尚未结婚的" ;
//		String str = "生前" ;
//		String str = "长春市长春药店" ;
//		String str = "长春市长春药店" ;
		String str = "T恤" ;
		Analysis toAnalysis = new ToAnalysis(new StringReader(str));

		Term term = null;

		while ((term = toAnalysis.next()) != null) {
			System.out.println(term.getOffe() + "	" + term);
		}
	}
}
