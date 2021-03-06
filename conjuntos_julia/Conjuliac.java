import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.text.*;
class Conjuliac
{
    static double cx2=0, cy2=0;
    public static void multiplica_complejo(double er, double ei, double er2, double ei2)
    {
        cx2=(-ei * ei2) + (er * er2);
        cy2=(ei * er2) + (ei2 * er);
    }

    public static void main(String args[])
    {
        double        intervalo1=2, intervalo2=-2, s;
        int           pixeles=4000;
        double        suma=(intervalo1 - intervalo2) / pixeles;
        int           m_inter=(int)((pixeles * (-intervalo2)) / (intervalo1 - intervalo2));
        double        cx=intervalo2, cy=intervalo2, cx3=0, cy3=0, cxa=intervalo2, cya=intervalo2, j, k;
        double        cr=-0.194, ci=0.6557;
        double        r=0, error2=1;
        int           pasar=0, i=0, con=0, kon=0;
        int           x=0, y=0, l=0;
        BufferedImage img  = new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
        int           re   =0;   // red component 0...255
        int           g    =255; // green component 0...255
        int           b    =255; // blue component 0...255
        int           col[]=new int[4096];
        for (i=0; i < 64; i++)
        {
            g=255;
            for (j=0; j < 64; j++)
            {
                col[kon] = (re << 16) | (g << 8) | b;
                g        =g - 3;
                kon++;
            }
            b=b - 3;
        }
        for (s=2; s > 0; s=s - 2)
        {
            img= new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            //Convertimos el numero
            String st=nf.format(s);
            //lo vuelves a convertir a double
            intervalo1 = Double.valueOf(st);
            suma       =(intervalo1 - intervalo2) / pixeles;
            m_inter    =(int)((pixeles * (-intervalo2)) / (intervalo1 - intervalo2));
            cx         =intervalo2;
            cy         =intervalo2;
            cxa        =intervalo2;
            cya        =intervalo2;
            for (j=0; j < (intervalo1 - intervalo2);)
            {
                for (k=0; k < (intervalo1 - intervalo2);)
                {
                    multiplica_complejo(cx, cy, cx, cy);
                    cx=cx2 + cr;
                    cy=cy2 + ci;
                    r =(cx * cx) + (cy * cy);
                    if (r < 2)
                        con++;
                    else
                    {
                        cx=cxa + j;
                        k =k + suma;
                        cy=cya + k;
                        if (con > 100)
                        {
                            x=(int)((cxa + j) / suma) + m_inter;
                            y=(int)((cya + k) / suma) + m_inter;
                            //System.out.println("x:"+x+"y:"+y);
                            if ((x < 4000 && y < 4000) && (x > 0 && y > 0))
                                img.setRGB(x, y, col[con]);
                            con=39;
                            con=0;
                        }
                    }
                }
                j=j + suma;
            }
            File f = new File("conjublue" + (intervalo1 * 10) + ".png");
            try {
                ImageIO.write(img, "PNG", f);
            }
            catch (IOException e) {
                System.err.println("image not saved.");
            }
            intervalo2=intervalo2 + .10;
        }
    }
}
