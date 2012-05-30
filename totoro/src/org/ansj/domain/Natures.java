package org.ansj.domain;

import java.util.HashMap;
import java.util.Map;

import org.ansj.library.NatureEnum;
import org.ansj.library.NatureMap;

public final class Natures {
	
	public static final Natures NULL = new Natures("{NULL=1}") ;

	public Map<NatureEnum,Integer> natures = new HashMap<NatureEnum,Integer>();
	public int maxWeight = Integer.MIN_VALUE;
	public NatureEnum maxNature;
	
	
	public Natures(String natureStr){
		natureStr = natureStr.substring(1,natureStr.length()-1) ;
		String[] split = natureStr.split(",") ;
		String[] strs = null ;
		Integer weight = null ;
		for (int i = 0; i < split.length; i++) {
			strs = split[i].split("=") ;
			weight = Integer.parseInt(strs[1]) ;
			if(weight>maxWeight){
				maxWeight = weight ;
				this.maxNature = NatureEnum.valueOf(strs[0].trim()) ;
			}
			natures.put(NatureEnum.valueOf(strs[0].trim()), weight) ;
		}
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
