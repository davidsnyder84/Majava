package majava.control.testcode;

import majava.tiles.Janpai;

public class TestingJanpai {
	
	
	
	
	public static void main(String[] args) {
//		nextTests();
//		canchitests();
		retrievefromIDtest();
		
		
		
	}
	
	
	public static void retrievefromIDtest(){
		for (int i=1; i<=34; i++)
			println(i + "\t" + Janpai.retrieveTile(i));
	}
	
	
	
	public static void canchitests(){
		for (int i=1; i<=34; i++)
			chistest(Janpai.retrieveTile(i), i);
	}
	private static void chistest(Janpai j, int i){
		print(i + "\t" + j.canCompleteChiL()+"\t");
		print(j.canCompleteChiM()+"\t");
		println(j.canCompleteChiH()+"\t");
	}
	
	
	
	public static void nextTests(){
		nextTest(Janpai.M1); nextTest(Janpai.P4); nextTest(Janpai.S9); nextTest(Janpai.WN); nextTest(Janpai.DR);
	}
	public static void nextTest(Janpai jan){
		for (int i = 0; i < 12; i++){
			print(jan + " ");
			jan = jan.nextTile();
		}
		println();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void print(String s){System.out.print(s);}
	public static void print(Object o){print(o.toString());}
	public static void println(String s){System.out.println(s);}
	public static void println(Object o){println(o.toString());}
	public static void println(){println("");}
}
