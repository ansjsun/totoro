package org.ansj.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.ansj.domain.Path;
import org.ansj.domain.Term;
import org.ansj.domain.TermNature;

/**
 * 最短路径
 * 
 * @author ansj
 * 
 */
public class Graph {
	private Term[][] terms = null;
	private Term end = null;
	private Term root = null;
	private static final String E = "END";
	private static final String B = "BEGIN";
	private String str;

	public Graph(String str) {
		this.str = str;
		int size = str.length();
		terms = new Term[size + 1][2];
		end = new Term(E, size, TermNature.END);
		root = new Term(B, -1, TermNature.BEGIN);
		terms[size][0] = end;
	}

	/**
	 * 构建最优路径
	 */
	public Merger getPath() {
		return new Merger();
	}

	public void addTerm(Term term) {
		// 如果当前词数组的长度小于字符串大的长度那么扩容
		int nameLength = term.getName().length();
		// 进行数组扩容荣
		terms[term.getOffe()] = growValite(terms[term.getOffe()], nameLength);
		// 将词放到图的位置
		terms[term.getOffe()][nameLength - 1] = term;

	}

	/**
	 * 验证数组是否需要扩容
	 * 
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

	// 对数组进行扩容
	private Term[] grow(Term[] elementData, int length) {
		// 如果起始长度则长度默认为起始长度+1
		if (elementData.length == 0) {
			return new Term[length + 1];
		} else {
			elementData = Arrays.copyOf(elementData, length);
		}
		return elementData;
	}

	private StringBuilder sb = new StringBuilder();

	public String toString() {
		showPath(end.getMaxPath());
		return sb.toString();
	}

	/**
	 * 显示路径
	 * 
	 * @param term
	 */
	private void showPath(Path path) {
		if (path.getFrom() != null) {
			showPath(path.getFrom());
		}
		if (path.getTerm().getOffe() < terms.length - 1) {
			sb.append(path.getTerm().toString());
		}
	}

	public void print() {
		for (int i = 0; i < terms.length; i++) {
			for (int j = 0; j < terms[i].length; j++) {
				if (terms[i][j] != null)
					System.out.println(terms[i][j] + ":" + (terms[i][j].getMaxPath() == null ? Double.MAX_EXPONENT : terms[i][j].getMaxPath().getScore()));
			}
		}
	}

	/**
	 * 合并term.因为目前的方式不适合长词的区分
	 * 
	 * @author ansj
	 * 
	 */
	public class Merger {
		/**
		 * 寻找最优路径
		 * 
		 * @param yuan
		 * @return
		 */
		public Merger merger() {
			// BEGIN先行打分
			for (int i = 0; i < terms[0].length; i++) {
				merger1(root, 0);
			}
			for (int i = 0; i < terms.length; i++) {
				for (int j = 0; j < terms[i].length; j++) {
					if (terms[i][j] != null && terms[i][j].getMaxPath().getFrom() != null) {
						int to = terms[i][j].getTo() + 1;
						if (to < terms.length) {
							merger1(terms[i][j], to);
						}

					}
				}
			}
			optimalRoot();
			return this;
		}

		/**
		 * 具体的遍历打分方法
		 * 
		 * @param i
		 *            起始位置
		 * @param j
		 *            起始属性
		 * @param to
		 */
		private void merger1(Term fromTerm, int to) {
			boolean flag = true;
			for (int k = 0; k < terms[to].length; k++) {
				if (terms[to][k] != null && fromTerm.getMaxPath() != null) {
					// 关系式to.set(from)
					terms[to][k].setPathScore(fromTerm);
					flag = false;
				}
			}
			if (flag) {
				terms[to][0] = new Term(String.valueOf(str.charAt(to)), to, TermNature.NULL);
				terms[to][0].setPathScore(fromTerm);
			}
		}

		/**
		 * 将最终结果放到Term数组中
		 */
		public List<Term> getResult() {
			List<Term> result = new ArrayList<Term>();
			Path path = root.getMaxPath();
			while ((path = path.getTo()) != end.getMaxPath()) {
				result.add(path.getTerm());
			}
			return result;
		}

		/**
		 * 将最终结果放到Term数组中,"感觉linkedlist" 没大用先注释看看哪里报错再说
		 */
		// public LinkedList<Term> getResultList() {
		// LinkedList<Term> result = new LinkedList<Term>() ;
		// Term term = root;
		// while ((term = term.getMaxPath().getTo()) != end) {
		// result.add(term);
		// }
		// return result;
		// }

	}

	/**
	 * 取得最优路径的root Term
	 * 
	 * @return
	 */
	private Term optimalRoot() {
		Path to = end.getMaxPath();
		Path from = null;
		while ((from = to.getFrom()) != null) {
			setToAndfrom(to, from);
			to = from;
		}
		return root;
	}

	private void setToAndfrom(Path to, Path from) {
		// TODO Auto-generated method stub
		from.setTo(to);
		to.setFrom(from);
	}

}
