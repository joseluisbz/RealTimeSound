package org.joseluisbz.sound;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;
import javax.swing.event.MouseInputListener;
import javax.swing.JPanel;

/**
 *
 * @author joseluisbz
 */
public class PanelChart {
  private final ArrayList<BasicStroke> arlBscStrk = new ArrayList<>();    //Vector de BasicStroke de la Variable
  private final ArrayList<Color> arlColor = new ArrayList<>();    //Vector de Color de la Variable
  private final ArrayList<Boolean> arlView = new ArrayList<>();    //Vector de Visibilidad de la Variable
  private final ArrayList<String> arlName = new ArrayList<>();  //Vector de Nombre de la Variable
  private final ArrayList<String> arlLabel = new ArrayList<>();  //Vector de Label de la Variable
//  private final ArrayList<Double []> arlX = new ArrayList<>();//Vector de Arreglo de X de la Variable
  private final ArrayList<double []> arlX = new ArrayList<>();//Vector de Arreglo de X de la Variable
//  private final ArrayList<Double []> arlY = new ArrayList<>();//Vector de Arreglo de X de la Variable
  private final ArrayList<double []> arlY = new ArrayList<>();//Vector de Arreglo de X de la Variable
  private final int borderHorz = 0, borderVert = 0;
  public static final Double DOUBLE_MAX_VALUE = Double.MAX_VALUE;
  public static final Double DOUBLE_MIN_VALUE = -Double.MAX_VALUE;
  
  private String vbleX = "", vbleY = ""; //Agregar Nombre de Variables X y Y
  private String titleY = "", titleX = ""; //Agrega Título Vertical(Izquierda) y Título Horizontal(Debajo)
  private String titleM, titleS; //Título Principal y Título Secundario
  
  private Color backColor = new Color(16,16,16);
  private Color extGridColor = Color.GRAY;
  private Color intGridColor = Color.DARK_GRAY; //Color(64, 64, 64);
  private Color midGridColor = Color.GRAY; //Color(128, 128, 128);
  private Color indNumbColor = Color.LIGHT_GRAY; //Color(192, 192, 192);
  private Color titleColor = Color.WHITE;
  private Color vbleXColor = Color.WHITE;
  private Color vbleYColor = Color.WHITE;
  private Color labelColor = Color.WHITE;
  
  private Integer yLarge = null;
  private Integer xWidth = null;
  
  //Ini:Almacena Máximos y Mínimos de Coordenadas X y Y
  private Double maxCoordinateX = null;
  private Double minCoordinateX = null;
  private Double maxCoordinateY = null;
  private Double minCoordinateY = null;
   //End:Almacena Máximos y Mínimos de Coordenadas X y Y
  private Integer beginCoordinateX = null;
  private Integer beginCoordinateY = null;  //Down Position, Greater Value
  private Integer finalCoordinateX = null;
  private Integer finalCoordinateY = null;  //Up Position, Lower Value
  private Integer halfCoordinateX = null;
  private Integer halfCoordinateY = null;
  
  private boolean bColorBack = true;
  private Integer gridDivHorzX = 16;
  private Integer gridDivVertY = 8;
  
  private Double dLogRespHorzX = Math.E;
  private Double dLogRespVertY = Math.E;
  
  private boolean bLogGridHorzX = false;
  private boolean bLogGridVertY = false;
  private boolean bLogRespHorzX = false;
  private boolean bLogRespVertY = false;
  
  private boolean bUseMinX = false;
  private boolean bUseMaxX = false;
  private boolean bUseMinY = false;
  private boolean bUseMaxY = false;
  
  private double dScaleX = 1.0;
  private double dScaleY = 1.0;
  private boolean bScaleX = false;
  private boolean bScaleY = false;
  
  private double dReqMinX = DOUBLE_MIN_VALUE;
  private double dReqMaxX = DOUBLE_MAX_VALUE;
  private double dReqMinY = DOUBLE_MIN_VALUE;
  private double dReqMaxY = DOUBLE_MAX_VALUE;
  private double dWorkMinX = DOUBLE_MIN_VALUE;
  private double dWorkMaxX = DOUBLE_MAX_VALUE;
  private double dWorkMinY = DOUBLE_MIN_VALUE;
  private double dWorkMaxY = DOUBLE_MAX_VALUE;
  
  private Double dLogWorkMaxX = null;
  private Double dLogWorkMinX = null;
  private Double dLogWorkMinY = null;
  private Double dLogWorkMaxY = null;
  
  private int iFracX = 2;
  private int iFracY = 2;
  
  private int maxStringWideIndicVert = 0;  //maxStringWideIndicatorVertical
  private int maxStringWideIndicHorz = 0;  //maxStringWideIndicatorHorizontal
  private int maxStringHighTitleHorz = 0;  //maxStringHighTitleHorizontal
  private int maxStringHighVbleHorz = 0;  //maxStringHighVbleHorizontal
  private int maxWideBox = 0;  //maxHighBox
  private int maxHighBox = 0;  //maxWideBox
  
  private final int displacementHorz = 0;
  private final int displacementVert = 0;
  
  private final Stroke strkDashedLin = new BasicStroke(/*width*/ 0.8f, 
      /*cap*/ BasicStroke.CAP_SQUARE, /*join*/ BasicStroke.JOIN_MITER, 
          /*miterlimit*/ 10.0f, /*dash*/ null, /*dash_phase*/ 0);
  private final Stroke strkDashedPiv = new BasicStroke(/*width*/ 0.5f, 
      /*cap*/ BasicStroke.CAP_BUTT, /*join*/ BasicStroke.JOIN_BEVEL, 
          /*miterlimit*/ 0, /*dash*/ new float[] {2.0f}, /*dash_phase*/ 0);
  private final Stroke strkDashedSec = new BasicStroke(/*width*/ 0.3f, 
      /*cap*/ BasicStroke.CAP_BUTT, /*join*/ BasicStroke.JOIN_BEVEL, 
          /*miterlimit*/ 0, /*dash*/ new float[]{1.0f, 6.0f}, /*dash_phase*/ 0);
        
  //private static int sepH = 20, sepV = 1;
  private final Font indicatorFont = new Font("Monospaced",Font.PLAIN,12);
  private final Font variableFont = new Font("Monospaced",Font.BOLD,16);
  private final Font labelFont = new Font("Monospaced",Font.PLAIN,16);
  private final Font titleFont = new Font("Monospaced",Font.BOLD,20);
  
  private BufferedImage bi = null;
  private Graphics2D g2d = null;
  private IntPanel outputPanel;
//  private final Semaphore smphr = new Semaphore(1);
  
  PanelChart(int xWidth, int yLarge) {
  //Asignar la Dimensión del Lienzo
    this.xWidth = xWidth;
    this.yLarge = yLarge;
        
//    BasicStroke bscStrk = (BasicStroke)g2d.getStroke();
//    System.out.println("\nPanelChart");
//    System.out.println("bscStrk.getLineWidth():" + bscStrk.getLineWidth());
//    System.out.println("bscStrk.getEndCap():" + bscStrk.getEndCap());
//    System.out.println("bscStrk.getLineJoin():" + bscStrk.getLineJoin());
//    System.out.println("bscStrk.getMiterLimit():" + bscStrk.getMiterLimit());
//    System.out.println("bscStrk.getDashPhase():" + bscStrk.getDashPhase());
//    System.out.println("bscStrk.getDashArray():" + Arrays.toString(bscStrk.getDashArray()));
//    System.out.println();
  }
  PanelChart() {
    this(1, 1);
  }
  
  public void restoreDefaultValues() {
    maxCoordinateX = null; 
    minCoordinateX = null;
    maxCoordinateY = null;
    minCoordinateY = null;
    
    beginCoordinateX = null;
    beginCoordinateY = null;
    finalCoordinateX = null;
    finalCoordinateY = null;
    halfCoordinateX = null;
    halfCoordinateY = null;
    
    dLogRespHorzX = Math.E;
    dLogRespVertY = Math.E;
    
    bUseMinX = false;
    bUseMaxX = false;
    bUseMinY = false;
    bUseMaxY = false;
    
//    dReqMinX = dMinVal;
//    dReqMaxX = dMaxVal;
//    dReqMinY = dMinVal;
//    dReqMaxY = dMaxVal;
//    dWorkMinX = dMinVal;
//    dWorkMaxX = dMaxVal;
//    dWorkMinY = dMinVal;
//    dWorkMaxY = dMaxVal;
    
    dLogWorkMaxX = null;
    dLogWorkMinX = null;
    dLogWorkMinY = null;
    dLogWorkMaxY = null;
    
//    mSWIV = 0;  //maxStringWidthIndicatorVertical
//    maxStringWideIndicHorz = 0;  //maxStringWidthIndicatorHorizontal
//    maxStringHighTitleHorz = 0;  //maxStringHighTitleHorizontal
//    maxStringHighVbleHorz = 0;  //maxStringHighVbleHorizontal
//    mWB = 0;  //maxHighBox
//    mHB = 0;  //maxWideBox
  }
  
