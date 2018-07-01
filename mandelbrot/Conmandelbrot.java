import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.text.*;
class Conmandelbrot
{
    static double cx2=0, cy2=0;
    public static void multiplica_complejo(double er, double ei, double er2, double ei2)
    {
        cx2=(-ei * ei2) + (er * er2);
        cy2=(ei * er2) + (ei2 * er);
    }

    public static void main(String args[])
    {
        double        intervalo1=0.8, intervalo2=-2.4, intervaloi1=1.25, intervaloi2=-1.25, s;
        int           pixeles=1000;
        double        suma=(intervalo1 - intervalo2) / pixeles;
        double        sumai=(intervaloi1 - intervaloi2) / pixeles;
        int           m_inter=(int)(pixeles * Math.abs(intervalo2) / (intervalo1 - intervalo2));
        int           m_interi=(int)(pixeles * Math.abs(intervaloi2) / (intervaloi1 - intervaloi2));
        double        cx=0, cy=0, cx3=0, cy3=0, cxa=0, cya=0, j, k;
        double        cr=0, ci=-1;
        double        r=0, error2=1;
        int           pasar=0, i=0, con=0;
        BufferedImage img  = new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
        int           col[]={
            0xCC0000, 0xFF0000, 0xFF3333, 0xCC6600, 0xFF8000, 0xFF9933, 0xCCCC00, 0xFFFF00, 0xFFFF33, 0x66CC00, 0x80FF00, 0x99FF33, 0x00CC00, 0x00FF00, 0x33FF33, 0x00CC66, 0x00FF80, 0x33FF99, 0x00CCCC, 0x00FFFF, 0x33FFFF, 0x0066CC, 0x0080FF, 0x3399FF, 0x0000CC, 0x0000FF, 0x3333FF, 0x6600CC, 0x7F00FF, 0x9933FF, 0XCC00CC, 0XFF00FF, 0xFF33FF, 0XCC0066, 0XFF007F, 0xFF3399, 0X606060, 0x808080, 0xA0A0A0
        };
        int           x=0, y=0, l=0;
        for (s=1; s < 30; s++)
        {
            img= new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            //Convertimos el numero
            String st=nf.format(s);
            //lo vuelves a convertir a double
            suma    =(intervalo1 - intervalo2) / pixeles;
            sumai   =(intervaloi1 - intervaloi2) / pixeles;
            m_inter =(int)(pixeles * Math.abs(intervalo2) / (intervalo1 - intervalo2));
            m_interi=(int)(pixeles * Math.abs(intervaloi2) / (intervaloi1 - intervaloi2));
            cxa     =intervalo2;
            cya     =intervaloi2;
            cr      =cxa;
            ci      =cya;
            for (j=0; j < (intervalo1 - intervalo2);)
            {
                for (k=0; k < (intervaloi1 - intervaloi2);)
                {
                    cx=0;
                    cy=0;
                    l =0;
                    while (l < 100)
                    {
                        multiplica_complejo(cx, cy, cx, cy);
                        cx=cx2 + cr;
                        cy=cy2 + ci;
                        r =(cx * cx) + (cy * cy);
                        if (r < 2)
                            con++;
                        l++;
                    }
                    if (intervalo2 < 0)
                        x=(int)((cxa + j) / suma) + m_inter;
                    else
                        x=(int)Math.abs(((cxa + j - intervalo2) * pixeles / (intervalo1 - intervalo2)));
                    if (intervaloi2 < 0)
                        y=(int)((cya + k) / sumai) + m_interi;
                    else
                        y=(int)Math.abs(((cya + k - intervaloi2) * pixeles / (intervaloi1 - intervaloi2)));

                    cr=cxa + j;
                    k =k + sumai;
                    ci=cya + k;
                    if ((x < 1000 && y < 1000) && (x > 0 && y > 0))
                        img.setRGB(x, y, col[(int)(con / 3)]);
                    con=0;
                }
                j=j + suma;
            }
            File f = new File("conmandelb" + (intervalo1 * 10) + ".png");
            try {
                ImageIO.write(img, "PNG", f);
            }
            catch (IOException e) {
                System.err.println("image not saved.");
            }
            st         =nf.format(intervalo2 + .15);
            intervalo2 =0.7 * (Double.parseDouble(st));
            st         =nf.format(intervaloi2 + .17);
            intervaloi2=0.7 * (Double.parseDouble(st));
            st         =nf.format(intervalo1 + .15);
            intervalo1 =0.7 * (Double.parseDouble(st));
            st         =nf.format(intervaloi1 + .17);
            intervaloi1=0.7 * (Double.parseDouble(st));
        }
    }
}
