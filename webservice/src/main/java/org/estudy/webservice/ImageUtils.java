 
package org.estudy.webservice;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

/**
 * Created by The eXo Platform SARL
 * Author : Dang Van Minh
 *          minh.dang@exoplatform.com
 * Oct 15, 2008 2:18:02 PM
 */
/**
 * Process images class
 */
public class ImageUtils {

  /**
   * Return the image which resized(support JPG, PNG, GIF)
   * @param image The BufferedImage which based on InputStream
   * BufferedImage image = ImageIO.read(InputStream)
   * @param maxWidth Max width of thumbnail will be resized
   * @param maxHeight Max height of thumbnail will be resized
   * @return InputStream
   * @throws Exception
   */
  public static InputStream scaleImage(BufferedImage image, int maxWidth, int maxHeight) throws Exception {
    return scaleImage(image, maxWidth, maxHeight, false);
  }

  /**
   * Return the image which resized(support JPG, PNG, GIF)
   * @param image The BufferedImage which based on InputStream
   * BufferedImage image = ImageIO.read(InputStream)
   * @param maxWidth Max width of thumbnail will be resized
   * @param maxHeight Max height of thumbnail will be resized
   * @return InputStream
   * @throws Exception
   */  
  public static InputStream scaleImage(BufferedImage image, int maxWidth, int maxHeight, boolean crop) throws Exception {
    // Make sure the aspect ratio is maintained, so the image is not skewed
    BufferedImage thumbImage = null;
    if(crop) {
      thumbImage = Scalr.crop(image, maxWidth, maxHeight);
    } else {
      if (maxHeight == 0) {
        //Just used in custom size case. When 0 as specified as height then it will be calculated automatically to fit 
        //with expected width.
        thumbImage = Scalr.resize(image, Scalr.Mode.FIT_TO_WIDTH, maxWidth);
      } else if(maxWidth == 0) {
        //Just used in custom size case. When 0 as specified as width then it will be calculated automatically to fit 
        //with expected height.
        thumbImage = Scalr.resize(image, Scalr.Mode.FIT_TO_HEIGHT, maxHeight);
      } else {
        //BALANCED: Used to indicate that the scaling implementation should use a scaling operation balanced 
        //between SPEED and QUALITY
        thumbImage = Scalr.resize(image, Scalr.Method.BALANCED, maxWidth, maxHeight);      
      }
    }
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    ImageIO.write(thumbImage, "png", outStream);
    InputStream is = new ByteArrayInputStream(outStream.toByteArray());
    return is;
  }

}
