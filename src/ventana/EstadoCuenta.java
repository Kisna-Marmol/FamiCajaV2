package ventana;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import dao.AporteDAO;
import dao.InteresDAO;
import dao.MiembroDAO;
import dao.PagoPrestamoDAO;
import dao.PrestamoDAO;
import modelo.Miembro;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class EstadoCuenta extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();
	private JLabel lblTotalAportado;
	private JLabel lblTotalPrestamo;
	private JLabel lblTotalPago;
	private JLabel lblTotalInteres;
	private JLabel lblInteresPagado;
	private JLabel lblSaldoPrestamo;
	private JLabel lblSaldoPropio;
	private JComboBox cbMiembros = new JComboBox();
	private Principal principal;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			EstadoCuenta dialog = new EstadoCuenta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public EstadoCuenta(Principal principal) {
		super(principal,"Estado de Cuenta", true);
		this.principal = principal;
		setBounds(100, 100, 500, 658);
		getContentPane().setLayout(new BorderLayout());
		contenido.setBackground(new Color(255, 255, 255));
		contenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contenido, BorderLayout.CENTER);
		contenido.setLayout(null);
		{
			JLabel lblMiembro = new JLabel("Miembro:");
			lblMiembro.setFont(new Font("Arial", Font.PLAIN, 17));
			lblMiembro.setBounds(23, 67, 129, 25);
			contenido.add(lblMiembro);
		}
		{
			cbMiembros.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Miembro seleccionado = (Miembro) cbMiembros.getSelectedItem();

			        if (seleccionado != null && seleccionado.getId() != 0) {
			            int idMiembro = seleccionado.getId();

			            BigDecimal totalAportado = AporteDAO.getTotalAportado(idMiembro);
			            BigDecimal totalPrestamos = PrestamoDAO.getTotalPrestamos(idMiembro);
			            BigDecimal totalPagos = PagoPrestamoDAO.getTotalPagado(idMiembro);
			            BigDecimal totalIntereses = InteresDAO.getTotalIntereses(idMiembro);
			            BigDecimal interesesPagados = InteresDAO.getInteresesPagados(idMiembro);
			            BigDecimal saldoPrestamos = PrestamoDAO.getSaldoPrestamos(idMiembro);

			            // Puedes ajustar la fórmula de saldo a favor si usas otra lógica
			            BigDecimal saldoAFavor = totalAportado.subtract(totalPrestamos)
			                                                 .add(totalPagos)
			                                                 .subtract(interesesPagados);

			            lblTotalAportado.setText("L. " + totalAportado);
			            lblTotalPrestamo.setText("L. " + totalPrestamos);
			            lblTotalPago.setText("L. " + totalPagos);
			            lblTotalInteres.setText("L. " + totalIntereses);
			            lblInteresPagado.setText("L. " + interesesPagados);
			            lblSaldoPrestamo.setText("L. " + saldoPrestamos);
			            lblSaldoPropio.setText("L. " + saldoAFavor);
			        }
				}
			});
			cbMiembros.setModel(new DefaultComboBoxModel(new String[] {"Seleccione un miembro"}));
			cbMiembros.setForeground(new Color(106, 106, 106));
			cbMiembros.setFont(new Font("Arial", Font.PLAIN, 12));
			cbMiembros.setBounds(188, 66, 252, 25);
			contenido.add(cbMiembros);
		}
		
		JPanel panelCuenta = new JPanel();
		panelCuenta.setBackground(new Color(255, 255, 255));
		panelCuenta.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelCuenta.setBounds(21, 121, 442, 424);
		contenido.add(panelCuenta);
		GridBagLayout gbl_panelCuenta = new GridBagLayout();
		gbl_panelCuenta.columnWidths = new int[]{300, 200};
		gbl_panelCuenta.rowHeights = new int[]{35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCuenta.columnWeights = new double[]{0.5, 0.5};
		gbl_panelCuenta.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelCuenta.setLayout(gbl_panelCuenta);
		
		JLabel lblCuenta = new JLabel("Cuenta");
		lblCuenta.setBackground(new Color(192, 192, 192));
		lblCuenta.setFont(new Font("Arial", Font.PLAIN, 25));
		lblCuenta.setOpaque(true);
		lblCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblCuenta = new GridBagConstraints();
		gbc_lblCuenta.insets = new Insets(0, 0, 5, 0);
		gbc_lblCuenta.fill = GridBagConstraints.BOTH;
		gbc_lblCuenta.gridx = 0;
		gbc_lblCuenta.gridy = 0;
		gbc_lblCuenta.gridwidth = 2;
		panelCuenta.add(lblCuenta, gbc_lblCuenta);
		{
			JLabel lblTotalAportados = new JLabel("Total aportados:");
			lblTotalAportados.setHorizontalAlignment(SwingConstants.LEFT);
			lblTotalAportados.setFont(new Font("Arial", Font.PLAIN, 20));
			lblTotalAportados.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
			GridBagConstraints gbc_lblTotalAportados = new GridBagConstraints();
			gbc_lblTotalAportados.anchor = GridBagConstraints.WEST;
			gbc_lblTotalAportados.insets = new Insets(10, 20, 10, 10);
			gbc_lblTotalAportados.gridx = 0;
			gbc_lblTotalAportados.gridy = 2;
			panelCuenta.add(lblTotalAportados, gbc_lblTotalAportados);
		}
		{
			lblTotalAportado = new JLabel("L.");
			lblTotalAportado.setFont(new Font("Arial", Font.PLAIN, 20));
			lblTotalAportado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			GridBagConstraints gbc_lblTotalApartado = new GridBagConstraints();
			gbc_lblTotalApartado.anchor = GridBagConstraints.WEST;
			gbc_lblTotalApartado.insets = new Insets(10, 10, 10, 20);
			gbc_lblTotalApartado.gridx = 1;
			gbc_lblTotalApartado.gridy = 2;
			panelCuenta.add(lblTotalAportado, gbc_lblTotalApartado);
		}
		{
			JLabel lblTotalPrestamos = new JLabel("Total prestamos:");
			lblTotalPrestamos.setHorizontalAlignment(SwingConstants.LEFT);
			lblTotalPrestamos.setFont(new Font("Arial", Font.PLAIN, 20));
			lblTotalPrestamos.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
			GridBagConstraints gbc_lblTotalPrestamos = new GridBagConstraints();
			gbc_lblTotalPrestamos.anchor = GridBagConstraints.WEST;
			gbc_lblTotalPrestamos.insets = new Insets(10, 20, 10, 10);
			gbc_lblTotalPrestamos.gridx = 0;
			gbc_lblTotalPrestamos.gridy = 4;
			panelCuenta.add(lblTotalPrestamos, gbc_lblTotalPrestamos);
		}
		{
			lblTotalPrestamo = new JLabel("L.");
			lblTotalPrestamo.setFont(new Font("Arial", Font.PLAIN, 20));
			lblTotalPrestamo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			GridBagConstraints gbc_lblTotalPrestamo = new GridBagConstraints();
			gbc_lblTotalPrestamo.anchor = GridBagConstraints.WEST;
			gbc_lblTotalPrestamo.insets = new Insets(10, 10, 10, 20);
			gbc_lblTotalPrestamo.gridx = 1;
			gbc_lblTotalPrestamo.gridy = 4;
			panelCuenta.add(lblTotalPrestamo, gbc_lblTotalPrestamo);
		}
		{
			JLabel lblTotalPagos = new JLabel("Total pagos:");
			lblTotalPagos.setHorizontalAlignment(SwingConstants.LEFT);
			lblTotalPagos.setFont(new Font("Arial", Font.PLAIN, 20));
			lblTotalPagos.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
			GridBagConstraints gbc_lblTotalPagos = new GridBagConstraints();
			gbc_lblTotalPagos.anchor = GridBagConstraints.WEST;
			gbc_lblTotalPagos.insets = new Insets(10, 20, 10, 10);
			gbc_lblTotalPagos.gridx = 0;
			gbc_lblTotalPagos.gridy = 6;
			panelCuenta.add(lblTotalPagos, gbc_lblTotalPagos);
		}
		{
			lblTotalPago = new JLabel("L.");
			lblTotalPago.setFont(new Font("Arial", Font.PLAIN, 20));
			lblTotalPago.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			GridBagConstraints gbc_lblTotalPago = new GridBagConstraints();
			gbc_lblTotalPago.anchor = GridBagConstraints.WEST;
			gbc_lblTotalPago.insets = new Insets(10, 10, 10, 20);
			gbc_lblTotalPago.gridx = 1;
			gbc_lblTotalPago.gridy = 6;
			panelCuenta.add(lblTotalPago, gbc_lblTotalPago);
		}
		{
			JLabel lblTotalIntereses = new JLabel("Total Intereses:");
			lblTotalIntereses.setHorizontalAlignment(SwingConstants.LEFT);
			lblTotalIntereses.setFont(new Font("Arial", Font.PLAIN, 20));
			lblTotalIntereses.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
			GridBagConstraints gbc_lblTotalIntereses = new GridBagConstraints();
			gbc_lblTotalIntereses.anchor = GridBagConstraints.WEST;
			gbc_lblTotalIntereses.insets = new Insets(10, 20, 10, 10);
			gbc_lblTotalIntereses.gridx = 0;
			gbc_lblTotalIntereses.gridy = 8;
			panelCuenta.add(lblTotalIntereses, gbc_lblTotalIntereses);
		}
		{
			lblTotalInteres = new JLabel("L.");
			lblTotalInteres.setFont(new Font("Arial", Font.PLAIN, 20));
			lblTotalInteres.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			GridBagConstraints gbc_lblTotalInteres = new GridBagConstraints();
			gbc_lblTotalInteres.anchor = GridBagConstraints.WEST;
			gbc_lblTotalInteres.insets = new Insets(10, 10, 10, 20);
			gbc_lblTotalInteres.gridx = 1;
			gbc_lblTotalInteres.gridy = 8;
			panelCuenta.add(lblTotalInteres, gbc_lblTotalInteres);
		}
		{
			JLabel lblInteresesPagados = new JLabel("Intereses Pagados");
			lblInteresesPagados.setHorizontalAlignment(SwingConstants.LEFT);
			lblInteresesPagados.setFont(new Font("Arial", Font.PLAIN, 20));
			lblInteresesPagados.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
			GridBagConstraints gbc_lblInteresesPagados = new GridBagConstraints();
			gbc_lblInteresesPagados.anchor = GridBagConstraints.WEST;
			gbc_lblInteresesPagados.insets = new Insets(10, 20, 10, 10);
			gbc_lblInteresesPagados.gridx = 0;
			gbc_lblInteresesPagados.gridy = 10;
			panelCuenta.add(lblInteresesPagados, gbc_lblInteresesPagados);
		}
		{
			lblInteresPagado = new JLabel("L.");
			lblInteresPagado.setFont(new Font("Arial", Font.PLAIN, 20));
			lblInteresPagado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			GridBagConstraints gbc_lblInteresPagado = new GridBagConstraints();
			gbc_lblInteresPagado.anchor = GridBagConstraints.WEST;
			gbc_lblInteresPagado.insets = new Insets(10, 10, 10, 20);
			gbc_lblInteresPagado.gridx = 1;
			gbc_lblInteresPagado.gridy = 10;
			panelCuenta.add(lblInteresPagado, gbc_lblInteresPagado);
		}
		{
			JLabel lblSaldoPrestamos = new JLabel("Saldo prestamos:");
			lblSaldoPrestamos.setHorizontalAlignment(SwingConstants.LEFT);
			lblSaldoPrestamos.setFont(new Font("Arial", Font.PLAIN, 20));
			lblSaldoPrestamos.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
			GridBagConstraints gbc_lblSaldoPrestamos = new GridBagConstraints();
			gbc_lblSaldoPrestamos.anchor = GridBagConstraints.WEST;
			gbc_lblSaldoPrestamos.insets = new Insets(10, 20, 10, 10);
			gbc_lblSaldoPrestamos.gridx = 0;
			gbc_lblSaldoPrestamos.gridy = 12;
			panelCuenta.add(lblSaldoPrestamos, gbc_lblSaldoPrestamos);
		}
		{
			lblSaldoPrestamo = new JLabel("L.");
			lblSaldoPrestamo.setFont(new Font("Arial", Font.PLAIN, 20));
			lblSaldoPrestamo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			GridBagConstraints gbc_lblSaldoPrestamo = new GridBagConstraints();
			gbc_lblSaldoPrestamo.anchor = GridBagConstraints.WEST;
			gbc_lblSaldoPrestamo.insets = new Insets(10, 10, 10, 20);
			gbc_lblSaldoPrestamo.gridx = 1;
			gbc_lblSaldoPrestamo.gridy = 12;
			panelCuenta.add(lblSaldoPrestamo, gbc_lblSaldoPrestamo);
		}
		{
			JLabel lblSaldoAFavor = new JLabel("Saldo a favor:");
			lblSaldoAFavor.setHorizontalAlignment(SwingConstants.LEFT);
			lblSaldoAFavor.setFont(new Font("Arial", Font.PLAIN, 20));
			lblSaldoAFavor.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
			GridBagConstraints gbc_lblSaldoAFavor = new GridBagConstraints();
			gbc_lblSaldoAFavor.insets = new Insets(10, 20, 10, 10);
			gbc_lblSaldoAFavor.anchor = GridBagConstraints.WEST;
			gbc_lblSaldoAFavor.gridx = 0;
			gbc_lblSaldoAFavor.gridy = 14;
			panelCuenta.add(lblSaldoAFavor, gbc_lblSaldoAFavor);
		}
		{
			lblSaldoPropio = new JLabel("L.");
			lblSaldoPropio.setFont(new Font("Arial", Font.PLAIN, 20));
			lblSaldoPropio.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			GridBagConstraints gbc_lblSaldoPropio = new GridBagConstraints();
			gbc_lblSaldoPropio.anchor = GridBagConstraints.WEST;
			gbc_lblSaldoPropio.insets = new Insets(10, 10, 10, 20);
			gbc_lblSaldoPropio.gridx = 1;
			gbc_lblSaldoPropio.gridy = 14;
			panelCuenta.add(lblSaldoPropio, gbc_lblSaldoPropio);
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JLabel lblEstadoCuenta = new JLabel("Estado de cuenta");
				lblEstadoCuenta.setIcon(new ImageIcon(EstadoCuenta.class.getResource("/img/estado_de_cuenta_icono.png")));
				lblEstadoCuenta.setFont(new Font("Arial", Font.PLAIN, 15));
				menuBar.add(lblEstadoCuenta);
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
				btnVolverMenu.setIcon(new ImageIcon(EstadoCuenta.class.getResource("/img/volver_icono2.png")));
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
}
