
package com.krysteltm.APP;

import com.krysteltm.UTIL.ConexionSQLite;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        Connection conn = ConexionSQLite.conectar();

        if (conn != null) {
            System.out.println("🚀 El sistema puede usar la base de datos");
        }
    }
}
