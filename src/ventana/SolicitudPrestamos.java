package ventana;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;

import dao.MiembroDAO;
import dao.PrestamoDAO;
import modelo.Miembro;
import modelo.Prestamo;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.awt.event.ActionEvent;

public class SolicitudPrestamos extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();
	private JTable tblInformacion;
	private JTextField txtMonto;
	private JComboBox cbMiembros;
	private JComboBox cbEstado;
	private JDateChooser fecha;
	
	//vareables auxiliares
		private boolean modoEdicion = false;
		private int idPrestamoEditar = -1;
		private Principal principal;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			SolicitudPrestamos dialog = new SolicitudPrestamos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public SolicitudPrestamos(Principal principal) {
		super(principal,"Solicitud de Prestamos", true);
		setTitle("Solicitud de Prestamos");
		this.principal = principal;
		setBounds(100, 100, 1080, 950);
		getContentPane().setLayout(new BorderLayout());
		contenido.setBackground(new Color(255, 255, 255));
		contenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contenido, BorderLayout.CENTER);
		contenido.setLayout(null);
		
		tblInformacion = new JTable();
		tblInformacion.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Nombre", "Fecha Inicio", "Fecha Finalicacion", "Monto", "Saldo", "Estado"
			}
		));
		tblInformacion.getColumnModel().getColumn(0).setPreferredWidth(39);
		tblInformacion.getColumnModel().getColumn(3).setPreferredWidth(98);
		tblInformacion.getColumnModel().getColumn(3).setMinWidth(18);
		tblInformacion.setFont(new Font("Arial", Font.PLAIN, 15));
		tblInformacion.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tblInformacion.setBounds(133, 454, 754, 222);
		configurarTabla(tblInformacion, 130, 30);
		contenido.add(tblInformacion);
		
		JScrollPane scrollpane = new JScrollPane(tblInformacion);
		scrollpane.setBounds(89, 438, 890, 356);
		contenido.add(scrollpane);
		
		JLabel lblHistorialPrestamos = new JLabel("Historial de Prestamos");
		lblHistorialPrestamos.setOpaque(true);
		lblHistorialPrestamos.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistorialPrestamos.setFont(new Font("Arial", Font.PLAIN, 30));
		lblHistorialPrestamos.setBackground(Color.LIGHT_GRAY);
		lblHistorialPrestamos.setBounds(0, 366, 1064, 41);
		contenido.add(lblHistorialPrestamos);
		
		JLabel lblMiembro = new JLabel("Miembro:");
		lblMiembro.setFont(new Font("Arial", Font.PLAIN, 17));
		lblMiembro.setBounds(271, 57, 129, 24);
		contenido.add(lblMiembro);
		
		JLabel lblFecha = new JLabel("Fecha de Ingreso:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 17));
		lblFecha.setBounds(271, 115, 155, 25);
		contenido.add(lblFecha);
		
		fecha = new JDateChooser();
		fecha.setToolTipText("");
		fecha.setBounds(436, 115, 143, 25);
		contenido.add(fecha);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setFont(new Font("Arial", Font.PLAIN, 17));
		lblMonto.setBounds(271, 172, 99, 24);
		contenido.add(lblMonto);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int indexMiembro = cbMiembros.getSelectedIndex();
					if(indexMiembro <= 0) {
						JOptionPane.showMessageDialog(null, "Seleccionar miembro");
						return;
					}
					
					// Extraer ID del miembro (suponiendo que cbMiembros contiene objetos Miembro)
					Miembro miembroSeleccionado = (Miembro) cbMiembros.getSelectedItem();
					int idMiembro = miembroSeleccionado.getId();
					
					//obtener fecha
					java.util.Date fechaUtil = fecha.getDate();
					if(fechaUtil == null) {
						JOptionPane.showMessageDialog(null, "Seleccionar fecha");
						return;
					}
					java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());
					
					// Obtener estado
					String estadoSeleccionado = cbEstado.getSelectedItem().toString();
					boolean estado = estadoSeleccionado.equalsIgnoreCase("Activo");
					
					
					//Obtener monto
					String montoTexto = txtMonto.getText().trim();
					if (montoTexto.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar un monto.");
						return;
					}
					
					BigDecimal monto = new BigDecimal(montoTexto);
					if (monto.compareTo(BigDecimal.ZERO) <= 0) {
		                JOptionPane.showMessageDialog(null, "El monto debe ser mayor que cero.");
		                return;
		            }
					
					Prestamo prestamo = new Prestamo(idMiembro, monto, fechaSQL, estado);
					prestamo.setSaldoRestante(monto);
					PrestamoDAO dao = new PrestamoDAO();
					
					if(modoEdicion) {
						//modo edicion
						prestamo.setId(idPrestamoEditar);
						if(dao.actualizar(prestamo)) {
							JOptionPane.showMessageDialog(null, "Aporte actualizado correctamente.");
							
							List<Prestamo> lista = dao.listarTodo();
					        cargarDatosEnTabla(lista);
						}else {
							JOptionPane.showMessageDialog(null, "Error al actualizar aporte.");
						}
						
						modoEdicion = false;
						idPrestamoEditar = -1;
					} else {
						//modo nuevo
						if(dao.insertar(prestamo)) {
							JOptionPane.showMessageDialog(null, "Aporte registrado correctamente");
							
							//refrescar tabla
							//List<Aporte> lista = dao.listarPorMiembro(idMiembro);
							List<Prestamo> lista = dao.listarTodo();
							cargarDatosEnTabla(lista);
						}else {
							JOptionPane.showMessageDialog(null, "Error al registrar aporte");
						}
					}
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Monto invalido. Ingrese un monto valido");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
				}
				
				limpiar();
			}
		});
		btnGuardar.setIcon(new ImageIcon(SolicitudPrestamos.class.getResource("/img/guardar.png")));
		btnGuardar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnGuardar.setContentAreaFilled(false);
		btnGuardar.setBounds(33, 299, 115, 27);
		botonTransparente(btnGuardar);
		contenido.add(btnGuardar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int filaSeleccionada = tblInformacion.getSelectedRow();
				
				if(filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione aporte para eliminar.");
					return;
				}
				
				int opcion = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminar este aporte?","Confirmar",JOptionPane.YES_NO_OPTION);
				if(opcion != JOptionPane.YES_OPTION) {
					return;
				}
				
				int idPrestamo = Integer.parseInt(tblInformacion.getValueAt(filaSeleccionada, 0).toString());
				PrestamoDAO dao = new PrestamoDAO();
				
				if(dao.eliminar(idPrestamo)) {
					JOptionPane.showMessageDialog(null, "Aporte eliminado correctamente.");
					cargarDatosEnTabla(dao.listarTodo());
				}else {
					JOptionPane.showMessageDialog(null, "Error al eliminar prestamo.");
				}
			}
		});
		btnEliminar.setIcon(new ImageIcon(SolicitudPrestamos.class.getResource("/img/eliminar.png")));
		btnEliminar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnEliminar.setBounds(879, 299, 115, 27);
		botonTransparente(btnEliminar);
		contenido.add(btnEliminar);
		
		cbMiembros = new JComboBox();
		cbMiembros.setForeground(new Color(106, 106, 106));
		cbMiembros.setModel(new DefaultComboBoxModel(new String[] {"Selccionar Miembro"}));
		cbMiembros.setFont(new Font("Arial", Font.PLAIN, 12));
		cbMiembros.setBounds(436, 56, 252, 25);
		contenido.add(cbMiembros);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(436, 172, 115, 24);
		contenido.add(txtMonto);
		txtMonto.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Arial", Font.PLAIN, 17));
		lblEstado.setBounds(271, 223, 129, 24);
		contenido.add(lblEstado);
		
		cbEstado = new JComboBox();
		cbEstado.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Finalizado"}));
		cbEstado.setForeground(new Color(106, 106, 106));
		cbEstado.setFont(new Font("Arial", Font.PLAIN, 12));
		cbEstado.setBounds(436, 222, 115, 25);
		contenido.add(cbEstado);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tblInformacion.getSelectedRow();
				if(filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un aporte para editar");
					return;
				}
				
				//Obtener datos de la tabla
				idPrestamoEditar = Integer.parseInt(tblInformacion.getValueAt(filaSeleccionada, 0).toString());
				String nombreMiembro = tblInformacion.getValueAt(filaSeleccionada, 1).toString();
				//int idMiembro = Integer.parseInt(tblInformacion.getValueAt(filaSeleccionada, 0).toString());
				java.sql.Date fechaSQL = java.sql.Date.valueOf(tblInformacion.getValueAt(filaSeleccionada, 2).toString());
				BigDecimal monto = new BigDecimal(tblInformacion.getValueAt(filaSeleccionada, 3).toString());
				
				//cargar datos editados
				for(int i = 1; i < cbMiembros.getItemCount(); i++) {//empieza en uno para evitar "seleccionar miembro"
					Object item = cbMiembros.getItemAt(i);
					if(item instanceof Miembro) {
						Miembro m = (Miembro) item;
						if(m.getNombre_completo().equalsIgnoreCase(nombreMiembro)) {
							cbMiembros.setSelectedItem(i);
							break;
						}
					}
				}
				
				txtMonto.setText(monto.toString());
				fecha.setDate(new java.util.Date(fechaSQL.getTime()));
				
				//establecer modo edicion
				modoEdicion = true;
			}
		});
		btnEditar.setIcon(new ImageIcon(SolicitudPrestamos.class.getResource("/img/editar.png")));
		btnEditar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnEditar.setBounds(436, 302, 115, 27);
		botonTransparente(btnEditar);
		contenido.add(btnEditar);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JLabel lblSolicitudPrestamos = new JLabel("Solicitud de Prestamos");
		lblSolicitudPrestamos.setIcon(new ImageIcon(SolicitudPrestamos.class.getResource("/img/prestamos_icono.png")));
		lblSolicitudPrestamos.setFont(new Font("Arial", Font.PLAIN, 15));
		menuBar.add(lblSolicitudPrestamos);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		JButton btnVolverMenu = new JButton("Volver a menu");
		btnVolverMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				principal.setVisible(true);
			}
		});
		btnVolverMenu.setIcon(new ImageIcon(SolicitudPrestamos.class.getResource("/img/volver_icono2.png")));
		btnVolverMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		botonTransparente(btnVolverMenu);
		menuBar.add(btnVolverMenu);
		
		PrestamoDAO dao = new PrestamoDAO();
		List<Prestamo> lista = dao.listarTodo();
		cargarDatosEnTabla(lista);
		
		setLocationRelativeTo(null);
		cargarMiembros();
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
	
	public void cargarDatosEnTabla(List<Prestamo> prestamo) {
		DefaultTableModel model = (DefaultTableModel) tblInformacion.getModel();
		model.setRowCount(0); //Limpiar tabla
		
		for(Prestamo p : prestamo) {
			model.addRow(new Object[] {
					p.getId(),
					p.getNombreMiembro(),
					p.getFechaInicio(),
					p.getFechaFinalizacion(),
					p.getMontoOriginal(),
					p.getSaldoRestante(),
					p.isEstado() ? "Activo" : "Finalizado"
			});
		}
	}
	
	public void cargarMiembros() {
		cbMiembros.removeAllItems(); //limpia todo
		cbMiembros.addItem("Seleccione miembro");
		
		List<Miembro> lista = MiembroDAO.listarPorNombre();
		
		for(Miembro m : lista) {
			cbMiembros.addItem(m);
		}
	}
	
	public void limpiar() {
		cbMiembros.setSelectedIndex(0);
		fecha.setDate(null);
		txtMonto.setText("");
		cbEstado.setSelectedItem("Activo");
		modoEdicion = false;
	}
}
