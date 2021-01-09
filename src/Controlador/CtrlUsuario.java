package Controlador;

import Modelo.Tipo;
import Modelo.TipoDAO;
import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Vista.frmUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CtrlUsuario implements ActionListener{

    UsuarioDAO  dao = new UsuarioDAO();
    Usuario u = new Usuario();
    TipoDAO tdao = new TipoDAO();
    Tipo t = new Tipo();
    frmUsuario vista = new frmUsuario();
    DefaultTableModel modelo = new DefaultTableModel();

    public CtrlUsuario(frmUsuario v) {
        this.vista=v;
        //Cargar en combobox de Tipo
        cargarTipos(this.vista.cbxTipo);
        //Cargar acciones
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnLimpiar){
            limpiarDatos(vista);
        }
        if(e.getSource()==vista.btnRegistrar){
            registrarUsuario(vista);
            limpiarDatos(vista);
        }
        if(e.getSource()==vista.btnCancelar){
            limpiarDatos(vista);
        }
    }
    
    public void limpiarDatos(frmUsuario vista){
        vista.txtUsuario.setText("");
        vista.jpassword.setText("");
        vista.cbxTipo.setSelectedIndex(0);
    }
    
    public void cargarTipos(JComboBox cbx){
        cbx.removeAllItems();
        cbx.addItem("Seleccione una opción");
        //Cargo los datos
        List<Tipo> lista = tdao.listar();
        for (Tipo tipo : lista) {
            cbx.addItem(tipo);
        }
    }
    
    public void registrarUsuario(frmUsuario vista){
        Usuario u = new Usuario();
        u.setUser(vista.txtUsuario.getText());
        u.setPassword(new String(vista.jpassword.getPassword()));
        t = (Tipo) vista.cbxTipo.getSelectedItem();
        u.setTipoId(t.getId());
        if(dao.registrar(u)>0){
            JOptionPane.showMessageDialog(vista, "Usuario se ha registrado correctamente", "Correcto",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(vista, "Ha ocurrido un problema al registrar","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
