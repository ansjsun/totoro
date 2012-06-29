package love.cq.domain;

import org.ansj.library.NatureEnum;

public class Segement {
	private String value;
	private NatureEnum natureEnum;
	private int offe;

	public Segement(String value, int offe) {
		this.value = value;
		this.offe = offe;
	}

	public Segement(String value, int offe, NatureEnum natureEnum) {
		this.value = value;
		this.offe = offe;
		this.natureEnum = natureEnum;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public NatureEnum getNatureEnum() {
		return natureEnum;
	}

	public void setNatureEnum(NatureEnum natureEnum) {
		this.natureEnum = natureEnum;
	}

	public int getOffe() {
		return offe;
	}

	public void setOffe(int offe) {
		this.offe = offe;
	}
	
	public String toString(){
		return this.value+":"+this.offe+"/"+this.natureEnum ;
	}

}
