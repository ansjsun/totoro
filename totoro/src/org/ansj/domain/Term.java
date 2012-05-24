package org.ansj.domain;

import java.util.LinkedList;

public class Term {
	// 当前词
	private String name;
	// 当前词的起始位置
	private int offe;
	// 最优的来源路径
	private Term maxFrom;
	// 最优的去路径
	private Term maxTo;
	// 路径权重
	private float weight = Float.MAX_VALUE;
	// 词性
	private Natures natures;

	public Term(String name, int offe, float weight, Natures natures) {
		super();
		this.name = name;
		this.offe = offe;
//		if (name.length() > 1) {
//			this.weight = weight / (1000 * name.length());
//		} else {
//			this.weight = weight==0?10000:weight;
//		}
		
		if(name.length()>1)
			this.weight = weight ;
		else
			this.weight = 1 ;

		this.natures = natures==null?Natures.NULL:natures;
	}

	// 可以到达的位置
	public int getTo() {
		return offe + name.length() - 1;
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

	public float getWeight() {
		return weight;
	}

	public void setPathWeight(Term term) {
		if(term.offe>0&&term.maxFrom==null){
			return ;
		}
		if (maxFrom == null) {
			this.weight = term.getWeight() + this.weight;
			this.maxFrom = term;
		} else {
			float score = this.weight - maxFrom.getWeight() + term.getWeight();
			if (score >= weight) {
				this.weight = score;
				this.maxFrom = term;
			}
		}

	}

	public String toString() {
		return this.name + ":" +this.weight+"/";
	}

	public Term getMaxFrom() {
		return maxFrom;
	}

	public void setMaxFrom(Term maxFrom) {
		this.maxFrom = maxFrom;
	}
	
	public Term getMaxTo() {
		return maxTo;
	}

	public void setMaxTo(Term maxTo) {
		this.maxTo = maxTo;
	}


	public void setWeight(float weight) {
		this.weight = weight;
	}

}