  public JPanel getChart() {
//    try {
//      smphr.acquire();
//    } catch (InterruptedException ex) {
//      System.out.println(ex);
//    }
    outputPanel = new IntPanel(xWidth, yLarge, backColor);
//    smphr.release();
    return outputPanel;
  }
  
  public JPanel getChart(int xWidth, int yLarge) {
    setNewDimensionXY(xWidth, yLarge);
    return getChart();
  }
  
  public void setNewDimensionXY(int xWidth, int yLarge) {
    // Is not used Internally
    this.xWidth = xWidth;
    this.yLarge = yLarge;
  }
  public int getHorzWidthX() {
    return xWidth;
  }
  public void setHorzWidthX(int xWidth) {
    this.xWidth = xWidth;
  }
  public int getVertLargeY() {
    return yLarge;
  }
  public void setVertLargeY(int yLarge) {
   this.yLarge = yLarge;
  }
  
  public void setFracX(int i) {
    iFracX = i;
  }
  public void setFracY(int i) {
    iFracY = i;
  }
  
  public boolean useScaleX() {
    return bScaleX;
  }
  public void useScaleX(boolean b) {
    bScaleX = b;
  }
  public double getScaleX() {
    return dScaleX;
  }
  public void setScaleX(double d) {
    dScaleX = d;
  }
  
  public boolean useScaleY() {
    return bScaleY;
  }
  public void useScaleY(boolean b) {
    bScaleY = b;
  }
  public double getScaleY() {
    return dScaleY;
  }
  public void setScaleY(double d) {
    dScaleY = d;
  }
  
  public boolean useMinX() {
    return bUseMinX;
  }
  public void useMinX(boolean b) {
    bUseMinX = b;
  }
  public double getMinX() {
    return dReqMinX;
  }
  public void setMinX(double d) {
    dReqMinX = d;
  }
  
  public boolean useMaxX() {
    return bUseMaxX;
  }
  public void useMaxX(boolean b) {
    bUseMaxX = b;
  }
  public double getMaxX() {
    return dReqMaxX;
  }
  public void setMaxX(double d) {
    dReqMaxX = d;
  }
  
  public boolean useMinY() {
    return bUseMinY;
  }
  public void useMinY(boolean b) {
    bUseMinY = b;
  }
  public double getMinY() {
    return dReqMinY;
  }
  public void setMinY(double d) {
    dReqMinY = d;
  }
  
  public boolean useMaxY() {
    return bUseMaxY;
  }
  public void useMaxY(boolean b) {
    bUseMaxY = b;
  }
  public double getMaxY() {
    return dReqMaxY;
  }
  public void setMaxY(double d) {
    dReqMaxY = d;
  }
  
  public boolean useLogRespHorzX() {
    return bLogRespHorzX;
  }
  public void useLogRespHorzX(boolean b) {
    bLogRespHorzX = b;
  }
  public boolean useLogGridHorzX() {
    return bLogGridHorzX;
  }
  public void useLogGridHorzX(boolean b) {
    bLogGridHorzX = b;
  }
  public Double getLogRespHorzX() {
    return dLogRespHorzX;
  }
  public void setLogRespHorzX(Double d) {
    if (!(d > 1.0)) throw new IllegalArgumentException("Base must be Integer greater than 1");
    dLogRespHorzX = d;
  }
  
  public boolean useLogRespVertY() {
    return bLogRespVertY;
  }
  public void useLogRespVertY(boolean b) {
    bLogRespVertY = b;
  }
  public boolean useLogGridVertY() {
    return bLogGridVertY;
  }
  public void useLogGridVertY(boolean b) {
    bLogGridVertY = b;
  }
  public Double getLogRespVertY() {
    return dLogRespVertY;
  }
  public void setLogRespVertY(Double d) {
    if (!(d > 1.0)) throw new IllegalArgumentException("Base must be Integer greater than 1");
    dLogRespVertY = d;
  }
  
  public boolean useColorBack() {
    return bColorBack;
  }
  public void useColorBack(boolean b) {
    bColorBack = b;
  }
  public void setBackColor(Color c) {
    backColor = c;
  }
  public Color getBackColor() {
    return backColor;
  }
  
  public void setExtGridColor(Color c) {
    extGridColor = c;
  }
  public void setIntGridColor(Color c) {
    intGridColor = c;
  }
  public void setIndNumbColor(Color c) {
    indNumbColor = c;
  }
  public void setTitleColor(Color c) {
    titleColor = c;
  }
  public void setVbleXColor(Color c) {
    vbleXColor = c;
  }
  public void setVbleYColor(Color c) {
    vbleYColor = c;
  }
  public void setLabelColor(Color c) {
    labelColor = c;
  }
  
  public void setGridDivHorzX(Integer i) {
    gridDivHorzX = i;
  }
  public void setGridDivVertY(Integer i) {
    gridDivVertY = i;
  }
  
  public void setNameVbleX(String n) {
    vbleX = n;
  }
  public void setNameVbleY(String n) {
    vbleY = n;
  }
  public void setNameTitleY(String n) {
    titleY = n;
  }
  public void setNameTitleX(String n) {
    titleX = n;
  }
  
  public boolean existVble(String nVble) {
  //Verifica si existe una Variable llamada nVble
    return arlName.contains(nVble);
  }
  
