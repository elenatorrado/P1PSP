package es.studium.Practica1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.JLabel;

public class PantallaFicheros extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtText;
    private JButton btnNewButton;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JLabel lblNewLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PantallaFicheros frame = new PantallaFicheros();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PantallaFicheros() {
        setTitle("Mis Ficheros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 310);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Campo de texto para que el usuario introduzca la extensión de archivo a buscar
        txtText = new JTextField();
        txtText.setHorizontalAlignment(SwingConstants.LEFT);
        txtText.setBounds(93, 209, 256, 20);
        contentPane.add(txtText);
        txtText.setColumns(10);

        // Botón Aceptar
        btnNewButton = new JButton("Buscar");
        btnNewButton.setBounds(170, 237, 89, 23);
        btnNewButton.addActionListener(this); // Añadimos el ActionListener al botón
        contentPane.add(btnNewButton);
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 414, 187);
        contentPane.add(scrollPane);
        
        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        
        lblNewLabel = new JLabel("Extension ");
        lblNewLabel.setBounds(22, 212, 61, 14);
        contentPane.add(lblNewLabel);
        
        // Añadir MouseListener para detectar doble clic en la JTextArea
        textArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Detecta el doble clic
                    try {
                        // Obtener la posición del clic en el JTextArea
                        int offset = textArea.viewToModel(e.getPoint());
                        int line = textArea.getLineOfOffset(offset);
                        
                        // Obtener el contenido de la línea donde se hizo clic
                        String archivoSeleccionado = textArea.getText(
                                textArea.getLineStartOffset(line), 
                                textArea.getLineEndOffset(line) - textArea.getLineStartOffset(line)).trim();

                        // Verificar si el archivo es un .exe
                        if (archivoSeleccionado.endsWith(".exe")) {
                            // Llamar a Funcionalidad para ejecutar el archivo
                            System.out.println("Ejecutando: " + archivoSeleccionado);
                            Funcionalidad.ejecutarArchivo(archivoSeleccionado);
                        } else {
                            System.out.println("No es un archivo .exe");
                        }
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Cuando el botón "Buscar" es presionado
        if (e.getSource() == btnNewButton) {
            // Obtenemos la extensión del archivo que el usuario ingresó
            String extension = txtText.getText();

            // Ruta del directorio que quieres buscar (puedes cambiarla)
            String directorio = "C://";  // ¡Reemplaza esto con la ruta real!

            // Llamamos al método funcionalidad de la clase Funcionalidad
            String resultado = Funcionalidad.buscarArchivos(extension, directorio);

            // Mostramos las rutas en el JTextArea
            textArea.setText(resultado);
        }
    }
}
