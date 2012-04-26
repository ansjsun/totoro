package org.ansj.domain;

import java.util.ArrayList;
import java.util.List;

import org.ansj.library.NatureEnum;
import org.ansj.library.NatureMap;

public final class Natures {

	public List<Nature> natures = new ArrayList();
	
	public Natures(String nature,Integer weight){
		natures.add(new Nature(NatureEnum.valueOf(nature),weight)) ;
	}
	
	public Natures(String natureStr){
		String[] split = natureStr.split(",") ;
		String[] strs = null ;
		for (int i = 0; i < split.length; i++) {
			strs = split[i].split(":") ;
			this.add(strs[0], Integer.parseInt(strs[1])) ;
		}
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
