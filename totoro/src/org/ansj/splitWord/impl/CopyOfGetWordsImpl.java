package org.ansj.splitWord.impl;

import static org.ansj.library.InitDictionary.base;
import static org.ansj.library.InitDictionary.check;
import static org.ansj.library.InitDictionary.natures;
import static org.ansj.library.InitDictionary.status;
import static org.ansj.library.InitDictionary.words;

import org.ansj.domain.Natures;
import org.ansj.library.InitDictionary;
import org.ansj.library.NatureEnum;
import org.ansj.splitWord.GetWords;
import org.ansj.util.WordAlert;

public class CopyOfGetWordsImpl implements GetWords {

	/**
	 * 记录上一次的偏移量
	 */
	private int tempOffe;
	/**
	 * offe : 当前词的偏移量
	 */
	public int offe;
	
	/**
	 * 构造方法，同时加载词典,传入词语相当于同时调用了setStr() ;
	 */
	public CopyOfGetWordsImpl(String str) {
		InitDictionary.init();
		setStr(str) ;
	}
	
	/**
	 * 构造方法，同时加载词典
	 */
	public CopyOfGetWordsImpl() {
		InitDictionary.init();
	}

	public void setStr(String str) {
		setStr(str.toCharArray()) ;
		
	}

	public void setStr(char[] chars) {
		this.chars = chars;
	}

	public char[] chars;
	private int charHashCode;
	private int start = 0;
	public int end = 0;
	private int baseValue = 0;
	private int checkValue = 0;
	private int tempBaseValue = 0;
	public int i = 0;
	private String str = null ;

	public String allWords() {
		for (; i < chars.length; i++) {
			charHashCode = chars[i];
			end++;
			switch (getStatement()) {
			case 0:
				if(baseValue==chars[i]){
					str = ""+chars[i] ;
					offe = i ;
					start  = ++i;
					end = 0;
					baseValue = 0;
					tempBaseValue = baseValue ;
					return str ;
				}else{
					i = start;
					start++;
					end = 0;
					baseValue = 0;
					break;
				}
			case 2:
				i++;
				offe = tempOffe + start;
				tempBaseValue = baseValue ; 
				return words[tempBaseValue];
			case 3:
				offe = tempOffe + start;
				start++;
				i = start;
				end = 0;
				tempBaseValue = baseValue;
				baseValue = 0;
				return words[tempBaseValue];
			case 4:
				while(status[chars[i++]]==4&&i<chars.length){
					end++ ;
				}
				if(i!=chars.length){
					end-- ;
				}
				offe = tempOffe+start ;
				str = WordAlert.alertEnglish(chars, start, end);
				start = start+end ;
				i = start ;
				end = 0 ;
				tempBaseValue = baseValue ;
				baseValue = 0;
				return str ;
			case 5:
				while(status[chars[i++]]==5&&i<chars.length){
					end++ ;
				}
				if(i!=chars.length){
					end-- ;
				}
				offe = tempOffe+start ;
				str = WordAlert.alertNumber(chars, start, end);
				start = start+end ;
				i = start ;
				end = 0 ;
				tempBaseValue = baseValue ;
				baseValue = 0;
				return str ;
			}
			
		}
		if (start++ != i) {
			i = start;
			return allWords();
		}
		tempOffe += chars.length;
		start = 0;
		end = 0;
		baseValue = 0;
		i = 0;
		return null;
	}

	/**
	 * 根据用户传入的c得到单词的状态. 0.代表这个字不在词典中 1.继续 2.是个词但是还可以继续 3.停止已经是个词了
	 * 
	 * @param c
	 * @return
	 */
	private int getStatement() {
//		if (charHashCode < 1) {
//			return 0;
//		}
		checkValue = baseValue;
		baseValue = base[checkValue] + charHashCode;
		if (check[baseValue] == checkValue || check[baseValue] == -1) {
			return status[baseValue];
		}
		return 0;
	}

	/**
	 * 重设分词
	 */
	public void reset() {
		checkValue = 0;
		tempOffe = 0;
	}

	/**
	 * 获得当前词的权重
	 * @return
	 */
	public int getWeight() {
		// TODO Auto-generated method stub
		if(natures[tempBaseValue] == null){
			return -100 ;
		}
		return natures[tempBaseValue].maxWeight;
	}

	/**
	 * 获得当前词的词性
	 * @return
	 */
	public Natures getNatures() {
		// TODO Auto-generated method stub
		if(natures[tempBaseValue] == null){
			return Natures.NULL ;
		}
		return natures[tempBaseValue];
	}
	
	public NatureEnum getMaxNature() {
		if(natures[tempBaseValue] == null){
			return Natures.NULL.maxNature ;
		}
		return natures[tempBaseValue].maxNature ;
	}

	public byte getStatus() {
		// TODO Auto-generated method stub
		return status[tempBaseValue];
	}
	
	

	

}