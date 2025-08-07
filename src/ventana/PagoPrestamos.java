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
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;

import dao.MiembroDAO;
import dao.PagoPrestamoDAO;
import dao.PrestamoDAO;
import modelo.Miembro;
import modelo.PagoPrestamo;
import modelo.Prestamo;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class PagoPrestamos extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();
	private JTable tblInformacion;
	private JTextField txtMonto;
	private JTextField txtSaldoActual;
	private JComboBox cbMiembros = new JComboBox();
	private JDateChooser fecha = new JDateChooser();

	//vareables auxiliares
	private boolean modoEdicion = false;
	private int idPrestamoEditar = -1;
	private Principal principal;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			PagoPrestamos dialog = new PagoPrestamos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public PagoPrestamos(Principal principal) {
		super(principal,"Pago de Prestamo", true);
		this.principal = principal;
		setBounds(100, 100, 1000, 950);
		getContentPane().setLayout(new BorderLayout());
		contenido.setBackground(new Color(255, 255, 255));
		contenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contenido, BorderLayout.CENTER);
		contenido.setLayout(null);
		{
			tblInformacion = new JTable();
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
					"Fecha", "Monto Pagado", "Saldo restante"
				}
			));
			tblInformacion.setFont(new Font("Arial", Font.PLAIN, 15));
			tblInformacion.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			tblInformacion.setBounds(33, 427, 915, 222);
			configurarTabla(tblInformacion, 146, 30);
			contenido.add(tblInformacion);
			
			JScrollPane scrollpane = new JScrollPane(tblInformacion);
			scrollpane.setBounds(285, 438, 455, 356);
			contenido.add(scrollpane);
		}
		{
			JLabel lblHistorialPrestamos = new JLabel("Historial de pagos de Prestamo del Miembro");
			lblHistorialPrestamos.setHorizontalAlignment(SwingConstants.CENTER);
			lblHistorialPrestamos.setOpaque(true);
			lblHistorialPrestamos.setFont(new Font("Arial", Font.PLAIN, 30));
			lblHistorialPrestamos.setBackground(Color.LIGHT_GRAY);
			lblHistorialPrestamos.setBounds(0, 339, 1064, 41);
			contenido.add(lblHistorialPrestamos);
		}
		{
			JLabel lblMiembro = new JLabel("Miembro:");
			lblMiembro.setFont(new Font("Arial", Font.PLAIN, 17));
			lblMiembro.setBounds(33, 43, 129, 18);
			contenido.add(lblMiembro);
		}
		{
			JLabel lblFecha = new JLabel("Fecha de Ingreso:");
			lblFecha.setFont(new Font("Arial", Font.PLAIN, 17));
			lblFecha.setBounds(33, 101, 155, 18);
			contenido.add(lblFecha);
		}
		{
			fecha.setToolTipText("");
			fecha.setBounds(198, 101, 143, 25);
			contenido.add(fecha);
		}
		{
			JLabel lblMonto = new JLabel("Monto:");
			lblMonto.setFont(new Font("Arial", Font.PLAIN, 17));
			lblMonto.setBounds(33, 158, 99, 18);
			contenido.add(lblMonto);
		}
		{
			JButton btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//validar miembro
					if(cbMiembros.getSelectedIndex() <= 0) {
						JOptionPane.showMessageDialog(null, "Seleccione miembro");
						return;
					}
					
					Miembro miembro = (Miembro) cbMiembros.getSelectedItem();
					//System.out.println("ID del miembro seleccionado: " + miembro.getId());
					
					List<Prestamo> prestamoActivo = PrestamoDAO.prestamosActivosPorMiembro(miembro.getId());
					
					if(prestamoActivo.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Este miembro no tiene prestamos activos.");
						return;
					}
					
					Prestamo prestamo = prestamoActivo.get(0);
					
					try {
						BigDecimal montoPagado = new BigDecimal(txtMonto.getText().trim());
						if(montoPagado.compareTo(BigDecimal.ZERO) <= 0) {
							JOptionPane.showMessageDialog(null, "El monto debe ser mayor que cero.");
							return;
						}
						
						if(montoPagado.compareTo(prestamo.getSaldoRestante()) > 0) {
							JOptionPane.showMessageDialog(null, "El monto excede el saldo restante.");
							return;
						}
						
						java.util.Date fechaUtil = fecha.getDate();
						if(fechaUtil == null) {
							JOptionPane.showMessageDialog(null, "Seleccione una fecha de pago.");
							return;
						}
						
						//crear objeto de PagoPrestamo
						PagoPrestamo pago = new PagoPrestamo();
						pago.setPrestamo(prestamo);
						pago.setMontoPagado(montoPagado);
						pago.setFechaPago(new java.sql.Date(fechaUtil.getTime()));
						
						
						
						//aplicar pago a todos los prestamos activos de miembro
						PrestamoDAO.aplicarPagoMultiples(miembro.getId(), montoPagado, new java.sql.Date(fechaUtil.getTime()));
							
						JOptionPane.showMessageDialog(null, "Pago registrado correctamente.");
						
						//Obtener nuevamente el prestamos mas antiguo
						List<Prestamo> prestamosActualizados = PrestamoDAO.prestamosActivosPorMiembro(miembro.getId());
						Prestamo prestamoActualizado = prestamosActualizados.isEmpty() ? null : prestamosActualizados.get(0);
							
						if(prestamoActualizado != null) {
							txtSaldoActual.setText(prestamoActualizado.getSaldoRestante().toPlainString());
							
							//cargar los datos del prestamo actualizado (mas antiguo)
							List<PagoPrestamo> pagos = PagoPrestamoDAO.listarPorPrestamo(prestamoActualizado.getId());
							cargarDatosEnTabla(pagos);
						}else {
							txtSaldoActual.setText("0.00");
							cargarDatosEnTabla(new ArrayList<>()); //limpia la tabla si no hay prestamos
						}

						//limpiar campo de monto
						txtMonto.setText("");
						
							//JOptionPane.showMessageDialog(null, "Error al registrar el pago.");
						
					} catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Ingrese un monto valido");
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage());
					}
				}
			});
			btnGuardar.setIcon(new ImageIcon(PagoPrestamos.class.getResource("/img/guardar.png")));
			btnGuardar.setFont(new Font("Arial", Font.PLAIN, 12));
			btnGuardar.setContentAreaFilled(false);
			btnGuardar.setBounds(33, 272, 115, 27);
			botonTransparente(btnGuardar);
			contenido.add(btnGuardar);
		}
		{
			JButton btnEliminar = new JButton("Eliminar");
			btnEliminar.setIcon(new ImageIcon(PagoPrestamos.class.getResource("/img/eliminar_icono.png")));
			btnEliminar.setFont(new Font("Arial", Font.PLAIN, 12));
			btnEliminar.setBounds(833, 272, 115, 27);
			botonTransparente(btnEliminar);
			contenido.add(btnEliminar);
		}
		{
			cbMiembros.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbMiembros.getSelectedIndex() > 0) {
						Miembro seleccionado = (Miembro) cbMiembros.getSelectedItem();

						//Obtener todos los prestamos activos del miembro
						List<Prestamo> prestamos = PrestamoDAO.prestamosActivosPorMiembro(seleccionado.getId());
						
						if(!prestamos.isEmpty()) {
							//calcular saldo total REAL basado en monto - pagod
							BigDecimal saldoTotal = BigDecimal.ZERO;
							
							for(Prestamo prestamo : prestamos) {
								BigDecimal montoPagado = BigDecimal.ZERO;
								
								List<PagoPrestamo> pagos = PagoPrestamoDAO.listarPorPrestamo(prestamo.getId());
								for(PagoPrestamo pago : pagos) {
									montoPagado = montoPagado.add(pago.getMontoPagado());
								}
								
								BigDecimal saldoReal = prestamo.getMontoOriginal().subtract(montoPagado);
								saldoTotal = saldoTotal.add(saldoReal);
							}
							
							txtSaldoActual.setText(saldoTotal.toPlainString());
							
							//Mostrar pagos del prestamo mas antiguo
							Prestamo prestamoMasAntiguo = PrestamoDAO.obtenerPrestamoMasAntiguo(seleccionado.getId());
							List<PagoPrestamo> pagos = new PagoPrestamoDAO().listarPorPrestamo(prestamoMasAntiguo.getId());
							cargarDatosEnTabla(pagos);
						}else {
							txtSaldoActual.setText("0.00");
							cargarDatosEnTabla(new ArrayList<>()); //limpiar tabla
							JOptionPane.showMessageDialog(null, "Este miembro no tiene mas prestamos pendientes.");
						}
					}
				}
			});
			cbMiembros.setModel(new DefaultComboBoxModel(new String[] {"Seleccione Miembro"}));
			cbMiembros.setForeground(new Color(106, 106, 106));
			cbMiembros.setFont(new Font("Arial", Font.PLAIN, 12));
			cbMiembros.setBounds(198, 42, 252, 25);
			contenido.add(cbMiembros);
		}
		{
			txtMonto = new JTextField();
			txtMonto.setColumns(10);
			txtMonto.setBounds(198, 158, 115, 24);
			contenido.add(txtMonto);
		}
		{
			JLabel lblSaldoActual = new JLabel("Saldo Actual:");
			lblSaldoActual.setFont(new Font("Arial", Font.PLAIN, 17));
			lblSaldoActual.setBounds(33, 209, 99, 18);
			contenido.add(lblSaldoActual);
		}
		{
			txtSaldoActual = new JTextField();
			txtSaldoActual.setColumns(10);
			txtSaldoActual.setBounds(198, 209, 115, 24);
			txtSaldoActual.setEditable(false);
			contenido.add(txtSaldoActual);
		}
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(PagoPrestamos.class.getResource("/img/editar_icono.png")));
		btnEditar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnEditar.setBounds(405, 272, 115, 27);
		contenido.add(btnEditar);
		botonTransparente(btnEditar);
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JLabel lblPagoPrestamo = new JLabel("Pago de Prestamos");
				lblPagoPrestamo.setIcon(new ImageIcon(PagoPrestamos.class.getResource("/img/pago_prestamos_icono.png")));
				lblPagoPrestamo.setFont(new Font("Arial", Font.PLAIN, 15));
				menuBar.add(lblPagoPrestamo);
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
				btnVolverMenu.setIcon(new ImageIcon(PagoPrestamos.class.getResource("/img/volver_icono2.png")));
				btnVolverMenu.setFont(new Font("Arial", Font.PLAIN, 18));
				botonTransparente(btnVolverMenu);
				menuBar.add(btnVolverMenu);
			}
		}
		setLocationRelativeTo(null);
		cargarMiembros();
	}
	
	public static void botonTransparente(JButton boton) {
		//boton.setBorderPainted(false); //no pinta el bonde
		boton.setContentAreaFilled(false);
		//boton.setFocusPainted(false); //no pinta el borde cuando enfoca
	}
	
	/*public Prestamo obtenerPrestamoActivo(int idMiembro) {
		return PrestamoDAO.prestamosActivosPorMiembro(idMiembro);
	}*/
	
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
	
	public void cargarDatosEnTabla(List<PagoPrestamo> pagos) {
		DefaultTableModel model = (DefaultTableModel) tblInformacion.getModel();
		model.setRowCount(0); //Limpiar tabla
		
		for(PagoPrestamo pr : pagos) {
			
			Prestamo prestamoActualizado = PrestamoDAO.obtenerPorId(pr.getPrestamo().getId());
			
			model.addRow(new Object[] {
					pr.getFechaPago(),
					pr.getMontoPagado(),
					prestamoActualizado != null ? prestamoActualizado.getSaldoRestante() : "N/A"
			});
		}
	}
	
	
	public void cargarMiembros() {
		cbMiembros.removeAllItems(); //limpia todo
		cbMiembros.addItem("Seleccione miembro");
		
		List<Miembro> lista = MiembroDAO.listarPorNombre();
		System.out.println("Miembros cargados: " + lista.size()); // 👈 Aquí
		
		for(Miembro m : lista) {
			System.out.println("→ " + m.getNombre_completo()); // 👈 Aquí
			cbMiembros.addItem(m);
		}
	}
	
	public void limpiar() {
		cbMiembros.setSelectedIndex(0);
		fecha.setDate(null);
		txtMonto.setText("");
		txtSaldoActual.setText("");
		modoEdicion = false;
	}
}
