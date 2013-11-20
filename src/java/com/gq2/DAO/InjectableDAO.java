package com.gq2.DAO;

import java.sql.Connection;

/**
 * Las clases que implementan esta interfaz reciben una conexion con
 * setConnection().
 *
 * El uso de esta interfaz con DAOProxy en DAOFactory evita tener que hacer el
 * tratamiento de conexiones (obtener conexión, comitar, hacer rollback,
 * devolver conexión) en cada DAO.
 *
 * Todo lo que ocurra en un método de la DAO ocurre dentro de una transacción,
 * siempre y cuando no se creen otras DAO. Se mantiene la transacción si se
 * llama a otros métodos dentro de la DAO (pues usan la misma conexión). Se
 * mantiene la transacción si se llama a métodos de otras DAO si se pasa el
 * objeto Connection por parámetro.
 */
public interface InjectableDAO {

    void setConnection(Connection conn);
}
