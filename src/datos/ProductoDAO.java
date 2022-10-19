/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import entidad.Producto;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ProductoDAO {

    public static int insertar(Producto x) throws SQLException {
        Connection cn = Conexion.getInstancia().getConexion();
        int estado = 1;
        CallableStatement cs = null;
        try {
            cs = cn.prepareCall("call spInsertarProducto(?,?,?,?,?)");
            cs.setString(1, x.getIdproducto());
            cs.setString(2, x.getDescripcion());
            cs.setDouble(3, x.getPrecio());
            cs.setInt(4, x.getStock());
            cs.registerOutParameter(5, Types.INTEGER);
            cs.executeUpdate();
            estado = cs.getInt(5);
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return estado;
    }

    public static Producto buscarProducto(String idproducto) throws SQLException {
        Connection cn = Conexion.getInstancia().getConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        Producto prod = null;
        try {
            cs = cn.prepareCall("call spBuscarProducto(?)");
            cs.setString(1, idproducto);
            rs = cs.executeQuery();
            if (rs.next()) {
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                prod = new Producto(idproducto, descripcion, precio, stock);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return prod;
    }

    public static void actualizar(Producto x) throws SQLException {
        Connection cn = Conexion.getInstancia().getConexion();
        CallableStatement cs = null;
        try {
            cs = cn.prepareCall("call spModificarProducto(?,?,?,?)");
            cs.setString(1, x.getIdproducto());
            cs.setString(2, x.getDescripcion());
            cs.setDouble(3, x.getPrecio());
            cs.setInt(4, x.getStock());
            cs.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static void eliminar(String idproducto) throws SQLException {
        Connection cn = Conexion.getInstancia().getConexion();
        CallableStatement cs = null;
        try {
            cs = cn.prepareCall("call spEliminarProducto(?)");
            cs.setString(1, idproducto);
            cs.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR" + ex.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static void mostrarProductos(DefaultTableModel modelo) throws SQLException {
        Connection cn = Conexion.getInstancia().getConexion();
        ResultSet rs = null;
        CallableStatement cs = null;
        String titulos[] = {"idproducto", "descripcion", "precio", "stock"};
        modelo.getDataVector().removeAllElements();
        modelo.setColumnIdentifiers(titulos);
        try {
            cs = cn.prepareCall("call spMostrarProductos()");
            rs = cs.executeQuery();
            while (rs.next()) {
                String idproducto = rs.getString(1);
                String descripcion = rs.getString(2);
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String fila[] = {idproducto, descripcion, String.valueOf(precio), String.valueOf(stock)};
                modelo.addRow(fila);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }
}
