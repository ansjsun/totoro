package org.ansj.splitWord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;

import org.ansj.domain.Term;
import org.ansj.util.filter.StopWord;

public class FilterAnalysis extends ToAnalysis {

	HashSet<String> hs = new HashSet<String>();

	/**
	 * 带有停用词过滤的
	 * 
	 * @param reader
	 * @param isNameRe
	 * @param hs
	 *            用户自定义停用词的hashSet
	 */
	public FilterAnalysis(Reader reader, boolean isNameRe, HashSet<String> hs) {
		super(reader, isNameRe);
		this.hs = hs;
	}

	/**
	 * 系统自带的停用词词典
	 * 
	 * @param reader
	 * @param isNameRe
	 */
	public FilterAnalysis(Reader reader, boolean isNameRe) {
		super(reader, isNameRe);
		this.hs = StopWord.getFilterSet();
	}

	@Override
	public Term next() throws IOException {
		// TODO Auto-generated method stub
		Term term = super.next();
		while (hs.contains(term.getName())) {
			term = super.next();
			if (term == null) {
				return null;
			}
		}
		// else {
		// return null;
		// }
		return term;
	}

	public static void main(String[] args) throws IOException {
		Reader reader = new InputStreamReader(new FileInputStream("/Users/ansj/Documents/快盘/冒死记录中国神秘事件（真全本）.txt"), "GBK");
		FilterAnalysis toAnalysis = new FilterAnalysis(reader, true);
		Term next = null;
		long start = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		while ((next = toAnalysis.next()) != null) {
//			System.out.println(next.getName() + ":" + next.maxNature);
//			sb.append(next.getName() + ":" + next.maxNature);
//			sb.append("\n");
		}
		System.out.println(System.currentTimeMillis() - start);
//		IOUtil.Writer("/Users/ansj/Documents/快盘/冒死.txt", "UTF-8", sb.toString());

	}

}
