package com.java.examen;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.List;

public class PrincipalController implements Initializable {

    // Tab 1
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colUsuario;
    @FXML private TableColumn<Usuario, String> colNombres;
    @FXML private TableColumn<Usuario, String> colApellidos;
    @FXML private TableColumn<Usuario, String> colDireccion;
    @FXML private TableColumn<Usuario, String> colTelefono;

    // Tab 2
    @FXML private TextField txtNomUsuario;
    @FXML private PasswordField txtPasswordNuevo;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;

    // Tab 3
    @FXML private TextField txtIdEliminar;

    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        colUsuario.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNomUsuario()));
        colNombres.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNombres()));
        colApellidos.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getApellidos()));
        colDireccion.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getDireccion()));
        colTelefono.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTelefono()));
    }

    // ---------- TAB 1: LECTURA ----------
    @FXML
    private void onLeer() {
        try {
            List<Usuario> lista = dao.listar();
            tablaUsuarios.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            e.printStackTrace();
            mostrar(Alert.AlertType.ERROR, "Error al leer la base de datos: " + e.getMessage());
        }
    }

    // ---------- TAB 2: INSERTAR ----------
    @FXML
    private void onInsertar() {
        String nomUsuario = txtNomUsuario.getText().trim();
        String password   = txtPasswordNuevo.getText();
        String nombres    = txtNombres.getText().trim();
        String apellidos  = txtApellidos.getText().trim();

        if (nomUsuario.isEmpty() || password.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()) {
            mostrar(Alert.AlertType.WARNING,
                    "Usuario, password, nombres y apellidos son obligatorios");
            return;
        }

        Usuario u = new Usuario(0, nomUsuario, password, nombres, apellidos,
                txtDireccion.getText().trim(), txtTelefono.getText().trim());
        try {
            if (dao.insertar(u)) {
                mostrar(Alert.AlertType.INFORMATION, "Usuario registrado correctamente");
                limpiarFormulario();
            }
        } catch (Exception e) {
            mostrar(Alert.AlertType.ERROR, "No se pudo registrar: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtNomUsuario.clear();
        txtPasswordNuevo.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtDireccion.clear();
        txtTelefono.clear();
    }

    // ---------- TAB 3: ELIMINAR REGISTRO ----------
    @FXML
    private void onEliminar() {
        String texto = txtIdEliminar.getText().trim();

        int id;
        try {
            id = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            mostrar(Alert.AlertType.WARNING, "Ingrese un ID numérico válido");
            return;
        }

        try {
            if (dao.eliminar(id)) {
                mostrar(Alert.AlertType.INFORMATION, "Usuario eliminado");
                txtIdEliminar.clear();
            } else {
                mostrar(Alert.AlertType.WARNING, "No existe un usuario con ID " + id);
            }
        } catch (Exception e) {
            mostrar(Alert.AlertType.ERROR, "No se pudo eliminar: " + e.getMessage());
        }
    }

    // ---------- Mensajeria ----------
    private void mostrar(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo, mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
