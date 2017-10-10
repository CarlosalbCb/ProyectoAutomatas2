package leng_auto2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener 
{
	private JTextArea codfte, consola;
	private JLabel etiq1, etiq2, etiq3;
	private JButton compilar;
	private Font f1, f2;
	
	public Panel()
	{
		 codfte   = new JTextArea();
		 consola  = new JTextArea();
		 etiq1    = new JLabel("Código Fuente");
	     etiq2    = new JLabel("Consola");
	     etiq3    = new JLabel("Jesús Gerardo Moreno Nieblas");
		 compilar = new JButton("Compilar");
		 
		 f1 = new Font("Arial", Font.PLAIN, 18);
		 f2 = new Font("Arial", Font.BOLD, 18);
		 
		    codfte.setFont(f1);
		   consola.setFont(f1);
		     etiq1.setFont(f2);
		     etiq2.setFont(f2);
		     etiq3.setFont(f1);
		  
		  compilar.setFont(f1);
		  compilar.setMnemonic('R');
		  compilar.addActionListener(this);
		  
		 agregarComponentes();
	}

	private void agregarComponentes() 
	{
		JPanel superior   = new JPanel();
		JPanel central    = new JPanel();
		JPanel inferior   = new JPanel();	
		JPanel auxiliar   = new JPanel();
		JPanel auxiliar2  = new JPanel();
		JPanel auxiliar3  = new JPanel();
		 
		setLayout(new BorderLayout());
	    
	    /*-------------------------------------------------*
		 *                PANEL SUPERIOR                   *
	     *-------------------------------------------------*/		
		add(superior, BorderLayout.NORTH);	 
	    superior.setPreferredSize(new Dimension(50, 50));
	    superior.setLayout(new GridLayout(1, 3)); 
	    superior.add(etiq1);
	    superior.add(new JPanel());
		superior.add(etiq2);
		etiq1.setHorizontalAlignment(JLabel.CENTER);
		etiq2.setHorizontalAlignment(JLabel.CENTER);
	    
		/*-------------------------------------------------*
		 *                  PANEL CENTRAL                  *
	     *-------------------------------------------------*/
	    add(central);
	    central.setLayout(new GridLayout(1, 3, 15, 15));
	    
	    central.add(auxiliar);
	    auxiliar.setLayout(new BorderLayout());
	    auxiliar.add(new JPanel(), BorderLayout.WEST);	    
	    auxiliar.add(new JScrollPane(codfte));
	   
	    central.add(auxiliar2);
	    auxiliar2.setLayout(new GridLayout(3, 1, 50, 50));
	    auxiliar2.add(new JPanel()); 
	    auxiliar2.add(compilar);
	    auxiliar2.add(new JPanel()); 
	   
	    central.add(auxiliar3);
	    auxiliar3.setLayout(new BorderLayout());
	    auxiliar3.add(new JPanel(), BorderLayout.EAST);
	    auxiliar3.add(new JScrollPane(consola));
	   
	    /*-------------------------------------------------*
		 *                  PANEL INFERIOR                 *
	     *-------------------------------------------------*/
	    inferior.add(etiq3);
	    add(inferior, BorderLayout.SOUTH);
	    inferior.setPreferredSize(new Dimension(50, 50));
	}

	public void actionPerformed(ActionEvent e) 
	{			
		if(e.getSource() == compilar)
		{			
			consola.setText("");
			
			if(!codfte.getText().trim().equals(""))
			{
				Scanner s = new Scanner(this);
				Parser p = new Parser(s, this);
				p.program();
			}
			else
				JOptionPane.showMessageDialog(null, "No hay código fuente.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public JTextArea getCodFteArea()
	{
		return codfte;
	}
	
	public String getCodFte()
	{
		return codfte.getText();
	}
	
	public void setCodFte(String p)
	{
		codfte.setText(p);
	}
	
	public void setConsola(String r)
	{
		consola.setText(r);
	}
	
	public int getLineasConsola()
	{
		return consola.getLineCount();
	}
	
	public void appendConsola(String r)
	{
		consola.append(r);
	}
}