package love.cq.domain;

import java.util.Arrays;

import org.ansj.util.AnsjArrays;

public class Branch implements WoodInterface {
	/**
	 * status 此字的状态1，继续  2，是个词语但是还可以继续 ,3确定
	 * nature 词语性质
	 */
	WoodInterface[] branches = new WoodInterface[0];
	private char c;
	// 状态
	private byte status = 1;
	// 索引
	private short index = -1;
	// 词性
	private int nature = 0;
	// 单独查找出来的对象
	WoodInterface branch = null;

	public WoodInterface add(WoodInterface branch) {
		if ((this.branch=this.get(branch.getC()))!=null) {
			switch (branch.getStatus()) {
			case 1:
				if(this.branch.getStatus()==2){
					this.branch.setStatus(2) ;
				}
				if(this.branch.getStatus()==3){
					this.branch.setStatus(2) ;
				}
				break;
			case 2:
				this.branch.setStatus(2) ;
			case 3:
				if(this.branch.getStatus()==2){
					this.branch.setStatus(2) ;
				}
				if(this.branch.getStatus()==1){
					this.branch.setStatus(2) ;
				}
			}
			if(this.branch.getNature()>0&&this.branch.getNature()!=branch.getNature()){
				this.branch.setNature(branch.getNature()*this.branch.getNature())   ;
			}else{
				this.branch.setNature(branch.getNature())   ;
			}
			return this.branch;
		}
		index++;
		if ((index + 1) > branches.length) {
			branches = Arrays.copyOf(branches, index + 1);
		}
		branches[index] = branch;
		AnsjArrays.sort(branches);
		return branch;
	}
	
	

	public Branch(char c, int status, int nature) {
		this.c = c;
		this.status = (byte) status;
		this.nature = nature;
	}

	int i = 0;

	public WoodInterface get(char c) {
		int i = AnsjArrays.binarySearch(branches, c);
		if (i > -1) {
			return branches[i];
		}
		return null;
	}

	public boolean contains(char c) {
		if (AnsjArrays.binarySearch(branches, c) > -1) {
			return true;
		} else {
			return false;
		}
	}

	public int compareTo(char c) {
		if (this.c > c) {
			return 1;
		}else if (this.c < c) {
			return -1;
		}else
		return 0 ;
	}

	public boolean equals(char c) {
		if (this.c == c) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return c;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = (byte) status;
	}

	public char getC() {
		return this.c;
	}

	public int getNature() {
		return nature;
	}

	public void setNature(int nature) {
		this.nature = nature;
	}

}
