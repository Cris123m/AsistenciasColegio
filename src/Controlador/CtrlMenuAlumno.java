package Controlador;

import Vista.frmCurso;
import Vista.frmAlumno;
import Vista.frmMenuAlumno;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class CtrlMenuAlumno implements ActionListener {

    frmMenuAlumno vista = new frmMenuAlumno();

    public CtrlMenuAlumno(frmMenuAlumno v) {
        this.vista = v;
        //Carga imagen de jPanel
        Imagen Imagen = new Imagen();
        this.vista.jPanel1.add(Imagen);
        this.vista.jPanel1.repaint();
        this.vista.jPanel1.setBounds(5, 5, 440, 288);
        
        //Instanciamos botones
        this.vista.btnAsistencias.addActionListener(this);
        this.vista.btnCerrar.addActionListener(this);
        this.vista.btnCursos.addActionListener(this);
        //this.vista.btnAlumnos.addActionListener(this);
        this.vista.btnEstudiantes.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnDocentes){
            frmAlumno fd;
            fd = new frmAlumno();
            CtrlAlumno cd = new CtrlAlumno(fd);
            
            fd.setVisible(true);
            fd.setLocationRelativeTo(null);
            vista.setVisible(false);
        }
        if(e.getSource()==vista.btnEstudiantes){
            //
        }
        if(e.getSource()==vista.btnCursos){
            frmCurso fc;
            fc = new frmCurso();
            CtrlCurso cc = new CtrlCurso(fc);
            
            fc.setVisible(true);
            fc.setLocationRelativeTo(null);
            vista.setVisible(false);
        }
        if(e.getSource()==vista.btnAsistencias){
            //
        }
        if(e.getSource()==vista.btnCerrar){
            //
        }
    }

    public class Imagen extends javax.swing.JPanel {

        public Imagen() {
            this.setSize(440, 288); //se selecciona el tamaño del panel
        }

        //Se crea un método cuyo parámetro debe ser un objeto Graphics
        public void paint(Graphics grafico) {
            Dimension height = getSize();

            //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
            ImageIcon Img = new ImageIcon(getClass().getResource("/img/profesores.jpg"));

            //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
            grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);

            setOpaque(false);
            super.paintComponent(grafico);
        }
    }
}
