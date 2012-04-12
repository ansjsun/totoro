package org.ansj.splitWord;

public interface SplitWord {
	/**
	 * 正向最大标记
	 * @param str 传入的需要分词的句子
	 * @return
	 */
	public String tagMaxFront(String str) ;
	/**
	 * 正向最小匹配标记
	 * @param str 传入的需要分词的句子
	 * @return 返还分完词后的句子
	 */
	public String tagMinFront(String str) ;
	/**
	 * 逆向最大匹配标记
	 * @param str 传入的需要分词的句子
	 * @return 返还分完词后的句子
	 */
	public String tagMaxConverse(String str) ;
	/**
	 * 逆向最小匹配标记
	 * @param str 传入的需要分词的句子
	 * @return 返还分完词后的句子
	 */
	public String tagMinConverse(String str) ;

}
