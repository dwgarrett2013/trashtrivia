/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

/*
This class represents user role objects in the database used to specific what permission as user has.
 */

public class Role {
    private String id;  //role id
    private String roleName;    //role name

    public Role(String id, String roleName){
        this.id=id;
        this.roleName=roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
