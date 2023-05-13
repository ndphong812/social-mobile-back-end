package com.backend.social.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
	@NotBlank(message = "phone not blank")
	private String phone;

	@Column(name="password")
	@Size(min = 6,message = "password must be greater than 6 char")
	private String password;
	
	@Column(name="full_name")
	@NotBlank
	@Size(max=100,message="The greatest length of the fullName is 100")
	private String fullName;
	
	@Column(name="nick_name")
	private String nickName;
	
	@Column(name="avatar")
	@Size(max = 200)
	private String avatar;
	
	@Column(name="dob")
	@Past
	private Date dob;
	
	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name = "locked")
	private Boolean locked;

	@Column(name = "enable")
	private Boolean enable;
	
	@Column(name="is_online")
	private Boolean isOnline;
	
	@Column(name="role")
	@NotBlank
	private String role;

}
