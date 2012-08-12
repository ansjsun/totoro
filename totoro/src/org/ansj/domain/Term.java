package org.ansj.domain;

import org.ansj.library.NatureLibrary;
import org.ansj.util.MathUtil;

public class Term {
	public static final Term NULL = new Term("NULL", 0, TermNature.NULL);
	// 当前词
	private String name;
	// 当前词的起始位置
	private int offe;
	// 词性列表
	private Path[] paths = new Path[0];
	// 最大路径
	private Path maxPath = Path.NULLPATH;
	// 同一行内数据
	private Term next;
	// 是有可能是一个数字
	public boolean isNum;
	// 是否可能是一个名字
	public boolean isName;
	//这个词的最大词频
	public int frequency ;

	public Term(String name, int offe, TermNature tn) {
		super();
		this.name = name;
		this.offe = offe;
		this.frequency = tn.frequency ;
		maxPath = new Path(tn, this);
		paths = new Path[] { maxPath };
	}

	public Term(String name, int offe, TermNature[] termNatures) {
		super();
		this.name = name;
		this.offe = offe;
		paths = new Path[termNatures.length];
		int maxFreq = 0;
		TermNature termNature = null;
		for (int i = 0; i < termNatures.length; i++) {
			termNature = termNatures[i];
			if (termNature.nature.index == 18 && this.name.length() == 1) {
				isNum = true;
			} else if (isNum && maxFreq < termNature.frequency) {
				isNum = false;
			}

			if (termNature.nature.natureIndex == 19) {
				isName = true;
			}
			this.frequency = termNature.frequency ;
			paths[i] = new Path(termNatures[i], this);
		}
	}

	// 可以到达的位置
	public int getTo() {
		return offe + name.length();
	}

	public int getOffe() {
		return offe;
	}

	public void setOffe(int offe) {
		this.offe = offe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 核心构建最优的路径
	 * 
	 * @param term
	 */
	public void setPathScore(Term from) {
		double score;
		// 遍历两个term的所有词性
		// 维特比进行最优路径的构建
		int nTwoWordsFreq = NatureLibrary.getTwoWordsFreq(from, this);
		Path[] fPaths = from.paths;
		for (Path fPath : fPaths) {
			for (Path tPath : paths) {
				score = MathUtil.compuScore(fPath, tPath, nTwoWordsFreq);
				if (tPath.getFrom() == null) {
					tPath.setFromAndScore(fPath, score);
					this.maxPath = tPath;
					this.maxPath.setScore(score);
				} else {
					// 如果当前最大权重比这个小
					if (tPath.getScore() >= score) {
						tPath.setFromAndScore(fPath, score);
						this.maxPath = tPath;
					}
				}
			}
		}
	}

	public String toString() {
		return this.name + "/" + this.maxPath.getNatureStr();
	}

	/**
	 * 进行term合并
	 * 
	 * @param term
	 * @param maxNature
	 */
	public Term merage(Term to, Path path) {
		this.name = this.name + to.getName();
		this.maxPath = path;
		return this;
	}

	/**
	 * 更新偏移量
	 * 
	 * @param offe
	 */
	public void updateOffe(int offe) {
		// TODO Auto-generated method stub
		this.offe += offe;
	}

	public Path getMaxPath() {
		return maxPath;
	}

	public Term getNext() {
		return next;
	}

	public Term setNext(Term next) {
		this.next = next;
		return this;
	}

}
