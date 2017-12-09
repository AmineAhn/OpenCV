/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devoiropencv;

import devoiropencv.ImageViewer;
import devoiropencv.DetectionContour.Px;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Amine
 */
public class util 
{
    public static Mat LireImg(String cheminFichier)
    {
        Mat Image = Imgcodecs.imread(cheminFichier);
	if(Image.dataAddr()==0)
        {
            return null;
	}
        else
        {
            return Image;
        }
    }
    
    public static Mat ImageEnGray(Mat Image)
    {
        Mat ImageGray = new Mat(Image.height(),Image.width(),CvType.CV_8UC1);
        Imgproc.cvtColor(Image, ImageGray, Imgproc.COLOR_BGR2GRAY);			
        return ImageGray;
    }
    public static Mat ImageEnBlur(Mat Image)
    {
        Mat ImageEnBlur = new Mat(Image.height(),Image.width(),CvType.CV_8UC1);
        Imgproc.medianBlur(Image, Image, 11);
        return ImageEnBlur;
    }
    
    public static void AfficherImage(Mat Image)
    {
        ImageViewer v = new ImageViewer();
        v.show(Image);
    }
    public static void AfficherImage(Mat Image, String titre)
    {
        ImageViewer v = new ImageViewer();
        v.show(Image, titre);
    }
    
    public static Mat TrouverContour(Mat Image, Mat ImageGray, int rayon)
    {
        Mat ImageAvecContours = Image;
        List<Px> corners = new ArrayList<>();
        double buffer[] = util.getBuffer(ImageGray);			
            
	for (int i = rayon; i < ImageGray.height()-rayon; i++) {
				
				for (int j = rayon; j  < ImageGray.width()-rayon; j++) {
					
					int pc = 0; // nbr pts fonc�s
					int pf = 0; // nbr pts clairs
					
					List<Px> circle = new ArrayList<>();
					
					int length = rayon*4+5; 
					char[] t = new char[length];
					
					t[0] = buffer[i*ImageGray.cols()+j] > 128 ? 'c' : 'f'; // c -> couleur clair, f -> couleur fonc�
					int i2 = i - rayon; // start from circle's top left
					int j2 = j - rayon/2;
										
					int k = 1;
					int lim = k+rayon;
					for (; k < lim; k++) {
						
						 t[k] = t[0] - buffer[i2*ImageGray.cols()+j2] > 20 ? 'f' : 'c';
						 
						 if(t[k] == 'c')
							 pc++;
						 else
							 pf++;
						 
						 circle.add(new Px(i2, j2));
						
						 j2++;
					}
					i2++;
					t[k] = t[0] - buffer[i2*ImageGray.cols()+j2] > 20 ? 'f' : 'c';
					circle.add(new Px(i2, j2));
					if(t[k] == 'c')
						 pc++;
					 else
						 pf++;
					i2++;
					j2++;
					k++;
					lim = k+rayon;
					for (; k < lim; k++) {
						t[k] = t[0] - buffer[i2*ImageGray.cols()+j2] > 20 ? 'f' : 'c';
						if(t[k] == 'c')
							 pc++;
						 else
							 pf++;
						 circle.add(new Px(i2, j2));
						
						 i2++;
					}
					
					j2--;
					t[k] = t[0] - buffer[i2*ImageGray.cols()+j2] > 20 ? 'f' : 'c';
					circle.add(new Px(i2, j2));
					if(t[k] == 'c')
						 pc++;
					 else
						 pf++;
					i2++;
					j2--;
					k++;
					lim = k+rayon;
					for (; k < lim; k++) {
						
						 t[k] = t[0] - buffer[i2*ImageGray.cols()+j2] > 20 ? 'f' : 'c';
						 circle.add(new Px(i2, j2));
						 
						 if(t[k] == 'c')
							 pc++;
						 else
							 pf++;
						
						 j2--;
					}
					
					i2--;
					t[k] = t[0] - buffer[i2*ImageGray.cols()+j2] > 20 ? 'f' : 'c';
					circle.add(new Px(i2, j2));
					if(t[k] == 'c')
						 pc++;
					 else
						 pf++;
					i2--;
					j2--;
					k++;
					lim = k+rayon;
					for (; k < lim; k++) {
						
						 t[k] = t[0] - buffer[i2*ImageGray.cols()+j2] > 20 ? 'f' : 'c';
						 circle.add(new Px(i2, j2));
						 
						 if(t[k] == 'c')
							 pc++;
						 else
							 pf++;
						
						 i2--;
					}
					j2++;
					t[k] = t[0] - buffer[i2*ImageGray.cols()+j2] > 20 ? 'f' : 'c';
					circle.add(new Px(i2, j2));
					if(t[k] == 'c')
						 pc++;
					 else
						 pf++;
					k++;
					
					
					int nbrChangements = 0;
					for (int l = 2; l < length; l++) {
						if(t[l] != t[l-1])
							nbrChangements++;
					}
					if(nbrChangements <= 2)
					{
						if((pc >= ((length-1)*0.75)-1 && t[0] == 'f') || (pf >= ((length-1)*0.75)-1 && t[0] == 'c'))
						{
							corners.add(new Px(i, j));
							double[] cornerColor =  t[0] == 'f' ? new double[] {0, 0, 255} : new double[] {255, 0, 0};
							ImageAvecContours.put(i, j, cornerColor); // change corner's color to red or blue
							for(Px p : circle)
							{
								ImageAvecContours.put(p.i, p.j, cornerColor);
							}
						}
						
					}
					
				}
				
			}
			
                return ImageAvecContours;
    }
    
    
    
    
    
    
    public static double[] getBuffer(Mat Image)
    {
        int totalBytes = (int)(Image.total()*Image.elemSize());
        double[] buffer = new double[totalBytes];
        for (int i = 0; i < Image.height(); i++) 
        {
            for (int j = 0; j < Image.width(); j++) 
            {
                buffer[i*Image.cols()+j] = Image.get(i, j)[0];
            }
	}
        return buffer;			
    }

    

    public static Mat SeuillageImage(Mat img, double seuil, double valeurMax) 
    {
        Mat bnw = new Mat();
        Imgproc.threshold(img, bnw, seuil, valeurMax, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);
	return bnw;
    }

    public static Mat ImageAvecHoughCircles(Mat ImageEnGray, double dp, double minDist, double param1, double param2, int minRayon, int maxRayon) {
        Mat cercles = new Mat();
        Imgproc.HoughCircles(ImageEnGray, cercles, Imgproc.HOUGH_GRADIENT, dp,minDist,param1, param2, minRayon, maxRayon); 
        return cercles;
    }
    
    
}
