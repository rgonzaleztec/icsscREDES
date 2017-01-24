package cr.ac.itcr.tecweather.model;

import java.io.Serializable;

/**
 * Created by Lincoln on 07/01/16.
 */
public class User implements Serializable {
    String id, name, email;
    int administrador, bloqueado;

    public User() {
    }

    public User(String id, String name, String email, int bloqueado, int administrador) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bloqueado = bloqueado;
        this.administrador = administrador;
    }

    public int getAdministrador() {
        return administrador;
    }

    public void setAdministrador(int administrador) {
        this.administrador = administrador;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
