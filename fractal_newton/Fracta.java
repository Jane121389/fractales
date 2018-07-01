import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.text.*;
class Fracta
{
    static double cx2=0, cy2=0;
    public static void multiplica_complejo(double er, double ei, double er2, double ei2)
    {
        cx2=(-ei * ei2) + (er * er2);
        cy2=(ei * er2) + (ei2 * er);
    }

    public static void main(String args[])
    {
        double       intervalo1=1, intervalo2=-1, intervaloi1=1, intervaloi2=-1, s;
        int          pixeles=1000;
        String       st;
        double       suma    =(Math.abs(intervalo1) + Math.abs(intervalo2)) / pixeles;
        double       sumai   =(Math.abs(intervaloi1) + Math.abs(intervaloi2)) / pixeles;
        int          m_inter =(int)(pixeles * Math.abs(intervalo2) / (Math.abs(intervalo1) + Math.abs(intervalo2)));
        int          m_interi=(int)(pixeles * Math.abs(intervaloi2) / (Math.abs(intervaloi1) + Math.abs(intervaloi2)));
        double       vxr[]   ={
            1, -0.5, -0.5
        };
        double       vxi[]={
            0, Math.sqrt(3) / 2, -Math.sqrt(3) / 2
        };
        double       cx=intervalo2, cy=intervaloi2, cx3=0, cy3=0, cxa=intervalo2, cya=intervaloi2, j, k;
        double       rr=0, ri=0;
        double       error1, error2=1;
        int          pasar=0, i=0, con=0;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        BufferedImage img  = new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
        int           col[]={
            0xCC0000, 0xFF0000, 0xFF3333, 0xCC6600, 0xFF8000, 0xFF9933, 0xCCCC00, 0xFFFF00, 0xFFFF33, 0x66CC00, 0x80FF00, 0x99FF33, 0x00CC00, 0x00FF00, 0x33FF33, 0x00CC66, 0x00FF80, 0x33FF99, 0x00CCCC, 0x00FFFF, 0x33FFFF, 0x0066CC, 0x0080FF, 0x3399FF, 0x0000CC, 0x0000FF, 0x3333FF, 0x6600CC, 0x7F00FF, 0x9933FF, 0XCC00CC, 0XFF00FF, 0xFF33FF, 0XCC0066, 0XFF007F, 0xFF3399, 0X606060, 0x808080, 0xA0A0A0
        };
        int           x=0, y=0, l=0;
        for (s=1; s < 30; s++)
        {
            img     = new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
            suma    =(intervalo1 - intervalo2) / pixeles;
            sumai   =(intervaloi1 - intervaloi2) / pixeles;
            m_inter =(int)(pixeles * Math.abs(intervalo2) / (intervalo1 - intervalo2));
            m_interi=(int)(pixeles * Math.abs(intervaloi2) / (intervaloi1 - intervaloi2));
            cx      =intervalo2;
            cy      =intervaloi2;
            cxa     =intervalo2;
            cya     =intervaloi2;
            for (j=0; j < (intervalo1 - intervalo2);)
            {
                for (k=0; k < (intervaloi1 - intervaloi2);)
                {
                    multiplica_complejo(cx, cy, cx, cy);
                    multiplica_complejo(cx, cy, cx2, cy2);
                    rr=(cx2 - 1) / 3;
                    ri=cy2 / 3;
                    multiplica_complejo(cx, cy, cx, cy);
                    cx3=cx2;
                    cy3=cy2;
                    multiplica_complejo(rr, ri, cx3, -cy3);
                    rr=cx2;
                    ri=cy2;
                    multiplica_complejo(cx3, cy3, cx3, -cy3);
                    rr=rr / cx2;
                    ri=ri / cx2;
                    cx=cx - rr;
                    cy=cy - ri;
                    con++;

                    for (i=0; i < 3; i++)
                    {
                        error1=Math.sqrt(Math.pow(cx - vxr[i], 2) + Math.pow(cy - vxi[i], 2));
                        if (con > 39)
                        {
                            con   =39;
                            error1=0;
                        }
                        if (error1 < 0.01)
                        {
                            if (intervalo2 < 0)
                            {
                                x=(int)((cxa + j) / suma) + m_inter;
                                y=(int)((cya + k) / sumai) + m_interi;
                            }
                            else
                            {
                                x=(int)((cxa + j - intervalo2) * pixeles / (intervalo1 - intervalo2));
                                y=(int)((cya + k - intervaloi2) * pixeles / (intervaloi1 - intervaloi2));
                            }
                            if ((x < 1000 && y < 1000) && (x > 0 && y > 0))
                                img.setRGB(x, y, col[con - 1]);
                            pasar++;
                            cx =cxa + j;
                            k  =k + sumai;
                            cy =cya + k;
                            i  =3;
                            con=0;
                        }
                    }
                }
                j=j + suma;
            }
            File f = new File("Frarrr" + (intervalo2 * 10) + ".png");
            try {
                ImageIO.write(img, "PNG", f);
            }
            catch (IOException e) {
                System.err.println("image not saved.");
            }
            intervalo2 =0.7 * (intervalo2 + .2);
            intervaloi2=0.7 * (intervaloi2 + .17);
            intervalo1 =0.7 * (intervalo1 + .2);
            intervaloi1=0.7 * (intervaloi1 + .17);
        }
    }
}
