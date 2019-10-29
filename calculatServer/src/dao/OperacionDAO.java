package dao;

import model.Operacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperacionDAO {

    private static final String SELECT_ALL_OPERACIONS = "SELECT * FROM operacions";
    private static final String INSERT_OPERACION_SQL = "INSERT INTO operacions" + "  (text) VALUES " +
            " (?);";
    private static final String SELECT_OPERACION_BY_ID = "select id,text from operacions where id =?";

    private Connection connection;

    public OperacionDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertOperacion(Operacion operacion) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OPERACION_SQL)) {
            preparedStatement.setString(1, operacion.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Operacion selectOperacion(int id) {
        Operacion operacion = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OPERACION_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String text = rs.getString("text");
                operacion = new Operacion(id, text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operacion;
    }

    public List<Operacion> selectAllOperacions() {
        List<Operacion> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_OPERACIONS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("text");
                users.add(new Operacion(id, text));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
