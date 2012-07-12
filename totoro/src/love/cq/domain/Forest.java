package love.cq.domain;


public class Forest implements WoodInterface {
	WoodInterface[] chars = new WoodInterface[65536];

	public WoodInterface add(WoodInterface branch) {
		WoodInterface temp = chars[branch.getC()] ;
		if(temp==null){
			chars[branch.getC()] = branch;
		}else{
			switch (temp.getStatus()) {
			case 1:
				if(branch.getStatus()==2){
					temp.setStatus(2) ;
				}
				if(branch.getStatus()==3){
					temp.setStatus(2) ;
				}
				break;
			case 2:
				branch.setStatus(2) ;
			case 3:
				if(branch.getStatus()==2){
					temp.setStatus(2) ;
				}
				if(branch.getStatus()==1){
					temp.setStatus(2) ;
				}
			}
			temp.setNature(branch.getNature()) ;
		}
		return chars[branch.getC()] ;
	}

	public boolean contains(char c) {
		if (chars[c] == null)
			return false;
		else
			return true;
	}

	public WoodInterface get(char c) {
		// TODO Auto-generated method stub
		return chars[c];
	}

	public static void main(String[] args) {
		Forest f = new Forest() ;
		System.out.println(f.chars[19968]);
	}

	public int compareTo(char c) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean equals(char c) {
		// TODO Auto-generated method stub
		return false;
	}

	public char getC() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNature() {
		// TODO Auto-generated method stub
		return 0;
	}

	public byte getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setNature(int nature) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(int status) {
		// TODO Auto-generated method stub
		
	}
	public int getSize(){
		return chars.length ; 
	}
}
