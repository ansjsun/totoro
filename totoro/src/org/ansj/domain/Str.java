package org.ansj.domain;

public class Str implements Comparable<Str> {
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	private int length;
	// 1未完结2完结但是还有可能有3完结了以后也没有了4英語5數字
	private int statu;
	private String nature;
	
	private Integer weight ;

	public int getStatu() {
		return statu;
	}

	public void setStatu(int statu) {
		this.statu = statu;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.str.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
//		return str + "	" + statu + "	" + nature;
		return str + "	" + statu + "	" + nature+ "	"+weight;
	}

	public Str(String str) {
		this.str = str;
		this.length = str.length();
	}

	public int length() {
		return this.length;
	}

	public char[] getchars() {
		return str.toCharArray();
	}

	public int compareTo(Str o) {
		// TODO Auto-generated method stub
		char[] c1 = this.getchars();
		char[] c2 = o.getchars();
		if (this.length() > o.length()) {
			return 1;
		}
		if (this.length() < o.length()) {
			return -1;
		}
		for (int i = 0; i < this.length; i++) {
			if (c1[i] > c2[i]) {
				return 1;
			}
			if (c1[i] < c2[i]) {
				return -1;
			}
		}
		return 0;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	
}
