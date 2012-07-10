package org.ansj.splitWord.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

import org.ansj.domain.Term;
import org.ansj.splitWord.Analysis;
import org.ansj.util.filter.StopWord;

/**
 * 如果对分词结果需要过滤停用词.那么就用这个吧
 * @author ansj
 *
 */
public class FilterAnalysis implements Analysis  {

	private static HashSet<String> hs = null ;

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
		FilterAnalysis.hs = hs;
	}

	/**
	 * 系统自带的停用词词典
	 * 
	 * @param reader
	 * @param isNameRe
	 */
	public FilterAnalysis(Analysis analysis) {
		this.analysis = analysis ;
		if(hs==null)
		FilterAnalysis.hs = StopWord.getFilterSet();
	}

	public Term next() throws IOException {
		// TODO Auto-generated method stub
		Term term = analysis.next();
		if(term==null) return null ;
		while (hs.contains(term.getName())) {
			term = analysis.next();
			if (term == null) {
				return null;
			}
		}
		return term;
	}

	

}
