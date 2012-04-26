package org.ansj.domain ;

import org.ansj.library.NatureEnum;

public class Nature {
	
	private NatureEnum name ;
	private int weight ;

	public Nature(NatureEnum name, int weight) {
		this.name = name ;
		this.weight = weight ;
	}

	public NatureEnum getName() {
		return name;
	}

	public void setName(NatureEnum name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
