/* David Poling
 * COMP 2230-Network Programming
 * Date: 02-04-2016
 * 
 * Small application that extracts images from a single web page and inserts the image SRC tag into a list.
 * Each image is displayed in the middle of the application. A small button is used to cycle through the images
 * in the list, and a URL (or domain name) can be inserted into a field in order to extract the images from the site.
 */

import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class PictureCycler {
	public static void main(String[] args) {
		new PictureFrame();
	} // end main.
} // end class PictureCycler.
