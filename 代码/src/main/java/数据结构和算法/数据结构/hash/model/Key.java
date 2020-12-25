package 数据结构和算法.数据结构.hash.model;

public class Key {
	protected int value;

	public Key(int value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		return value / 10;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != getClass()) return false;
		return ((Key) obj).value == value;
	}
	
	@Override
	public String toString() {
		return  String.valueOf(value);
	}
}
