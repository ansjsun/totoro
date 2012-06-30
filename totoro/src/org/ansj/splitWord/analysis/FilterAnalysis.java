package org.ansj.splitWord.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

import org.ansj.domain.Term;
import org.ansj.splitWord.Analysis;
import org.ansj.util.filter.StopWord;

public class FilterAnalysis  {

	private HashSet<String> hs = new HashSet<String>();

	private Analysis analysis = null ;
	/**
	 * 带有停用词过滤的
	 * 
	 * @param reader
	 * @param isNameRe
	 * @param hs
	 *            用户自定义停用词的hashSet
	 */
	public FilterAnalysis(Analysis analysis, HashSet<String> hs) {
		this.analysis = analysis ;
		this.hs = hs;
	}

	/**
	 * 系统自带的停用词词典
	 * 
	 * @param reader
	 * @param isNameRe
	 */
	public FilterAnalysis(Analysis analysis) {
		this.analysis = analysis ;
		this.hs = StopWord.getFilterSet();
	}

	public Term next() throws IOException {
		// TODO Auto-generated method stub
		Term term = analysis.next();
		while (hs.contains(term.getName())) {
			term = analysis.next();
			if (term == null) {
				return null;
			}
		}
		return term;
	}

	

}
