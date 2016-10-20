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

public class JLabelEstrella
  extends JLabel
{
  private static final long serialVersionUID = 1L;
  public static final int TAMANYO_ESTRELLA = 40;
  public static final int RADIO_ESFERA = 17;
  private static final boolean DIBUJAR_ESFERA = false;
  private long horaCreacion = System.currentTimeMillis();
  
  public JLabelEstrella()
  {
    try
    {
      setIcon(new ImageIcon(JLabelEstrella.class.getResource("img/estrella.png").toURI().toURL()));
    }
    catch (Exception e)
    {
      System.err.println("Error en carga de recurso: estrella.png no encontrado");
      e.printStackTrace();
    }
    setBounds(0, 0, 40, 40);
  }
  
  private double miGiro = 0.0D;
  
  public void setGiro(double gradosGiro)
  {
    this.miGiro = (gradosGiro / 180.0D * 3.141592653589793D);
    
    this.miGiro = (-this.miGiro);
    if (this.miGiro > 6.283185307179586D) {
      this.miGiro -= 6.283185307179586D;
    } else if (this.miGiro < 0.0D) {
      this.miGiro += 6.283185307179586D;
    }
  }
  
  public void addGiro(double gradosGiro)
  {
    this.miGiro -= gradosGiro / 180.0D * 3.141592653589793D;
  }
  
  public double getGiro()
  {
    return -this.miGiro * 180.0D / 3.141592653589793D;
  }
  
  public long getHoraCreacion()
  {
    return this.horaCreacion;
  }
  
  protected void paintComponent(Graphics g)
  {
    Image img = ((ImageIcon)getIcon()).getImage();
    Graphics2D g2 = (Graphics2D)g;
    
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    g2.rotate(this.miGiro, 20.0D, 20.0D);
    
    g2.drawImage(img, 0, 0, 40, 40, null);
  }
}
//qqqq