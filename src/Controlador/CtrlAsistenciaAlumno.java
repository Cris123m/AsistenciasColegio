package Controlador;

import Modelo.Alumno;
import Modelo.AlumnoDAO;
import Modelo.Asistencia;
import Modelo.AsistenciaDAO;
import Modelo.Curso;
import Modelo.CursoDAO;
import Modelo.Docente;
import Modelo.DocenteDAO;
import Modelo.EstadoAsistencia;
import Modelo.EstadoAsistenciaDAO;
import Modelo.Render;
import Vista.frmAsistenciaAlumno;
import Vista.frmMenuDocente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CtrlAsistenciaAlumno implements ActionListener {

    AsistenciaDAO dao = new AsistenciaDAO();
    Asistencia a = new Asistencia();
    CursoDAO cdao = new CursoDAO();
    Curso cu = new Curso();
    AlumnoDAO adao = new AlumnoDAO();
    Alumno al = new Alumno();
    DocenteDAO ddao = new DocenteDAO();
    Docente doc = new Docente();
    frmAsistenciaAlumno vista = new frmAsistenciaAlumno();
    DefaultTableModel modelo = new DefaultTableModel();

    public CtrlAsistenciaAlumno() {
    }

    public CtrlAsistenciaAlumno(frmAsistenciaAlumno v) {
        this.vista = v;
        //Instaciamos la tabla con render
        vista.tabla.setDefaultRenderer(Object.class, new Render());

        //Fecha actual
        Calendar c2 = new GregorianCalendar();
        this.vista.dchFecha.setCalendar(c2);
        //Btngroup default
        this.vista.rbtnTodos.setSelected(true);
        this.vista.rbtnFecha.setSelected(false);
        //cargarCursos(vista.cbxCurso);
        //Instanciamos los botones 
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        cargarAlumnos(this.vista.cbxAlumno);
        vista.dchFecha.setEnabled(false);
        this.vista.cbxAlumno.addItemListener(new ItemListener() {
            //Escucha del combobox al cambiar
            public void itemStateChanged(ItemEvent arg0) {
                if (arg0.getStateChange() == 1) {
                    if (validacion(vista)) {
                        Alumno al = (Alumno) arg0.getItem();
                        //Listar los asistencias en la tabla
                        listar(vista.tabla);
                    }
                }
            }
        });
        //Escuchar eventos de Fecha
        this.vista.dchFecha.getDateEditor().addPropertyChangeListener(
                new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if ("date".equals(e.getPropertyName())) {
                    listar(vista.tabla);
                }
            }
        }
        );
        this.vista.rbtnTodos.addActionListener(this);
        this.vista.rbtnFecha.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnLimpiar) {
            limpiarDatos(vista);
        }
        if (e.getSource() == vista.btnCancelar) {
            frmMenuDocente fmd = new frmMenuDocente();
            CtrlMenuDocente cmd = new CtrlMenuDocente(fmd);
            fmd.setVisible(true);
            fmd.setLocationRelativeTo(null);
            //limpiarDatos(vista);
            vista.setVisible(false);
        }
        if (e.getSource() == vista.rbtnTodos) {
            if (validacion(vista)) {
                vista.dchFecha.setEnabled(false);
                listar(vista.tabla);
            }
        }
        if (e.getSource() == vista.rbtnFecha) {
            if (validacion(vista)) {
                vista.dchFecha.setEnabled(true);
                listar(vista.tabla);
            }
        }
    }

    public void limpiarDatos(frmAsistenciaAlumno vista) {
        vista.cbxAlumno.setSelectedIndex(0);
        Calendar c2 = new GregorianCalendar();
        this.vista.dchFecha.setCalendar(c2);
        this.vista.rbtnTodos.setSelected(true);
        this.vista.rbtnFecha.setSelected(false);
        this.vista.dchFecha.setEnabled(false);
        modelo.setRowCount(0);

        vista.btnCancelar.setEnabled(true);
    }

    public void listar(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        //Dy formato para las fechas
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Obtengo la fecha desde Fecha
        String sqlDate = sdf.format(vista.dchFecha.getDate());
        //Recojo alumno de combobox
        Alumno a = (Alumno) vista.cbxAlumno.getSelectedItem();
        if (vista.rbtnTodos.isSelected()) {
            //Obtengo la lista de asistencias
            List<Asistencia> listaAsistencia = dao.listarPorAlumno(a.getId());

            Object[] object = new Object[4];
            for (Asistencia asistencia : listaAsistencia) {
                //Traigo datos de alumno
                Alumno al = new Alumno();
                al.setId(asistencia.getAlumnoId());
                adao.buscar(al);
                //TRaigo datos del estado de asistencia
                EstadoAsistenciaDAO ead = new EstadoAsistenciaDAO();
                EstadoAsistencia ea = ead.buscar(asistencia.getEstadoId());
                object[0] = asistencia.getFecha();
                object[1] = al.getNombre();
                object[2] = al.getApellido();
                object[3] = ea;
                modelo.addRow(object);
            }
        }else{
            Object[] object = new Object[4];
            //Traigo datos de alumno
                Alumno al = (Alumno) vista.cbxAlumno.getSelectedItem();
                Asistencia asistencia = new Asistencia();
                asistencia.setAlumnoId(al.getId());
                if(dao.asistenciaAlFecha(asistencia, sqlDate)){
                //TRaigo datos del estado de asistencia
                EstadoAsistenciaDAO ead = new EstadoAsistenciaDAO();
                EstadoAsistencia ea = ead.buscar(asistencia.getEstadoId());
                object[0] = asistencia.getFecha();
                object[1] = al.getNombre();
                object[2] = al.getApellido();
                object[3] = ea;
                modelo.addRow(object);
                }
        }
        vista.tabla.setModel(modelo);
        vista.tabla.setRowHeight(30);
    }

    public boolean validacion(frmAsistenciaAlumno vista) {
        String msj = "No deje vacios el/los campo/s:";
        boolean vacios = false;
        if (vista.cbxAlumno.getSelectedIndex() == 0) {
            msj += " Alumno";
            vacios = true;
            vista.cbxAlumno.setBackground(Color.PINK);
        } else {
            vista.cbxAlumno.setBackground(Color.white);
        }
        if (vista.dchFecha.toString().equals("")) {
            msj += " Fecha";
            vacios = true;
            vista.dchFecha.setBackground(Color.PINK);
        } else {
            vista.dchFecha.setBackground(Color.white);
        }
        if (vacios) {
            JOptionPane.showMessageDialog(vista, msj, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return !vacios;
    }

    public void cargarAlumnos(JComboBox cbx) {
        cbx.removeAllItems();
        cbx.addItem("Seleccione");
        //Cargo los datos
        List<Alumno> lista = adao.listar();
        for (Alumno alumno : lista) {
            cbx.addItem(alumno);
        }
    }
}
