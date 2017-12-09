package devoiropencv.CompteDirhams.rapport;

import java.util.ArrayList;

import devoiropencv.CompteDirhams.coins.Coin	  ;
import devoiropencv.CompteDirhams.coins.CoinsGen ;

public class Rapport 
{
	private Coin coin1	  ;
	private Coin coin2    ;
	private double rapport;
	
	public Coin getCoin1() {
		return coin1;
	}
	
	public Coin getCoin2() {
		return coin2;
	}
	
	public double getRapport() {
		return rapport;
	}
	
	public void setCoin1(Coin coin1) {
		this.coin1 = coin1;
	}
	
	public void setCoin2(Coin coin2) {
		this.coin2 = coin2;
	}
	
	public void setRapport(double rapport) {
		this.rapport = rapport;
	}
	
	
	public Rapport()
	{
		super();
	}
	public Rapport(Coin c1, Coin c2)
	{
		double diam1 = c1.getRadius();
		double diam2 = c2.getRadius();
		double r     = diam1/diam2   ;     // WATCHOUT FROM X/0 EXCEPTION
		ArrayList<Rapport> ALIR = Rapport.GetBaseRapports();
		if(r == 1)
		{
			this.coin1   = null;
			this.coin2 	 = null;
			this.rapport = 1;
		}
		else
		{
			if(r > 1)
			{
				r = 1/r;
				CheckForCoins(ALIR, c1, c2, r);
			}
			else
			{
				CheckForCoins(ALIR, c1, c2, r);
			}
		}
	}
	
	public static void CheckForCoins(ArrayList<Rapport> ALIR, Coin c1, Coin c2, double rapport)
	{
		System.out.println("* COMPARE RAPPROT BTWN C1-RADIUS: " + c1.getRadius() + " AND C2-RADIUS: " + c2.getRadius() + " \n\tWITH RAPPORT OF: " + rapport );
		for(int i = 0 ; i < ALIR.size(); i++)
		{
			Rapport aRapport = ALIR.get(i);
			if( (aRapport.getRapport() == rapport) )
			{
				//System.out.println(" TEST > ------------------ ");
				System.out.println("	>COMPARING RAPPORT: " + rapport );
				System.out.println(" 	>TO\n 	COIN["+aRapport.getCoin1().toString()+"]\n\tAND\n 	COIN["+(aRapport.getCoin2().toString())+"] \n	WITH RAPPORT: " + aRapport.getRapport());
				System.out.println(" CONTROL > EQUALITY :) ");
				c1.setValue(aRapport.getCoin1().getValue());
				c2.setValue(aRapport.getCoin2().getValue());
				break;
			}
			/*else 
			{
				System.out.println(" CONTROL > FAILURE :(");
			}*/
		}
		System.out.println(" ************** ******************************* " + " ************* " + " ********************************** ");
	}
	
	public static ArrayList<Rapport> GetBaseRapports()
	{
		ArrayList<Rapport> ALIR = new ArrayList<>();
		ArrayList<Coin>    ALIC = CoinsGen.GenerateBaseCoins();
		
		for(int i = 0 ; i < ALIC.size()-1; i++)
		{ 
			for(int j = i+1; j < ALIC.size(); j++)
			{
				
				Coin c1 = ALIC.get(i)          					   ;
				Coin c2 = ALIC.get(j)        					   ;
				Rapport rapport = new Rapport()					   ;
				rapport.coin1 = c1								   ;
				rapport.coin2 = c2								   ;
				double r = c1.getRadius()/c2.getRadius()           ;
				if( r > 1)
				{
					r = 1/r                                        ;
				}
				rapport.setRapport(r)							   ;
				//System.out.println("RAPPORT["+i+"] BTWN COIN["+i+"] AND COIN["+(j)+"]: "+rapport.getRapport());
				ALIR.add(rapport)								   ;
			}
		}
		return ALIR;
	}
	
	public static double[][] GetMatRapports()
	{
		
		double[][] ALIR = new double[][] {};
		ArrayList<Coin>    ALIC = CoinsGen.GenerateBaseCoins();
		
		for(int i = 0 ; i < ALIC.size(); i++)
		{ 
			Coin c1 = ALIC.get(i)          					   ;
			for(int j = 0; j < ALIC.size(); j++ )
			{
				Coin c2 = ALIC.get(j)        					   ;
				Rapport rapport = new Rapport()					   ;
				rapport.coin1 = c1								   ;
				rapport.coin2 = c2								   ;
				double r = c1.getRadius()/c2.getRadius()        ;
				System.out.println(" VALUE IS : " + r)             ;
				if( r > 1)
				{
					r= 1/r                       ;
				}
				ALIR[i][j] = r;
				System.out.println("RAPPORT BTWN COIN["+i+"] AND COIN["+j+"]: "+ALIR[i][j]);
			}
			
		}
		return ALIR;
	}
	
	public static Rapport getClosestRapportTo(double r)
	{
		ArrayList<Rapport> ALIR = Rapport.GetBaseRapports();
		Rapport rapportToReturn = null					   ;
		double minValue          = Double.MAX_VALUE		   ;
		for(int i = 0 ; i < (ALIR.size()-1) ; i++)
		{
			Rapport r1 = ALIR.get(i);
			double det1 = Math.abs( r1.getRapport() - r);
			for(int j = 1; j < ALIR.size(); j++ )
			{
				Rapport r2 = ALIR.get(j);
				double det2 = Math.abs( r2.getRapport() - r);
				if( det1 < det2) // SO RAPPORT 1 IS THE CLOSEST 
				{
					if( minValue > det1) // IS IT BETTER THAN THE OLD ONE ?
					{
						minValue = det1;
						rapportToReturn = r1;
					}
				}
				else // SO ITS RAPPORT 2 IS THE CLOSEST (OR THE SAME)
				{
					if( minValue > det2) // IS IT BETTER THAN THE OLD ONE ? 
					{
						minValue = det2;
						rapportToReturn = r2;
					}
				}
			}
		}
		return rapportToReturn;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Rapport [rapport] = " + this.getRapport() + " | COIN1 = "+ this.getCoin1().toString() + " <=> COIN2 = " + this.getCoin2().toString() ;
	}
	public static void PrintRapports(ArrayList<Rapport> rapports)
	{
		for(Rapport rapport : rapports)
		{
			System.out.println(rapport.toString());
		}
	}
	public static void CompareRapportsInList(ArrayList<Rapport> LR)
	{
		for(int i = 0; i < LR.size(); i++)
		{
			Rapport RI = LR.get(i);
			for(int j = i+1 ; j < LR.size(); j++)
			{
				Rapport RJ = LR.get(j);
				if(RI.getRapport() == RJ.getRapport())
				{
					System.out.println(RI.toString());
					System.out.println(RJ.toString());
					System.out.println(" SHIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT " + (i)  +" - "+ j);
				}
			}
		}
		System.out.println("DONE");
	}
}
