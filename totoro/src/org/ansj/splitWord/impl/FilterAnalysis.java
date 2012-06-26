package org.ansj.splitWord.impl;

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

	

}
