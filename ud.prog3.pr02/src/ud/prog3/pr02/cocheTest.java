package ud.prog3.pr02;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class cocheTest
{
  Coche c;
  
  @Before
  public void setUp()
    throws Exception
  {
    this.c = new Coche();
  }
  
  @Test
  public void testGira()
  {
    this.c.gira(10.0D);
    Assert.assertEquals(10.0D, this.c.getDireccionActual(), 0.0D);
    this.c.gira(360.0D);
    Assert.assertEquals(10.0D, this.c.getDireccionActual(), 0.0D);
    this.c.gira(-360.0D);
    Assert.assertEquals(10.0D, this.c.getDireccionActual(), 0.0D);
  }
  
  @Test
  public void testFuerzaAceleracionAdelante()
  {
    double[] tablaVel = { -600.0D, -500.0D, -150.0D, -75.0D, 0.0D, 125.0D, 250.0D, 500.0D, 750.0D, 875.0D, 1000.0D };
    double[] tablaFuerza = { 1.0D, 1.0D, 1.0D, 0.75D, 0.5D, 0.75D, 1.0D, 1.0D, 1.0D, 0.5D, 0.0D };
    for (int i = 0; i < tablaVel.length; i++)
    {
      this.c.setVelocidad(tablaVel[i]);
      Assert.assertEquals("Velocidad " + tablaVel[i], tablaFuerza[i] * 2000.0D, this.c.fuerzaAceleracionAdelante(), 1.0E-7D);
    }
  }
  
  @Test
  public void testFuerzaAceleracionAtras()
  {
    double[] tablaVel = { -500.0D, -425.0D, -300.0D, -250.0D, -200.0D, -100.0D, 0.0D, 125.0D, 250.0D, 500.0D, 1100.0D };
    double[] tablaFuerza = { 0.0D, 0.5D, 1.0D, 1.0D, 1.0D, 0.65D, 0.3D, 0.575D, 0.85D, 0.85D, 0.85D };
    for (int i = 0; i < tablaVel.length; i++)
    {
      this.c.setVelocidad(tablaVel[i]);
      Assert.assertEquals("Velocidad " + tablaVel[i], tablaFuerza[i] * 1000.0D, this.c.fuerzaAceleracionAtras(), 1.0E-7D);
    }
  }
}
