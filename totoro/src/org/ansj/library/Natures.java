package org.ansj.library;

import java.util.ArrayList;
import java.util.List;

public final class Natures {

	public List<Nature> natures = new ArrayList();
	
	public Natures(String nature,Integer weight){
		natures.add(new Nature(NatureEnum.valueOf(nature),weight)) ;
	}
	
	public Natures(){
	}

	
	public void add(String nature,Integer weight){
		natures.add(new Nature(NatureEnum.valueOf(nature),weight)) ;
	}

	/**
	 * 判断一个词性是否在这里面有
	 * 
	 * @param natureStr
	 * @return
	 */
	public int getNatureIndex(String natureStr) {
		return NatureMap.natrueMap.get(natureStr);
	}

	public int getNatureIndex(Nature nature) {
		return NatureMap.natrueMap.get(nature);
	}
	
	

}
