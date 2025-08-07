package ventana;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;


import dao.InteresDAO;
import dao.PrestamoDAO;
import modelo.Interes;

import modelo.Prestamo;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

public class RegistroIntereses extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();
	private JTable tblInformacion;
	private Principal principal;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			RegistroIntereses dialog = new RegistroIntereses();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public RegistroIntereses(Principal principal) {
		super(principal,"Registro de Intereses", true);
		this.principal = principal;
		setBounds(100, 100, 964, 730);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contenido.setBackground(new Color(255, 255, 255));
		contenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contenido, BorderLayout.CENTER);
		contenido.setLayout(null);
		{
			JLabel lblInteresesGenerados = new JLabel("Intereses generados por prestamos");
			lblInteresesGenerados.setOpaque(true);
			lblInteresesGenerados.setHorizontalAlignment(SwingConstants.CENTER);
			lblInteresesGenerados.setFont(new Font("Arial", Font.PLAIN, 30));
			lblInteresesGenerados.setBackground(Color.LIGHT_GRAY);
			lblInteresesGenerados.setBounds(0, 191, 950, 41);
			contenido.add(lblInteresesGenerados);
		}
		{
			tblInformacion = new JTable();
			tblInformacion.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
				},
				new String[] {
					"Miembro", "Prestamo ID", "Intereses", "Fecha Generacion", "Pagado"
				}
			));
			tblInformacion.getColumnModel().getColumn(3).setPreferredWidth(95);
			tblInformacion.setFont(new Font("Arial", Font.PLAIN, 15));
			tblInformacion.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			tblInformacion.setBounds(89, 10, 915, 222);
			configurarTabla(tblInformacion, 127, 30);
			contenido.add(tblInformacion);
			
			JScrollPane scrollpane = new JScrollPane(tblInformacion);
			scrollpane.setBounds(170, 262, 633, 356);
			contenido.add(scrollpane);
		}
		{
			JButton btnRegistrar = new JButton("Registrar todos los intereses");
			btnRegistrar.setIcon(new ImageIcon(RegistroIntereses.class.getResource("/img/guardar.png")));
			btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 15));
			btnRegistrar.setContentAreaFilled(false);
			btnRegistrar.setBounds(360, 548, 255, 33);
			botonTransparente(btnRegistrar);
			contenido.add(btnRegistrar);
		}
		
		JButton btnCalcularIntereses = new JButton("Calcular intereses del mes");
		btnCalcularIntereses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Prestamo> prestamosActivos = PrestamoDAO.listarPrestamosActivos();

				if (prestamosActivos.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No hay préstamos activos.");
					return;
				}

				int generados = 0;

				for (Prestamo prestamo : prestamosActivos) {
					boolean generado = InteresDAO.generarInteresMensual(prestamo);
					if (generado) generados++;
				}

				JOptionPane.showMessageDialog(null, generados + " interés(es) generados correctamente.");
			
				//Cargar los intereses actualizados en la tabla
				cargarDatosEnTabla(new InteresDAO().listarIntereses());
			}
		});
		btnCalcularIntereses.setIcon(new ImageIcon(RegistroIntereses.class.getResource("/img/calcular.png")));
		btnCalcularIntereses.setFont(new Font("Arial", Font.PLAIN, 20));
		btnCalcularIntereses.setContentAreaFilled(false);
		btnCalcularIntereses.setBounds(315, 67, 344, 50);
		botonTransparente(btnCalcularIntereses);
		contenido.add(btnCalcularIntereses);
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JLabel lblRegistroIntereses = new JLabel("Registro de intereses");
				lblRegistroIntereses.setIcon(new ImageIcon(RegistroIntereses.class.getResource("/img/registro_de_intereses_icono.png")));
				lblRegistroIntereses.setFont(new Font("Arial", Font.PLAIN, 15));
				menuBar.add(lblRegistroIntereses);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				menuBar.add(horizontalGlue);
			}
			{
				JButton btnVolverMenu = new JButton("Volver a menu");
				btnVolverMenu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						principal.setVisible(true);
					}
				});
				btnVolverMenu.setIcon(new ImageIcon(RegistroIntereses.class.getResource("/img/volver_icono2.png")));
				btnVolverMenu.setFont(new Font("Arial", Font.PLAIN, 18));
				botonTransparente(btnVolverMenu);
				menuBar.add(btnVolverMenu);
			}
		}
		setLocationRelativeTo(null);
		cargarDatosEnTabla(new InteresDAO().listarIntereses());
	}

	public static void botonTransparente(JButton boton) {
		//boton.setBorderPainted(false); //no pinta el bonde
		boton.setContentAreaFilled(false);
		//boton.setFocusPainted(false); //no pinta el borde cuando enfoca
	}
	
	public void configurarTabla(JTable tabla, int ancho, int alto) {
		tabla.setRowHeight(alto);
		tabla.setFont(new Font("Arial", Font.PLAIN, 15));
		
		int columnas = tabla.getColumnCount();
		
		for (int i = 0; i < columnas; i++) {
			tabla.getColumnModel().getColumn(i).setPreferredWidth(ancho);
			tabla.getColumnModel().getColumn(i).setMinWidth(ancho);
			tabla.getColumnModel().getColumn(i).setMaxWidth(ancho);
		}
	}
	
	public void cargarDatosEnTabla(List<Interes> intereses) {
		DefaultTableModel model = (DefaultTableModel) tblInformacion.getModel();
		model.setRowCount(0); //Limpiar tabla
		
		for(Interes interes : intereses) {
			
			Prestamo prestamo = interes.getPrestamo();
			
			model.addRow(new Object[] {
					prestamo != null ? prestamo.getNombreMiembro() : "N/A",
				    prestamo != null ? prestamo.getId() : "N/A",
				    interes.getMontoInteres(),
				    interes.getFechaGeneracion(),
				    interes.isPagado() ? "Sí" : "No"
			});
		}
	}
}
