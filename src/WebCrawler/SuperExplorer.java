package WebCrawler;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import org.apache.commons.io.FileUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;


/**
 *
 * @author rajib_000
 */
public class SuperExplorer 
{

    JTree fileTree;
    private FileSystemModel fileSystemModel;
    JFrame frame;
    JPanel panel, hPanel, cPanel;
    DefaultListModel listModel;
    JList hList;
    /**
     *
     */
    protected JPopupMenu m_popup;
    File srcFile;
    
    /**
     *
     */
    protected JPopupMenu m_popup2;
    
    private void addRoots() 
    {
        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++) 
        {
            listModel.addElement(roots[i]);
        }
        
    }
    
    
    /**
     *
     * @param dir
     */
    public static void listFolders(String dir)
    {
        
        File d = new File(dir);
        if(d.exists()==false)
            return;
        if(d.isFile()==true)
        {
            new File(dir).delete();
            return;
        }
        
        File[] fList = d.listFiles();
        for(File file:fList)
        {
            if(file.isDirectory())
            {
                listFolders(dir+"/"+file.getName());
                new File(dir+"/"+file.getName()).delete();
            }
            else if(file.isFile())
            {
                new File(dir+"/"+file.getName()).delete();
            }
        }
        return;
    }

    /**
     *
     */
    public SuperExplorer() 
    {
        frame = new JFrame("Power Explorer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(200, 20);
        frame.setSize(500, 400);
        frame.setVisible(true);

        panel = new JPanel();
        frame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        hPanel = new JPanel();
        hPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        panel.add(hPanel);

        listModel = new DefaultListModel();
        hList = new JList(listModel);
        hList.setPreferredSize(new Dimension(180,frame.getHeight()));
        hList.setBackground(Color.white);
        hList.setFont(new Font(null, Font.PLAIN, 17));
        hPanel.add(hList);
        hList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                File file = (File) hList.getSelectedValue();
                indexDisk(file.toString());
//                System.out.println("ev=" + file.toString());

            }
        });

        cPanel = new JPanel();
//        cPanel.setPreferredSize(new Dimension(600,frame.getHeight()));
        cPanel.setLayout(new BorderLayout());
        panel.add(cPanel);

        m_popup = new JPopupMenu();
        m_popup.addSeparator(); 
        m_popup.setPopupSize(100, 100);
        
        Action a1 = new AbstractAction("Delete") {

            public void actionPerformed(ActionEvent e) {

                String fp = decodeFilePath(fileTree.getSelectionPath().toString());

                TreePath pPath = fileTree.getSelectionPath().getParentPath();

                int cn = JOptionPane.showConfirmDialog(frame, "delete " + fp + " ?", "Warning", JOptionPane.INFORMATION_MESSAGE);
                if (cn == JOptionPane.YES_OPTION) {

                    listFolders(fp);
//                    new File(fp).delete();
                    
                    
                    
                    System.out.println("fp=" + fp);
                    File ff = new File(fp);
                    System.out.println("exist=" + ff.exists());
                    System.out.println("delete=" + ff.delete());
                    
                    File file = (File) hList.getSelectedValue();
                    indexDisk(file.toString());
                    System.out.println("path="+pPath.toString());
                    fileTree.scrollPathToVisible(pPath);
                    fileTree.setSelectionPath(pPath);
                    fileTree.expandPath(pPath);

                    
                      
                }
            }
        };
        m_popup.add(a1);

        Action a2 = new AbstractAction("Rename") {

            public void actionPerformed(ActionEvent e) {
                String fp = decodeFilePath(fileTree.getSelectionPath().toString());
                File file = new File(fp);
                System.out.println("oldFile=" + file.getAbsolutePath());

                String pth = fp.substring(0, fp.lastIndexOf('\\'));
                System.out.println("pth=" + pth);

                fileTree.repaint();
                String nName = JOptionPane.showInputDialog(frame, "Enter Name");
                System.out.println("new name=" + nName);
                if (nName != null) {
                    File rFile = new File(pth + "\\" + nName);
                    System.out.println("newFile=" + rFile);
                    System.out.println("oldFileExist=" + file.exists());
                    System.out.println("rename=" + file.renameTo(rFile));

                    TreePath pPath = fileTree.getSelectionPath().getParentPath();
                    File file2 = (File) hList.getSelectedValue();
                    indexDisk(file2.toString());
                    
                    
                    
                    System.out.println("path="+pPath.toString());
                    
                    fileTree.setSelectionPath(pPath);
                    fileTree.expandPath(pPath);

                    fileTree.scrollPathToVisible(pPath);
                }
            }
        };
        m_popup.add(a2);
        Action a4 = new AbstractAction("Select") 
        {
            public void actionPerformed(ActionEvent e) {
                String str = decodeFilePath(fileTree.getSelectionPath().toString());
                System.out.println(str);
                String fileName = "";
                String ss = "";
                for (int j = str.length() - 1; j >= 0; j--) {
                    if (str.charAt(j) == '\\') {
                        break;
                    }
                    //System.out.print(str.charAt(j));
                    ss += String.valueOf(str.charAt(j));

                }

                for (int j = ss.length() - 1; j >= 0; j--) {
                    fileName += String.valueOf(ss.charAt(j));
                }
                for (int i = 0; i < Main.downloaded_files.size(); i++) 
                {
                    String a = Main.downloaded_files.elementAt(i);
                    new DeleteFile("downloads/" + a);

                }
                
                String student="";
                student = ListFilesUtil.get_user_file("userinput/");
                new DeleteFile(student);
                
                
                PDFTextParser pdfTextParserObj = new PDFTextParser();
                String pdfToText = "";
                try {
                    pdfToText = pdfTextParserObj.pdftoText(str);
                    if (pdfToText == null) {
                        System.out.println("PDF to Text Conversion failed.");
                    } else {
                        //System.out.println("\nThe text parsed from the PDF Document....\n" + pdfToText);
                        String s = "userinput/" + fileName;
                        //s += String.valueOf(++Main.counter);
                        s += ".txt";
                        System.out.println("priontinsafasfd :" + s);
                        pdfTextParserObj.writeTexttoFile(pdfToText, s);
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ListLinks.class.getName()).log(Level.SEVERE, null, ex);
                }

                frame.dispose();
                JOptionPane.showMessageDialog(null, "COMPLETED USER INPUT");
            }
        };
       
        m_popup.add(a4);
        

        addRoots();
        indexDisk(listModel.elementAt(0).toString());
        hPanel.validate();
        hPanel.repaint(50L);
        frame.validate();
    }
    
    private String decodeFilePath(String sp) {
        sp = sp.substring(sp.indexOf('[') + 1, sp.length());

        int lst = sp.lastIndexOf(']');
        if (lst > 0 && sp.charAt(lst) == ']') {
            sp = sp.substring(0, lst);
        }
        String fp = "";
        String[] inf = sp.split(", ");
        for (int i = 0; i < inf.length - 1; i++) {
            fp += inf[i] + '\\';
        }
        fp += inf[inf.length - 1];
        return fp;
    }

    
    
    
    private void indexDisk(String dir) {
        cPanel.removeAll();
        cPanel.repaint(50L);
        
        
        fileSystemModel = new FileSystemModel(new File(dir));
        fileTree = new JTree(fileSystemModel);
        fileTree.setEditable(true);
        fileTree.setFont(new Font(null,Font.PLAIN,16));
        
        fileTree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent event) {
                File file = (File) fileTree.getLastSelectedPathComponent();
                
            }
        });


        fileTree.addMouseListener(new PopupTrigger());



        JScrollPane lPane = new JScrollPane(fileTree);
