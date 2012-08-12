package org.ansj.util;

import java.util.ArrayList;
import java.util.Arrays;
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
	private String str = null;
	private Term[] terms = null;
	private Term end = null;
	private Term root = null;
	private static final String E = "END";
	private static final String B = "BEGIN";

	public Graph(String str) {
		this.str = str;
		int size = str.length();
		terms = new Term[size + 1];
		end = new Term(E, size, TermNature.END);
		root = new Term(B, -1, TermNature.BEGIN);
		terms[size] = end;
	}

	/**
	 * 构建最优路径
	 */
	public Merger getPath() {
		return new Merger();
	}

	public void addTerm(Term term) {
		// 将词放到图的位置
		if (terms[term.getOffe()] == null) {
			terms[term.getOffe()] = term;
		} else {
			terms[term.getOffe()] = term.setNext(terms[term.getOffe()]);
		}
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
		Term term = null;
		for (int i = 0; i < terms.length; i++) {
			term = terms[i];
			while (term != null) {
				System.out.println(term + ":" + (term.getMaxPath() == null ? Double.MAX_EXPONENT : term.getMaxPath().getScore()));
				term = term.getNext();
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
			Term term = null;
			// BEGIN先行打分
			merger1(root, 0);
			for (int i = 0; i < terms.length; i++) {
				term = terms[i];
				while (term != null && term.getMaxPath().getFrom() != null && term != end) {
					int to = term.getTo();
					merger1(term, to);
					term = term.getNext();
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
			Term term = null;
			if (terms[to] != null) {
				term = terms[to];
				while (term != null) {
					// 关系式to.set(from)
					term.setPathScore(fromTerm);
					term = term.getNext();
				}
			} else {
				terms[to] = new Term(String.valueOf(str.charAt(to)), to, TermNature.NULL);
				terms[to].setPathScore(fromTerm);
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
			// 断开横向链表.节省内存
			to.getFrom().getTerm().setNext(null);
			to = setToAndfrom(to, from);
		}
		return root;
	}

	private Path setToAndfrom(Path to, Path from) {
		// TODO Auto-generated method stub
		//数字合并
		if (Recognition.num(to, from)) {
			return from;
		}
		
		//姓名识别
		if (Recognition.name(to, from)) {
			return from;
		}

		
		from.setTo(to);
		to.setFrom(from);
		return from;
	}

}