  private boolean valueRepeated(double[] data) {
  //Verifica si un elemento repetido en un Arreglo
    boolean result = false;
    for (int i = 0; i < data.length - 1; i++)
      for (int j = i+1; j < data.length; j++)
        if(data[i] == data[j]) {
          result = true;
        }
    return result;
  }
  private boolean isValueIncluded(double val, double[] data) {
  //Verifica si existe un elemento dentro de un Arreglo
    boolean result = false;
    for (int i = 0; i < data.length-1; i++)
      if(data[i] == val) {
        result = true;
      }
    return result;
  }
  private void removeInoperableHorzPairs(double[] aX, double[] aY) {
    int InoperableXValues = 0;
    for (int i = 0; i < aX.length; i++) {
      if (Double.isInfinite(aX[i]) || Double.isNaN(aX[i])) {
        InoperableXValues++;
      }
    }
    double[] nX = new double[aX.length - InoperableXValues];
    double[] nY = new double[aY.length - InoperableXValues];
    int j = 0;
    for (int i = 0; i < nX.length; i++) {
      if (!Double.isInfinite(aX[i]) && !Double.isNaN(aX[i])) {
        nX[j] = aX[i];
        nY[j] = aY[i];
      }
      j++;
    }
    aX = nX;
    aY = nY;
  }
  private void quickSortPairs(double[] aX, double[] aY, int loIndex, int hiIndex) {
    int i = loIndex;
    int j = hiIndex;
    // Get the pivot element from the middle of the array
    Double pivot = aX[loIndex+(hiIndex-loIndex)/2];
    // Divide into two arrays
    while (i <= j) {
      /**
       * In each iteration, we will identify a number from left side which
       * is greater then the pivot value, and also we will identify a number
       * from right side which is less then the pivot value. Once the search
       * is done, then we exchange both numbers.
       */
      // If the current value from the left array is smaller then the pivot
      // element then get the next element from the left array
      while (aX[i] < pivot) {
        i++;
      }
      // If the current value from the right array is larger then the pivot
      // element then get the next element from the right array
      while (aX[j] > pivot) {
        j--;
      }
      
      // If we have found a values in the left array which is larger then
      // the pivot element and if we have found a value in the right array
      // which is smaller then the pivot element then we exchange the
      // values.
      // As we are done we can increase i and j
      if (i <= j) {
        Double tempX;  Double tempY;
        tempX = aX[i]; tempY = aY[i];
        aX[i] = aX[j]; aY[i] = aY[j];
        aX[j] = tempX; aY[j] = tempY;
        //move index to next position on both sides
        i++;
        j--;
      }
    }
    // call quickSort() method recursively
    if (loIndex < j) {
      quickSortPairs(aX, aY, loIndex, j);
    }
    if (i < hiIndex) {
      quickSortPairs(aX, aY, i, hiIndex);
    }
  }
  private void bubbleSortPairs(double[] aX, double[] aY) {
  //Ordena los pares de los arreglos según aX de menor a mayor
    if (aX.length != aY.length) throw new RuntimeException("Talla de arreglos diferentes");
    double xTemp, yTemp;
    for (int i = 0; i < aX.length-1; i++) {
      for (int j = i+1; j < aX.length; j++) {
        if(aX[j] < aX[i]) {
          xTemp = aX[j]; yTemp = aY[j];
          aX[j] = aX[i]; aY[j] = aY[i];
          aX[i] = xTemp; aY[i] = yTemp;
        }
      }
    }
  }
  private double getMax(double[] data) {
  //Obtener el valor MAYOR del Arreglo
    if (data.length < 1) throw new RuntimeException("Arreglo de datos vacío");
    double Max = DOUBLE_MIN_VALUE;
    for (int i = 0; i < data.length; i++) {
      if ((data[i] != Double.NEGATIVE_INFINITY) && !Double.isNaN(data[i]) 
          && (data[i] != Double.POSITIVE_INFINITY)) {
        if (Max < data[i]) {
          Max = data[i];
        }
      }
    }
    return Max;
  }
  private double getMin(double[] data) {
  //Obtener el valor MENOR del Arreglo
    if (data.length < 1) throw new RuntimeException("Arreglo de datos vacío");
    double Min = DOUBLE_MAX_VALUE;
    for (int i = 0; i < data.length; i++) {
      if ((data[i] != Double.NEGATIVE_INFINITY) && !Double.isNaN(data[i]) 
          && (data[i] != Double.POSITIVE_INFINITY)) {
        if (Min > data[i]) {
          Min = data[i];
        }
      }
    }
    return Min;
  }
  private double getMinGreaterThanZero(double[] data) {
  //Obtener el valor MENOR del Arreglo Mayor que Cero
    if (data.length < 1) throw new RuntimeException("Arreglo de datos vacío");
    double Min = DOUBLE_MAX_VALUE;
    for (int i = 0; i < data.length; i++) {
      if (!Double.isInfinite(data[i]) && !Double.isNaN(data[i]) && (data[i] > 0)) {
        if (Min > data[i]) {
          Min = data[i];
        }
      }
    }
    return Min;
  }
  private double getMaxStep(double[] data) {
  //Obtener el valor Paso o Delta Máximo entre dos muestras consecutivas del Arreglo
    if (data.length < 1) throw new RuntimeException("Arreglo de datos con muestras menores a dos");
    double Max = DOUBLE_MIN_VALUE;
    for (int i = 0; i < data.length - 1; i++) {
      if (data[i] < data[i+1]) {
        if (Max < (data[i+1] - data[i])) {
          Max = (data[i+1] - data[i]);
        }
      }
      if (data[i] > data[i+1]) {
        if (Max < (data[i] - data[i+1])) {
          Max = (data[i] - data[i+1]);
        }
      }
    }
    return Max;
  }
  private double getMinStep(double[] data) {
  //Obtener el valor Paso o Delta Mínimo entre dos muestras consecutivas del Arreglo
    if (data.length < 1) throw new RuntimeException("Arreglo de datos con muestras menores a dos");
    double Min = DOUBLE_MAX_VALUE;
    for (int i = 0; i < data.length - 1 ;i++) {
      if (data[i] < data[i+1]) {
        if (Min > (data[i+1] - data[i])) {
          Min = (data[i+1] - data[i]);
        }
      }
      if (data[i] > data[i+1]) {
        if (Min > (data[i] - data[i+1])) {
          Min= (data[i] - data[i+1]);
        }
      }
    }
    return Min;
  }
  
  public int getNumStoredVbles() {
  //Retorna el número de variables que hay almacenadas
    return arlName.size();
  }
  
  private boolean addValueVble(String nVble, double valX, double valY) {
  //Agrega una valor a una variable a Graficar llamada nVble
    if (!arlName.contains(nVble)) throw new RuntimeException("La Variable no existe");
    if (Double.isInfinite(valX) || Double.isNaN(valX)) throw new RuntimeException("Valor Horizontal Inoperable (NaN o Infinito)");
    int indx = arlName.indexOf(nVble);
    double[] aX = getVbleDataX(nVble);
    double[] aY = getVbleDataY(nVble);
    
    if (isValueIncluded(valX,aX)) throw new RuntimeException("Valor repetido en Coordenada Horizontal");
    
    double[] aX_ValX = new double[aX.length+1];
    System.arraycopy(aX, 0, aX_ValX, 0, aX.length);
    aX_ValX[aX.length] = valX;     //Inserta el nuevo valor al final
    double[] aY_ValY = new double[aY.length+1];
    System.arraycopy(aY, 0, aY_ValY, 0, aY.length);
    aY_ValY[aY.length] = valY;     //Inserta el nuevo valor al final
    
    bubbleSortPairs(aX_ValX,aY_ValY);     //Reordena los arreglos aX_ValX y aY_ValY, siendo aX_ValX independiente y creciente
    arlX.set(indx, aX_ValX);
    arlY.set(indx, aY_ValY);
    
    return true;
  }
  
  public void setLabelVble(String nVble, String lbl) {
  //Establece el Render de la Variable llamada nVble
    if (!arlName.contains(nVble)) throw new RuntimeException("La Variable no existe");
    int indx = arlName.indexOf(nVble);
    arlLabel.set(indx, lbl);
  }
  public void setRenderVble(String nVble, BasicStroke bs) {
  //Establece el Render de la Variable llamada nVble
    if (!arlName.contains(nVble)) throw new RuntimeException("La Variable no existe");
    int indx = arlName.indexOf(nVble);
    arlBscStrk.set(indx, bs);
  }
  
  public void viewAll() {
  //Muestra todas las graficas almacenadas en los vectores
    for (int indx = 0; indx < arlName.size(); indx++) {
      arlView.set(indx, Boolean.TRUE);
    }
  }
  public void hideAll() {
  //Oculta todas las graficas almacenadas en los vectores
    for (int indx = 0; indx < arlName.size(); indx++) {
      arlView.set(indx, Boolean.FALSE);
    }
  }
  public void viewVble(String nVble) {
  //Muestra la grafica de la Variable llamada nVble
    if (!arlName.contains(nVble)) throw new RuntimeException("La Variable no existe");
    int indx = arlName.indexOf(nVble);
    arlView.set(indx, true);
  }
  public void hideVble(String nVble) {
  //Oculta la grafica de la Variable llamada nVble
    if (!arlName.contains(nVble)) throw new RuntimeException("La Variable no existe");
    int indx = arlName.indexOf(nVble);
    arlView.set(indx, false);
  }
  public int addVble(String nVble, double[] aX, double[] aY, Color color) {
  //Agrega una variable a Graficar, llamada nVble, de Color VbleColor y Grosor zDepth
    if (arlName.contains(nVble)) throw new RuntimeException("La Variable ya existe");
    if(aX.length != aY.length) throw new RuntimeException("Talla de arreglos diferentes");
    removeInoperableHorzPairs(aX,aY);
    if(aY.length == 0) throw new RuntimeException("Arreglos de datos vacíos");
    if (valueRepeated(aX)) throw new RuntimeException("Valor repetido en Coordenada Horizontal");
    arlName.add(nVble);
//    sortPairs(aX,aY);     //Ordena los arreglos aX y aY, tomando a aX como independiente y creciente
    quickSortPairs(aX, aY, 0, aX.length - 1);     //Ordena los arreglos aX y aY, tomando a aX como independiente y creciente
    arlX.add(aX);
    arlY.add(aY);
    arlColor.add(color);
    arlBscStrk.add(new BasicStroke(1.0f));
    arlLabel.add(nVble);
    arlView.add(true);
    
    return arlName.size();
  }
  public int delVble(String nVble) {
  //Elimina una variable a Graficar llamada nVble
    if (!arlName.contains(nVble)) throw new RuntimeException("La Variable no existe");
    int indx = arlName.indexOf(nVble);
    arlName.remove(indx);
    arlX.remove(indx);
    arlY.remove(indx);
    arlColor.remove(indx);
    arlBscStrk.remove(indx);
    arlLabel.remove(indx);
    arlView.remove(indx);
    return arlName.size();
  }
  public int delAllVble() {
  //Elimina una variable a Graficar llamada nVble
    if (!arlName.isEmpty()) {//throw new RuntimeException("No hay variables"); 
      arlName.clear();
      arlX.clear();
      arlY.clear();
      arlColor.clear();
      arlBscStrk.clear();
      arlLabel.clear();
      arlView.clear();

      minCoordinateX = DOUBLE_MAX_VALUE;  //  Mínimo Valor de Y
      maxCoordinateX = DOUBLE_MIN_VALUE;  //  Máximo Valor de X
      minCoordinateY = DOUBLE_MAX_VALUE;  //  Mínimo Valor de Y
      maxCoordinateY = DOUBLE_MIN_VALUE;  //  Máximo Valor de Y
    }
    return getNumStoredVbles();
  }
  
