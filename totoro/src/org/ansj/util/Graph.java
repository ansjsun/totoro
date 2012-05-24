package org.ansj.util;

import java.util.Arrays;
import java.util.LinkedList;

import org.ansj.domain.Term;

/**
 * 最短路径
 * @author ansj
 *
 */
public class Graph {
	private Term[][] terms = null;
	private Term end = null ;
	private Term root = null ;

	public Graph(int size) {
		terms = new Term[size+1][1];
		end = new Term("E",size,1,null) ;
		root = new Term("B",size,1,null) ;
		addTerm(end) ;
	}
	/**
	 * 构建最优路径
	 */
	public void getPath(){
		for (int i = 0; i < terms.length; i++) {
			for (int j = 0; j < terms[i].length; j++) {
				if(terms[i][j]!=null){
					int to = terms[i][j].getTo()+1 ;
					if(to>=terms.length)continue ;
					for (int k = 0; k < terms[to].length; k++) {
						if(terms[to][k]!=null){
							terms[to][k].setPathWeight(terms[i][j]) ;
						}
					}
				}
			}
		}
	}

	public void addTerm(Term term) {
		// 如果当前词数组的长度小于字符串大的长度那么扩容
		int nameLength = term.getName().length();
		//进行数组扩容荣
		terms[term.getOffe()] = growValite(terms[term.getOffe()], nameLength);
		// 将词放到图的位置
		terms[term.getOffe()][nameLength - 1] = term;
	}

	/**
	 * 验证数组是否需要扩容
	 * @param elementData
	 * @param length
	 * @return
	 */
	private Term[] growValite(Term[] elementData, int length) {
		if (elementData.length >= length) {
			return elementData;
		}
		return grow(elementData, length);
	}

	//对数组进行扩容
	private Term[] grow(Term[] elementData, int length) {
		// 如果起始长度则长度默认为起始长度+1
		if (elementData.length == 0) {
			return new Term[length + 1];
		} else {
			elementData = Arrays.copyOf(elementData, length);
		}
		return elementData;
	}
	
	private StringBuilder sb = new StringBuilder() ;
	public String toString(){
		showPath(end) ;
		return sb.toString() ;
	}
	
	/**
	 * 取得最优路径的root Term
	 * @return
	 */
	public Term optimalRoot(){
		Term to = end.getMaxFrom() ;
		Term from = null ;
		while((from=to.getMaxFrom())!=null){
			from.setMaxTo(to) ;
			to = from ;
		}
		root.setMaxTo(to) ;
		return root ;
	}
	
	
	
	/**
	 * 显示路径
	 * @param term
	 */
	private void showPath(Term term){
		if(term.getMaxFrom()!=null){
			showPath(term.getMaxFrom()) ;
		}
		if(term.getOffe()<terms.length-1){
			sb.append(term.toString()) ;
		}
	}
}

	


