package com.tgb.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.swetake.util.Qrcode;
  
/** 
 * ��ά�빤���� 
 *  
 */  
public class QRCodeUtil {  
  
    private static final String CHARSET = "utf-8";  
    private static final String FORMAT_NAME = "JPG";  
    // ��ά��ߴ�  
    private static final int QRCODE_SIZE = 200;  
    // LOGO���  
    private static final int WIDTH = 60;  
    // LOGO�߶�  
    private static final int HEIGHT = 60;  
  
    private static BufferedImage createImage(String content, String imgPath,  
            boolean needCompress) throws Exception {  
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
        hints.put(EncodeHintType.MARGIN, 1);  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);  
        int width = bitMatrix.getWidth();  
        int height = bitMatrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000  
                        : 0xFFFFFFFF);  
            }  
        }  
        if (imgPath == null || "".equals(imgPath)) {  
            return image;  
        }  
        // ����ͼƬ  
        if(imgPath!=null){
            QRCodeUtil.insertImage(image, imgPath, needCompress);  
        }
        return image;  
    }  
  
    /** 
     * ����LOGO 
     *  
     * @param source 
     *            ��ά��ͼƬ 
     * @param imgPath 
     *            LOGOͼƬ��ַ 
     * @param needCompress 
     *            �Ƿ�ѹ�� 
     * @throws Exception 
     */  
    private static void insertImage(BufferedImage source, String imgPath,  
            boolean needCompress) throws Exception {  
        File file = new File(imgPath);  
        if (!file.exists()) {  
            System.err.println(""+imgPath+"   ���ļ������ڣ�");  
            return;  
        }  
        Image src = ImageIO.read(new File(imgPath));  
        int width = src.getWidth(null);  
        int height = src.getHeight(null);  
        if (needCompress) { // ѹ��LOGO  
            if (width > WIDTH) {  
                width = WIDTH;  
            }  
            if (height > HEIGHT) {  
                height = HEIGHT;  
            }  
            Image image = src.getScaledInstance(width, height,  
                    Image.SCALE_SMOOTH);  
            BufferedImage tag = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics g = tag.getGraphics();  
            g.drawImage(image, 0, 0, null); // ������С���ͼ  
            g.dispose();  
            src = image;  
        }  
        // ����LOGO  
        Graphics2D graph = source.createGraphics();  
        int x = (QRCODE_SIZE - width) / 2;  
        int y = (QRCODE_SIZE - height) / 2;  
        graph.drawImage(src, x, y, width, height, null);  
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);  
        graph.setStroke(new BasicStroke(3f));  
        graph.draw(shape);  
        graph.dispose();  
    }  
  
    /** 
     * ���ļ��в�����ʱ��mkdirs���Զ��������Ŀ¼��������mkdir��(mkdir�����Ŀ¼����������׳��쳣) 
     * @author lanyuan 
     * Email: mmm333zzz520@163.com 
     * @date 2013-12-11 ����10:16:36 
     * @param destPath ���Ŀ¼ 
     */  
    public static void mkdirs(String destPath) {  
        File file =new File(destPath);      
        //���ļ��в�����ʱ��mkdirs���Զ��������Ŀ¼��������mkdir��(mkdir�����Ŀ¼����������׳��쳣)  
        if (!file.exists() && !file.isDirectory()) {  
            file.mkdirs();  
        }  
    }  
    /** 
     * ���ɶ�ά��(��ǶLOGO) 
     *  
     * @param content 
     *            ���� 
     * @param imgPath 
     *            LOGO��ַ 
     * @param destPath 
     *            ���Ŀ¼ 
     * @param needCompress 
     *            �Ƿ�ѹ��LOGO 
     * @throws Exception 
     */  
    public static void encode(String content, String imgPath, String destPath,  
            boolean needCompress) throws Exception {  
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,  
                needCompress);  
        mkdirs(destPath);  
        String file = new Random().nextInt(99999999)+".jpg";  
        ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));  
    }  
    /** 
     * ���ɶ�ά��(��ǶLOGO)  
     *  
     * @param content 
     *            ���� 
     * @param imgPath 
     *            LOGO��ַ 
     * @param output 
     *            ����� 
     * @param needCompress 
     *            �Ƿ�ѹ��LOGO 
     * @throws Exception 
     */  
    public static void encode(String content, String logoPath,  
            OutputStream output, boolean needCompress) throws Exception {  
        BufferedImage image = QRCodeUtil.createImage(content, logoPath,  
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);  
    }  

  
    /** 
     * ������ά�� 
     *  
     * @param file 
     *            ��ά��ͼƬ 
     * @return 
     * @throws Exception 
     */  
    public static String decode(File file) throws Exception {  
        BufferedImage image;  
        image = ImageIO.read(file);  
        if (image == null) {  
            return null;  
        }  
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(  
                image);  
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
        Result result;  
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();  
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);  
        result = new MultiFormatReader().decode(bitmap, hints);  
        String resultStr = result.getText();  
        return resultStr;  
    }  
  
  
    public static void main(String[] args) throws Exception {  
        String text = "���ƿɷ���ʽ��������Ҫ������䣡000000000000000";  
        QRCodeUtil.encode(text, "c:/df.jsp", "c:/a/", true);  
    }   
  
    //@Test
    public void testss() throws Exception{
    	   //���ɶ�ά��
    	   String text = "��000000000000000";  
           QRCodeUtil.encode(text, null, "D:/erweima/", true);  
           
    	//����
          //String string= QRCodeUtil.decode(new File("D:/erweima/24677622.jpg"));
          //System.out.println(string);
    }
    
    private static int UNIT_WIDTH = 2;
    /**
	 * ���ɶ�ά��(QRCode)ͼƬ�Ĺ�������
	 * 
	 * @param content
	 *            �洢����
	 * @param imgType
	 *            ͼƬ����
	 * @param size
	 *            ��ά��ߴ�
	 * @return
	 */
	public static BufferedImage qRCodeCommon(String content,int size,String logoPath) {
		BufferedImage bufImg = null;
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// ���ö�ά���Ŵ��ʣ���ѡL(7%)��M(15%)��Q(25%)��H(30%)���Ŵ���Խ�߿ɴ洢����ϢԽ�٣����Զ�ά�������ȵ�Ҫ��ԽС
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			// �������ö�ά��ߴ磬ȡֵ��Χ1-40��ֵԽ��ߴ�Խ�󣬿ɴ洢����ϢԽ��
			qrcodeHandler.setQrcodeVersion(size);
			// ������ݵ��ֽ����飬���ñ����ʽ
			byte[] contentBytes = content.getBytes("utf-8");
			// ͼƬ�ߴ�
			int imgSize = (67 + 12 * (size - 1))*UNIT_WIDTH;
			bufImg = new BufferedImage(imgSize, imgSize,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			// ���ñ�����ɫ
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// �趨ͼ����ɫ> BLACK
			gs.setColor(Color.BLACK);
			// ����ƫ�����������ÿ��ܵ��½�������
			int pixoff = 2;
			// �������> ��ά��
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j*3*UNIT_WIDTH + pixoff, i*3*UNIT_WIDTH + pixoff, 3*UNIT_WIDTH, 3*UNIT_WIDTH);
						}
					}
				}
			} else {
				throw new Exception("QRCode content bytes length = "
						+ contentBytes.length + " not in [0, 800].");
			}
			gs.dispose();
			bufImg.flush();
			/*if(logoPath!=null&&!"".equals(logoPath)){
				insertImage(bufImg,5,logoPath,true);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImg;
	}
	/**
	 * ��ά�����logo����
	 * @param source
	 * @param imageSize
	 * @param imgPath
	 * @param needCompress
	 * @throws Exception
	 */
	public static void insertImage(BufferedImage source,int size,String imgPath,
            boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(""+imgPath+"   ���ļ������ڣ�");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // ѹ��LOGO
        	// LOGO���
            final int WIDTH = source.getWidth()/size;
            // LOGO�߶�
            final int HEIGHT = source.getHeight()/size;
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // ������С���ͼ
            g.dispose();
            src = image;
        }
        // ����LOGO
        Graphics2D graph = source.createGraphics();
        int x = (source.getWidth() - width) / 2;
        int y = (source.getHeight() - height) / 2;
        graph.drawImage(src, x, y, width, height, Color.WHITE , null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
} 
