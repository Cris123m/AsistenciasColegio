package Controlador;

import Modelo.Alumno;
import Modelo.AlumnoDAO;
import Modelo.Asistencia;
import Modelo.AsistenciaDAO;
import Modelo.Curso;
import Modelo.CursoDAO;
import Modelo.Docente;
import Modelo.DocenteDAO;
import Modelo.Render;
import Vista.frmAsistencia;
import Vista.frmMenuDocente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CtrlAsistencia implements ActionListener {

    AsistenciaDAO dao = new AsistenciaDAO();
    Asistencia a = new Asistencia();
    CursoDAO cdao = new CursoDAO();
    Curso cu = new Curso();
    AlumnoDAO adao = new AlumnoDAO();
    Alumno al = new Alumno();
    DocenteDAO ddao = new DocenteDAO();
    Docente doc = new Docente();
    frmAsistencia vista = new frmAsistencia();
    DefaultTableModel modelo = new DefaultTableModel();

    public CtrlAsistencia() {
    }

    public CtrlAsistencia(frmAsistencia v) {
        this.vista = v;
        //Instaciamos la tabla con render
        vista.tabla.setDefaultRenderer(Object.class, new Render());

        //Fecha actual
        Calendar c2 = new GregorianCalendar();
        this.vista.dchFecha.setCalendar(c2);

        cargarCursos(vista.cbxCurso);
        //Instanciamos los botones 
        //this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.cbxCurso.addItemListener(new ItemListener() {
            //Escucha del combobox al cambiar
            public void itemStateChanged(ItemEvent arg0) {
                if (arg0.getStateChange() == 1) {
                    if (validacion(vista)) {
                        Curso cu = (Curso) arg0.getItem();
                        Docente d = new Docente();
                        d.setId(cu.getDocenteId());
                        ddao.buscar(d);
                        vista.txtDocente.setText(d.toString());
                        //Listar los asistencias en la tabla
                        listar(vista.tabla, cu.getId());
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
                    /*System.out.println(e.getPropertyName()
                            + ": " + (Date) e.getNewValue());*/
                    //Listar los asistencias en la tabla
                        listar(vista.tabla, cu.getId());
                }
            }
        }
        );
        //Escucha eventos de tabla
        this.vista.tabla.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = vista.tabla.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / vista.tabla.getRowHeight();

                if (row < vista.tabla.getRowCount() && row >= 0 && column < vista.tabla.getColumnCount() && column >= 0) {
                    Object value = vista.tabla.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                        JButton boton = (JButton) value;

                        if (boton.getName().equals("m")) {
                            System.out.println("Boton modificar" + row);
                        }
                        if (boton.getName().equals("e")) {
                            System.out.println("Boton eliminar" + row);
                        }
                        if (boton.getName().equals("a")) {
                            System.out.println("click check");
                        }
                    }
                    if (value instanceof JCheckBox) {
                        if (validacion(vista)) {
                            registrarAsistencia(row,((JCheckBox) value).getName());
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
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
    }

    public void limpiarDatos(frmAsistencia vista) {
        vista.txtDocente.setText("");
        vista.cbxCurso.setSelectedIndex(0);
        vista.txtDocente.setBackground(Color.white);
        vista.cbxCurso.setBackground(Color.white);
        //vista.btnRegistrar.setEnabled(true);
        vista.btnLimpiar.setEnabled(true);
        vista.btnCancelar.setEnabled(true);
        //vista.btnRegistrar.setText("Registrar");
    }

    public void listar(JTable tabla, int idCurso) {
        //Dy formato para las fechas
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         //Obtengo la fecha desde Fecha
         String sqlDate = sdf.format(vista.dchFecha.getDate());
         //Guardo la idCurso dentro de el objeto curso
         cu.setId(idCurso);
         //Obtengo el listado de alumnos que pertenezcan al curso
         List<Alumno> listaAlumnos = adao.listarPorCurso(cu.getId());
         //Obtengo la lista de asistencias
        //List<Asistencia> lista = dao.listar();
        //Reviso si ya existen asistencias para esta fecha y curso
        if(dao.cantAsistenciasFecha(sqlDate,idCurso)==0){
            //Primero bloqueo la tabla antes de confirmar
            vista.tabla.setEnabled(false);
            //Envio mensaje de confirmación para crear asistencias en esta fecha y curso
            int opcion = JOptionPane.showConfirmDialog(vista, "No se ha registrado asistencias en esta fecha, ¿Desea crear nueva asistencia?", "Mensaje de confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            //Si seleccionó que si, registra nuevas asistencias con valor predeterminado de Falta a todos los alumnos, hasta que se seleccione el check
            if (opcion == JOptionPane.YES_OPTION) {
                //Desbloqueo la tabla
                vista.tabla.setEnabled(true);
                //Variable  bandera para verificar que se hayan registrado todos los alumnos
                boolean registrados = true;
                //Hago una iteración de los alumnos
                for (Alumno alumno : listaAlumnos) {
                    //Creo un objeto asistencia con los parámetros necesarios
                    Asistencia a = new Asistencia(0,vista.dchFecha.getDate(),alumno.getId(),2);    
                    //Registro la asistencia de cada uno de los alumnos
                    if(dao.registrar(a)==0){
                        //En caso de que uno no se registre le mando el valor de falso a la variable bandera
                        registrados=false;
                    }
                }
                //Si uno de los alumnos no se registro, envia el msj
                if(!registrados){
                    JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar nuevo listado de asistencias", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }else{
            vista.tabla.setEnabled(true);
        }
//        JButton btnModificar = new JButton("Modificar");
//        btnModificar.setName("m");
//        JButton btnEliminar = new JButton("Eliminar");
//        btnEliminar.setName("e");

//        JCheckBox ch = new JCheckBox();
//        ch.setName("a");
//        ch.setSelected(false);
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        Object[] object = new Object[5];

        for (Alumno alumno : listaAlumnos) {
            int cantAsistencias = dao.asistenciaAlumno(alumno.getId());
           
            Asistencia a = new Asistencia();
            a.setAlumnoId(alumno.getId());
            boolean asis = false;
            boolean fuga = false;
            if(dao.asistenciaAlFecha(a, sqlDate)){
                if(a.getEstadoId()==1){
                    asis = true;
                }else if(a.getEstadoId()==3){
                    fuga = true;
                }
            }
            //Genero checbox para asistencias
            JCheckBox ch = new JCheckBox();
            //Genero checkbox para fugas
            JCheckBox chf = new JCheckBox();
            ch.setName("a");//Identifico el checkbox para asistencias
            ch.setSelected(asis);//Le envio el valor del chek dependiendo de su estado
            chf.setName("f");//Identifico el checkbox para fugas
            chf.setSelected(fuga);//Le envio el valor del chek dependiendo de su estado
            object[0] = alumno.getNombre();
            object[1] = alumno.getApellido();
            object[2] = cantAsistencias;
            object[3] = ch;
            object[4] = chf;
            modelo.addRow(object);
        }
        vista.tabla.setModel(modelo);
        vista.tabla.setRowHeight(30);
    }

    public void registrarAsistencia(int row,String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sqlDate = sdf.format(vista.dchFecha.getDate());
        int idCurso = ((Curso) vista.cbxCurso.getSelectedItem()).getId();
        cu.setId(idCurso);
        List<Alumno> listaAlumnos = adao.listarPorCurso(cu.getId());
        for (int i = 0; i < listaAlumnos.size(); i++) {
            if (i == row) {
                Alumno al = listaAlumnos.get(i);
                Asistencia a = new Asistencia();
                a.setAlumnoId(al.getId());
                if (dao.asistenciaAlFecha(a, sqlDate)) {
                    if(name.equals("a")){
                        if(a.getEstadoId()==1){
                            a.setEstadoId(2);
                        }else{
                            a.setEstadoId(1);
                        }                        
                        if(dao.modificar(a)){
                            JOptionPane.showMessageDialog(vista, "Se ha registrado la asistencia", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar asistencia", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }else if(name.equals("f")){
                        if(a.getEstadoId()==3){
                            a.setEstadoId(2);
                        }else{
                            a.setEstadoId(3);
                        }                        
                        if(dao.modificar(a)){
                            JOptionPane.showMessageDialog(vista, "Se ha registrado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    a.setFecha(vista.dchFecha.getDate());

                    int idA = dao.registrar(a);
                    if (idA > 0) {
                        JOptionPane.showMessageDialog(vista, "El asistencia registrada", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar  asistencia", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            listar(vista.tabla, idCurso);

        }
    }

    public boolean validacion(frmAsistencia vista) {
        String msj = "No deje vacios el/los campo/s:";
        boolean vacios = false;
        if (vista.cbxCurso.getSelectedIndex() == 0) {
            msj += " Curso";
            vacios = true;
            vista.cbxCurso.setBackground(Color.PINK);
        } else {
            vista.cbxCurso.setBackground(Color.white);
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

    public void modificar(frmAsistencia vista, int row) {
        //Obtengo la lista misma que estaría en la tabla
        List<Asistencia> lista = dao.listar();
        //Obtengo el item que se encuentra en la misma ubicación de la fila
        a = lista.get(row);
        //envio la id en una variable pública en el formulario para mantener la id a ser modificado
        vista.idD = a.getId();
        //Creo un nuevo objeto alumno y guardo la id
        Alumno alum = new Alumno();
        alum.setId(a.getAlumnoId());
        //Busco el alumno con aquella id
        adao.buscar(alum);
        //Busco el curso
        List<Curso> listaCurso = cdao.listar();
        int indexCurso = 0;
        for (int i = 0; i < listaCurso.size(); i++) {
            Curso cur = listaCurso.get(i);
//            if(cur.getId()==a.getCursoId()){
//                indexCurso=i+1;
//            }
        }

//        vista.cbxCurso.setSelectedIndex(indexCurso);
//
//        u = udao.buscarId(a.getUsuarioId());
//        vista.u = u;
//        vista.txtUsuario.setText(u.getUser());
//        vista.jPassword.setText(u.getPassword());
//
//        vista.btnRegistrar.setText("Actualizar");
    }

    public void accionModificar(frmAsistencia vista) {
//        a.setNombre(vista.txtNombre.getText().trim());
//        a.setApellido(vista.txtApellido.getText().trim());
//        a.setDireccion(vista.txtDireccion.getText().trim());
//        a.setTelefono(vista.txtTelefono.getText().trim());
//        a.setCursoId(((Curso)vista.cbxCurso.getSelectedItem()).getId());
//        a.setId(vista.idD);
//        a.setUsuarioId(vista.u.getId());
//        u = vista.u;
//        String usuarioAnt = u.getUser();
//        u.setUser(vista.txtUsuario.getText().trim());
//        u.setPassword(new String(vista.jPassword.getPassword()).trim());
//
//        if (usuarioAnt.equals(u.getUser()) || !usuarioExiste(u)) {
//            if (dao.modificar(a)) {
//                JOptionPane.showMessageDialog(vista, "El asistencia se ha actualizado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al actualizar  asistencia", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//            if (udao.modificar(u)) {
//                JOptionPane.showMessageDialog(vista, "El usuario se ha actualizado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al actualizar  usuario", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } else {
//            JOptionPane.showMessageDialog(vista, "No se puede registrar, nombre de usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
//        }

    }

    public void eliminar(frmAsistencia vista, int row) {
        int opcion = JOptionPane.showConfirmDialog(vista, "¿Esta seguro/a que desea eliminar?", "Mensaje de confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            //Obtengo la lista misma que estaría en la tabla
            List<Asistencia> lista = dao.listar();
            //Obtengo el item que se encuentra en la misma ubicación de la fila
            Asistencia a = new Asistencia();
            a = lista.get(row);
            if (dao.eliminar(a)) {
                JOptionPane.showMessageDialog(vista, "El asistencia se ha eliminado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al eliminar asistencia", "Error", JOptionPane.ERROR_MESSAGE);
            }

            Curso cu = (Curso) vista.cbxCurso.getSelectedItem();
            listar(vista.tabla, cu.getId());
            //limpiarDatos(vista);

        } else {
            JOptionPane.showMessageDialog(vista, "El asistencia NO se ha eliminado", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void cargarCursos(JComboBox cbx) {
        cbx.removeAllItems();
        cbx.addItem("Seleccione");
        //Cargo los datos
        List<Curso> lista = cdao.listar();
        for (Curso curso : lista) {
            cbx.addItem(curso);
        }
    }
}
