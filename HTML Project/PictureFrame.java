/* Subclass of JFrame used to construct a GUI for the images.
 * The images are cycled in the center of the GUI, and a JTextField and JButton are constructed at the bottom.
 * Uses a custom JPanel for the images.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.text.html.parser.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.imageio.ImageIO;

public class PictureFrame extends JFrame implements ActionListener, DocumentListener, DropTargetListener {
    JButton cycleButton;
    JTextField urlField;
    String domain;
    PicturePanel imagePanel;
    Vector<String> imageList;
    int index;

    // Default constructor.
    public PictureFrame() {
        Container contentPane;
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        imagePanel = new PicturePanel();
        imageList = new Vector<String>();
        index = 0;

        cycleButton = new JButton("Go");
        cycleButton.addActionListener(this);
        getRootPane().setDefaultButton(cycleButton);
        cycleButton.setEnabled(false);

        urlField = new JTextField(20);
        urlField.getDocument().addDocumentListener(this);

        bottomPanel.add(urlField);
        bottomPanel.add(cycleButton);
        mainPanel.add(imagePanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPane = getContentPane();
        contentPane.add(mainPanel);

        setupMainFrame();

    } // end default constructor PictureFrame().
//=========================================================================
    // Overriden actionPerformed() method that allows image cycling when the button is clicked.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cycleButton && cycleButton.getText().equals("Go")) {
			imageList.removeAllElements();
            extractImages();
            displayImage(index);
            cycleButton.setText("Next");
            index++;
        } 
        else if (e.getSource() == cycleButton && cycleButton.getText().equals("Next")) {
			 if (index < imageList.size()) {
				 displayImage(index);
				 index++;
			 } 
			 else {
				 cycleButton.setText("Go");
				 imageList.removeAllElements();
				 index = 0;
			 }
		 }
    } // end actionPerformed().
//=========================================================================
    /* Method to extract all images from the URL provided in the text field and store them in a list.
     * Uses ParserDelegator().parse() to parse through the images obtained through the ParserCallback class.
     * (As well as the InputStream)
     */
    public void extractImages() {
        URL url;
        URLConnection urlConnection;
        InputStreamReader isr;
        PictureParser pictureParser;
        domain = urlField.getText().trim();

        try {
            url = new URL(domain);
            urlConnection = url.openConnection();
            isr = new InputStreamReader(urlConnection.getInputStream());
            pictureParser = new PictureParser(imageList, domain);

            new ParserDelegator().parse(isr, pictureParser, true);
        } 
        catch (MalformedURLException mue) {
            System.out.println("Bad URL");
            mue.printStackTrace();
        } 
        catch (IOException ioe) {
            System.out.println("IO Exception found");
            ioe.printStackTrace();
        }
    } // end method extractImages().
//=========================================================================
	/* Method that reads a string from the list as a URL and converts it to an image (BufferedImage).
	 * setImage(im) then sends the image to the JPanel for drawing.
	 */
	public void displayImage(int index) {
		Image im;
		MediaTracker mt;
		
		try {
			if (imageList.isEmpty()) {
				System.out.println("Empty list!");
				return;
			}
			im = ImageIO.read(new URL(imageList.elementAt(index)));
			mt = new MediaTracker(this);
			mt.addImage(im, 123);
			mt.waitForAll();

			imagePanel.setImage(im);
		} 
		catch (MalformedURLException mue) {
            System.out.println("Bad URL");
            mue.printStackTrace();
        } 
        catch (IOException ioe) {
            System.out.println("IO Exception found");
            ioe.printStackTrace();
        } 
        catch (InterruptedException ie) {
			System.out.println("Image loading was interrupted.");
            ie.printStackTrace();
        }
	} // end method displayImage().
//=========================================================================
    public void changedUpdate(DocumentEvent de) {} // end method changedUpdate().
//=========================================================================
	/* Document listener methods insertUpdate and removeUpdate ensure the URL in the text field
	 * is valid and changes the text of the button if any changes in the field are made.
	 */
    public void insertUpdate(DocumentEvent de) {
		URL url;
		
		try {
			if (!(urlField.getText().equals(domain))) {
				cycleButton.setText("Go");
			}
			url = new URL(urlField.getText());
			cycleButton.setEnabled(true);
		} 
		catch (MalformedURLException mue) {
			cycleButton.setText("Go");
			cycleButton.setEnabled(false);
		}
    } // end method insertUpdate().
//=========================================================================
    public void removeUpdate(DocumentEvent de) {
		URL url;
		
		try {
			if (!(urlField.getText().equals(domain))) {
				cycleButton.setText("Go");
			}
			url = new URL(urlField.getText());
			cycleButton.setEnabled(true);
		} 
		catch (MalformedURLException mue) {
			cycleButton.setText("Go");
			cycleButton.setEnabled(false);
		}
    } // end method removeUpdate().
//=========================================================================
    @SuppressWarnings("unchecked")
    public void drop(DropTargetDropEvent dtde) {
        String url;
        Transferable transferableData;

        transferableData = dtde.getTransferable();

        try {
            if(transferableData.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                url = (String)transferableData.getTransferData(DataFlavor.stringFlavor);
                urlField.setText(url);
            } 
            else
                System.out.println("File list flavor not supported.");
        } 
        catch(UnsupportedFlavorException ufe) {
            System.out.println("Unsupported flavor found.");
            ufe.printStackTrace();
        } 
        catch(IOException ioe) {
            System.out.println("IOException found getting transferable data!");
            ioe.printStackTrace();
        }
    } // end method drop().
//=========================================================================
    public void dragEnter(DropTargetDragEvent dtde){}
//=========================================================================
    public void dragExit(DropTargetEvent dtde){}
//=========================================================================
    public void dropActionChanged(DropTargetDragEvent dtde){}
//=========================================================================
    public void dragOver(DropTargetDragEvent dtde){}
//=========================================================================
    public void setupMainFrame() {   // Function to set up the mainframe.
        Toolkit tk;     // A pointer to class Toolkit created.
        Dimension d;    // A pointer to class Dimension created.

        tk = Toolkit.getDefaultToolkit();   // Toolkit object instantiated.
        d = tk.getScreenSize();     // Dimension object instantiated. Assigned dimensions of what the Toolkit object's method getScreenSize() returns.
        setSize(5*(d.width) / 10, 5*(d.height) / 10);
        setLocation(d.width / 4, d.height / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // Enabled the exit button.
        setTitle("Picture Frame");        // Title.
        setVisible(true);   // Allow the mainframe to be visible.
    }
} // end class PictureFrame.
