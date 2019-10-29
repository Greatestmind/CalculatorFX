package service;

import dao.OperacionDAO;
import exception.Error;
import model.Operacion;
import service.response.ResponseCreator;
import utils.DBHelper;
import exception.DBException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public class OperacionService {

    public OperacionService() {
    }

    private static OperacionDAO getUserDao() {
        return new OperacionDAO(DBHelper.getMysqlConnection());
    }

    @Context
    private HttpHeaders requestHeaders;

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("version").get(0);
    }

    @GET
    @Path("http://localhost:8080/{id}")
    public Response getCustomer(@PathParam("id") String id) {
        int ids=Integer.parseInt(id);
        Operacion operacion = getUserDao().selectOperacion(ids);
        if (operacion != null) {
            return ResponseCreator.success(getHeaderVersion(), operacion);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode(),
                    getHeaderVersion());
        }
    }



    @GET
    @Path("/operacions")
    public Response getCustomer() {
        List<Operacion> list = getUserDao().selectAllOperacions();
        if (list != null) {
            GenericEntity<List<Operacion>> entity = new GenericEntity<List<Operacion>>(
                    list) {
            };
            return ResponseCreator.success(getHeaderVersion(), entity);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode(),
                    getHeaderVersion());
        }
    }


    public List<Operacion> selectAllOperacion() {
        return getUserDao().selectAllOperacions();
    }

    public void insertUser(Operacion operacion) throws DBException {
        try {
            getUserDao().insertOperacion(operacion);
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

}
