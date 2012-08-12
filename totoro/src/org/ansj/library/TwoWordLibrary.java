package org.ansj.library;

import love.cq.domain.Forest;
import love.cq.domain.WoodInterface;
import love.cq.library.Library;

import org.ansj.domain.Term;
import org.ansj.util.MyStaticValue;

public class TwoWordLibrary {
	private static Forest FOREST = null ;
	static{
		try {
			long start = System.currentTimeMillis() ;
			FOREST = Library.makeForest(MyStaticValue.rb.getString("bigramdict"));
			System.out.println("加载关联词典完成用时:"+(System.currentTimeMillis()-start));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static final char LC = '@' ;
	
	public static int getTwoWordFreq(Term from , Term to) {
		if(from.getMaxPath().getTermNature().frequency==1||
				to.getMaxPath().getTermNature().frequency==1){
			return 0 ;
		}
		WoodInterface branch = FOREST ;
		String content = from.getName() ;
		for (int i = 0; i < content.length(); i++) {
			branch = branch.get(content.charAt(i)) ;
			if(branch==null){
				return 0 ;
			}
		}
		branch = branch.get(LC) ;
		if(branch==null){
			return 0 ;
		}
		content = to.getName() ;
		for (int i = 0; i < content.length(); i++) {
			branch = branch.get(content.charAt(i)) ;
			if(branch==null){
				return 0 ;
			}
		}
		
		if(branch.getStatus()<2){
			return 0 ;
		}
		
		return Integer.parseInt(branch.getParams()[0]) ;
	}
	
	public static void main(String[] args) {
		WoodInterface branch = FOREST ;
		String content = "福建@万利达"  ;
		for (int i = 0; i < content.length(); i++) {
			branch = branch.get(content.charAt(i)) ;
			if(branch==null){
				return ;
			}
		}
		System.out.println(Integer.parseInt(branch.getParams()[0]));
	}
}
