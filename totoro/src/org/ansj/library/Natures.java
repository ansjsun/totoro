package org.ansj.library;

import java.util.Arrays;

public final class Natures {

	public int[] natures = null;;

	public Natures(String str) {
		String[] natruesStr = str.split(",");
		natures = new int[natruesStr.length];
		for (int i = 0; i < natruesStr.length; i++) {
			natures[i] = getNatureIndex(natruesStr[i].trim());
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

	public boolean contains(int n) {
		for (int i = 0; i < natures.length; i++) {
			if (n == natures[i]) {
				return true;
			}
		}
		return false;
	}
}
