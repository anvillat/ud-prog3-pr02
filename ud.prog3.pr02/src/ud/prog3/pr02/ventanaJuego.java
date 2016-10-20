package ud.prog3.pr02;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ventanaJuego
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  JPanel pPrincipal;
  JLabel lMensaje;
  mundoJuego miMundo;
  cocheJuego miCoche;
  MiRunnable miHilo = null;
  static Integer[] codsTeclasControladas = { Integer.valueOf(38), Integer.valueOf(40), Integer.valueOf(37), Integer.valueOf(39) };
  static List<Integer> listaTeclas = Arrays.asList(codsTeclasControladas);
  boolean[] teclasPulsadas;
  
  public ventanaJuego()
  {
    setDefaultCloseOperation(2);
    
    this.pPrincipal = new JPanel();
    JPanel pBotonera = new JPanel();
    
    this.lMensaje = new JLabel(" ");
    
    this.pPrincipal.setLayout(null);
    this.pPrincipal.setBackground(Color.white);
    
    add(this.pPrincipal, "Center");
    
    pBotonera.add(this.lMensaje);
    add(pBotonera, "South");
    
    setSize(1000, 750);
    setResizable(false);
    
    this.teclasPulsadas = new boolean[codsTeclasControladas.length];
    this.pPrincipal.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        if (ventanaJuego.listaTeclas.contains(Integer.valueOf(e.getKeyCode()))) {
          ventanaJuego.this.teclasPulsadas[ventanaJuego.listaTeclas.indexOf(Integer.valueOf(e.getKeyCode()))] = true;
        }
      }
      
      public void keyReleased(KeyEvent e)
      {
        if (ventanaJuego.listaTeclas.contains(Integer.valueOf(e.getKeyCode()))) {
          ventanaJuego.this.teclasPulsadas[ventanaJuego.listaTeclas.indexOf(Integer.valueOf(e.getKeyCode()))] = false;
        }
      }
    });
    this.pPrincipal.setFocusable(true);
    this.pPrincipal.requestFocus();
    this.pPrincipal.addFocusListener(new FocusAdapter()
    {
      public void focusLost(FocusEvent e)
      {
        ventanaJuego.this.pPrincipal.requestFocus();
      }
    });
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        if (ventanaJuego.this.miHilo != null) {
          ventanaJuego.this.miHilo.acaba();
        }
      }
    });
  }
  
  public static void main(String[] args)
  {
    try
    {
      ventanaJuego miVentana = new ventanaJuego();
      SwingUtilities.invokeAndWait(new Runnable()
      {
        public void run()
        {
          ventanaJuego.this.setVisible(true);
        }
      });
      miVentana.miMundo = new mundoJuego(miVentana.pPrincipal);
      miVentana.miMundo.creaCoche(150, 100);
      miVentana.miCoche = miVentana.miMundo.getCoche();
      miVentana.miCoche.setPiloto("Fernando Alonso"); ventanaJuego 
      
        tmp72_71 = miVentana;tmp72_71.getClass();miVentana.miHilo = new MiRunnable(tmp72_71);
      Thread nuevoHilo = new Thread(miVentana.miHilo);
      nuevoHilo.start();
    }
    catch (Exception e)
    {
      System.exit(1);
    }
  }
  
  class MiRunnable
    implements Runnable
  {
    boolean sigo = true;
    
    MiRunnable() {}
    
    public void run()
    {
      while (this.sigo)
      {
        if (ventanaJuego.this.miMundo.hayChoqueHorizontal(ventanaJuego.this.miCoche)) {
          ventanaJuego.this.miMundo.rebotaHorizontal(ventanaJuego.this.miCoche);
        }
        if (ventanaJuego.this.miMundo.hayChoqueVertical(ventanaJuego.this.miCoche)) {
          ventanaJuego.this.miMundo.rebotaVertical(ventanaJuego.this.miCoche);
        }
        double fuerzaAceleracion = 0.0D;
        if (ventanaJuego.this.teclasPulsadas[ventanaJuego.listaTeclas.indexOf(Integer.valueOf(38))] != 0) {
          fuerzaAceleracion = ventanaJuego.this.miCoche.fuerzaAceleracionAdelante();
        }
        if (ventanaJuego.this.teclasPulsadas[ventanaJuego.listaTeclas.indexOf(Integer.valueOf(40))] != 0) {
          fuerzaAceleracion = -ventanaJuego.this.miCoche.fuerzaAceleracionAtras();
        }
        MundoJuego.aplicarFuerza(fuerzaAceleracion, ventanaJuego.this.miCoche);
        if (ventanaJuego.this.teclasPulsadas[ventanaJuego.listaTeclas.indexOf(Integer.valueOf(37))] != 0) {
          ventanaJuego.this.miCoche.gira(10.0D);
        }
        if (ventanaJuego.this.teclasPulsadas[ventanaJuego.listaTeclas.indexOf(Integer.valueOf(39))] != 0) {
          ventanaJuego.this.miCoche.gira(-10.0D);
        }
        ventanaJuego.this.miCoche.mueve(0.04D);
        
        int estrellasPerdidas = ventanaJuego.this.miMundo.quitaYRotaEstrellas(6000L);
        if (estrellasPerdidas > 0)
        {
          String mensaje = "Puntos: " + ventanaJuego.this.miMundo.getPuntuacion();
          mensaje = mensaje + "  -  ESTRELLAS PERDIDAS: " + ventanaJuego.this.miMundo.getEstrellasPerdidas();
          ventanaJuego.this.lMensaje.setText(mensaje);
        }
        ventanaJuego.this.miMundo.creaEstrella();
        int choquesEstrellas = ventanaJuego.this.miMundo.choquesConEstrellas();
        if (choquesEstrellas > 0)
        {
          String mensaje = "Puntos: " + ventanaJuego.this.miMundo.getPuntuacion();
          ventanaJuego.this.lMensaje.setText(mensaje);
        }
        if (ventanaJuego.this.miMundo.finJuego())
        {
          this.sigo = false;
          ventanaJuego.this.lMensaje.setText("SE ACAB� EL JUEGO!!! Has sacado " + 
            ventanaJuego.this.miMundo.getPuntuacion() + " puntos.");
          try
          {
            Thread.sleep(3000L);
          }
          catch (Exception localException) {}
          ventanaJuego.this.dispose();
        }
        try
        {
          Thread.sleep(40L);
        }
        catch (Exception localException1) {}
      }
    }
    
    public void acaba()
    {
      this.sigo = false;
    }
  }
}