  private double[] getVbleDataX(String nVble) {
  //Retorna el arreglo de datos X llamada nVble
    if (!arlName.contains(nVble)) throw new RuntimeException("La Variable no existe");
    int indx = arlName.indexOf(nVble);
    return arlX.get(indx);
  }
  private double[] getVbleDataY(String nVble) {
  //Retorna el arreglo de datos Y llamada nVble
    if (!arlName.contains(nVble)) throw new RuntimeException("La Variable no existe");
    int indx = arlName.indexOf(nVble);
    return arlY.get(indx);
  }
  
  private void getWiderIndicatorY() {
    //Only used in plotAll (getBounds)
    //Returns the Wide Value of wider Indicator Number
    g2d.setFont(indicatorFont);
    FontMetrics fm = g2d.getFontMetrics();
    double dStpY = (dWorkMaxY - dWorkMinY)/gridDivVertY;
    if (dStpY < 0.0000001) {
      dStpY = 0.0000001; //Avoid Infinite Loop
    }
    double i = dWorkMinY;
    int maxStringWide = 0;
    while (i <= dWorkMaxY) {
      double I = fracVal(i,iFracY);
      maxStringWide = Math.max(maxStringWide, fm.stringWidth(String.valueOf(I)));
      i += dStpY;
    }
    maxStringWide += (int)(1.5*(double)fm.getHeight());
    maxStringWide += fm.stringWidth("w");
    maxStringWideIndicVert = maxStringWide;
  }
  private void getWiderIndicatorX() {
    //Only used in plotAll (getBounds)
    //Returns the Wide Value of wider Indicator Number
    g2d.setFont(indicatorFont);
    FontMetrics fm = g2d.getFontMetrics();
    double dStpX = (dWorkMaxX - dWorkMinX)/gridDivHorzX;
    double i = dWorkMinX;
    int maxStringWide = 0;
    while (i <= dWorkMaxX) {
      double I = fracVal(i,iFracX);
      maxStringWide = Math.max(maxStringWide, fm.stringWidth(String.valueOf(I)));
      i += dStpX;
    }
    maxStringWide += fm.getHeight();
    maxStringWide += fm.stringWidth("w");
    maxStringHighTitleHorz = maxStringWide;
  }
  
  private void getHighTitleX() {
    //Only used in plotAll (getBounds)
    g2d.setFont(titleFont);
    FontMetrics fm = g2d.getFontMetrics();
    maxStringHighTitleHorz = (fm.getHeight() + 2*fm.getDescent());
  }
  private void getHighVbleX() {
    //Only used in plotAll (getBounds)
    g2d.setFont(indicatorFont);
    FontMetrics fm = g2d.getFontMetrics();
    maxStringHighVbleHorz = fm.getHeight();
    g2d.setFont(variableFont);
    fm = g2d.getFontMetrics();
    maxStringHighVbleHorz += (fm.getHeight() + 2*fm.getDescent());
  }

