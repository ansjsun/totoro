package org.ansj.domain;

import org.ansj.library.NatureEnum;

public class Term{
	// 当前词
	private String name;
	// 当前词的起始位置
	private int offe;
	// 最优的来源路径
	private Term maxFrom;
	// 最优的去路径
	private Term maxTo;
	// 路径权重
	private int pathWeight = 0;
	//当前term的权重
	private int weight = 0 ;
	// 词性列表
	private Natures natures;
	//最可能词性
	public NatureEnum maxNature;

	public Term(String name, int offe, int weight, Natures natures ,NatureEnum maxNature ) {
		super();
		this.name = name;
		this.offe = offe;
		this.weight = weight ;
		this.pathWeight = weight ;
		this.maxNature = maxNature ;
		this.natures = natures==null?Natures.NULL:natures;
	}
	
	public Term(String name ,int offe ,NatureEnum maxNature){
		this.name = name ;
		this.offe = offe ;
		this.maxNature = maxNature ;
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

	public int getWeight(int yuan) {
		if(this.name.length()>yuan){
			return weight;
		}else{
			return -100 ;
		}
	}

	public void setPathWeight(Term term, int yuan) {
		if(term.offe>0&&term.maxFrom==null){
			return ;
		}
		if (maxFrom == null) {
			this.pathWeight = term.pathWeight + this.getWeight(yuan);
			this.maxFrom = term;
		} else {
			int score = term.pathWeight + this.getWeight(yuan);
			if (score >= pathWeight) {
				this.pathWeight = score;
				this.maxFrom = term;
			}
		}

	}

	public String toString() {
		return this.name + "/" +this.maxNature+" ";
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

	public int getPathWeight() {
		return pathWeight;
	}

	public int getWeight() {
		return weight;
	}

	public void reset(int yuan) {
		// TODO Auto-generated method stub
		this.maxFrom = null ;
		this.pathWeight = this.getWeight(yuan);
	}

	public void UpdateOffe(int baseOffe) {
		// TODO Auto-generated method stub
		this.offe+=baseOffe ;
	}

	public Natures getNatures() {
		return natures;
	}

	/**
	 * 进行term合并
	 * @param term
	 * @param maxNature
	 */
	public Term merage(Term term , NatureEnum maxNature){
		this.name = this.name+term.getName() ;
		this.maxTo = term.getMaxTo() ;
		this.maxNature = maxNature ;
		return this ;
	}

	/**
	 * 更新偏移量
	 * @param offe
	 */
	public void updateOffe(int offe) {
		// TODO Auto-generated method stub
		this.offe += offe ;
	}
	
	public boolean isName(){
		return this.natures.isName ;
	}

}
