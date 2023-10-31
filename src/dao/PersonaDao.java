package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Persona;

public class PersonaDao {

    private ConexionMysql conexion;

    /**
     * Método para cargar personas cuando se inicia el programa.
     * @return ObservavleList<Persona>
     */
    public ObservableList<Persona> cargarPersonas()  {
    	
    	ObservableList<Persona> persona = FXCollections.observableArrayList();
        try {
            conexion = new ConexionMysql();        	
        	String consulta = "select * from Persona;";
        	PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);      
        	ResultSet rs = pstmt.executeQuery();   				
			while (rs.next()) {
	            int idPersona = rs.getInt("idPersona");
	            String nombre = rs.getString("nombre");
	            String apellidos = rs.getString("apellidos");
	            int edad = rs.getInt("edad");
	            Persona p = new Persona(nombre,apellidos,edad,idPersona);
	            persona.add(p);
            }		              
			 rs.close();
	    } catch (SQLException e) {	    	
	    	e.printStackTrace();
	    }    
        return persona;    
    }
    
    /**
     * Metodo para insertar persona y devuelve el id.
     * @param p
     * @return int
     */
    public int insertarPersona(Persona p) {
    	String consulta = "INSERT INTO Persona(nombre,apellidos,edad) values('" + p.getNombre() + "','"+p.getApellidos() +"',"+p.getEdad()+")";
    	try {
			conexion = new ConexionMysql();
			PreparedStatement ps =conexion.getConexion().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			p.setId(rs.getInt(1));
			return p.getId();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}	
    }
    
    /**
     * Método para eliminar una persona de la BBDD 
     * @param p
     * @return boolean
     */
    public boolean eliminarPersona(Persona p) {
    	try {
	    	conexion = new ConexionMysql();
	    	String consulta = "DELETE FROM Persona WHERE idPersona = " + p.getId() + ";";
	    	PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
	    	ps.executeUpdate();
			conexion.CloseConexion();
			return true;
		} catch (SQLException e) {
			return false;
		}      
    }
    
    public boolean modificarPersona(Persona pAnt,Persona p) {
    	try {
			conexion = new ConexionMysql();
			String consulta = "UPDATE Persona SET nombre = '"+ p.getNombre() +"', apellidos = '" + p.getApellidos() + "', edad = " + p.getEdad() + " WHERE idPersona = " + pAnt.getId() + ";";
			PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
	    	ps.executeUpdate();
			conexion.CloseConexion();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    	
    }
}