  private void paintGridX() {
    //Only used in plotAll 
    g2d.setFont(indicatorFont);
    FontMetrics fm = g2d.getFontMetrics();
    g2d.setStroke(strkDashedLin);
    double lblNumbIndic = 0.0;  //LabelNumberIndicator
    if (bLogRespVertY) {
      //<editor-fold defaultstate="collapsed" desc="bLogRespVertY = true">//
      if (bLogGridVertY) {
        //<editor-fold defaultstate="collapsed" desc="bLogGridVertY = true">//
        double maxPivoteRef = getBaseLoPowValue(dWorkMaxY, 10.0);
        double minPivoteRef = maxPivoteRef - 2.0;
        double ind;
        double PivoteRef = minPivoteRef;
        while (PivoteRef <= maxPivoteRef) {
          ind = Math.pow(10.0,PivoteRef);
          for (int j = 1; j < 10; j++) {
            double val = ind*j;
            double i = Math.log(val);
            if (dWorkMinY <= val && val <= dWorkMaxY) {
              lblNumbIndic = fracVal(val,iFracY);
              if (j == 1) {
                g2d.setStroke(strkDashedPiv);
                g2d.setColor(midGridColor);
              } else {
                g2d.setStroke(strkDashedSec);
                g2d.setColor(midGridColor);
              }
              g2d.drawLine(displacementHorz + beginCoordinateX, displacementVert 
                  + getPosY(i), displacementHorz + finalCoordinateX, 
                  displacementVert + getPosY(i)); //Horizontal Indicator Bar Division
              g2d.setStroke(strkDashedLin);
              g2d.setColor(indNumbColor);
              if (j == 1) {
                g2d.drawString(String.valueOf(lblNumbIndic), 
                    displacementHorz + beginCoordinateX - fm.stringWidth(String.valueOf(lblNumbIndic)) - 5, 
                    displacementVert + getPosY(i) + (fm.getAscent() - fm.getDescent())/2); //Value Indicator
              }
            }
          }
          PivoteRef += 1.0;
        }
        //</editor-fold>
      } else {
        //<editor-fold defaultstate="collapsed" desc="bLogGridVertY = false">//
        double dStpY = (dLogWorkMaxY - dLogWorkMinY)/gridDivVertY;
        double i = dLogWorkMinY;
        while (i <= dLogWorkMaxY) {
          double p = Math.pow(dLogRespVertY, i);
          if (bScaleY) {
            p *= dScaleY;
          }
          lblNumbIndic = fracVal(p,iFracY);
          g2d.setColor(intGridColor);
          g2d.drawLine(
              displacementHorz + beginCoordinateX, 
              displacementVert + getPosY(i), 
              displacementHorz + finalCoordinateX, 
              displacementVert + getPosY(i)); //Horizontal Indicator Bar Division
          g2d.setColor(indNumbColor);
          g2d.drawString(String.valueOf(lblNumbIndic), 
              displacementHorz + beginCoordinateX - fm.stringWidth(String.valueOf(lblNumbIndic)) - 5, 
              displacementVert + getPosY(i) + (fm.getAscent() - fm.getDescent())/2); //Value Indicator
          i += dStpY;
        }
        double p = Math.pow(dLogRespVertY, dLogWorkMaxY);
        if (bScaleY) {
          p *= dScaleY;
        }
        lblNumbIndic = fracVal(p,iFracY);
        //</editor-fold>
      }
      //</editor-fold>
    } else {
      //<editor-fold defaultstate="collapsed" desc="bLogRespVertY = false">//
      double dStpY = (dWorkMaxY - dWorkMinY)/gridDivVertY;
      double i = dWorkMinY;
      while (i <= dWorkMaxY) {
        lblNumbIndic = fracVal(i,iFracY);
        if (bScaleY) {
          lblNumbIndic = fracVal(i*dScaleY,iFracY);
        }
        g2d.setColor(intGridColor);
        g2d.drawLine(
            displacementHorz + beginCoordinateX, displacementVert + getPosY(i), 
            displacementHorz + finalCoordinateX, displacementVert + getPosY(i)); //Horizontal Indicator Bar Division
        g2d.setColor(indNumbColor);
        g2d.drawString(String.valueOf(lblNumbIndic), 
            displacementHorz + beginCoordinateX - fm.stringWidth(String.valueOf(lblNumbIndic)) - 5, 
            displacementVert + getPosY(i) + (fm.getAscent() - fm.getDescent())/2); //Value Indicator
        i += dStpY;
      }
      lblNumbIndic = fracVal(dWorkMaxY,iFracY);
      if (bScaleY) {
        lblNumbIndic = fracVal(i*dScaleY,iFracY);
      }
      //</editor-fold>
    }
    //<editor-fold defaultstate="collapsed" desc="Horizontal Contour Division">//
    g2d.setStroke(new BasicStroke(1.5f));
    g2d.setColor(extGridColor);
    g2d.drawLine(
        displacementHorz + beginCoordinateX, displacementVert + beginCoordinateY, 
        displacementHorz + finalCoordinateX, displacementVert + beginCoordinateY);
    g2d.drawLine(
        displacementHorz + beginCoordinateX, displacementVert + finalCoordinateY, 
        displacementHorz + finalCoordinateX, displacementVert + finalCoordinateY);
    g2d.setColor(indNumbColor);
    g2d.drawString(String.valueOf(lblNumbIndic), 
        displacementHorz + beginCoordinateX - fm.stringWidth(String.valueOf(lblNumbIndic)) - 5, 
        displacementVert + finalCoordinateY + (fm.getAscent() - fm.getDescent())/2);
    //</editor-fold>
  }
  private void paintGridY() {
    //Only used in plotAll 
    g2d.setFont(indicatorFont);
    FontMetrics fm = g2d.getFontMetrics();
    g2d.setStroke(strkDashedLin);
    double lblNumbIndic = 0.0;  //LabelNumberIndicator
    if (bLogRespHorzX) {
      //<editor-fold defaultstate="collapsed" desc="bLogRespHorzX = true">//
      if (bLogGridHorzX) {
        //<editor-fold defaultstate="collapsed" desc="bLogGridHorzX = true">//
        double maxPivoteRef = getBaseLoPowValue(dWorkMaxX, 10.0);
        double minPivoteRef = maxPivoteRef - 2.0;
        double ind;
        double PivoteRef = minPivoteRef;
        while (PivoteRef <= maxPivoteRef) {
          ind = Math.pow(10.0,PivoteRef);
          for (int j = 1; j < 10; j++) {
            double val = ind*j;
            double i = Math.log(val);
            if (dWorkMinX <= val && val <= dWorkMaxX) {
              lblNumbIndic = fracVal(val,iFracX);
              if (j == 1) {
                g2d.setStroke(strkDashedPiv);
                g2d.setColor(midGridColor);
              } else {
                g2d.setStroke(strkDashedSec);
                g2d.setColor(midGridColor);
              }
              g2d.drawLine(
                  displacementHorz + getPosX(i), displacementVert + beginCoordinateY, 
                  displacementHorz + getPosX(i), displacementVert + finalCoordinateY); //Vertical Indicator Bar Division
              g2d.setStroke(strkDashedLin);
              g2d.setColor(indNumbColor);
              if (j == 1) {
                g2d.drawString(String.valueOf(lblNumbIndic),
                    displacementHorz + getPosX(i) - fm.stringWidth(String.valueOf(lblNumbIndic))/2,
                    displacementVert + beginCoordinateY + fm.getHeight()); //Value Indicator
              }
            }
          }
          PivoteRef += 1.0;
        }
        //</editor-fold>
      } else {
        //<editor-fold defaultstate="collapsed" desc="bLogGridHorzX = false">//
        double dStpX = (dLogWorkMaxX - dLogWorkMinX)/gridDivHorzX;
        double i = dLogWorkMinX;
        while (i <= dLogWorkMaxX) {
          double p = Math.pow(dLogRespHorzX, i);
          if (bScaleX) {
            p *= dScaleX;
          }
          lblNumbIndic = fracVal(p,iFracX);
          g2d.setColor(intGridColor);
          g2d.drawLine(
              displacementHorz + getPosX(i), displacementVert + beginCoordinateY, 
              displacementHorz + getPosX(i), displacementVert + finalCoordinateY); //Vertical Indicator Bar Division
          g2d.setColor(indNumbColor);
          g2d.drawString(String.valueOf(lblNumbIndic), 
              displacementHorz + getPosX(i) - fm.stringWidth(String.valueOf(lblNumbIndic))/2, 
              displacementVert + beginCoordinateY + fm.getHeight()); //Value Indicator
          i += dStpX;
        }
        double p = Math.pow(dLogRespHorzX, dLogWorkMaxX);
        if (bScaleX) {
          p *= dScaleX;
        }
        lblNumbIndic = fracVal(p,iFracX);
        //</editor-fold>
      }
      //</editor-fold>
    } else {
      //<editor-fold defaultstate="collapsed" desc="bLogRespHorzX = false">//
      double dStpX = (dWorkMaxX - dWorkMinX)/gridDivHorzX;
      double i = dWorkMinX;
      while (i <= dWorkMaxX) {
        lblNumbIndic = fracVal(i,iFracX);
        if (bScaleX) {
          lblNumbIndic = fracVal(i*dScaleX,iFracX);
        }
        g2d.setColor(intGridColor);
        g2d.drawLine(
            displacementHorz + getPosX(i), displacementVert + beginCoordinateY, 
            displacementHorz + getPosX(i), displacementVert + finalCoordinateY); //Vertical Indicator Bar Division
        g2d.setColor(indNumbColor);
        g2d.drawString(String.valueOf(lblNumbIndic), 
            displacementHorz + getPosX(i) - fm.stringWidth(String.valueOf(lblNumbIndic))/2, 
            displacementVert + beginCoordinateY + fm.getHeight()); //Indicative value
        i += dStpX;
      }
      lblNumbIndic = fracVal(dWorkMaxX,iFracX);
      if (bScaleX) {
        lblNumbIndic = fracVal(i*dScaleX,iFracX);
      }
      //</editor-fold>
    }
    //<editor-fold defaultstate="collapsed" desc="Vertical Contour Division">//
    g2d.setStroke(new BasicStroke(1.5f));
    g2d.setColor(extGridColor);
    g2d.drawLine(
        displacementHorz + beginCoordinateX, displacementVert + beginCoordinateY, 
        displacementHorz + beginCoordinateX, displacementVert + finalCoordinateY);
    g2d.drawLine(
        displacementHorz + finalCoordinateX, displacementVert + beginCoordinateY, 
        displacementHorz + finalCoordinateX, displacementVert + finalCoordinateY);
    g2d.setColor(indNumbColor);
    g2d.drawString(String.valueOf(lblNumbIndic), 
        displacementHorz + finalCoordinateX - fm.stringWidth(String.valueOf(lblNumbIndic))/2, 
        displacementVert + beginCoordinateY + fm.getHeight());
    //</editor-fold>
  }
  private void getPositionScreenBounds() {
    //Only used in plotAll
  //Buscar Posicion Inicial Vertical para Graficar
    getWiderIndicatorY();  //Indicator Numbers located Left in Vertical Axe
    getWiderIndicatorX();  //Indicator Numbers located Low in Horizontal Axe
    getHighTitleX();
    getHighVbleX();
    if (!arlName.isEmpty()) {
      beginCoordinateX = borderHorz + maxStringWideIndicVert;  //Lefter
      beginCoordinateY = yLarge - borderVert - maxStringHighVbleHorz;//(Largo - 20) - 30 = Largo - 50  //Down Position, Greater Value
//      finalCoordinateX = Ancho - borderHorz - maxStringWideIndicHorz/2;  //Righter
      finalCoordinateX = xWidth - borderHorz - maxWideBox;  //Righter
      finalCoordinateY = borderVert + maxStringHighTitleHorz;  //60 - 20 = 40  //Up Position, Lower Value
      halfCoordinateX = (finalCoordinateX + beginCoordinateX)/2;
      halfCoordinateY = (beginCoordinateY + finalCoordinateY)/2;
      /*
      System.out.println("getPositionScreenBounds.beginCoordinateX:" + beginCoordinateX);
      System.out.println("getPositionScreenBounds.beginCoordinateY:" + beginCoordinateY);
      System.out.println("getPositionScreenBounds.finalCoordinateX:" + finalCoordinateX);
      System.out.println("getPositionScreenBounds.finalCoordinateY:" + finalCoordinateY);
      System.out.println("getPositionScreenBounds.halfCoordinateX:" + halfCoordinateX);
      System.out.println("getPositionScreenBounds.halfCoordinateY:" + halfCoordinateY);
      */
    }
  }
  private void getMinMaxValueExtrems() {
    //Only used in plotAll 
    minCoordinateX = DOUBLE_MAX_VALUE;
    maxCoordinateX = DOUBLE_MIN_VALUE;
    minCoordinateY = DOUBLE_MAX_VALUE;
    maxCoordinateY = DOUBLE_MIN_VALUE;
    for (int indx = 0; indx < arlName.size(); indx++) {
      if(arlView.get(indx)) {
        if (bLogRespHorzX) {
          minCoordinateX = Math.min(minCoordinateX, getMinGreaterThanZero(arlX.get(indx)));
        } else {
          minCoordinateX = Math.min(minCoordinateX, getMin(arlX.get(indx)));
        }
        maxCoordinateX = Math.max(maxCoordinateX, getMax(arlX.get(indx)));
        
        if (bLogRespVertY) {
          minCoordinateY = Math.min(minCoordinateY, getMinGreaterThanZero(arlY.get(indx)));
        } else {
          minCoordinateY = Math.min(minCoordinateY, getMin(arlY.get(indx)));
        }
        maxCoordinateY = Math.max(maxCoordinateY, getMax(arlY.get(indx)));
      }
    }
    dWorkMinY = Math.max(DOUBLE_MIN_VALUE, minCoordinateY);
    dWorkMaxY = Math.min(DOUBLE_MAX_VALUE, maxCoordinateY);
    dWorkMaxX = Math.max(DOUBLE_MIN_VALUE, maxCoordinateX);
    dWorkMinX = Math.min(DOUBLE_MAX_VALUE, minCoordinateX);
    if (dWorkMaxY == dWorkMinY) {
      double dWorkY = dWorkMaxY;
      if (dWorkMinY != 0) {
        dWorkMinY -= Math.abs(dWorkY)/2.0;
        dWorkMaxY += Math.abs(dWorkY)/2.0;
      } else {
        dWorkMinY = -0.5;
        dWorkMaxY = 0.5;
      }
    }
    if (bUseMinY) {
      if (bLogRespVertY && (dReqMinY > 0)) {
        dWorkMinY = dReqMinY;
      }
      if (!bLogRespVertY) {
        dWorkMinY = dReqMinY;
      }
    }
    if (bUseMaxY) {
      dWorkMaxY = dReqMaxY;
    }
    if (bUseMinX) {
      if (bLogRespHorzX && (dReqMinX > 0)) {
        dWorkMinX = dReqMinX;
      }
      if (!bLogRespHorzX) {
        dWorkMinX = dReqMinX;
      }
    }
    if (bUseMaxX) {
      dWorkMaxX = dReqMaxX;
    }
    if (bLogRespVertY) {
      dLogWorkMinY = Math.log(dWorkMinY)/Math.log(dLogRespVertY);
      dLogWorkMaxY = Math.log(dWorkMaxY)/Math.log(dLogRespVertY);
    }
    if (bLogRespHorzX) {
      dLogWorkMaxX = Math.log(dWorkMaxX)/Math.log(dLogRespHorzX);
      dLogWorkMinX = Math.log(dWorkMinX)/Math.log(dLogRespHorzX);
    }
  }
  private void getDimsBoxLabel() {
    //Only used in plotAll
    int wideBox = 0;
    int highBox = 0;
    int viewVble = 0;
    g2d.setFont(labelFont);
    FontMetrics fm = g2d.getFontMetrics();
    int brdr = fm.stringWidth("w");
    for (int indx = 0; indx < arlName.size(); indx++) {
      if(arlView.get(indx)) {
        wideBox = Math.max(fm.stringWidth(arlLabel.get(indx)),wideBox);
        viewVble++;
      }
    }
    wideBox += brdr;  // Space Box and Widest Label
    wideBox += brdr;  // Space Widest Label and DemoLine
    wideBox += 16;  // Space Wide of DemoLine
    wideBox += brdr;  // Space DemoLine and Box
    wideBox += 2*brdr;  // Left and Right Space
    
    highBox += viewVble*(fm.getHeight());
    highBox += fm.getDescent();
    highBox += 2*brdr;  // Top and Bot Space
    
    maxWideBox = wideBox;
    maxHighBox = highBox;
  }
  private void plotBoxLabel() {
    //Only used in plotAll 
    g2d.setStroke(new BasicStroke((float)1.5));
    g2d.setColor(extGridColor);
    g2d.setFont(labelFont);
    FontMetrics fm = g2d.getFontMetrics();
    int brdr = fm.stringWidth("w");
    g2d.drawRect(displacementHorz + finalCoordinateX + brdr, 
        displacementVert + halfCoordinateY + brdr - maxHighBox/2, 
        maxWideBox - 2*brdr, maxHighBox - 2*brdr);
    int viewVble = 0;
    for (int indx = 0; indx < arlName.size(); indx++) {
      if(arlView.get(indx)) {
        int pX = displacementHorz + finalCoordinateX + 2*brdr;
        int pY = displacementVert + halfCoordinateY + 2*brdr - maxHighBox/2 
            + fm.getAscent()/2 + viewVble*(fm.getHeight());
        g2d.setColor(labelColor);
        g2d.drawString(arlLabel.get(indx), pX + 16 + brdr, pY);
        g2d.setColor(arlColor.get(indx));
        g2d.setStroke(arlBscStrk.get(indx));
        g2d.drawLine(
            pX, pY + 0*(fm.getAscent() - fm.getDescent())/2, 
            pX + 16, pY - (fm.getAscent() - fm.getDescent()));
        viewVble++;
      }
    }
  }
  private void plotTitleX() {
    //Only used in plotAll 
    g2d.setFont(titleFont);
    FontMetrics fm = g2d.getFontMetrics();
    int pX = displacementHorz + halfCoordinateX - fm.stringWidth(titleX)/2;  //Greater Value, Move to Right
    int pY = displacementVert + finalCoordinateY - fm.getHeight()/2;  //Greater Value, Move to Down
    g2d.setColor(titleColor);
    g2d.drawString(titleX, pX, pY);  //setNameTitleX
  }
  private void plotTitleY() {
    //Is not used
    g2d.setFont(titleFont);
    FontMetrics fm = g2d.getFontMetrics();
    g2d.setColor(titleColor);
    g2d.drawString(titleY, 
        displacementHorz - fm.stringWidth(titleY) - 5, 
        displacementVert + finalCoordinateY + fm.getHeight() + 5);  //setNameVbleY
  }
  private void plotNameVbleX() {
    //Only used in plotAll 
    int pY = displacementVert + beginCoordinateY ;  //Greater Value, Move to Down
    g2d.setFont(indicatorFont);
    FontMetrics fm = g2d.getFontMetrics();
    pY += fm.getHeight();
    g2d.setFont(variableFont);
    fm = g2d.getFontMetrics();
    int pX = displacementHorz + halfCoordinateX - fm.stringWidth(vbleX)/2;  //Greater Value, Move to Right
    pY += fm.getLeading();
    pY += fm.getAscent();
    g2d.setColor(vbleXColor);
    g2d.drawString(vbleX, pX, pY);
  }
  private void plotNameVbleY() {
    //Only used in plotAll 
    g2d.setFont(variableFont);
    FontMetrics fm = g2d.getFontMetrics();
    AffineTransform bt = g2d.getTransform();
    AffineTransform at = new AffineTransform();
    int pX = displacementHorz + fm.getHeight();  //Greater Value, Move to Right
    int pY = displacementVert + halfCoordinateY + fm.stringWidth(vbleY)/2;  //Greater Value, Move to Down
    at.setToRotation(-Math.PI/2.0, pX, pY);  //  Math.toRadians(-90)
    g2d.setTransform(at);
    g2d.setColor(vbleYColor);
    g2d.drawString(vbleY, pX, pY);
    g2d.setTransform(bt);
  }
  private void plotAll() {
  //Grafica todas las variables Visibles almacenadas en los vectores
    if (!arlName.isEmpty()) {
      getMinMaxValueExtrems();
      getDimsBoxLabel();
      getPositionScreenBounds();
      paintGridX();
      paintGridY();  //Paint Vertical Lines with Lower Indicators
//      plotTitleY(); //Dibuja el título TitleV
      plotNameVbleY();
      plotNameVbleX();
      plotTitleX();
      plotBoxLabel();
      for (int indx = 0; indx < arlName.size(); indx++) {
        if (arlView.get(indx)) { //Se debe visualizar la variable?
          plotVble(indx);
        }
      }
    }
  }
  private void plotVble(int indx) {
    //System.out.println("plotVble(" + indx + ")");
    //Only used in plotAll 
  //Recarga una gráfica para mostrarla
    Color color = arlColor.get(indx);
    double[] aX = arlX.get(indx);
    double[] aY = arlY.get(indx);
    g2d.setColor(color);
    g2d.setStroke(arlBscStrk.get(indx));
    if (aX.length == 1) {
      boolean bDoDraw = true;
      if (Double.isInfinite(aX[0]) || Double.isNaN(aX[0])) {
        bDoDraw = false;
      }
      double dX0 = aX[0];
      if (bLogRespHorzX) {
        if (!(aX[0] > 0)) {
          bDoDraw = false;
        } else {
          dX0 = Math.log(dX0)/Math.log(dLogRespHorzX);
        }
      }
      if (Double.isInfinite(aY[0]) || Double.isNaN(aY[0])) {
        bDoDraw = false;
      }
      double dY0 = aY[0];
      if (bLogRespVertY) {
        if (!(aY[0] > 0)) {
          bDoDraw = false;
        } else {
          dY0 = Math.log(dY0)/Math.log(dLogRespVertY);
        }
      }
      if (bDoDraw) {
        g2d.draw(new Line2D.Double(
            displacementHorz + getPosX(dX0), displacementHorz + getPosX(dX0), 
            displacementVert + getPosY(dY0), displacementVert + getPosY(dY0)));
      }
    } else {
      for (int j = 0; j < aY.length - 1; j++) {
        boolean bDoDraw = true;
        double dX0 = 0.0, dY0 = 0.0, dX1 = 0.0, dY1 = 0.0;
        if (Double.isInfinite(aX[j]) || Double.isNaN(aX[j])) {
          bDoDraw = false;
        } else {
          dX0 = aX[j];
        }
        if (bLogRespHorzX) {
          if (!(dX0 > 0)) {
            bDoDraw = false;
          } else {
            dX0 = Math.log(dX0)/Math.log(dLogRespHorzX);
          }
        }
        if (Double.isInfinite(aX[j+1]) || Double.isNaN(aX[j+1])) {
          bDoDraw = false;
        } else {
          dX1 = aX[j+1];
        }
        if (bLogRespHorzX) {
          if (!(dX1 > 0)) {
            bDoDraw = false;
          } else {
            dX1 = Math.log(dX1)/Math.log(dLogRespHorzX);
          }
        }
        if (Double.isInfinite(aY[j]) || Double.isNaN(aY[j])) {
          bDoDraw = false;
        } else {
          dY0 = aY[j];
        }
        if (bLogRespVertY) {
          if (!(dY0 > 0)) {
            bDoDraw = false;
          } else {
            dY0 = Math.log(dY0)/Math.log(dLogRespVertY);
          }
        }
        if (Double.isInfinite(aY[j+1]) || Double.isNaN(aY[j+1])) {
          bDoDraw = false;
        } else {
          dY1 = aY[j+1];
        }
        if (bLogRespVertY) {
          if (!(dY1 > 0)) {
            bDoDraw = false;
          } else {
            dY1 = Math.log(dY1)/Math.log(dLogRespVertY);
          }
        }
        
        if (bUseMaxX && bDoDraw) {
          if (!(aX[j+1] < dReqMaxX) || !(aX[j] < dReqMaxX)) {
            bDoDraw = false;
          }
        }
        if (bUseMinX && bDoDraw) {
          if (!(aX[j+1] > dReqMinX) || !(aX[j] > dReqMinX)) {
            bDoDraw = false;
          }
        }
        if (bUseMaxY && bDoDraw) {
          if (!(aY[j+1] < dReqMaxY) || !(aY[j] < dReqMaxY)) {
            bDoDraw = false;
          }
        }
        if (bUseMinY && bDoDraw) {
          if (!(aY[j+1] > dReqMinY) || !(aY[j] > dReqMinY)) {
            bDoDraw = false;
          }
        }
        if (bDoDraw) {
          g2d.drawLine(
              displacementHorz + getPosX(dX0), displacementVert + getPosY(dY0), 
              displacementHorz + getPosX(dX1), displacementVert + getPosY(dY1));
        }
      }
    }
//    System.out.println("aX:"+Arrays.toString(aX));
  }
  
