package org.ansj.library;

import java.util.Arrays;

public final class Natures {

	public Nature[] natures = null;

	public Natures(String str) {
		String[] natruesStr = str.split(",");
		String[] strs = null ;
		natures = new Nature[natruesStr.length];
		for (int i = 0; i < natruesStr.length; i++) {
			strs = natruesStr[i].split(":") ;
			natures[i] = new Nature(NatureEnum.valueOf(strs[0]),Integer.parseInt(strs[1])) ;
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
