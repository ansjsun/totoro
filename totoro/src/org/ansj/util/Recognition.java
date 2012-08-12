package org.ansj.util;

import org.ansj.domain.Path;
import org.ansj.domain.Term;

/**
 * 人名识别工具类
 * 
 * @author ansj
 * 
 */
public class Recognition {
	/**
	 * 一个超级简单的姓名识别.临时用
	 * @param to
	 * @param from
	 * @return
	 */
	public static boolean name(Path to, Path from){
		if (from.getTermNature().nature.natureIndex == 19 && to.getTerm().isName) {
			//合并名称
			from = from.merage(to, from.getTermNature()) ;
			return true ;
		} 
		return false ;
	}
	
	/**
	 * 数字识别
	 * @param to
	 * @param from
	 * @return
	 */
	public static boolean num(Path to, Path from){
		if(from.getTerm().isNum && to.getTermNature().nature.natureIndex == 15){
			from = from.merage(to, to.getTermNature());
			return true ;
		}
		return false ;
	}
	
	
	/**
	 * 用户字典识别
	 * @param to
	 * @param from
	 * @return
	 */
	public static boolean userDefine(Path to, Path from){
		if(from.getTerm().isNum && to.getTermNature().nature.natureIndex == 15){
			from = from.merage(to, to.getTermNature());
			return true ;
		}
		return false ;
	}
}
