// Program to find all links on a webpage.

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class FindAllLinks {
	public static void main(String[] args) {
		URL url;
		URLConnection urlConnection;
		InputStreamReader isr;
		MyParser parser;
		String domain = "http://www.cnn.com";
		
		try {
			url = new URL(domain);
			urlConnection = url.openConnection();
			isr = new InputStreamReader(urlConnection.getInputStream());
			
			parser = new MyParser(domain);
			new ParserDelegator().parse(isr, parser, true);
		} catch (MalformedURLException mue) {
			System.out.println("Bad URL");
			mue.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IOException occured");
			ioe.printStackTrace();
		}
	}
}

class MyParser extends HTMLEditorKit.ParserCallback {
	String baseDomain;
	
	public MyParser(String baseDomain) {
		this.baseDomain = baseDomain;
	}
	
	public void handleStartTag(HTML.Tag tag, MutableAttributeSet attSet, int pos) {
		Object attribute;
		
		if (tag == HTML.Tag.A) {
			attribute = attSet.getAttribute(HTML.Attribute.HREF);
			if (attribute != null) {
				System.out.println(baseDomain + ":> " + attribute.toString());
			}
		}
	}
}
