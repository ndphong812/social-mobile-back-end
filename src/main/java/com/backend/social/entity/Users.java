package com.backend.social.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="email",unique = true)
	@Email(message = "the email must be in correct format")
	@NotBlank
	private String email;
	
	@Column(name="phone",unique = true)
	@NotBlank
	private String phone;

	@Column(name="password")
	@Size(min = 6,message = "The greatest length of the name is 6")
	private String password;
	
	@Column(name="full_name")
	@NotBlank
	@Size(max=100,message="The greatest length of the fullName is 100")
	private String fullName;
	
	@Column(name="nick_name")
	@NotBlank
	private String nickName;
	
	@Column(name="avatar")
	@NotBlank
	private String avatar;
	
	@Column(name="dob")
	@NotBlank
	private Date dob;
	
	@Column(name="created_at")
	@NotBlank
	private Timestamp createdAt;
	
	@Column(name="status")
	@NotBlank
	private String status;
	
	@Column(name="is_online")
	@NotBlank
	private Boolean isOnline;
	
	@Column(name="role")
	@NotBlank
	private String role;


	
	
}
