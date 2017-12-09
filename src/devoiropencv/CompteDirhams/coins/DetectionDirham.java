package devoiropencv.CompteDirhams.coins;

import devoiropencv.CompteDirhams.rapport.Rapport;
import devoiropencv.ImageViewer;
import devoiropencv.util;
import java.util.ArrayList;
import java.util.Arrays;

import org.opencv.core.Core			 ;
import org.opencv.core.Mat			 ;		
import org.opencv.core.Point		 ;
import org.opencv.core.Scalar		 ;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc	 ;


public class DetectionDirham {
	static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	 
	public static Mat RetrouverCercles(Mat Image)
        {
            Mat img = Image;
            if(img == null)
            {
                return null;
            }
            else
            {
            Mat ImageEnGray = util.ImageEnGray(img);
            Mat ImageEnBlur = util.ImageEnBlur(ImageEnGray);
            double seuille = 0;
            double valeurMax = 100; 
            Mat bnw  = util.SeuillageImage(img, seuille, valeurMax);
            double dp = 1.0;
            double minDist = (double)bnw.rows()/16; // change this value to detect circles with different distances to each other
            double param1  = 100.0;
            double param2  = 30.0;
            int minRayon= 1;
            int maxRayon= 10000;
                // change the last two parameters
                // (min_radius & max_radius) to detect larger circles
            Mat cercles = util.ImageAvecHoughCircles(ImageEnGray, dp, minDist, param1, param2, minRayon, maxRayon);
            return cercles;
            }
            
        }

	public static ArrayList<Coin> detecterDirham(Mat Image)
	{
            Mat ImageAvecCercles    = Image;
            Mat circles = DetectionDirham.RetrouverCercles(Image);
            ArrayList<Coin> coins = new ArrayList<>();
            for(int i=0; i<circles.cols(); i++)
            {
                double[] c = circles.get(0,i);
                //System.out.println(" ADDING COIN / CIRCLE["+i+"] WITH RADIUS OF: " + c[2]);
		coins.add(new Coin(c[2]));
                Point center = new Point(Math.round(c[0]), Math.round(c[1]));
                Imgproc.circle(ImageAvecCercles, center, 1, new Scalar(0,100,100), 3, 8, 0 );
		int radius = (int) Math.round(c[2]);
		Imgproc.circle(ImageAvecCercles, center, radius , new Scalar(255,0,255), 3, 8, 0 );
            }
            //util.AfficherImage(ImageAvecCercles, " DIRHAMS DETECTÉS ");
            return coins;
	}
        
        public static Mat dessinerDirham(Mat Image)
	{
            Mat ImageAvecCercles    = Image;
            Mat circles = DetectionDirham.RetrouverCercles(Image);
            for(int i=0; i<circles.cols(); i++)
            {
                double[] c = circles.get(0,i);
                Point center = new Point(Math.round(c[0]), Math.round(c[1]));
                Imgproc.circle(ImageAvecCercles, center, 1, new Scalar(0,100,100), 3, 8, 0 );
		int radius = (int) Math.round(c[2]);
		Imgproc.circle(ImageAvecCercles, center, radius , new Scalar(255,0,255), 3, 8, 0 );
            }
            //util.AfficherImage(ImageAvecCercles, " DIRHAMS DETECTÉS ");
            return ImageAvecCercles;
	}

    public static void RetrouverValeur(ArrayList<Coin> COINS, ArrayList<Rapport> ALIR)
    {
        for(int i = 0 ; i < COINS.size()-1; i++) 
	{ 
            for(int j = i+1; j < COINS.size();j++)
            {
                Coin c1 = COINS.get(i);
                Coin c2 = COINS.get(j);
                double rapport  = c1.getRadius()/c2.getRadius()          ;
                if(rapport > 1)
                {
                    rapport = 1/rapport;
                }
                Rapport RAPPORT = Rapport.getClosestRapportTo(rapport)   ;
                Rapport.CheckForCoins(ALIR, c1, c2, RAPPORT.getRapport());
            }
	}
    }

    public static double CalculerTotal(ArrayList<Coin> COINS) 
    {   
        double total = 0;
        for(int i = 0 ; i < COINS.size(); i++) 
	{
            Coin c1 = COINS.get(i);
            System.out.println(c1);
            total += c1.getValue();
        }
        return total;
    }


}
