package com.friends.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 生成二维码
 * 
 * @author pengyl
 *
 */
public class MatrixUtil {
	private static final String CHARSET = "utf-8";
	private static final int RED = 0xff2121;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * 生成二维码
	 * 
	 * @param text
	 * @param background
	 * @param logo
	 * @param format
	 * @param qrPath
	 * @throws IOException
	 */
	public static void writeToFile(String text, String background, String logo, String format, File qrPath) throws IOException {
		int width = 525;
		int height = 277;
		// LOGO
		BufferedImage logoImg = scale(logo, 50, 50, true);
		int[][] logoMatrix = to2DMatrix(logoImg);
		// 将背景图片转换为矩阵
		BufferedImage backgroundImg = scale(background, height, width, true);
		int[][] backgroundMatrix = to2DMatrix(backgroundImg);
		// 生成二维码备用 140*140
		BufferedImage qrImg = toBufferedImage(toQRCodeMatrix(text, 170, 170));
		int[][] qrMatrix = to2DMatrix(qrImg);

		// 重构图片：将二维码矩阵嵌入背景图中
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x > 175 && x < 340 && y > 83 && y < 248) {
					if (x > 235 && x < 284 && y > 145 && y < 184) {
						image.setRGB(x, y, logoMatrix[x - 235][y - 140]);
					} else {
						image.setRGB(x, y, qrMatrix[x - 175][y - 83]);
					}
				} else {
					image.setRGB(x, y, backgroundMatrix[x][y]);
				}
			}
		}
		if (!ImageIO.write(image, format, qrPath)) {
			throw new IOException("Could not write an image of format " + format + " to " + qrPath);
		}
	}

	/**
	 * 对图片进行缩放
	 * 
	 * @param srcImageFile
	 *            LOGO图片
	 * @param height
	 * @param width
	 * @param hasFiller
	 *            是否补白
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller) throws IOException {
		double ratio = 0.0; // 缩放比例
		File file = new File(srcImageFile);
		BufferedImage srcImage = ImageIO.read(file);
		Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}
		if (hasFiller) {
			destImage = filler(width, height, destImage);
		}
		return (BufferedImage) destImage;
	}

	/**
	 * 补白
	 * 
	 * @param width
	 * @param height
	 * @param srcImage
	 * @return
	 */
	public static Image filler(int width, int height, Image srcImage) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = image.createGraphics();
		// 设置透明 new Color(0,0,0,0);
		graphic.setColor(Color.white);
		graphic.fillRect(0, 0, width, height);
		// 将缩放后的图片绘制到矩阵中
		if (width == srcImage.getWidth(null)) {
			graphic.drawImage(srcImage, 0, (height - srcImage.getHeight(null)) / 2, srcImage.getWidth(null), srcImage.getHeight(null), Color.white, null);
		} else {
			graphic.drawImage(srcImage, (width - srcImage.getWidth(null)) / 2, 0, srcImage.getWidth(null), srcImage.getHeight(null), Color.white, null);
		}
		graphic.dispose();
		return image;
	}

	/**
	 * 将图片转换为二维矩阵
	 * 
	 * @param img
	 * @return
	 */
	public static int[][] to2DMatrix(BufferedImage img) {
		int[][] matrix = new int[img.getWidth()][img.getHeight()];
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				matrix[i][j] = img.getRGB(i, j);
			}
		}
		return matrix;
	}

	/**
	 * 根据二维码矩阵生成二维码
	 * 
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? RED : WHITE);
			}
		}
		return image;
	}

	/**
	 * 生成二维码矩阵
	 * 
	 * @param text
	 * @param width
	 * @param height
	 * @return
	 */
	public static BitMatrix toQRCodeMatrix(String text, Integer width, Integer height) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);// 内容所使用编码
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 容错率
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = null;

		try {
			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return bitMatrix;
	}

	/**
	 * 解码
	 */
	public static String decode(File file) {
		BufferedImage image;
		try {
			if (file == null || file.exists() == false) {
				throw new Exception(" File not found:" + file.getPath());
			}

			image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result result;

			// 解码设置编码方式为：utf-8，
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
