package Controlador;

import Modelo.Curso;
import Modelo.CursoDAO;
import Modelo.Docente;
import Modelo.DocenteDAO;
import Modelo.Render;
import Vista.frmCurso;
import Vista.frmMenuDocente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CtrlCurso implements ActionListener {

    CursoDAO dao = new CursoDAO();
    Curso c = new Curso();
    DocenteDAO ddao = new DocenteDAO();
    Docente d = new Docente();
    frmCurso vista = new frmCurso();
    DefaultTableModel modelo = new DefaultTableModel();

    public CtrlCurso() {
    }

    public CtrlCurso(frmCurso v) {
        this.vista = v;
        //Instaciamos la tabla con render
        vista.tabla.setDefaultRenderer(Object.class, new Render());
        //Listar los docentes en la tabla
        listar(vista.tabla);
        //Instanciamos los botones 
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        //Cargar combobox
        cargarCombos(this.vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnLimpiar) {
            limpiarDatos(vista);
        }
        if (e.getSource() == vista.btnRegistrar) {
            if (validaciones(vista)) {
                if (vista.btnRegistrar.getText().equals("Registrar")) {
                    registrarCurso(vista);
                } else {
                    accionModificar(vista);
                }
            }
            limpiarDatos(vista);
            listar(vista.tabla);
        }
        if (e.getSource() == vista.btnCancelar) {
            frmMenuDocente fmd = new frmMenuDocente();
            CtrlMenuDocente cmd = new CtrlMenuDocente(fmd);
            fmd.setVisible(true);
            fmd.setLocationRelativeTo(null);
            limpiarDatos(vista);
            vista.setVisible(false);
        }
    }

    public void limpiarDatos(frmCurso vista) {
        vista.cbxNivel.setSelectedIndex(0);
        vista.cbxParalelo.setSelectedIndex(0);
        vista.cbxDocente.setSelectedIndex(0);
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

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        List<Curso> lista = dao.listar();
        Object[] object = new Object[7];
        for (Curso curso : lista) {
            Docente d = new Docente();
            d.setId(curso.getDocenteId());
            ddao.buscar(d);
            object[0] = curso.getNivel();
            object[1] = curso.getParalelo();
            object[2] = d;
            object[3] = btnModificar;
            object[4] = btnEliminar;
            modelo.addRow(object);
        }
        vista.tabla.setModel(modelo);
        vista.tabla.setRowHeight(30);
    }

    public void cargarCombos(frmCurso vista) {
        //Carga niveles
        vista.cbxNivel.removeAllItems();
        vista.cbxNivel.addItem("-S-");
        vista.cbxNivel.addItem("1");
        vista.cbxNivel.addItem("2");
        vista.cbxNivel.addItem("3");
        vista.cbxNivel.addItem("4");
        vista.cbxNivel.addItem("5");
        vista.cbxNivel.addItem("6");
        //Cargar Paralelos
        vista.cbxParalelo.removeAllItems();
        vista.cbxParalelo.addItem("-S-");
        vista.cbxParalelo.addItem("A");
        vista.cbxParalelo.addItem("B");
        vista.cbxParalelo.addItem("C");
        vista.cbxParalelo.addItem("D");
        vista.cbxParalelo.addItem("E");
        vista.cbxParalelo.addItem("F");
        //Carga Docentes
        vista.cbxDocente.removeAllItems();
        vista.cbxDocente.addItem("Seleccione una opción");
        //Cargo los datos
        JComboBox cbx = vista.cbxDocente;
        List<Docente> lista = ddao.listar();
        for (Docente docente : lista) {
            cbx.addItem(docente);
        }

    }

    public void registrarCurso(frmCurso vista) {
        Curso c = new Curso();
        c.setNivel(vista.cbxNivel.getSelectedIndex());
        c.setParalelo(vista.cbxParalelo.getSelectedItem().toString().charAt(0));
        c.setDocenteId(((Docente) vista.cbxDocente.getSelectedItem()).getId());
        if (!dao.buscarCurso(c)) {
            int id = dao.registrar(c);
            if (id > 0) {
                JOptionPane.showMessageDialog(vista, "El curso se ha registrado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un error al registrar curso", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No se puede registrar, curso ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void modificar(frmCurso vista, int row) {
        //Obtengo la lista misma que estaría en la tabla
        List<Curso> lista = dao.listar();
        //Obtengo el item que se encuentra en la misma ubicación de la fila
        c = lista.get(row);
        //Enviar el curso a formulario
        vista.cu = c;

        Docente d = new Docente();
        d.setId(c.getDocenteId());
        ddao.buscar(d);
        int indexDocente=0;
        List<Docente> listaD = ddao.listar();
        for (int i = 0; i < listaD.size(); i++) {
            Docente get = listaD.get(i);
            if(get.toString().equals(d.toString())){
                indexDocente=i+1;
            }
        }
        
        //Busco paralelo por su caracter, internamente busca por ascil
        int indexParalelo = c.getParalelo()-'A'+1;
        
        ddao.buscar(d);
        vista.cbxNivel.setSelectedIndex(c.getNivel());
        vista.cbxParalelo.setSelectedIndex(indexParalelo);
        vista.cbxDocente.setSelectedIndex(indexDocente);

        vista.btnRegistrar.setText("Actualizar");
    }

    public void accionModificar(frmCurso vista) {
        c.setId(vista.cu.getId());
        c.setNivel(vista.cbxNivel.getSelectedIndex());
        c.setParalelo(vista.cbxParalelo.getSelectedItem().toString().charAt(0));
        c.setDocenteId(((Docente) vista.cbxDocente.getSelectedItem()).getId());

        if ((vista.cu.getNivel() == c.getNivel() && vista.cu.getParalelo() == c.getParalelo()) || !dao.buscarCurso(c)) {
            if (dao.modificar(c)) {
                JOptionPane.showMessageDialog(vista, "El curso se ha actualizado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al actualizar curso", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(vista, "No se puede registrar, curso ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void eliminar(frmCurso vista, int row) {
        int opcion = JOptionPane.showConfirmDialog(vista, "¿Esta seguro/a que desea eliminar?", "Mensaje de confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            //Obtengo la lista misma que estaría en la tabla
            List<Curso> lista = dao.listar();
            //Obtengo el item que se encuentra en la misma ubicación de la fila
            Curso c = new Curso();
            c = lista.get(row);
            if (dao.eliminar(c)) {
                JOptionPane.showMessageDialog(vista, "El curso se ha eliminado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al eliminar curso", "Error", JOptionPane.ERROR_MESSAGE);
            }

            listar(vista.tabla);
            limpiarDatos(vista);

        } else {
            JOptionPane.showMessageDialog(vista, "El docente NO se ha eliminado", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public boolean validaciones(frmCurso vista) {
        boolean valida = true;
        String msj = "Seleccione la/s opcione/s de: ";
        if (vista.cbxNivel.getSelectedIndex() == 0) {
            msj += "Nivel ";
            valida = false;
        }
        if (vista.cbxParalelo.getSelectedIndex() == 0) {
            msj += "Paralelo ";
            valida = false;
        }
        if (vista.cbxDocente.getSelectedIndex() == 0) {
            msj += "Docente ";
            valida = false;
        }
        if (!valida) {
            JOptionPane.showMessageDialog(vista, msj, "Advertencia", JOptionPane.ERROR_MESSAGE);
        }
        return valida;
    }
}