  private void setNewMinMaxXY(int iniX, int iniY, int endX, int endY) {
    // Set new Min and Max values of HorzX and VertY according to provided rectangle
    Integer Xini, Xend, Yini, Yend;
    if (endX != 0 && endY != 0) {
      Xini = (iniX < beginCoordinateX)? beginCoordinateX : iniX;
      Xend = ((iniX + endX) > finalCoordinateX)? finalCoordinateX : (iniX + endX);
      Yini = ((iniY + endY) > beginCoordinateY)? beginCoordinateY : (iniY + endY);
      Yend = (iniY < finalCoordinateY)? finalCoordinateY : iniY;
      bUseMinX = true;
      bUseMaxX = true;
      bUseMinY = true;
      bUseMaxY = true;
      if (bLogRespHorzX) {
        if (bLogGridHorzX) {
//          dReqMinX = Math.pow(10.0, getValX(Xini));
//          dReqMaxX = Math.pow(10.0, getValX(Xend));
          dReqMinX = Math.pow(dLogRespHorzX, getValX(Xini));
          dReqMaxX = Math.pow(dLogRespHorzX, getValX(Xend));
        } else {
          dReqMinX = Math.pow(dLogRespHorzX, getValX(Xini));
          dReqMaxX = Math.pow(dLogRespHorzX, getValX(Xend));
        }
      } else {
        dReqMinX = getValX(Xini);
        dReqMaxX = getValX(Xend);
      }
      if (bLogRespVertY) {
        if (bLogGridVertY) {
//          dReqMinY = Math.pow(10.0, getValY(Yini));
//          dReqMaxY = Math.pow(10.0, getValY(Yend));
          dReqMinY = Math.pow(dLogRespVertY, getValY(Yini));
          dReqMaxY = Math.pow(dLogRespVertY, getValY(Yend));
        } else {
          dReqMinY = Math.pow(dLogRespVertY, getValY(Yini));
          dReqMaxY = Math.pow(dLogRespVertY, getValY(Yend));
        }
      } else {
        dReqMinY = getValY(Yini);
        dReqMaxY = getValY(Yend);
      }
//      output.resetRentangles();
      getChart();
    }
  }
  
