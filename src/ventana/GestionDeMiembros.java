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

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Miembro;
import dao.MiembroDAO;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class GestionDeMiembros extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();
	private JTextField txtBuscarMiembro;
	private JTable tblMostrarInformacion;
	private Principal principal;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			GestionDeMiembros dialog = new GestionDeMiembros();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public GestionDeMiembros(Principal principal) {
		super(principal,"Gestion de Miembros", true);
		this.principal = principal;
		setBounds(100, 100, 1080, 800);
		getContentPane().setLayout(new BorderLayout());
		contenido.setBackground(new Color(255, 255, 255));
		contenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contenido, BorderLayout.CENTER);
		contenido.setLayout(null);
		{
			txtBuscarMiembro = new JTextField();
			txtBuscarMiembro.setForeground(new Color(0, 0, 0));
			txtBuscarMiembro.setText("Buscar miembro por nombre");
			txtBuscarMiembro.setFont(new Font("Arial", Font.PLAIN, 15));
			txtBuscarMiembro.setBounds(51, 40, 366, 30);
			contenido.add(txtBuscarMiembro);
			txtBuscarMiembro.setColumns(10);
		}
		
		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtBuscarMiembro.getText().trim();
				List<Miembro> resultados = MiembroDAO.buscarMiembro(nombre);
				cargarDatosEnTabla(resultados);
			}
		});
		btnBuscar.setIcon(new ImageIcon(GestionDeMiembros.class.getResource("/img/buscar.png")));
		btnBuscar.setBounds(427, 40, 42, 30);
		botonTransparente(btnBuscar);
		contenido.add(btnBuscar);
		
		JButton btnAgregar = new JButton("Agregar Miembro");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AgregarMiembro dialog = new AgregarMiembro(GestionDeMiembros.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					
					//listener para recargar tabla
					dialog.addWindowListener(new java.awt.event.WindowAdapter() {
						public void windowClosed(java.awt.event.WindowEvent e) {
							cargarDatosEnTabla(MiembroDAO.listarTodos());
						}
					});
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnAgregar.setFont(new Font("Arial", Font.PLAIN, 10));
		btnAgregar.setBounds(507, 40, 121, 30);
		botonTransparente(btnAgregar);
		contenido.add(btnAgregar);
		
		tblMostrarInformacion = new JTable();
		tblMostrarInformacion.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		//tblMostrarInformacion.setFont(new Font("Arial", Font.PLAIN, 15));
		configurarTabla(tblMostrarInformacion, 200, 40);
		tblMostrarInformacion.setModel(new DefaultTableModel(
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
			},
			new String[] {
				"ID", "Nombre Completo", "Fecha de Ingreso", "Estado"
			}
		));
		tblMostrarInformacion.setBounds(51, 143, 959, 405);
		contenido.add(tblMostrarInformacion);
		
		JScrollPane scrollpane = new JScrollPane(tblMostrarInformacion);
		scrollpane.setBounds(51, 143, 959, 405);
		contenido.add(scrollpane);
		
		{
			JButton btnEditar = new JButton("Editar");
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int filaSeleccionada = tblMostrarInformacion.getSelectedRow();
					
					if(filaSeleccionada == -1) {
						JOptionPane.showMessageDialog(null, "Seleccione miembro para editar.");
						return;
					}
					
					//datos de la tabla
					int id = Integer.parseInt(tblMostrarInformacion.getValueAt(filaSeleccionada, 0).toString());
					String nombre = tblMostrarInformacion.getValueAt(filaSeleccionada, 1).toString();
					java.sql.Date fecha = java.sql.Date.valueOf(tblMostrarInformacion.getValueAt(filaSeleccionada, 2).toString());
					String estadoTexto = tblMostrarInformacion.getValueAt(filaSeleccionada, 3).toString();
					boolean estado = estadoTexto.equalsIgnoreCase("Activo");
					
					Miembro m = new Miembro(id, nombre, estado, fecha);
					
					
					try {
						EditarMiembro dialog = new EditarMiembro(m, GestionDeMiembros.this);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						
						//listener para recargar tabla
						dialog.addWindowListener(new java.awt.event.WindowAdapter() {
							public void windowClosed(java.awt.event.WindowEvent e) {
								cargarDatosEnTabla(MiembroDAO.listarTodos());
							}
						});
						dialog.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			btnEditar.setIcon(new ImageIcon(GestionDeMiembros.class.getResource("/img/editar.png")));
			btnEditar.setFont(new Font("Arial", Font.PLAIN, 10));
			btnEditar.setBounds(51, 606, 106, 30);
			botonTransparente(btnEditar);
			contenido.add(btnEditar);
		}
		{
			JButton btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int filaSeleccionada = tblMostrarInformacion.getSelectedRow();
					
					if(filaSeleccionada == -1) {
						JOptionPane.showMessageDialog(null, "Seleccione miembro para eliminar.");
						return;
					}
					
					//datos de la tabla
					int id = Integer.parseInt(tblMostrarInformacion.getValueAt(filaSeleccionada, 0).toString());
					String nombre = tblMostrarInformacion.getValueAt(filaSeleccionada, 1).toString();
					java.sql.Date fecha = java.sql.Date.valueOf(tblMostrarInformacion.getValueAt(filaSeleccionada, 2).toString());
					String estadoTexto = tblMostrarInformacion.getValueAt(filaSeleccionada, 3).toString();
					boolean estado = estadoTexto.equalsIgnoreCase("Activo");
					
					Miembro m = new Miembro(id, nombre, estado, fecha);
					
					try {
						EliminarMiembro dialog = new EliminarMiembro(m,GestionDeMiembros.this);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						
						//listener para recargar tabla
						dialog.addWindowListener(new java.awt.event.WindowAdapter() {
							public void windowClosed(java.awt.event.WindowEvent e) {
								cargarDatosEnTabla(MiembroDAO.listarTodos());
							}
						});
						dialog.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			btnEliminar.setIcon(new ImageIcon(GestionDeMiembros.class.getResource("/img/eliminar.png")));
			btnEliminar.setFont(new Font("Arial", Font.PLAIN, 10));
			btnEliminar.setBounds(889, 606, 121, 30);
			botonTransparente(btnEliminar);
			contenido.add(btnEliminar);
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JLabel lblNewLabel = new JLabel("Gestion de Miembros");
				lblNewLabel.setIcon(new ImageIcon(GestionDeMiembros.class.getResource("/img/miembros_icono2.png")));
				lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
				menuBar.add(lblNewLabel);
			}
			menuBar.add(Box.createHorizontalGlue());
			{
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
				btnVolverMenu.setIcon(new ImageIcon(GestionDeMiembros.class.getResource("/img/volver_icono2.png")));
				btnVolverMenu.setFont(new Font("Arial", Font.PLAIN, 18));
				botonTransparente(btnVolverMenu);
				menuBar.add(btnVolverMenu);
			}
		}
		cargarDatosEnTabla(MiembroDAO.listarTodos());
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
	
	public void cargarDatosEnTabla(List<Miembro> miembros) {
		DefaultTableModel model = (DefaultTableModel) tblMostrarInformacion.getModel();
		model.setRowCount(0); //Limpiar tabla
		
		for(Miembro m : miembros) {
			model.addRow(new Object[] {
					m.getId(),
					m.getNombre_completo(),
					m.getFecha_ingreso(),
					m.isEstado() ? "Activo" : "Inactivo"
			});
		}
	}
}
