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
		int frequency = to.getTermNature().frequency + 1;
		double b = -Math.log(dSmoothingPara * frequency / (MAX_FREQUENCE + 80000) + (1 - dSmoothingPara) * ((1 - dTemp) * nTwoWordsFreq / frequency + dTemp));
//System.out.print(from+"\t"+to+"\t"+compuNatureScore(from,to));
		
		return from.getScore() + b + to.index + 1/compuNatureScore(from,to);
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
		int allFrequency = to.getTerm().frequency ;
		int frequency = to.getTermNature().frequency + 1;
		if(frequency<1){
			allFrequency = 1 ;
		}
		if(allFrequency < frequency){
			allFrequency = frequency ;
		}
		double twoNatureFreq = NatureLibrary.getTwoNatureFreq(from, to)+1;
		double b = (0.9 * twoNatureFreq) / frequency + 0.1 * frequency
				/ (allFrequency + 1);
		return b;
	}

	public static void main(String[] args) {
		int twoNatureFreq = 1;
		int frequency = 0;
		int allFrequency = 1;
		double b = (0.9 * twoNatureFreq) / (frequency + 1) + 0.1 * frequency / (allFrequency + 1);
		System.out.println(b);
	}

}
