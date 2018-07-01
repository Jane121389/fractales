/** Crea fractales con colores introduciendo valores iniciales y finales**/
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.text.*;
import java.util.Scanner;
class Fracta_color_if
{
    static double cx2=0, cy2=0;
    static int total=0, num_col=0;
    static int col[];
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
                        //System.out.println(" ="+vxr[i]+"="+vxi[i]+"="+con);
                        if (con >= total)
                        {
                            con   =total - 1;
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
                            while (con > total) {
                                con=total - con;
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
            File f = new File("Fra_colores_if" + (intervalo2 * 10) + ".png");
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
            //Convertimos el numero
            //lo vuelves a convertir a double
        }
    }

    public static void colores()
    {
        Scanner sc = new Scanner(System.in);
        int     xi=0, xf=0, yi=0, yf=0, zi=0, zf=0, colore=0, xp, yp, zp, xv=0, yv=0, zv=0, con=0, ci, cf;
        System.out.println("Escriba el numero total de colores que desee que tenga su fractal");
        total=sc.nextInt();
        System.out.println("Escriba el numero de colores base a utilizar(1,2,3)(rojo,verde,azul)");
        num_col=sc.nextInt();
        col    =new int[(int)Math.pow(total, num_col)];
        for (int i=0; i < num_col; i++)
        {
            System.out.println("Elija el color numero " + (i + 1) + " base a utilizar (1.-Rojo,2.-Verde,3.-Azul");
            colore=sc.nextInt();
            System.out.println("Elija el valor inicial del color (0-255)");
            ci=sc.nextInt();
            System.out.println("Elija el valor final del color (0-255)");
            cf=sc.nextInt();
            if (colore == 1)
            {
                xi=ci;
                xf=cf;
            }
            else if (colore == 2)
            {
                yi=ci;
                yf=cf;
            }
            else if (colore == 3)
            {
                zi=ci;
                zf=cf;
            }
        }
        zv=zi;
        yv=yi;
        xv=xi;
        xp=(xf - xi) / total;
        yp=(yf - yi) / total;
        zp=(zf - zi) / total;
        for (int i=0; i < total; i++)
        {
            col[con] = (xv << 16) | (yv << 8) | zv;
            con++;
            zv=zv + zp;
            yv=yv + yp;
            xv=xv + xp;
        }
    }
}
