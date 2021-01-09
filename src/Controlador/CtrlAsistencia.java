package Controlador;

import Modelo.Alumno;
import Modelo.AlumnoDAO;
import Modelo.Asistencia;
import Modelo.AsistenciaDAO;
import Modelo.Curso;
import Modelo.CursoDAO;
import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Modelo.LimitadorCaracteres;
import Modelo.Render;
import Vista.frmAsistencia;
import Vista.frmMenuDocente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    frmAsistencia vista = new frmAsistencia();
    DefaultTableModel modelo = new DefaultTableModel();

    public CtrlAsistencia() {
    }

    public CtrlAsistencia(frmAsistencia v) {
        this.vista = v;
        //Instaciamos la tabla con render
        vista.tabla.setDefaultRenderer(Object.class, new Render());
        //Listar los asistencias en la tabla
        listar(vista.tabla);
        cargarCursos(vista.cbxCurso);
        //Instanciamos los botones 
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.cbxCurso.addItemListener(new ItemListener() { 
        public void itemStateChanged(ItemEvent arg0) { 
            System.out.println(arg0);
           } 
          }); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnLimpiar) {
            limpiarDatos(vista);
        }
//        if (e.getSource() == vista.btnRegistrar) {
//            if (validacion(vista)) {
//                if (vista.btnRegistrar.getText().equals("Registrar")) {
//                    registrarUsuario(vista);
//                } else {
//                    accionModificar(vista);
//                }
//                limpiarDatos(vista);
//                listar(vista.tabla);
//            }
//
//        }
        if (e.getSource() == vista.btnCancelar) {
            frmMenuDocente fmd = new frmMenuDocente();
            CtrlMenuDocente cmd = new CtrlMenuDocente(fmd);
            fmd.setVisible(true);
            fmd.setLocationRelativeTo(null);
            limpiarDatos(vista);
            vista.setVisible(false);
        }
    }

    public void limpiarDatos(frmAsistencia vista) {
        vista.txtDocente.setText("");
        vista.cbxCurso.setSelectedIndex(0);
        vista.txtDocente.setBackground(Color.white);
        vista.cbxCurso.setBackground(Color.white);
        vista.btnRegistrar.setEnabled(true);
        vista.btnLimpiar.setEnabled(true);
        vista.btnCancelar.setEnabled(true);
        vista.btnRegistrar.setText("Registrar");
    }

    public void listar(JTable tabla) {
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setName("m");
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setName("e");
        
        JCheckBox ch = new JCheckBox();
        ch.setName("a");
        ch.setSelected(true);

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        //cu.setId(((Curso)vista.cbxCurso.getSelectedItem()).getId());
        cu.setId(1);
        List<Alumno> listaAlumnos = adao.listarPorCurso(cu.getId());
        
        List<Asistencia> lista = dao.listar();
        Object[] object = new Object[8];
        
        for (Alumno alumno : listaAlumnos) {
            //Curso cu = new Curso();
            //cu.setId(asistencia.getCursoId());
            //cdao.buscar(cu);
            object[0] = alumno.getNombre();
            object[1] = alumno.getApellido();
            object[2] = 0;
            object[3] = ch;
            object[4] = btnModificar;
            object[5] = btnEliminar;
            modelo.addRow(object);
        }
        vista.tabla.setModel(modelo);
        vista.tabla.setRowHeight(30);
    }

    /*public void registrarUsuario(frmAsistencia vista) {
        Usuario u = new Usuario();
        u.setUser(vista.txtUsuario.getText().trim());
        u.setPassword(new String(vista.jPassword.getPassword()).trim());
        u.setTipoId(1);
        if (!usuarioExiste(u)) {
            int idU = udao.registrar(u);
            if (idU > 0) {
                registrarAsistencia(vista, idU);
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No se puede registrar, nombre de usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }*/

    /*public void registrarAsistencia(frmAsistencia vista, int idUsuario) {
        Asistencia a = new Asistencia();
        a.setNombre(vista.txtNombre.getText().trim());
        a.setApellido(vista.txtApellido.getText().trim());
        a.setDireccion(vista.txtDireccion.getText().trim());
        a.setTelefono(vista.txtTelefono.getText().trim());
        a.setCursoId(((Curso)vista.cbxCurso.getSelectedItem()).getId());
        a.setUsuarioId(idUsuario);
        
        if (dao.registrar(a) > 0) {
            JOptionPane.showMessageDialog(vista, "El asistencia se ha registrado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar asistencia", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/

    public boolean validacion(frmAsistencia vista) {
        String msj = "No deje vacios el/los campo/s:";
        boolean vacios = false;
        if (vista.cbxCurso.getSelectedIndex()==0) {
            msj += " Curso";
            vacios = true;
            vista.cbxCurso.setBackground(Color.PINK);
        } else {
            vista.cbxCurso.setBackground(Color.white);
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
        int indexCurso=0;
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

            listar(vista.tabla);
            limpiarDatos(vista);

        } else {
            JOptionPane.showMessageDialog(vista, "El asistencia NO se ha eliminado", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void cargarCursos(JComboBox cbx){
        cbx.removeAllItems();
        cbx.addItem("Seleccione");
        //Cargo los datos
        List<Curso> lista = cdao.listar();
        for (Curso curso : lista) {
            cbx.addItem(curso);
        }
    }
}
