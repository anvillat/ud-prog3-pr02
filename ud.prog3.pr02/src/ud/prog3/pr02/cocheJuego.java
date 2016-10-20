package ud.prog3.pr02;

public class cocheJuego
  extends Coche
{
  private JLabelCoche miGrafico;
  
  public cocheJuego()
  {
    this.miGrafico = new JLabelCoche();
  }
  
  public JLabelCoche getGrafico()
  {
    return this.miGrafico;
  }
  
  public void setPosX(double posX)
  {
    super.setPosX(posX);
    this.miGrafico.setLocation((int)posX, (int)this.posY);
  }
  
  public void setPosY(double posY)
  {
    super.setPosY(posY);
    this.miGrafico.setLocation((int)this.posX, (int)posY);
  }
  
  public void setDireccionActual(double dir)
  {
    super.setDireccionActual(dir);
    this.miGrafico.setGiro(this.miDireccionActual);
    this.miGrafico.repaint();
  }
}
//qqqq