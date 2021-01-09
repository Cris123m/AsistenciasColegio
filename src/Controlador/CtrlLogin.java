package Controlador;

import Modelo.Tipo;
import Modelo.TipoDAO;
import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Vista.frmLogin;
import Vista.frmMenuDocente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CtrlLogin implements ActionListener{
    UsuarioDAO  dao = new UsuarioDAO();
    Usuario u = new Usuario();
    TipoDAO tdao = new TipoDAO();
    Tipo t = new Tipo();
    frmLogin vista = new frmLogin();

    public CtrlLogin(frmLogin v) {
        this.vista=v;
        this.vista.btnLogin.addActionListener(this);
        this.vista.btnCerrar.addActionListener(this);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnLogin){
            login(vista);
            limpiarDatos(vista);
        }
        if(e.getSource()==vista.btnCerrar){
            vista.dispose();
            limpiarDatos(vista);
        }
    }
    
    public void login(frmLogin vista){
        u.setUser(vista.txtUsuario.getText().trim());
        u.setPassword(new String(vista.jpassword.getPassword()).trim());
        if(dao.login(u)){
            switch (u.getTipoId()) {
                case 1:
                    frmMenuDocente fmd = new frmMenuDocente();
                    CtrlMenuDocente cmd = new CtrlMenuDocente(fmd);
                    fmd.setVisible(true);
                    fmd.setLocationRelativeTo(null);
                    vista.setVisible(false);
                    break;
                case 2:
                    //Menú de estudiante
                default:
                    throw new AssertionError();
            }
        }
    }
    
    public void limpiarDatos(frmLogin vista){
        vista.txtUsuario.setText("");
        vista.jpassword.setText("");
    }
}
