package org.ansj.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.library.NatureEnum;

/**
 * 最短路径
 * @author ansj
 *
 */
public class Graph {
	private static Term[] EMPTYTERMS = new Term[0] ;
	private Term[][] terms = null;
	private Term end = null ;
	private Term root = null ;
	private static final byte SE = 3 ;
	private static final String E = "E" ;
	private static final String B = "B" ;

	public Graph(int size) {
		terms = new Term[size+1][1];
		end = new Term(E,size,1,null,NatureEnum.NULL,SE) ;
		root = new Term(B,size,1,null,NatureEnum.NULL,SE) ;
		addTerm(end) ;
	}
	/**
	 * 构建最优路径
	 */
	public Merger getPath(){
		return new Merger() ;
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
	
	public void print(){
		for (int i = 0; i < terms.length; i++) {
			boolean flag = false ;
			for (int j = 0; j < terms[i].length; j++) {
				if(terms[i][j]!=null)
					System.out.print(terms[i][j]+":"+terms[i][j].getWeight()+":"+terms[i][j].getPathWeight());
				flag = true ;
			}
			if(flag)
			System.out.println();
		}
	}
	int testji = 0 ;
	
	/**
	 * 合并term.因为目前的方式不适合长词的区分
	 * @author ansj
	 *
	 */
	public class Merger{
		/**
		 * 进行n元语法数据合并
		 * @param yuan
		 * @return
		 */
		public Merger merger(int yuan){
			boolean flag = false ;
			for (int y = 0; y <= yuan; y++) {
				if(flag){
					updateTerms(y) ;
				}else{
					flag = true ;
				}
				for (int i = 0; i < terms.length; i++) {
					for (int j = 0; j < terms[i].length; j++) {
						if(terms[i][j]!=null){
							int to = terms[i][j].getTo()+1 ;
							if(to>=terms.length)continue ;
							for (int k = 0; k < terms[to].length; k++) {
								if(terms[to][k]!=null){
									terms[to][k].setPathWeight(terms[i][j],y) ;
								}
							}
						}
					}
				}
			}
			return this ;
		}
		

		
		/**
		 * 将最终结果放到Term数组中
		 */
		public List<Term> getResult(){
			optimalRoot() ;
			List<Term> result = new ArrayList<Term>() ;
			Term term = root ;
			while((term=term.getMaxTo())!=end){
				result.add(term) ;
			}
			return result ;
		}
		
		
		/**
		 * 将最终结果放到Term数组中
		 */
		public LinkedList<Term> getResultLinked(){
			LinkedList<Term> result = new LinkedList<Term>() ;
			Term term = end ;
			while((term=term.getMaxFrom())!=null){
				result.addFirst(term) ;
			}
			return result ;
		}
		
		
		
		/**
		 * 进行人名识别
		 * @param yuan
		 * @return
		 */
		public Merger mergerName(int yuan){
			LinkedList<Term> link = getResultLinked() ;
			Term term = null ;
			for (Iterator<Term> iterator = link.iterator(); iterator.hasNext();) {
				term = iterator.next();
			}
			
			return this ;
		}
		/**
		 * 移除中间的可能结果.讲路径重新设置
		 */
		private void updateTerms(int yuan){
			testji++ ;
			Term[][] tempTerms = new Term[terms.length][0] ;
			Term term = end ;
			int length = 0  ;
			int begin = 0  ;
			int index = 0  ;
			while(term!=null){
				index = term.getOffe() ;
				begin = term.getName().length()-1 ;
				length = terms[index].length ;
				tempTerms[index] = new Term[length] ;
				for (int i = begin; i < length; i++) {
					if(terms[index][i]==null) continue ;
					if(i==begin)
					term = term.getMaxFrom() ;
					terms[index][i].reset(yuan) ;
					tempTerms[index][i] = terms[index][i] ;
				}
			}
			terms = tempTerms ;
		}
	}
	
	/**
	 * 取得最优路径的root Term
	 * @return
	 */
	private Term optimalRoot(){
		Term to = end;
		Term from = null ;
		while((from=to.getMaxFrom())!=null){
			from.setMaxTo(to) ;
			to = from ;
		}
		root.setMaxTo(to) ;
		to.setMaxFrom(root); 
		return root ;
	}
	
}

	


