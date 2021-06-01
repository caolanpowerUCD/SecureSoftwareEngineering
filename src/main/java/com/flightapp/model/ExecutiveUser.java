package com.flightapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "executiveusers")
public class ExecutiveUser implements Serializable{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        
        private String username;
        
        private String password;
        @Transient
        private String passwordConfirm;
        
        private String first_name;
        
        private String last_name;
        
        private String address;
        
        private String email;
        private String phone_number;
        //private boolean isUser;



    public String getUsername() {
		return this.username;
	}

    public void setUsername(String username) {
		this.username = username;
	}

    public String getPassword() {
		return this.password;
	}

    public void setPassword(String password) {
		this.password = password;
	}

        public String getPasswordConfirm() {
                return passwordConfirm;
        }

        public void setPasswordConfirm(String passwordConfirm) {
                this.passwordConfirm = passwordConfirm;
        }
    

    public String getFirst_name() {
		return this.first_name;
	}

    public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

    
    public String getLast_name() {
		return this.last_name;
	}

    public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

    public String getAddress() {
		return this.address;
	}

    public void setAddress(String address) {
		this.address = address;
	}

    public String getEmail() {
		return this.email;
	}

    public void setEmail(String email) {
		this.email = email;
	}

    public String getPhone_number() {
		return this.phone_number;
	}

    public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

        // public boolean isIsUser() {
        //         return this.isUser;
        // }

        // public void setIsUser(boolean isUser) {
        //         this.isUser = isUser;
        // }

        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="executiveuser_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName="id")
        )
        private Set<Role> roles = new HashSet<>();

        public Set<Role> getRoles() {
                return this.roles;
        }
        
        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }

        public Long getId() {
                return this.id;
        }

        public void setId(Long id) {
                this.id = id;
        }    
        public ExecutiveUser(){ }

        public ExecutiveUser(String username, String password, String first_name, String last_name, String address, String email, String phone_number, boolean isUser) {
            this.username = username;
            this.password = password;
            this.first_name = first_name;
            this.last_name = last_name;
            this.address = address;
            this.email = email;
            this.phone_number = phone_number;
            //this.isUser = isUser;
        }

        public ExecutiveUser(String first_name, String last_name, String address, String email, String phone_number){
                this.first_name = first_name;
                this.last_name = last_name;
                this.address = address;
                this.email = email;
                this.phone_number = phone_number;
                //this.isUser = isUser;
        }
}