  boolean hasPlottable() {
    return (!arlName.isEmpty());
  }
  
  public String[] getAllVblesName() {
  //Retorna un arreglo con los nombres de las variables almacenadas
    String[] Vbles = new String[arlName.size()];
    for (int i = 0; i < arlName.size();i++) {
      Vbles[i] = arlName.get(i);
    }
    return Vbles;
  }
  
  private double getValX(int x) {
    //Get cX value for x position
    double dx, cX;
    dx = (double)(x - beginCoordinateX)/(double)(finalCoordinateX - beginCoordinateX);
    if (bLogRespHorzX) {
      cX = dLogWorkMinX + (dLogWorkMaxX - dLogWorkMinX)*dx;
    } else {
      cX = dWorkMinX + (dWorkMaxX - dWorkMinX)*dx;
    }
    return cX;
  }
  private double getValY(int y) {
    //Get cY value for y position
    double dy, cY;
    dy = (double)(y - beginCoordinateY)/(double)(finalCoordinateY - beginCoordinateY);
    if (bLogRespVertY) {
      cY = dLogWorkMinY + (dLogWorkMaxY - dLogWorkMinY)*dy;
    } else {
      cY = dWorkMinY + (dWorkMaxY - dWorkMinY)*dy;
    }
    return cY;
  }
  
