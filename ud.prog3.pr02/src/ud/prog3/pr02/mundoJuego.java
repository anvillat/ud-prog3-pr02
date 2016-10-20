package ud.prog3.pr02;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class mundoJuego
{
  private JPanel panel;
  coche miCoche;
  ArrayList<JLabelEstrella> estrellas = new ArrayList();
  private static long tiempoUltEstrellaCreada = 0L;
  private int puntosJuego = 0;
  private int estrellasPerdidas = 0;
  
  public mundoJuego(JPanel panel)
  {
    this.panel = panel;
  }
  
  private static Random r = new Random();
  
  public void creaEstrella()
  {
    if (System.currentTimeMillis() - tiempoUltEstrellaCreada > 1200L)
    {
      JLabelEstrella nuevaEstrella = new JLabelEstrella();
      nuevaEstrella.setLocation(r.nextInt(this.panel.getWidth() - 40), 
        r.nextInt(this.panel.getHeight() - 40));
      this.panel.add(nuevaEstrella);
      nuevaEstrella.repaint();
      this.estrellas.add(nuevaEstrella);
      tiempoUltEstrellaCreada = System.currentTimeMillis();
    }
  }
  
  public int quitaYRotaEstrellas(long maxTiempo)
  {
    int numEstFuera = 0;
    for (int i = this.estrellas.size() - 1; i >= 0; i--)
    {
      JLabelEstrella est = (JLabelEstrella)this.estrellas.get(i);
      if (System.currentTimeMillis() - est.getHoraCreacion() > maxTiempo)
      {
        this.panel.remove(est);
        this.panel.repaint();
        this.estrellas.remove(est);
        numEstFuera++;
        this.estrellasPerdidas += 1;
      }
      else
      {
        est.addGiro(10.0D);
        est.repaint();
      }
    }
    return numEstFuera;
  }
  
  private boolean chocaCocheConEstrella(JLabelEstrella est)
  {
    double distX = est.getX() + 20 - this.miCoche.getPosX() - 50.0D;
    double distY = est.getY() + 20 - this.miCoche.getPosY() - 50.0D;
    double dist = Math.sqrt(distX * distX + distY * distY);
    return dist <= 52.0D;
  }
  
  public int choquesConEstrellas()
  {
    int numChoques = 0;
    for (int i = this.estrellas.size() - 1; i >= 0; i--)
    {
      JLabelEstrella est = (JLabelEstrella)this.estrellas.get(i);
      if (chocaCocheConEstrella(est))
      {
        numChoques++;
        this.panel.remove(est);
        this.panel.repaint();
        this.estrellas.remove(est);
        this.puntosJuego += 5;
      }
    }
    return numChoques;
  }
  
  public int getPuntuacion()
  {
    return this.puntosJuego;
  }
  
  public int getEstrellasPerdidas()
  {
    return this.estrellasPerdidas;
  }
  
  public boolean finJuego()
  {
    return this.estrellasPerdidas >= 10;
  }
  
  public void creaCoche(int posX, int posY)
  {
    this.miCoche = new cocheJuego();
    this.miCoche.setPosicion(posX, posY);
    this.panel.add(this.miCoche.getGrafico());
    this.miCoche.getGrafico().repaint();
  }
  
  public CocheJuego getCoche()
  {
    return this.miCoche;
  }
  
  public boolean hayChoqueHorizontal(cocheJuego coche)
  {
    return (coche.getPosX() < -15.0D) || (
      coche.getPosX() > this.panel.getWidth() - 50 - 35);
  }
  
  public boolean hayChoqueVertical(cocheJuego coche)
  {
    return (coche.getPosY() < -15.0D) || (
      coche.getPosY() > this.panel.getHeight() - 50 - 35);
  }
  
  public void rebotaHorizontal(cocheJuego coche)
  {
    double dir = coche.getDireccionActual();
    dir = 180.0D - dir;
    if (dir < 0.0D) {
      dir += 360.0D;
    }
    coche.setDireccionActual(dir);
  }
  
  public void rebotaVertical(cocheJuego coche)
  {
    double dir = this.miCoche.getDireccionActual();
    dir = 360.0D - dir;
    this.miCoche.setDireccionActual(dir);
  }
  
  public static double calcMovtoX(double vel, double dir, double tiempo)
  {
    return vel * Math.cos(dir / 180.0D * 3.141592653589793D) * tiempo;
  }
  
  public static double calcMovtoY(double vel, double dir, double tiempo)
  {
    return vel * -Math.sin(dir / 180.0D * 3.141592653589793D) * tiempo;
  }
  
  public static double calcVelocidadConAceleracion(double vel, double acel, double tiempo)
  {
    return vel + acel * tiempo;
  }
  
  public static double calcFuerzaRozamiento(double masa, double coefRozSuelo, double coefRozAire, double vel)
  {
    double fuerzaRozamientoSuelo = masa * coefRozSuelo * (vel > 0.0D ? -1 : 1);
    double fuerzaRozamientoAire = coefRozAire * -vel;
    return fuerzaRozamientoAire + fuerzaRozamientoSuelo;
  }
  
  public static double calcAceleracionConFuerza(double fuerza, double masa)
  {
    return fuerza / masa;
  }
  
  public static void aplicarFuerza(double fuerza, coche coche)
  {
    double fuerzaRozamiento = calcFuerzaRozamiento(1.0D, 
      15.5D, 0.35D, coche.getVelocidad());
    double aceleracion = calcAceleracionConFuerza(fuerza + fuerzaRozamiento, 1.0D);
    if (fuerza == 0.0D)
    {
      double velAntigua = coche.getVelocidad();
      coche.acelera(aceleracion, 0.04D);
      if (((velAntigua >= 0.0D) && (coche.getVelocidad() < 0.0D)) || (
        (velAntigua <= 0.0D) && (coche.getVelocidad() > 0.0D))) {
        coche.setVelocidad(0.0D);
      }
    }
    else
    {
      coche.acelera(aceleracion, 0.04D);
    }
  }
}
//qqqq