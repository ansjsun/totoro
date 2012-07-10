package org.ansj.util;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.ansj.domain.Term;
import org.ansj.library.NatureEnum;

public class NumRecognition {

	/**
	 * 合并量词
	 * 
	 * @param list
	 * @return
	 */
	public static LinkedList<Term> recognition(List<Term> list) {
		LinkedList<Term> result = new LinkedList<Term>();

		Term term = null;
		Term term1 = null;

		ListIterator<Term> iterator = list.listIterator();

		while (iterator.hasNext()) {
			term = iterator.next();
			if (iterator.hasNext() && (term.maxNature == NatureEnum.nb || term.maxNature == NatureEnum.m)) {
				term1 = iterator.next() ;
				while(term1.maxNature==NatureEnum.m){
					term.merage(term1, NatureEnum.m) ;
					if(iterator.hasNext()){
						term1 = iterator.next() ;
					}else{
						result.add(term) ;
						return result ;
					}
				}
				if(term1.getNatures()!=null&&term1.getName().length()<3&&(term1.getNatures().contains(NatureEnum.q)||term1.getNatures().contains(NatureEnum.qt)||term1.getNatures().contains(NatureEnum.qv))){
					term.merage(term1, NatureEnum.m) ;
					result.add(term) ;
				}else{
					result.add(term) ;
					iterator.previous() ;
				}
			}else{
				result.add(term) ;
			}
		}

		return result;
	}
}