  private int getPosX(double x) {
    //Get cX position for x value
    double dx;
    if (bLogRespHorzX) {
      dx = (x - dLogWorkMinX)/(dLogWorkMaxX - dLogWorkMinX);
    } else {
      dx = (x - dWorkMinX)/(dWorkMaxX - dWorkMinX);
    }
    int cX = (int)(beginCoordinateX + (finalCoordinateX - beginCoordinateX)*dx);
    return cX;
  }
  private int getPosY(double y) {
    //Get cY position for y value
    double dy;
    if (bLogRespVertY) {
      dy = (y - dLogWorkMinY)/(dLogWorkMaxY - dLogWorkMinY);
    } else {
      dy = (y - dWorkMinY)/(dWorkMaxY - dWorkMinY);
    }
    int cY = (int)(beginCoordinateY - (beginCoordinateY - finalCoordinateY)*dy);
    return cY;
  }
  
  private double truncVal(double Valor, int Trunk) {
  //Trunca Valores, dejando número variable de decimales
    double NuevoValor = 0.0;
    if (Valor != 0.0) {
      double inv = 1/Valor;
      Math.log10(inv);
      Math.getExponent(Math.log10(inv));
      int Exp = (int)Math.floor(Math.log10(inv));
      double Pot = Math.pow(10, Exp+Trunk);
      NuevoValor = Math.floor(Valor*Pot)/Pot;
    }
    return NuevoValor;
  }
  
  private double fracVal(double value, int frac) {
    return Math.round(value * Math.pow(10.0, frac)) / Math.pow(10.0, frac);
  }
  
  private double getBaseLoPowValue(double value, double base) {
    Double loPow = null;
    if (value != 0.0) {
      if (value < 0.0 || base < 0.0) {
        throw new IllegalArgumentException("The Value and Base must be greater than zero");
      }
      loPow = Math.floor(Math.log(value)/Math.log(base));
    }
    return loPow;
  }
  
  public void repaintChart() {
    outputPanel.repaint();
  }
  
  private class IntPanel extends JPanel implements MouseInputListener {
    Rectangle currentRect = null;
    Rectangle rectToDraw = null;
    Rectangle prevRectDrawn = new Rectangle();
    MouseEvent meLast = null;
    Point pntLast = null;
    Color backColor;
    int xWidth;
    int yLarge;
    
    private void resetRentangles() {
      currentRect = null;
      rectToDraw = null;
      prevRectDrawn = new Rectangle();
    }

    public IntPanel(int xWidth, int yLarge, Color backColor) {
      this.xWidth = xWidth;
      this.yLarge = yLarge;
      this.backColor = backColor;
      /*x
      System.out.println("xWidth:"  + xWidth);
      System.out.println("yLarge:"  + yLarge);
      System.out.println("cColor:"  + cColor);
      */
      bi = new BufferedImage(xWidth, yLarge, BufferedImage.TYPE_INT_RGB);
      g2d = bi.createGraphics();
      /*
        "Overridable method call in constructor" solved removing super's method from 
        constructor was moved to paintComponent method.
        setSize(new java.awt.Dimension(xWidth, yLarge));
        setBackground(cColor);
//      */
      init();
    }
    
    private void init() {
      addMouseListener(this);
      addMouseMotionListener(this);
      paintPanel(g2d);
    }
    
    @Override public void paintComponent(Graphics g) {
      paintPanel(g);
    }
    
    public void paintPanel(Graphics g) {
//      System.out.println("\n IntPanel.paintComponent" + "\n\tTitleX:" + TitleX + "\n"
//      + "xWidth:" + xWidth + " - yLarge:" + yLarge);
      //System.out.println("\n paintPanel(Graphics g):0");
      setSize(new java.awt.Dimension(xWidth, yLarge));
      setBackground(backColor);
      super.paintComponent(g);
      if (bColorBack) {
        g2d.setColor(this.getBackground());
        g2d.fillRect(0, 0, xWidth, yLarge);
      }
      plotAll();
      g2d.dispose();
      g.drawImage(bi, 0, 0, null);
      
      //If currentRect exists, paint a box on top.
      if (currentRect != null) {
        //Draw a rectangle on top of the image.
        g.setXORMode(Color.WHITE); //Color of line varies depending on image colors
        int iX = rectToDraw.x;
        int iY = rectToDraw.y;
        int iW = rectToDraw.width - 1;
        int iL = rectToDraw.height - 1;
        g.drawRect(iX, iY, iW, iL);
        //Call the Outter Method for Zoom
        if (meLast.getID() == MouseEvent.MOUSE_RELEASED) {
          currentRect = null;
          rectToDraw = null;
          meLast = null;
          setNewMinMaxXY(iX, iY, iW, iL);
        }
      }
    }
    
    @Override public void mouseDragged(MouseEvent e) {
      //Invoked when a mouse button is pressed on a component and then dragged.
      meLast = e;
//      System.out.println("mouseDragged(x:" + e.getX() + " , y:"+ e.getY() + ")");
      if ((pntLast.x != meLast.getPoint().x) && (pntLast.y != meLast.getPoint().y)) {
        // Only call updateSize when the Location was changed
          currentRect = new Rectangle(pntLast.x, pntLast.y, 0, 0);
          updateDrawableRect(getWidth(), getHeight());
          repaint();
        updateSize(e);
      }
    }
    
    @Override public void mousePressed(MouseEvent e) {
      //Invoked when a mouse button has been pressed on a component.
      meLast = e;
//      System.out.println("mousePressed(x:" + e.getX() + " , y:"+ e.getY() + ")");
      pntLast = meLast.getPoint();
    }
    
    @Override public void mouseReleased(MouseEvent e) {
      //Invoked when a mouse button has been released on a component.
      meLast = e;
//      System.out.println("mouseReleased(x:" + e.getX() + " , y:"+ e.getY() + ")");
      if ((pntLast.x != meLast.getPoint().x) && (pntLast.y != meLast.getPoint().y)) {
        // Only call updateSize when the Location was changed
        updateSize(e);
      }
      pntLast = null;
    }
    
    
    void updateSize(MouseEvent e) {
    /** 
     * Update the size of the current rectangle and call repaint.
     * Because currentRect always has the same origin, translate it if 
     * the width or height is negative.
     * 
     * For efficiency (though that isn't an issue for this program),
     * specify the painting region using arguments to the repaint() call.
     */
      currentRect.setSize(e.getX() - currentRect.x, e.getY() - currentRect.y);
      updateDrawableRect(getWidth(), getHeight());
      Rectangle totalRepaint = rectToDraw.union(prevRectDrawn);
      repaint(totalRepaint.x, totalRepaint.y, totalRepaint.width,
          totalRepaint.height);
    }
    
    private void updateDrawableRect(int compWidth, int compLarge) {
      int posX = currentRect.x;
      int posY = currentRect.y;
      int width = currentRect.width;
      int large = currentRect.height;
      
      //Make the width and height positive, if necessary.
      if (width < 0) {
        width = 0 - width;
        posX = posX - width + 1;
        if (posX < 0) {
          width += posX;
          posX = 0;
        }
      }

      if (large < 0) {
        large = 0 - large;
        posY = posY - large + 1;
        if (posY < 0) {
          large += posY;
          posY = 0;
        }
      }
      
      //The rectangle shouldn't extend past the drawing area.
      if ((posX + width) > compWidth) {
        width = compWidth - posX;
      }

      if ((posY + large) > compLarge) {
        large = compLarge - posY;
      }

      //Update rectToDraw after saving old value.
      if (rectToDraw != null) {
        prevRectDrawn.setBounds(rectToDraw.x, rectToDraw.y,
            rectToDraw.width, rectToDraw.height);
        rectToDraw.setBounds(posX, posY, width, large);
      } else {
        rectToDraw = new Rectangle(posX, posY, width, large);
      }
    }
    
    @Override public void mouseClicked(MouseEvent e) {
      //Invoked when the mouse button has been clicked (pressed and released) on a component.
//      System.out.println("mouseClicked(x:" + e.getX() + " , y:"+ e.getY() + ")");
    }
    
    @Override public void mouseEntered(MouseEvent e) {
      //Invoked when the mouse enters a component.
    }
    
    @Override public void mouseExited(MouseEvent e) {
      //Invoked when the mouse exits a component.
    }
    
    @Override public void mouseMoved(MouseEvent e) {
      //Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
    }

  }
}