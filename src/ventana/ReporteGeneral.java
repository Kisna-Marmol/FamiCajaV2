package ventana;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

import dao.AporteDAO;
import dao.InteresDAO;
import dao.MiembroDAO;
import dao.PagoInteresDAO;
import dao.PagoPrestamoDAO;
import dao.PrestamoDAO;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReporteGeneral extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblTotalMiembros;
	private JLabel lblTotalAportado;
	private JLabel lblTotalPrestamo;
	private JLabel lblPagos;
	private JLabel lblIntereses;
	private JLabel lblInteresPagados;
	private JLabel lblSaldoPrestamo;
	private JLabel lblFondosDisponible;
	private Principal principal;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			ReporteGeneral dialog = new ReporteGeneral();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public ReporteGeneral(Principal principal) {
		super(principal,"Reporte General", true);
		this.principal = principal;
		setBounds(100, 100, 600, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			JPanel panelCuenta = new JPanel();
			panelCuenta.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panelCuenta.setBackground(Color.WHITE);
			contentPanel.add(panelCuenta);
			GridBagLayout gbl_panelCuenta = new GridBagLayout();
			gbl_panelCuenta.columnWidths = new int[]{300, 200, 0};
			gbl_panelCuenta.rowHeights = new int[]{35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_panelCuenta.columnWeights = new double[]{0.5, 0.5, Double.MIN_VALUE};
			gbl_panelCuenta.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panelCuenta.setLayout(gbl_panelCuenta);
			{
				JLabel lblFamiCaja = new JLabel("FamiCaja");
				lblFamiCaja.setOpaque(true);
				lblFamiCaja.setHorizontalAlignment(SwingConstants.CENTER);
				lblFamiCaja.setFont(new Font("Arial", Font.PLAIN, 25));
				lblFamiCaja.setBackground(Color.LIGHT_GRAY);
				GridBagConstraints gbc_lblFamiCaja = new GridBagConstraints();
				gbc_lblFamiCaja.fill = GridBagConstraints.BOTH;
				gbc_lblFamiCaja.gridwidth = 2;
				gbc_lblFamiCaja.insets = new Insets(0, 0, 5, 0);
				gbc_lblFamiCaja.gridx = 0;
				gbc_lblFamiCaja.gridy = 0;
				panelCuenta.add(lblFamiCaja, gbc_lblFamiCaja);
			}
			{
				JLabel lblMiembros = new JLabel("Miembros:");
				lblMiembros.setHorizontalAlignment(SwingConstants.LEFT);
				lblMiembros.setFont(new Font("Arial", Font.PLAIN, 20));
				lblMiembros.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblMiembros = new GridBagConstraints();
				gbc_lblMiembros.anchor = GridBagConstraints.WEST;
				gbc_lblMiembros.insets = new Insets(10, 20, 10, 10);
				gbc_lblMiembros.gridx = 0;
				gbc_lblMiembros.gridy = 2;
				panelCuenta.add(lblMiembros, gbc_lblMiembros);
			}
			{
				lblTotalMiembros = new JLabel("0");
				lblTotalMiembros.setFont(new Font("Arial", Font.PLAIN, 20));
				lblTotalMiembros.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblTotalMiembros = new GridBagConstraints();
				gbc_lblTotalMiembros.anchor = GridBagConstraints.WEST;
				gbc_lblTotalMiembros.insets = new Insets(10, 10, 10, 20);
				gbc_lblTotalMiembros.gridx = 1;
				gbc_lblTotalMiembros.gridy = 2;
				panelCuenta.add(lblTotalMiembros, gbc_lblTotalMiembros);
			}
			{
				JLabel lblTotalAportados = new JLabel("Total aportados:");
				lblTotalAportados.setHorizontalAlignment(SwingConstants.LEFT);
				lblTotalAportados.setFont(new Font("Arial", Font.PLAIN, 20));
				lblTotalAportados.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblTotalAportados = new GridBagConstraints();
				gbc_lblTotalAportados.anchor = GridBagConstraints.WEST;
				gbc_lblTotalAportados.insets = new Insets(10, 20, 10, 10);
				gbc_lblTotalAportados.gridx = 0;
				gbc_lblTotalAportados.gridy = 4;
				panelCuenta.add(lblTotalAportados, gbc_lblTotalAportados);
			}
			{
				lblTotalAportado = new JLabel("L.");
				lblTotalAportado.setFont(new Font("Arial", Font.PLAIN, 20));
				lblTotalAportado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblTotalAportado = new GridBagConstraints();
				gbc_lblTotalAportado.anchor = GridBagConstraints.WEST;
				gbc_lblTotalAportado.insets = new Insets(10, 10, 10, 20);
				gbc_lblTotalAportado.gridx = 1;
				gbc_lblTotalAportado.gridy = 4;
				panelCuenta.add(lblTotalAportado, gbc_lblTotalAportado);
			}
			{
				JLabel lblTotalPrestamos = new JLabel("Total prestamos:");
				lblTotalPrestamos.setHorizontalAlignment(SwingConstants.LEFT);
				lblTotalPrestamos.setFont(new Font("Arial", Font.PLAIN, 20));
				lblTotalPrestamos.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblTotalPrestamos = new GridBagConstraints();
				gbc_lblTotalPrestamos.anchor = GridBagConstraints.WEST;
				gbc_lblTotalPrestamos.insets = new Insets(10, 20, 10, 10);
				gbc_lblTotalPrestamos.gridx = 0;
				gbc_lblTotalPrestamos.gridy = 6;
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
				gbc_lblTotalPrestamo.gridy = 6;
				panelCuenta.add(lblTotalPrestamo, gbc_lblTotalPrestamo);
			}
			{
				JLabel lblTotalPago = new JLabel("Total pagos:");
				lblTotalPago.setHorizontalAlignment(SwingConstants.LEFT);
				lblTotalPago.setFont(new Font("Arial", Font.PLAIN, 20));
				lblTotalPago.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblTotalPago = new GridBagConstraints();
				gbc_lblTotalPago.anchor = GridBagConstraints.WEST;
				gbc_lblTotalPago.insets = new Insets(10, 20, 10, 10);
				gbc_lblTotalPago.gridx = 0;
				gbc_lblTotalPago.gridy = 8;
				panelCuenta.add(lblTotalPago, gbc_lblTotalPago);
			}
			{
				lblPagos = new JLabel("L.");
				lblPagos.setFont(new Font("Arial", Font.PLAIN, 20));
				lblPagos.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblPagos = new GridBagConstraints();
				gbc_lblPagos.anchor = GridBagConstraints.WEST;
				gbc_lblPagos.insets = new Insets(10, 10, 10, 20);
				gbc_lblPagos.gridx = 1;
				gbc_lblPagos.gridy = 8;
				panelCuenta.add(lblPagos, gbc_lblPagos);
			}
			{
				JLabel lblTotalIntereses = new JLabel("Total Intereses:");
				lblTotalIntereses.setHorizontalAlignment(SwingConstants.LEFT);
				lblTotalIntereses.setFont(new Font("Arial", Font.PLAIN, 20));
				lblTotalIntereses.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblTotalIntereses = new GridBagConstraints();
				gbc_lblTotalIntereses.anchor = GridBagConstraints.WEST;
				gbc_lblTotalIntereses.insets = new Insets(10, 20, 10, 10);
				gbc_lblTotalIntereses.gridx = 0;
				gbc_lblTotalIntereses.gridy = 10;
				panelCuenta.add(lblTotalIntereses, gbc_lblTotalIntereses);
			}
			{
				lblIntereses = new JLabel("L.");
				lblIntereses.setFont(new Font("Arial", Font.PLAIN, 20));
				lblIntereses.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblIntereses = new GridBagConstraints();
				gbc_lblIntereses.anchor = GridBagConstraints.WEST;
				gbc_lblIntereses.insets = new Insets(10, 10, 10, 20);
				gbc_lblIntereses.gridx = 1;
				gbc_lblIntereses.gridy = 10;
				panelCuenta.add(lblIntereses, gbc_lblIntereses);
			}
			{
				JLabel lblInteresesPagados = new JLabel("Intereses Pagados");
				lblInteresesPagados.setHorizontalAlignment(SwingConstants.LEFT);
				lblInteresesPagados.setFont(new Font("Arial", Font.PLAIN, 20));
				lblInteresesPagados.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblInteresesPagados = new GridBagConstraints();
				gbc_lblInteresesPagados.anchor = GridBagConstraints.WEST;
				gbc_lblInteresesPagados.insets = new Insets(10, 20, 10, 10);
				gbc_lblInteresesPagados.gridx = 0;
				gbc_lblInteresesPagados.gridy = 12;
				panelCuenta.add(lblInteresesPagados, gbc_lblInteresesPagados);
			}
			{
				lblInteresPagados = new JLabel("L.");
				lblInteresPagados.setFont(new Font("Arial", Font.PLAIN, 20));
				lblInteresPagados.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblInteresPagados = new GridBagConstraints();
				gbc_lblInteresPagados.anchor = GridBagConstraints.WEST;
				gbc_lblInteresPagados.insets = new Insets(10, 10, 10, 20);
				gbc_lblInteresPagados.gridx = 1;
				gbc_lblInteresPagados.gridy = 12;
				panelCuenta.add(lblInteresPagados, gbc_lblInteresPagados);
			}
			{
				JLabel lblSaldoPrestamos = new JLabel("Saldo prestamos:");
				lblSaldoPrestamos.setHorizontalAlignment(SwingConstants.LEFT);
				lblSaldoPrestamos.setFont(new Font("Arial", Font.PLAIN, 20));
				lblSaldoPrestamos.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblSaldoPrestamos = new GridBagConstraints();
				gbc_lblSaldoPrestamos.anchor = GridBagConstraints.WEST;
				gbc_lblSaldoPrestamos.insets = new Insets(10, 20, 10, 10);
				gbc_lblSaldoPrestamos.gridx = 0;
				gbc_lblSaldoPrestamos.gridy = 14;
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
				gbc_lblSaldoPrestamo.gridy = 14;
				panelCuenta.add(lblSaldoPrestamo, gbc_lblSaldoPrestamo);
			}
			{
				JLabel lblFondosDisponibles = new JLabel("Fondos disponibles:");
				lblFondosDisponibles.setHorizontalAlignment(SwingConstants.LEFT);
				lblFondosDisponibles.setFont(new Font("Arial", Font.PLAIN, 20));
				lblFondosDisponibles.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblFondosDisponibles = new GridBagConstraints();
				gbc_lblFondosDisponibles.anchor = GridBagConstraints.WEST;
				gbc_lblFondosDisponibles.insets = new Insets(10, 20, 10, 10);
				gbc_lblFondosDisponibles.gridx = 0;
				gbc_lblFondosDisponibles.gridy = 16;
				panelCuenta.add(lblFondosDisponibles, gbc_lblFondosDisponibles);
			}
			{
				lblFondosDisponible = new JLabel("L.");
				lblFondosDisponible.setFont(new Font("Arial", Font.PLAIN, 20));
				lblFondosDisponible.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
				GridBagConstraints gbc_lblFondoDisponible = new GridBagConstraints();
				gbc_lblFondoDisponible.insets = new Insets(10, 10, 10, 20);
				gbc_lblFondoDisponible.anchor = GridBagConstraints.WEST;
				gbc_lblFondoDisponible.gridx = 1;
				gbc_lblFondoDisponible.gridy = 16;
				panelCuenta.add(lblFondosDisponible, gbc_lblFondoDisponible);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JLabel lblReporteGeneral = new JLabel("Reporte General");
				lblReporteGeneral.setIcon(new ImageIcon(ReporteGeneral.class.getResource("/img/estado_de_cuenta_icono.png")));
				lblReporteGeneral.setFont(new Font("Arial", Font.PLAIN, 15));
				menuBar.add(lblReporteGeneral);
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
				btnVolverMenu.setIcon(new ImageIcon(ReporteGeneral.class.getResource("/img/volver_icono2.png")));
				btnVolverMenu.setFont(new Font("Arial", Font.PLAIN, 18));
				botonTransparente(btnVolverMenu);
				menuBar.add(btnVolverMenu);
			}
		}
		
		cargarDatosReporte();
		
		setLocationRelativeTo(null);
	}
	
	public static void botonTransparente(JButton boton) {
		//boton.setBorderPainted(false); //no pinta el bonde
		boton.setContentAreaFilled(false);
		//boton.setFocusPainted(false); //no pinta el borde cuando enfoca
	}

	public void cargarDatosReporte() {
	    try {
	        int totalMiembros = new MiembroDAO().contarMiembros();
	        double totalAportados = new AporteDAO().getTotalAportado();
	        double totalPrestamos = new PrestamoDAO().getTotalPrestamos();
	        double totalPagos = new PagoPrestamoDAO().getTotalPagado();
	        double totalIntereses = new InteresDAO().getTotalInteresGenerado();
	        double interesesPagados = new PagoInteresDAO().getTotalPagado();
	        double saldoPrestamos = totalPrestamos - totalPagos;
	        double fondosDisponibles = totalAportados + interesesPagados - saldoPrestamos;

	        lblTotalMiembros.setText(String.valueOf(totalMiembros));
	        lblTotalAportado.setText("L. " + totalAportados);
	        lblTotalPrestamo.setText("L. " + totalPrestamos);
	        lblPagos.setText("L. " + totalPagos);
	        lblIntereses.setText("L. " + totalIntereses);
	        lblInteresPagados.setText("L. " + interesesPagados);
	        lblSaldoPrestamo.setText("L. " + saldoPrestamos);
	        lblFondosDisponible.setText("L. " + fondosDisponibles);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al cargar los datos del reporte general.");
	    }
	}

}
