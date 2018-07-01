import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.text.*;
class Arbol
{
    static double cx2=0, cy2=0;
    public static void multiplica_complejo(double er, double ei, double er2, double ei2)
    {
        cx2=(-ei * ei2) + (er * er2);
        cy2=(ei * er2) + (ei2 * er);
    }

    public static void main(String args[])
    {
        double        intervalo1=-3, intervalo2=5, intervaloi1=0, intervaloi2=5, p1=0.05, p2=0.3, p3=0.3, p4=0.35;
        double        f=3.1416 / 180, r, s, h, k, A, B, m_inter, m_interi, suma, sumai;
        int           pixeles=1000;
        BufferedImage img    = new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
        int           col[]  ={
            0xCC0000, 0xFF0000, 0xFF3333, 0xCC6600, 0xFF8000, 0xFF9933, 0xCCCC00, 0xFFFF00, 0xFFFF33, 0x66CC00, 0x80FF00, 0x99FF33, 0x00CC00, 0x00FF00, 0x33FF33, 0x00CC66, 0x00FF80, 0x33FF99, 0x00CCCC, 0x00FFFF, 0x33FFFF, 0x0066CC, 0x0080FF, 0x3399FF, 0x0000CC, 0x0000FF, 0x3333FF, 0x6600CC, 0x7F00FF, 0x9933FF, 0XCC00CC, 0XFF00FF, 0xFF33FF, 0XCC0066, 0XFF007F, 0xFF3399, 0X606060, 0x808080, 0xA0A0A0
        };
        double        x=0, y=0, l=0, n=0;
        int           con=0, cx, cy;
        double        d = 0;
        suma    =(-intervalo1 + intervalo2) / pixeles;
        m_inter =(int)((pixeles * (intervalo2)) / (-intervalo1 + intervalo2));
        sumai   =(intervaloi1 + intervaloi2) / pixeles;
        m_interi=(int)((pixeles) / (intervaloi1 + intervaloi2));
        for (n=0; n < 50000; n++)
        {
            d = Math.random();
            if (d < p1)
            {
                r=0.47;
                s=0.12;
                h=0.77;
                k=0.77;
                A=80;
                B=-50;
            }
            else if (d > p1 && d < p1 + p2)
            {
                r=0.49;
                s=0.66;
                h=0.42;
                k=0.75;
                A=60.5;
                B=47.8;
            }
            else if (d > p1 && d < p1 + p2 + p3)
            {
                r=0.53;
                s=0.55;
                h=0.9;
                k=1.6;
                A=-20.6;
                B=-48.9;
            }
            else
            {
                r=0.53;
                s=0.76;
                h=0.6;
                k=0.1;
                A=-10;
                B=-2;
            }
            x =(r * Math.cos(A * f) * x) - (s * Math.sin(B * f) * y) + h;
            y =(r * Math.sin(A * f) * x) + (s * Math.cos(B * f) * y) + k;
            cx=(int)((x / suma) + m_inter);
            cy=(int)((y / sumai) + m_interi);
            //System.out.println("d:"+d+"x:"+x+"y:"+y+"cx:"+cx+"cy:"+cy);
            if ((cx < 1000 && cy < 1000) && (cx > 0 && cy > 0))
                img.setRGB(cx, cy, col[(int)(d * 39)]);
        }

        File fi = new File("arbol.png");
        try {
            ImageIO.write(img, "PNG", fi);
        }
        catch (IOException e) {
            System.err.println("image not saved.");
        }
    }
}
