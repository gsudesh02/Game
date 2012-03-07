package entity;


public class Job {
	private String name;
	private String description;
	
	private int experiencePoint;
	
	private Skill[] jobSkills;

	//Load a state of a Skill Tree
	public void UpdateSkillSheet(boolean[] skillSheetTree){
		for (int i = 0; i < skillSheetTree.length; i++) {
			jobSkills[i].setBought(skillSheetTree[i]);
		}			
	}	
	
	public void getCharJobState(String XMLjobState){
		setExperiencePoint(Integer.valueOf(GetXMLElement(XMLjobState,"expPoints")));
		int nbSkills=(Integer.valueOf(GetXMLElement(XMLjobState,"nbSkills")));
		
		String XMLskillPoints =GetXMLElement(XMLjobState,"Skills");
		boolean[] jobSkillPoints = new boolean[nbSkills];
		
		
		for (int i = 0; i < nbSkills; i++) {
			if(GetXMLElement(XMLskillPoints,String.valueOf(i)).equals("true")){
				jobSkillPoints[i]=true;
			}
			else{
				jobSkillPoints[i]=false;
			}
		}
		UpdateSkillSheet(jobSkillPoints);
	}
	
	public String saveToXML(){
		String s = new String();
		s = "";

		s += "<expPoints>";
		s += this.getExperiencePoint();
		s += "</expPoints>";

		s += "<nbSkills>";
		s += jobSkills.length;
		s += "</nbSkills>";
		
		s += "<Skills>";
		for (int i = 0; i < jobSkills.length; i++) {
			s += "<"+i+">";
			jobSkills[i].isBought();
			s += "</"+i+">";
		}
		s += "</nbSkills>";
		
		return s;
	}
	
	private String GetXMLElement(String XMLString, String balise) {
		int len = ("<" + balise + ">").length();
		int posDeb = XMLString.indexOf("<" + balise + ">");
		int posFin = XMLString.indexOf("</" + balise + ">");

		return XMLString.substring(posDeb + len, posFin);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExperiencePoint() {
		return experiencePoint;
	}

	public void setExperiencePoint(int experiencePoint) {
		this.experiencePoint = experiencePoint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
	public class Warrior extends Job {
		
		public Warrior(){
			setName("Warrior");
			setDescription("tapertapertaper");
			setExperiencePoint(0);
			createSkillTree();
		}
			
		public void createSkillTree(){
			ForceAmelio1 forceAmelio1 = new ForceAmelio1();
			ForceAmelio2 forceAmelio2 = new ForceAmelio2();
			ForceAmelio3 forceAmelio3 = new ForceAmelio3();
			ForceAmelio4 forceAmelio4 = new ForceAmelio4();
			
			forceAmelio2.setDependance(new boolean[] {forceAmelio1.isBought()});
			forceAmelio3.setDependance(new boolean[] {forceAmelio1.isBought(),forceAmelio2.isBought()});
			forceAmelio4.setDependance(new boolean[] {forceAmelio1.isBought(),forceAmelio2.isBought(),forceAmelio3.isBought()});
			
			jobSkills= new Skill[] {forceAmelio1,forceAmelio2,forceAmelio3,forceAmelio4};					
		}
		
		//Warrior Skills
		public class ForceAmelio1 extends Skill {
			
			public ForceAmelio1(){
				super("Force +1", "augmente la force de 1", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				character.setAttackPower(character.getAttackPower()+1);
			}
		}		
		public class ForceAmelio2 extends Skill {
			public ForceAmelio2(){
				super("Force +2", "augmente la force de 2", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				character.setAttackPower(character.getAttackPower()+2);
			}
		}
		public class ForceAmelio3 extends Skill {
			public ForceAmelio3(){
				super("Force +5", "augmente la force de 5", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				character.setAttackPower(character.getAttackPower()+5);
			}
		}
		public class ForceAmelio4 extends Skill {
			public ForceAmelio4(){
				super("Force +12", "augmente la force de 12", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				character.setAttackPower(character.getAttackPower()+12);
			}
		}
		
		public class LifeAmelio1 extends Skill {
			public LifeAmelio1(){
				super("Force +12 ", "augmente la force de 12", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				character.setAttackPower(character.getMaxLifePoints()+20);
			}
		}
		public class LifeAmelio2 extends Skill {
			public LifeAmelio2(){
				super("Force +12 ", "augmente la force de 12", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				character.setAttackPower(character.getMaxLifePoints()+80);
			}
		}
		public class LifeAmelio3 extends Skill {
			public LifeAmelio3(){
				super("Force +12 ", "augmente la force de 12", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				character.setAttackPower(character.getMaxLifePoints()+200);
			}
		}
		public class LifeAmelio4 extends Skill {
			public LifeAmelio4(){
				super("Force +12 ", "augmente la force de 12", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				character.setAttackPower(character.getMaxLifePoints()+300);
			}
		}
		
		public class Dash extends Skill {
			public Dash(){
				super("Force +12 ", "augmente la force de 12", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				//set Dash available a true
			}
		}
		public class CriticalStrike extends Skill {
			public CriticalStrike(){
				super("Force +12 ", "augmente la force de 12", 100, skillType.amelioration);
			}
			@Override
			public void process(Character character) {
				//set CriticalStrike available a true
			}
		}
		
	}

	public class Rogue extends Job {

	}

	public class Mage extends Job {

	}

	public class Priest extends Job {

	}
}
