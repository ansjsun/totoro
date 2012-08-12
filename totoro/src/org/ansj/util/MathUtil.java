package org.ansj.util;

import org.ansj.domain.Path;
import org.ansj.library.NatureLibrary;

public class MathUtil {

	// 平滑参数
	private static final double dSmoothingPara = 0.1;
	// 一个参数
	private static final int MAX_FREQUENCE = 20799970;
	// ﻿Two linked Words frequency
	private static final double dTemp = (double) 1 / MAX_FREQUENCE;

	/**
	 * 从一个词的词性到另一个词的词性的分数
	 * 
	 * @param form
	 *            前面的词
	 * @param to
	 *            后面的词
	 * @return 分数
	 */
	public static double compuScore(Path from, Path to, int nTwoWordsFreq) {
		int frequency = to.getTermNature().frequency+1;
		double b = -Math.log(dSmoothingPara * frequency / (MAX_FREQUENCE + 80000) + (1 - dSmoothingPara)
				* ((1 - dTemp) * nTwoWordsFreq / frequency + dTemp));
		if (b < 0){
//			System.out.print(b+"\t");
//			System.out.println(from.getTerm().getName()+":"+from.getNatureStr()+"\t"+to.getTerm().getName()+":"+to.getNatureStr());
//			b += to.getTermNature().frequency;
		}
		
		b += to.index ;
//		System.out.println(b);
		
//		System.out.println(b);
		return from.getScore() + b ;
	}


	/**
	 * 从一个词的词性到另一个词的词性的分数,考虑了到路径的词频.两端的词性.相关度越高返回值越大
	 * 
	 * @param form
	 *            前面的词
	 * @param to
	 *            后面的词
	 * @return 分数
	 */
	private static double compuNatureScore(Path from, Path to) {
		int twoNatureFreq = NatureLibrary.getTwoNatureFreq(from, to);
		double b = Math.log((0.9 * twoNatureFreq) / (to.getTermNature().frequency + 1) + 0.1 * to.getTermNature().frequency
				/ (to.getTermNature().nature.allFrequency + 1));
//		 System.out.println(from.getNatureStr()+"\t"+"\t"+to.getNatureStr()+b);
		return b;
	}
	
	public static void main(String[] args) {
		System.out.println((char)(1061971/256));
		System.out.println((char)(1061971%256));
	}

}
