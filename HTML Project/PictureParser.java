/* Parser class used to find all images on a page and store the source tags in a list. 
 */
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class PictureParser extends HTMLEditorKit.ParserCallback {
	Vector<String> tagList;
	String domain;
	String attributeString;
	
	public PictureParser(Vector<String> tagList, String domain) {
		this.tagList = tagList;
		this.domain = domain;
	} // end constructor PictureParser().
//=========================================================================	
	// Overriden handleSimpleTag() method to parse image source tags from a URL.
	@Override
	public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attSet, int pos) {
		Object attribute;
		
		if (tag == HTML.Tag.IMG) {
			attribute = attSet.getAttribute(HTML.Attribute.SRC);
			if (attribute != null) {
				attributeString = attribute.toString();
				if (!(attributeString.startsWith("http") || attributeString.startsWith("file:///"))) {
					attributeString = domain + attributeString;
				}
				tagList.addElement(attributeString);
			}
		}		
	} // end method handleSimpleTag().
} // end class PictureParser.
