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
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import dao.MiembroDAO;
import dao.InteresDAO;
import modelo.Interes;
import modelo.Miembro;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class PagoIntereses extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();
	private JTable tblInformacion;
	private JComboBox cbMiembros = new JComboBox();
	private JComboBox cbIntereses = new JComboBox();
	private JDateChooser fecha = new JDateChooser();
	private JTextField txtMonto;
	private Principal principal;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			PagoIntereses dialog = new PagoIntereses();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public PagoIntereses(Principal principal) {
		super(principal,"Pago de Intereses", true);
		this.principal = principal;
		setBounds(100, 100, 1000, 941);
		setResizable(false);

		getContentPane().setLayout(new BorderLayout());
		contenido.setBackground(new Color(255, 255, 255));
		contenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contenido, BorderLayout.CENTER);
		contenido.setLayout(null);
		
		JLabel lblInteresesGenerados = new JLabel("Intereses generados por prestamos");
		lblInteresesGenerados.setHorizontalAlignment(SwingConstants.CENTER);
		lblInteresesGenerados.setOpaque(true);
		lblInteresesGenerados.setFont(new Font("Arial", Font.PLAIN, 30));
		lblInteresesGenerados.setBackground(Color.LIGHT_GRAY);
		lblInteresesGenerados.setBounds(0, 352, 986, 41);
		contenido.add(lblInteresesGenerados);
		
		tblInformacion = new JTable();
		tblInformacion.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Nombre", "Fecha de pago", "Monto Pagado", "Estado"
			}
		));
		tblInformacion.setFont(new Font("Arial", Font.PLAIN, 15));
		tblInformacion.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tblInformacion.setBounds(10, 25, 915, 222);
		configurarTabla(tblInformacion, 127, 30);
		contenido.add(tblInformacion);
		
		JScrollPane scrollpane = new JScrollPane(tblInformacion);
		scrollpane.setBounds(235, 426, 525, 356);
		contenido.add(scrollpane);
		
		JLabel lblMiembro = new JLabel("Miembro:");
		lblMiembro.setFont(new Font("Arial", Font.PLAIN, 17));
		lblMiembro.setBounds(258, 51, 129, 25);
		contenido.add(lblMiembro);
		cbMiembros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Miembro seleccionado = (Miembro) cbMiembros.getSelectedItem();
				if(seleccionado != null && !seleccionado.toString().equals("Seleccione miembro")) {
					System.out.println("Miembro seleccionado: " + seleccionado.getNombre_completo());
					cargarInteresPorMiembro(seleccionado.getId());
				}else {
					cbIntereses.removeAllItems();
					cbIntereses.addItem(new Interes(0, "Seleccione intereses"));
				}
			}
		});
		
		cbMiembros.setModel(new DefaultComboBoxModel(new String[] {"Seleccionar Miembro"}));
		cbMiembros.setForeground(new Color(106, 106, 106));
		cbMiembros.setFont(new Font("Arial", Font.PLAIN, 12));
		cbMiembros.setBounds(423, 50, 252, 25);
		contenido.add(cbMiembros);
		
		JLabel lblFecha = new JLabel("Fecha de Ingreso:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 17));
		lblFecha.setBounds(258, 160, 155, 25);
		contenido.add(lblFecha);
		
		fecha.setToolTipText("");
		fecha.setBounds(423, 160, 143, 25);
		contenido.add(fecha);
		
		JButton btnRegistrar = new JButton("Registrar pago");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Miembro miembro = (Miembro) cbMiembros.getSelectedItem();
		        Interes interes = (Interes) cbIntereses.getSelectedItem();
		        java.util.Date fechaPago = fecha.getDate();

		        if (miembro == null || miembro.getId() == 0) {
		            JOptionPane.showMessageDialog(null, "Seleccione un miembro válido.");
		            return;
		        }

		        if (interes == null || interes.getIdIntereses() == 0) {
		            JOptionPane.showMessageDialog(null, "Seleccione un interés válido.");
		            return;
		        }

		        if (fechaPago == null) {
		            JOptionPane.showMessageDialog(null, "Seleccione la fecha de ingreso.");
		            return;
		        }

		        // 1. Actualizar tabla intereses (marcar como pagado)
		        boolean actualizado = InteresDAO.registrarPago(interes.getIdIntereses(), fechaPago);

		        // 2. Insertar en tabla pagoIntereses
		        boolean registrado = InteresDAO.registrarPagoInteres(
		            interes.getIdIntereses(),
		            interes.getMontoInteres(),
		            fechaPago
		        );

		        if (actualizado && registrado) {
		            JOptionPane.showMessageDialog(null, "Pago registrado correctamente.");
		            cargarInteresPorMiembro(miembro.getId());
		            cargarCargarDatosEnTabla();
		            txtMonto.setText("");
		            fecha.setDate(null);
		        } else {
		            JOptionPane.showMessageDialog(null, "Error al registrar el pago.");
		        }
		    }
		});
		btnRegistrar.setIcon(new ImageIcon(PagoIntereses.class.getResource("/img/guardar.png")));
		btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnRegistrar.setContentAreaFilled(false);
		btnRegistrar.setBounds(33, 293, 155, 39);
		botonTransparente(btnRegistrar);
		contenido.add(btnRegistrar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int filaSeleccionada = tblInformacion.getSelectedRow();

			    if (filaSeleccionada == -1) {
			        JOptionPane.showMessageDialog(null, "Seleccione un pago para eliminar.");
			        return;
			    }

			    int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este pago?", "Confirmación", JOptionPane.YES_NO_OPTION);
			    if (opcion != JOptionPane.YES_OPTION) return;

			    // Obtener el ID del interés desde la tabla
			    int idInteres = (int) tblInformacion.getValueAt(filaSeleccionada, 0); // Ajusta la columna si es necesario

			    boolean eliminado = InteresDAO.eliminarPagoInteres(idInteres);

			    if (eliminado) {
			        JOptionPane.showMessageDialog(null, "Pago eliminado correctamente.");
			        // Refrescar tabla
			        Miembro miembro = (Miembro) cbMiembros.getSelectedItem();
			        if (miembro != null && miembro.getId() != 0) {
			            cargarInteresPorMiembro(miembro.getId());
			        }
			    } else {
			        JOptionPane.showMessageDialog(null, "Error al eliminar el pago.");
			    }
			}
		});
		btnEliminar.setIcon(new ImageIcon(PagoIntereses.class.getResource("/img/eliminar_icono.png")));
		btnEliminar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnEliminar.setBounds(833, 293, 115, 39);
		botonTransparente(btnEliminar);
		contenido.add(btnEliminar);
		
		JLabel lblIntereses = new JLabel("Intereses:");
		lblIntereses.setFont(new Font("Arial", Font.PLAIN, 17));
		lblIntereses.setBounds(258, 107, 129, 25);
		contenido.add(lblIntereses);
		cbIntereses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Interes interesSeleccionado = (Interes) cbIntereses.getSelectedItem();
		        if (interesSeleccionado != null && interesSeleccionado.getIdIntereses() != 0) {
		            txtMonto.setText(interesSeleccionado.getMontoInteres().toString());
		        } else {
		            txtMonto.setText("");
		        }
			}
		});
		
		cbIntereses.setModel(new DefaultComboBoxModel(new String[] {"Seleccionar Intereses"}));
		cbIntereses.setForeground(new Color(106, 106, 106));
		cbIntereses.setFont(new Font("Arial", Font.PLAIN, 12));
		cbIntereses.setBounds(423, 106, 252, 25);
		contenido.add(cbIntereses);
		
		txtMonto = new JTextField();
		txtMonto.setColumns(10);
		txtMonto.setBounds(423, 213, 115, 24);
		txtMonto.setEditable(false);
		contenido.add(txtMonto);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setFont(new Font("Arial", Font.PLAIN, 17));
		lblMonto.setBounds(258, 213, 99, 24);
		contenido.add(lblMonto);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JLabel lblPagoIntereses = new JLabel("Pago de intereses");
		lblPagoIntereses.setIcon(new ImageIcon(PagoIntereses.class.getResource("/img/pago_intereses_icono.png")));
		lblPagoIntereses.setFont(new Font("Arial", Font.PLAIN, 15));
		menuBar.add(lblPagoIntereses);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		JButton btnVolverMenu = new JButton("Volver a menu");
		btnVolverMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				principal.setVisible(true);
			}
		});
		btnVolverMenu.setIcon(new ImageIcon(PagoIntereses.class.getResource("/img/volver_icono2.png")));
		btnVolverMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		botonTransparente(btnVolverMenu);
		menuBar.add(btnVolverMenu);
		
		setLocationRelativeTo(null);
		cargarMiembros();
		cargarCargarDatosEnTabla();
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
	
	public void cargarCargarDatosEnTabla() {
	    List<Interes> lista = new InteresDAO().listarIntereses(); // usa tu método ya existente

	    DefaultTableModel model = (DefaultTableModel) tblInformacion.getModel();
	    model.setRowCount(0); // limpiar tabla

	    for (Interes interes : lista) {
	        String nombre = interes.getPrestamo().getNombreMiembro();
	        String fecha = interes.getFechaGeneracion().toString();
	        String monto = interes.getMontoInteres().toString();
	        String estado = interes.isPagado() ? "Pagado" : "Pendiente";

	        model.addRow(new Object[] { nombre, fecha, monto, estado });
	    }
	}
	
	public void cargarMiembros() {
		cbMiembros.removeAllItems(); //limpia todo
		cbMiembros.addItem(new Miembro(0, "Seleccione miembro"));
		
		List<Miembro> lista = MiembroDAO.listarPorNombre();
		System.out.println("Miembros cargados: " + lista.size()); // 👈 Aquí
		
		for(Miembro m : lista) {
			System.out.println("→ " + m.getNombre_completo()); // 👈 Aquí
			cbMiembros.addItem(m);
		}
	}
	
	private void cargarInteresPorMiembro(int idMiembro) {
		cbIntereses.removeAllItems();
		cbIntereses.addItem(new Interes(0, "Seleccione interes"));
		
		List<Interes> lista = InteresDAO.listarPorIdMiembro(idMiembro);
		for(Interes i : lista) {
			cbIntereses.addItem(i);
		}
	}
	
	public void limpiar() {
		cbMiembros.setSelectedIndex(0);
		cbIntereses.setSelectedIndex(0);
		fecha.setDate(null);
		txtMonto.setText("");
		//txtSaldoActual.setText("");
		//modoEdicion = false;
	}
}
