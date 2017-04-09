package br.com.margel.gp.view.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconUtils {

	private static final String IMAGES_DIR = "img/";
	
	public static ImageIcon getIcon(String img){
		try {
			return new ImageIcon(IconUtils.class.getClassLoader().getResource(IMAGES_DIR+img));
		} catch (Exception e) {
			System.err.println("Falha ao carregar icone: "+img);
			return null;
		}
	}
	
	public static BufferedImage getBufferedImage(String img){
		try {
			return ImageIO.read(IconUtils.class.getClassLoader().getResource(IMAGES_DIR+img));
		} catch (Exception e) {
			System.err.println("Falha ao carregar buffered: "+img);
			return null;
		}
	}

	public static Image getWindowIcon() {
		return getIcon("logo.png").getImage();
	}
}
