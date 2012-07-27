package org.ansj.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.library.NatureEnum;

/**
 * 人名识别工具类
 * 
 * @author ansj
 * 
 */
public class NameRecognition {
	//
	// /*
	// * BBCD 343 0.003606 BBC 2 0.000021 BBE 125 0.001314 BBZ 30 0.000315 BCD
	// * 62460 0.656624 BEE 0 0.000000 BE 13899 0.146116 BG 869 0.009136 BXD 4
	// * 0.000042 BZ 3707 0.038971 CD 8596 0.090367 EE 26 0.000273 FB 871
	// 0.009157
	// * Y 3265 0.034324 XD 926 0.009735
	// */
	// // The person recognition patterns set
	// // BBCD:姓+姓+名1+名2;
	// // BBE: 姓+姓+单名;
	// // BBZ: 姓+姓+双名成词;
	// // BCD: 姓+名1+名2;
	// // BE: 姓+单名;
	// // BEE: 姓+单名+单名;韩磊磊
	// // BG: 姓+后缀
	// // BXD: 姓+姓双名首字成词+双名末字
	// // BZ: 姓+双名成词;
	// // B: 姓
	// // CD: 名1+名2;
	// // EE: 单名+单名;
	// // FB: 前缀+姓
	// // XD: 姓双名首字成词+双名末字
	// // Y: 姓单名成词
	//
	// private static final double DFACTORYS[] = { 0.003606, 0.000021, 0.001314,
	// 0.000315, 0.656624, 0.000021, 0.146116, 0.009136, 0.000042, 0.038971, 0,
	// 0.090367, 0.000273, 0.009157, 0.034324, 0.009735 };
	// private static final NatureEnum PATTERN[][] = { { NatureEnum.nr1,
	// NatureEnum.nr1, NatureEnum.nr2, NatureEnum.nr2 },
	// { NatureEnum.nr1, NatureEnum.nr1, NatureEnum.nr2, }, { NatureEnum.nr1,
	// NatureEnum.nr1, NatureEnum.NULL },
	// { NatureEnum.nr1, NatureEnum.NULL, NatureEnum.NULL }, { NatureEnum.nr1,
	// NatureEnum.NULL }, { NatureEnum.nr1, NatureEnum.n },
	// { NatureEnum.nr2, NatureEnum.nr2 }, { NatureEnum.nr2, NatureEnum.NULL }
	// };
	//
	// private static Entry root = null;
	//
	// /**
	// * 构建决策树
	// */
	// static {
	// root = new Entry(NatureEnum.NULL);
	// Entry temp = root;
	// for (int i = 0; i < PATTERN.length; i++) {
	// for (int j = 0; j < PATTERN[i].length; j++) {
	// if (j < PATTERN[i].length - 1) {
	// temp = temp.add(new Entry(PATTERN[i][j]));
	// } else {
	// temp.add(new Entry(PATTERN[i][j], 3, DFACTORYS[i]));
	// temp = root;
	// }
	// }
	// }
	//
	// }

	public static LinkedList<Term> recognition(List<Term> list) {

		LinkedList<Term> result = new LinkedList<Term>();

		Term term = null;
		Term term1 = null;
		Term term2 = null;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			term = list.get(i);
			if (!isB(term)) {
				result.add(term);
				continue;
			}

			switch (size - i) {
			case 1:
				result.add(term);
				return result;
			case 2:
				term1 = list.get(i + 1);
				if (term1.isName() || term1.maxNature == NatureEnum.NULL) {
					// term 和term1 合并
					term.merage(term1, NatureEnum.nr);
					result.add(term);
					return result;
				} else {
					result.add(term);
					result.add(term1);
					return result;
				}
			default:
				term1 = list.get(i + 1);
				switch (MStatus(term1)) {
				case 0:
					result.add(term);
					break;
				case 1:
					// 中间部分找到.判断第三个term
					term2 = list.get(i + 2);
					if (isE(term2)) {
						term.merage(term1, NatureEnum.nr).merage(term2, NatureEnum.nr);
						result.add(term);
						i += 2;
					} else {
						term.merage(term1, NatureEnum.nr);
						result.add(term);
						i++;
					}
					break;
				case 2:
					term.merage(term1, NatureEnum.nr);
					result.add(term);
					i++;
					break;
				}

				break;
			}
		}

		return result;
	}

	public static boolean isB(Term term) {
//		return term.isName() ;
		return term.maxNature == NatureEnum.nr1 ;
	}

	// 0 不是姓名的中间部分
	// 1 是姓名的中间部分
	// 2 是姓名的结束部分
	public static int MStatus(Term term) {
		if (term.maxNature == NatureEnum.NULL) {
			return 1;
		}
//		if (term.isName()) {
			if (term.getName().length() == 1) {
				return 1;
			} else if(term.isName()){
				return 2;
			}
//		}
		return 0;
	}

	public static boolean isE(Term term) {
		if (term.getName().length() > 1) {
			return false;
		}
		if (term.maxNature == NatureEnum.NULL || term.isName()) {
			return true;
		}
		return false;
	}
	// static class Entry {
	// NatureEnum parrent;
	// int status;
	// double dfactory;
	// Entry[] nexts = new Entry[5];
	//
	// public Entry(NatureEnum parrent) {
	// this.parrent = parrent;
	// this.status = 1;
	// }
	//
	// public int findMax(Term term) {
	// // TODO Auto-generated method stub
	// return term.getNatures().getNatureIndex(parrent.toString());
	//
	// }
	//
	// public Entry(NatureEnum parrent, int status, double dfactory) {
	// this.parrent = parrent;
	// this.status = status;
	// this.dfactory = dfactory;
	// }
	//
	// public Entry add(Entry entry) {
	// Entry temp = nexts[getInt(entry.parrent)];
	// if (temp == null) {
	// nexts[getInt(entry.parrent)] = entry;
	// } else {
	// switch (temp.status) {
	// case 3:
	// if (entry.status == 1) {
	// nexts[getInt(entry.parrent)].status = 2;
	// }
	// break;
	// case 1:
	// if (entry.status == 3) {
	// entry.status = 2;
	// nexts[getInt(entry.parrent)] = entry;
	// }
	// }
	// }
	// return nexts[getInt(entry.parrent)];
	// }
	// }
	//
	// /**
	// * 将模式转换为数字
	// *
	// * @param c
	// * @return
	// */
	// public static int getInt(NatureEnum n) {
	// switch (n) {
	// case nr1:
	// return 1;
	// case nr2:
	// return 2;
	// case n:
	// return 3;
	// case NULL:
	// return 4;
	// default:
	// return 0;
	// }
	// }

}
