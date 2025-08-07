package ventana;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.MatteBorder;
import com.toedter.calendar.JDateChooser;

import modelo.Aporte;
import modelo.Miembro;
import dao.AporteDAO;
import dao.MiembroDAO;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class GestionDeAportes extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();
	private JTable tblInformacion;
	private JTextField txtMonto;
	private JComboBox cbMiembros;
	private JDateChooser fecha;
	
	//vareables auxiliares
	private boolean modoEdicion = false;
	private int idAporteEditar = -1;
	private Principal principal;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			GestionDeAportes dialog = new GestionDeAportes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public GestionDeAportes(Principal principal) {
		super(principal,"Gestion de Aportes", true);
		this.principal = principal;
		setBounds(100, 100, 1080, 950);
		getContentPane().setLayout(new BorderLayout());
		contenido.setBackground(new Color(255, 255, 255));
		getContentPane().add(contenido, BorderLayout.CENTER);
		contenido.setLayout(null);
		
		tblInformacion = new JTable();
		tblInformacion.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tblInformacion.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"ID", "Nombre", "Monto", "Fecha"
			}
		));
		tblInformacion.setFont(new Font("Arial", Font.PLAIN, 15));
		tblInformacion.setBounds(2, 23, 940, 274);
		tblInformacion.getColumnModel().getColumn(0).setMinWidth(0);
		tblInformacion.getColumnModel().getColumn(0).setMaxWidth(0);
		tblInformacion.getColumnModel().getColumn(0).setWidth(0);
		configurarTabla(tblInformacion, 200, 40);
		contenido.add(tblInformacion);
		
		JScrollPane scrollpane = new JScrollPane(tblInformacion);
		scrollpane.setBounds(215, 438, 615, 356);
		contenido.add(scrollpane);

		JLabel lblHistorialAportes = new JLabel("Historial de Aportes");
		lblHistorialAportes.setOpaque(true);//
		lblHistorialAportes.setBackground(new Color(192, 192, 192));
		lblHistorialAportes.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistorialAportes.setFont(new Font("Arial", Font.PLAIN, 30));
		lblHistorialAportes.setBounds(2, 354, 1064, 41);
		contenido.add(lblHistorialAportes);
		
		JLabel lblMiembro = new JLabel("Miembro:");
		lblMiembro.setFont(new Font("Arial", Font.PLAIN, 18));
		lblMiembro.setBounds(290, 29, 129, 24);
		contenido.add(lblMiembro);
		
		cbMiembros = new JComboBox();
		cbMiembros.setModel(new DefaultComboBoxModel(new String[] {"Selecciones un miembro"}));
		cbMiembros.setFont(new Font("Arial", Font.PLAIN, 14));
		cbMiembros.setBounds(455, 28, 280, 25);
		contenido.add(cbMiembros);
		cargarMiembros();
		
		JLabel lblFecha = new JLabel("Fecha de Ingreso:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 18));
		lblFecha.setBounds(290, 87, 155, 25);
		contenido.add(lblFecha);
		
		fecha = new JDateChooser();
		fecha.setToolTipText("");
		fecha.setBounds(455, 87, 143, 25);
		contenido.add(fecha);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setFont(new Font("Arial", Font.PLAIN, 18));
		lblMonto.setBounds(290, 148, 99, 24);
		contenido.add(lblMonto);
		
		txtMonto = new JTextField();
		txtMonto.setFont(new Font("Arial", Font.PLAIN, 14));
		txtMonto.setBounds(455, 148, 115, 24);
		contenido.add(txtMonto);
		txtMonto.setColumns(10);
		
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
					//String estado = cbEstado.getSelectedItem().toString();
					
					String montoTexto = txtMonto.getText().trim();
					if (montoTexto.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar un monto.");
						return;
					}
					
					BigDecimal monto = new BigDecimal(montoTexto);
					if(monto.compareTo(new BigDecimal("500")) < 0) {
						JOptionPane.showMessageDialog(null, "Monto minimo permitido Lps. 500.");
						return;
					}
					
					Aporte aporte = new Aporte(idMiembro, monto, fechaSQL);
					AporteDAO dao = new AporteDAO();
					
					if(modoEdicion) {
						//modo edicion
						aporte.setId(idAporteEditar);
						if(dao.actualizar(aporte)) {
							JOptionPane.showMessageDialog(null, "Aporte actualizado correctamente.");
							
							List<Aporte> lista = dao.listarTodo();
					        cargarDatosEnTabla(lista);
						}else {
							JOptionPane.showMessageDialog(null, "Error al actualizar aporte.");
						}
						
						modoEdicion = false;
						idAporteEditar = -1;
					} else {
						//modo nuevo
						if(dao.insertar(aporte)) {
							JOptionPane.showMessageDialog(null, "Aporte registrado correctamente");
							
							//refrescar tabla
							//List<Aporte> lista = dao.listarPorMiembro(idMiembro);
							List<Aporte> lista = dao.listarTodo();
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
		btnGuardar.setIcon(new ImageIcon(GestionDeAportes.class.getResource("/img/guardar.png")));
		btnGuardar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnGuardar.setContentAreaFilled(false);
		botonTransparente(btnGuardar);
		btnGuardar.setBounds(70, 275, 115, 27);
		contenido.add(btnGuardar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tblInformacion.getSelectedRow();
				
				if(filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione aporte para eliminar.");
					return;
				}
				
				int opcion = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminar este aporte?","Confirmar",JOptionPane.YES_NO_OPTION);
				if(opcion != JOptionPane.YES_OPTION) {
					return;
				}
				
				int idAporte = Integer.parseInt(tblInformacion.getValueAt(filaSeleccionada, 0).toString());
				AporteDAO dao = new AporteDAO();
				
				if(dao.eliminar(idAporte)) {
					JOptionPane.showMessageDialog(null, "Aporte eliminado correctamente.");
					cargarDatosEnTabla(dao.listarTodo());
				}else {
					JOptionPane.showMessageDialog(null, "Error al eliminar aporte.");
				}
			}
		});
		btnEliminar.setIcon(new ImageIcon(GestionDeAportes.class.getResource("/img/eliminar_icono.png")));
		btnEliminar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnEliminar.setBounds(880, 275, 115, 27);
		botonTransparente(btnEliminar);
		contenido.add(btnEliminar);
		
		
		JLabel lblRecomendacion = new JLabel("(Minimo Lps. 500)");
		lblRecomendacion.setFont(new Font("Arial", Font.PLAIN, 18));
		lblRecomendacion.setBounds(606, 148, 146, 24);
		contenido.add(lblRecomendacion);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tblInformacion.getSelectedRow();
				if(filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un aporte para editar");
					return;
				}
				
				//Obtener datos de la tabla
				idAporteEditar = Integer.parseInt(tblInformacion.getValueAt(filaSeleccionada, 0).toString());
				String nombreMiembro = tblInformacion.getValueAt(filaSeleccionada, 1).toString();
				//int idMiembro = Integer.parseInt(tblInformacion.getValueAt(filaSeleccionada, 0).toString());
				BigDecimal monto = new BigDecimal(tblInformacion.getValueAt(filaSeleccionada, 2).toString());
				java.sql.Date fechaSQL = java.sql.Date.valueOf(tblInformacion.getValueAt(filaSeleccionada, 3).toString());
				
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
					
					/*Miembro m = (Miembro) cbMiembros.getItemAt(i);
					if(m.getId() == idMiembro) {
						cbMiembros.setSelectedIndex(i);
						break;
					}*/
				}
				
				txtMonto.setText(monto.toString());
				fecha.setDate(new java.util.Date(fechaSQL.getTime()));
				
				//establecer modo edicion
				modoEdicion = true;
			}
		});
		btnEditar.setIcon(new ImageIcon(GestionDeAportes.class.getResource("/img/editar_icono.png")));
		btnEditar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnEditar.setBounds(455, 275, 115, 27);
		botonTransparente(btnEditar);
		contenido.add(btnEditar);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JLabel lblGestionDeAportes = new JLabel("Gestion de Aportes");
		lblGestionDeAportes.setIcon(new ImageIcon(GestionDeAportes.class.getResource("/img/aportes_icono.png")));
		lblGestionDeAportes.setFont(new Font("Arial", Font.PLAIN, 15));
		menuBar.add(lblGestionDeAportes);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		JButton btnVolverMenu = new JButton("Volver a menu");
		btnVolverMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				principal.setVisible(true);
				/*try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}*/
			}
		});
		btnVolverMenu.setIcon(new ImageIcon(GestionDeAportes.class.getResource("/img/volver_icono2.png")));
		btnVolverMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		botonTransparente(btnVolverMenu);
		menuBar.add(btnVolverMenu);
		
		AporteDAO dao = new AporteDAO();
		List<Aporte> lista = dao.listarTodo();
		cargarDatosEnTabla(lista);

		
		setLocationRelativeTo(null);
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
	
	public void cargarDatosEnTabla(List<Aporte> aportes) {
		DefaultTableModel model = (DefaultTableModel) tblInformacion.getModel();
		model.setRowCount(0); //Limpiar tabla
		
		for(Aporte a : aportes) {
			model.addRow(new Object[] {
					a.getId(),
					a.getNombreMiembro(),
					a.getMonto(),
					a.getFecha_aporte()
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
		modoEdicion = false;
	}
}
