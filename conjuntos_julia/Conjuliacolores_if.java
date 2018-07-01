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
class Conjuliacolores_if
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
        double        intervalo1=2, intervalo2=-2, s;
        int           pixeles=4000;
        double        suma=(intervalo1 - intervalo2) / pixeles;
        int           m_inter=(int)((pixeles * (-intervalo2)) / (intervalo1 - intervalo2));
        double        cx=intervalo2, cy=intervalo2, cx3=0, cy3=0, cxa=intervalo2, cya=intervalo2, j, k;
        double        cr=-0.194, ci=0.6557;
        double        r=0, error2=1;
        int           pasar=0, i=0, con=0, kon=0;
        int           num_may=0, x=0, y=0, num_men;
        BufferedImage img = new BufferedImage(pixeles, pixeles, BufferedImage.TYPE_INT_RGB);
        colores();
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
            con        =0;
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
                        if (con > num_may)
                            num_may=con;
                        if (con > 100)
                        {
                            x=(int)((cxa + j) / suma) + m_inter;
                            y=(int)((cya + k) / suma) + m_inter;
                            //System.out.println("x:"+x+"y:"+y);
                            con=con - 100;
                            while (con >= total) {
                                con=con - total;
                            }
                            if ((x < 4000 && y < 4000) && (x > 0 && y > 0))
                                //img.setRGB(x, y, col[con*(total-1)/738]);
                                img.setRGB(x, y, col[con]);
                            con=0;
                        }
                    }
                }
                j=j + suma;
            }
            File f = new File("conj_col_if" + (intervalo1 * 10) + ".png");
            try {
                ImageIO.write(img, "PNG", f);
            }
            catch (IOException e) {
                System.err.println("image not saved.");
            }
            intervalo2=intervalo2 + .10;
            System.out.println("numero mayor " + num_may);
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
