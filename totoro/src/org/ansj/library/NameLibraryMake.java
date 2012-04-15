package org.ansj.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.ansj.util.IOUtil;
import org.ansj.util.MyStaticValue;

public class NameLibraryMake {
	public static void main(String[] args) throws IOException {
		BufferedReader br = IOUtil.getReader(MyStaticValue.rb.getString("familyName"), "UTF-8") ;
		String temp = null ;
		Integer value = 0 ;
		TreeMap<Character,Integer> map = new TreeMap<Character,Integer>() ;
		while((temp=br.readLine())!=null){
			if(temp.length()<1)continue ;
			char[] chars = temp.trim().toCharArray() ;
			for (int i = 0; i < chars.length; i++) {
				if((value=map.get(chars[i]))!=null){
					map.put(chars[i], ++value) ;
				}else{
					map.put(chars[i], 1) ;
				}
			}
		}
		
		Set<Entry<Character, Integer>> entrySet = map.entrySet() ;
		Iterator<Entry<Character, Integer>> it = entrySet.iterator() ;
		Entry<Character, Integer> entry = null ;
		StringBuilder sb = new StringBuilder() ;
		while(it.hasNext()){
			entry = it.next() ;
			sb.append(entry.getKey()+"	"+entry.getValue()) ;
			sb.append("\n") ;
		}
		
		IOUtil.Writer("d:/aa.txt", "UTF-8", sb.toString()) ;
	}
}
