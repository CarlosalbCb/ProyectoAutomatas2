package leng_auto2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class Ventana extends JFrame implements ActionListener
{
	private File      file;
	private Panel     panel;
	private JMenuBar  barrademenu;
	private JMenu     archivo, edicion;
	private JMenuItem nuevo, abrir, guardar, guardarcomo, salir;
	private JMenuItem buscar;
	
	public Ventana()
	{
		super("Compilador: <sin titulo>");
		
		panel       = new Panel();
		barrademenu = new JMenuBar();
		archivo     = new JMenu("Archivo");
		nuevo       = new JMenuItem("Nuevo", new ImageIcon(this.getClass().getResource("/leng_auto2/Nuevo.png")));
		abrir       = new JMenuItem("Abrir", new ImageIcon(this.getClass().getResource("/leng_auto2/Abrir.png")));
		guardar     = new JMenuItem("Guardar", new ImageIcon(this.getClass().getResource("/leng_auto2/Guardar.png")));
		guardarcomo = new JMenuItem("Guardar como...", new ImageIcon(this.getClass().getResource("/leng_auto2/Guardarcomo.png")));
		salir       = new JMenuItem("Salir", new ImageIcon(this.getClass().getResource("/leng_auto2/Salir.png")));
		edicion     = new JMenu("Edición");
		buscar      = new JMenuItem("Buscar", new ImageIcon(this.getClass().getResource("/leng_auto2/Buscar.png")));		
		
		asignarOyentes();
		agregarComponentes();
	}
	
	private void agregarComponentes() 
	{
		barrademenu.add(archivo);
		    archivo.add(nuevo);
		    archivo.add(abrir);
		    archivo.addSeparator();
		    archivo.add(guardar);
		    archivo.add(guardarcomo);
		    archivo.addSeparator();
		    archivo.add(salir);
		   
		    archivo.setMnemonic('A');
		      nuevo.setMnemonic('N');
		      abrir.setMnemonic('A');
		    guardar.setMnemonic('G');
		guardarcomo.setMnemonic('C');
		      salir.setMnemonic('S');
		
		      //Asigna teclas de acceso rápido para el menú Archivo.
		      nuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	          abrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK  + ActionEvent.ALT_MASK));
		    guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		guardarcomo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		      salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		      
		barrademenu.add(edicion);
		    edicion.add(buscar);
			
			edicion.setMnemonic('E');
		     buscar.setMnemonic('B');
		     
		     //Asigna teclas de acceso rápido para el menú Edición.
		     buscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		  
		add(barrademenu, BorderLayout.NORTH);
		add(panel);
	}
	
	private void asignarOyentes() 
	{		
		      nuevo.addActionListener(this);
		      abrir.addActionListener(this);
	        guardar.addActionListener(this);
	    guardarcomo.addActionListener(this);
		      salir.addActionListener(this); 
			 buscar.addActionListener(this); 
		      
		Oyente oyente = new Oyente();
		addWindowListener(oyente);	    
	}

	public void setVisible()
	{
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		setLocationRelativeTo(null);		
	}
	
	public void actionPerformed(ActionEvent e) 
	{		
		if(e.getSource() == nuevo)
		{
			Ventana ventana = new Ventana();
			ventana.setVisible();
		}
		else if (e.getSource() == abrir)
		{
			JFileChooser abrirArchivo =  new JFileChooser();
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Documentos de texto (*.txt)","txt");
			abrirArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
			abrirArchivo.setFileFilter(filtro);
			abrirArchivo.setAcceptAllFileFilterUsed(false);
			int opcion = abrirArchivo.showOpenDialog(this);
			
			if (opcion == JFileChooser.APPROVE_OPTION)
			{
				File fcopia = file;
				file = abrirArchivo.getSelectedFile();
				FileReader fr = null;
		        BufferedReader br = null;
		        String texto = "";
		       
		    	if(!file.getAbsolutePath().endsWith(".txt"))
				{
					File tmp = new File (file.getAbsolutePath()+".txt");
					file = tmp;
				}
				
		        if(file.exists())
		        {
		        	try
		        	{
		        		fr = new FileReader(file);
				        br = new BufferedReader(fr);
				 
				        String linea;
			         
    		            while((linea = br.readLine()) != null)
    		            	texto += linea + "\n";
    		            
    		            br.close();
				        setTitle("Compilador: <"+file.getName()+">");
				        panel.setCodFte(texto);
				        panel.setConsola("");
				    } 
				    catch(Exception ex)
				    {  	
				    	JOptionPane.showMessageDialog(this, "No se pudo leer el archivo. Inténtelo de nuevo.", "Mensaje", JOptionPane.ERROR_MESSAGE);
				    }
		        }
		        else
		        {
		        	JOptionPane.showMessageDialog(this, "Archivo no encontrado. Inténtelo de nuevo.", "Mensaje", JOptionPane.ERROR_MESSAGE);
		        	file = fcopia;
		        }	
			}		
		}
		else if (e.getSource() == guardar)
		{					
			if(file == null)
				guardarcomo();
			else
			{
				try
				{
					FileWriter fw = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw); 
						
					pw.write(panel.getCodFte());
						
					pw.close();
					bw.close();
					
					JOptionPane.showMessageDialog(this, "Archivo guardado.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (Exception ex) 
				{
					JOptionPane.showMessageDialog(this, "Error al guardar el archivo. Inténtelo de nuevo.", "Mensaje", JOptionPane.ERROR_MESSAGE);
				}
			}
		}			
		else if (e.getSource() == salir) 
		{
			int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas salir?", "Confirmar", JOptionPane.YES_NO_OPTION);

			if (respuesta == JOptionPane.YES_OPTION)
				dispose();
		}
		else if (e.getSource() == guardarcomo)
		{
			guardarcomo();
		}
		else //Buscar.
		{
			String textoABuscar = JOptionPane.showInputDialog(null, "Buscar", "Escribe el texto a buscar");
				
			if(textoABuscar != null)
			{
				try 
				{
					int pos = panel.getCodFte().indexOf(textoABuscar);
					panel.getCodFteArea().setCaretPosition(pos);
					panel.getCodFteArea().moveCaretPosition(pos + textoABuscar.length());
				} 
				catch (Exception ex) 
				{
					JOptionPane.showMessageDialog(null, "Texto no encontrado. Inténtelo de nuevo.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private void guardarcomo() 
	{
		try
		{
			File fcopia = file;
			JFileChooser guardarArchivo = new JFileChooser();
			int opcion = guardarArchivo.showSaveDialog(this);
			file = guardarArchivo.getSelectedFile();
			FileWriter fw = null;
			
			if(opcion == JFileChooser.APPROVE_OPTION)
			{
				fw = new FileWriter(file); 
				fw.write(panel.getCodFte());
		        fw.close();
		        
		        if(!file.getAbsolutePath().endsWith(".txt"))
				{
					File tmp = new File (file.getAbsolutePath()+".txt");
					file.renameTo(tmp);
					file = tmp;
				}
					
		        JOptionPane.showMessageDialog(this, "Archivo guardado.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		        setTitle("Compilador: <"+file.getName()+">");
		   }
		   else
			   file = fcopia;
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Error al guardar el archivo. Inténtelo de nuevo.", "Mensaje", JOptionPane.ERROR_MESSAGE);
		} 
	}
}

class Oyente extends WindowAdapter
{
	public void windowClosing (WindowEvent e)
	{
		int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas salir?", "Confirmar", JOptionPane.YES_NO_OPTION);

		if (respuesta == JOptionPane.YES_OPTION)
		{
			JFrame ventana = (JFrame) e.getSource();
			ventana.dispose();
		}	
	}
}
