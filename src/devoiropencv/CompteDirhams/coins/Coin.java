package devoiropencv.CompteDirhams.coins;

import java.util.ArrayList;

public class Coin {
	

	private double radius;
	private double value;
	
	public Coin() {
		super();
	}
	public Coin(double rad) {
		System.out.println("      CONTROL > CTOR COIN WITH RADIUS OF: " + rad);
		this.radius = rad;
	}
	public Coin(double diam, double value) {
		this.radius = diam;
		this.value    = value;
	}
	
	public double getRadius() {
		return radius;
	}


	public void setRadius(double rad) {
		this.radius = rad;
	}
	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}
	
	public void setCoin(Coin coin)
	{
		this.radius = coin.radius;
		this.value    = coin.value   ;
	}

	@Override
	public String toString() {
		return "Coin [radius = " + radius + " ; value = "+ value + " ]";
	}
		public static void PrintCoins(ArrayList<Coin> coins) 
		{
			for(Coin coin:coins)
			{
				System.out.println(coin.toString());
			}
		}
	}
