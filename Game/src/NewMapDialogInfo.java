public class NewMapDialogInfo {
	private String name;
	private int length;
	private int width;
	
	public NewMapDialogInfo(){}
	public NewMapDialogInfo(String name, int length,int width){
		this.setName(name);
		this.setLength(length);
		this.setWidth(width);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}	
}
