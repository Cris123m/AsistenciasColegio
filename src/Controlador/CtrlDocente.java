package Controlador;

import Modelo.Docente;
import Modelo.DocenteDAO;
import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Modelo.LimitadorCaracteres;
import Modelo.Render;
import Vista.frmDocente;
import Vista.frmMenuDocente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CtrlDocente implements ActionListener {

    UsuarioDAO udao = new UsuarioDAO();
    Usuario u = new Usuario();
    DocenteDAO dao = new DocenteDAO();
    Docente d = new Docente();
    frmDocente vista = new frmDocente();
    DefaultTableModel modelo = new DefaultTableModel();

    public CtrlDocente() {
    }

    public CtrlDocente(frmDocente v) {
        this.vista = v;
        //Permitir solo números en teléfono
        vista.txtTelefono.setDocument(new LimitadorCaracteres());
        //Instaciamos la tabla con render
        vista.tabla.setDefaultRenderer(Object.class, new Render());
        //Listar los docentes en la tabla
        listar(vista.tabla);
        //Instanciamos los botones 
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnLimpiar) {
            limpiarDatos(vista);
        }
        if (e.getSource() == vista.btnRegistrar) {
            if (validaEspacios(vista)) {
                if (vista.btnRegistrar.getText().equals("Registrar")) {
                    registrarUsuario(vista);
                } else {
                    accionModificar(vista);
                }
                limpiarDatos(vista);
                listar(vista.tabla);
            }

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

    public void limpiarDatos(frmDocente vista) {
        vista.txtNombre.setText("");
        vista.txtApellido.setText("");
        vista.txtDireccion.setText("");
        vista.txtTelefono.setText("");
        vista.txtUsuario.setText("");
        vista.jPassword.setText("");
        vista.txtNombre.setBackground(Color.white);
        vista.txtApellido.setBackground(Color.white);
        vista.txtDireccion.setBackground(Color.white);
        vista.txtTelefono.setBackground(Color.white);
        vista.txtUsuario.setBackground(Color.white);
        vista.jPassword.setBackground(Color.white);
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
        List<Docente> lista = dao.listar();
        Object[] object = new Object[7];
        for (Docente docente : lista) {
            object[0] = docente.getId();
            object[1] = docente.getNombre();
            object[2] = docente.getApellido();
            object[3] = docente.getDireccion();
            object[4] = docente.getTelefono();
            object[5] = btnModificar;
            object[6] = btnEliminar;
            modelo.addRow(object);
        }
        vista.tabla.setModel(modelo);
        vista.tabla.setRowHeight(30);
    }

    public void registrarUsuario(frmDocente vista) {
        Usuario u = new Usuario();
        u.setUser(vista.txtUsuario.getText().trim());
        u.setPassword(new String(vista.jPassword.getPassword()).trim());
        u.setTipoId(1);
        if (!usuarioExiste(u)) {
            int idU = udao.registrar(u);
            if (idU > 0) {
                registrarDocente(vista, idU);
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No se puede registrar, nombre de usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void registrarDocente(frmDocente vista, int idUsuario) {
        Docente d = new Docente();
        d.setNombre(vista.txtNombre.getText().trim());
        d.setApellido(vista.txtApellido.getText().trim());
        d.setDireccion(vista.txtDireccion.getText().trim());
        d.setTelefono(vista.txtTelefono.getText().trim());
        d.setUsuarioId(idUsuario);
        if (dao.registrar(d) > 0) {
            JOptionPane.showMessageDialog(vista, "El docente se ha registrado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar docente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean validaEspacios(frmDocente vista) {
        String msj = "No deje vacios el/los campo/s:";
        boolean vacios = false;
        if (vista.txtNombre.getText().trim().equals("")) {
            msj += " Nombre";
            vacios = true;
            vista.txtNombre.setBackground(Color.PINK);
        } else {
            vista.txtNombre.setBackground(Color.white);
        }
        if (vista.txtApellido.getText().trim().equals("")) {
            msj += " Apellido";
            vacios = true;
            vista.txtApellido.setBackground(Color.PINK);
        } else {
            vista.txtApellido.setBackground(Color.white);
        }
        if (vista.txtDireccion.getText().trim().equals("")) {
            msj += " Dirección";
            vacios = true;
            vista.txtDireccion.setBackground(Color.PINK);
        } else {
            vista.txtDireccion.setBackground(Color.white);
        }
        if (vista.txtTelefono.getText().trim().equals("")) {
            msj += " Teléfono";
            vacios = true;
            vista.txtTelefono.setBackground(Color.PINK);
        } else {
            vista.txtTelefono.setBackground(Color.white);
        }
        if (vista.txtUsuario.getText().trim().equals("")) {
            msj += " Usuario";
            vacios = true;
            vista.txtUsuario.setBackground(Color.PINK);
        } else {
            vista.txtUsuario.setBackground(Color.white);
        }
        if (new String(vista.jPassword.getPassword()).trim().equals("")) {
            msj += " Contraseña";
            vacios = true;
            vista.jPassword.setBackground(Color.PINK);
        } else {
            vista.jPassword.setBackground(Color.white);
        }
        if (vacios) {
            JOptionPane.showMessageDialog(vista, msj, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return !vacios;
    }

    public void modificar(frmDocente vista, int row) {
        //Obtengo la lista misma que estaría en la tabla
        List<Docente> lista = dao.listar();
        //Obtengo el item que se encuentra en la misma ubicación de la fila
        d = lista.get(row);
        vista.txtNombre.setText(d.getNombre());
        vista.txtApellido.setText(d.getApellido());
        vista.txtDireccion.setText(d.getDireccion());
        vista.txtTelefono.setText(d.getTelefono());
        vista.idD = d.getId();

        u = udao.buscarId(d.getUsuarioId());
        vista.u = u;
        vista.txtUsuario.setText(u.getUser());
        vista.jPassword.setText(u.getPassword());

        vista.btnRegistrar.setText("Actualizar");
    }

    public void accionModificar(frmDocente vista) {
        d.setNombre(vista.txtNombre.getText().trim());
        d.setApellido(vista.txtApellido.getText().trim());
        d.setDireccion(vista.txtDireccion.getText().trim());
        d.setTelefono(vista.txtTelefono.getText().trim());
        d.setId(vista.idD);
        d.setUsuarioId(vista.u.getId());
        u = vista.u;
        String usuarioAnt = u.getUser();
        u.setUser(vista.txtUsuario.getText().trim());
        u.setPassword(new String(vista.jPassword.getPassword()).trim());

        if (usuarioAnt.equals(u.getUser()) || !usuarioExiste(u)) {
            if (dao.modificar(d)) {
                JOptionPane.showMessageDialog(vista, "El docente se ha actualizado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al actualizar  docente", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (udao.modificar(u)) {
                JOptionPane.showMessageDialog(vista, "El usuario se ha actualizado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al actualizar  usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No se puede registrar, nombre de usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void eliminar(frmDocente vista, int row) {
        int opcion = JOptionPane.showConfirmDialog(vista, "¿Esta seguro/a que desea eliminar?", "Mensaje de confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            //Obtengo la lista misma que estaría en la tabla
            List<Docente> lista = dao.listar();
            //Obtengo el item que se encuentra en la misma ubicación de la fila
            Docente d = new Docente();
            d = lista.get(row);
            if (dao.eliminar(d)) {
                JOptionPane.showMessageDialog(vista, "El docente se ha eliminado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                if (udao.eliminar(d.getUsuarioId())) {
                    JOptionPane.showMessageDialog(vista, "El usuario se ha eliminado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al eliminar docente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al eliminar docente", "Error", JOptionPane.ERROR_MESSAGE);
            }

            listar(vista.tabla);
            limpiarDatos(vista);

        } else {
            JOptionPane.showMessageDialog(vista, "El docente NO se ha eliminado", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public boolean usuarioExiste(Usuario u) {
        return udao.buscar(u);
    }

}