//        lPane.setPreferredSize(new Dimension(400, rPane.getHeight()));

    
        
        cPanel.add(lPane);
        frame.validate();
    }

    class PopupTrigger extends MouseAdapter {

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                Point p = e.getPoint();
                fileTree.getSelectionModel().setSelectionPath(fileTree.getPathForLocation(p.x, p.y));

                int x = e.getX();
                int y = e.getY();
                TreePath path = fileTree.getPathForLocation(x, y);
                if (path != null) {
                    m_popup.show(fileTree, x, y);
                }
            }
        }
    }
  
    

    private String getFileDetails(File file) 
    {
        if (file == null) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name: " + file.getName() + "\n");
        buffer.append("Path: " + file.getPath() + "\n");

        long length = file.length();
        float lkb = 0, lmb = 0, lgb = 0;
        String len = length + "B";
        if (length > 1024) {
            lkb = (length / 1024);
            len = lkb + "";
            len = len.substring(0, len.indexOf('.') + 2);
            len += "KB";
        }
        if (lkb > 1024) {
            lmb = lkb / 1024;
            len = lmb + "";
            len = len.substring(0, len.indexOf('.') + 2);
            len += "MB";
        }
        if (lmb > 1024) {
            lgb = lmb / 1024;
            len = lgb + "";
            len = len.substring(0, len.indexOf('.') + 2);
            len += "GB";
        }


        buffer.append("Size: " + len + "\n");
        return buffer.toString();
    }

    
}
class FileSystemModel implements TreeModel {

    private File root;
    private Vector listeners = new Vector();

    public FileSystemModel(File rootDirectory) {
        root = rootDirectory;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        String[] children = directory.list();
        return new TreeFile(directory, children[index]);
    }

    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            String[] fileList = file.list();
            if (fileList != null) {
                return file.list().length;
            }
        }
        return 0;
    }

    public boolean isLeaf(Object node) {
        File file = (File) node;
        return file.isFile();
    }

    public int getIndexOfChild(Object parent, Object child) {
        File directory = (File) parent;
        File file = (File) child;
        String[] children = directory.list();
        for (int i = 0; i < children.length; i++) {
            if (file.getName().equals(children[i])) {
                return i;
            }
        }
        return -1;

    }

    public void valueForPathChanged(TreePath path, Object value) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) value;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
        Object[] changedChildren = {targetFile};
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);

    }

    public void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        Iterator iterator = listeners.iterator();
        TreeModelListener listener = null;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }

    public void addTreeModelListener(TreeModelListener listener) {
        listeners.add(listener);
    }

    public void removeTreeModelListener(TreeModelListener listener) {
        listeners.remove(listener);
    }

    private class TreeFile extends File {

        public TreeFile(File parent, String child) {
            super(parent, child);
        }

        public String toString() {
            return getName();
        }
    }
}



