package org.ansj.util;

import org.ansj.domain.Term;
import static org.ansj.library.InitDictionary.*;

public class NameIdentify {
	static{
		initLibrary() ;
	}
	/**
	 * 加载姓名词典
	 */
	public static void initLibrary(){
		try {
			initNameLibrary() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 传入需要识别人名的根Term
	 * @param root
	 * @return 返回根term
	 */
	public static Term identify(Term root){
		Term term = null ;
		int i = 1   ;
		String name = null ;
		while((term=root.getMaxTo())!=null){
			if("nr1".equals(term.getNature())){
				while((name=term.getMaxTo().getName()).length()==1&&nameMap.containsKey(name.charAt(0))){
					i++ ;
				}
				if(i==1&&name.length()==2){
					
				}else{
					
				}
			}
		}
		return root ;
	}
}
