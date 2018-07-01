import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.text.*;
import java.util.Scanner;
class Fracta_colores_c
{
    static double cx2=0, cy2=0;
    static int col[];
    static int num_col=0;
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
        BufferedImage img = new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
        int           x=0, y=0, l=0;
        colores();
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
                            while (con > num_col) {
                                con=con - num_col;
                            }
                            //System.out.println("x:"+x+"y:"+y);
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
            File f = new File("Fracta_cir" + (intervalo2 * 10) + ".png");
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

    public static void colores()
    {
        Scanner sc=new Scanner(System.in);
        int     color=0, color1=0, color2=0, cri=0, crf=0, cgi=0, cgf=0, cbi=0, cbf=0, sumr=0, sumg=0, sumb=0, inicial=0, fin=0, div=0;
        double  seg=(Math.PI);
        System.out.print("\nCuantos colores desea en su fractal:");
        num_col=sc.nextInt();
        col    =new int[num_col + 1];
        for (int n=0; n < 2; n++)
        {
            System.out.print("\nTono " + (n + 1) + " del fractal (1)Rojo, (2)Verde, (3)Azul:");
            color=sc.nextInt();
            System.out.print("\nTono inicial del color (0-255):");
            inicial=sc.nextInt();
            System.out.print("\nTono final del color (0-255):");
            fin=sc.nextInt();
            if (color == 1)
            {
                cri=inicial;
                crf=fin;
            }
            if (color == 2)
            {
                cgi=inicial;
                cgf=fin;
            }
            if (color == 3)
            {
                cbi=inicial;
                cbf=fin;
            }
            if (n == 0)
                color1=color;
            else
                color2=color;
        }
        for (int s=0; s <= num_col; s++)
        {
            if (color1 == 1)
                sumr=(int)((((crf - cri) / 2) * Math.cos(seg)) + ((crf - cri) / 2)) + cri;
            else if (color2 == 1)
                sumr=(int)(((crf - cri) / 2) * Math.sin(seg)) + cri;
            if (color1 == 2)
                sumg=(int)((((cgf - cgi) / 2) * Math.cos(seg)) + ((cgf - cgi) / 2)) + cgi;
            else if (color2 == 2)
                sumg=(int)(((cgf - cgi) / 2) * Math.sin(seg)) + cgi;
            if (color1 == 3)
                sumb=(int)((((cbf - cbi) / 2) * Math.cos(seg)) + ((cbf - cbi) / 2)) + cbi;
            else if (color2 == 3)
                sumb=(int)(((cbf - cbi) / 2) * Math.sin(seg)) + cbi;
            System.out.println("ang:" + seg + " cos:" + Math.cos(seg) + " sen:" + Math.sin(seg));
            seg    =seg - (Math.PI) / (num_col);
            col[s] = (sumr << 16) | (sumg << 8) | sumb;
        }
    }
}
