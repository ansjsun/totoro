package org.ansj.splitWord;

import static org.ansj.library.InitDictionary.natures;
import static org.ansj.library.InitDictionary.status;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;

import org.ansj.domain.Term;
import org.ansj.library.NatureEnum;
import org.ansj.splitWord.impl.GetWordsImpl;
import org.ansj.util.Graph;
import org.ansj.util.IOUtil;
import org.ansj.util.StringUtil;
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
	 * 记录上一次文本长度
	 */
	private int tempLength;
	
	/**
	 * 是否识别人名
	 */
	private boolean isNameRe ;
	
	
	
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
	public ToAnalysis(String str, boolean isNameRe) {
		br = new BufferedReader(new StringReader(str));
	}

	/**
	 * 如果文档太过大建议传入输入流
	 * 
	 * @param reader
	 */
	public ToAnalysis(Reader reader , boolean isNameRe) {
		br = new BufferedReader(reader);
		this.isNameRe = isNameRe ; 
	}
	

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

		analysis(temp);

		if (!terms.isEmpty()) {
			return terms.poll();
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
				terms.add(new Term(temp.charAt(i) + "", i + offe, NatureEnum.NULL));
				break;
			case 3:
				terms.add(new Term(temp.charAt(i) + "", i + offe, natures[temp.charAt(i)].maxNature));
				start = i;
				end = start;
				break;
			case 4:
				start = i;
				end = 1;
				while (++i < length && status[temp.charAt(i)] == 4) {
					end++;
				}
				str = WordAlert.alertEnglish(temp, start, end);
				terms.add(new Term(str, start + offe, NatureEnum.en));
				i--;
				break;
			case 5:
				start = i;
				end = 1;
				while (++i < length && status[temp.charAt(i)] == 5) {
					end++;
				}
				str = WordAlert.alertNumber(temp, start, end);
				terms.add(new Term(str, start + offe, NatureEnum.nb));
				i--;
				break;
			default:
				start = i;
				end = i;
				c = temp.charAt(start);
				while (0 < status[c] && status[c] < 4) {
					end++;
					if (++i >= length)
						break;
					c = temp.charAt(i);
				}
				str = temp.substring(start, end);
				gwi.setStr(str);
				Graph gp = new Graph(str, offe + start);
				while ((str = gwi.allWords()) != null) {
					gp.addTerm(new Term(str, gwi.offe, gwi.getWeight(), gwi.getNatures(), gwi.getMaxNature()));
				}
				if(isNameRe){
					terms.addAll(gp.getPath().merger(2).mergerName());
				}else{
					terms.addAll(gp.getPath().merger(2).getResultLinked());
				}
				if (i < length) {
					i -= 1;
				}
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Reader reader = new InputStreamReader(new FileInputStream("/Users/ansj/Documents/快盘/冒死记录中国神秘事件（真全本）.txt"), "GBK");
		ToAnalysis toAnalysis = new ToAnalysis(reader,true);
		Term next = null;
		long start = System.currentTimeMillis();
		StringBuilder sb =  new StringBuilder() ;
		while ((next = toAnalysis.next()) != null) {
			System.out.println(next.getName() + ":" + next.maxNature);
			sb.append(next.getName()+":"+next.maxNature) ;
			sb.append("\n") ;
		}
		System.out.println(System.currentTimeMillis() - start);
		IOUtil.Writer("/Users/ansj/Documents/快盘/冒死.txt", "UTF-8", sb.toString()) ;
		
	}
}
