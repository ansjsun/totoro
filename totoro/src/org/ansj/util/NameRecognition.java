package org.ansj.util;

import java.util.LinkedList;

import org.ansj.domain.Nature;
import org.ansj.domain.Term;
import org.ansj.library.NatureEnum;

/**
 * 人名识别工具类
 * 
 * @author ansj
 * 
 */
public class NameRecognition {

	/*
	 * BBCD 343 0.003606 BBC 2 0.000021 BBE 125 0.001314 BBZ 30 0.000315 BCD
	 * 62460 0.656624 BEE 0 0.000000 BE 13899 0.146116 BG 869 0.009136 BXD 4
	 * 0.000042 BZ 3707 0.038971 CD 8596 0.090367 EE 26 0.000273 FB 871 0.009157
	 * Y 3265 0.034324 XD 926 0.009735
	 */
	// The person recognition patterns set
	// BBCD:姓+姓+名1+名2;
	// BBE: 姓+姓+单名;
	// BBZ: 姓+姓+双名成词;
	// BCD: 姓+名1+名2;
	// BE: 姓+单名;
	// BEE: 姓+单名+单名;韩磊磊
	// BG: 姓+后缀
	// BXD: 姓+姓双名首字成词+双名末字
	// BZ: 姓+双名成词;
	// B: 姓
	// CD: 名1+名2;
	// EE: 单名+单名;
	// FB: 前缀+姓
	// XD: 姓双名首字成词+双名末字
	// Y: 姓单名成词

	/**
	 * B=1 C=2 D=3 E=4 F=5 G=6 X=7 Y=8 Z=9
	 */

	private static final int PATTERNLEN[] = { 4, 3, 3, 3, 3, 3, 2, 2, 3, 2, 4, 2, 2, 2, 1, 2 };

	private static final double DFACTORYS[] = { 0.003606, 0.000021, 0.001314, 0.000315, 0.656624, 0.000021, 0.146116, 0.009136, 0.000042, 0.038971, 0,
			0.090367, 0.000273, 0.009157, 0.034324, 0.009735 };
	private static final char PATTERN[][] = { "BBCD".toCharArray(), "BBC".toCharArray(), "BBE".toCharArray(), "BBZ".toCharArray(), "BCD".toCharArray(),
			"BEE".toCharArray(), "BE".toCharArray(), "BG".toCharArray(), "BXD".toCharArray(), "BZ".toCharArray(), "CDCD".toCharArray(), "CD".toCharArray(),
			"EE".toCharArray(), "FB".toCharArray(), "Y".toCharArray(), "XD".toCharArray() };

	private static Entry root = null;
	
	/**
	 * 构建决策树
	 */
	static {
		root = new Entry('R');
		Entry temp = root;
		for (int i = 0; i < PATTERN.length; i++) {
			for (int j = 0; j < PATTERN[i].length; j++) {
				if (j < PATTERN[i].length - 1) {
					temp = temp.add(new Entry(PATTERN[i][j]));
				} else {
					temp.add(new Entry(PATTERN[i][j], 3, DFACTORYS[i]));
					temp = root ;
				}
			}
		}

	}
	

	public LinkedList<Term> recognition(LinkedList<Term> lists , int baseOffe) {
		Entry[] entrys = root.nexts ;
		Entry maxEntry = null ;
		for (Term term : lists) {
			for (int i = 0; i < entrys.length; i++) {
				maxEntry = entrys[i].findMax(term) ;
			}
		}
		
		return lists;
	}

	static class Entry {
		char parrent;
		int status;
		double dfactory;
		Entry[] nexts = new Entry[10];

		public Entry(char parrent) {
			this.parrent = parrent;
			this.status = 1;
		}

		public Entry findMax(Term term) {
			// TODO Auto-generated method stub
			Natures natures = term.getNatures() ;
			natures.getNatureIndex(parrent) ;
			return null;
		}

		public Entry(char parrent, int status, double dfactory) {
			this.parrent = parrent;
			this.status = status;
			this.dfactory = dfactory;
		}

		public Entry add(Entry entry) {
			Entry temp = nexts[getInt(entry.parrent)];
			if (temp == null) {
				nexts[getInt(entry.parrent)] = entry;
			} else {
				switch (temp.status) {
				case 3:
					if (entry.status == 1) {
						nexts[getInt(entry.parrent)].status = 2;
					}
					break;
				case 1:
					if (entry.status == 3) {
						entry.status = 2;
						nexts[getInt(entry.parrent)] = entry;
					}
				}
			}
			return nexts[getInt(entry.parrent)] ;
		}
	}

	/**
	 * 将模式转换为数字
	 * 
	 * @param c
	 * @return
	 */
	public static int getInt(char c) {
		switch (c) {
		case 'B':
			return 1;
		case 'C':
			return 2;
		case 'D':
			return 3;
		case 'E':
			return 4;
		case 'F':
			return 5;
		case 'G':
			return 6;
		case 'X':
			return 7;
		case 'Y':
			return 8;
		case 'Z':
			return 9;

		default:
			return 0;
		}
	}
	
	
	public static NatureEnum getNature(char c) {
		switch (c) {
		case 'B':
			return NatureEnum.nr1;
		case 'C':
			return NatureEnum.nr2;
		case 'D':
			return 3;
		case 'E':
			return 4;
		case 'F':
			return 5;
		case 'G':
			return 6;
		case 'X':
			return 7;
		case 'Y':
			return 8;
		case 'Z':
			return 9;

		default:
			return 0;
		}
	}
}
