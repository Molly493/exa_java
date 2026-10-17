package com.java.examen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;

    private final UsuarioDAO dao = new UsuarioDAO();
    private List<Usuario> usuarios = new ArrayList<>();

    /** Se ejecuta al iniciarse la ventana: carga los usuarios desde la BD. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            usuarios = dao.listar();
        } catch (Exception e) {
            lblMensaje.setText("No se pudo conectar a la base de datos");
            e.printStackTrace();
        }
    }

    @FXML
    private void onIngresar() {
        String user = txtUsuario.getText().trim();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            lblMensaje.setText("Ingrese usuario y contraseña");
            return;
        }

        boolean valido = usuarios.stream().anyMatch(
                u -> u.getNomUsuario().equals(user) && u.getPassword().equals(pass));

        if (valido) {
            abrirVentanaPrincipal();
        } else {
            lblMensaje.setText("Usuario o contraseña incorrectos");
            txtPassword.clear();
        }
    }

    private void abrirVentanaPrincipal() {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/java/examen/principal.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestión de usuarios");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana de login
            ((Stage) txtUsuario.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "No se pudo abrir la ventana principal").show();
        }
    }
}
