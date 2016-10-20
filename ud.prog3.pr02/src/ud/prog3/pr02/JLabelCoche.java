package ud.prog3.pr02;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelCoche
  extends JLabel
{
  private static final long serialVersionUID = 1L;
  public static final int TAMANYO_COCHE = 100;
  public static final int RADIO_ESFERA_COCHE = 35;
  private static final boolean DIBUJAR_ESFERA_COCHE = false;
  
  public JLabelCoche()
  {
    try
    {
      setIcon(new ImageIcon(JLabelCoche.class.getResource("img/coche.png").toURI().toURL()));
    }
    catch (Exception e)
    {
      System.err.println("Error en carga de recurso: coche.png no encontrado");
      e.printStackTrace();
    }
    setBounds(0, 0, 100, 100);
  }
  
  private double miGiro = 1.5707963267948966D;
  
  public void setGiro(double gradosGiro)
  {
    this.miGiro = (gradosGiro / 180.0D * 3.141592653589793D);
    
    this.miGiro = (-this.miGiro);
    
    this.miGiro += 1.5707963267948966D;
  }
  
  protected void paintComponent(Graphics g)
  {
    Image img = ((ImageIcon)getIcon()).getImage();
    Graphics2D g2 = (Graphics2D)g;
    
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    g2.rotate(this.miGiro, 50.0D, 50.0D);
    
    g2.drawImage(img, 0, 0, 100, 100, null);
  }
}
