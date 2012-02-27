
public class Map {

	private Tile[][] map;
	private int length;
	private int width;
	private String name;
	
	public Map(int length, int width, String name)
	{
		this.setLength(length);
		this.setWidth(width);
		this.setName(name);
		
		map=new Tile[length][width];
		for(int i=0;i<length;i++)
		{
			for(int j=0;j<width;j++)
			{
				map[i][j]=new Tile();
			}
		}		
	}
	
	public Map(String XMLString)
	{
		int length;
		int width;
		String name;
		
		length=Integer.parseInt(GetXMLElement(XMLString,"Length"));
		width=Integer.parseInt(GetXMLElement(XMLString,"Width"));
		name=GetXMLElement(XMLString,"Name");
		
		this.setLength(length);
		this.setWidth(width);
		this.setName(name);
		
		map=new Tile[length][width];
		
		for(int i=0;i<length;i++)
		{
			for(int j=0;j<width;j++)
			{
				map[i][j]=new Tile(GetXMLElement(XMLString,"Tile"+i+j));
			}
		}		
	}
	
	public void EditTile(int i,int j,Tile tile){
		map[i][j].setPosX(tile.getPosX());
		map[i][j].setPosY(tile.getPosY());
		map[i][j].setHeightSE(tile.getHeightSE());
		map[i][j].setHeightNE(tile.getHeightNE());
		map[i][j].setHeightNO(tile.getHeightNO());
		map[i][j].setHeightSO(tile.getHeightSO());		
		map[i][j].setTexture(tile.getTexture());
		map[i][j].setType(tile.getType());
		map[i][j].setDecoration(tile.getDecoration());				
	}
	
	private String GetXMLElement(String XMLString,String balise)
	{		
		int len=("<"+balise+">").length();
		int posDeb=XMLString.indexOf("<"+balise+">");
		int posFin=XMLString.indexOf("</"+balise+">");
		
		return XMLString.substring(posDeb+len,posFin);
	}
	
	public String toXMLString()
	{
		String s=new String();
		s="";
		s+="<Map>";
		
		s+="<Length>";
		s+=this.getLength();
		s+="</Length>";
		
		s+="<Width>";
		s+=this.getWidth();
		s+="</Width>";
		
		s+="<Name>";
		s+=this.getName();
		s+="</Name>";
		
		for(int i=0;i<length;i++)
		{
			for(int j=0;j<width;j++)
			{
				s+="<Tile"+i+j+">";
				s+=map[i][j].toXMLString();
				s+="</Tile"+i+j+">";
			}
		}			
		s+="</Map>";
		
		return s;
	}
	
	
	
	public Tile getTile(int i,int j){
		return map[i][j];
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public static void main(String[] argv) {
		Map m=new Map(2, 5, "blabla");
		String s=m.toXMLString();
		System.out.println(s);
		Map m2=new Map(s);
		String s2=m2.toXMLString();
		System.out.println(s2);
	}
	
}
